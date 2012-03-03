package com.oracle.coherence.common.serialization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import junit.framework.Assert;

import com.oracle.coherence.common.serialization.annotations.PofField;
import com.oracle.coherence.common.serialization.annotations.PofIgnore;
import com.oracle.coherence.common.serialization.annotations.PofType;

/**
 * @author coh 2010-05-23
 */
@PofType(id = 1000, version = 1)
public class BasicType extends BaseType
{

    @PofField(since = 1)
    private int[] aa;

    private String c;

    @PofIgnore
    private String[] cs;

    private int[] ia;

    private int a;

    private float b;

    private Map<Integer, String> map = new HashMap<Integer, String>();

    private HashMap<Integer, Integer> map2;

    @PofField(type = ConcurrentHashMap.class)
    private Map<Integer, Integer[]> map3;

    private PofRemainder remainder;


    public BasicType()
    {
    }


    @Override
    public void init()
    {
        super.init();

        a = 33;
        b = 1.0f;
        c = "AABCDEFGG";
        cs = new String[] { "aaa", "bbb" };
        ia = new int[] { 1, 2, 3 };
        aa = new int[] { 4, 2, 3 };
        map.put(1, "apa");
        map2 = new HashMap<Integer, Integer>();
        map2.put(2, 2);
        map3 = new HashMap<Integer, Integer[]>();
        map3.put(4, new Integer[] { 2, 3, 4 });
    }


    public void verify()
    {
        super.verify();

        Assert.assertEquals(a, 33);
        Assert.assertEquals(b, 1.0f);
        Assert.assertEquals(c, "AABCDEFGG");

        Assert.assertTrue(Arrays.equals(ia, new int[] { 1, 2, 3 }));
        Assert.assertTrue(Arrays.equals(aa, new int[] { 4, 2, 3 }));

        Assert.assertEquals(1, map.size());
        Assert.assertEquals(map.get(1), "apa");

        Assert.assertEquals(1, map2.size());
        Assert.assertEquals(map2.get(2), new Integer(2));

        Assert.assertEquals(1, map3.size());
        Assert.assertTrue(Arrays.equals(map3.get(4), new Integer[] { 2, 3, 4 }));
    }
}
