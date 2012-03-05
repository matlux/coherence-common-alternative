package com.oracle.coherence.common.runtime.process;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Interface representing a class that can build instances of {@link Process}.
 *
 * @author Jonathan Knight
 */
public abstract class JavaProcessBuilder {

    private ProcessBuilder delegate;
    private Properties systemProperties = new Properties();
    private List<String> arguments = new ArrayList<String>();

    /**
     */
    protected JavaProcessBuilder() {
        this("java");
    }

    /**
     */
    public JavaProcessBuilder(String executableName) {
        this.delegate = new ProcessBuilder(executableName);
    }

    /**
     * Sets this process builder's operating system program and
     * arguments.  This method does <i>not</i> make a copy of the
     * <code>command</code> list.  Subsequent updates to the list will
     * be reflected in the state of the process builder.  It is not
     * checked whether <code>command</code> corresponds to a valid
     * operating system command.</p>
     *
     * @param command The list containing the program and its arguments
     * @return This process builder
     * @throws NullPointerException If the argument is <code>null</code>
     */
    public JavaProcessBuilder command(List<String> command) {
        if (command == null) {
            throw new NullPointerException();
        }
        delegate.command(command);
        return this;
    }

    /**
     * Sets this process builder's operating system program and
     * arguments.  This is a convenience method that sets the command
     * to a string list containing the same strings as the
     * <code>command</code> array, in the same order.  It is not
     * checked whether <code>command</code> corresponds to a valid
     * operating system command.</p>
     *
     * @param command A string array containing the program and its arguments
     * @return This process builder
     */
    public JavaProcessBuilder command(String... command) {
        delegate.command(command);
        return this;
    }

    /**
     * Returns this process builder's operating system program and
     * arguments.  The returned list is <i>not</i> a copy.  Subsequent
     * updates to the list will be reflected in the state of this
     * process builder.</p>
     *
     * @return This process builder's program and its arguments
     */
    public List<String> command() {
        return delegate.command();
    }

    /**
     * Returns a string map view of this process builder's environment.
     * <p/>
     * Whenever a process builder is created, the environment is
     * initialized to a copy of the current process environment (see
     * {@link System#getenv()}).  Subprocesses subsequently started by
     * this object's {@link #start()} method will use this map as
     * their environment.
     * <p/>
     * <p>The returned object may be modified using ordinary {@link
     * java.util.Map Map} operations.  These modifications will be
     * visible to subprocesses started via the {@link #start()}
     * method.  Two <code>ProcessBuilder</code> instances always
     * contain independent process environments, so changes to the
     * returned map will never be reflected in any other
     * <code>ProcessBuilder</code> instance or the values returned by
     * {@link System#getenv System.getenv}.
     * <p/>
     * <p>If the system does not support environment variables, an
     * empty map is returned.
     * <p/>
     * <p>The returned map does not permit null keys or values.
     * Attempting to insert or query the presence of a null key or
     * value will throw a {@link NullPointerException}.
     * Attempting to query the presence of a key or value which is not
     * of type {@link String} will throw a {@link ClassCastException}.
     * <p/>
     * <p>The behavior of the returned map is system-dependent.  A
     * system may not allow modifications to environment variables or
     * may forbid certain variable names or values.  For this reason,
     * attempts to modify the map may fail with
     * {@link UnsupportedOperationException} or
     * {@link IllegalArgumentException}
     * if the modification is not permitted by the operating system.
     * <p/>
     * <p>Since the external format of environment variable names and
     * values is system-dependent, there may not be a one-to-one
     * mapping between them and Java's Unicode strings.  Nevertheless,
     * the map is implemented in such a way that environment variables
     * which are not modified by Java code will have an unmodified
     * native representation in the subprocess.
     * <p/>
     * <p>The returned map and its collection views may not obey the
     * general contract of the {@link Object#equals} and
     * {@link Object#hashCode} methods.
     * <p/>
     * <p>The returned map is typically case-sensitive on all platforms.
     * <p/>
     * <p>If a security manager exists, its
     * {@link SecurityManager#checkPermission checkPermission}
     * method is called with a
     * <code>{@link RuntimePermission}("getenv.*")</code>
     * permission.  This may result in a {@link SecurityException} being
     * thrown.
     * <p/>
     * <p>When passing information to a Java subprocess,
     * <a href=System.html#EnvironmentVSSystemProperties>system properties</a>
     * are generally preferred over environment variables.</p>
     *
     * @return This process builder's environment
     * @throws SecurityException If a security manager exists and its
     *                           {@link SecurityManager#checkPermission checkPermission}
     *                           method doesn't allow access to the process environment
     * @see Runtime#exec(String[], String[], java.io.File)
     * @see System#getenv()
     */
    public Map<String, String> environment() {
        return delegate.environment();
    }

    /**
     * Returns this process builder's working directory.
     * <p/>
     * Subprocesses subsequently started by this object's {@link
     * #start()} method will use this as their working directory.
     * The returned value may be <code>null</code> -- this means to use
     * the working directory of the current Java process, usually the
     * directory named by the system property <code>user.dir</code>,
     * as the working directory of the child process.</p>
     *
     * @return This process builder's working directory
     */
    public File directory() {
        return delegate.directory();
    }

    /**
     * Sets this process builder's working directory.
     * <p/>
     * Subprocesses subsequently started by this object's {@link
     * #start()} method will use this as their working directory.
     * The argument may be <code>null</code> -- this means to use the
     * working directory of the current Java process, usually the
     * directory named by the system property <code>user.dir</code>,
     * as the working directory of the child process.</p>
     *
     * @param directory The new working directory
     * @return This process builder
     */
    public JavaProcessBuilder directory(File directory) {
        delegate.directory(directory);
        return this;
    }

    public Properties systemProperties() {
        return systemProperties;
    }

    public JavaProcessBuilder systemProperty(String key, String value) {
        systemProperties.setProperty(key, value);
        return this;
    }

    public List<String> arguments() {
        return arguments;
    }

    public JavaProcessBuilder argument(String arg) {
        arguments.add(arg);
        return this;
    }

    /**
     * Starts a new process using the attributes of this process builder.
     *
     * <p>The new process will
     * invoke the command and arguments given by {@link #command()},
     * in a working directory as given by {@link #directory()},
     * with a process environment as given by {@link #environment()}.
     *
     * <p>This method checks that the command is a valid operating
     * system command.  Which commands are valid is system-dependent,
     * but at the very least the command must be a non-empty list of
     * non-null strings.
     *
     * <p>If there is a security manager, its
     * {@link SecurityManager#checkExec checkExec}
     * method is called with the first component of this object's
     * <code>command</code> array as its argument. This may result in
     * a {@link SecurityException} being thrown.
     *
     * <p>Starting an operating system process is highly system-dependent.
     * Among the many things that can go wrong are:
     * <ul>
     * <li>The operating system program file was not found.
     * <li>Access to the program file was denied.
     * <li>The working directory does not exist.
     * </ul>
     *
     * <p>In such cases an exception will be thrown.  The exact nature
     * of the exception is system-dependent, but it will always be a
     * subclass of {@link java.io.IOException}.
     *
     * <p>Subsequent modifications to this process builder will not
     * affect the returned {@link Process}.</p>
     *
     * @return  A new {@link Process} object for managing the subprocess
     *
     * @throws  NullPointerException
     *          If an element of the command list is null
     *
     * @throws  IndexOutOfBoundsException
     *          If the command is an empty list (has size <code>0</code>)
     *
     * @throws  SecurityException
     *          If a security manager exists and its
     *          {@link SecurityManager#checkExec checkExec}
     *          method doesn't allow creation of the subprocess
     *
     * @throws  java.io.IOException
     *          If an I/O error occurs
     *
     * @see     Runtime#exec(String[], String[], java.io.File)
     * @see     SecurityManager#checkExec(String)
     */
    public Process start() throws IOException {
        return delegate.start();
    }

}
