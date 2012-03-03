/*
 * File: AbstractResourceProvider.java
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
package com.oracle.coherence.common.resourcing;

/**
 * An {@link AbstractResourceProvider} is a base implementation of {@link ResourceProvider}s.
 * 
 * @author Brian Oliver
 */
public abstract class AbstractResourceProvider<T> implements ResourceProvider<T>
{

    /**
     * The name of the Resource being provided.
     */
    private String resourceName;


    /**
     * Standard Constructor.
     * 
     * @param resourceName
     */
    public AbstractResourceProvider(String resourceName)
    {
        this.resourceName = resourceName;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceName()
    {
        return resourceName;
    }


    /**
     * Attempt to ensure that the Resource is available and can be provided to a caller.
     * 
     * @throws RuntimeException When attempting to access the Resource failed, but is not fatal (can be retried)
     * @throws ResourceUnavailableException If the Resource is simply unavailable and won't ever be.
     *  
     * @return <code>null</code> if the resource is not yet available but should be retried at a later point in time, 
     *         otherwise returns the resource. 
     */
    protected abstract T ensureResource() throws ResourceUnavailableException;
}