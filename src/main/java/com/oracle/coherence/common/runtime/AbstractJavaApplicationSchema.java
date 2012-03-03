/*
 * File: AbstractJavaApplicationSchema.java
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
import com.oracle.coherence.common.network.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link AbstractJavaApplicationSchema} is a base implementation of a {@link JavaApplicationSchema}.
 *
 * @author Brian Oliver
 */
public abstract class AbstractJavaApplicationSchema<A extends JavaApplication, S extends JavaApplicationSchema<A, S>>
    extends AbstractApplicationSchema<A, S> implements JavaApplicationSchema<A, S>
{
    /**
     * The com.sun.management.jmxremote JVM property.
     */
    public static final String SUN_MANAGEMENT_JMXREMOTE = "com.sun.management.jmxremote";

    /**
     * The com.sun.management.jmxremote.port JVM property.
     */
    public static final String SUN_MANAGEMENT_JMXREMOTE_PORT = "com.sun.management.jmxremote.port";

    /**
     * The com.sun.management.jmxremote.authenticate JVM property.
     */
    public static final String SUN_MANAGEMENT_JMXREMOTE_AUTHENTICATE = "com.sun.management.jmxremote.authenticate";

    /**
     * The com.sun.management.jmxremote.ssl JVM property.
     */
    public static final String SUN_MANAGEMENT_JMXREMOTE_SSL = "com.sun.management.jmxremote.ssl";

    /**
     * The java.rmi.server.hostname JVM property.
     */
    public static final String JAVA_RMI_SERVER_HOSTNAME = "java.rmi.server.hostname";

    /**
     * The java.net.preferIPv4Stack JVM property (false by default in most JVMs)
     */
    public static final String JAVA_NET_PREFER_IPV4_STACK = "java.net.preferIPv4Stack";

    /**
     * The class name for the {@link JavaApplication}.
     */
    private String m_applicationClassName;

    /**
     * The class path for the {@link JavaApplication}.
     */
    private String m_classPath;

    /**
     * The JVM options for the {@link JavaApplication}.
     */
    private ArrayList<String> m_jvmOptions;

    /**
     * The system properties for the {@link JavaApplication}.
     */
    private PropertiesBuilder m_systemPropertiesBuilder;


    /**
     * Construct a {@link JavaApplicationSchema} with a given application class name,
     * but using the class path of the executing application.
     *
     * @param applicationClassName  The fully qualified class name of the Java application.
     */
    public AbstractJavaApplicationSchema(String applicationClassName)
    {
        this(applicationClassName, System.getProperty("java.class.path"));
    }


    /**
     * Construct a {@link JavaApplicationSchema}.
     *
     * @param applicationClassName  The fully qualified class name of the Java application.
     * @param classPath             The class path for the Java application.
     */
    public AbstractJavaApplicationSchema(String applicationClassName,
                                         String classPath)
    {
        this.m_applicationClassName    = applicationClassName;
        this.m_classPath               = classPath;
        this.m_jvmOptions              = new ArrayList<String>();
        this.m_systemPropertiesBuilder = new PropertiesBuilder();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public PropertiesBuilder getSystemPropertiesBuilder()
    {
        return m_systemPropertiesBuilder;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getClassPath()
    {
        return m_classPath;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getApplicationClassName()
    {
        return m_applicationClassName;
    }


    /**
     * Sets the class path for the Java application.
     *
     * @param classPath  The classPath of the {@link JavaApplication}.
     *
     * @return The {@link JavaApplicationSchema}.
     */
    @SuppressWarnings("unchecked")
    public S setClassPath(String classPath)
    {
        this.m_classPath = classPath;

        return (S) this;
    }


    /**
     * Sets the specified system property.
     *
     * @param name   The name of the system property.
     * @param value  The value for the system property.
     *
     * @return The {@link JavaApplicationSchema}.
     */
    @SuppressWarnings("unchecked")
    public S setSystemProperty(String name,
                               Object value)
    {
        m_systemPropertiesBuilder.setProperty(name, value);

        return (S) this;
    }


    /**
     * Sets a default value for specified system property (to be used if it's not defined)
     *
     * @param name   The name of the system property.
     * @param value  The value for the system property.
     *
     * @return The {@link JavaApplicationSchema}.
     */
    @SuppressWarnings("unchecked")
    public S setDefaultSystemProperty(String name,
                                      Object value)
    {
        m_systemPropertiesBuilder.setDefaultProperty(name, value);

        return (S) this;
    }


    /**
     * Adds the properties defined by the {@link PropertiesBuilder} to this {@link JavaApplicationSchema}.
     *
     * @param systemProperties  The system {@link PropertiesBuilder}.
     *
     * @return The {@link JavaApplicationSchema}.
     */
    @SuppressWarnings("unchecked")
    public S setSystemProperties(PropertiesBuilder systemProperties)
    {
        m_systemPropertiesBuilder.addProperties(systemProperties);

        return (S) this;
    }


    /**
     * Adds a JVM Option to use when starting the Java application.
     *
     * @param option The JVM option
     *
     * @return The {@link JavaApplicationSchema}
     */
    @SuppressWarnings("unchecked")
    public S addOption(String option)
    {
        // drop the "-" if specified
        m_jvmOptions.add(option.startsWith("-") ? option.substring(1) : option);

        return (S) this;
    }


    /**
     * Adds a JVM Option to use when starting the Java application.
     *
     * @param option The JVM option.
     *
     * @return The {@link JavaApplicationSchema}
     */
    @SuppressWarnings("unchecked")
    public S setOption(String option)
    {
        addOption(option);

        return (S) this;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getJVMOptions()
    {
        return m_jvmOptions;
    }


    /**
     * Enables/Disables JMX support for the build {@link JavaApplication}s.
     * <p>
     * This method sets/removes numerous properties to enable/disable JMX.  Including:
     * <p>
     * <ol>
     *    <li>The remote JMX port to 9000, the Java default for remote JMX management.
     *        You can override this setting by calling {@link #setJMXPort(int)} or
     *        {@link #setJMXPort(AvailablePortIterator)}.
     *
     *    <li>The java.rmi.server.hostname to be {@link #LOCAL_HOST}
     *        You can override this setting by calling {@link #setRMIServerHostName(String)}.
     *
     *    <li>The sun.management.jmxremote.ssl to false (off)
     *
     *    <li>Disables JMX authentication.
     *        You can override this setting by calling {@link #setJMXAuthentication(boolean)}.
     *
     * @param enabled Should JMX support be enabled
     *
     * @return The {@link JavaApplicationSchema}
     */
    @SuppressWarnings("unchecked")
    public S setJMXSupport(boolean enabled)
    {
        if (enabled)
        {
            setDefaultSystemProperty(SUN_MANAGEMENT_JMXREMOTE_PORT, 9000);
            setDefaultSystemProperty(SUN_MANAGEMENT_JMXREMOTE, "");
            setDefaultSystemProperty(SUN_MANAGEMENT_JMXREMOTE_AUTHENTICATE, false);
            setDefaultSystemProperty(SUN_MANAGEMENT_JMXREMOTE_SSL, false);
            setDefaultSystemProperty(JAVA_RMI_SERVER_HOSTNAME, Constants.LOCAL_HOST);

            return (S) this;
        }
        else
        {
            m_systemPropertiesBuilder.removeProperty(SUN_MANAGEMENT_JMXREMOTE);
            m_systemPropertiesBuilder.removeProperty(SUN_MANAGEMENT_JMXREMOTE_PORT);
            m_systemPropertiesBuilder.removeProperty(SUN_MANAGEMENT_JMXREMOTE_AUTHENTICATE);
            m_systemPropertiesBuilder.removeProperty(SUN_MANAGEMENT_JMXREMOTE_SSL);
            m_systemPropertiesBuilder.removeProperty(JAVA_RMI_SERVER_HOSTNAME);

            return (S) this;
        }
    }


    /**
     * Specifies if IPv4 is required.
     *
     * @param enabled
     *
     * @return
     */
    public S setPreferIPv4(boolean enabled)
    {
        return setDefaultSystemProperty(JAVA_NET_PREFER_IPV4_STACK, enabled);
    }


    /**
     * Specifies if JMX authentication is enabled.
     *
     * @param enabled Is JMX Authentication required.
     *
     * @return The {@link JavaApplication}.
     */
    public S setJMXAuthentication(boolean enabled)
    {
        return setDefaultSystemProperty(SUN_MANAGEMENT_JMXREMOTE_AUTHENTICATE, enabled);
    }


    /**
     * Specifies the JMX remote port.
     *
     * @param port The port on which remote JMX should be enabled.
     *
     * @return The {@link JavaApplication}.
     */
    public S setJMXPort(int port)
    {
        return setSystemProperty(SUN_MANAGEMENT_JMXREMOTE_PORT, port);
    }


    /**
     * Specifies the JMX remote port using an AvailablePortIterator.
     *
     * @param portIterator The {@link AvailablePortIterator} that will be used to determine the JMX remote port.
     *
     * @return The {@link JavaApplication}.
     */
    public S setJMXPort(AvailablePortIterator portIterator)
    {
        return setSystemProperty(SUN_MANAGEMENT_JMXREMOTE_PORT, portIterator);
    }


    /**
     * Specifies the RMI Server Host Name.  By default this is typically "localhost".
     *
     * @param rmiServerHostName The hostname
     *
     * @return The {@link JavaApplication}.
     */
    @SuppressWarnings("unchecked")
    public S setRMIServerHostName(String rmiServerHostName)
    {
        setDefaultSystemProperty(JAVA_RMI_SERVER_HOSTNAME, rmiServerHostName);

        return (S) this;
    }
}
