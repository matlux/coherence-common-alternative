/*
 * File: Application.java
 *
 * Copyright (c) 2011. All Rights Reserved. Oracle Corporation.
 *
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * This software is the confidential and proprietary information of Oracle
 * Corporation. You shall not disclose such confidential and proprietary
 * information and shall use it only in accordance with the terms of the license
 * agreement you entered into with Oracle Corporation.
 *
 * Oracle Corporation makes no representations or warranties about the
 * suitability of the software, either express or implied, including but not
 * limited to the implied warranties of merchantability, fitness for a
 * particular purpose, or non-infringement. Oracle Corporation shall not be
 * liable for any damages suffered by licensee as a result of using, modifying
 * or distributing this software or its derivatives.
 *
 * This notice may not be removed or altered.
 */

package com.oracle.coherence.common.runtime;

import java.util.Properties;

/**
 * An {@link Application} provides a mechanism to represent, access and control an executing process, typically
 * managed by an operating system.
 *
 * @see ApplicationBuilder
 *
 * @author Brian Oliver
 */
public interface Application
{
    /**
     * Obtains the environment variables that were supplied to the {@link Application} when it was realized.
     *
     * @return {@link Properties} of containing name value pairs, each one representing an environment variable provided
     *         to the {@link Application} when it was realized.
     */
    public Properties getEnvironmentVariables();


    /**
     * Obtains the name of the {@link Application}.
     *
     * @return The name of the {@link Application}.
     */
    public String getName();


    /**
     * Destroys the running {@link Application}.  Upon returning from this method you can safely assume the
     * {@link Application} is no longer running.
     */
    public void destroy();

    /**
     * causes the current thread to wait, if necessary, until the
     * process represented by this <code>Process</code> object has
     * terminated. This method returns
     * immediately if the subprocess has already terminated. If the
     * subprocess has not yet terminated, the calling thread will be
     * blocked until the subprocess exits.
     *
     * @return     the exit value of the process. By convention,
     *             <code>0</code> indicates normal termination.
     * @exception  InterruptedException  if the current thread is
     *             {@linkplain Thread#interrupt() interrupted} by another
     *             thread while it is waiting, then the wait is ended and
     *             an {@link InterruptedException} is thrown.
     */
    public int waitFor() throws InterruptedException;

    /**
     * Returns the exit value for the subprocess.
     *
     * @return  the exit value of the subprocess represented by this
     *          <code>Process</code> object. by convention, the value
     *          <code>0</code> indicates normal termination.
     * @exception  IllegalThreadStateException  if the subprocess represented
     *             by this <code>Process</code> object has not yet terminated.
     */
    public int exitValue();
}
