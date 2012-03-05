package com.oracle.coherence.common.runtime.process;

import com.tangosol.util.Base;
import com.tangosol.util.NullImplementation;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * An implementation of {@link Process} that represents a pseudo-process
 * running in an isolated ClassLoader withing a JVM.
 *
 * @author Jonathan Knight
 */
public class InternalProcess extends Process {

    private String name;
    private ClassLoader classLoader;
    private String className;
    private String startMethodName;
    private String stopMethodName;
    private String[] args;
    private InputStream inputStream;
    private InputStream errorStream;
    private InProcessRunner startRunnable;


    /**
     * Create an instance of an InternalProcess
     * @param name - the Application name
     * @param classLoader - the {@link ClassLoader} providing the isolation for this pseudo-process
     * @param className - the name of the Class to run the pseudo-process
     * @param startMethodName - the name of the method to use to start the pseudo-process
     * @param stopMethodName - the name of the method to call to stop the pseudo-process
     */
    public InternalProcess(String name, ClassLoader classLoader, String className, String startMethodName, String stopMethodName, String[] args) {
        this.name = name;
        this.classLoader = classLoader;
        this.className = className;
        this.startMethodName = startMethodName;
        this.stopMethodName = stopMethodName;
        this.args = args;
        this.inputStream = new NullInputStream();
        this.errorStream = new NullInputStream();
    }

    public String getName() {
        return name;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public String getClassName() {
        return className;
    }

    public String getStartMethodName() {
        return startMethodName;
    }

    public String getStopMethodName() {
        return stopMethodName;
    }

    public String[] getArgs() {
        return args;
    }

    /**
     * Capturing of the OutputStream in order to pipe input to this
     * process is not supported in InternalProcess.
     *
     * @return a {@link com.tangosol.util.NullImplementation.NullOutputStream}.
     */
    @Override
    public OutputStream getOutputStream() {
        return NullImplementation.getOutputStream();
    }

    /**
     * Gets the input stream of the subprocess.
     * The stream obtains data piped from the standard output stream
     * of the process represented by this <code>Process</code> object.
     * <p>
     * Implementation note: It is a good idea for the input stream to
     * be buffered.
     *
     * @return  the input stream connected to the normal output of the sub-process.
     * @see ProcessBuilder#redirectErrorStream()
     */
    @Override
    public InputStream getInputStream() {
        return  inputStream;
    }

    /**
     * Gets the error stream of the subprocess.
     * The stream obtains data piped from the error output stream of the
     * process represented by this <code>Process</code> object.
     * <p>
     * Implementation note: It is a good idea for the input stream to be
     * buffered.
     *
     * @return  the input stream connected to the error stream of the sub-process.
     * @see ProcessBuilder#redirectErrorStream()
     */
    @Override
    public InputStream getErrorStream() {
        return errorStream;
    }

    @Override
    public int waitFor() throws InterruptedException {
        return 0;
    }

    @Override
    public int exitValue() {
        return 0;
    }

    public void start() {
        if (startRunnable == null) {
            startRunnable = createRunner(className, startMethodName, null);
            invoke(startRunnable);
        }
    }

    @Override
    public void destroy() {
        Object instance = (startRunnable != null) ? startRunnable.getInstance() : null;
        invoke(createRunner(className, stopMethodName, instance));
        startRunnable = null;
    }

    protected InProcessRunner getStartRunnable() {
        return startRunnable;
    }

    protected void setStartRunnable(InProcessRunner startRunnable) {
        this.startRunnable = startRunnable;
    }

    protected InProcessRunner createRunner(String className, String methodName, Object instance) {
        return new InProcessRunner(name, classLoader, className, methodName, args, instance);
    }

    /**
     * Invoke the specified method on the specified class
     * within the current pseudo-process isolation.
     * </p>
     * If the method is not static the class must have a default empty
     * constructor to allow an instance of it to be created.
     * </p>
     * This allows methods to be called that will execute as though they
     * were part of the current pseudo-process' isolation. Any return value
     * from the method is returned to the caller. As the method will execute
     * within another ClassLoader it is best to only use Java primitives or
     * classes that are part of the JRE as return types.
     *
     * @param className - the name of the class to run the specified method on
     * @param methodName - the method to run
     * @return any return value from invoking the method
     */
    @SuppressWarnings({"unchecked"})
    public <T> T invoke(String className, String methodName) {
        InProcessRunner runner = createRunner(className, methodName, null);
        invoke(runner);
        return (T)runner.getResult();
    }

    private void invoke(final InProcessRunner runner) {
        Thread t = new Thread(runner);
        t.start();
        synchronized(runner) {
            while (!runner.isFinished()) {
                try {
                    runner.wait(1000);
                } catch (InterruptedException e) {
                    // ignored
                }
            }
        }

        Throwable error = runner.getError();
        if(error != null) {
            throw Base.ensureRuntimeException(error);
        }
    }

}
