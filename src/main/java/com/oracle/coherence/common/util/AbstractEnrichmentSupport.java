/*
 * File: AbstractEnrichmentSupport.java
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
package com.oracle.coherence.common.util;

import java.util.Collections;
import java.util.HashMap;

/**
 * An {@link AbstractEnrichmentSupport} provides a base implementation for {@link EnrichmentSupport}.
 *
 * @author Brian Oliver
 */
public abstract class AbstractEnrichmentSupport implements EnrichmentSupport
{

    /**
     * The enrichments by type and name being tracked.
     */
    private HashMap<Class<?>, HashMap<?, ?>> enrichmentsByType;


    /**
     * Standard Constructor.
     */
    public AbstractEnrichmentSupport()
    {
        this.enrichmentsByType = new HashMap<Class<?>, HashMap<?, ?>>();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, N> void addEnrichment(java.lang.Class<T> type,
                                     N enrichmentKey,
                                     T enrichment)
    {
        HashMap<N, T> namedEnrichments = (HashMap<N, T>) enrichmentsByType.get(type);
        if (namedEnrichments == null)
        {
            namedEnrichments = new HashMap<N, T>();
            enrichmentsByType.put(type, namedEnrichments);
        }

        namedEnrichments.put(enrichmentKey, enrichment);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void addEnrichmentsFrom(EnrichmentSupport enrichmentSupport)
    {
        for (Class<?> type : enrichmentSupport.getEnrichmentTypes())
        {
            for (Object enrichmentKey : enrichmentSupport.getEnrichmentKeys(type))
            {
                addEnrichment((Class<Object>) type, (Object) enrichmentKey,
                    (Object) enrichmentSupport.getEnrichment(type, enrichmentKey));
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, N> T getEnrichment(java.lang.Class<T> type,
                                  N enrichmentKey)
    {
        return (T) (enrichmentsByType.containsKey(type) ? enrichmentsByType.get(type).get(enrichmentKey) : null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<?> getEnrichmentKeys(Class<?> type)
    {
        return (enrichmentsByType.containsKey(type) ? enrichmentsByType.get(type).keySet() : Collections.EMPTY_LIST);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Iterable<T> getEnrichments(Class<T> type)
    {
        return (enrichmentsByType.containsKey(type) ? enrichmentsByType.get(type).values() : Collections.EMPTY_LIST);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Class<?>> getEnrichmentTypes()
    {
        return enrichmentsByType.keySet();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public <T, N> boolean hasEnrichment(java.lang.Class<T> type,
                                        N enrichmentKey)
    {
        return getEnrichment(type, enrichmentKey) != null;
    }
}
