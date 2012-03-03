/*
 * File: Environment.java
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
package com.oracle.coherence.environment;

import java.util.Map;

import com.oracle.coherence.common.builders.NoArgsBuilder;

/**
 * An {@link Environment} provides access to strongly typed resources and contextual information for one or 
 * more applications, components and/or extensions <strong>within</strong> a Coherence-based process (ie: JVM).
 * <p>
 * In most cases the {@link com.tangosol.net.ConfigurableCacheFactory} implementations are responsible for implementing
 * the {@link Environment}.  Consequently the typical approach to locate and access a configured {@link Environment} 
 * is to simply perform the following;
 * 
 * <code>({@link Environment}){@link com.tangosol.net.CacheFactory#getConfigurableCacheFactory()}</code>
 * 
 * @author Brian Oliver
 */
public interface Environment
{

    /**
     * Attempts to resolve and return a resource registered with the specified interface. Returns
     * <code>null</code> if the resource can't be resolved.
     * 
     * @param <R>   The type of the expected resource
     * 
     * @param clazz The interface {@link Class} of the expected resource.
     * 
     * @return A resource of type <R> or <code>null</code> if one could not resolved.
     */
    public <R> R getResource(Class<R> clazz);


    /**
     * Attempts to resolve and return a uniquely name resource registered with the specified interface. Returns
     * <code>null</code> if the resource can't be resolved.
     * 
     * @param <R>   The type of the expected resource
     * 
     * @param clazz The interface {@link Class} of the expected resource.
     * @param name  The name of the resource (can't be <code>null</code>)
     * 
     * @return A resource of type <R> or <code>null</code> if one could not resolved.
     */
    public <R> R getResource(Class<R> clazz,
                             String name);


    /**
     * Returns a {@link Map} of the resources registered using the specified interface {@link Class}.
     *  
     * @param <R>       The type of the expected resource.
     * @param clazz     The interface {@link Class} of the expected resources.
     * @return A {@link Map} of resources keyed by name.
     */
    public <R> Map<String, R> getResources(Class<R> clazz);


    /**
     * Registers a resource with the {@link Environment} that may be later retrieved using the 
     * {@link #getResource(Class)} method using the specified {@link Class}.
     * <p>
     * If a resource with the specified {@link Class} already exists in the {@link Environment}, the resource is returned.  
     * Should you wish to atomically register a resource in the case that it is not already known, use the 
     * {@link #registerResource(Class, String, NoArgsBuilder)} method.
     * <p>
     * Note: The resource will automatically be given the name of the fully-qualified-class-name of the resource.
     * 
     * @param <R>       The type of the interface to be associated with the resource
     * 
     * @param clazz     The interface {@link Class} of the resource to register
     * @param resource  The resource to register
     * 
     * @return The resource that already exists or was newly registered
     */
    public <R> R registerResource(Class<R> clazz,
                                  Object resource);


    /**
     * Registers a uniquely named resource with the {@link Environment} that may be later retrieved using the 
     * {@link #getResource(Class, String)} method using the specified {@link Class} and name.
     * <p>
     * If a resource with the specified {@link Class} already exists in the {@link Environment}, the resource is returned.  
     * Should you wish to atomically register a resource in the case that it is not already known, use the 
     * {@link #registerResource(Class, String, NoArgsBuilder)} method.
     * <p>
     * Note: Multiple resources of the same {@link Class} may be registered so long as the names for each resource
     * is unique for the type.
     * 
     * @param <R>       The type of the interface to be associated with the resource.
     * 
     * @param clazz     The interface {@link Class} of the resource to register.
     * @param name      The name of the resource (can't be <code>null</code>).
     * @param resource  The resource to register.
     * 
     * @return The resource that already exists or was newly registered
     */
    public <R> R registerResource(Class<R> clazz,
                                  String name,
                                  Object resource);


    /**
     * Conditionally registers a uniquely named resource with the {@link Environment} that may be later retrieved using 
     * the {@link #getResource(Class, String)} method using the specified {@link Class} and name.
     * <p>
     * If a resource with the specified {@link Class} and name already exists in the {@link Environment}, it is returned.  
     * If the resource is unknown, the provided {@link NoArgsBuilder} is used to instantiate and register the resource
     * atomically, after which it is returned
     * <p>
     * Note: Multiple resources of the same {@link Class} may be registered so long as the names for each resource
     * is unique for the type.
     * 
     * @param <R>       The type of the interface to be associated with the resource.
     * 
     * @param clazz     The interface {@link Class} of the resource to register.
     * @param name      The name of the resource (can't be <code>null</code>).
     * @param builder   The {@link NoArgsBuilder} to be used if the resource is unknown
     * 
     * @return The resource that already exists or was newly registered
     */
    public <R> R registerResource(Class<R> clazz,
                                  String name,
                                  NoArgsBuilder<R> builder);


    /**
     * Returns the {@link ClassLoader} that should be used to load any classes used by the 
     * {@link Environment}.
     *  
     * @return The {@link ClassLoader} for the {@link Environment}.
     */
    public ClassLoader getClassLoader();
}
