/*
 * File: FieldSerializer.java
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
package com.oracle.coherence.common.serialization;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * A {@link FieldSerializer} provides the ability to read and write a specific {@link Field} of an 
 * {@link Object} to and from a {@link PortableObject} stream using a {@link PofReader} and {@link PofWriter}  
 * respectively.
 * <p>
 * Classes implementing this interface are designed to provide low-level strongly-typed {@link Field}-based 
 * serialization to support higher-level serialization like that provided by the {@link DefaultReflectedSerializer}s.
 * <p>
 * In general, the only time you will ever need to implement this interface is when you wish to provide 
 * serialization support for a new primitive type of serializer.
 * 
 * @author Charlie Helin
 */
public interface FieldSerializer
{

    /**
     * Writes the specified {@link Field} of the specified {@link Object} to the provided {@link PofWriter} at the
     * specified index.
     * 
     * @param object    The {@link Object} containing the {@link Field} to write
     * @param field     The {@link Field} to write
     * @param writer    The {@link PofWriter} in which to write the {@link Field} value
     * @param index     The index in the {@link PofWriter} to write the value
     * @throws IllegalArgumentException
     * @throws IOException
     * @throws IllegalAccessException
     */
    public void writeField(Object object,
                           Field field,
                           PofWriter writer,
                           int index) throws IllegalArgumentException, IOException, IllegalAccessException;


    /**
     * Reads and assigns the specified {@link Field} of the specified {@link Object} from the provided {@link PofReader}
     * from the specified index.
     * 
     * @param object    The {@link Object} in which to assign the read {@link Field}
     * @param field     The {@link Field} to assign in the {@link Object}
     * @param reader    The {@link PofReader} from which to read the {@link Field} value
     * @param index     The index in the {@link PofReader} from which to read the value
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws IOException
     */
    void readField(Object object,
                   Field field,
                   PofReader reader,
                   int index) throws IllegalArgumentException, IllegalAccessException, IOException;
}
