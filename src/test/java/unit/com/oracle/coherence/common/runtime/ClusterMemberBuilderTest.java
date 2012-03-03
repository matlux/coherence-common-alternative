/*
 * File: ClusterMemberBuilderTest.java
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

import com.oracle.coherence.common.runtime.ClusterMemberSchema.JMXManagementMode;

import junit.framework.Assert;

import org.junit.Test;

import javax.management.MBeanInfo;

/**
 * Test the {@link ClusterMemberBuilder} class.
 *
 * @author Brian Oliver
 */
public class ClusterMemberBuilderTest
{
    /**
     * A test to determine if we can connect to the Coherence JMX infrastructure.
     *
     * @throws Exception
     */
    @Test
    public void testJMXConnection() throws Exception
    {
        AvailablePortIterator portIterator = new AvailablePortIterator(40000);

        ClusterMemberSchema schema =
            new ClusterMemberSchema().setEnvironmentVariables(PropertiesBuilder.fromCurrentEnvironmentVariables())
                .setSingleServerMode().setClusterPort(portIterator).setJMXPort(portIterator)
                .setJMXManagementMode(JMXManagementMode.LOCAL_ONLY);

        ClusterMember member = null;

        try
        {
            ClusterMemberBuilder builder = new ClusterMemberBuilder();

            member = builder.realize(schema, "TEST");

            MBeanInfo mBeanInfo = member.getClusterMBeanInfo();

            if (member instanceof AbstractApplication)
            {
                long pid = ((AbstractApplication) member).getPid();

                Assert.assertTrue(pid > 0);
            }

            Assert.assertNotNull(mBeanInfo);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (member != null)
            {
                member.destroy();
            }
        }
    }
}
