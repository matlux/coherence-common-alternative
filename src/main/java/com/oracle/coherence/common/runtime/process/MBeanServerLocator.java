package com.oracle.coherence.common.runtime.process;

import com.oracle.coherence.common.runtime.AbstractJavaApplicationSchema;
import com.tangosol.net.InetAddressHelper;
import com.tangosol.net.management.MBeanServerFinder;
import com.tangosol.util.Base;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnectorServer;
import javax.management.remote.rmi.RMIJRMPServerImpl;
import javax.management.remote.rmi.RMIServerImpl;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jonathan Knight
 */
public class MBeanServerLocator implements MBeanServerFinder {
    /**
     * <p>The {@link java.util.logging.Logger} for this class.</p>
     */
    private static final Logger logger = Logger.getLogger(MBeanServerLocator.class.getName());

    private static Map<String,MBeanServer> servers = new HashMap<String, MBeanServer>();

    @Override
    public MBeanServer findMBeanServer(String domain) {
        if (!servers.containsKey(domain)) {
            MBeanServer mbs = MBeanServerFactory.createMBeanServer(domain);
            if (System.getProperties().containsKey("com.sun.management.jmxremote")) {
                try {
                    startRmiConnector(mbs);
                } catch (Exception e) {
                    throw Base.ensureRuntimeException(e);
                }
            }
            servers.put(domain, mbs);
        }

        return servers.get(domain);
    }

    public JMXConnectorServer startRmiConnector(MBeanServer mbs) throws Exception {
        // Ensure cryptographically strong random number generator used
        // to choose the object number - see java.rmi.server.ObjID
        //
        System.setProperty("java.rmi.server.randomIDs", "true");

        // Start an RMI registry on port specified by example.rmi.agent.port
        // (default 3000).
        //
        final int port = Integer.parseInt(System.getProperty("com.sun.management.jmxremote.port", "9000"));
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Create RMI registry on port " + port);
        }

        // We create a couple of RMIClientSocketFactory and
        // RMIServerSocketFactory. We will use the same factories to export
        // the RMI Registry and the JMX RMI Connector Server objects. This
        // will allow us to use the same port for all the exported objects.
        // If we didn't use the same factories everywhere, we would have to
        // use at least two ports, because two different RMI Socket Factories
        // cannot share the same port.
        //
        final RMIClientSocketFactory csf = new JMXRMIClientSocketFactory();
        final RMIServerSocketFactory ssf = new JMXRMIServerSocketFactory();

        // Create the RMI Registry using the SSL socket factories above.
        // In order to use a single port, we must use these factories
        // everywhere, or nowhere. Since we want to use them in the JMX
        // RMI Connector server, we must also use them in the RMI Registry.
        // Otherwise, we wouldn't be able to use a single port.
        //
        final Registry registry = LocateRegistry.createRegistry(port, csf, ssf);


        // Environment map.
        HashMap<String, Object> env = new HashMap<String, Object>();

        // Manually creates and binds a JMX RMI Connector Server stub with the
        // registry created above: the port we pass here is the port that can
        // be specified in "service:jmx:rmi://"+hostname+":"+port - where the
        // RMI server stub and connection objects will be exported.
        // Here we choose to use the same port as was specified for the
        // RMI Registry. We can do so because we're using \*the same\* client
        // and server socket factories, for the registry itself \*and\* for this
        // object.
        //
        final RMIServerImpl stub = new RMIJRMPServerImpl(port, csf, ssf, env);

        // Create an RMI connector server.
        //
        // As specified in the JMXServiceURL the RMIServer stub will be
        // registered in the RMI registry running in the local host on
        // port <port> with the name "jmxrmi". This is the same name the
        // out-of-the-box management agent uses to register the RMIServer
        // stub too.
        //
        // The port specified in "service:jmx:rmi://"+hostname+":"+port
        // is the second port, where RMI connection objects will be exported.
        // Here we use the same port as that we choose for the RMI registry.
        // The port for the RMI registry is specified in the second part
        // of the URL, in "rmi://"+hostname+":"+port
        //
        // We construct a JMXServiceURL corresponding to what we have done
        // for our stub...
        String hostname = System.getProperty(AbstractJavaApplicationSchema.JAVA_RMI_SERVER_HOSTNAME, null);
        if (hostname == null) {
            hostname = ((InetAddress) InetAddressHelper.getAllLocalAddresses().get(0)).getHostName();
        }
        String urlString = "service:jmx:rmi:///jndi/rmi://" + hostname + ":" + port + "/jmxrmi";
        final JMXServiceURL url = new JMXServiceURL(urlString);

        if (logger.isLoggable(Level.INFO)) {
            logger.info("Creating JMX server with URL: " + url);
        }

        // Now create the server manually....
        // We can't use the JMXConnectorServerFactory because of
        // http://bugs.sun.com/view_bug.do?bug_id=5107423
        //
        final JMXConnectorServer cs =
                new RMIConnectorServer(new JMXServiceURL("rmi", hostname, port), env, stub, mbs) {
                    @Override
                    public JMXServiceURL getAddress() {
                        return url;
                    }

                    @Override
                    public synchronized void start() throws IOException {
                        try {
                            registry.bind("jmxrmi", stub);
                        } catch (AlreadyBoundException x) {
                            final IOException io = new IOException(x.getMessage());
                            io.initCause(x);
                            throw io;
                        }
                        super.start();
                    }
                };


        // Start the RMI connector server.
        //
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Starting JMX server with URL: " + url);
        }
        cs.start();
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Started JMX server with URL: " + url);
        }
        return cs;
    }

    public static class JMXRMIClientSocketFactory implements RMIClientSocketFactory, Serializable {

        private transient SocketFactory factory;

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return getFactory().createSocket(host, port);
        }

        public SocketFactory getFactory() {
            if (factory == null) {
                factory = SocketFactory.getDefault();
            }
            return factory;
        }

        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj == this) return true;
            return this.getClass().equals(obj.getClass());
        }

        public int hashCode() {
            return this.getClass().hashCode();
        }

    }

    public static class JMXRMIServerSocketFactory implements RMIServerSocketFactory, Serializable {

        private transient ServerSocketFactory factory;

        @Override
        public ServerSocket createServerSocket(int port) throws IOException {
            return getFactory().createServerSocket(port);
        }

        public ServerSocketFactory getFactory() {
            if (factory == null) {
                factory = ServerSocketFactory.getDefault();
            }
            return factory;
        }

        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj == this) return true;
            return this.getClass().equals(obj.getClass());
        }

        public int hashCode() {
            return this.getClass().hashCode();
        }
    }}
