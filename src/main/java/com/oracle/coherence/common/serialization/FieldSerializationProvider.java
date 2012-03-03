/*
 * File: FieldSerializerProvider.java
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

import java.lang.reflect.Field;

/**
 * A {@link FieldSerializationProvider} provides a mechanism to locate an appropriate {@link FieldSerializer} for
 * a given {@link Field}.
 *
 * @author Charlie Helin
 */
public interface FieldSerializationProvider
{

    /**
     * Determines and returns the {@link FieldSerializer} for the specified {@link Field} or <code>null</code> if
     * an appropriate {@link FieldSerializer} can not be located.
     *  
     * @param field         The {@link Field} for which a {@link FieldSerializer} is required.
     * @param preferredType (optional) When specified, this type should be used to determine the 
     *                      {@link FieldSerializer}.  Otherwise the type of the {@link Field} itself should be used.
     * 
     * @return The {@link FieldSerializer} for the specified {@link Field} or <code>null</code> 
     *         if one is not available.
     */
    public FieldSerializer getFieldSerializer(Field field,
                                              Class<?> preferredType);
}