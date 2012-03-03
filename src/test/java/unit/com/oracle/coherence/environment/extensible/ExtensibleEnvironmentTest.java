/*
 * File: ExtensibleEnvironmentTest.java
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

package com.oracle.coherence.environment.extensible;

import com.oracle.coherence.common.events.dispatching.EventDispatcher;
import com.oracle.coherence.common.events.lifecycle.LifecycleEvent;
import com.oracle.coherence.common.events.processing.EventProcessor;

import com.oracle.coherence.common.network.Constants;

import com.oracle.coherence.environment.Environment;

import com.oracle.coherence.environment.extensible.namespaces.EnvironmentNamespaceContentHandler;
import com.oracle.coherence.environment.extensible.namespaces.InstanceNamespaceContentHandler;
import com.oracle.coherence.environment.extensible.namespaces.ValueNamespaceContentHandler;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import com.tangosol.run.xml.XmlHelper;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import static org.mockito.Matchers.isA;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.awt.Point;

/**
 * <p>The unit tests for the {@link ExtensibleEnvironment}. </p>
 *
 * @author Brian Oliver
 */
public class ExtensibleEnvironmentTest
{
    /**
     * <p>Test that an empty configuration file produces an {@link ExtensibleEnvironment}
     * with a {@link EventDispatcher}.</p>
     */
    @Test
    public void testExtensibleEnvironmentInitialization()
    {
        String                xml = "<cache-config>" + "</cache-config>";

        ExtensibleEnvironment env = new ExtensibleEnvironment(XmlHelper.loadXml(xml));

        assertTrue(env.getResource(EventDispatcher.class) != null);
        assertTrue(env.getResource(EventDispatcher.class) instanceof EventDispatcher);

        CacheFactory.shutdown();
    }


    /**
     * <p>Test that resources are registered and returned as expected from an {@link ExtensibleEnvironment}.</p>
     */
    @Test
    public void testResourceRegistration()
    {
        String xml =
            "<cache-config xmlns:env=\"class://" + EnvironmentNamespaceContentHandler.class.getName() + "\" "
            + "              xmlns:instance=\"class://" + InstanceNamespaceContentHandler.class.getName() + "\" "
            + "              xmlns:value=\"class://" + ValueNamespaceContentHandler.class.getName() + "\" " + ">"
            + "<env:resource id=\"java.awt.Point\">"
            + "<instance:class classname=\"java.awt.Point\"><value:integer>100</value:integer><value:integer>200</value:integer></instance:class>"
            + "</env:resource>" + "</cache-config>";

        ExtensibleEnvironment env = new ExtensibleEnvironment(XmlHelper.loadXml(xml));

        assertTrue(env.getResource(Point.class) != null);
        assertTrue(env.getResource(Point.class) instanceof Point);
        assertTrue(env.getResource(Point.class).equals(new Point(100, 200)));

        CacheFactory.shutdown();
    }


    /**
     * <p>Test that {@link NamedCache} lifecycle events are raised as expected from an {@link ExtensibleEnvironment}.</p>
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void testNamedCacheLifecycleEvents()
    {
        // force development mode
        System.setProperty("tangosol.coherence.ttl", "0");
        System.setProperty("tangosol.coherence.mode", "dev");
        System.setProperty("tangosol.coherence.localhost", Constants.LOCAL_HOST);

        Environment environment = (Environment) CacheFactory.getConfigurableCacheFactory();

        // TODO: we should create specialized mocks for each of the life-cycle events we'll see
        // 1. LifecycleStart<DistributedCacheService>
        // 2. StorageRealized<NamedCache>
        // 3. StorageReleased<NamedCache>
        // 4. LifecycleStop<DistributedCacheService>
        EventProcessor eventProcessor = mock(EventProcessor.class);

        environment.getResource(EventDispatcher.class).registerEventProcessor(LifecycleEventFilter.INSTANCE,
                                                                              eventProcessor);

        NamedCache myCache = CacheFactory.getCache("my-cache");

        CacheFactory.destroyCache(myCache);

        CacheFactory.shutdown();

        // verify the number of events we're expecting to see.
        verify(eventProcessor, times(4)).process(isA(EventDispatcher.class), isA(LifecycleEvent.class));
    }
}
