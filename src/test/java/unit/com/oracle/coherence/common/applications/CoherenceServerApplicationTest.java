/*
 * File: CoherenceServerApplicationTest.java
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

import com.oracle.coherence.common.applications.AbstractCoherenceJavaApplicationBuilder.JMXManagementMode;

import com.oracle.coherence.common.network.AvailablePortIterator;
import com.oracle.coherence.common.network.Constants;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;

import java.net.UnknownHostException;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanInfo;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

/**
 * Test the {@link JavaConsoleApplication} class.
 *
 * @author Brian Oliver
 */
public class CoherenceServerApplicationTest
{
    /**
     * A test to determine if we can connect to the Coherence JMX infrastructure.
     *
     * @throws UnknownHostException
     * @throws IOException
     * @throws MalformedObjectNameException
     * @throws NullPointerException
     * @throws InstanceNotFoundException
     * @throws IntrospectionException
     * @throws ReflectionException
     */
    @Test
    public void testJMXConnection() throws UnknownHostException
    {
        AvailablePortIterator portIterator = new AvailablePortIterator(40000);

        CoherenceServerBuilder builder =
            new CoherenceServerBuilder().setEnvironmentVariables(PropertiesBuilder.fromCurrentEnvironmentVariables())
                .setMulticastTTL(0).setLocalHostAddress(Constants.LOCAL_HOST).setClusterPort(portIterator)
                .setJMXPort(portIterator).setJMXManagementMode(JMXManagementMode.LOCAL_ONLY);

        JavaApplication application = null;

        try
        {
            application = builder.realize("TEST");

            MBeanInfo mBeanInfo = application.getMBeanInfo(new ObjectName("Coherence:type=Cluster"));

            if (application instanceof AbstractApplication)
            {
                long pid = ((AbstractApplication) application).getPid();

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
            if (application != null)
            {
                application.destroy();
            }
        }
    }
}
