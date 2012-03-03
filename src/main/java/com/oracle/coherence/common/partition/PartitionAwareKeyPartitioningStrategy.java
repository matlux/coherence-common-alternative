/*
 * File: PartitionKeyAwarePartitioningStrategy.java
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
package com.oracle.coherence.common.partition;

import com.tangosol.net.partition.DefaultKeyPartitioningStrategy;
import com.tangosol.net.partition.KeyPartitioningStrategy;

/**
 * A {@link PartitionAwareKeyPartitioningStrategy} is a {@link KeyPartitioningStrategy} that is aware of 
 * {@link PartitionAwareKey}s.
 *
 * @author Christer Fahlgren
 */
public class PartitionAwareKeyPartitioningStrategy extends DefaultKeyPartitioningStrategy
{

    /**
    * {@inheritDoc}
    */
    public int getKeyPartition(Object oKey)
    {
        if (oKey instanceof PartitionAwareKey)
        {
            return ((PartitionAwareKey) oKey).getPartitionId();
        }
        return super.getKeyPartition(oKey);
    }
}