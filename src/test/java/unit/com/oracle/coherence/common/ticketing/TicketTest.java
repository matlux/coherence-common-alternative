/*
 * File: TicketTest.java
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
package com.oracle.coherence.common.ticketing;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * <p>Unit tests for the {@link Ticket} implementation.</p>
 * 
 * @author Christer Fahlgren
 */
public class TicketTest
{

    /**
     * Test {@link Ticket} creation.
     */
    @Test
    public void testTicketCreation()
    {
        Ticket ticket = new Ticket();
        assertTrue(ticket != null);
        assertTrue(ticket.getIssuerId() == 0);
        assertTrue(ticket.getSequenceNumber() == 0);

    }

    
    /**
     * Test {@link Ticket} creation.
     */
    @Test
    public void testTicketCreationLongLong()
    {
        Ticket ticket = new Ticket(1, 1000);
        assertTrue(ticket != null);
        assertTrue(ticket.getIssuerId() == 1);
        assertTrue(ticket.getSequenceNumber() == 1000);
    }

    /**
     * Test equals.
     */
    @Test
    public void testTicketEquals()
    {
        Ticket ticket = new Ticket(1, 1000);
        Ticket other = new Ticket(1, 1000);
        assertTrue(ticket.equals(other));
        other = new Ticket(0, 1000);
        assertTrue(!ticket.equals(other));
        other = new Ticket(1, 999);
        assertTrue(!ticket.equals(other));
        assertTrue(ticket.equals(ticket));
        assertTrue(!ticket.equals(null));

    }

    /**
     * Test compareTo.
     */
    @Test
    public void testTicketCompareTo()
    {
        Ticket ticket = new Ticket(1, 1000);
        Ticket other = new Ticket(1, 1000);
        assertTrue(ticket.compareTo(other) == 0);

        other = new Ticket(0, 1000);
        assertTrue(ticket.compareTo(other) == 1);

        other = new Ticket(2, 0);
        assertTrue(ticket.compareTo(other) == -1);

        other = new Ticket(1, 1001);
        assertTrue(ticket.compareTo(other) == -1);

        other = new Ticket(1, 999);
        assertTrue(ticket.compareTo(other) == 1);

    }
}
