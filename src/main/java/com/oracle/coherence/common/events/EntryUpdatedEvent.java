/*
 * File: EntryUpdatedEvent.java
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
package com.oracle.coherence.common.events;

import java.util.Map.Entry;

/**
 * <p>An {@link EntryUpdatedEvent} is an {@link EntryEvent} that
 * represents when an {@link Entry} has been updated in a {@link com.tangosol.net.NamedCache}. 
 *  
 * @author Brian Oliver
 */
@SuppressWarnings("rawtypes")
public interface EntryUpdatedEvent<E extends Entry> extends EntryEvent<E>
{

    /**
     * <p>Returns the original {@link Entry} (the {@link Entry} prior to being updated).</p>
     * 
     * @return the original {@link Entry} (the {@link Entry} prior to being updated).
     */
    public E getOriginalEntry();
}
