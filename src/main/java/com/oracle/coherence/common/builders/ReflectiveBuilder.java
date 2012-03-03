/*
 * File: ReflectiveBuilder.java
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

import com.oracle.coherence.configuration.parameters.ParameterProvider;

/**
 * A {@link ReflectiveBuilder} enables runtime type information to be determined about the instance of objects
 * a {@link Builder} may produce.  
 * <p>
 * This interface essentially provides us with the ability to determine the actual type of objects
 * a {@link Builder} may realize at runtime, without needing to realize an object.
 *
 * @author Mark Johnson
 * @author Brian Oliver
 */
public interface ReflectiveBuilder<T> extends Builder<T>
{

    /**
     * Returns if the implementing class (of builder) will realize instances of the specified {@link Class}.
     * 
     * @param clazz             The {@link Class} that we expect may be realized by the {@link ReflectiveBuilder}
     * @param parameterProvider The {@link ParameterProvider} providing 
     *                          {@link com.oracle.coherence.configuration.parameters.Parameter}s possibly used by the builder.
     * @return <code>true</code> if the specified {@link Class} may be realized by the {@link ReflectiveBuilder}.
     */
    public boolean realizesClassOf(Class<?> clazz,
                                   ParameterProvider parameterProvider);
}
