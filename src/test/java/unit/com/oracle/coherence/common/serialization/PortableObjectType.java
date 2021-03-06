package com.oracle.coherence.common.serialization;


import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;

import java.io.IOException;

import junit.framework.Assert;


public class PortableObjectType
        implements PortableObject
{
    private int bb;

    private String second;

    private int aa;

    public void init()
    {
        aa = 43690;
        bb = 768955;
        second = "AAABBBCCC";
    }

    public void verify()
    {
        Assert.assertEquals(aa, 43690);
        Assert.assertEquals(bb, 768955);
        Assert.assertEquals(second, "AAABBBCCC");
    }

    /**
     * @{inheritDoc
     */
    @Override
    public int hashCode()
        {
        final int prime = 31;
        int result = 1;
        result = prime * result + aa;
        result = prime * result + bb;
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        return result;
        }

    /**
     * @{inheritDoc
     */
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
        if (!(obj instanceof PortableObjectType))
            {
            return false;
            }
        PortableObjectType other = (PortableObjectType) obj;
        if (aa != other.aa)
            {
            return false;
            }
        if (bb != other.bb)
            {
            return false;
            }
        if (second == null)
            {
            if (other.second != null)
                {
                return false;
                }
            }
        else if (!second.equals(other.second))
            {
            return false;
            }
        return true;
        }

    /**
     * @{inheritDoc
     */
    @Override
    public String toString()
        {
        return "PortableObjectType [bb=" + bb + ", second=" + second + ", aa=" + aa + "]";
        }

    @Override
    public void readExternal(PofReader in)
            throws IOException
        {
        bb = in.readInt(0);
        second = in.readString(1);
        aa = in.readInt(2);
        }
    
    @Override
    public void writeExternal(PofWriter out)
            throws IOException
        {
        out.writeInt(0, bb);
        out.writeString(1, second);
        out.writeInt(2, aa);
        }
}
