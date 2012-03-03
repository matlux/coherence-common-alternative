/*
 * File: ObjectArrayFieldSerializer.java
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

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import com.oracle.coherence.common.serialization.FieldSerializer;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;

/**
 * An {@link ObjectArrayFieldSerializer} is a {@link FieldSerializer} for {@link Object} arrays.
 *
 * @author Charlie Helin
 */
public final class ObjectArrayFieldSerializer implements FieldSerializer
{

    /**
     * The desired component {@link Type} of arrays produced by this {@link FieldSerializer}.
     */
    private Class<?> componentType;


    /**
     * Standard Constructor
     *
     * @param componentType The desired component type of arrays produced by this {@link FieldSerializer}.
     */
    public ObjectArrayFieldSerializer(Class<?> componentType)
    {
        this.componentType = componentType;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readField(Object object,
                          Field field,
                          PofReader reader,
                          int index) throws IllegalArgumentException, IllegalAccessException, IOException
    {
        field.set(object, reader.readObjectArray(index, (Object[]) Array.newInstance(componentType, 0)));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeField(Object object,
                           Field field,
                           PofWriter writer,
                           int index) throws IllegalArgumentException, IOException, IllegalAccessException
    {
        writer.writeObjectArray(index, (Object[]) field.get(object));
    }
}
