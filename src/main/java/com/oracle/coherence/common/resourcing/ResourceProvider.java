/*
 * File: ResourceProvider.java
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

import com.oracle.coherence.common.builders.Builder;

/**
 * A {@link ResourceProvider} manages the provision and access to a runtime resource.
 * <p>
 * {@link ResourceProvider}s are commonly used for resources that may not be immediately available at the time of 
 * request, make take some time to reach a certain required state, or may need to be lazily constructed. 
 * <p>
 * {@link ResourceProvider}s are not {@link Builder}s.  An individual {@link ResourceProvider} instance is designed to 
 * manage a single Resource, where as a {@link Builder} is designed to realize, build or allocate multiple resources.
 * <p> 
 * For example, a {@link ResourceProvider} may be used to represent a connection to a specific server that has not yet 
 * been started, where as a Connection {@link Builder} may be used to create connections to any number of servers. 
 * <p>
 * There may be many implementations of {@link ResourceProvider}s, each with their own strategy for dealing 
 * with resource provision and what happens when a resource is not available.  Consequently careful consideration as to 
 * the choice of {@link ResourceProvider} implementation should be made to ensure the correct semantics are achieved. 
 *
 * @author Brian Oliver
 */
public interface ResourceProvider<T>
{

    /**
     * Determines the name of the Resource.  This is often used for logging and display purposes.
     * 
     * @return A {@link String}
     */
    public String getResourceName();


    /**
     * Requests the Resource.   
     * 
     * @return A Resource of type T (never <code>null</code>)
     * 
     * @throws ResourceUnavailableException When the {@link ResourceProvider} implementation can't produce the resource. 
     */
    public T getResource() throws ResourceUnavailableException;

}
