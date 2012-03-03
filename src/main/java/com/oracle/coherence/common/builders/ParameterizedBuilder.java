/*
 * File: ParameterizedBuilder.java
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
 * An {@link ParameterizedBuilder} is a {@link ReflectiveBuilder} that requires runtime
 * {@link com.oracle.coherence.configuration.parameters.Parameter}s, supplied by a {@link ParameterProvider}, 
 * in order to realize an object.
 *
 * @param <T> The class of object that will be produced by the {@link ParameterizedBuilder}
 * 
 * @author Brian Oliver
 */
public interface ParameterizedBuilder<T> extends ReflectiveBuilder<T>
{

    /**
     * Realizes an instance of the specified type of class.
     * 
     * @param parameterProvider The {@link ParameterProvider} that provides 
     *                          {@link com.oracle.coherence.configuration.parameters.Parameter}s.
     * 
     * @return An instance of <T>
     */
    public T realize(ParameterProvider parameterProvider);
}
