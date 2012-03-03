/*
 * File: ResourceUnavailableException.java
 * 
 * Copyright (c) 2010. All Rights Reserved. Oracle Corporation.
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
 * A {@link ResourceUnavailableException} is thrown when an attempt to request a Resource is made, but the
 * said Resource is unavailable.
 *
 * @author Brian Oliver
 */
@SuppressWarnings("serial")
public class ResourceUnavailableException extends RuntimeException
{

    /**
     * The name of the resource that is unavailable.
     */
    private String resourceName;


    /**
     * Standard Constructor (with a causing exception)
     * 
     * @param resourceName  The name of the unavailable Resource
     * @param cause         The {@link Throwable} that caused the Resource to become unavailable.
     */
    public ResourceUnavailableException(String resourceName,
                                        Throwable cause)
    {
        super(String.format("The resource [%s] is currently unavailable", resourceName), cause);
        this.resourceName = resourceName;
    }


    /**
     * Standard Constructor (without a causing exception)
     * 
     * @param resourceName The name of the unavailable Resource
     */
    public ResourceUnavailableException(String resourceName)
    {
        this(resourceName, null);
    }


    /**
     * Determines the name of the Resource that is unavailable.
     * 
     * @return A {@link String}
     */
    public String getResourceName()
    {
        return resourceName;
    }
}
