package com.oracle.coherence.common.runtime.process;

import com.oracle.coherence.common.classloader.ChildFirstClassLoader;

import java.io.IOException;

/**
 * An implementation of {@link JavaProcessBuilder} that builds an instance of
 * {@link InternalProcess}
 * </p>
 * A {@link InternalProcess} is an instance of {@link Process} that represents
 * a pseudo-process running isolated within a single JVM.
 *
 * @author Jonathan Knight
 */
public class InProcessBuilder extends JavaProcessBuilder {

    private String applicationName;

    private String applicationClassName;

    private String startMethod;

    private String stopMethod;

    private String[] args;

    public InProcessBuilder(String applicationName, String applicationClassName, String startMethod, String stopMethod, String[] args) {
        this.applicationName = applicationName;
        this.applicationClassName = applicationClassName;
        this.startMethod = startMethod;
        this.stopMethod = stopMethod;
        this.args = args;
    }

    /**
     * Starts a new process using the attributes of this process builder.
     * <p/>
     * <p>The new process will
     * invoke the command and arguments given by {@link #command()},
     * in a working directory as given by {@link #directory()},
     * with a process environment as given by {@link #environment()}.
     * <p/>
     * <p>This method checks that the command is a valid operating
     * system command.  Which commands are valid is system-dependent,
     * but at the very least the command must be a non-empty list of
     * non-null strings.
     * <p/>
     * <p>If there is a security manager, its
     * {@link SecurityManager#checkExec checkExec}
     * method is called with the first component of this object's
     * <code>command</code> array as its argument. This may result in
     * a {@link SecurityException} being thrown.
     * <p/>
     * <p>Starting an operating system process is highly system-dependent.
     * Among the many things that can go wrong are:
     * <ul>
     * <li>The operating system program file was not found.
     * <li>Access to the program file was denied.
     * <li>The working directory does not exist.
     * </ul>
     * <p/>
     * <p>In such cases an exception will be thrown.  The exact nature
     * of the exception is system-dependent, but it will always be a
     * subclass of {@link java.io.IOException}.
     * <p/>
     * <p>Subsequent modifications to this process builder will not
     * affect the returned {@link Process}.</p>
     *
     * @return A new {@link Process} object for managing the subprocess
     * @throws NullPointerException      If an element of the command list is null
     * @throws IndexOutOfBoundsException If the command is an empty list (has size <code>0</code>)
     * @throws SecurityException         If a security manager exists and its
     *                                   {@link SecurityManager#checkExec checkExec}
     *                                   method doesn't allow creation of the subprocess
     * @throws java.io.IOException       If an I/O error occurs
     * @see Runtime#exec(String[], String[], java.io.File)
     * @see SecurityManager#checkExec(String)
     */
    @Override
    public Process start() throws IOException {
        try {
            ClassLoader classLoader = createClassLoader();
            InternalProcess process = new InternalProcess(applicationName, classLoader, applicationClassName, startMethod, stopMethod, args);
            process.start();
            return process;
        } catch (Exception e) {
            throw new IOException("Error starting process", e);
        }
    }

    public ClassLoader createClassLoader()  throws Exception {
        String classPath = environment().get("CLASSPATH");
        return ChildFirstClassLoader.newInstance(applicationName, classPath, systemProperties());
    }

}
