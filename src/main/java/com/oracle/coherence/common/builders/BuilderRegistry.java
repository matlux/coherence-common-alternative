/*
 * File: BuilderRegistry.java
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
package com.oracle.coherence.common.builders;

import java.util.LinkedHashMap;


/**
 * A {@link BuilderRegistry} is responsible for managing the known set of {@link Builder}s
 * in an {@link com.oracle.coherence.environment.Environment}.
 *
 * @author Brian Oliver
 */
public class BuilderRegistry
{

    /**
     * The {@link Builder}s currently known by the {@link BuilderRegistry}.
     * <p>
     * NOTE: This is a {@link LinkedHashMap} as order of registration of {@link Scheme}s is often important
     * to Coherence.
     */
    private LinkedHashMap<String, Builder<?>> builders;


    /**
     * <p>Standard Constructor.</p>
     */
    public BuilderRegistry()
    {
        this.builders = new LinkedHashMap<String, Builder<?>>();
    }


    /**
     * Registers the specified {@link Builder} with the specified identity.
     * <p>
     * NOTE: If a {@link Builder} with the same identity is already registered, a call
     * to this method will silently override the existing registration.
     * 
     * @param id        The identity for the {@link Builder}.  This identity may be used later to retrieve the {@link Builder}.
     * @param builder   The {@link Builder} to register.
     */
    public void registerBuilder(String id,
                                Builder<?> builder)
    {
        builders.put(id, builder);
    }


    /**
     * Requests a reference to the {@link Builder} that was previously registered with
     * the specified identity.
     * <p>
     * NOTE: Returns <code>null</code> if the {@link Builder} requested has not be registered/is
     * unknown.
     * 
     * @param id The identity of the {@link Builder} being requested.
     * @return A {@link Builder} or <code>null</code> if not found.
     */
    public Builder<?> getBuilder(String id)
    {
        return builders.get(id);
    }
}
