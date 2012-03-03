/*
 * File: ClusterBuilderTest.java
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

import com.oracle.coherence.common.network.AvailablePortIterator;

import com.oracle.coherence.common.resourcing.AbstractDeferredResourceProvider;
import com.oracle.coherence.common.resourcing.ResourceProvider;
import com.oracle.coherence.common.resourcing.ResourceUnavailableException;

import com.oracle.coherence.common.runtime.ClusterMemberSchema.JMXManagementMode;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Test the {@link ClusterBuilder} class.
 *
 * @author Brian Oliver
 */
public class ClusterBuilderTest
{
    /**
     * A test to determine if we can create and destroy {@link Cluster}s.
     *
     * @throws Exception
     */
    @Test
    public void testClusterBuilding() throws Exception
    {
        final int             CLUSTER_SIZE = 5;

        AvailablePortIterator portIterator = new AvailablePortIterator(40000);

        ClusterMemberSchema schema =
            new ClusterMemberSchema().setEnvironmentVariables(PropertiesBuilder.fromCurrentEnvironmentVariables())
                .setSingleServerMode().setClusterPort(portIterator.next()).setJMXPort(portIterator)
                .setJMXManagementMode(JMXManagementMode.LOCAL_ONLY);

        Cluster cluster = null;

        try
        {
            ClusterBuilder builder = new ClusterBuilder();

            builder.addBuilder(new ClusterMemberBuilder(), schema, "DCCF", CLUSTER_SIZE);

            cluster = builder.realize(new SystemApplicationConsole());

            final Cluster             initialCluster = cluster;
            ResourceProvider<Cluster> resource = new AbstractDeferredResourceProvider<Cluster>("Cluster", 500, 60000)
            {
                @Override
                protected Cluster ensureResource() throws ResourceUnavailableException
                {
                    return initialCluster.iterator().next().getClusterSize() == CLUSTER_SIZE ? initialCluster : null;
                }
            };

            Assert.assertNotNull(resource.getResource());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail();
        }
        finally
        {
            if (cluster != null)
            {
                cluster.destroy();
            }
        }
    }
}
