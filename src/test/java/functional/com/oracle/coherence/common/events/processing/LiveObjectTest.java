/*
 * File: LiveObjectTest.java
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

package com.oracle.coherence.common.events.processing;

import com.oracle.coherence.common.events.EntryEvent;
import com.oracle.coherence.common.events.EntryInsertedEvent;
import com.oracle.coherence.common.events.EntryRemovedEvent;
import com.oracle.coherence.common.events.EntryUpdatedEvent;
import com.oracle.coherence.common.events.Event;
import com.oracle.coherence.common.events.dispatching.EventDispatcher;
import com.oracle.coherence.common.events.processing.annotations.EventProcessorFor;
import com.oracle.coherence.common.events.processing.annotations.LiveObject;

import com.oracle.coherence.environment.Environment;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;

import java.util.Map.Entry;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Functional Test for {@link LiveObject}s.
 *
 * @author Brian Oliver
 */
public class LiveObjectTest
{
    private static final String INSERTS = "INSERTS";
    private static final String UPDATES = "UPDATES";
    private static final String REMOVES = "REMOVES";

    private NamedCache          m_cacheLiveObjects;
    private Environment         m_environment;


    /**
     * Setup for each test.
     */
    @Before
    public void setup()
    {
        System.setProperty("tangosol.coherence.cacheconfig", "coherence-common-cache-config.xml");
        System.setProperty("tangosol.coherence.ttl", "0");
        System.setProperty("tangosol.coherence.localhost", "127.0.0.1");

        m_cacheLiveObjects = CacheFactory.getCache("coherence.live.objects.distributed");

        m_environment      = (Environment) CacheFactory.getConfigurableCacheFactory();

        m_environment.registerResource(AtomicLong.class, INSERTS, new AtomicLong());
        m_environment.registerResource(AtomicLong.class, REMOVES, new AtomicLong());
        m_environment.registerResource(AtomicLong.class, UPDATES, new AtomicLong());
    }


    /**
     * Cleanup after each test.
     */
    @After
    public void cleanup()
    {
        CacheFactory.shutdown();
    }


    /**
     * Ensure LiveObjects are inserted, updated and removed as expected.
     */
    @Test
    public void testLiveObjectInsertUpdateemove()
    {
        assertEquals(0, m_cacheLiveObjects.size());
        assertEquals(0, m_environment.getResource(AtomicLong.class, INSERTS).get());
        assertEquals(0, m_environment.getResource(AtomicLong.class, UPDATES).get());
        assertEquals(0, m_environment.getResource(AtomicLong.class, REMOVES).get());

        m_cacheLiveObjects.put("alo", new AnnotatedLiveObject());

        assertEquals(1, m_cacheLiveObjects.size());
        assertEquals(1, m_environment.getResource(AtomicLong.class, INSERTS).get());
        assertEquals(0, m_environment.getResource(AtomicLong.class, UPDATES).get());
        assertEquals(0, m_environment.getResource(AtomicLong.class, REMOVES).get());

        m_cacheLiveObjects.put("alo", new AnnotatedLiveObject());

        assertEquals(1, m_cacheLiveObjects.size());
        assertEquals(1, m_environment.getResource(AtomicLong.class, INSERTS).get());
        assertEquals(1, m_environment.getResource(AtomicLong.class, UPDATES).get());
        assertEquals(0, m_environment.getResource(AtomicLong.class, REMOVES).get());

        m_cacheLiveObjects.remove("alo");

        assertEquals(0, m_cacheLiveObjects.size());
        assertEquals(1, m_environment.getResource(AtomicLong.class, INSERTS).get());
        assertEquals(1, m_environment.getResource(AtomicLong.class, UPDATES).get());
        assertEquals(1, m_environment.getResource(AtomicLong.class, REMOVES).get());
    }


    /**
     * An {@link AnnotatedLiveObject}.
     */
    @LiveObject
    @SuppressWarnings("serial")
    public static class AnnotatedLiveObject implements Serializable
    {
        /**
         * Constructs an {@link AnnotatedLiveObject}.
         */
        public AnnotatedLiveObject()
        {
            // required for serialization
        }


        /**
         * Handle when the {@link AnnotatedLiveObject} is inserted.
         *
         * @param dispatcher  the {@link EventDispatcher}
         * @param event       the {@link Event}
         */
        @EventProcessorFor(events = {EntryInsertedEvent.class})
        public void onInsert(EventDispatcher dispatcher,
                             EntryEvent<Entry<String, AnnotatedLiveObject>> event)
        {
            dispatcher.getEnvironment().getResource(AtomicLong.class, LiveObjectTest.INSERTS).incrementAndGet();
        }


        /**
         * Handle when the {@link AnnotatedLiveObject} is updated.
         *
         * @param dispatcher  the {@link EventDispatcher}
         * @param event       the {@link Event}
         */
        @EventProcessorFor(events = {EntryUpdatedEvent.class})
        public void onUpdate(EventDispatcher dispatcher,
                             EntryEvent<Entry<String, AnnotatedLiveObject>> event)
        {
            dispatcher.getEnvironment().getResource(AtomicLong.class, LiveObjectTest.UPDATES).incrementAndGet();
        }


        /**
         * Handle when the {@link AnnotatedLiveObject} is removed.
         *
         * @param dispatcher  the {@link EventDispatcher}
         * @param event       the {@link Event}
         */
        @EventProcessorFor(events = {EntryRemovedEvent.class})
        public void onRemove(EventDispatcher dispatcher,
                             EntryEvent<Entry<String, AnnotatedLiveObject>> event)
        {
            dispatcher.getEnvironment().getResource(AtomicLong.class, LiveObjectTest.REMOVES).incrementAndGet();
        }
    }
}
