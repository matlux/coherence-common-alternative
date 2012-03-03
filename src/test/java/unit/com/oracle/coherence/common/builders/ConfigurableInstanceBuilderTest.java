/*
 * File: ConfigurableClassSchemeTest.java
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
package com.oracle.coherence.common.builders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;

import org.junit.Test;

import com.oracle.coherence.configuration.expressions.Constant;
import com.oracle.coherence.configuration.expressions.MacroParameterExpression;
import com.oracle.coherence.configuration.parameters.EmptyParameterProvider;
import com.oracle.coherence.configuration.parameters.MutableParameterProvider;
import com.oracle.coherence.configuration.parameters.Parameter;
import com.oracle.coherence.configuration.parameters.SimpleParameterProvider;

/**
 * <p>The {@link ConfigurableInstanceBuilderTest} represents unit tests for the 
 * {@link ConfigurableClassScheme}.</p>
 *
 * @author Brian Oliver
 */
public class ConfigurableInstanceBuilderTest
{

    /**
     * <p>A test using a constructor with no parameters.</p>
     */
    @Test
    public void testConstructorWithNoParameters()
    {
        ClassSchemeBasedParameterizedBuilder builder = new ClassSchemeBasedParameterizedBuilder();
        builder.setClassName(new Constant("java.lang.String"));

        assertTrue(builder.realizesClassOf(String.class, EmptyParameterProvider.INSTANCE));
        assertFalse(builder.realizesClassOf(Rectangle.class, EmptyParameterProvider.INSTANCE));

        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE), "");
    }


    /**
     * <p>A test using a constructor with the wrong number of parameters.</p>
     */
    @Test(expected = Exception.class)
    public void testConstructorWithWrongNumberOfParameters()
    {
        ClassSchemeBasedParameterizedBuilder builder = new ClassSchemeBasedParameterizedBuilder();
        builder.setClassName(new Constant("java.lang.String"));

        MutableParameterProvider parameters = new SimpleParameterProvider();
        parameters.addParameter(new Parameter("autostart", false));
        parameters.addParameter(new Parameter("size", 1234));
        parameters.addParameter(new Parameter("height", 1234));
        builder.setParameters(parameters);

        assertTrue(builder.realizesClassOf(String.class, EmptyParameterProvider.INSTANCE));
        assertFalse(builder.realizesClassOf(Rectangle.class, EmptyParameterProvider.INSTANCE));

        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE), "");
    }


    /**
     * <p>A test using a constructor with the correct number and type of parameters.</p>
     */
    @Test
    public void testConstructorWithCorrectParameters()
    {
        ClassSchemeBasedParameterizedBuilder builder = new ClassSchemeBasedParameterizedBuilder();
        builder.setClassName(new Constant("java.lang.String"));

        MutableParameterProvider parameters = new SimpleParameterProvider();
        parameters.addParameter(new Parameter("string", "hello world"));
        builder.setParameters(parameters);

        assertTrue(builder.realizesClassOf(String.class, EmptyParameterProvider.INSTANCE));
        assertFalse(builder.realizesClassOf(Rectangle.class, EmptyParameterProvider.INSTANCE));

        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE), "hello world");
    }


    /**
     * <p>A test using a constructor with parameterized parameters.</p>
     */
    @Test
    public void testConstructorWithParameterizedParameters()
    {
        ClassSchemeBasedParameterizedBuilder builder = new ClassSchemeBasedParameterizedBuilder();
        builder.setClassName(new Constant("java.lang.String"));
        MutableParameterProvider parameters = new SimpleParameterProvider();
        parameters.addParameter(new Parameter("string", new MacroParameterExpression("{greeting}")));
        builder.setParameters(parameters);

        MutableParameterProvider parameterProvider = new SimpleParameterProvider();
        parameterProvider.addParameter(new Parameter("greeting", "hello world"));

        assertTrue(builder.realizesClassOf(String.class, parameterProvider));
        assertFalse(builder.realizesClassOf(Rectangle.class, parameterProvider));

        assertEquals(builder.realize(parameterProvider), "hello world");
    }
}