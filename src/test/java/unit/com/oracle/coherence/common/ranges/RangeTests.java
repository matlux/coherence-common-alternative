/*
 * File: RangeTests.java
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

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

/**
 * <p>Unit tests for the {@link Range} implementations.</p>
 * 
 * @author Brian Oliver, Christer Fahlgren
 */
public class RangeTests
{
    /**
     * Tests union of ranges.
     */
    @Test
    public void testUnion()
    {
        Range range = new SparseRange(new ContiguousRange(4, 4), new ContiguousRange(2, 2));

        assertTrue(range.size() == 2);
        assertTrue(range.getFrom() == 2);
        assertTrue(range.getTo() == 4);

        Iterator<Long> iterator = range.iterator();
        assertTrue(iterator.hasNext() && iterator.next() == 2);
        assertTrue(iterator.hasNext() && iterator.next() == 4);
        assertTrue(!iterator.hasNext());

        range = range.union(range.union(new ContiguousRange(1, 1)));

        assertTrue(range.size() == 3);
        assertTrue(range.getFrom() == 1);
        assertTrue(range.getTo() == 4);

        iterator = range.iterator();
        assertTrue(iterator.hasNext() && iterator.next() == 1);
        assertTrue(iterator.hasNext() && iterator.next() == 2);
        assertTrue(iterator.hasNext() && iterator.next() == 4);
        assertTrue(!iterator.hasNext());
    }


    /**
     * Tests remove.
     */
    @Test
    public void testRemove()
    {
        Range range = new SparseRange(new ContiguousRange(4, 4), new ContiguousRange(2, 2));

        range = range.remove(2);

        assertTrue(range.size() == 1);
        assertTrue(range.getFrom() == 4);
        assertTrue(range.getTo() == 4);

        Iterator<Long> iterator = range.iterator();
        assertTrue(iterator.hasNext() && iterator.next() == 4);
        assertTrue(!iterator.hasNext());

        range = range.remove(4);
        assertTrue(range.size() == 0);
        assertTrue(range.isEmpty());
    }
}
