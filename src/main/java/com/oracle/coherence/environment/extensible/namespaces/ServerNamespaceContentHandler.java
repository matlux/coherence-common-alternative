/*
 * File: BackingMapNamespaceContentHandler.java
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

import com.oracle.coherence.common.builders.ParameterizedBuilder;
import com.oracle.coherence.common.events.processing.EventProcessor;
import com.oracle.coherence.configuration.caching.CacheMapping;
import com.oracle.coherence.configuration.caching.CacheMappingRegistry;
import com.oracle.coherence.environment.extensible.ConfigurationContext;
import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.oracle.coherence.environment.extensible.ElementContentHandler;
import com.oracle.coherence.environment.extensible.QualifiedName;
import com.tangosol.run.xml.XmlElement;

/**
 * <p>A {@link ServerNamespaceContentHandler} handles XML Configuration processing for the
 * backing map {@link EventProcessor}s for {@link com.tangosol.net.NamedCache}s.</p>
 *
 * @author Christer Fahlgren
 */
public class ServerNamespaceContentHandler extends AbstractNamespaceContentHandler
{

    /**
     * <p>Standard Constructor.</p>
     */
    public ServerNamespaceContentHandler()
    {
        registerContentHandler("backingmap-event-processor", new ElementContentHandler()
        {

            public Object onElement(ConfigurationContext context,
                                    QualifiedName qualifiedName,
                                    XmlElement xmlElement) throws ConfigurationException
            {
                //decorate the CacheMapping for the cache with the PublisherDefinition
                String cacheName = getSourceCacheName(xmlElement);
                CacheMapping cacheMapping = context.getEnvironment().getResource(CacheMappingRegistry.class)
                    .findCacheMapping(cacheName);
                ParameterizedBuilder<?> builder = (ParameterizedBuilder<?>) context.processOnlyElementOf(xmlElement);
                cacheMapping.addEnrichment(ParameterizedBuilder.class, "event-processor", builder);
                return builder;
            }
        });
    }


    /**
     * <p>Determines the name of the cache in which a cache-backingmap-event-processor element is declared.</p>
     * 
     * @param cacheEventProcessorElement The {@link XmlElement} for the cache-backingmap-event-processor declaration
     * 
     * @return A string containing the name of the cache for the cache-backingmap-event-processor
     * 
     * @throws ConfigurationException If the name of the cache could not be determined
     */
    private String getSourceCacheName(XmlElement cacheEventProcessorElement) throws ConfigurationException
    {
        //ensure the publisherElement is inside a cache-mapping
        if (cacheEventProcessorElement.getParent() != null
                && new QualifiedName(cacheEventProcessorElement.getParent()).getLocalName().equals("cache-mapping"))
        {
            //determine the cache name from the cache-mapping
            XmlElement cacheNameElement = cacheEventProcessorElement.getParent().findElement("cache-name");
            if (cacheNameElement == null)
            {
                //the cache-backingmap-event-processor should be in a cache-mapping
                throw new ConfigurationException(String.format(
                    "The element %s is missing a <cache-name> element declaration",
                    cacheEventProcessorElement.getParent()),
                    "Please consult the documentation on how to use the BackingMapEventProcessor namespace");
            }
            return cacheNameElement.getString();
        }
        else
        {
            //the cache-backingmap-event-processor should be in a cache-mapping
            throw new ConfigurationException(
                String.format("Missing 'cache-name' declaration for the cache-backingmap-event-processor %s",
                    cacheEventProcessorElement),
                "The cache-backingmap-event-processor declaration should either inside a <cache-mapping> element or declare a 'cache-name' attribute.");
        }
    }
}