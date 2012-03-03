/*
 * File: Parameter.java
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

import com.oracle.coherence.common.util.Value;
import com.oracle.coherence.configuration.expressions.Constant;
import com.oracle.coherence.configuration.expressions.Expression;
import com.tangosol.io.ExternalizableLite;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.ExternalizableHelper;

/**
 * <p>A {@link Parameter} represents an optionally named and optionally typed {@link Expression}.</p>
 *
 * @author Brian Oliver
 */
@SuppressWarnings("serial")
public class Parameter implements Expression, ExternalizableLite, PortableObject
{

    /**
     * <p>The name of the {@link Parameter}.<p>
     */
    private String name;

    /**
     * <p>(optional)The expected type (as a fully qualified class name) of the 
     * {@link Parameter} when evaluated.</p>
     * 
     * <p>NOTE: when <code>null</code> a type has not been specified.</p>
     */
    private String type;

    /**
     * <p>The {@link Expression} of the {@link Parameter} that when evaluated will produce
     * a {@link Value}.
     */
    private Expression expression;


    /**
     * Standard Constructor (required for {@link ExternalizableLite} and {@link PortableObject}).
     */
    public Parameter()
    {
        //deliberately empty
    }


    /**
     * <p>Standard Constructor.</p>
     * 
     * @param name The name of the {@link Parameter}
     * @param type The expected type of the {@link Parameter}
     * @param expression The {@link Expression} for the {@link Parameter}
     */
    public Parameter(String name,
                     String type,
                     Expression expression)
    {
        this.name = name;
        this.type = type;
        this.expression = expression;
    }


    /**
     * <p>Standard Constructor (with no explicitly declared type).</p>
     * 
     * @param name The name of the {@link Parameter}
     * @param expression The {@link Expression} for the {@link Parameter}
     */
    public Parameter(String name,
                     Expression expression)
    {
        this.name = name;
        this.type = null;
        this.expression = expression;
    }


    /**
     * <p>Standard Constructor (for a {@link Boolean} parameter).</p>
     * 
     * @param name The name of the parameter
     * @param value The value for the parameter
     */
    public Parameter(String name,
                     boolean value)
    {
        this.name = name;
        this.type = Boolean.TYPE.getName();
        this.expression = new Constant(value);
    }


    /**
     * <p>Standard Constructor (for an {@link Object} parameter).</p>
     * 
     * @param name The name of the parameter
     * @param value The value for the parameter
     */
    public Parameter(String name,
                     Object value)
    {
        this.name = name;
        this.type = null;
        this.expression = new Constant(value);
    }


    /**
     * <p>Standard Constructor (for a {@link String} parameter).</p>
     * 
     * @param name The name of the parameter
     * @param value The value for the parameter
     */
    public Parameter(String name,
                     String value)
    {
        this.name = name;
        this.type = String.class.getName();
        this.expression = new Constant(value);
    }


    /**
     * <p>Standard Constructor (for a {@link Value} parameter).</p>
     * 
     * @param name The name of the parameter
     * @param type The expected type of the parameter (may be null if unknown)
     * @param value The value for the parameter
     */
    public Parameter(String name,
                     String type,
                     Value value)
    {
        this.name = name;
        this.type = type;
        this.expression = new Constant(value);
    }


    /**
     * <p>Returns the name of the {@link Parameter}.</p>
     * 
     * @return A {@link String} representing the name of the {@link Parameter}
     */
    public String getName()
    {
        return name;
    }


    /**
     * <p>Determines the expected type of the {@link Parameter} (as a fully qualified class name).</p>
     * 
     * @return A {@link String} representing the type of the {@link Parameter}
     */
    public String getType()
    {
        return type;
    }


    /**
     * <p>Determines if an expected/actual type of the {@link Parameter} has been specified/is known.</p>
     * 
     * @return <code>true</code> if the type of the {@link Parameter} is known/specified.  ie: {@link #getType()}
     *         is not <code>null</code>, otherwise returns <code>false</code>.
     */
    public boolean isStronglyTyped()
    {
        return type != null;
    }

    
    /**
     * Determines if all of the members of the {@link Parameter} are serializable.
     * 
     * @return <code>true</code> if all of the members of the {@link Parameter} are serializable, <code>false</code>
     *         otherwise.
     */
    public boolean isSerializable()
    {
        return expression.isSerializable();
    }

    /**
     * <p>Determines the {@link Expression} for the {@link Parameter}.</p>
     * 
     * @return The {@link Expression} for the {@link Parameter}
     */
    public Expression getExpression()
    {
        return expression;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Value evaluate(ParameterProvider parameterProvider)
    {
        return expression.evaluate(parameterProvider);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readExternal(DataInput in) throws IOException
    {
        this.name = ExternalizableHelper.readSafeUTF(in);
        this.type = ExternalizableHelper.readSafeUTF(in);
        this.expression = (Expression) ExternalizableHelper.readObject(in);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeExternal(DataOutput out) throws IOException
    {
        ExternalizableHelper.writeSafeUTF(out, name);
        ExternalizableHelper.writeSafeUTF(out, type);
        ExternalizableHelper.writeObject(out, expression);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readExternal(PofReader reader) throws IOException
    {
        this.name = reader.readString(1);
        this.type = reader.readString(2);
        this.expression = (Expression) reader.readObject(3);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeExternal(PofWriter writer) throws IOException
    {
        writer.writeString(1, name);
        writer.writeString(2, type);
        writer.writeObject(3, expression);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        String result = "";
        if (name != null)
        {
            result += "name=" + getName();
        }
        if (type != null)
        {
            result += (result.isEmpty() ? "" : ", ") + "type=" + getType();
        }
        result += (result.isEmpty() ? "" : ", ") + "expression=" + getExpression();

        return "Parameter{" + result + "}";
    }
}