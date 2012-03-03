/*
 * File: AvailablePortIterator.java
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

package com.oracle.coherence.common.network;

import java.io.IOException;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An {@link AvailablePortIterator} is an {@link Iterator} implementation that lazily performs a
 * port scanning on a specified address to determine what {@link ServerSocket} and {@link DatagramSocket}
 * ports are available.
 *
 * @author Brian Oliver
 */
public class AvailablePortIterator implements Iterator<Integer>
{
    /**
     * The minimum port that can be used.
     */
    private final static int MINIMUM_PORT = 1;

    /**
     * The maximum port that can be used.
     */
    private final static int MAXIMUM_PORT = 65535;

    /**
     * The {@link InetAddress} on which the port scanning is occurring.
     */
    private InetAddress inetAddress;

    /**
     * The start of the port range in which scanning will occur.
     */
    private int portRangeStart;

    /**
     * The end of the port range in which scanning will occur.
     */
    private int portRangeEnd;

    /**
     * The currently determined next available port.
     */
    private int nextAvailablePort;


    /**
     * Standard Constructor (defaults to using the loop back address and full port range).
     *
     * @throws UnknownHostException When the loop back address can not be determined
     */
    public AvailablePortIterator() throws UnknownHostException
    {
        this(InetAddress.getByName(Constants.LOCAL_HOST), MINIMUM_PORT, MAXIMUM_PORT);
    }


    /**
     * <p>Standard Constructor (defaults to using the loop back address and starting port).</p>
     *
     * @param portRangeStart The port at which to start scanning (inclusive)
     *
     * @throws UnknownHostException When the loop back address can not be determined
     */
    public AvailablePortIterator(int portRangeStart) throws UnknownHostException
    {
        this(InetAddress.getByName(Constants.LOCAL_HOST), portRangeStart, MAXIMUM_PORT);
    }


    /**
     * Standard Constructor (defaults to using the loop back address but the specified port range).
     *
     * @param portRangeStart The port at which to start scanning (inclusive)
     * @param portRangeEnd The port at which to stop scanning (inclusive)
     *
     * @throws UnknownHostException When the loop back address can not be determined
     */
    public AvailablePortIterator(int portRangeStart,
                                 int portRangeEnd) throws UnknownHostException
    {
        this(InetAddress.getByName(Constants.LOCAL_HOST), portRangeStart, portRangeEnd);
    }


    /**
     * Standard Constructor.
     *
     * @param inetAddress The {@link InetAddress} on which to scan ports (typically loopback or localhost)
     * @param portRangeStart The port at which to start scanning (inclusive)
     * @param portRangeEnd The port at which to stop scanning (inclusive)
     */
    public AvailablePortIterator(InetAddress inetAddress,
                                 int portRangeStart,
                                 int portRangeEnd)
    {
        this.inetAddress    = inetAddress;
        this.portRangeStart = portRangeStart;
        this.portRangeEnd   = portRangeEnd;
        nextAvailablePort   = nextAvailablePortAfter(portRangeStart - 1);
    }


    /**
     * Standard Constructor.
     *
     * @param host The host name or IP address on which to scan (as a String)
     * @param portRangeStart The port at which to start scanning (inclusive)
     * @param portRangeEnd The port at which to stop scanning (inclusive)
     */
    public AvailablePortIterator(String host,
                                 int portRangeStart,
                                 int portRangeEnd) throws UnknownHostException
    {
        this(InetAddress.getByName(host), portRangeStart, portRangeEnd);
    }


    /**
     * Returns the {@link InetAddress} on which port scanning is occuring.
     *
     * @return An {@link InetAddress}
     */
    public InetAddress getInetAddress()
    {
        return inetAddress;
    }


    /**
     * <p>Determines if the specified port for the {@link InetAddress} is currently unbound and thus available.</p>
     *
     * @param port The port to test (for both TCP and UDP bindings)
     *
     * @return <code>true</code> if the port is currently unbound and thus assumed currently available,
     *         <code>false</code> otherwise.
     */
    private boolean isPortAvailable(int port)
    {
        if (port < portRangeStart || port > portRangeEnd)
        {
            return false;
        }
        else
        {
            // attempt to connect to both a server and datagram socket (we want both to be free)
            ServerSocket   serverSocket   = null;
            DatagramSocket datagramSocket = null;

            try
            {
                serverSocket = new ServerSocket(port, 1, inetAddress);
                serverSocket.setReuseAddress(true);
                datagramSocket = new DatagramSocket(port, inetAddress);
                datagramSocket.setReuseAddress(true);

                return true;
            }
            catch (IOException ioException)
            {
                // could not connect so the port is assumed unavailable
                return false;
            }
            finally
            {
                if (datagramSocket != null)
                {
                    datagramSocket.close();
                }

                if (serverSocket != null)
                {
                    try
                    {
                        serverSocket.close();
                    }
                    catch (IOException ioException)
                    {
                        // deliberately empty as failing here will have no effect on scanning for ports
                    }
                }
            }
        }
    }


    /**
     * Determines the next available port after the specified port.
     *
     * @param port The starting port (not included in the search)
     * @return The next available port or a value greater than the specified range if non-found
     */
    private int nextAvailablePortAfter(int port)
    {
        for (int i = port + 1; i <= portRangeEnd; i++)
        {
            if (isPortAvailable(i))
            {
                return i;
            }
        }

        return portRangeEnd + 1;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext()
    {
        return nextAvailablePort <= portRangeEnd;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Integer next()
    {
        if (hasNext())
        {
            int result = nextAvailablePort;

            nextAvailablePort = nextAvailablePortAfter(nextAvailablePort);

            return result;
        }
        else
        {
            throw new NoSuchElementException("Attempted to iterate outside of the range of available ports");
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("It's illegal to attempt to remove() a port from an AvailablePortIterator");
    }
}
