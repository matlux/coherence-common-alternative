/*
 * File: ValueNamespaceContentHandler.java
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
package com.oracle.coherence.environment.extensible.namespaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.oracle.coherence.environment.extensible.ConfigurationContext;
import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.oracle.coherence.environment.extensible.QualifiedName;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlHelper;

import static org.mockito.Mockito.*;
import org.junit.Test;

/**
 * <p>A {@link ValueNamespaceContentHandlerTest}.</p>
 *
 * @author Christer Falhgren
 * @author Brian Oliver
 */
@Deprecated
public class ValueNamespaceContentHandlerTest
{

    /**
     * Test instance:string.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementString() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:string>This is a string</value:string>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new String("This is a string"));
    }


    /**
     * Test failure when input is not a string parsing as an integer.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test(expected = NumberFormatException.class)
    public void testOnElementGibberishInteger() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:integer>gibberish</value:integer>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Integer(100));
    }


    /**
     * Test value:long.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementLong() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:long>100</value:long>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Long(100));
    }


    /**
     * Test string not parsing as a long.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test(expected = NumberFormatException.class)
    public void testOnElementGibberishLong() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:long>gibberish</value:long>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Long(100));
    }


    /**
     * Test value:boolean.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementBoolean() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:boolean>true</value:boolean>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Boolean(true));
    }


    /**
     * Test value:boolean for false. 
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementFalseBoolean() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:boolean>false</value:boolean>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(new Boolean(false), result);
    }


    /**
     * Test result of invalid input to boolean.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementGibberishBoolean() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:boolean>gibberish</value:boolean>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertFalse(new Boolean(true).equals(result));
    }


    /**
     * Test value:double.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementDouble() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:double>123.456</value:double>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Double(123.456));
    }


    /**
     * Test string not parsing as a double.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test(expected = NumberFormatException.class)
    public void testOnElementGibberishDouble() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:double>gibberish</value:double>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Double(123.456));
    }


    /**
     * Test value:float.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementFloat() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:float>123.456</value:float>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Float(123.456));
    }


    /**
     * Test string not parsing as a double.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test(expected = NumberFormatException.class)
    public void testOnElementGibberishFloat() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:float>gibberish</value:float>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Float(123.456));
    }


    /**
     * Test short.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementShort() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:short>100</value:short>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Short((short) 100));
    }


    /**
     * Test failure when input is not a string parsing as an integer.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test(expected = NumberFormatException.class)
    public void testOnElementGibberishShort() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:short>gibberish</value:short>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Short((short) 100));
    }


    /**
     * Test short.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementCharacter() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:character>c</value:character>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Character('c'));
    }


    /**
     * Test short.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test
    public void testOnElementByte() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:byte>100</value:byte>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Byte((byte) 100));
    }


    /**
     * Test failure when input is not a string parsing as an integer.
     * 
     * @throws ConfigurationException if there is a configuration error
     */
    @Test(expected = NumberFormatException.class)
    public void testOnElementGibberishByte() throws ConfigurationException
    {
        ConfigurationContext context = mock(ConfigurationContext.class);

        XmlElement elem = XmlHelper.loadXml("<value:byte>gibberish</value:byte>");
        ValueNamespaceContentHandler handler = new ValueNamespaceContentHandler();
        Object result = handler.onElement(context, new QualifiedName(elem), elem);

        assertEquals(result, new Byte((byte) 100));
    }
}
