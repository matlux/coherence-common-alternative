/*
 * File: MapFieldSerializer.java
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
import java.lang.reflect.Field;
import java.util.Map;

import com.oracle.coherence.common.logging.Logger;
import com.oracle.coherence.common.serialization.FieldSerializer;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;

/**
 * A {@link MapFieldSerializer} is a {@link FieldSerializer} for {@link Map}s.  
 * <p>
 * Importantly all effort is made to ensure type-safety of the {@link Map}s used, including their
 * generic type arguments by either using the type information declared by the {@link Field} or
 * using a specified preferred type.
 *
 * @author Charlie Helin
 */
public final class MapFieldSerializer extends AbstractGenericFieldSerializer
{

    /**
     * Standard Constructor.
     * 
     * @param field         The {@link Field} containing the {@link Map} to serialize.
     * @param preferredType The preferred type of {@link Map} to instantiate when deserializing.
     */
    public MapFieldSerializer(Field field,
                              Class<?> preferredType)
    {
        super(field, preferredType);
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
        //determine the existing map (we'll populate this if there is one)
        Map<?, ?> map = (Map<?, ?>) field.get(object);

        if (map == null)
        {
            //attempt to create a new map of the appropriate type
            try
            {
                map = (Map<?, ?>) constructor.newInstance();
            }
            catch (Exception e)
            {
                Logger.log(Logger.ERROR, "Failed to create new instance of a map for %s", field);
                throw new IllegalStateException(e);
            }
        }

        field.set(object, reader.readMap(index, map));
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
        //when we have concrete type information we can write out a uniform map (more efficient)
        if (typeArguments == null)
        {
            writer.writeMap(index, (Map<?, ?>) field.get(object));
        }
        else
        {
            writer.writeMap(index, (Map<?, ?>) field.get(object), (Class<?>) typeArguments[0],
                (Class<?>) typeArguments[1]);
        }
    }
}
