/*
 * File: CacheMappingRegistry.java
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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * <p>Unit tests for a {@link CacheMappingRegistry}.</p>
 *
 * @author Brian Oliver
 */
public class CacheMappingRegistryTest
{

    /**
     * Ensure that a new {@link CacheMappingRegistry} is built with the correct state
     */
    @Test
    public void testCacheMappingRegistryConstruction()
    {
        CacheMappingRegistry registry = new CacheMappingRegistry();

        assertTrue(registry.findCacheMapping("unknown") == null);
    }


    /**
     * Ensure {@link CacheMappingRegistry} find mapping returns the correct mappings
     */
    @Test
    public void testCacheMappingRegistryFind()
    {
        CacheMappingRegistry registry = new CacheMappingRegistry();
        
        assertTrue(registry.findCacheMapping("example") == null);
        
        CacheMapping distCacheMapping = new CacheMapping("dist-*", "DistributedScheme", null);
        registry.addCacheMapping(distCacheMapping);
        
        assertTrue(registry.findCacheMapping("dist-*") == distCacheMapping);
        assertTrue(registry.findCacheMapping("dist-me") == distCacheMapping);
        assertTrue(registry.findCacheMapping("dist-") == distCacheMapping);
        assertTrue(registry.findCacheMapping("unknown") == null);

        CacheMapping dCacheMapping = new CacheMapping("d*", "DistributedScheme", null);
        registry.addCacheMapping(dCacheMapping);

        assertTrue(registry.findCacheMapping("dist-*") == distCacheMapping);
        assertTrue(registry.findCacheMapping("dist-me") == distCacheMapping);
        assertTrue(registry.findCacheMapping("dist-") == distCacheMapping);
        assertTrue(registry.findCacheMapping("d*") == dCacheMapping);
        assertTrue(registry.findCacheMapping("d") == dCacheMapping);
        assertTrue(registry.findCacheMapping("dist") == dCacheMapping);
        assertTrue(registry.findCacheMapping("unknown") == null);

        CacheMapping genericCacheMapping = new CacheMapping("*", "DistributedScheme", null);
        registry.addCacheMapping(genericCacheMapping);
        
        assertTrue(registry.findCacheMapping("dist-*") == distCacheMapping);
        assertTrue(registry.findCacheMapping("dist-me") == distCacheMapping);
        assertTrue(registry.findCacheMapping("dist-") == distCacheMapping);
        assertTrue(registry.findCacheMapping("d*") == dCacheMapping);
        assertTrue(registry.findCacheMapping("d") == dCacheMapping);
        assertTrue(registry.findCacheMapping("dist") == dCacheMapping);
        assertTrue(registry.findCacheMapping("unknown") == genericCacheMapping);
    }
}
