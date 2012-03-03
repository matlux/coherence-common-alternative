/*
 * File: ClassSchemeBasedParameterizedBuilderTest.java
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

import java.awt.Point;
import java.awt.Rectangle;

import org.junit.Test;

import com.oracle.coherence.common.util.Value;
import com.oracle.coherence.configuration.expressions.Constant;
import com.oracle.coherence.configuration.expressions.MacroParameterExpression;
import com.oracle.coherence.configuration.parameters.EmptyParameterProvider;
import com.oracle.coherence.configuration.parameters.MutableParameterProvider;
import com.oracle.coherence.configuration.parameters.Parameter;
import com.oracle.coherence.configuration.parameters.SimpleParameterProvider;

/**
 * The {@link ClassSchemeBasedParameterizedBuilderTest} represents unit tests for the 
 * {@link ClassSchemeBasedParameterizedBuilder}.
 *
 * @author Brian Oliver
 */
public class ClassSchemeBasedParameterizedBuilderTest
{

    /**
     * A test using a constructor with no parameters.
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
     * A test using a constructor with the wrong number of parameters.
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
     * A test using a constructor with the correct number and type of parameters.
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
     * A test using a constructor with parameterized parameters.
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


    /**
     * A test using a constructor with primitive parameters.
     */
    @Test
    public void testConstructorWithPrimitiveParameters()
    {
        ClassSchemeBasedParameterizedBuilder builder = new ClassSchemeBasedParameterizedBuilder();
        builder.setClassName(new Constant("java.awt.Point"));
        MutableParameterProvider parameters = new SimpleParameterProvider();
        parameters.addParameter(new Parameter("x", "java.lang.Integer", new Value(10)));
        parameters.addParameter(new Parameter("y", "java.lang.Integer", new Value(20)));
        builder.setParameters(parameters);

        assertTrue(builder.realizesClassOf(Point.class, parameters));
        assertFalse(builder.realizesClassOf(Rectangle.class, parameters));

        Point p = (Point)builder.realize(parameters);
        
        assertEquals(10, (int)p.getX());
        assertEquals(20, (int)p.getY());
    }
}