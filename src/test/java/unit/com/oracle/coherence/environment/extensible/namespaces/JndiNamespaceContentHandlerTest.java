/*
 * File: JndiNamespaceContentHandlerTest.java
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Test;

import com.oracle.coherence.common.builders.BuilderRegistry;
import com.oracle.coherence.common.builders.ParameterizedBuilder;
import com.oracle.coherence.environment.Environment;
import com.oracle.coherence.environment.extensible.ConfigurationContext;
import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.oracle.coherence.environment.extensible.DefaultConfigurationContext;
import com.sun.jndi.dns.DnsContext;

/**
 * The {@link JndiNamespaceContentHandlerTest} contains unit tests for the {@link JndiNamespaceContentHandler}.
 *
 * @author Brian Oliver
 */
public class JndiNamespaceContentHandlerTest
{

    /**
     * A simple test to ensure JNDI dns lookups are possible.
     * 
     * @throws NamingException 
     */
    @Test
    public void testSimpleJNDILookup() throws NamingException
    {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");

        Context ctx = new InitialContext();

        Assert.assertNotNull(ctx.lookup("dns:///www.oracle.com"));
    }


    /**
     * A test to use the jndi:resource to lookup a resource via JNDI.
     * 
     * @throws NamingException
     * @throws ConfigurationException
     * @throws URISyntaxException
     */
    @Test
    public void testJNDINamespaceResource() throws NamingException, ConfigurationException, URISyntaxException
    {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");

        Environment env = mock(Environment.class);

        BuilderRegistry schemeRegistry = new BuilderRegistry();
        when(env.getResource(BuilderRegistry.class)).thenReturn(schemeRegistry);

        ConfigurationContext context = new DefaultConfigurationContext(env);
        context.ensureNamespaceContentHandler("",
            new URI(String.format("class:%s", CoherenceNamespaceContentHandler.class.getName())));
        context.ensureNamespaceContentHandler("jndi",
            new URI(String.format("class:%s", JndiNamespaceContentHandler.class.getName())));

        String xml = "<jndi:resource><jndi:resource-name>dns:///www.oracle.com</jndi:resource-name></jndi:resource>";

        Object processedElement = context.processElement(xml);

        Assert.assertNotNull(processedElement);
        Assert.assertTrue(processedElement instanceof ParameterizedBuilder<?>);

        ParameterizedBuilder<?> classScheme = (ParameterizedBuilder<?>) processedElement;

        Assert.assertTrue(classScheme.realizesClassOf(DnsContext.class, null));
    }
}
