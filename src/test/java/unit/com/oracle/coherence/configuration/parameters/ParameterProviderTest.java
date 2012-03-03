/*
 * File: ParameterProviderTest.java
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

/**
 * <p>Unit tests for the {@link ParameterProvider}s.</p>
 *
 * @author Brian Oliver
 */
public class ParameterProviderTest
{

    /**
     * <p>A unit test for the definition and resolution of {@link Parameter}s with a {@link SimpleParameterProvider}.</p>
     */
    @Test
    public void testParameterDefinition()
    {
        MutableParameterProvider parameterProvider = new SimpleParameterProvider();

        //ensure that a parameter doesn't exist
        assertFalse(parameterProvider.isDefined("doesnt-exist"));

        //add a parameter
        parameterProvider.addParameter(new Parameter("my-parameter", "hello world"));

        //ensure that it exists
        assertTrue(parameterProvider.isDefined("my-parameter"));
        assertTrue(parameterProvider.getParameter("my-parameter").evaluate(EmptyParameterProvider.INSTANCE).getObject()
            .equals("hello world"));

        //ensure the iteration over the scope is correct
        Iterator<Parameter> parameters = parameterProvider.iterator();
        assertTrue(parameters.hasNext());
        assertTrue(parameters.next().getName().equals("my-parameter"));
        assertFalse(parameters.hasNext());
    }


    /**
     * <p>A unit test for the definition and resolution of {@link Parameter}s in a {@link ScopedParameterProvider}.</p>
     */
    @Test
    public void testNestedParameterProvider()
    {
        //create a simple scope with a parameter
        MutableParameterProvider parameterProvider = new SimpleParameterProvider();
        parameterProvider.addParameter(new Parameter("my-parameter", "hello world"));

        //create a scoped provider with the simple provider
        ScopedParameterProvider scopedParameterProvider = new ScopedParameterProvider(parameterProvider);

        //ensure that we can see the parameter through the nested scope
        assertFalse(scopedParameterProvider.isDefined("doesnt-exist"));
        assertTrue(scopedParameterProvider.isDefined("my-parameter"));
        assertTrue(scopedParameterProvider.getParameter("my-parameter").evaluate(EmptyParameterProvider.INSTANCE)
            .getObject().equals("hello world"));

        //ensure the iteration over the nested provider is correct
        Iterator<Parameter> parameters = scopedParameterProvider.iterator();
        assertTrue(parameters.hasNext());
        assertTrue(parameters.next().getName().equals("my-parameter"));
        assertFalse(parameters.hasNext());

        //add a parameter to the nested scope to hide the one defined in the simple scope
        scopedParameterProvider.addParameter(new Parameter("my-parameter", "gudday"));

        //ensure that we can only see the new parameter (and not the one from the simple scope)
        assertTrue(scopedParameterProvider.getParameter("my-parameter").evaluate(EmptyParameterProvider.INSTANCE)
            .getObject().equals("gudday"));
        assertTrue(scopedParameterProvider.isDefined("my-parameter"));

        //ensure the iteration over the nested scope is correct
        parameters = scopedParameterProvider.iterator();
        assertTrue(parameters.hasNext());
        assertTrue(parameters.next().evaluate(EmptyParameterProvider.INSTANCE).getObject().equals("gudday"));
        assertFalse(parameters.hasNext());

        //ensure that we haven't changed the parameter in the simple scope
        assertTrue(parameterProvider.getParameter("my-parameter").evaluate(EmptyParameterProvider.INSTANCE).getObject()
            .equals("hello world"));

        //add a parameter to the nested scope to hide the one defined in the simple scope
        scopedParameterProvider.addParameter(new Parameter("my-other-parameter", "howdy"));

        //ensure that we can only see the new parameter in the nested scope
        assertTrue(scopedParameterProvider.getParameter("my-other-parameter").evaluate(EmptyParameterProvider.INSTANCE)
            .getObject().equals("howdy"));
        assertFalse(parameterProvider.isDefined("my-other-parameter"));

        //ensure the iteration over the nested scope is correct
        parameters = scopedParameterProvider.iterator();
        assertTrue(parameters.hasNext());
        assertTrue(parameters.next().getName().equals("my-parameter"));
        assertTrue(parameters.hasNext());
        assertTrue(parameters.next().getName().equals("my-other-parameter"));
        assertFalse(parameters.hasNext());
    }


    /**
     * A unit test for the {@link SystemPropertyParameterProvider}.
     */
    @Test
    public void testSystemPropertyParameterProvider()
    {
        ParameterProvider parameterProvider = SystemPropertyParameterProvider.INSTANCE;

        assertTrue(parameterProvider.isDefined("java.home"));
        assertFalse(parameterProvider.isDefined("my.spagetti.man"));
    }
}
