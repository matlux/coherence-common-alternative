/*
 * File: ParameterProvder.java
 * 
 * Copyright (c) 2010. All Rights Reserved. Oracle Corporation.
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
package com.oracle.coherence.configuration.parameters;

import java.util.Map;
import java.util.Properties;

import com.oracle.coherence.configuration.expressions.Expression;

/**
 * A {@link ParameterProvider} provides a mechanism to request and access an immutable collection of
 * {@link Parameter}s.
 * <p>
 * For {@link ParameterProvider}s that support mutation of the collection, refer to the 
 * {@link MutableParameterProvider}.
 *
 * @author Brian Oliver
 */
public interface ParameterProvider extends Iterable<Parameter>
{

    /**
     * Returns the specified named {@link Parameter}.
     * 
     * @param name The name of the {@link Parameter}
     * @return A {@link Parameter} or <code>null</code> if the {@link Parameter} is unknown to
     *         the {@link ParameterProvider}.
     */
    public Parameter getParameter(String name);


    /**
     * Determines if the specified named {@link Parameter} is known by the {@link ParameterProvider}.
     * @param name The name of the {@link Parameter}
     * 
     * @return <code>true</code> if the parameter is known by the {@link ParameterProvider}, 
     *         <code>false</code> otherwise.
     */
    public boolean isDefined(String name);


    /**
     * Determines if the {@link ParameterProvider} has known {@link Parameter}s.
     * 
     * @return <code>true</code> if the {@link ParameterProvider} knows of one or more {@link Parameter}s
     *         <code>false</code> otherwise
     */
    public boolean isEmpty();


    /**
     * Determines the number of {@link Parameter}s known to the {@link ParameterProvider}.
     * 
     * @return The number of {@link Parameter}s known to the {@link ParameterProvider}
     */
    public int size();


    /**
     * Constructs a {@link Map} of all of the {@link Parameter}s, each coerced to the require type if they
     * are strongly typed.
     * 
     * @param parameterProvider The {@link ParameterProvider} to use to resolve expressions used by {@link Parameter}s.
     * 
     * @return {@link Map}
     */
    public Map<String, ?> getParameters(ParameterProvider parameterProvider);


    /**
     * Constructs a Java {@link Properties} implementation containing all of the {@link Parameter}s, 
     * each coerced to the require type (if they are strongly typed).
     * 
     * @param parameterProvider The {@link ParameterProvider} to use to resolve {@link Expression}s within {@link Parameter}s.
     * 
     * @return {@link Properties}
     */
    public Properties getProperties(ParameterProvider parameterProvider);

}
