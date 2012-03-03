package com.oracle.coherence.common.serialization;

import com.oracle.coherence.common.serialization.annotations.PofType;

import junit.framework.Assert;

@PofType(id = 1002)
public class SimpleType
{

    private int a;
    private float b;
    private double c;
    private byte d;
    private boolean f;
    private short g;
    private long h;

    private transient int ta;
    private transient float tb;
    private transient double tc;
    private transient byte td;
    private transient boolean tf;
    private transient short tg;
    private transient long th;


    public SimpleType()
    {
    }


    public void init()
    {
        a = 1;
        b = 2.0f;
        c = 3.0;
        d = 4;
        f = true;
        g = 6;
        h = 7;

        ta = 1;
        tb = 2.0f;
        tc = 3.0;
        td = 4;
        tf = true;
        tg = 6;
        th = 7;
    }


    public void verify()
    {
        Assert.assertEquals(1, a);
        Assert.assertEquals(2.0f, b);
        Assert.assertEquals(3.0, c);
        Assert.assertEquals(4, d);
        Assert.assertEquals(true, f);
        Assert.assertEquals(6, g);
        Assert.assertEquals(7, h);

        Assert.assertEquals(0, ta);
        Assert.assertEquals(0.0f, tb);
        Assert.assertEquals(0.0, tc);
        Assert.assertEquals(0, td);
        Assert.assertEquals(false, tf);
        Assert.assertEquals(0, tg);
        Assert.assertEquals(0, th);
    }
}
