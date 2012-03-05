/*
 * File: ApplicationSchema.java
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

import java.io.File;
import java.util.List;

/**
 * {@link ApplicationSchema} defines the schema encapsulating configuration and operational settings that an
 * {@link ApplicationBuilder} will use to correctly realize an {@link Application}.
 *
 * @param <A>  The type of the {@link Application} that will be realized by an {@link ApplicationBuilder} using
 *             this {@link ApplicationSchema}.
 *
 * @param <S>  The type of {@link ApplicationSchema} that will be returned from setter calls
 *             on this interface.  This is to permit extensible type-safe fluent-style definitions of
 *             setter methods in sub-interfaces.
 *
 * @author Brian Oliver
 */
public interface ApplicationSchema<A extends Application, S extends ApplicationSchema<A, S>>
{
    /**
     * @return the name of the executable to run
     */
    public String getExecutableName();

    /**
     * @return the working directory to run the process in
     */
    public File getWorkingDirectory();

    /**
     * Obtains the {@link PropertiesBuilder} that will be used to determine operating system environment
     * variables to be provided to the {@link Application}.
     *
     * @return {@link PropertiesBuilder}
     */
    public PropertiesBuilder getEnvironmentVariablesBuilder();

    /**
     * @return true if the current processes environment variables
     * should be used as a base for the new process' environment variables
     */
    public boolean shouldCloneEnvironment();

    /**
     * Obtains the arguments for the {@link Application}.
     *
     * @return A {@link List} of {@link String}s
     */
    public List<String> getArguments();
}
