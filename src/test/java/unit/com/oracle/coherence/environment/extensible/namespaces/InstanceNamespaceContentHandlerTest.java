/*
 * File: InstanceNamespaceContentHandlerTest.java
 * 
 * Copyright (c) 2010. All Rights Reserved. Oracle Corporation.
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
package com.oracle.coherence.environment.extensible.namespaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import com.oracle.coherence.common.builders.Builder;
import com.oracle.coherence.common.builders.ParameterizedBuilder;
import com.oracle.coherence.configuration.parameters.SystemPropertyParameterProvider;
import com.oracle.coherence.environment.Environment;
import com.oracle.coherence.environment.extensible.ConfigurationContext;
import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.oracle.coherence.environment.extensible.DefaultConfigurationContext;
import com.oracle.coherence.environment.extensible.QualifiedName;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlHelper;

/**
 * <p>Test the {@link InstanceNamespaceContentHandler}. </p>
 *
 * @author Christer Fahlgren
 * @author Brian Oliver
 */
@Deprecated
public class InstanceNamespaceContentHandlerTest
{

    /**
     * Test instance:class.
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementInstance() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);
        when(env.getClassLoader()).thenReturn(Thread.currentThread().getContextClassLoader());

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("instance",
            new URI(String.format("class:%s", InstanceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("environment",
            new URI(String.format("class:%s", EnvironmentNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("value",
            new URI(String.format("class:%s", ValueNamespaceContentHandler.class.getName())));

        XmlElement elem = XmlHelper
            .loadXml("<instance:class classname=\"java.awt.Point\"><value:integer>100</value:integer><value:integer>200</value:integer></instance:class>");

        InstanceNamespaceContentHandler instanceNamespaceContentHandler = (InstanceNamespaceContentHandler) context
            .getNamespaceContentHandler("instance");

        ParameterizedBuilder<?> builder = (ParameterizedBuilder<?>) instanceNamespaceContentHandler.onElement(context,
            new QualifiedName(elem), elem);

        assertEquals(builder.realize(SystemPropertyParameterProvider.INSTANCE), new Point(100, 200));
    }


    /**
     * Test instance:class.
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementInstanceNotEquals() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);
        when(env.getClassLoader()).thenReturn(Thread.currentThread().getContextClassLoader());

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("instance",
            new URI(String.format("class:%s", InstanceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("environment",
            new URI(String.format("class:%s", EnvironmentNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("value",
            new URI(String.format("class:%s", ValueNamespaceContentHandler.class.getName())));

        XmlElement elem = XmlHelper
            .loadXml("<instance:class classname=\"java.awt.Point\"><value:integer>100</value:integer><value:integer>200</value:integer></instance:class>");

        InstanceNamespaceContentHandler instanceNamespaceContentHandler = (InstanceNamespaceContentHandler) context
            .getNamespaceContentHandler("instance");

        ParameterizedBuilder<?> builder = (ParameterizedBuilder<?>) instanceNamespaceContentHandler.onElement(context,
            new QualifiedName(elem), elem);

        assertFalse(builder.realize(SystemPropertyParameterProvider.INSTANCE).equals(new Point(100, 100)));
    }


    /**
     * Test instance:class.
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementInstanceRectangle() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);
        when(env.getClassLoader()).thenReturn(Thread.currentThread().getContextClassLoader());

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("instance",
            new URI(String.format("class:%s", InstanceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("environment",
            new URI(String.format("class:%s", EnvironmentNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("value",
            new URI(String.format("class:%s", ValueNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        InstanceNamespaceContentHandler instanceNamespaceContentHandler = (InstanceNamespaceContentHandler) context
            .getNamespaceContentHandler("instance");

        StringBuilder sb = new StringBuilder("<instance:class classname=\"java.awt.Rectangle\">");
        sb.append("  <instance:class classname=\"java.awt.Point\">");
        sb.append("    <value:integer>100</value:integer>");
        sb.append("    <value:integer>100</value:integer>");
        sb.append("  </instance:class>");
        sb.append("  <instance:class classname=\"java.awt.Dimension\">");
        sb.append("    <value:integer>200</value:integer>");
        sb.append("    <value:integer>200</value:integer>");
        sb.append("  </instance:class>");
        sb.append("</instance:class>");
        XmlElement elem = XmlHelper.loadXml(sb.toString());

        ParameterizedBuilder<?> builder = (ParameterizedBuilder<?>) instanceNamespaceContentHandler.onElement(context,
            new QualifiedName(elem), elem);
        assertEquals(builder.realize(SystemPropertyParameterProvider.INSTANCE), new Rectangle(new Point(100, 100),
            new Dimension(200, 200)));
    }


    /**
     * <p>Test instance:class with a scheme.</p>
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementInstanceWithScheme() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);
        when(env.getClassLoader()).thenReturn(Thread.currentThread().getContextClassLoader());

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("instance",
            new URI(String.format("class:%s", InstanceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("environment",
            new URI(String.format("class:%s", EnvironmentNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("value",
            new URI(String.format("class:%s", ValueNamespaceContentHandler.class.getName())));

        XmlElement elem = XmlHelper
            .loadXml("<instance:class classname=\"com.oracle.coherence.environment.extensible.namespaces.InstanceNamespaceContentHandlerTest$HelloWorldScheme\"/>");

        InstanceNamespaceContentHandler instanceNamespaceContentHandler = (InstanceNamespaceContentHandler) context
            .getNamespaceContentHandler("instance");

        Object result = instanceNamespaceContentHandler.onElement(context, new QualifiedName(elem), elem);

        assertTrue(result instanceof HelloWorldScheme);
    }


    /**
     * <p>A {@link HelloWorldScheme} is a simple {@link Builder} that when realized will return a greeting.</p>
     */
    public static class HelloWorldScheme implements Builder<String>
    {

        /**
         * A simple test {@link Scheme}.
         * 
         * @return a String
         */
        public String realize()
        {
            return "Hello World";
        }
    }
}
