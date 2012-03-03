/*
 * File: ScopedParameterProvider.java
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.ExternalizableHelper;

/**
 * A {@link ScopedParameterProvider} is a {@link MutableParameterProvider} that wraps/nests an inner
 * {@link ParameterProvider} to provide the notion of scoping providers.  That is, should a {@link Parameter} not be 
 * defined by the {@link ScopedParameterProvider} then the inner/wrapped {@link ParameterProvider} is consulted.  
 * When adding {@link Parameter}s, they are only added to the {@link ScopedParameterProvider} and not the 
 * inner/wrapped {@link ParameterProvider}.
 * <p>
 * Hence this permits the scoping and hiding of {@link Parameter} definitions as inner
 * {@link ParameterProvider}s are only consulted when the outer {@link ScopedParameterProvider} does not know
 * of a {@link Parameter}.
 * <p>
 * NOTE: {@link ScopedParameterProvider}s may wrap/nest other {@link ScopedParameterProvider}s, thus
 * it's possible to create any number of layers of wrapping/nesting or {@link ParameterProvider}s.
 *
 * @author Brian Oliver
 */
@SuppressWarnings("serial")
public class ScopedParameterProvider implements MutableParameterProvider, ExternalizableLite, PortableObject
{

    /**
     * The {@link ParameterProvider} for this level.
     */
    private MutableParameterProvider parameterProvider;

    /**
     * The inner/wrapped {@link ParameterProvider}.
     */
    private ParameterProvider innerParameterProvider;


    /**
     * Standard Constructor.
     * 
     * @param parameterProvider The {@link ParameterProvider} to wrap/nest.
     */
    public ScopedParameterProvider(ParameterProvider parameterProvider)
    {
        this.parameterProvider = new SimpleParameterProvider();
        this.innerParameterProvider = parameterProvider;
    }


    /**
     * Standard Constructor (nesting a {@link EmptyParameterProvider}).
     */
    public ScopedParameterProvider()
    {
        this(EmptyParameterProvider.INSTANCE);
    }


    /**
     * {@inheritDoc}
     */
    public void addParameter(Parameter parameter)
    {
        parameterProvider.addParameter(parameter);
    }


    /**
     * {@inheritDoc}
     */
    public Parameter getParameter(String name) throws ClassCastException
    {
        if (parameterProvider.isDefined(name))
        {
            return parameterProvider.getParameter(name);
        }
        else
        {
            return innerParameterProvider.getParameter(name);
        }
    }


    /**
     * {@inheritDoc}
     */
    public boolean isDefined(String name)
    {
        return parameterProvider.isDefined(name) || innerParameterProvider.isDefined(name);
    }


    /**
     * {@inheritDoc}
     */
    public boolean isEmpty()
    {
        return innerParameterProvider.isEmpty() && parameterProvider.isEmpty();
    }


    /**
     * {@inheritDoc}
     */
    public int size()
    {
        return innerParameterProvider.size() + parameterProvider.size();
    }


    /**
     * {@inheritDoc}
     */
    public Iterator<Parameter> iterator()
    {
        LinkedHashMap<String, Parameter> parameters = new LinkedHashMap<String, Parameter>();

        //add all of the current (outer) parameters to the map
        for (Parameter parameter : parameterProvider)
        {
            parameters.put(parameter.getName(), parameter);
        }

        //add all visible (not already existing in the map) parameters from the inner scope to the map 
        for (Parameter parameter : innerParameterProvider)
        {
            if (!parameters.containsKey(parameter.getName()))
            {
                parameters.put(parameter.getName(), parameter);
            }
        }

        return parameters.values().iterator();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, ?> getParameters(ParameterProvider parameterProvider)
    {
        HashMap<String, Object> parameters = new HashMap<String, Object>();

        for (Parameter parameter : this)
        {
            Object value;
            if (parameter.isStronglyTyped())
            {
                //TODO: use the "getValue(...)" with the parameter type to ensure type-safety 
                value = parameter.getExpression().evaluate(parameterProvider).getObject();
            }
            else
            {
                value = parameter.getExpression().evaluate(parameterProvider).getObject();
            }
            parameters.put(parameter.getName(), value);
        }

        return parameters;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Properties getProperties(ParameterProvider parameterProvider)
    {
        Properties properties = new Properties();

        for (Parameter parameter : this)
        {
            properties.put(parameter.getName(), parameter.getExpression().evaluate(parameterProvider).getString());
        }
        return properties;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readExternal(DataInput in) throws IOException
    {
        this.innerParameterProvider = (ParameterProvider) ExternalizableHelper.readObject(in);
        this.parameterProvider = (MutableParameterProvider) ExternalizableHelper.readObject(in);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeExternal(DataOutput out) throws IOException
    {
        ExternalizableHelper.writeObject(out, innerParameterProvider);
        ExternalizableHelper.writeObject(out, parameterProvider);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readExternal(PofReader reader) throws IOException
    {
        this.innerParameterProvider = (ParameterProvider) reader.readObject(1);
        this.parameterProvider = (MutableParameterProvider) reader.readObject(2);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeExternal(PofWriter writer) throws IOException
    {
        writer.writeObject(1, innerParameterProvider);
        writer.writeObject(2, parameterProvider);
    }


    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        return String.format("ScopedParameterProvider{parameterProvider=%s, innerParameterProvider=%s}",
            parameterProvider, innerParameterProvider);
    }
}
