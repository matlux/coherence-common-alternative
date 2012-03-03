/*
 * File: MacroParameterExpression.java
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
 * A {@link MacroParameterExpression} represents a Coherence {@link Expression} 
 * (ie: the use of a macro parameter) within something like a Coherence Cache Configuration file.
 * <p>
 * Macro Parameters are usually represented as follows.
 * <p>
 * <code>{parameter-name[ default-value]}</code>
 * <p>
 * When value of the macro containing a parameter-name is resolved by the {@link ParameterProvider}.  If it's 
 * resolvable by the {@link ParameterProvider} the value of the resolved parameter is returned.  If it's not,
 * the specified default value is returned.
 * <p>
 * NOTE: Unlike Coherence itself, this implementation supports multiple uses of macros.  eg:
 * <code>This {param1}-{param2}-{param3 default} is valid and resolvable.</code>
 * 
 * @author Brian Oliver
 */
@SuppressWarnings({ "serial" })
public class MacroParameterExpression implements Expression, ExternalizableLite, PortableObject
{

    /**
     * The expression to be evaluated.
     */
    private String expression;


    /**
     * Standard Constructor (required for {@link ExternalizableLite} and {@link PortableObject}).
     */
    public MacroParameterExpression()
    {
        //deliberately empty
    }


    /**
     * Standard Constructor.
     * 
     * @param expression A string representation of the {@link Expression}
     */
    public MacroParameterExpression(String expression)
    {
        this.expression = expression.trim();
    }


    /**
     * {@inheritDoc}
     */
    public Value evaluate(ParameterProvider parameterProvider)
    {
        //resolve the parameter
        Value result = null;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < expression.length(); i++)
        {
            if (expression.charAt(i) == '{')
            {
                String parameterName;
                String parameterDefaultValue;
                int defaultValuePos = expression.indexOf(" ", i + 1);
                if (defaultValuePos >= 0)
                {
                    parameterName = expression.substring(i + 1, defaultValuePos).trim();

                    int endMacroPos = expression.indexOf("}", defaultValuePos);
                    if (endMacroPos > defaultValuePos)
                    {
                        parameterDefaultValue = expression.substring(defaultValuePos, endMacroPos).trim();

                        i = endMacroPos;
                    }
                    else
                    {
                        throw new IllegalArgumentException(String.format(
                            "Invalid parameter macro definition in [%s].  Missing closing brace '}'.", expression));
                    }
                }
                else
                {
                    int endMacroPos = expression.indexOf("}", i + 1);
                    if (endMacroPos > i + 1)
                    {
                        parameterName = expression.substring(i + 1, endMacroPos).trim();
                        parameterDefaultValue = null;

                        i = endMacroPos;
                    }
                    else
                    {
                        throw new IllegalArgumentException(String.format(
                            "Invalid parameter macro definition in [%s].  Missing closing brace '}'.", expression));
                    }
                }

                if (parameterProvider.isDefined(parameterName))
                {
                    result = parameterProvider.getParameter(parameterName).evaluate(parameterProvider);
                }
                else if (parameterDefaultValue != null)
                {
                    result = new Value(parameterDefaultValue);
                }
                else
                {
                    //the parameter is unknown
                    throw new IllegalArgumentException(String.format(
                        "The specified parameter name '%s' in the macro parameter '%s' is unknown and not resolvable",
                        parameterName, expression));
                }

                if (builder.length() > 0)
                {
                    builder.append(result.getString());
                    result = null;
                }
            }
            else
            {
                if (result != null)
                {
                    builder.append(result.getString());
                    result = null;
                }

                if (expression.startsWith("\\{", i))
                {
                    builder.append("{");
                    i++;
                }
                else if (expression.startsWith("\\}", i))
                {
                    builder.append("}");
                    i++;
                }
                else
                {
                    builder.append(expression.charAt(i));
                }
            }
        }

        return result == null ? new Value(builder.toString()) : result;
    }


    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        return String.format("MacroParameterExpression{expression=%s}", expression);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSerializable()
    {
        //macro parameter expressions are always serializable as they are represented as strings.
        return true;
    }
    
    
    /**
     * {@inheritDoc}
     */
    public void readExternal(DataInput in) throws IOException
    {
        this.expression = ExternalizableHelper.readSafeUTF(in);
    }


    /**
     * {@inheritDoc}
     */
    public void writeExternal(DataOutput out) throws IOException
    {
        ExternalizableHelper.writeSafeUTF(out, expression);
    }


    /**
     * {@inheritDoc}
     */
    public void readExternal(PofReader reader) throws IOException
    {
        this.expression = reader.readString(1);
    }


    /**
     * {@inheritDoc}
     */
    public void writeExternal(PofWriter writer) throws IOException
    {
        writer.writeString(1, expression);
    }
}
