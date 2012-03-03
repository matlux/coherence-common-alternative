/*
 * File: StringTransformerTest.java
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
package com.oracle.coherence.common.util;

import junit.framework.Assert;

import org.junit.Test;

/**
 * The {@link StringTransformerTest} provides unit tests for the {@link StringTransformer}.
 *
 * @author Brian Oliver
 */
public class StringTransformerTest
{

    /**
     * Test that we can match strings using regular expressions.
     */
    @Test
    public void testExactMatch()
    {
        StringTransformer transformer = new StringTransformer("dist-cache", "my-cache");

        Assert.assertTrue(transformer.isTransformable("dist-cache"));
        Assert.assertFalse(transformer.isTransformable("dist-me"));
        Assert.assertFalse(transformer.isTransformable("my-cache"));
        Assert.assertEquals("my-cache", transformer.transform("dist-cache"));
    }


    /**
     * Test that we can transform one string into another using regular expressions.
     */
    @Test
    public void testTranform()
    {
        StringTransformer transformer = new StringTransformer("dist-(.*)", "my-$1");

        Assert.assertTrue(transformer.isTransformable("dist-cache"));
        Assert.assertFalse(transformer.isTransformable("me"));
        Assert.assertFalse(transformer.isTransformable("my-cache"));
        Assert.assertEquals("my-cache", transformer.transform("dist-cache"));
    }
}
