/*
 * File: UnknownNamespaceContentHandlerTest.java
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
package com.oracle.coherence.environment.extensible.namespaces;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Test;

import com.oracle.coherence.common.builders.BuilderRegistry;
import com.oracle.coherence.environment.Environment;
import com.oracle.coherence.environment.extensible.ConfigurationContext;
import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.oracle.coherence.environment.extensible.DefaultConfigurationContext;
import com.oracle.coherence.environment.extensible.NamespaceContentHandler;

/**
 * The {@link UnknownNamespaceContentHandlerTest} exercises {@link DefaultConfigurationContext} handling of 
 * unknown {@link NamespaceContentHandler}s.
 *
 * @author Brian Oliver
 */
public class UnknownNamespaceContentHandlerTest
{

    /**
     * Ensure that valid http URIs are allowed, especially for xsi definitions.
     * 
     * @throws URISyntaxException if there is a problem with the URI
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testHttpAndXsiScheme() throws URISyntaxException, ConfigurationException
    {
        Environment env = mock(Environment.class);

        BuilderRegistry schemeRegistry = new BuilderRegistry();
        when(env.getResource(BuilderRegistry.class)).thenReturn(schemeRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);

        String xml = "<note xmlns=\"http://www.w3schools.com\"" + 
                     "      xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                     "      xsi:schemaLocation=\"http://www.w3schools.com/note.xsd\">" +
                     "    <to>Skippy</to>" +
                     "    <from>Lassie</from>" +
                     "    <heading>Reminder</heading>" +
                     "    <body>Woof Woof!</body>" +
                     "</note>";

        Object document = context.processDocument(xml);

        Assert.assertNull(document);
    }

}
