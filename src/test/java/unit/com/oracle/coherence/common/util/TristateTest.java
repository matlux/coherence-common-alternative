/*
 * File: TristateTest.java
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
package com.oracle.coherence.common.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * <p>Unit Tests for the {@link Tristate} class.</p>
 * 
 * @author Brian Oliver
 */
public class TristateTest
{
    /**
     * Test Boolean compatibility.
     */
    @Test
    public void testBooleanCompatibility()
    {
        assertTrue(Tristate.TRUE.equals(new Tristate(true)));
        assertTrue(Tristate.FALSE.equals(new Tristate(false)));
    }


    /**
     * Test constants.
     */
    @Test
    public void testContants()
    {
        assertTrue(Tristate.TRUE.isTrue());
        assertFalse(Tristate.TRUE.isFalse());
        assertFalse(Tristate.TRUE.isUndefined());

        assertFalse(Tristate.FALSE.isTrue());
        assertTrue(Tristate.FALSE.isFalse());
        assertFalse(Tristate.FALSE.isUndefined());

        assertFalse(Tristate.UNDEFINED.isTrue());
        assertFalse(Tristate.UNDEFINED.isFalse());
        assertTrue(Tristate.UNDEFINED.isUndefined());
    }


    /**
     * Test not().
     */
    @Test
    public void testNot()
    {
        assertTrue(Tristate.TRUE.not().equals(Tristate.FALSE));
        assertTrue(Tristate.FALSE.not().equals(Tristate.TRUE));
        assertTrue(Tristate.UNDEFINED.not().equals(Tristate.UNDEFINED));
    }


    /**
     * Test or().
     */
    @Test
    public void testOr()
    {
        assertTrue(Tristate.TRUE.or(Tristate.TRUE).equals(Tristate.TRUE));
        assertTrue(Tristate.TRUE.or(Tristate.FALSE).equals(Tristate.TRUE));
        assertTrue(Tristate.TRUE.or(Tristate.UNDEFINED).equals(Tristate.TRUE));

        assertTrue(Tristate.FALSE.or(Tristate.TRUE).equals(Tristate.TRUE));
        assertTrue(Tristate.FALSE.or(Tristate.FALSE).equals(Tristate.FALSE));
        assertTrue(Tristate.FALSE.or(Tristate.UNDEFINED).equals(Tristate.FALSE));

        assertTrue(Tristate.UNDEFINED.or(Tristate.TRUE).equals(Tristate.TRUE));
        assertTrue(Tristate.UNDEFINED.or(Tristate.FALSE).equals(Tristate.FALSE));
        assertTrue(Tristate.UNDEFINED.or(Tristate.UNDEFINED).equals(Tristate.UNDEFINED));
    }


    /**
     * Test and().
     */
    @Test
    public void testAnd()
    {
        assertTrue(Tristate.TRUE.and(Tristate.TRUE).equals(Tristate.TRUE));
        assertTrue(Tristate.TRUE.and(Tristate.FALSE).equals(Tristate.FALSE));
        assertTrue(Tristate.TRUE.and(Tristate.UNDEFINED).equals(Tristate.UNDEFINED));

        assertTrue(Tristate.FALSE.and(Tristate.TRUE).equals(Tristate.FALSE));
        assertTrue(Tristate.FALSE.and(Tristate.FALSE).equals(Tristate.FALSE));
        assertTrue(Tristate.FALSE.and(Tristate.UNDEFINED).equals(Tristate.UNDEFINED));

        assertTrue(Tristate.UNDEFINED.and(Tristate.TRUE).equals(Tristate.UNDEFINED));
        assertTrue(Tristate.UNDEFINED.and(Tristate.FALSE).equals(Tristate.UNDEFINED));
        assertTrue(Tristate.UNDEFINED.and(Tristate.UNDEFINED).equals(Tristate.UNDEFINED));
    }
}
