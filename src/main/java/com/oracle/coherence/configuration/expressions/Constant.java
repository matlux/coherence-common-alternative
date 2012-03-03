/*
 * File: Constant.java
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
package com.oracle.coherence.configuration.expressions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.oracle.coherence.common.util.Value;
import com.oracle.coherence.configuration.parameters.ParameterProvider;
import com.tangosol.io.ExternalizableLite;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.ExternalizableHelper;

/**
 * A {@link Constant} represents a simple {@link Value} as an {@link Expression}.
 * <p>
 * {@link Constant}s are useful {@link Expression} substitutes when only a single {@link Value} is 
 * required and you don't want to create an entire {@link Expression}.
 * 
 * @author Brian Oliver
 */
@SuppressWarnings("serial")
public class Constant implements Expression, ExternalizableLite, PortableObject
{

    /**
     * The {@link Value} of the constant.
     */
    private Value value;


    /**
     * Standard Constructor (required for {@link ExternalizableLite} and {@link PortableObject}).
     */
    public Constant()
    {
        //deliberately empty
    }


    /**
     * Standard Constructor.
     * 
     * @param value The {@link Value} of the constant
     */
    public Constant(Value value)
    {
        this.value = value;
    }


    /**
     * Standard Constructor.
     * 
     * @param <T> the type of the constant
     * @param value The value of the constant
     */
    public <T> Constant(T value)
    {
        this.value = new Value(value);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Value evaluate(ParameterProvider parameterProvider)
    {
        return value;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return String.format("Constant{value=%s}", value);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSerializable()
    {
        return value == null || value.isSerializable();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readExternal(DataInput in) throws IOException
    {
        this.value = (Value) ExternalizableHelper.readObject(in);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeExternal(DataOutput out) throws IOException
    {
        ExternalizableHelper.writeObject(out, value);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readExternal(PofReader reader) throws IOException
    {
        this.value = (Value) reader.readObject(1);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeExternal(PofWriter writer) throws IOException
    {
        writer.writeObject(1, value);
    }
}
