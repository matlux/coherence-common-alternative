/*
 * File: JndiNamespaceContentHandler.java
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
import com.oracle.coherence.common.builders.JndiBasedParameterizedBuilder;
import com.oracle.coherence.environment.extensible.ConfigurationContext;
import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.oracle.coherence.environment.extensible.ElementContentHandler;
import com.oracle.coherence.environment.extensible.QualifiedName;
import com.tangosol.run.xml.XmlElement;

/**
 * The {@link JndiNamespaceContentHandler} provides the ability to work with JNDI and produce {@link ParameterizedBuilder}s
 * as required for integration with Coherence.
 * 
 * @author Brian Oliver
 */
public class JndiNamespaceContentHandler extends AbstractNamespaceContentHandler
{

    /**
     * Standard Constructor.
     */
    public JndiNamespaceContentHandler()
    {
        registerContentHandler("resource", new ElementContentHandler()
        {

            @Override
            public Object onElement(ConfigurationContext context,
                                    QualifiedName qualifiedName,
                                    XmlElement xmlElement) throws ConfigurationException
            {
                JndiBasedParameterizedBuilder builder = new JndiBasedParameterizedBuilder();
                context.configure(builder, qualifiedName, xmlElement);
                return builder;
            }
        });
    }
}
