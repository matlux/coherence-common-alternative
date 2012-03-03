/*
 * File: ApplicationGroupBuilder.java
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

import java.io.IOException;

/**
 * An {@link ApplicationGroupBuilder} is a builder for {@link ApplicationGroup}s.  That is, collections of related
 * {@link Application}s.
 *
 * @param <A>  The type of {@link Application}s that will be built by the {@link ApplicationGroupBuilder}.
 *
 * @author Brian Oliver
 */
public interface ApplicationGroupBuilder<A extends Application, S extends ApplicationSchema<A, S>,
                                         B extends ApplicationBuilder<A, S, B>, G extends ApplicationGroup<A>>
{
    /**
     * Adds a {@link ApplicationBuilder} to the {@link ApplicationGroupBuilder} that will be used to realize
     * a type of {@link Application} when the {@link ApplicationGroup} is realized with {@link #realize(ApplicationConsole)}.
     *
     * @param builder             The {@link ApplicationBuilder} for the {@link Application}s.
     * @param schema              The {@link ApplicationSchema} from which to realize/configure the {@link Application}s.
     * @param sPrefix             The {@link Application} name prefix for each of the realized {@link Application}.
     * @param cRequiredInstances  The number of instances of the {@link Application} that should be realized for the
     *                            {@link ApplicationGroup} when {@link #realize(ApplicationConsole)} is called.
     */
    public void addBuilder(B builder,
                           S schema,
                           String sPrefix,
                           int cRequiredInstances);


    /**
     * Realizes an instance of an {@link ApplicationGroup}.
     *
     * @param console           The {@link ApplicationConsole} that will be used for I/O by the
     *                          {@link Application}s realized in the {@link ApplicationGroup}.
     *                          This may be <code>null</code> if not required.
     *
     * @return An {@link ApplicationGroup} representing the collection of realized {@link Application}s.
     *
     * @throws IOException Thrown if a problem occurs while realizing the application
     */
    public G realize(ApplicationConsole console) throws IOException;


    /**
     * Realizes an instance of an {@link ApplicationGroup} (without a console).
     *
     * @return An {@link ApplicationGroup} representing the collection of realized {@link Application}s.
     *
     * @throws IOException Thrown if a problem occurs while realizing the application
     */
    public G realize() throws IOException;
}
