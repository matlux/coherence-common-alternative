/*
 * File: ContiguousRangeTest.java
 * 
 * Copyright (c) 2009-2010. All Rights Reserved. Oracle Corporation.
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
package com.oracle.coherence.common.ranges;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;

/**
 * <p>Unit tests for the {@link ContiguousRange} implementation.</p>
 * 
 * @author Christer Fahlgren
 * @author Brian Oliver
 */
public class ContiguousRangeTest
{
    /**
     * Tests an empty range.
     */
    @Test
    public void testContiguousRangeLongLong()
    {
        Range range = new ContiguousRange(5);
        
        assertTrue(range.getFrom() == 5);
        assertTrue(range.isEmpty());
        assertFalse(range.isSingleton());
    }


    /**
     * Tests an empty range.
     */
    @Test
    public void testContiguousRange()
    {
        Range range = new ContiguousRange();
        assertTrue(range.getFrom() == 0);
        assertTrue(range.isEmpty());
        assertFalse(range.isSingleton());
    }


    /**
     * Tests additions.
     */
    @Test
    public void testAdd()
    {
        Range range = new ContiguousRange(1, 5);

        // Just add one number that is adjacent
        range = range.add(6);
        assertTrue(range.size() == 6);

        // Just add one number that is adjacent
        range = range.add(0);
        assertTrue(range.size() == 7);

        // Just add one number that already exists
        range = range.add(0);
        assertTrue(range.size() == 7);

        // Add another one that is not adjacent
        range = range.add(8);
        assertTrue(range.size() == 8);
        assertTrue(range instanceof SparseRange);

    }


    /**
     * Tests unions.
     */
    @Test
    public void testUnion()
    {
        Range range = new ContiguousRange(1, 5);

        assertTrue(range.size() == 5);

        // make union with empty range
        range = range.union(new ContiguousRange());
        assertTrue(range.size() == 5);

        // make union with non adjacent range
        Range other = new ContiguousRange(7, 8);
        range = range.union(other);
        assertTrue(range.size() == 7);
        assertTrue(range instanceof SparseRange);
        assertTrue(!range.contains(6));

        // Make union with other sparserange
        range = new ContiguousRange(1, 5);
        other = new SparseRange(new ContiguousRange(7, 8), new ContiguousRange(9, 10));
        range = range.union(other);
        assertTrue(range.size() == 9);
        assertTrue(range instanceof SparseRange);

    }


    /**
     * Test compareTo.
     */
    @Test
    public void testCompareTo()
    {
        ContiguousRange range = new ContiguousRange(1, 5);
        ContiguousRange other = new ContiguousRange(6, 10);
        assertTrue(range.compareTo(other) == -1);

        other = new ContiguousRange(-5, 0);
        assertTrue(range.compareTo(other) == 1);

        other = new ContiguousRange(1, 5);
        assertTrue(range.compareTo(other) == 0);

        other = new ContiguousRange(1, 3);
        try
        {
            range.compareTo(other);
            assertTrue(false);
        }
        catch (NotComparableRuntimeException e)
        {
            assertTrue(true);
        }

    }


    /**
     * Test PofWriter.
     * 
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testWriteExternalPofWriter() throws IOException
    {
        PofWriter writer = mock(PofWriter.class);

        ContiguousRange range = new ContiguousRange(0, 1);
        range.writeExternal(writer);

        verify(writer).writeLong(0, 0L);
        verify(writer).writeLong(1, 1L);
    }


    /**
     * Test PofReader.
     * 
     * @throws IOException if there is an IO error.
     */
    @Test
    public void testReadExternalPofReader() throws IOException
    {
        PofReader reader = mock(PofReader.class);

        when(reader.readLong(0)).thenReturn(0L);
        when(reader.readLong(1)).thenReturn(5L);

        ContiguousRange range = new ContiguousRange();
        range.readExternal(reader);
        
        assertTrue(range.getFrom() == 0);
        assertTrue(range.getTo() == 5);
    }
}
