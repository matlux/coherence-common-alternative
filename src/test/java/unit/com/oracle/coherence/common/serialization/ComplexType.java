package com.oracle.coherence.common.serialization;

import com.oracle.coherence.common.serialization.annotations.PofField;
import com.oracle.coherence.common.serialization.annotations.PofType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import junit.framework.Assert;

/**
 * @author Christer Fahlgren
 */
@PofType(id = 1005)
public class ComplexType
{

    Object[] objectArrayField;
 
    public ComplexType()
    {
    }


    public void init()
    {
        objectArrayField = new Object[]{"a","b","c"};
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(objectArrayField);
        return result;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ComplexType other = (ComplexType) obj;
        if (!Arrays.equals(objectArrayField, other.objectArrayField))
            return false;
        return true;
    }

}
