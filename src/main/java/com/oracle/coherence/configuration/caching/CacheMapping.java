/*
 * File: CacheMapping.java
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

import com.oracle.coherence.common.util.AbstractEnrichmentSupport;
import com.oracle.coherence.configuration.parameters.ParameterProvider;
import com.oracle.coherence.configuration.parameters.SystemPropertyParameterProvider;

/**
 * <p>A {@link CacheMapping} represents the configuration information for an individual mapping from a named cache
 * to a cache scheme, including any specific {@link com.oracle.coherence.configuration.parameters.Parameter}s (represented as a {@link ParameterProvider})
 * for the said mapping.</p>
 *
 * @author Brian Oliver
 */
public class CacheMapping extends AbstractEnrichmentSupport
{

    /**
     * <p>The name of the cache.</p>
     */
    private String cacheName;

    /**
     * <p>The name of the  scheme to which the cache is mapped.</p>
     */
    private String schemeName;

    /**
     * <p>The {@link ParameterProvider} containing the {@link com.oracle.coherence.configuration.parameters.Parameter}s defined by the {@link CacheMapping}.</p>
     */
    private ParameterProvider parameterProvider;


    /** 
     * <p>Standard Constructor.</p>
     * 
     * @param cacheName The name of the cache
     * @param schemeName The scheme for the cache
     * @param parameterProvider The {@link ParameterProvider} containing the {@link com.oracle.coherence.configuration.parameters.Parameter}s defined for the mapping.
     */
    public CacheMapping(String cacheName,
                        String schemeName,
                        ParameterProvider parameterProvider)
    {
        super();
        
        this.cacheName = cacheName.trim();
        this.schemeName = schemeName.trim();
        this.parameterProvider = parameterProvider == null
                ? SystemPropertyParameterProvider.INSTANCE : parameterProvider;
    }


    /**
     * <p>Returns the name of the cache for the {@link CacheMapping}.</p>
     * 
     * @return A String representing the cache name for the {@link CacheMapping}
     */
    public String getCacheName()
    {
        return cacheName;
    }


    /**
     * <p>Determines if the {@link CacheMapping} is for (matches) the specified cache name.</p>
     * 
     * @param cacheName The cacheName to check for a match
     * @return <code>true</code> if the {@link CacheMapping} is for the specified cache name, 
     *         <code>false</code> otherwise.
     */
    public boolean isForCacheName(String cacheName)
    {
        if (getCacheName().equals("*"))
        {
            return true;
        }
        else if (getCacheName().contains("*"))
        {
            String pattern = getCacheName().substring(0, getCacheName().indexOf("*"));
            return cacheName.startsWith(pattern);
        }
        else
        {
            return false;
        }
    }


    /**
     * <p>Returns the scheme name for the {@link CacheMapping}.</p>
     * 
     * @return A String representing the scheme name for the {@link CacheMapping}
     */
    public String getSchemeName()
    {
        return schemeName;
    }


    /**
     * <p>Returns the {@link ParameterProvider} that contains the {@link com.oracle.coherence.configuration.parameters.Parameter}s 
     * defined for the {@link CacheMapping}.</p>
     * 
     * @return A {@link ParameterProvider}
     */
    public ParameterProvider getParameterProvider()
    {
        return parameterProvider;
    }
}