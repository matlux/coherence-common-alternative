/*
 * File: EmptyParameterProvider.java
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
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;

/**
 * A {@link EmptyParameterProvider} is an implementation of a {@link ParameterProvider} that
 * knows of no {@link Parameter}s.
 *
 * @author Brian Oliver
 */
@SuppressWarnings("serial")
public class EmptyParameterProvider implements ParameterProvider, ExternalizableLite, PortableObject
{

    /**
     * A constant {@link EmptyParameterProvider}.
     */
    public static final EmptyParameterProvider INSTANCE = new EmptyParameterProvider();


    /**
     * Standard Constructor.
     */
    public EmptyParameterProvider()
    {
        //deliberately empty
    }


    /**
     * {@inheritDoc}
     */
    public Parameter getParameter(String name) throws ClassCastException
    {
        //always returns null for a EmptyParameterProvider
        return null;
    }


    /**
     * {@inheritDoc}
     */
    public boolean isDefined(String name)
    {
        //always returns false for a EmptyParameterProvider
        return false;
    }


    /**
     * {@inheritDoc}
     */
    public boolean isEmpty()
    {
        return true;
    }


    /**
     * {@inheritDoc}
     */
    public int size()
    {
        return 0;
    }


    /**
     * {@inheritDoc}
     */
    public Iterator<Parameter> iterator()
    {
        return new Iterator<Parameter>()
        {

            /**
             * {@inheritDoc}
             */
            public boolean hasNext()
            {
                return false;
            }


            /**
             * {@inheritDoc}
             */
            public Parameter next()
            {
                return null;
            }


            /**
             * {@inheritDoc}
             */
            public void remove()
            {
                throw new UnsupportedOperationException("Can't remove a parameter from an EmptyParameterProvider");
            }
        };
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, ?> getParameters(ParameterProvider parameterProvider)
    {
        return Collections.EMPTY_MAP;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Properties getProperties(ParameterProvider parameterProvider)
    {
        return new Properties();
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
