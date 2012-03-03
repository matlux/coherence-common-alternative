package com.oracle.coherence.environment.extensible.namespaces;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import com.oracle.coherence.common.builders.ParameterizedBuilder;
import com.oracle.coherence.configuration.caching.CacheMapping;
import com.oracle.coherence.configuration.caching.CacheMappingRegistry;
import com.oracle.coherence.configuration.parameters.EmptyParameterProvider;
import com.oracle.coherence.environment.Environment;
import com.oracle.coherence.environment.extensible.ConfigurationContext;
import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.oracle.coherence.environment.extensible.DefaultConfigurationContext;
import com.oracle.coherence.environment.extensible.QualifiedName;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlHelper;

/**
 * <p>A {@link ServerNamespaceContentHandlerTest} tests the BackingMapEventProcessor configuration.</p>
 *
 * @author Christer Fahlgren
 */
public class ServerNamespaceContentHandlerTest
{

    /**
     * Test creation of a BackingMapEventProcessor.
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration issue.
     */
    @Test
    public void testBackingMapEventProcessor() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);
        when(env.getClassLoader()).thenReturn(Thread.currentThread().getContextClassLoader());
        CacheMappingRegistry mockRegistry = mock(CacheMappingRegistry.class);
        CacheMapping mockCacheMapping = mock(CacheMapping.class);
        when(mockRegistry.findCacheMapping("coherence.patterns.actor.actors")).thenReturn(mockCacheMapping);
        when(env.getResource(CacheMappingRegistry.class)).thenReturn(mockRegistry);
        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("instance",
            new URI(String.format("class:%s", InstanceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("environment",
            new URI(String.format("class:%s", EnvironmentNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("value",
            new URI(String.format("class:%s", ValueNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("bm",
            new URI(String.format("class:%s", ServerNamespaceContentHandler.class.getName())));
        XmlElement elem = XmlHelper
            .loadXml("  <cache-mapping><cache-name>coherence.patterns.actor.actors</cache-name><scheme-name>ActorCacheScheme</scheme-name> <bm:backingmap-event-processor><instance:class classname=\"com.oracle.coherence.environment.extensible.namespaces.BMProcessor\" /></bm:backingmap-event-processor></cache-mapping>");
        XmlElement bmelem = elem.findElement("bm:backingmap-event-processor");
        ServerNamespaceContentHandler bmNamespaceContentHandler = (ServerNamespaceContentHandler) context
            .getNamespaceContentHandler("bm");
        ParameterizedBuilder<?> builder = (ParameterizedBuilder<?>) bmNamespaceContentHandler.onElement(context,
            new QualifiedName(bmelem), bmelem);
        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE).getClass(), BMProcessor.class);
    }


    /**
     * Test creation of a BackingMapEventProcessor but no cache-name in the enclosing element.
     * 
     * @throws URISyntaxException if there is a problem with the URI.
     * @throws ConfigurationException if there is a configuration issue.
     */
    @Test(expected = ConfigurationException.class)
    public void testNoCacheName() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);
        when(env.getClassLoader()).thenReturn(Thread.currentThread().getContextClassLoader());
        CacheMappingRegistry mockRegistry = mock(CacheMappingRegistry.class);
        CacheMapping mockCacheMapping = mock(CacheMapping.class);
        when(mockRegistry.findCacheMapping("coherence.patterns.actor.actors")).thenReturn(mockCacheMapping);
        when(env.getResource(CacheMappingRegistry.class)).thenReturn(mockRegistry);
        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("instance",
            new URI(String.format("class:%s", InstanceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("environment",
            new URI(String.format("class:%s", EnvironmentNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("value",
            new URI(String.format("class:%s", ValueNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("bm",
            new URI(String.format("class:%s", ServerNamespaceContentHandler.class.getName())));
        XmlElement elem = XmlHelper
            .loadXml("  <cache-mapping><scheme-name>ActorCacheScheme</scheme-name> <bm:backingmap-event-processor><instance:class classname=\"com.oracle.coherence.environment.extensible.namespaces.BMProcessor\" /></bm:backingmap-event-processor></cache-mapping>");
        XmlElement bmelem = elem.findElement("bm:backingmap-event-processor");
        ServerNamespaceContentHandler bmNamespaceContentHandler = (ServerNamespaceContentHandler) context
            .getNamespaceContentHandler("bm");
        ParameterizedBuilder<?> result = (ParameterizedBuilder<?>) bmNamespaceContentHandler.onElement(context,
            new QualifiedName(bmelem), bmelem);
        assertEquals(result.realize(EmptyParameterProvider.INSTANCE).getClass(), BMProcessor.class);
    }
}
