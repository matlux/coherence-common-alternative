/*
 * File: Expression.java
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
package com.oracle.coherence.configuration.expressions;

import com.oracle.coherence.common.util.Value;
import com.oracle.coherence.configuration.parameters.ParameterProvider;

/**
 * <p>A {@link Expression} represents a user-defined expression that may be evaluated at 
 * runtime to produce a {@link Value}.</p>
 *
 * @author Brian Oliver
 */
public interface Expression
{

    /**
     * <p>Evaluates the expression to produce a resulting {@link Value}.</p>
     * 
     * @param parameterProvider The {@link ParameterProvider} that may be used to determine parameters occurring in 
     *                          the {@link Expression}
     * 
     * @return The {@link Value} as a result of evaluating the expression
     */
    public Value evaluate(ParameterProvider parameterProvider);


    /**
     * Determines if the {@link Expression} is serializable.
     * 
     * @return <code>true</code> if the expression can be serialized, <code>false</code> otherwise.
     */
    public boolean isSerializable();

}
