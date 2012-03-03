/*
 * File: InvokeMethodProcessorTest.java
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
package com.oracle.coherence.common.processors;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;

import com.oracle.coherence.common.util.ChangeIndication;
import com.tangosol.util.InvocableMap.Entry;

/**
 * <p>A {@link InvokeMethodProcessorTest} tests the {@link InvokeMethodProcessor}. </p>
 *
 * @author Christer Fahlgren
 */
public class InvokeMethodProcessorTest
{

    /**
     * Tests an invocation supporting {@link ChangeIndication}.
     */
    @Test
    public void testProcess()
    {
        Entry entry = Mockito.mock(Entry.class);
        ChangeIndicationImpl valueObject = new ChangeIndicationImpl();
        Mockito.stub(entry.getValue()).toReturn(valueObject);
        valueObject.test(10);
        InvokeMethodProcessor processor = new InvokeMethodProcessor("test", new Object[] { 10 });
        processor.process(entry);
        Mockito.verify(entry).setValue(valueObject);

    }


    /**
     * Tests an invocation supporting {@link ChangeIndication} that didn't change.
     */
    @Test
    public void testProcessNoChange()
    {
        Entry entry = Mockito.mock(Entry.class);
        ChangeIndicationImpl valueObject = new ChangeIndicationImpl();
        Mockito.stub(entry.getValue()).toReturn(valueObject);
        valueObject.testNoChange();
        InvokeMethodProcessor processor = new InvokeMethodProcessor("testNoChange");
        processor.process(entry);
        Mockito.verify(entry, Mockito.never()).setValue(valueObject);

    }


    /**
     * Tests an invocation without support for {@link ChangeIndication}.
     */
    @Test
    public void testProcessNoChangeIndication()
    {
        Entry entry = Mockito.mock(Entry.class);
        PlainValueObject valueObject = new PlainValueObject();
        Mockito.stub(entry.getValue()).toReturn(valueObject);
        InvokeMethodProcessor processor = new InvokeMethodProcessor("test", new Object[] { 10 });
        processor.process(entry);
        Mockito.verify(entry).setValue(valueObject);

    }


    /**
     * Tests an invocation where there is no object.
     */
    @Test
    public void testProcessNoValue()
    {
        Entry entry = Mockito.mock(Entry.class);
        Mockito.stub(entry.getValue()).toReturn(null);
        InvokeMethodProcessor processor = new InvokeMethodProcessor("test", new Object[] { 10 });
        Object result = processor.process(entry);

        assertTrue(result.getClass().equals(NullPointerException.class));

    }


    /**
     * Tests the toString method.
     */
    @Test
    public void testToString()
    {
        InvokeMethodProcessor processor = new InvokeMethodProcessor("test", new Object[] { 10 });
        assertTrue(processor.toString().equals("test(10)"));
    }


    /**
     * <p>A {@link ChangeIndicationImpl} for testing. </p>
     *
     * @author Christer Fahlgren
     */
    static class ChangeIndicationImpl implements ChangeIndication
    {

        /**
         * 
         */
        private boolean changed;


        /**
         * Test method.
         * @param i test parameter.
         */
        public void test(int i)
        {
            setChanged();
        }


        /**
         * Sets changed status.
         */
        private void setChanged()
        {
            changed = true;
        }


        /**
         * Test method.
         */
        public void testNoChange()
        {
        }


        /**
         * {@inheritDoc}
         */
        public void beforeChange()
        {
            changed = false;
        }


        /**
         * {@inheritDoc}
         */
        public boolean changed()
        {
            return changed;
        }
    }


    /**
     * <p>A {@link PlainValueObject}. </p>
     *
     * @author Christer Fahlgren
     */
    static class PlainValueObject
    {

        /**
         * Test method.
         * 
         * @param i test parameter
         */
        public void test(int i)
        {
        }
    }

}
