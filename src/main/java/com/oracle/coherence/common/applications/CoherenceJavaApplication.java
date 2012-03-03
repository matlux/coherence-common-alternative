/*
 * File: CoherenceJavaApplication.java
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

import java.util.Properties;

import javax.management.MBeanInfo;
import javax.management.ObjectName;

import com.oracle.coherence.common.resourcing.ResourceUnavailableException;

/**
 * A {@link CoherenceJavaApplication} is a {@link JavaConsoleApplication} that provides special methods
 * to manage Coherence-based Java Applications.  
 * <p>
 * NOTE: This class is now deprecated.  As a replacement please use the com.oracle.coherence.common.runtime package
 * for controlling application processes and coherence servers.
 *
 * @author Brian Oliver
 */
@Deprecated
public class CoherenceJavaApplication extends JavaConsoleApplication
{

    /**
     * Standard Constructor.
     * 
     * @param process               The {@link Process} representing the {@link CoherenceJavaApplication}.
     *                      
     * @param name                  The name of the {@link CoherenceJavaApplication}.
     *                      
     * @param console               The {@link ApplicationConsole} that will be used for I/O by the 
     *                              realized {@link Application}. This may be <code>null</code> if not required.     
     *                                       
     * @param environmentVariables  The environment variables used when starting the {@link CoherenceJavaApplication}.
     * 
     * @param systemProperties      The system properties provided to the {@link CoherenceJavaApplication}
     */
    public CoherenceJavaApplication(Process process,
                                    String name,
                                    ApplicationConsole console,
                                    Properties environmentVariables,
                                    Properties systemProperties)
    {
        super(process, name, console, environmentVariables, systemProperties);
    }


    /**
     * Returns the Coherence Cluster {@link MBeanInfo} for the {@link CoherenceJavaApplication}. 
     * <p>
     * If the JMX infrastructure in the {@link JavaApplication} is not yet available, it will block at wait for
     * 60 seconds until it becomes available.
     * 
     * @return A {@link MBeanInfo}.
     * 
     * @throws ResourceUnavailableException  If the Cluster MBean is not available.
     * @throws UnsupportedOperationException If JMX is not enabled for the {@link JavaApplication}.
     */
    public MBeanInfo getClusterMBeanInfo()
    {
        try
        {
            return super.getMBeanInfo(new ObjectName("Coherence:type=Cluster"));
        }
        catch (Exception e)
        {
            throw new UnsupportedOperationException("Could not retrieve the Coherence Cluster MBean", e);
        }
    }


    /**
     * Returns the Coherence Service {@link MBeanInfo} for the {@link CoherenceJavaApplication}. 
     * <p>
     * If the JMX infrastructure in the {@link JavaApplication} is not yet available, it will block at wait for
     * 60 seconds until it becomes available.
     * 
     * @param serviceName The name of the service
     * @param nodeId      The nodeId on which the service is defined
     * 
     * @return A {@link MBeanInfo}.
     * 
     * @throws ResourceUnavailableException  If the Cluster MBean is not available.
     * @throws UnsupportedOperationException If JMX is not enabled for the {@link JavaApplication}.
     */
    public MBeanInfo getServiceMBeanInfo(String serviceName,
                                         int nodeId)
    {
        try
        {
            return super.getMBeanInfo(new ObjectName(String.format("Coherence:type=Service,name=%s,nodeId=%d",
                serviceName, nodeId)));
        }
        catch (Exception e)
        {
            throw new UnsupportedOperationException(String.format(
                "Could not retrieve the Coherence Service MBean [%s]", serviceName), e);
        }
    }
}
