/*
 * File: AbstractDeferredSingletonResourceProvider.java
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
 * An {@link AbstractDeferredSingletonResourceProvider} may be used to defer the creation
 * of a singleton resource, once and only once, to a later point in time.
 * <p>
 * Multiple calls to instances of this class are thread-safe.
 * 
 * @author Brian Oliver
 */
public abstract class AbstractDeferredSingletonResourceProvider<R> extends AbstractResourceProvider<R>
{

    /**
     * Has the resource been initialized?  (a volatile to avoid synchronized blocks)
     */
    private volatile boolean isInitialized;

    /**
     * The resolved resource.
     */
    private R resource;


    /**
     * Standard Constructor
     * 
     * @param name The name of the resource
     */
    public AbstractDeferredSingletonResourceProvider(String name)
    {
        super(name);
        isInitialized = false;
        resource = null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final R getResource() throws ResourceUnavailableException
    {
        if (isInitialized)
        {
            return resource;
        }
        else
        {
            synchronized(this)
            {
                if (isInitialized)
                {
                    return resource;
                }
                else
                {
                    resource = ensureResource();
                    isInitialized = true;
                    return resource;
                }
            }
        }
    }
}
