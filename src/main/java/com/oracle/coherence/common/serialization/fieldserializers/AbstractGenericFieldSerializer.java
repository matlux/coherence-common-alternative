/*
 * File: AbstractGenericFieldSerializer.java
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
package com.oracle.coherence.common.serialization.fieldserializers;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

import com.oracle.coherence.common.logging.Logger;
import com.oracle.coherence.common.serialization.FieldSerializer;

/**
 * An {@link AbstractGenericFieldSerializer} is a {@link FieldSerializer} that captures the
 * runtime type information of a {@link Field} that is declared as a generic type, but 
 * potentially with concrete parameters.  This information is encapsulated in instances
 * of this {@link FieldSerializer} so that sub-classes may optimally serialize, deserialize
 * and instantiate concrete instances of the generic type. 
 *
 * @author Charlie Helin
 */
public abstract class AbstractGenericFieldSerializer implements FieldSerializer
{

    /**
     * The arguments for the default constructor.
     */
    private static final Class<?>[] DEFAULT_PARAMETER_TYPES = new Class<?>[] {};

    /**
     * The constructor to use for {@link FieldSerializer}.
     */
    protected Constructor<? extends Object> constructor;

    /**
     * The resolved concrete type arguments for the constructor or <code>null</code> if the type
     * arguments are not all concrete.
     */
    protected Type[] typeArguments;


    /**
     * Given a {@link Field} that is declared as a generic type and an optionally specified
     * preferred concrete type of the said {@link Field} (set to {@link Object}.class if not used), 
     * this constructor determines the underlying concrete parameter types of the generically declared {@link Field} 
     * together with an appropriate constructor to use for instantiating a suitable representation
     * of the said {@link Field}. 
     *
     * @param field         
     *              The generic {@link Field}
     * @param preferredType 
     *              The preferred concrete type to use for instantiating a representation of the {@link Field}.
     *              (set to {@link Object}.class if the declared type of the {@link Field} should be used instead).
     */
    protected AbstractGenericFieldSerializer(Field field,
                                             Class<?> preferredType)
    {
        if (field.getGenericType() instanceof ParameterizedType)
        {
            typeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();

            for (int i = 0; i < typeArguments.length; i++)
            {
                Class<?> concreteClass = getConcreteClass(typeArguments[i]);
                if (concreteClass == null)
                {
                    typeArguments = null;
                    break;
                }
                else
                {
                    typeArguments[i] = concreteClass;
                }
            }
        }

        Class<?> declaredType = field.getType();
        try
        {
            constructor = preferredType.isInterface() || preferredType.isAssignableFrom(Object.class) ? declaredType
                .isInterface()
                    ? new HashMap<Object, Object>().getClass().getConstructor(DEFAULT_PARAMETER_TYPES) : declaredType
                        .getConstructor(DEFAULT_PARAMETER_TYPES) : preferredType
                .getConstructor(DEFAULT_PARAMETER_TYPES);
        }
        catch (Exception e)
        {
            Logger.log(Logger.ERROR, "Cannot locate constructor for %s (%s)", preferredType, e.getMessage());
            throw new IllegalStateException(e);
        }
    }


    /**
     * Determines the concrete {@link Class} for a given {@link Type}.
     *
     * @param type The Type
     * 
     * @return A concrete (that may be instantiated) {@link Class} or <code>null</code> if the type is an 
     *         interface or variable.
     */
    protected Class<?> getConcreteClass(Type type)
    {
        if (type instanceof Class<?>)
        {
            return ((Class<?>) type).isInterface() ? null : (Class<?>) type;
        }
        else if (type instanceof ParameterizedType)
        {
            return getConcreteClass(((ParameterizedType) type).getRawType());
        }
        else if (type instanceof GenericArrayType)
        {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            Class<?> componentClass = getConcreteClass(componentType);
            return componentClass != null ? Array.newInstance(componentClass, 0).getClass() : null;
        }
        else
        {
            return null;
        }
    }
}