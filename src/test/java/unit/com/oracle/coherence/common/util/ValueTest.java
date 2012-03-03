/*
 * File: ValueTest.java
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.util.UnknownFormatConversionException;

import org.junit.Test;

import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.tangosol.run.xml.XmlHelper;

/**
 * <p>Unit tests for the {@link Value} class.</p>
 *
 * @author Brian Oliver
 */
public class ValueTest
{

    /**
     * <p>Testing using a valid String.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidString() throws ConfigurationException
    {
        Value value = new Value("This is a string");

        assertEquals(value.getString(), "This is a string");
    }


    /**
     * <p>Test using a valid integer.</p>
     * 
    * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidInteger() throws ConfigurationException
    {
        Value value = new Value("100");

        assertEquals(value.getInt(), 100);
    }


    /**
     * <p>Test that a invalid integer fails to parse.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test(expected = NumberFormatException.class)
    public void testInvalidInteger() throws ConfigurationException
    {
        Value value = new Value("gibberish");

        assertEquals(value.getInt(), 100);
    }


    /**
     * <p>Test using a valid long.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidLong() throws ConfigurationException
    {
        Value value = new Value("100");

        assertEquals(value.getLong(), 100);
    }


    /**
     * <p>Test that a invalid long fails to parse.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test(expected = NumberFormatException.class)
    public void testInvalidLong() throws ConfigurationException
    {
        Value value = new Value("gibberish");

        assertEquals(value.getLong(), 100);
    }


    /**
     * <p>Test using a valid true boolean.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidTrueBoolean() throws ConfigurationException
    {
        Value value = new Value("true");

        assertTrue(value.getBoolean());
    }


    /**
     * <p>Test using a valid false boolean.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidFalseBoolean() throws ConfigurationException
    {
        Value value = new Value("false");

        assertFalse(value.getBoolean());
    }


    /**
     * <p>Test that a invalid boolean to parse.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test(expected = UnknownFormatConversionException.class)
    public void testInvalidBoolean() throws ConfigurationException
    {
        Value value = new Value("gibberish");

        assertTrue(value.getBoolean());
    }


    /**
     * <p>Test using a valid double.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidDouble() throws ConfigurationException
    {
        Value value = new Value("123.456");

        assertTrue(value.getDouble() == 123.456);
    }


    /**
     * <p>Test that a invalid double fails to parse.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test(expected = NumberFormatException.class)
    public void testInvalidDouble() throws ConfigurationException
    {
        Value value = new Value("gibberish");

        assertTrue(value.getDouble() == 123.456);
    }


    /**
     * <p>Test using a valid BigDecimal.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidBigDecimal() throws ConfigurationException
    {
        Value value = new Value("123.456");

        assertEquals(value.getBigDecimal(),  new BigDecimal("123.456"));
    }


    /**
     * <p>Test that a invalid BigDecimal fails to parse.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test(expected = NumberFormatException.class)
    public void testInvalidBigDecimal() throws ConfigurationException
    {
        Value value = new Value("gibberish");

        assertEquals(value.getBigDecimal(),  new BigDecimal("123.456"));
    }


    /**
     * <p>Test using a valid float.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidFloat() throws ConfigurationException
    {
        Value value = new Value("123.00");

        assertTrue(value.getFloat() == 123.00);
    }


    /**
     * <p>Test that a invalid float fails to parse.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test(expected = NumberFormatException.class)
    public void testInvalidFloat() throws ConfigurationException
    {
        Value value = new Value("gibberish");

        assertTrue(value.getFloat() == 123.00);
    }


    /**
     * <p>Test using a valid short.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidShort() throws ConfigurationException
    {
        Value value = new Value("100");

        assertEquals(value.getShort(), 100);
    }


    /**
     * <p>Test that a invalid short fails to parse.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test(expected = NumberFormatException.class)
    public void testInvalidShort() throws ConfigurationException
    {
        Value value = new Value("gibberish");

        assertEquals(value.getShort(), 100);
    }


    /**
     * <p>Test using a valid byte.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidByte() throws ConfigurationException
    {
        Value value = new Value("100");

        assertEquals(value.getByte(), 100);
    }


    /**
     * <p>Test that a invalid byte fails to parse.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test(expected = NumberFormatException.class)
    public void testInvalidByte() throws ConfigurationException
    {
        Value value = new Value("gibberish");

        assertEquals(value.getByte(), 100);
    }


    /**
     * <p>Test a valid null.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidNull() throws ConfigurationException
    {
        Value value = new Value((Object)null);

        assertTrue(value.isNull());
    }


    /**
     * <p>Test using valid xml.</p>
     * 
     * @throws ConfigurationException if config is invalid
     */
    @Test
    public void testValidXml() throws ConfigurationException
    {
        String xml = "<element attribute=\"value\">content</element>";
        Value value = new Value(xml);

        assertEquals(value.getXmlValue(), XmlHelper.loadXml(xml));
    }
}
