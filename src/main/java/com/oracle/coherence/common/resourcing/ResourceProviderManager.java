/*
 * File: ResourceProviderManager.java
 * 
 * Copyright (c) 2011. All Rights Reserved. Oracle Corporation.
 * 
 * Oracle is a registered trademark of Oracle Corporation and/or its
 * affiliates.
 * 
 * This software is the confidential and proprietary information of Oracle
 * Corporation. You shall not disclose such confidential and proprietary
 * information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Oracle Corporation.
 * 
 * Oracle Corporation makes no representations or warranties about 
 * the suitability of the software, either express or implied, 
 * including but not limited to the implied warranties of 
 * merchantability, fitness for a particular purpose, or 
 * non-infringement.  Oracle Corporation shall not be liable for 
 * any damages suffered by licensee as a result of using, modifying 
 * or distributing this software or its derivatives.
 * 
 * This notice may not be removed or altered.
 */
package com.oracle.coherence.common.resourcing;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The role of a {@link ResourceProviderManager} instance to so manage a named collection of {@link ResourceProvider}s.
 *
 * @author Brian Oliver
 */
public class ResourceProviderManager
{

    /**
     * The named collection of {@link ResourceProvider}s.
     */
    private ConcurrentHashMap<String, ResourceProvider<?>> resourceProvidersByName;


    /**
     * Standard Constructor.
     */
    public ResourceProviderManager()
    {
        this.resourceProvidersByName = new ConcurrentHashMap<String, ResourceProvider<?>>();
    }


    /**
     * Attempts to register the specified uniquely named (for the class) {@link ResourceProvider}.  If a
     * {@link ResourceProvider} with the specified name (and class) is already registered, the request to (re)register
     * is ignored.
     * 
     * @param <T>                           The type of the {@link ResourceProvider}
     * @param clazz                         The class of the {@link ResourceProvider}
     * @param name                          The unique name (for the class) of the {@link ResourceProvider}
     * @param supervisedResourceProvider    The {@link ResourceProvider}
     */
    public <T> void registerResourceProvider(Class<T> clazz,
                                             String name,
                                             ResourceProvider<T> supervisedResourceProvider)
    {
        resourceProvidersByName.putIfAbsent(name + clazz.getName(), supervisedResourceProvider);
    }


    /**
     * Attempts to return a previously registered {@link ResourceProvider} with the specified name (and class)
     * 
     * @param <T>
     * @param clazz The class of the {@link ResourceProvider}
     * @param name The unique name (for the class) of the {@link ResourceProvider}
     * 
     * @return The registered {@link ResourceProvider} or <code>null</code> if the {@link ResourceProvider} is 
     *         unknown.
     */
    @SuppressWarnings("unchecked")
    public <T> ResourceProvider<T> getResourceProvider(Class<T> clazz,
                                                       String name)
    {
        return (ResourceProvider<T>) resourceProvidersByName.get(name + clazz.getName());
    }
}
