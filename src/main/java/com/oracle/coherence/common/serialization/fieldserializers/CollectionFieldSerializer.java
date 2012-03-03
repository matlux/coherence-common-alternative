/*
 * File: CollectionFieldSerializer.java
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
import java.util.Collection;

import com.oracle.coherence.common.logging.Logger;
import com.oracle.coherence.common.serialization.FieldSerializer;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;

/**
 * A {@link CollectionFieldSerializer} is a {@link FieldSerializer} for {@link Collection}s.  
 * <p>
 * Importantly all effort is made to ensure type-safety of the collections used, including their
 * generic type arguments by either using the type information declared by the {@link Field} or
 * using a specified preferred type.
 *
 * @author Charlie Helin
 */
public final class CollectionFieldSerializer extends AbstractGenericFieldSerializer
{

    /**
     * Standard Constructor.
     * 
     * @param field         The {@link Field} containing the {@link Collection} to serialize.
     * @param preferredType The preferred type of {@link Collection} to instantiate when deserializing.
     */
    public CollectionFieldSerializer(Field field,
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
        //determine the existing collection (we'll populate this if there is one)
        Collection<?> collection = (Collection<?>) field.get(object);

        if (collection == null)
        {
            //attempt to create a new collection of the appropriate type
            try
            {
                collection = (Collection<?>) constructor.newInstance();
            }
            catch (Exception e)
            {
                Logger.log(Logger.ERROR, "Failed to create a new instance (%s): %s", collection, e.getMessage());
                throw new IllegalStateException(e);
            }
        }

        field.set(object, reader.readCollection(index, collection));
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
        //when we have concrete type information we can write out a uniform collection (more efficient)
        if (typeArguments == null)
        {
            writer.writeCollection(index, (Collection<?>) field.get(object));
        }
        else
        {
            writer.writeCollection(index, (Collection<?>) field.get(object), (Class<?>) typeArguments[0]);
        }
    }
}
