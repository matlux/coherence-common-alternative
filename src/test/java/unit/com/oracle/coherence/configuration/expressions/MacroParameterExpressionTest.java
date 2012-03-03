/*
 * File: MacroParameterExpressionTest.java
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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.oracle.coherence.configuration.parameters.MutableParameterProvider;
import com.oracle.coherence.configuration.parameters.Parameter;
import com.oracle.coherence.configuration.parameters.SimpleParameterProvider;
import com.oracle.coherence.configuration.parameters.SystemPropertyParameterProvider;

/**
 * <p>Unit tests for the {@link MacroParameterExpression} class.</p>
 *
 * @author Brian Oliver
 */
public class MacroParameterExpressionTest
{

    /**
     * Tests a boolean Macro without parameters.
     * 
     * @throws ClassNotFoundException if class not found
     */
    @Test
    public void testBooleanMacroWithoutParameter() throws ClassNotFoundException
    {
        Expression expression = new MacroParameterExpression("true");
        assertTrue(expression.evaluate(SystemPropertyParameterProvider.INSTANCE).getBoolean());
    }


    /**
     * Tests a boolean macro without parameters but with default value.
     * 
     * @throws ClassNotFoundException if class not found
     */
    @Test
    public void testBooleanMacroWithParameterUsingDefaultValue() throws ClassNotFoundException
    {
        Expression expression = new MacroParameterExpression("{my-parameter true}");
        assertTrue(expression.evaluate(SystemPropertyParameterProvider.INSTANCE).getBoolean());
    }


    /**
     * Tests a boolean macro using a parameters.
     * 
     * @throws ClassNotFoundException if class not found
     */
    @Test
    public void testBooleanMacroWithParameter() throws ClassNotFoundException
    {
        MutableParameterProvider parameterProvider = new SimpleParameterProvider();
        parameterProvider.addParameter(new Parameter("my-parameter", false));
        Expression expression = new MacroParameterExpression("{my-parameter}");
        assertTrue(!expression.evaluate(parameterProvider).getBoolean());
    }


    /**
     * Tests a string macro without parameters.
     * 
     * @throws ClassNotFoundException if class not found
     */
    @Test
    public void testStringMacroWithoutParameter() throws ClassNotFoundException
    {
        Expression expression = new MacroParameterExpression("Hello World");
        assertTrue(expression.evaluate(SystemPropertyParameterProvider.INSTANCE).getString().equals("Hello World"));
    }


    /**
     * Tests a string macro with an undefined parameter but with a default.
     * 
     * @throws ClassNotFoundException if class not found
     */
    @Test
    public void testStringMacroWithParameterUsingDefaultValue() throws ClassNotFoundException
    {
        Expression expression = new MacroParameterExpression("{my-parameter Gudday}");
        assertTrue(expression.evaluate(SystemPropertyParameterProvider.INSTANCE).getString().equals("Gudday"));
    }


    /**
     * Tests a macro with a single parameter.
     * 
     * @throws ClassNotFoundException if class not found
     */
    @Test
    public void testStringMacroWithParameter() throws ClassNotFoundException
    {
        MutableParameterProvider parameterProvider = new SimpleParameterProvider();
        parameterProvider.addParameter(new Parameter("my-parameter", "Hello World"));
        Expression expression = new MacroParameterExpression("{my-parameter}");
        assertTrue(expression.evaluate(parameterProvider).getString().equals("Hello World"));
    }


    /**
     * Tests a macro with multiple parameters.
     * 
     * @throws ClassNotFoundException if class not found
     */
    @Test
    public void testStringMacroWithParameters() throws ClassNotFoundException
    {
        MutableParameterProvider parameterProvider = new SimpleParameterProvider();
        parameterProvider.addParameter(new Parameter("my-parameter-1", "Hello"));
        parameterProvider.addParameter(new Parameter("my-parameter-2", "World"));
        Expression expression = new MacroParameterExpression("({my-parameter-1}-{my-parameter-2})");
        assertTrue(expression.evaluate(parameterProvider).getString().equals("(Hello-World)"));
    }


    /**
     * Tests a macro with multiple parameters using defaults.
     * 
     * @throws ClassNotFoundException if class not found
     */
    @Test
    public void testStringMacroWithParametersAndDefaults() throws ClassNotFoundException
    {
        MutableParameterProvider parameterProvider = new SimpleParameterProvider();
        parameterProvider.addParameter(new Parameter("my-parameter-1", "Hello"));
        parameterProvider.addParameter(new Parameter("my-parameter-2", "World"));
        Expression expression = new MacroParameterExpression("Greeting is \\{{my-parameter-0 Gudday}-{my-parameter-2}\\}");
        assertTrue(expression.evaluate(parameterProvider).getString().equals("Greeting is {Gudday-World}"));
    }
}
