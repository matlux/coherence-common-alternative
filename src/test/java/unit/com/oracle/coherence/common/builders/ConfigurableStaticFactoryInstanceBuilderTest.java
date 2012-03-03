/*
 * File: ConfigurableStaticFactoryClassSchemeTest.java
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
import java.util.Properties;

import org.junit.Test;

import com.oracle.coherence.configuration.expressions.Constant;
import com.oracle.coherence.configuration.parameters.EmptyParameterProvider;
import com.oracle.coherence.configuration.parameters.MutableParameterProvider;
import com.oracle.coherence.configuration.parameters.Parameter;
import com.oracle.coherence.configuration.parameters.SimpleParameterProvider;

/**
 * <p>The {@link ConfigurableStaticFactoryInstanceBuilderTest} represents unit tests for the 
 * {@link ConfigurableStaticFactoryClassScheme}.</p>
 *
 * @author Brian Oliver
 */
public class ConfigurableStaticFactoryInstanceBuilderTest
{

    /**
     * <p>A test using a static method with no parameters.</p>
     */
    @Test
    public void testStaticMethodWithNoParameters()
    {
        StaticFactoryClassSchemeBasedParameterizedBuilder builder = new StaticFactoryClassSchemeBasedParameterizedBuilder();
        builder.setFactoryClassName(new Constant("java.lang.System"));
        builder.setFactoryMethodName(new Constant("getProperties"));

        assertTrue(builder.realizesClassOf(Properties.class, EmptyParameterProvider.INSTANCE));
        assertFalse(builder.realizesClassOf(Rectangle.class, EmptyParameterProvider.INSTANCE));

        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE), System.getProperties());
    }


    /**
     * <p>A test using a static method with the wrong number of parameters.</p>
     */
    @Test(expected = Exception.class)
    public void testStaticMethodWrongNumberOfParameters()
    {
        StaticFactoryClassSchemeBasedParameterizedBuilder builder = new StaticFactoryClassSchemeBasedParameterizedBuilder();
        builder.setFactoryClassName(new Constant("java.lang.System"));
        builder.setFactoryMethodName(new Constant("getProperties"));

        MutableParameterProvider parameters = new SimpleParameterProvider();
        parameters.addParameter(new Parameter("autostart", false));
        builder.setParameters(parameters);

        assertFalse(builder.realizesClassOf(Properties.class, EmptyParameterProvider.INSTANCE));
        assertFalse(builder.realizesClassOf(Rectangle.class, EmptyParameterProvider.INSTANCE));

        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE), System.getProperties());
    }


    /**
     * <p>A test using a static method with the corrent number and type of parameters.</p>
     */
    @Test
    public void testStaticMethodWithCorrectParameters()
    {
        StaticFactoryClassSchemeBasedParameterizedBuilder builder = new StaticFactoryClassSchemeBasedParameterizedBuilder();
        builder.setFactoryClassName(new Constant("java.lang.System"));
        builder.setFactoryMethodName(new Constant("getProperty"));

        MutableParameterProvider parameters = new SimpleParameterProvider();
        parameters.addParameter(new Parameter("key", "java.class.path"));
        builder.setParameters(parameters);

        assertTrue(builder.realizesClassOf(String.class, EmptyParameterProvider.INSTANCE));
        assertFalse(builder.realizesClassOf(Rectangle.class, EmptyParameterProvider.INSTANCE));

        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE), System.getProperty("java.class.path"));
    }


    /**
     * <p>A test using a non-static method.</p>
     */
    @Test(expected = Exception.class)
    public void testNonStaticMethod()
    {
        StaticFactoryClassSchemeBasedParameterizedBuilder builder = new StaticFactoryClassSchemeBasedParameterizedBuilder();
        builder.setFactoryClassName(new Constant("java.lang.String"));
        builder.setFactoryMethodName(new Constant("size"));

        assertFalse(builder.realizesClassOf(int.class, EmptyParameterProvider.INSTANCE));
        assertFalse(builder.realizesClassOf(Rectangle.class, EmptyParameterProvider.INSTANCE));

        assertEquals(builder.realize(EmptyParameterProvider.INSTANCE), 0);
    }
}