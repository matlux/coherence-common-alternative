package com.oracle.coherence.common.runtime.process;

import com.oracle.coherence.common.classloader.ClassLoaderOutputStream;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A Runnable that will call a method on a class isolated with a specific
 * ClassLoader and return any return value from invoking the method.
 * If the method is not static an instance of the class will be created.
 *
 * @author Jonathan Knight
 */
public class InProcessRunner implements Runnable {
    private final String name;
    private final ClassLoader classLoader;
    private final String className;
    private final String methodName;
    private String[] args;
    private Object instance;
    private AtomicReference<Throwable> throwable = new AtomicReference<Throwable>(null);
    private Object result;

    private ClassLoaderOutputStream.Context outputStreamContext;

    private AtomicBoolean finished = new AtomicBoolean(false);

    /**
     * Create an instance of InProcessRunner.
     * If the method is not static then an instance of the class will be created by calling
     * the default no-arg constructor.
     *
     * @param name - the name of the process
     * @param classLoader - the ClassLoader to use for isolation of the method invocation
     * @param className - the name of the class to call the method on
     * @param methodName - the name of the method
     * @param the arguments to pass to the start method
     */
    public InProcessRunner(String name, ClassLoader classLoader, String className, String methodName, String[] args) {
        this(name, classLoader, className, methodName, args, null);
    }

    /**
     * Create an instance of InProcessRunner.
     * If the method is not static then the instance specified will be used to invoke the method.
     * If instance is null an instance of the class will be created by calling
     * the default no-arg constructor.
     *
     * @param name - the name of the process
     * @param classLoader - the ClassLoader to use for isolation of the method invocation
     * @param className - the name of the class to call the method on
     * @param methodName - the name of the method
     * @param the arguments to pass to the start method
     * @param instance - the instance of the class to use to invoke the method if the method is not static
     */
    public InProcessRunner(String name, ClassLoader classLoader, String className, String methodName, String[] args, Object instance) {
        this.name = name;
        this.classLoader = classLoader;
        this.className = className;
        this.methodName = methodName;
        this.args = args;
        this.instance = instance;
        this.outputStreamContext = new ClassLoaderOutputStream.Context(name);
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    /**
     * @return any error that was thrown as a result of invoking the method or null if no error occurred
     */
    public Throwable getError() {
        return throwable.get();
    }

    /**
     * @return the instance of the class used to invoke the method if the method was not static
     */
    public Object getInstance() {
        return instance;
    }

    /**
     * @return any return value from invoking the method
     */
    @SuppressWarnings({"unchecked"})
    public <T> T getResult() {
        return (T) result;
    }

    public boolean isFinished() {
        return finished.get();
    }

    public void run() {
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            System.err.println("Setting ClassLoaderOutputStream context to " + name + " original=" + ClassLoaderOutputStream.getContext());
            ClassLoaderOutputStream.setContext(new ClassLoaderOutputStream.Context(name));
            initializeCoherenceIncubatorLogging();
            Class aClass = classLoader.loadClass(className);

            Method method;
            boolean hasArgs;
            try {
                method = aClass.getMethod(methodName, new String[0].getClass());
                hasArgs = true;
            } catch (NoSuchMethodException e) {
                method = aClass.getMethod(methodName);
                hasArgs = false;
            }

            if (!Modifier.isStatic(method.getModifiers())) {
                if (instance == null) {
                    instance = aClass.newInstance();
                }
            }

            if (hasArgs) {
                result = method.invoke(instance, (Object)args);
            } else {
                result = method.invoke(instance);
            }

        } catch (Throwable t) {
            throwable.set(t);
        }

        synchronized (this) {
            finished.set(true);
            this.notifyAll();
        }
    }

    private void initializeCoherenceIncubatorLogging() throws Exception {
        Class handlerClass = Class.forName("com.oracle.coherence.common.logging.CoherenceLogHandler");
        Method method = handlerClass.getDeclaredMethod("initializeIncubatorLogging");
        method.invoke(null);
    }
}
