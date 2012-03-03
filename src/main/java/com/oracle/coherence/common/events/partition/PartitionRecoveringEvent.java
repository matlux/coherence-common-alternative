/*
 * File: PartitionRecoveringEvent.java
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
package com.oracle.coherence.common.events.partition;

import com.oracle.coherence.common.events.PhasedEvent;
import com.tangosol.net.partition.PartitionEvent;

/**
 * <p>A {@link PartitionRecoveringEvent} is a phased {@link PhasedEvent} that captures the information concerning the
 * recovery (and thus ownership responsibility) of a set of partitions.</p>
 * 
 * <p>Such events typically occur as a response to a storage-enabled member being terminated.</p>
 * 
 * @author Brian Oliver
 */
public class PartitionRecoveringEvent extends AbstractPartitionEvent implements PhasedEvent
{

    /**
     * <p>The {@link Phase} of the {@link PartitionRecoveringEvent}.</p>
     */
    private Phase phase;

    /**
     * <p>Standard Constructor for Tangosol-based {@link PartitionEvent}s.</p>
     * 
     * @param partitionEvent the Tangosol based {@link PartitionEvent}
     */
    public PartitionRecoveringEvent(PartitionEvent partitionEvent)
    {
        super(partitionEvent);
        this.phase = partitionEvent.getId() == PartitionEvent.PARTITION_RECEIVE_BEGIN ? Phase.Commenced
                : Phase.Completed;
    }

    /**
     * {@inheritDoc}
     */
    public Phase getPhase()
    {
        return phase;
    }

    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        return String.format("PartitionRecoveryingEvent{partitionSet=%s, phase=%s}", getPartitionSet(), getPhase());
    }
}
