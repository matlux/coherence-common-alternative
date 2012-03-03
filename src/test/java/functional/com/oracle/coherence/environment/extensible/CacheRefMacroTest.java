/*
 * File: CacheRefMacroTest.java
 *
 * Copyright (c) 2012. All Rights Reserved. Oracle Corporation.
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

import com.oracle.coherence.environment.extensible.ExtensibleEnvironment;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

import com.tangosol.net.cache.ContinuousQueryCache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * {@link CacheRefMacroTest} tests the resolution of {cache-ref}s when using {@link ExtensibleEnvironment}s. 
 * 
 * @author Jonathan Knight
 */
public class CacheRefMacroTest
{
    private NamedCache cache;


    /**
     * Setup for each test.
     */
    @Before
    public void setup()
    {
        System.setProperty("tangosol.coherence.cacheconfig", "test-cache-ref-config.xml");
        System.setProperty("tangosol.coherence.ttl", "0");
        System.setProperty("tangosol.coherence.localhost", "127.0.0.1");

        cache = CacheFactory.getCache("dist-test");
        cache.put("Key-1", "Value-1");
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
     * Ensure that the correct {@link NamedCache} is resolved by the {@link CacheFactory}.
     *
     * @throws Exception
     */
    @Test
    public void testWrapperNamedCacheResolution() throws Exception
    {
        NamedCache cqc = CacheFactory.getCache("cqc-test");

        assertEquals(cqc.getCacheName(), "cqc-test");
    }


    /**
     * Ensure that the correct {@link NamedCache} implementation is resolved by the {@link CacheFactory}
     *
     * @throws Exception
     */
    @Test
    public void testNamedCacheImplementationResolution() throws Exception
    {
        WrapperContinuousQueryCache wrapper = (WrapperContinuousQueryCache) CacheFactory.getCache("cqc-test");
        ContinuousQueryCache        cqc     = (ContinuousQueryCache) wrapper.getMap();

        assertTrue(cqc.getCache() == cache);
    }
}
