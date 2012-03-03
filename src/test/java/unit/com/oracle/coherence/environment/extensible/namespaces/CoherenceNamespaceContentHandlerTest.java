/*
 * File: CoherenceNamespaceContentHandlerTest.java
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;
import java.awt.Rectangle;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import com.oracle.coherence.common.builders.BuilderRegistry;
import com.oracle.coherence.common.builders.ParameterizedBuilder;
import com.oracle.coherence.configuration.caching.CacheMapping;
import com.oracle.coherence.configuration.caching.CacheMappingRegistry;
import com.oracle.coherence.configuration.parameters.EmptyParameterProvider;
import com.oracle.coherence.configuration.parameters.Parameter;
import com.oracle.coherence.configuration.parameters.ParameterProvider;
import com.oracle.coherence.configuration.parameters.SystemPropertyParameterProvider;
import com.oracle.coherence.environment.Environment;
import com.oracle.coherence.environment.extensible.ConfigurationContext;
import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.oracle.coherence.environment.extensible.DefaultConfigurationContext;
import com.tangosol.net.NamedCache;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlHelper;

/**
 * <p>Test the {@link CoherenceNamespaceContentHandler}. </p>
 *
 * @author Brian Oliver
 */
public class CoherenceNamespaceContentHandlerTest
{

    /**
     * <p>Test that we can realize custom-namespace elements in &lt;class-schemes&gt;.</p>
     * 
     * @throws URISyntaxException if there is a problem with the URI
     * @throws ConfigurationException if there is a configuration error
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testCustomClassScheme() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        BuilderRegistry schemeRegistry = new BuilderRegistry();
        when(env.getResource(BuilderRegistry.class)).thenReturn(schemeRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("value",
            new URI(String.format("class:%s", ValueNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("instance",
            new URI(String.format("class:%s", InstanceNamespaceContentHandler.class.getName())));

        String xml = "<class-scheme>" + "<instance:class classname=\"java.awt.Point\">"
                + "<value:integer>100</value:integer>" + "<value:integer>100</value:integer>" + "</instance:class>"
                + "</class-scheme>";

        XmlElement element = XmlHelper.loadXml(xml);

        Object processedElement = context.processElement(element);

        assertTrue(processedElement instanceof ParameterizedBuilder<?>);

        Point result = (Point) ((ParameterizedBuilder<?>) processedElement)
            .realize(SystemPropertyParameterProvider.INSTANCE);

        assertTrue(result.getX() == 100);
        assertTrue(result.getY() == 100);
    }


    /**
     * <p>Test that we can realize a &lt;class-scheme&gt;.</p>
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testConfigurableClassScheme() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        BuilderRegistry schemeRegistry = new BuilderRegistry();
        when(env.getResource(BuilderRegistry.class)).thenReturn(schemeRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        String xml = "<class-scheme>" + "<class-name>java.awt.Point</class-name>" + "<init-params>"
                + "<init-param><param-value>100</param-value></init-param>"
                + "<init-param><param-value>100</param-value></init-param>" + "</init-params>" + "</class-scheme>";

        XmlElement element = XmlHelper.loadXml(xml);

        ParameterizedBuilder<?> builder = (ParameterizedBuilder<?>) context.processElement(element);

        assertTrue(builder != null);
        assertTrue(builder.realizesClassOf(Point.class, EmptyParameterProvider.INSTANCE));
        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE), new Point(100, 100));
    }


    /**
     * <p>Test static factory use of a &lt;class-scheme&gt;.</p>
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testConfigurableStaticFactoryClassScheme() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        BuilderRegistry schemeRegistry = new BuilderRegistry();
        when(env.getResource(BuilderRegistry.class)).thenReturn(schemeRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        String xml = "<class-scheme>" + "<class-factory-name>java.lang.System</class-factory-name>"
                + "<method-name>getProperty</method-name>" + "<init-params>"
                + "<init-param><param-value>java.class.path</param-value></init-param>" + "</init-params>"
                + "</class-scheme>";

        XmlElement element = XmlHelper.loadXml(xml);

        ParameterizedBuilder<?> builder = (ParameterizedBuilder<?>) context.processElement(element);

        assertTrue(builder != null);
        assertTrue(builder.realizesClassOf(String.class, EmptyParameterProvider.INSTANCE));
        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE), System.getProperty("java.class.path"));
    }


    /**
     * <p>Test that invalid strongly typed params in a &lt;class-scheme&gt;s fail.</p>
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test(expected = Exception.class)
    public void testInvalidConfigurableClassScheme() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        BuilderRegistry schemeRegistry = new BuilderRegistry();
        when(env.getResource(BuilderRegistry.class)).thenReturn(schemeRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        String xml = "<class-scheme>" + "<class-name>java.awt.Point</class-name>" + "<init-params>"
                + "<init-param><param-type>double</param-type><param-value>100</param-value></init-param>"
                + "<init-param><param-value>100</param-value></init-param>" + "</init-params>" + "</class-scheme>";

        XmlElement element = XmlHelper.loadXml(xml);

        ParameterizedBuilder<?> builder = (ParameterizedBuilder<?>) context.processElement(element);

        assertTrue(builder != null);
        assertTrue(builder.realizesClassOf(Point.class, EmptyParameterProvider.INSTANCE));
        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE), new Point(100, 100));
    }


    /**
     * <p>Test nesting of traditional &lt;class-scheme&gt;s.</p>
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testNestedConfigurableClassScheme() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        BuilderRegistry schemeRegistry = new BuilderRegistry();
        when(env.getResource(BuilderRegistry.class)).thenReturn(schemeRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        String xml = "<class-scheme>" + "<class-name>java.awt.Rectangle</class-name>" + "<init-params>"
                + "<init-param><param-value><class-scheme>" + "<class-name>java.awt.Point</class-name>"
                + "<init-params><init-param><param-value>100</param-value></init-param>"
                + "<init-param><param-value>100</param-value></init-param>"
                + "</init-params></class-scheme></param-value></init-param>" + "</init-params></class-scheme>";

        XmlElement element = XmlHelper.loadXml(xml);

        ParameterizedBuilder<?> builder = (ParameterizedBuilder<?>) context.processElement(element);

        assertTrue(builder != null);
        assertTrue(builder.realizesClassOf(Rectangle.class, EmptyParameterProvider.INSTANCE));
        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE), new Rectangle(new Point(100, 100)));
    }


    /**
     * <p>Test that an init-param is returned as a {@link Parameter}.</p>
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a problem with the parsing.
     */
    @Test
    public void testInitParam() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        BuilderRegistry schemeRegistry = new BuilderRegistry();
        when(env.getResource(BuilderRegistry.class)).thenReturn(schemeRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        String xml = "<init-param><param-name>size</param-name><param-value>100</param-value></init-param>";

        XmlElement element = XmlHelper.loadXml(xml);

        Object processedElement = context.processElement(element);

        assertTrue(processedElement instanceof Parameter);
        assertTrue(((Parameter) processedElement).getName().equals("size"));
        assertTrue(((Parameter) processedElement).evaluate(EmptyParameterProvider.INSTANCE).getInt() == 100);
    }


    /**
     * <p>Test that an init-param using a {cache-ref} returns a special {@link Parameter}.</p>
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a problem with the parsing.
     */
    @Test
    public void testCacheRefInitParam() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        BuilderRegistry schemeRegistry = new BuilderRegistry();
        when(env.getResource(BuilderRegistry.class)).thenReturn(schemeRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        String xml = "<init-param><param-type>{cache-ref}</param-type><param-value>dist-cache</param-value></init-param>";

        XmlElement element = XmlHelper.loadXml(xml);

        Parameter parameter = (Parameter) context.processElement(element);

        assertTrue(parameter.isStronglyTyped());
        assertTrue(parameter.getType().equals(NamedCache.class.getName()));

        //TODO: we should use a mock CacheFactory here to ensure we're returning an instance of "dist-cache" here
    }


    /**
     * <p>Test that init-params are returned as a {@link ParameterProvider}s.</p>
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a problem with the parsing.
     */
    @Test
    public void testInitParams() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        BuilderRegistry schemeRegistry = new BuilderRegistry();
        when(env.getResource(BuilderRegistry.class)).thenReturn(schemeRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        String xml = "<init-params>"
                + "<init-param><param-name>size</param-name><param-value>100</param-value></init-param>"
                + "<init-param><param-name>autostart</param-name><param-value>true</param-value></init-param>"
                + "<init-param><param-name>name</param-name><param-value>rolf harris</param-value></init-param>"
                + "</init-params>";

        XmlElement element = XmlHelper.loadXml(xml);

        Object processedElement = context.processElement(element);

        assertTrue(processedElement instanceof ParameterProvider);
        ParameterProvider parameterProvider = (ParameterProvider) processedElement;

        assertTrue(parameterProvider.getParameter("size").evaluate(EmptyParameterProvider.INSTANCE).getInt() == 100);
        assertTrue(parameterProvider.getParameter("autostart").evaluate(EmptyParameterProvider.INSTANCE).getBoolean());
        assertTrue(parameterProvider.getParameter("name").evaluate(EmptyParameterProvider.INSTANCE).getString()
            .equals("rolf harris"));
    }


    /**
     * <p>Test that &lt;cache-mapping&gt;'s return a {@link CacheMapping}.</p>
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testCacheMapping() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        CacheMappingRegistry cacheMappingRegistry = new CacheMappingRegistry();
        when(env.getResource(CacheMappingRegistry.class)).thenReturn(cacheMappingRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        String xml = "<cache-mapping>" + "<cache-name>dist-*</cache-name>" + "<scheme-name>Distributed</scheme-name>"
                + "<init-params>"
                + "<init-param><param-name>size</param-name><param-value>100</param-value></init-param>"
                + "<init-param><param-name>autostart</param-name><param-value>true</param-value></init-param>"
                + "<init-param><param-name>name</param-name><param-value>rolf harris</param-value></init-param>"
                + "</init-params>" + "</cache-mapping>";

        XmlElement element = XmlHelper.loadXml(xml);

        Object processedElement = context.processElement(element);

        assertTrue(processedElement instanceof CacheMapping);
        CacheMapping cacheMapping = (CacheMapping) processedElement;
        assertTrue(cacheMappingRegistry.findCacheMapping("dist-*") == processedElement);
        assertTrue(cacheMapping.getCacheName().equals("dist-*"));
        assertTrue(cacheMapping.getSchemeName().equals("Distributed"));
        assertTrue(cacheMapping.getParameterProvider().getParameter("size").evaluate(EmptyParameterProvider.INSTANCE)
            .getInt() == 100);
        assertTrue(cacheMapping.getParameterProvider().getParameter("autostart")
            .evaluate(EmptyParameterProvider.INSTANCE).getBoolean());
        assertTrue(cacheMapping.getParameterProvider().getParameter("name").evaluate(EmptyParameterProvider.INSTANCE)
            .getString().equals("rolf harris"));
    }


    /**
     * <p>Test that &lt;defaults&gt; are captured during parsing and generated by the 
     * {@link CoherenceNamespaceContentHandler}.</p>
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testDefaults() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        CacheMappingRegistry cacheMappingRegistry = new CacheMappingRegistry();
        when(env.getResource(CacheMappingRegistry.class)).thenReturn(cacheMappingRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        CoherenceNamespaceContentHandler handler = (CoherenceNamespaceContentHandler) context
            .ensureNamespaceContentHandler("",
                new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));

        String xml = "<defaults>" + "<serializer><instance>"
                + "<class-name>com.tangosol.io.pof.ConfigurablePofContext</class-name><init-params>"
                + "<init-param><param-type>String</param-type>" + "<param-value>my-pof-config.xml</param-value>"
                + "</init-param></init-params></instance>" + "</serializer></defaults>";

        context.processElement(xml);

        //build the coherence config
        StringBuilder builder = new StringBuilder();
        handler.build(builder);
        String coherenceConfig = builder.toString();

        assertTrue(coherenceConfig.contains("<defaults>"));
        assertTrue(coherenceConfig.contains("com.tangosol.io.pof.ConfigurablePofContext"));
    }
}
