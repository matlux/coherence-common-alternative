/*
 * File: ParameterizedClassSchemeTest.java
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.awt.Rectangle;

import org.junit.Test;

import com.oracle.coherence.configuration.parameters.EmptyParameterProvider;

/**
 * <p>The {@link ParameterizedInstanceBuilderTest} represents unit tests for the {@link ParameterizedClassScheme}.</p>
 *
 * @author Brian Oliver
 */
public class ParameterizedInstanceBuilderTest
{

    /**
     * Tests the {@link ParameterizedClassScheme}.
     */
    @Test
    public void testParameterizedClassScheme()
    {
        VarArgsParameterizedBuilder builder = new VarArgsParameterizedBuilder("java.awt.Point", 1, 1);

        assertTrue(builder.realizesClassOf(Point.class, EmptyParameterProvider.INSTANCE));
        assertFalse(builder.realizesClassOf(Rectangle.class, EmptyParameterProvider.INSTANCE));
    }
}