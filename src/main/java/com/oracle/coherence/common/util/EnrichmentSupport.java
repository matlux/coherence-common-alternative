/*
 * File: EnrichmentSupport.java
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

import java.util.Iterator;

import com.oracle.coherence.configuration.caching.CacheMapping;

/**
 * The {@link EnrichmentSupport} interface defines methods for objects that support enrichment (addition of 
 * strongly typed and named attribute values at runtime).
 *
 * @author Brian Oliver
 */
public interface EnrichmentSupport
{

    /**
     * Returns if the implementing object has the specified enrichment.
     * 
     * @param <T>           The type of the enrichment
     * @param <N>           The type of the enrichment key
     * @param type          The type of the enrichment
     * @param enrichmentKey The key of the enrichment
     * 
     * @return <code>true</code> if the named enrichment exists on the implementing object, 
     *         <code>false</code> otherwise.
     */
    public <T, N> boolean hasEnrichment(Class<T> type,
                                        N enrichmentKey);


    /**
     * Returns the enrichment associated with the specified name.
     *
     * @param <T>           The type of the enrichment
     * @param <N>           The type of the enrichment key
     * @param type          The type of the enrichment
     * @param enrichmentKey The name of the enrichment
     * 
     * @return The enrichment or <code>null</code> if the enrichment is not defined.
     */
    public <T, N> T getEnrichment(Class<T> type,
                                  N enrichmentKey);


    /**
     * Returns an {@link Iterable} over the enrichment keys for the specified type.
     * 
     * @param type  The type of the enrichments required
     * 
     * @return An {@link Iterable} over the enrichment keys of the specified type.
     */
    public Iterable<?> getEnrichmentKeys(Class<?> type);


    /**
     * Returns an {@link Iterable} over all of the enrichments defined for the specified type.
     * 
     * @param <T>   The type of the enrichment
     * @param type  The type of the enrichments required
     * 
     * @return An {@link Iterable} over the decoratations of the specified type.
     */
    public <T> Iterable<T> getEnrichments(Class<T> type);


    /**
     * Returns an {@link Iterator} over the types of enrichments that have been registered.
     * 
     * @return An {@link Iterator}
     */
    public Iterable<Class<?>> getEnrichmentTypes();


    /**
     * Adds and/or overrides an existing enrichment with the specified type and name.
     * 
     * @param <T>           The type of the enrichment
     * @param <N>           The type of the enrichment key
     * @param type          The type of the enrichment
     * @param enrichmentKey The name of the enrichment (should be unique for the {@link CacheMapping}).
     * @param enrichment The enrichment to add
     */
    public <T, N> void addEnrichment(Class<T> type,
                                     N enrichmentKey,
                                     T enrichment);


    /**
     * Adds all of the enrichments from the provided {@link EnrichmentSupport} instance to the this 
     * {@link EnrichmentSupport} instance.
     * 
     * @param enrichmentSupport The {@link EnrichmentSupport} from which to retrieve enrichments to add.
     */
    public void addEnrichmentsFrom(EnrichmentSupport enrichmentSupport);
}
