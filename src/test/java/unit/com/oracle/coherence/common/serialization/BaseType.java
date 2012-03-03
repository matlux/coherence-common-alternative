package com.oracle.coherence.common.serialization;

import junit.framework.Assert;

import com.oracle.coherence.common.serialization.annotations.PofField;
import com.oracle.coherence.common.serialization.annotations.PofType;

@PofType(id = 1001, version = 1)
public class BaseType
{

    private int bb;

    @PofField(since = 1)
    private String second;

    @PofField()
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
}
