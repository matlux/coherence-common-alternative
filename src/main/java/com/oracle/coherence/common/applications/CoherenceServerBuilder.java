/*
 * File: CoherenceServerBuilder.java
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
package com.oracle.coherence.common.applications;

/**
 * A {@link CoherenceServerBuilder} is a specialized {@link AbstractCoherenceJavaApplicationBuilder} that's designed
 * explicitly for realizing Coherence DefaultCacheServers.
 * <p>
 * NOTE: This class is now deprecated.  As a replacement please use the com.oracle.coherence.common.runtime package
 * for controlling application processes and coherence servers.
 *
 * @author Brian Oliver
 */
@Deprecated
public class CoherenceServerBuilder extends AbstractCoherenceJavaApplicationBuilder<CoherenceServerBuilder>
{

    public static final String DEFAULT_CACHE_SERVER_CLASSNAME = "com.tangosol.net.DefaultCacheServer";


    public CoherenceServerBuilder()
    {
        super(DEFAULT_CACHE_SERVER_CLASSNAME);
    }


    public CoherenceServerBuilder(String classPath)
    {
        super(DEFAULT_CACHE_SERVER_CLASSNAME, classPath);
    }
}
