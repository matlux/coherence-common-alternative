/*
 * File: NullNamespaceContentHandler.java
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

import java.net.URI;

import com.oracle.coherence.environment.extensible.AttributeContentHandler;
import com.oracle.coherence.environment.extensible.ConfigurationContext;
import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.oracle.coherence.environment.extensible.ElementContentHandler;
import com.oracle.coherence.environment.extensible.NamespaceContentHandler;
import com.oracle.coherence.environment.extensible.QualifiedName;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlValue;

/**
 * The {@link NullNamespaceContentHandler} provides a non-operation implementation of a {@link NamespaceContentHandler},
 * with an associated {@link ElementContentHandler} and {@link AttributeContentHandler}. 
 * <p>
 * This is use implementation is used to provide {@link NamespaceContentHandler}s for unknown but valid namespace URIs.
 * 
 * @author Brian Oliver
 */
public class NullNamespaceContentHandler implements NamespaceContentHandler, ElementContentHandler,
        AttributeContentHandler
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStartScope(ConfigurationContext context,
                             String prefix,
                             URI uri)
    {
        //deliberately empty
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onEndScope(ConfigurationContext context,
                           String prefix,
                           URI uri)
    {
        //deliberately empty
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object onElement(ConfigurationContext context,
                            QualifiedName qualifiedName,
                            XmlElement xmlElement) throws ConfigurationException
    {
        return null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttribute(ConfigurationContext context,
                            QualifiedName qualifiedName,
                            XmlValue xmlValue) throws ConfigurationException
    {
        //deliberately empty
    }
}
