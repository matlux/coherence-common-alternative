/*
 * File: CacheMappingTest.java
 * 
 * Copyright (c) 2010. All Rights Reserved. Oracle Corporation.
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
package com.oracle.coherence.configuration.caching;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * <p>Unit tests for a {@link CacheMapping}.</p>
 *
 * @author Brian Oliver
 */
public class CacheMappingTest
{

    /**
     * Ensure that a new CacheMapping is built with the correct state.
     */
    @Test
    public void testCacheMappingContruction()
    {
        CacheMapping cacheMapping = new CacheMapping("dist-*", "DistributedScheme", null);

        assertTrue(cacheMapping.getCacheName().equals("dist-*"));
        assertTrue(cacheMapping.isForCacheName("dist-me"));
        assertTrue(cacheMapping.isForCacheName("dist-"));
        assertFalse(cacheMapping.isForCacheName("dist"));
        assertTrue(cacheMapping.getSchemeName().equals("DistributedScheme"));
        assertFalse(cacheMapping.hasEnrichment(String.class, "message"));
    }


    /**
     * Ensure that a CacheMapping decoration addition occurs as expected.
     */
    @Test
    public void testCacheMappingDecoration()
    {
        CacheMapping cacheMapping = new CacheMapping("dist-*", "DistributedScheme", null);

        assertFalse(cacheMapping.hasEnrichment(String.class, "welcome"));

        cacheMapping.addEnrichment(String.class, "welcome", "Hello World");

        assertTrue(cacheMapping.hasEnrichment(String.class, "welcome"));
        assertTrue(cacheMapping.getEnrichment(String.class, "welcome").equals("Hello World"));

        cacheMapping.addEnrichment(String.class, "welcome", "Gudday");
        cacheMapping.addEnrichment(String.class, "goodbye", "Cheers");

        assertTrue(cacheMapping.hasEnrichment(String.class, "welcome"));
        assertTrue(cacheMapping.getEnrichment(String.class, "welcome").equals("Gudday"));

        assertTrue(cacheMapping.hasEnrichment(String.class, "welcome"));
        assertTrue(cacheMapping.getEnrichment(String.class, "goodbye").equals("Cheers"));

        CacheMapping otherCacheMapping = new CacheMapping("repl-*", "ReplicatedScheme", null);
        otherCacheMapping.addEnrichment(String.class, "welcome", "Bonjour");

        assertTrue(otherCacheMapping.hasEnrichment(String.class, "welcome"));
        assertTrue(otherCacheMapping.getEnrichment(String.class, "welcome").equals("Bonjour"));

        cacheMapping.addEnrichmentsFrom(otherCacheMapping);

        assertTrue(cacheMapping.hasEnrichment(String.class, "welcome"));
        assertTrue(cacheMapping.getEnrichment(String.class, "welcome").equals("Bonjour"));

        assertTrue(cacheMapping.hasEnrichment(String.class, "goodbye"));
        assertTrue(cacheMapping.getEnrichment(String.class, "goodbye").equals("Cheers"));

    }
}
