/*
 * File: AbstractCoherenceJavaApplicationBuilder.java
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

import java.util.Iterator;
import java.util.Properties;

import com.tangosol.coherence.component.net.Management;

/**
 * An {@link AbstractCoherenceJavaApplicationBuilder} is a specialized {@link AbstractJavaApplicationBuilder} that's 
 * designed to build {@link CoherenceJavaApplication}s.
 * <p>
 * NOTE: This class is now deprecated.  As a replacement please use the com.oracle.coherence.common.runtime package
 * for controlling application processes and coherence servers.
 *
 * @author Brian Oliver
 */
@Deprecated
public abstract class AbstractCoherenceJavaApplicationBuilder<B extends AbstractCoherenceJavaApplicationBuilder<?>>
        extends AbstractJavaApplicationBuilder<CoherenceJavaApplication, B>
{

    public static final String PROPERTY_CACHECONFIG = "tangosol.coherence.cacheconfig";

    public static final String PROPERTY_CLUSTER_NAME = "tangosol.coherence.cluster";

    public static final String PROPERTY_CLUSTER_PORT = "tangosol.coherence.clusterport";

    public static final String PROPERTY_DISTRIBUTED_LOCALSTORAGE = "tangosol.coherence.distributed.localstorage";

    public static final String PROPERTY_LOCALHOST_ADDRESS = "tangosol.coherence.localhost";

    public static final String PROPERTY_LOG_LEVEL = "tangosol.coherence.log.level";

    public static final String PROPERTY_SITE_NAME = "tangosol.coherence.site";

    public static final String PROPERTY_TCMP_ENABLED = "tangosol.coherence.tcmp.enabled";

    public static final String PROPERTY_MANAGEMENT_MODE = "tangosol.coherence.management";

    public static final String PROPERTY_MANAGEMENT_REMOTE = "tangosol.coherence.management.remote";

    public static final String PROPERTY_MULTICAST_TTL = "tangosol.coherence.ttl";

    public static final String PROPERTY_POF_CONFIG = "tangosol.pof.config";

    public static final String PROPERTY_POF_ENABLED = "tangosol.pof.enabled";


    /**
     * The {@link Management} enumeration specifies the valid JMX Management Modes for a cluster node.
     */
    public enum JMXManagementMode
    {
        ALL, NONE, REMOTE_ONLY, LOCAL_ONLY;

        /**
         * Determines the system property representation of the {@link JMXManagementMode}
         * 
         * @return A {@link String}
         */
        public String getSystemProperty()
        {
            //default to all
            String result = "all";

            if (this == NONE)
            {
                result = "none";
            }
            else if (this == REMOTE_ONLY)
            {
                result = "remote-only";
            }
            else if (this == LOCAL_ONLY)
            {
                result = "local-only";
            }

            return result;
        }
    }


    public AbstractCoherenceJavaApplicationBuilder(String applicationClassName)
    {
        super(applicationClassName);
    }


    public AbstractCoherenceJavaApplicationBuilder(String applicationClassName,
                                                   String classPath)
    {
        super(applicationClassName, classPath);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected CoherenceJavaApplication createJavaApplication(Process process,
                                                             String name,
                                                             ApplicationConsole console,
                                                             Properties environmentVariables,
                                                             Properties systemProperties)
    {
        return new CoherenceJavaApplication(process, name, console, environmentVariables, systemProperties);
    }


    @SuppressWarnings("unchecked")
    public B setCacheConfigURI(String cacheConfigURI)
    {
        setSystemProperty(PROPERTY_CACHECONFIG, cacheConfigURI);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setStorageEnabled(boolean isStorageEnabled)
    {
        setSystemProperty(PROPERTY_DISTRIBUTED_LOCALSTORAGE, isStorageEnabled);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setTCMPEnabled(boolean isTCMPEnabled)
    {
        setSystemProperty(PROPERTY_TCMP_ENABLED, isTCMPEnabled);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setClusterPort(int port)
    {
        setSystemProperty(PROPERTY_CLUSTER_PORT, port);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setClusterPort(Iterator<Integer> portIterator)
    {
        setSystemProperty(PROPERTY_CLUSTER_PORT, portIterator);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setClusterName(String name)
    {
        setSystemProperty(PROPERTY_CLUSTER_NAME, name);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setSiteName(String name)
    {
        setSystemProperty(PROPERTY_SITE_NAME, name);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setMulticastTTL(int ttl)
    {
        setSystemProperty(PROPERTY_MULTICAST_TTL, ttl);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setJMXManagementMode(JMXManagementMode mode)
    {
        setJMXSupport(mode != JMXManagementMode.NONE);
        setSystemProperty(PROPERTY_MANAGEMENT_MODE, mode.getSystemProperty());
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setRemoteJMXManagement(boolean enabled)
    {
        setJMXSupport(enabled);
        setSystemProperty(PROPERTY_MANAGEMENT_REMOTE, enabled);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setLocalHostAddress(String localHostAddress)
    {
        setSystemProperty(PROPERTY_LOCALHOST_ADDRESS, localHostAddress);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setLogLevel(int level)
    {
        setSystemProperty(PROPERTY_LOG_LEVEL, level);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setPofConfigURI(String pofConfigURI)
    {
        setSystemProperty(PROPERTY_POF_CONFIG, pofConfigURI);
        setPofEnabled(true);
        return (B) this;
    }


    @SuppressWarnings("unchecked")
    public B setPofEnabled(boolean isEnabled)
    {
        setSystemProperty(PROPERTY_POF_ENABLED, isEnabled);
        return (B) this;
    }
}
