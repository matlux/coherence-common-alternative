/*
 * File: WrapperContinuousQueryCache.java
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

import com.tangosol.net.NamedCache;

import com.tangosol.net.cache.ContinuousQueryCache;
import com.tangosol.net.cache.WrapperNamedCache;

import com.tangosol.util.filter.AlwaysFilter;

/**
 * A {@link WrapperContinuousQueryCache} is an implementation of a {@link WrapperNamedCache}
 * that internally uses a {@link ContinuousQueryCache} for locally maintaining a cache of another cache.
 * 
 * @author Jonathan Knight
 */
public class WrapperContinuousQueryCache extends WrapperNamedCache
{
    /**
     * Constructs a {@link WrapperContinuousQueryCache}
     *
     * @param wrappedCache  the {@link NamedCache} to wrap
     * @param cacheName     the name of the wrapped cache
     */
    public WrapperContinuousQueryCache(NamedCache wrappedCache,
                                       String cacheName)
    {
        super(new ContinuousQueryCache(wrappedCache, AlwaysFilter.INSTANCE), cacheName);
    }
}
