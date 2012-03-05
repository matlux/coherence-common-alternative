package com.oracle.coherence.common.classloader;

import javax.management.MBeanServer;

/**
 * @author Jonathan Knight
 */
public interface MBeanServerProvider {

    MBeanServer getMBeanServer(String domain);

}
