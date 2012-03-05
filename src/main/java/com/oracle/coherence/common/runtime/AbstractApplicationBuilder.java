/*
 * File: AbstractApplicationBuilder.java
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

import com.tangosol.util.UUID;

import java.io.IOException;

import java.util.ArrayList;

/**
 * An {@link AbstractApplicationBuilder} is a base implementation of an {@link ApplicationBuilder}.
 *
 * @author Brian Oliver
 */
public abstract class AbstractApplicationBuilder<A extends Application, S extends ApplicationSchema<A, S>,
                                                 B extends ApplicationBuilder<A, S, B>>
    implements ApplicationBuilder<A, S, B>
{
    /**
     * The collection of {@link LifecycleEvent} {@link EventProcessor}s to apply to {@link Application} events.
     */
    protected ArrayList<EventProcessor<LifecycleEvent<A>>> m_lifecycleEventProcessors;


    /**
     * Constructs an {@link AbstractApplicationBuilder} with default {@link ApplicationSchema}.
     */
    public AbstractApplicationBuilder()
    {
        m_lifecycleEventProcessors = new ArrayList<EventProcessor<LifecycleEvent<A>>>();
    }


    /**
     * {@inheritDoc}
     */
    public A realize(S schema) throws IOException
    {
        return realize(schema, new UUID().toString());
    }


    /**
     * {@inheritDoc}
     */
    public A realize(S schema,
                     String name) throws IOException
    {
        return realize(schema, name, new NullApplicationConsole());
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public B addApplicationLifecycleProcessor(EventProcessor<LifecycleEvent<A>> processor)
    {
        m_lifecycleEventProcessors.add(processor);

        return (B) this;
    }

    /**
     * Send the specified {@link LifecycleEvent} to all of the registered {@link EventProcessor}s.
     *
     * @param event - the {@link LifecycleEvent} to send
     */
    protected void sendEvent(LifecycleEvent<A> event) {
        for (EventProcessor<LifecycleEvent<A>> processor : m_lifecycleEventProcessors)
        {
            processor.process(null, event);
        }
    }
}
