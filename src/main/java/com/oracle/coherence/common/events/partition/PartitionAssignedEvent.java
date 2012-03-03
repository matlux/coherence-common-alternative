/*
 * File: PartitionAssignedEvent.java
 * 
 * Copyright (c) 2011. All Rights Reserved. Oracle Corporation.
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
package com.oracle.coherence.common.events.partition;

import com.tangosol.net.PartitionedService;
import com.tangosol.net.partition.PartitionEvent;
import com.tangosol.net.partition.PartitionSet;

/**
 * <p>A {@link PartitionAssignedEvent} captures the information concerning one or more partitions that have been 
 * assigned to a storage enabled member (due to initialization at the member or the member taking ownership due to 
 * loss).</p>
 * 
 * @author Brian Oliver
 */
public class PartitionAssignedEvent extends AbstractPartitionEvent
{

    /**
     * <p>Standard Constructor for Tangosol-based {@link PartitionEvent}s.</p>
     * 
     * @param partitionEvent the Tangosol based {@link PartitionEvent}
     */
    public PartitionAssignedEvent(PartitionEvent partitionEvent)
    {
        super(partitionEvent);
    }


    /**
     * <p>Standard Constructor when using explicit {@link PartitionedService} and {@link PartitionSet}s.</p>
     * 
     * @param partitionedService The {@link PartitionedService}
     * @param partitionSet       The {@link PartitionSet}
     */
    public PartitionAssignedEvent(PartitionedService partitionedService,
                                  PartitionSet partitionSet)
    {
        super(partitionedService, partitionSet);
    }


    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        return String.format("PartitionAssignedEvent{partitionSet=%s}", getPartitionSet());
    }

}
