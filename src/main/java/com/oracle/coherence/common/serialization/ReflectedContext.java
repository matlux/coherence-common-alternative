/*
 * File: ReflectedContext.java
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

import com.oracle.coherence.common.serialization.annotations.PofType;
import com.tangosol.io.pof.ConfigurablePofContext;
import com.tangosol.io.pof.PofContext;

/**
 * A {@link ReflectedContext} is a context of {@link DefaultReflectedSerializer}s that is built-up
 * through the runtime reflection of {@link PofType}s.
 * <p>
 * Note 1: Unlike the {@link ConfigurablePofContext}, you should never directly configure an instance of this class as a
 * Pof Serializer. Instead you should use the {@link ReflectiveSerializer} that will manage instances of this class for
 * you.
 * <p>
 * Note 2: Although like a {@link PofContext}, a {@link ReflectedContext} does not implement Serializer.
 *
 * @author Charlie Helin
 * @author Brian Oliver
 */
public interface ReflectedContext
{

    /**
     * Retrieves the {@link ReflectedSerializer} from the
     * {@link ReflectedContext} based on the specified userTypeId.
     *
     * @param userTypeId The User Type Identifier
     *
     * @return The {@link ReflectedSerializer} for the userTypeId
     *
     * @throws IllegalArgumentException If the specified userTypeId is unknown
     *             to the {@link ReflectedContext}.
     */
    public ReflectedSerializer getPofSerializer(int userTypeId);


    /**
     * Retrieves the User Type Identifier for the {@link DefaultReflectedSerializer} associated with the
     * specified {@link Object}.
     *
     * @param object The {@link Object}
     *
     * @return The User Type Identifier
     *
     * @throws IllegalArgumentException If the specified {@link Object} class is unknown to the {@link ReflectedContext}.
     */
    public int getUserTypeIdentifier(Object object);


    /**
     * Retrieves the User Type Identifier for the {@link DefaultReflectedSerializer} associated with the
     * specified {@link Class}.
     *
     * @param clazz The {@link Class}
     *
     * @return The User Type Identifier
     *
     * @throws IllegalArgumentException If the specified {@link Class} is unknown to the {@link ReflectedContext}.
     */
    public int getUserTypeIdentifier(Class<?> clazz);


    /**
     * Retrieves the User Type Identifier for the {@link DefaultReflectedSerializer} associated with the
     * specified class name
     *
     * @param className The name of the {@link Class}
     *
     * @return The User Type Identifier
     *
     * @throws IllegalArgumentException If the specified class name is unknown to the {@link ReflectedContext}.
     */
    public int getUserTypeIdentifier(String className);


    /**
     * Retrieves the name of the {@link Class} associated with the specified User Type Identifier.
     *
     * @param userTypeId The User Type Identifier
     *
     * @return The class name associated with the userTypeId
     *
     * @throws IllegalArgumentException If the specified userTypeId is unknown to the {@link ReflectedContext}.
     */
    public String getClassName(int userTypeId);


    /**
     * Retrieves the {@link Class} associated with the specified User Type Identifier.
     *
     * @param userTypeId The User Type Identifier
     *
     * @return The {@link Class} associated with the userTypeId
     *
     * @throws IllegalArgumentException If the specified userTypeId is unknown to the {@link ReflectedContext}.
     */
    public Class<?> getClass(int userTypeId);


    /**
     * Retrieves the {@link ReflectedSerializer} associated with the specified
     * {@link Class}.
     * 
     * @param clazz The {@link Class}
     * 
     * @return the {@link ReflectedSerializer} used to serialize the type or
     *         null if no serializer could be found
     */
    public ReflectedSerializer getTypeSerializer(Class<?> clazz);


    /**
     * Determines if a {@link DefaultReflectedSerializer} can be associated to specified {@link Object} in
     * the {@link ReflectedContext}.
     *
     * @param object The {@link Object}
     *
     * @return <code>true</code> if an associated {@link DefaultReflectedSerializer} is known to the
     *         {@link DefaultReflectedSerializer}, <code>false</code> otherwise.
     */
    public boolean isUserType(Object object);


    /**
     * Determines if a {@link DefaultReflectedSerializer} is associated with specified {@link Class} in
     * the {@link ReflectedContext}.
     *
     * @param clazz The {@link Class}
     *
     * @return <code>true</code> if an associated {@link DefaultReflectedSerializer} is known to the
     *         {@link DefaultReflectedSerializer}, <code>false</code> otherwise.
     */
    public boolean isUserType(Class<?> clazz);


    /**
     * Determines if a {@link DefaultReflectedSerializer} is associated with specified class name in
     * the {@link ReflectedContext}.
     *
     * @param className The name of the {@link Class}.
     *
     * @return <code>true</code> if an associated {@link DefaultReflectedSerializer} is known to the
     *         {@link DefaultReflectedSerializer}, <code>false</code> otherwise.
     */
    public boolean isUserType(String className);


    /**
     * Ensures that a {@link ReflectedSerializer} for the specified type is
     * available in the {@link ReflectedContext}. If one is not (as the type has
     * not been previously seen by the {@link ReflectedContext}), reflection is
     * used to generate an appropriate {@link ReflectedSerializer}. The newly
     * generated {@link ReflectedContext} is then remembered for later retrieval
     * via the {@link ReflectedContext}.
     *
     * @param type The type for which to determine the
     *            {@link ReflectedSerializer}
     * @param pofContext a Base context used to locate information about an
     *            unknown type
     * @return the TypeMetaInfo representing the type
     */
    public ReflectedSerializer ensurePofSerializer(Class<?> type,
                                                   PofContext pofContext);


    /**
     * The {@link FieldSerializationProvider} associated with this context to serialize {@link Field}s.
     *
     * @return the {@link FieldSerializationProvider}
     */
    public FieldSerializationProvider getFieldSerializationProvider();
}