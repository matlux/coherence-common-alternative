/*
 * File: ClusterMemberSchema.java
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

package com.oracle.coherence.common.runtime;

import com.oracle.coherence.common.network.Constants;

import com.tangosol.coherence.component.net.Management;

import com.tangosol.net.DefaultCacheServer;

import java.util.Iterator;

/**
 * A {@link ClusterMemberSchema} is a Coherence-based specific {@link JavaApplicationSchema}.
 *
 * @author  Brian Oliver
 */
public class ClusterMemberSchema extends AbstractJavaApplicationSchema<ClusterMember, ClusterMemberSchema>
{
    /**
     * The {@link Management} enumeration specifies the valid JMX Management Modes for a cluster node.
     */
    public enum JMXManagementMode
    {
        ALL,
        NONE,
        REMOTE_ONLY,
        LOCAL_ONLY;

        /**
         * Determines the system property representation of the {@link JMXManagementMode}
         *
         * @return A {@link String}
         */
        public String getSystemProperty()
        {
            // default to all
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


    /**
     * The tangosol.coherence.cacheconfig property.
     */
    public static final String PROPERTY_CACHECONFIG = "tangosol.coherence.cacheconfig";

    /**
     * The tangosol.coherence.cluster property.
     */
    public static final String PROPERTY_CLUSTER_NAME = "tangosol.coherence.cluster";

    /**
     * The tangosol.coherence.clusterport property.
     */
    public static final String PROPERTY_CLUSTER_PORT = "tangosol.coherence.clusterport";

    /**
     * The tangosol.coherence.distributed.localstorage property.
     */
    public static final String PROPERTY_DISTRIBUTED_LOCALSTORAGE = "tangosol.coherence.distributed.localstorage";

    /**
     * The tangosol.coherence.localhost property.
     */
    public static final String PROPERTY_LOCALHOST_ADDRESS = "tangosol.coherence.localhost";

    /**
     * The tangosol.coherence.log.level property.
     */
    public static final String PROPERTY_LOG_LEVEL = "tangosol.coherence.log.level";

    /**
     * The tangosol.coherence.site property.
     */
    public static final String PROPERTY_SITE_NAME = "tangosol.coherence.site";

    /**
     * The tangosol.coherence.tcmp.enabled property.
     */
    public static final String PROPERTY_TCMP_ENABLED = "tangosol.coherence.tcmp.enabled";

    /**
     * The tangosol.coherence.management property.
     */
    public static final String PROPERTY_MANAGEMENT_MODE = "tangosol.coherence.management";

    /**
     * The tangosol.coherence.management.remote property.
     */
    public static final String PROPERTY_MANAGEMENT_REMOTE = "tangosol.coherence.management.remote";

    /**
     * The tangosol.coherence.ttl property.
     */
    public static final String PROPERTY_MULTICAST_TTL = "tangosol.coherence.ttl";

    /**
     * The tangosol.pof.config property.
     */
    public static final String PROPERTY_POF_CONFIG = "tangosol.pof.config";

    /**
     * The tangosol.pof.enabled property.
     */
    public static final String PROPERTY_POF_ENABLED = "tangosol.pof.enabled";

    /**
     * The tangosol.coherence.wka property.
     */
    public static final String PROPERTY_WELL_KNOWN_ADDRESS = "tangosol.coherence.wka";

    /**
     * The com.tangosol.net.DefaultCacheServer classname.
     */
    public static final String DEFAULT_CACHE_SERVER_CLASSNAME = "com.tangosol.net.DefaultCacheServer";


    /**
     * Constructs a {@link ClusterMemberSchema} for the {@link DefaultCacheServer}
     * defaulting to use the current classpath.
     */
    public ClusterMemberSchema()
    {
        super(DEFAULT_CACHE_SERVER_CLASSNAME);
    }


    /**
     * Constructs a {@link ClusterMemberSchema} defaulting to use the current classpath
     *
     * @param applicationClassName  The name of the class for the Coherence-based application.
     */
    public ClusterMemberSchema(String applicationClassName)
    {
        super(applicationClassName);
    }


    /**
     * Constructs a {@link ClusterMemberSchema}.
     *
     * @param applicationClassName  The name of the class for the Coherence-based application.
     * @param classPath             The class path of the application.
     */
    public ClusterMemberSchema(String applicationClassName,
                               String classPath)
    {
        super(applicationClassName, classPath);
    }


    /**
     * Sets the Cache Configuration URI for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param cacheConfigURI
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setCacheConfigURI(String cacheConfigURI)
    {
        setSystemProperty(PROPERTY_CACHECONFIG, cacheConfigURI);

        return this;
    }


    /**
     * Sets if storage should be enabled for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param isStorageEnabled
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setStorageEnabled(boolean isStorageEnabled)
    {
        setSystemProperty(PROPERTY_DISTRIBUTED_LOCALSTORAGE, isStorageEnabled);

        return this;
    }


    /**
     * Sets if TCMP should be enabled for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param isTCMPEnabled
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setTCMPEnabled(boolean isTCMPEnabled)
    {
        setSystemProperty(PROPERTY_TCMP_ENABLED, isTCMPEnabled);

        return this;
    }


    /**
     * Sets the cluster port for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param port
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setClusterPort(int port)
    {
        setSystemProperty(PROPERTY_CLUSTER_PORT, port);

        return this;
    }


    /**
     * Sets the cluster port for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param portIterator
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setClusterPort(Iterator<Integer> portIterator)
    {
        setSystemProperty(PROPERTY_CLUSTER_PORT, portIterator);

        return this;
    }


    /**
     * Sets the cluster name for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param name
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setClusterName(String name)
    {
        setSystemProperty(PROPERTY_CLUSTER_NAME, name);

        return this;
    }


    /**
     * Sets the site name for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param name
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setSiteName(String name)
    {
        setSystemProperty(PROPERTY_SITE_NAME, name);

        return this;
    }


    /**
     * Sets the multicast time-to-live for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param ttl
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setMulticastTTL(int ttl)
    {
        setSystemProperty(PROPERTY_MULTICAST_TTL, ttl);

        return this;
    }


    /**
     * Sets the JMX management mode for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param mode
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setJMXManagementMode(JMXManagementMode mode)
    {
        setJMXSupport(mode != JMXManagementMode.NONE);
        setSystemProperty(PROPERTY_MANAGEMENT_MODE, mode.getSystemProperty());

        return this;
    }


    /**
     * Sets if remote JMX monitoring is enabled for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param enabled
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setRemoteJMXManagement(boolean enabled)
    {
        setJMXSupport(enabled);
        setSystemProperty(PROPERTY_MANAGEMENT_REMOTE, enabled);

        return this;
    }


    /**
     * Sets the localhost address for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param localHostAddress
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setLocalHostAddress(String localHostAddress)
    {
        setSystemProperty(PROPERTY_LOCALHOST_ADDRESS, localHostAddress);

        return this;
    }


    /**
     * Sets the log level for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param level
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setLogLevel(int level)
    {
        setSystemProperty(PROPERTY_LOG_LEVEL, level);

        return this;
    }


    /**
     * Sets POF configuration uri for {@link ClusterMember}s created with this {@link ClusterMemberSchema}.
     *
     * @param pofConfigURI
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setPofConfigURI(String pofConfigURI)
    {
        setSystemProperty(PROPERTY_POF_CONFIG, pofConfigURI);
        setPofEnabled(true);

        return this;
    }


    /**
     * Configures the resulting {@link ClusterMember} to be Portable-Object-Format serialization enabled.
     *
     * @param isEnabled  <code>true</code> for POF enabled, <code>false</code> otherwise.
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setPofEnabled(boolean isEnabled)
    {
        setSystemProperty(PROPERTY_POF_ENABLED, isEnabled);

        return this;
    }


    /**
     * Configures the {@link ClusterMemberSchema} so that when realized by a {@link ClusterMemberBuilder}
     * the resulting {@link ClusterMember} will be running locally in single-server mode.
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setSingleServerMode()
    {
        setLocalHostAddress(Constants.LOCAL_HOST);
        setMulticastTTL(0);

        return this;
    }


    /**
     * Sets the Coherence Well Known Address (WKA).
     *
     * @param address  The address (as a {@link String}).
     *
     * @return The {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema setWellKnownAddress(String address)
    {
        return setSystemProperty(PROPERTY_WELL_KNOWN_ADDRESS, address);
    }

    /**
     * This Application will run in-process in an isolated ClassLoader
     *
     * @return This {@link ClusterMemberSchema}.
     */
    public ClusterMemberSchema runInProcess() {
        return super.runInProcess("startDaemon", "shutdown");
    }

}
