/*
 * File: SupervisedResourceProvider.java
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
 * A {@link SupervisedResourceProvider} is a {@link ResourceProvider} that has knowledge of the last known 
 * availability of a Resource, the purpose of which is to ensure that the Resource is protected from repeated 
 * requests to access it when may it be unavailable for a long period of time.
 * <p>
 * This interface is part of a work-around for INC-757 (and COH-3681). It's designed to restrict repeated attempts
 * to request a Resource that itself may take significant time and/or infrastructure to retrieve.
 * 
 * @author Brian Oliver
 */
public interface SupervisedResourceProvider<T> extends ResourceProvider<T>
{

    /**
     * Returns if the underlying Resource may be requested for accessed.
     * <p>
     * While a Resource may be accessible, this does not mean that it is actually available or usable.  
     * Being accessible simply determines if an attempt to access a resource is possible at the current point in time
     * or if it will be futile (known to fail immediately). 
     * 
     * @return <code>true</code> the Resource is accessible, <code>false</code> otherwise.
     */
    public boolean isResourceAccessible();


    /**
     * Applications should call this method when they determine a Resource they have acquired from the associated 
     * {@link SupervisedResourceProvider} is no longer available.  This ensures that a {@link SupervisedResourceProvider}
     * can protect repeated requests for the Resource from other parts of an Application.
     */
    public void resourceNoLongerAvailable();
}
