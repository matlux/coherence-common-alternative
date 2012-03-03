/*
 * File: AbstractDeferredResourceProvider.java
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
 * An {@link AbstractDeferredResourceProvider} is a {@link ResourceProvider} implementation that provides the ability to
 * block and wait a specified period of time for a Resource to become available.  
 * <p>
 * During the waiting time the {@link AbstractDeferredResourceProvider} implementation will attempt to make a 
 * configurable number of requests to determine if the resource is available.  If it becomes available before the 
 * specified time, the resource will be returned.  If it does not, a {@link ResourceUnavailableException} will be 
 * thrown.  
 * <p>
 * Extend this class to provide implementations for specific types of Resources.
 * 
 * @author Brian Oliver
 */
public abstract class AbstractDeferredResourceProvider<T> extends AbstractResourceProvider<T>
{

    /**
     * The duration (in milliseconds) that we will wait between failed attempts to ensure a Resource.
     */
    private long retryDelayDurationMS;

    /**
     * The total duration (in milliseconds) that we will attempt to ensure that a Resource before we fail. 
     */
    private long totalRetryDurationMS;


    /**
     * Standard Constructor
     * @param resourceName          The Resource name.
     * @param retryDelayDurationMS  The duration (in milliseconds) that we will wait between failed attempts to ensure a 
     *                              Resource.
     * @param totalRetryDurationMS  The total duration (in milliseconds) that we will attempt to ensure that a Resource 
     *                              before we fail. 
     */
    public AbstractDeferredResourceProvider(String resourceName,
                                            long retryDelayDurationMS,
                                            long totalRetryDurationMS)
    {
        super(resourceName);

        this.retryDelayDurationMS = retryDelayDurationMS;
        this.totalRetryDurationMS = totalRetryDurationMS;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public final T getResource() throws ResourceUnavailableException
    {
        long retryDuration = 0;
        while (totalRetryDurationMS < 0 ? true : retryDuration < totalRetryDurationMS)
        {
            try
            {
                T resource = ensureResource();
                
                if (resource != null)
                {
                    return resource;
                }
            }
            catch (ResourceUnavailableException resourceUnavailableException)
            {
                throw resourceUnavailableException;
            }
            catch (RuntimeException runtimeException)
            {
                //SKIP: Nothing to do here as we'll just retry
            }
            
            //wait for the value to become available
            try
            {
                Thread.sleep(retryDelayDurationMS);
                retryDuration += retryDelayDurationMS;
            }
            catch (InterruptedException interruptedException)
            {
                throw new ResourceUnavailableException(String.format(
                    "The resource [%s] is unavailable (ensuring was interrupted).", getResourceName()),
                    interruptedException);
            }

        }
        throw new ResourceUnavailableException(getResourceName());
    }
}