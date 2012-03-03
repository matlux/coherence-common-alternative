/*
 * File: ApplicationBuilder.java
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
package com.oracle.coherence.common.applications;

import java.io.IOException;

import com.oracle.coherence.common.resourcing.ResourcePreparer;

/**
 * An {@link ApplicationBuilder} is responsible the creation of one or more {@link Application}s.
 * <p>
 * NOTE: This class is now deprecated.  As a replacement please use the com.oracle.coherence.common.runtime package
 * for controlling application processes and coherence servers.
 *
 * @param <A> The type of {@link Application} that the {@link ApplicationBuilder} will create.
 * @param <T> The type of the {@link ApplicationBuilder} from which default configuration may be retrieved.
 *
 * @author Brian Oliver
 */
@Deprecated
public interface ApplicationBuilder<A extends Application, T extends ApplicationBuilder<A, ?>>
{

    /**
     * Returns the {@link PropertiesBuilder} that will be used to configure the operating system environment
     * variables for the realized {@link Application}.
     * 
     * @return {@link PropertiesBuilder}
     */
    public PropertiesBuilder getEnvironmentVariablesBuilder();


    /**
     * Sets the {@link ResourcePreparer} to use with the built {@link Application}s prior to returning 
     * from realize.
     * 
     * @param preparer The {@link ResourcePreparer}
     * 
     * @return The resulting {@link ApplicationBuilder}.
     */
    public T setApplicationPreparer(ResourcePreparer<A> preparer);


    /**
     * Determines the current {@link ResourcePreparer} for {@link Application}s built by this {@link ApplicationBuilder}.
     * 
     * @return A {@link ResourcePreparer}
     */
    public ResourcePreparer<A> getApplicationPreparer();


    /**
     * Realizes an instance of an {@link Application}, optionally using the information provided by 
     * parent {@link ApplicationBuilder}.
     * 
     * @param defaultBuilder    An {@link ApplicationBuilder} that should be used to determine default environment 
     *                          variable declarations.  This may be <code>null</code> if not required.
     *                      
     * @param name              The name of the application. 
     *                      
     * @param console           The {@link ApplicationConsole} that will be used for I/O by the 
     *                          realized {@link Application}. This may be <code>null</code> if not required.                      
     * 
     * @return An {@link Application} representing the application realized by the {@link ApplicationBuilder}.
     * 
     * @throws IOException Thrown if a problem occurs while realizing the application
     */
    public A realize(T defaultBuilder,
                     String name,
                     ApplicationConsole console) throws IOException;
}
