/*
 * File: JavaApplicationSchema.java
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

import java.util.List;

/**
 * A {@link JavaApplicationSchema} is a Java specific {@link ApplicationSchema}.
 *
 * @author Brian Oliver
 */
public interface JavaApplicationSchema<A extends JavaApplication, S extends JavaApplicationSchema<A, S>>
    extends ApplicationSchema<A, S>
{
    /**
     * Obtains the {@link PropertiesBuilder} that will be used as a basis for configuring the Java System Properties
     * of the realized {@link JavaApplication}s from an {@link JavaApplicationBuilder}.
     *
     * @return {@link PropertiesBuilder}
     */
    public PropertiesBuilder getSystemPropertiesBuilder();


    /**
     * Obtains the JVM options to be used for starting the {@link JavaApplication}.
     *
     * @return A {@link List} of {@link String}s
     */
    public List<String> getJVMOptions();


    /**
     * Obtains the class path to be used for the {@link JavaApplication}
     *
     * @return {@link String}
     */
    public String getClassPath();


    /**
     * Obtain the application main class name.
     *
     * @return {@link String}
     */
    public String getApplicationClassName();
}
