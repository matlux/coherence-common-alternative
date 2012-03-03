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

package com.oracle.coherence.common.runtime;

import com.oracle.coherence.common.events.lifecycle.LifecycleEvent;
import com.oracle.coherence.common.events.processing.EventProcessor;

import java.io.IOException;

/**
 * An {@link ApplicationBuilder} is responsible the creation of {@link Application}s based on {@link ApplicationSchema}s.
 *
 * @param <A> The type of the {@link Application}s the {@link ApplicationBuilder} will realize.
 * @param <S> The type of the {@link ApplicationSchema} for the {@link Application}s.
 * @param <B> The type of the {@link ApplicationBuilder} (to support fluent-style setters with sub-classes of
 *            {@link ApplicationBuilder}).
 *
 * @author Brian Oliver
 */
public interface ApplicationBuilder<A extends Application, S extends ApplicationSchema<A, S>,
                                    B extends ApplicationBuilder<A, S, B>>
{
    /**
     * Realizes an instance of an {@link Application}.
     *
     * @param schema    The {@link ApplicationSchema} to use for realizing the {@link Application}.
     * @param name      The name of the application.
     * @param console   The {@link ApplicationConsole} that will be used for I/O by the
     *                  realized {@link Application}. This may be <code>null</code> if not required.
     *
     * @return An {@link Application} representing the application realized by the {@link ApplicationBuilder}.
     *
     * @throws IOException thrown if a problem occurs while starting the application.
     */
    public A realize(S schema,
                     String name,
                     ApplicationConsole console) throws IOException;


    /**
     * Realizes an instance of an {@link Application} (using a {@link SystemApplicationConsole}).
     *
     * @param schema    The {@link ApplicationSchema} to use for realizing the {@link Application}.
     * @param name      The name of the application.
     *
     * @return An {@link Application} representing the application realized by the {@link ApplicationBuilder}.
     *
     * @throws IOException thrown if a problem occurs while starting the application.
     */
    public A realize(S schema,
                     String name) throws IOException;


    /**
     * Realizes an instance of an {@link Application} (without a name and using a {@link NullApplicationConsole}).
     *
     * @param schema    The {@link ApplicationSchema} to use for realizing the {@link Application}.
     *
     * @return An {@link Application} representing the application realized by the {@link ApplicationBuilder}.
     *
     * @throws IOException thrown if a problem occurs while starting the application.
     */
    public A realize(S schema) throws IOException;


    /**
     * Adds a {@link LifecycleEvent} {@link EventProcessor} to the {@link ApplicationBuilder} allowing
     * developers to intercept and react to {@link LifecycleEvent}s during the process of building, starting and
     * stopping {@link Application}s.
     *
     * @param processor The {@link LifecycleEvent} {@link EventProcessor}.
     *
     * @return The {@link ApplicationBuilder}.
     */
    public B addApplicationLifecycleProcessor(EventProcessor<LifecycleEvent<A>> processor);
}
