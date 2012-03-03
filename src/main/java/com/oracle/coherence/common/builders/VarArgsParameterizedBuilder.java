/*
 * File: VarArgsParameterizedBuilder.java
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
package com.oracle.coherence.common.builders;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oracle.coherence.common.util.ReflectionHelper;
import com.oracle.coherence.configuration.parameters.ParameterProvider;
import com.tangosol.io.ExternalizableLite;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.Base;
import com.tangosol.util.ExternalizableHelper;

/**
 * A {@link VarArgsParameterizedBuilder} is an implementation of an {@link ParameterizedBuilder} that uses a specified
 * class name and set of constructor arguments to realize an instance of a class.
 * 
 * @author Brian Oliver
 */
@SuppressWarnings("serial")
public class VarArgsParameterizedBuilder<T> extends AbstractClassLoaderAwareParameterizedBuilder<T> implements
        ExternalizableLite, PortableObject
{

    /**
     * <p>The {@link Logger} for this class.</p>
     */
    private static final Logger logger = Logger.getLogger(VarArgsParameterizedBuilder.class.getName());

    /**
     * <p>The name of the class to create when this {@link VarArgsParameterizedBuilder} is realized.</p>
     */
    private String className;

    /**
     * <p>The parameters for the class constructor.</p>
     * 
     * <p>NOTE: Parameters may also be instances of Schemes, in which case they may be realized.</p>
     */
    private Object[] constructorParameters;


    /**
     * Standard Constructor. (Required for {@link ExternalizableLite} and {@link PortableObject}).
     */
    public VarArgsParameterizedBuilder()
    {
        //SKIP: deliberately empty
    }


    /**
     * Standard Constructor.
     * 
     * @param className The name of the class that will be created when this {@link VarArgsParameterizedBuilder} is realized.
     * @param constructorParameters The constructor parameters for realizing the class.
     */
    public VarArgsParameterizedBuilder(String className,
                                       Object... constructorParameters)
    {
        this.className = className;
        this.constructorParameters = constructorParameters;
    }


    /**
     * Standard Constructor.
     * 
     * @param clazz The {@link Class} that will be created when this {@link VarArgsParameterizedBuilder} is realized.
     * @param constructorParameters The constructor parameters for realizing the class.
     */
    public VarArgsParameterizedBuilder(Class<?> clazz,
                                       Object... constructorParameters)
    {
        this.className = clazz.getName();
        this.constructorParameters = constructorParameters;
    }


    /**
     * {@inheritDoc}
     */
    public boolean realizesClassOf(Class<?> clazz,
                                   ParameterProvider parameterProvider)
    {
        try
        {
            //load the parameterized class, but don't initialize it
            Class<?> parameterizedClazz = Class.forName(className, false, getContextClassLoader());

            //is the class the same or a super class/interface of the parameterized Clazz
            return clazz.isAssignableFrom(parameterizedClazz);
        }
        catch (ClassNotFoundException classNotFoundException)
        {
            if (logger.isLoggable(Level.WARNING))
            {
                logger.log(Level.WARNING, String.format(
                    "Class %s not found while attempting to determine type information of a ReflectiveScheme",
                    className), classNotFoundException);
            }

            return false;
        }
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T realize(ParameterProvider parameterProvider)
    {
        try
        {
            //ensure that each of the ClassScheme parameters are realized
            Object[] parameters = new Object[constructorParameters.length];
            for (int i = 0; i < constructorParameters.length; i++)
            {
                if (constructorParameters[i] instanceof ParameterizedBuilder<?>)
                {
                    parameters[i] = ((ParameterizedBuilder<Object>) constructorParameters[i])
                        .realize(parameterProvider);
                }
                else
                {
                    parameters[i] = constructorParameters[i];
                }
            }

            return (T) ReflectionHelper.createObject(className, parameters, this.getClass().getClassLoader());
        }
        catch (Exception exception)
        {
            throw Base.ensureRuntimeException(exception);
        }
    }


    /**
     * {@inheritDoc}
     */
    public void readExternal(DataInput in) throws IOException
    {
        this.className = ExternalizableHelper.readSafeUTF(in);
        int parameterCount = ExternalizableHelper.readInt(in);
        this.constructorParameters = new Object[parameterCount];
        for (int i = 0; i < parameterCount; i++)
        {
            this.constructorParameters[i] = ExternalizableHelper.readObject(in);
        }
    }


    /**
     * {@inheritDoc}
     */
    public void writeExternal(DataOutput out) throws IOException
    {
        ExternalizableHelper.writeSafeUTF(out, className);
        ExternalizableHelper.writeInt(out, constructorParameters.length);
        for (int i = 0; i < constructorParameters.length; i++)
        {
            ExternalizableHelper.writeObject(out, constructorParameters[i]);
        }
    }


    /**
     * {@inheritDoc}
     */
    public void readExternal(PofReader reader) throws IOException
    {
        this.className = reader.readString(1);
        int parameterCount = reader.readInt(2);
        this.constructorParameters = new Object[parameterCount];
        for (int i = 0; i < parameterCount; i++)
        {
            this.constructorParameters[i] = reader.readObject(3 + i);
        }

    }


    /**
     * {@inheritDoc}
     */
    public void writeExternal(PofWriter writer) throws IOException
    {
        writer.writeString(1, className);
        writer.writeInt(2, constructorParameters.length);
        for (int i = 0; i < constructorParameters.length; i++)
        {
            writer.writeObject(3 + i, constructorParameters[i]);
        }
    }
}
