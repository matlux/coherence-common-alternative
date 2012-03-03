/*
 * File: PartitionKey.java
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

package com.oracle.coherence.common.partition;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.util.ExternalizableHelper;

/**
 * <p>A {@link PartitionKey} is a key that is assigned to a particular partition.
 * Works together with the {@link PartitionAwareKeyPartitioningStrategy}. </p>
 *
 * @author Christer Fahlgren
 */
@SuppressWarnings("serial")
public class PartitionKey implements PartitionAwareKey, ExternalizableLite, PortableObject
{

    /**
     * The partition this key is assigned to.
     */
    private int partition;

    /**
     * The embedded object that is the "real" key. 
     */
    private Object embeddedKey;


    /**
     * Default constructor required by PortableObject.
     */
    public PartitionKey()
    {
    }


    /**
     * Standard constructor.
     * 
     * @param partition   the partition this key belongs to
     * @param embeddedKey the real key
     */
    public PartitionKey(int partition,
                        Object embeddedKey)
    {
        this.partition = partition;
        this.embeddedKey = embeddedKey;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return "PartitionKey [" + (embeddedKey != null ? "embeddedKey=" + embeddedKey + ", " : "") + "partition="
                + partition + "]";
    }


    /**
     * {@inheritDoc}
     */
    public int getPartition()
    {
        return partition;
    }


    /**
     * Returns the real embedded key.
     * 
     * @return the real key.
     */
    public Object getKey()
    {
        return embeddedKey;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + embeddedKey.hashCode();
        result = prime * result + partition;
        return result;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        PartitionKey other = (PartitionKey) obj;
        if (embeddedKey == null)
        {
            if (other.embeddedKey != null)
            {
                return false;
            }
        }
        else
        {
            if (!embeddedKey.equals(other.embeddedKey))
            {
                return false;
            }
        }
        if (partition != other.partition)
        {
            return false;
        }
        return true;
    }


    /**
     * {@inheritDoc}
     */
    public void readExternal(DataInput in) throws IOException
    {
        partition = in.readInt();
        embeddedKey = ExternalizableHelper.readObject(in);
    }


    /**
     * {@inheritDoc}
     */
    public void writeExternal(DataOutput out) throws IOException
    {
        out.writeInt(partition);
        ExternalizableHelper.writeObject(out, embeddedKey);
    }


    /**
     * {@inheritDoc}
     */
    public void readExternal(PofReader in) throws IOException
    {
        partition = in.readInt(0);
        embeddedKey = in.readObject(1);
    }


    /**
     * {@inheritDoc}
     */
    public void writeExternal(PofWriter out) throws IOException
    {
        out.writeInt(0, partition);
        out.writeObject(1, embeddedKey);
    }
}
