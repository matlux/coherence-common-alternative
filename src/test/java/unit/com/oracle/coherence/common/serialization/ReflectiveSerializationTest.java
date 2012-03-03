/*
 * File: ReflectiveSerializationTest.java
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
package com.oracle.coherence.common.serialization;

import java.io.IOException;
import java.util.Arrays;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.tangosol.io.ByteArrayWriteBuffer;
import com.tangosol.io.pof.ConfigurablePofContext;

/**
 * The unit tests for {@link ReflectiveSerializer}s.
 *
 * @author Charlie Helin
 * @author Brian Oliver
 */
public class ReflectiveSerializationTest
{

    public static final byte[] simpleTypePofStream = new byte[] { -86, 15, 0, 1, 65, 1, 2, 107, 3, 108, 4, 109, 5, 97,
            6, 111, 7, 112, 64 };

    public static final byte[] collectionTypePofStream = new byte[] { -84, 15, 0, 1, 93, 78, 78, 4, 3, 116, 119, 111,
            3, 116, 118, 97, 3, 111, 110, 101, 3, 101, 116, 116, 5, 116, 104, 114, 101, 101, 3, 116, 114, 101, 4, 102,
            111, 117, 114, 4, 102, 121, 114, 97, 2, 86, 78, 4, 3, 111, 110, 101, 3, 116, 119, 111, 5, 116, 104, 114,
            101, 101, 4, 102, 111, 117, 114, 3, 85, 2, 85, 4, 78, 3, 111, 110, 101, 78, 3, 116, 119, 111, 78, 5, 116,
            104, 114, 101, 101, 78, 4, 102, 111, 117, 114, 85, 4, 78, 3, 111, 110, 101, 78, 3, 116, 119, 111, 78, 5,
            116, 104, 114, 101, 101, 78, 4, 102, 111, 117, 114, 4, 86, 78, 4, 3, 111, 110, 101, 3, 116, 119, 111, 5,
            116, 104, 114, 101, 101, 4, 102, 111, 117, 114, 5, 93, 78, 78, 4, 3, 116, 119, 111, 3, 116, 118, 97, 3,
            111, 110, 101, 3, 101, 116, 116, 4, 102, 111, 117, 114, 4, 102, 121, 114, 97, 5, 116, 104, 114, 101, 101,
            3, 116, 114, 101, 6, 93, 78, 78, 4, 4, 102, 111, 117, 114, 4, 102, 121, 114, 97, 3, 111, 110, 101, 3, 101,
            116, 116, 5, 116, 104, 114, 101, 101, 3, 116, 114, 101, 3, 116, 119, 111, 3, 116, 118, 97, 64 };

    public static final byte[] versionedTypePofStreamV0 = new byte[] { -85, 15, 0, 1, 65, 1, 2, 107, 3, 108, 4, 109, 5,
            97, 6, 111, 7, 112, 64 };

    public static final byte[] versionedTypePofStreamV1 = new byte[] { -85, 15, 1, 1, 65, 1, 2, 107, 3, 108, 4, 109, 5,
            97, 6, 111, 7, 112, 8, 115, 9, 125, 10, 69, 64, 62, 0, 0, 0, 0, 0, 0, 11, 75, 40, 12, 97, 13, 64, 60, 14,
            66, -122, 1, 64 };

    public static final byte[] basicTypePofStream = new byte[] { -88, 15, 1, 0, -88, 15, 1, 1, 65, -86, -86, 5, 2, 65,
            -69, -18, 93, 3, 78, 9, 65, 65, 65, 66, 66, 66, 67, 67, 67, 64, 1, 65, 33, 2, 106, 3, 78, 9, 65, 65, 66,
            67, 68, 69, 70, 71, 71, 4, 88, 65, 3, 1, 2, 3, 5, 93, 65, 78, 1, 1, 3, 97, 112, 97, 6, 93, 65, 65, 1, 2, 2,
            7, 93, 65, 87, 1, 4, 3, 65, 2, 65, 3, 65, 4, 8, 88, 65, 3, 4, 2, 3, 64 };

    public static final byte[] nakedTypePofStream = new byte[] { -83, 15, 0, 1, 65, -86, -86, 5, 2, 65, -69, -18, 93,
            3, 78, 9, 65, 65, 65, 66, 66, 66, 67, 67, 67, 64 };

    /**
     * The {@link ConfigurablePofContext} that we're using for testing out the {@link ReflectiveSerializer}s.
     */
    private static ConfigurablePofContext pofContext;


    @BeforeClass
    public static void beforeClass() throws Exception
    {
        pofContext = new ConfigurablePofContext("com/oracle/coherence/common/serialization/pof-config.xml");
    }


    @Test
    public void testSimpleTypeSerialization() throws IOException
    {
        SimpleType type = new SimpleType();
        type.init();

        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());

        pofContext.serialize(buffer.getBufferOutput(), type);

        Assert.assertEquals(simpleTypePofStream.length, buffer.length());
        Assert.assertTrue(Arrays.equals(simpleTypePofStream, buffer.toByteArray()));
    }


    @Test
    public void testSimpleTypeDeserialization() throws IOException
    {
        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());
        buffer.write(0, simpleTypePofStream);

        SimpleType type = (SimpleType) pofContext.deserialize(buffer.getReadBuffer().getBufferInput());

        type.verify();
    }


    @Test
    public void testComplexTypeSerialization() throws IOException
    {
        ComplexType type = new ComplexType();
        type.init();

        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());

        pofContext.serialize(buffer.getBufferOutput(), type);

        ComplexType type2 = (ComplexType) pofContext.deserialize(buffer.getReadBuffer().getBufferInput());

        Assert.assertEquals(type, type2);
         }

    @Test
    public void testCollectionTypeSerialization() throws IOException
    {
        CollectionType type = new CollectionType();
        type.init();

        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());

        pofContext.serialize(buffer.getBufferOutput(), type);

        Assert.assertEquals(collectionTypePofStream.length, buffer.length());
        Assert.assertTrue(Arrays.equals(collectionTypePofStream, buffer.toByteArray()));
    }


    @Test
    public void testCollectionTypeDeserialization() throws IOException
    {
        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());
        buffer.write(0, collectionTypePofStream);

        CollectionType type = (CollectionType) pofContext.deserialize(buffer.getReadBuffer().getBufferInput());

        type.verify();
    }


    @Test
    public void testVersionedTypeV0Serialization() throws IOException
    {
        VersionedType type = new VersionedType();
        type.init(0);

        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());

        //force the serializer for the class to use version 0.
        int userTypeId = pofContext.getUserTypeIdentifier(type);
        ReflectiveSerializer reflectiveSerializer = (ReflectiveSerializer) pofContext.getPofSerializer(userTypeId);
        ReflectedSerializer reflectedPofSerializer = reflectiveSerializer.getReflectedPofContext()
                .ensurePofSerializer(type.getClass(), pofContext);
        reflectedPofSerializer.setVersion(0);

        buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());

        pofContext.serialize(buffer.getBufferOutput(), type);

        ((DefaultReflectedSerializer) reflectedPofSerializer).resetVersion();

        Assert.assertEquals(versionedTypePofStreamV0.length, buffer.length());
        Assert.assertTrue(Arrays.equals(versionedTypePofStreamV0, buffer.toByteArray()));
    }


    @Test
    public void testVersionedTypeV0Deserialization() throws IOException
    {
        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());
        buffer.write(0, versionedTypePofStreamV0);

        VersionedType type = (VersionedType) pofContext.deserialize(buffer.getReadBuffer().getBufferInput());

        type.verify(0);
    }


    @Test
    public void testVersionedTypeV1Serialization() throws IOException
    {
        VersionedType type = new VersionedType();
        int userTypeId = pofContext.getUserTypeIdentifier(type);
        ReflectiveSerializer reflectiveSerializer = (ReflectiveSerializer) pofContext.getPofSerializer(userTypeId);
        ReflectedSerializer reflectedPofSerializer = reflectiveSerializer.getReflectedPofContext()
                .ensurePofSerializer(type.getClass(), pofContext);


        ((DefaultReflectedSerializer) reflectedPofSerializer).resetVersion();

        type.init(1);

        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());

        pofContext.serialize(buffer.getBufferOutput(), type);

        Assert.assertEquals(versionedTypePofStreamV1.length, buffer.length());
        Assert.assertTrue(Arrays.equals(versionedTypePofStreamV1, buffer.toByteArray()));
    }


    @Test
    public void testVersionedTypeV1Deserialization() throws IOException
    {

        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());
        buffer.write(0, versionedTypePofStreamV1);

        VersionedType type = (VersionedType) pofContext.deserialize(buffer.getReadBuffer().getBufferInput());

        type.verify(1);
    }


    @Test
    public void testVersionedTypeV0toV1Upgrade() throws IOException
    {
        //deserialize a V0
        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());
        buffer.write(0, versionedTypePofStreamV0);

        VersionedType type = (VersionedType) pofContext.deserialize(buffer.getReadBuffer().getBufferInput());
        type.verify(0);

        //update V0 to V1
        type.updateTo(1);

        //serialize type (should now be V1)
        buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());

        pofContext.serialize(buffer.getBufferOutput(), type);

        Assert.assertEquals(versionedTypePofStreamV1.length, buffer.length());
        Assert.assertTrue(Arrays.equals(versionedTypePofStreamV1, buffer.toByteArray()));
    }


    @Test
    public void testVersionedTypeV1toV0Downgrade() throws IOException
    {
        //deserialize a V1
        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());
        buffer.write(0, versionedTypePofStreamV1);

        //force the serializer to think it's version 0 so we can downgrade when we deserialize
        int userTypeId = pofContext.getUserTypeIdentifier(VersionedType.class);
        ReflectiveSerializer reflectiveSerializer = (ReflectiveSerializer) pofContext.getPofSerializer(userTypeId);
        ReflectedSerializer reflectedPofSerializer = reflectiveSerializer.getReflectedPofContext()
                .ensurePofSerializer(VersionedType.class, pofContext);
        reflectedPofSerializer.setVersion(0);

        VersionedType type = (VersionedType) pofContext.deserialize(buffer.getReadBuffer().getBufferInput());
        type.verify(0);

        Assert.assertEquals(type.remainder.getFromVersion(), 1);

        //now serialize back (should be the same as V1)
        buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());

        pofContext.serialize(buffer.getBufferOutput(), type);

        Assert.assertEquals(versionedTypePofStreamV1.length, buffer.length());
        Assert.assertTrue(Arrays.equals(versionedTypePofStreamV1, buffer.toByteArray()));
    }


    @Test
    public void testInheritanceSerialization() throws IOException
    {
        BasicType type = new BasicType();
        type.init();

        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());

        pofContext.serialize(buffer.getBufferOutput(), type);

        Assert.assertEquals(basicTypePofStream.length, buffer.length());
        Assert.assertTrue(Arrays.equals(basicTypePofStream, buffer.toByteArray()));
    }

    @Test
    public void testNakedSerialization()
            throws IOException
        {
        NakedType type = new NakedType();
        type.init();

        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());

        pofContext.serialize(buffer.getBufferOutput(), type);

        NakedType copy = (NakedType) pofContext.deserialize(buffer.getReadBuffer().getBufferInput());

        Assert.assertEquals(type, copy);

        Assert.assertEquals(nakedTypePofStream.length, buffer.length());

        Assert.assertTrue(Arrays.equals(nakedTypePofStream, buffer.toByteArray()));
        }

    @Test
    public void testPortableObjectSerialization()
            throws IOException
        {
        PortableObjectType type = new PortableObjectType();
        type.init();

        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());

        pofContext.serialize(buffer.getBufferOutput(), type);

        buffer.getBufferOutput().flush();
        PortableObjectType copy = (PortableObjectType) pofContext.deserialize(buffer.getReadBuffer().getBufferInput());

        Assert.assertEquals(type, copy);
        }


    @Test
    public void testInheritanceDeserialization() throws IOException
    {
        ByteArrayWriteBuffer buffer = new ByteArrayWriteBuffer(1000);
        Assert.assertEquals(0, buffer.length());
        buffer.write(0, basicTypePofStream);

        BasicType type = (BasicType) pofContext.deserialize(buffer.getReadBuffer().getBufferInput());

        type.verify();
    }


    public static String createByteArrayStatment(byte[] array)
    {
        return String.format("public static final byte[] var = new byte[] {%s};", dumpArrayAsString(array));
    }


    public static String dumpArrayAsString(byte[] array)
    {
        StringBuilder builder = new StringBuilder();
        for (byte b : array)
        {
            if (builder.length() > 0)
            {
                builder.append(String.format(", %d", b));
            }
            else
            {
                builder.append(String.format("%d", b));
            }
        }

        return builder.toString();
    }
}
