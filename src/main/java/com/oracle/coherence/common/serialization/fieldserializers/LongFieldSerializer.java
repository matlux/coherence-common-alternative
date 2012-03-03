/*
 * File: LongFieldSerializer.java
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

import com.oracle.coherence.common.serialization.FieldSerializer;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * A {@link LongFieldSerializer} is a {@link FieldSerializer} for {@link Long}s.
 *
 * @author Charlie Helin
 */
public final class LongFieldSerializer implements FieldSerializer
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void readField(Object object,
                          Field field,
                          PofReader reader,
                          int index) throws IllegalArgumentException, IllegalAccessException, IOException
    {
        field.setLong(object, reader.readLong(index));
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
        writer.writeLong(index, field.getLong(object));
    }
}
