/*
 * File: EC2AddressProviderTest.java
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

package com.oracle.coherence.cloud.amazon;

import com.amazonaws.auth.AWSCredentials;

import com.amazonaws.services.ec2.AmazonEC2;

import com.amazonaws.services.ec2.model.Address;
import com.amazonaws.services.ec2.model.DescribeAddressesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;

import com.oracle.coherence.common.network.Constants;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.io.IOException;

import java.net.InetSocketAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>A {@link EC2AddressProviderTest} class testing the combinations of Elastic IP to instance mappings that the EC2 API can yield. </p>
 *
 * @author Christer Fahlgren
 */
public class EC2AddressProviderTest
{
    /**
     * Tests generation of WKA list where there is one Elastic IP matching an instance.
     */
    @Test
    public void generateWKAListTest()
    {
        AmazonEC2               ec2              = mock(AmazonEC2.class);
        DescribeInstancesResult descResult       = mock(DescribeInstancesResult.class);
        ArrayList<Reservation>  reservationsList = new ArrayList<Reservation>();
        Reservation             mockReservation  = mock(Reservation.class);
        Instance                mockInstance     = mock(Instance.class);

        reservationsList.add(mockReservation);
        stub(ec2.describeInstances()).toReturn(descResult);
        stub(descResult.getReservations()).toReturn(reservationsList);

        ArrayList<Instance> instanceList = new ArrayList<Instance>();

        instanceList.add(mockInstance);
        stub(mockReservation.getInstances()).toReturn(instanceList);

        ArrayList<Address> elasticIPList        = new ArrayList<Address>();
        Address            mockElasticIPAddress = mock(Address.class);

        elasticIPList.add(mockElasticIPAddress);

        DescribeAddressesResult descAddressesResult = mock(DescribeAddressesResult.class);

        stub(ec2.describeAddresses()).toReturn(descAddressesResult);
        stub(descAddressesResult.getAddresses()).toReturn(elasticIPList);

        stub(mockElasticIPAddress.getInstanceId()).toReturn("wka-instance");
        stub(mockInstance.getInstanceId()).toReturn("wka-instance");

        stub(mockInstance.getPrivateIpAddress()).toReturn(Constants.LOCAL_HOST);

        EC2AddressProvider      addressProvider = new EC2AddressProvider("dummy");

        List<InetSocketAddress> wkaList         = addressProvider.generateWKAList(ec2);

        assertTrue(wkaList.size() > 0);
        assertEquals("127.0.0.1", wkaList.get(0).getAddress().getHostAddress());
        assertTrue(wkaList.get(0).getPort() == 8088);
    }


    /**
     * Tests generation of WKA list where there is one Elastic IP matching an instance and we set the port to a non-default port.
     */
    @Test
    public void generateWKAListTestSettingPort()
    {
        System.setProperty("tangosol.coherence.ec2addressprovider.port", "9999");

        AmazonEC2               ec2              = mock(AmazonEC2.class);
        DescribeInstancesResult descResult       = mock(DescribeInstancesResult.class);
        ArrayList<Reservation>  reservationsList = new ArrayList<Reservation>();
        Reservation             mockReservation  = mock(Reservation.class);
        Instance                mockInstance     = mock(Instance.class);

        reservationsList.add(mockReservation);
        stub(ec2.describeInstances()).toReturn(descResult);
        stub(descResult.getReservations()).toReturn(reservationsList);

        ArrayList<Instance> instanceList = new ArrayList<Instance>();

        instanceList.add(mockInstance);
        stub(mockReservation.getInstances()).toReturn(instanceList);

        ArrayList<Address> elasticIPList        = new ArrayList<Address>();
        Address            mockElasticIPAddress = mock(Address.class);

        elasticIPList.add(mockElasticIPAddress);

        DescribeAddressesResult descAddressesResult = mock(DescribeAddressesResult.class);

        stub(ec2.describeAddresses()).toReturn(descAddressesResult);
        stub(descAddressesResult.getAddresses()).toReturn(elasticIPList);

        stub(mockElasticIPAddress.getInstanceId()).toReturn("wka-instance");
        stub(mockInstance.getInstanceId()).toReturn("wka-instance");

        stub(mockInstance.getPrivateIpAddress()).toReturn(Constants.LOCAL_HOST);

        EC2AddressProvider      addressProvider = new EC2AddressProvider("dummy");

        List<InetSocketAddress> wkaList         = addressProvider.generateWKAList(ec2);

        assertTrue(wkaList.size() > 0);
        assertEquals("127.0.0.1", wkaList.get(0).getAddress().getHostAddress());
        assertTrue(wkaList.get(0).getPort() == 9999);
    }


    /**
     * Tests generation of WKA list where there is no match between running instances and the  Elastic IP.
     */
    @Test(expected = RuntimeException.class)
    public void generateWKAListTestNoMatch()
    {
        AmazonEC2               ec2              = mock(AmazonEC2.class);
        DescribeInstancesResult descResult       = mock(DescribeInstancesResult.class);
        ArrayList<Reservation>  reservationsList = new ArrayList<Reservation>();
        Reservation             mockReservation  = mock(Reservation.class);
        Instance                mockInstance     = mock(Instance.class);

        reservationsList.add(mockReservation);
        stub(ec2.describeInstances()).toReturn(descResult);
        stub(descResult.getReservations()).toReturn(reservationsList);

        ArrayList<Instance> instanceList = new ArrayList<Instance>();

        instanceList.add(mockInstance);
        stub(mockReservation.getInstances()).toReturn(instanceList);

        ArrayList<Address> elasticIPList        = new ArrayList<Address>();
        Address            mockElasticIPAddress = mock(Address.class);

        elasticIPList.add(mockElasticIPAddress);

        DescribeAddressesResult descAddressesResult = mock(DescribeAddressesResult.class);

        stub(ec2.describeAddresses()).toReturn(descAddressesResult);
        stub(descAddressesResult.getAddresses()).toReturn(elasticIPList);

        stub(mockElasticIPAddress.getInstanceId()).toReturn("elastic-instance");
        stub(mockInstance.getInstanceId()).toReturn("another-instance");

        stub(mockInstance.getPrivateIpAddress()).toReturn(Constants.LOCAL_HOST);

        EC2AddressProvider      addressProvider = new EC2AddressProvider("dummy");

        List<InetSocketAddress> wkaList         = addressProvider.generateWKAList(ec2);

        assertTrue(wkaList.size() == 0);

    }


    /**
     * Tests generation of WKA list where there is no Elastic IP Address.
     */
    @Test(expected = RuntimeException.class)
    public void generateWKAListTestNoElasticIP()
    {
        AmazonEC2               ec2              = mock(AmazonEC2.class);
        DescribeInstancesResult descResult       = mock(DescribeInstancesResult.class);
        ArrayList<Reservation>  reservationsList = new ArrayList<Reservation>();
        Reservation             mockReservation  = mock(Reservation.class);
        Instance                mockInstance     = mock(Instance.class);

        reservationsList.add(mockReservation);
        stub(ec2.describeInstances()).toReturn(descResult);
        stub(descResult.getReservations()).toReturn(reservationsList);

        ArrayList<Instance> instanceList = new ArrayList<Instance>();

        instanceList.add(mockInstance);
        stub(mockReservation.getInstances()).toReturn(instanceList);

        ArrayList<Address>      elasticIPList       = new ArrayList<Address>();
        DescribeAddressesResult descAddressesResult = mock(DescribeAddressesResult.class);

        stub(ec2.describeAddresses()).toReturn(descAddressesResult);
        stub(descAddressesResult.getAddresses()).toReturn(elasticIPList);

        stub(mockInstance.getInstanceId()).toReturn("another-instance");

        stub(mockInstance.getPrivateIpAddress()).toReturn(Constants.LOCAL_HOST);

        EC2AddressProvider      addressProvider = new EC2AddressProvider("dummy");

        List<InetSocketAddress> wkaList         = addressProvider.generateWKAList(ec2);

        assertTrue(wkaList.size() == 0);

    }


    /**
     * Tests passing in credentials using system properties.
     *
     * @throws IOException if reading a resource fails
     */
    @Test
    public void testBasicCredentials() throws IOException
    {
        System.setProperty("tangosol.coherence.ec2addressprovider.accesskey", "accessKey");
        System.setProperty("tangosol.coherence.ec2addressprovider.secretkey", "secretKey");

        EC2AddressProvider addressProvider = new EC2AddressProvider("dummy");
        AWSCredentials     credentials     = addressProvider.determineCredentials();

        assertTrue(credentials.getAWSAccessKeyId().equals("accessKey"));
        assertTrue(credentials.getAWSSecretKey().equals("secretKey"));
    }


    /**
     * Tests passing in credentials using system properties.
     * Expects a RuntimeException because there are no credentials.
     *
     * @throws IOException if reading a resource fails
     */
    @Test(expected = RuntimeException.class)
    public void testNoCredentials() throws IOException
    {
        System.setProperty("tangosol.coherence.ec2addressprovider.accesskey", "");
        System.setProperty("tangosol.coherence.ec2addressprovider.secretkey", "");

        EC2AddressProvider addressProvider = new EC2AddressProvider("dummy");

        addressProvider.determineCredentials();
    }


    /**
     * Tests passing in credentials using resource file.
     *
     * @throws IOException if reading a resource fails
     */
    @Test
    public void testResourceCredentials() throws IOException
    {
        System.setProperty("tangosol.coherence.ec2addressprovider.propertyfile",
                           "com/oracle/coherence/cloud/amazon/ec2credentials.properties");

        EC2AddressProvider addressProvider = new EC2AddressProvider("dummy");
        AWSCredentials     credentials     = addressProvider.determineCredentials();

        assertTrue(credentials.getAWSAccessKeyId().equals("properties-accesskey"));
        assertTrue(credentials.getAWSSecretKey().equals("properties-secretkey"));

    }
}
