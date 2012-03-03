/*
 * File: SystemPropertyParameterProvider.java
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
package com.oracle.coherence.configuration.parameters;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;

/**
 * A {@link SystemPropertyParameterProvider} provides a {@link ParameterProvider} based representation of
 * the current state of the defined Java System {@link Properties}.
 *
 * @author Brian Oliver
 */
@SuppressWarnings("serial")
public class SystemPropertyParameterProvider implements ParameterProvider, ExternalizableLite, PortableObject
{

    /**
     * The default {@link SystemPropertyParameterProvider} instance.
     */
    public static final SystemPropertyParameterProvider INSTANCE = new SystemPropertyParameterProvider();


    /**
     * Standard Constructor.
     */
    public SystemPropertyParameterProvider()
    {
        //deliberately empty
    }


    /**
     * {@inheritDoc}
     */
    public boolean isDefined(String name)
    {
        return System.getProperties().containsKey(name);
    }


    /**
     * {@inheritDoc}
     */
    public Parameter getParameter(String name)
    {
        if (isDefined(name))
        {
            return new Parameter(name, System.getProperty(name));
        }
        else
        {
            return null;
        }
    }


    /**
     * {@inheritDoc}
     */
    public boolean isEmpty()
    {
        return System.getProperties().isEmpty();
    }


    /**
     * {@inheritDoc}
     */
    public int size()
    {
        return System.getProperties().size();
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Iterator<Parameter> iterator()
    {
        final Enumeration<String> propertyNames = (Enumeration<String>) System.getProperties().propertyNames();

        return new Iterator<Parameter>()
        {

            /**
             * {@inheritDoc}
             */
            public boolean hasNext()
            {
                return propertyNames.hasMoreElements();
            }


            /**
             * {@inheritDoc}
             */
            public Parameter next()
            {
                String propertyName = propertyNames.nextElement();
                return new Parameter(propertyName, System.getProperty(propertyName));
            }


            /**
             * {@inheritDoc}
             */
            public void remove()
            {
                throw new UnsupportedOperationException(
                    "Can't remove a parameter from an SystemPropertyParameterProvider");
            }
        };
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, ?> getParameters(ParameterProvider parameterProvider)
    {
        HashMap<String, Object> result = new HashMap<String, Object>();
        for (Entry<Object, Object> entry : System.getProperties().entrySet())
        {
            result.put(entry.getKey().toString(), entry.getValue());
        }
        return result;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Properties getProperties(ParameterProvider parameterProvider)
    {
        return System.getProperties();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readExternal(DataInput in) throws IOException
    {
        //deliberately empty
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeExternal(DataOutput out) throws IOException
    {
        //deliberately empty
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readExternal(PofReader reader) throws IOException
    {
        //deliberately empty
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeExternal(PofWriter writer) throws IOException
    {
        //deliberately empty
    }
}
