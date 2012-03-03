/*
 * File: EnvironmentNamespaceContentHandlerTest.java
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
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import com.oracle.coherence.environment.Environment;
import com.oracle.coherence.environment.extensible.ConfigurationContext;
import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.oracle.coherence.environment.extensible.DefaultConfigurationContext;
import com.oracle.coherence.environment.extensible.QualifiedName;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlHelper;

/**
 * <p>The unit tests for the {@link EnvironmentNamespaceContentHandler}.</p>
 *
 * @author Christer Fahlgren
 * @author Brian Oliver
 */
public class EnvironmentNamespaceContentHandlerTest
{

    /**
     * Test environment:instance.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testEnvironmentInstance() throws ConfigurationException
    {
        Environment env = mock(Environment.class);

        ConfigurationContext context = new DefaultConfigurationContext(env);

        StringBuilder sb = new StringBuilder("<environment:instance/>");
        XmlElement elem = XmlHelper.loadXml(sb.toString());

        EnvironmentNamespaceContentHandler handler = new EnvironmentNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertSame(result, env);
    }


    /**
     * Test environment:resource.
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testEnvironmentResource() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("instance",
            new URI(String.format("class:%s", InstanceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("value",
            new URI(String.format("class:%s", ValueNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("environment",
            new URI(String.format("class:%s", EnvironmentNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        StringBuilder sb = new StringBuilder("<environment:resource id=\"java.awt.Point\">");
        sb.append("  <instance:class classname=\"java.awt.Point\">");
        sb.append("    <value:integer>100</value:integer>");
        sb.append("    <value:integer>100</value:integer>");
        sb.append("  </instance:class>");
        sb.append("</environment:resource>");
        XmlElement elem = XmlHelper.loadXml(sb.toString());

        EnvironmentNamespaceContentHandler handler = (EnvironmentNamespaceContentHandler) context
            .getNamespaceContentHandler("environment");
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        verify(env).registerResource(Point.class, (Point) result);
    }


    /**
     * Test environment:ref.
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testEnvironmentReference() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("instance",
            new URI(String.format("class:%s", InstanceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("environment",
            new URI(String.format("class:%s", EnvironmentNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        when(env.getResource(Point.class)).thenReturn(new Point(100, 100));

        StringBuilder sb = new StringBuilder("<environment:ref id=\"java.awt.Point\"/>");
        XmlElement elem = XmlHelper.loadXml(sb.toString());

        EnvironmentNamespaceContentHandler handler = (EnvironmentNamespaceContentHandler) context
            .getNamespaceContentHandler("environment");
        Object result = handler.onElement(context, new QualifiedName(elem), elem);
        assertEquals(result.getClass(), Point.class);
    }
}
