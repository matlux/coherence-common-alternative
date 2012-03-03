/*
 * File: AbstractSupervisedResourceProvider.java
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

/**
 * An {@link AbstractSupervisedResourceProvider} is a base implementation of a {@link SupervisedResourceProvider}.
 * <p>
 * Extend this class to provide implementations for specific types of Resources.
 *
 * @author Brian Oliver
 */
public abstract class AbstractSupervisedResourceProvider<T> extends AbstractResourceProvider<T> implements
        SupervisedResourceProvider<T>
{

    /**
     * The instant in time (since the epoc) when the last access to the Resource failed.
     */
    private volatile long instantOfLastFailure;

    /**
     * The minimum amount of time (in ms) that must pass before accessing the Resource can be retried.
     */
    private long minimumAccessRetryDelay;


    /**
     * Standard Constructor.
     * 
     * @param minimumAccessRetryDelay The minimum number of milliseconds that must pass between access attempts
     */
    public AbstractSupervisedResourceProvider(String resourceName,
                                              long minimumAccessRetryDelay)
    {
        super(resourceName);

        this.instantOfLastFailure = 0;
        this.minimumAccessRetryDelay = minimumAccessRetryDelay;
    }


    /**
     * {@inheritDoc}
     */
    public final T getResource() throws ResourceUnavailableException
    {
        if (isResourceAccessible())
        {
            //we synchronize here to protect the underlying resource, 
            //that may not be able to cope with a lot of concurrent access
            synchronized (this)
            {
                //the current thread may have be held up for a while waiting, 
                //so we check again if the resource is still accessible 
                if (isResourceAccessible())
                {
                    try
                    {
                        //attempt to get the resource
                        return ensureResource();
                    }
                    catch (ResourceUnavailableException resourceUnavailableException)
                    {
                        //when a resource is unavailable, we must re-throw it
                        instantOfLastFailure = System.currentTimeMillis();
                        throw resourceUnavailableException;
                    }
                    catch (RuntimeException runtimeException)
                    {
                        //for runtime exceptions we re-throw them
                        instantOfLastFailure = System.currentTimeMillis();
                        throw runtimeException;
                    }
                }
                else
                {
                    throw new ResourceUnavailableException(getResourceName());
                }
            }
        }
        else
        {
            throw new ResourceUnavailableException(getResourceName());
        }
    }


    /**
     * {@inheritDoc}
     */
    public boolean isResourceAccessible()
    {
        return System.currentTimeMillis() > instantOfLastFailure + minimumAccessRetryDelay;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void resourceNoLongerAvailable()
    {
        instantOfLastFailure = System.currentTimeMillis();
    }
}
