package com.oracle.coherence.common.classloader;

import com.tangosol.util.Base;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * This is a ChildFirst (also called ParentLast) ClassLoader.
 * It allows us to Isolate things (eg Coherence nodes).
 *
 * @author Andrew Wilson
 * @author <a href="jk@thegridman.com">Jonathan Knight</a>
 */
public class ChildFirstClassLoader extends PropertyIsolatingClassLoader
        implements MBeanServerProvider, ClassLoaderOutputStream.ContextProvider {

    private Set<String> packagesToLoadFromParent = new HashSet<String>();
	private Map<String, Class> loadedClasses = new HashMap<String, Class>();
	private Map<String, URL> loadedResources = new HashMap<String, URL>();
    private ClassLoader root;
    private ClassLoader parent;
    private MBeanServer mbs;
    private ClassLoaderOutputStream.Context context;

    public static ChildFirstClassLoader newInstance(String applicationName, String classpath, Properties localProperties) throws Exception {
        if (classpath == null || classpath.length() == 0) {
            classpath = System.getProperty("java.class.path");
        }
        String[] vals = classpath.split(System.getProperty("path.separator"));
        List<URL> urls = new ArrayList<URL>();
        for (String val : vals) {
            String ending = val.endsWith(".jar") ? "" : "/";
            urls.add(new URL("file:///" + val + ending));
        }

        ClassLoader parentLoader = ChildFirstClassLoader.class.getClassLoader();
        ChildFirstClassLoader loader = new ChildFirstClassLoader(urls.toArray(new URL[urls.size()]), parentLoader);
        loader.setContext(new ClassLoaderOutputStream.Context(applicationName));
        loader.setProperties(System.getProperties());

        ClassLoaderOutputStream.use();

        loader.setProperties(localProperties);
        ClassLoaderProperties.use();

        return loader;
    }

    @Override
    public ClassLoaderOutputStream.Context getContext() {
        return context;
    }

    public void setContext(ClassLoaderOutputStream.Context context) {
        this.context = context;
    }

    public void addPackageToLoadFromParent(String packagePrefix) {
        packagesToLoadFromParent.add(packagePrefix);
    }

    public ChildFirstClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, null);
        this.parent = parent;
        while(parent.getParent() != null) {
            root = parent.getParent();
            parent = parent.getParent();
        }
    }

    @Override
    public MBeanServer getMBeanServer(String domain) {
        if (mbs == null) {
            mbs = MBeanServerFactory.createMBeanServer(domain);
            Properties props = getProperties();

            if (props.containsKey("com.sun.management.jmxremote")) {
                startRmiConnector(mbs);
            }
        }
        return mbs;
    }

    public JMXConnectorServer startRmiConnector(MBeanServer mbs) {
        try {
            Properties props = getProperties();

            String hostName = props.getProperty("java.rmi.server.hostname");
            int port = Integer.parseInt(props.getProperty("com.sun.management.jmxremote.port"));
            String shouldAuthenticate = props.getProperty("com.sun.management.jmxremote.authenticate");
            String authFileName = props.getProperty("com.sun.management.jmxremote.password.file");
            String accessFileName = props.getProperty("com.sun.management.jmxremote.access.file");
            String shouldUseSSL = props.getProperty("com.sun.management.jmxremote.ssl");

            LocateRegistry.createRegistry(port);

            String remoteJMXConnectionUrl = String.format("service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi", hostName, port);
            System.err.println("JMX URL: " + remoteJMXConnectionUrl);

            JMXServiceURL url = new JMXServiceURL(remoteJMXConnectionUrl);

            if ((shouldAuthenticate != null) && (shouldAuthenticate.equalsIgnoreCase("true"))) {
                String javaHome = System.getProperty("java.home");
                if (authFileName == null) {
                    authFileName = javaHome + "/lib/management/jmxremote.password";
                }
                if (accessFileName == null) {
                    accessFileName = javaHome + "/lib/management/jmxremote.access";
                }
            }

            Map<String,Object> mapEnv = new HashMap<String,Object>();

            if ((authFileName != null) && (mapEnv.get("jmx.remote.x.password.file") == null)) {
                mapEnv.put("jmx.remote.x.password.file", authFileName);
            }

            if ((accessFileName != null) && (mapEnv.get("jmx.remote.x.access.file") == null)) {
                mapEnv.put("jmx.remote.x.access.file", accessFileName);
            }

            if ((shouldUseSSL != null) && (shouldUseSSL.equalsIgnoreCase("true"))) {
                try {
                    if (mapEnv.get("jmx.remote.rmi.client.socket.factory") == null) {
                        mapEnv.put("jmx.remote.rmi.client.socket.factory", Class.forName("javax.rmi.ssl.SslRMIClientSocketFactory").newInstance());
                    }

                    if (mapEnv.get("jmx.remote.rmi.server.socket.factory") == null) {
                        mapEnv.put("jmx.remote.rmi.server.socket.factory", Class.forName("javax.rmi.ssl.SslRMIServerSocketFactory").newInstance());
                    }

                } catch (ClassNotFoundException e) {
                    String sMsg = "JMXConnectorServer not started. SSL security requires the Java Dynamic Management Kit or Java 1.5.";
                    throw Base.ensureRuntimeException(e, sMsg);
                }
            }

            JMXConnectorServer connector = JMXConnectorServerFactory.newJMXConnectorServer(url, mapEnv, mbs);

            connector.start();
            return connector;
        } catch (Exception e) {
            throw Base.ensureRuntimeException(e, "Could not start JMXConnectorServer");
        }
    }

	public Class loadClass(String name) throws ClassNotFoundException {
        if (shouldLoadFromParent(name) && parent != null) {
            return parent.loadClass(name);
        }

		Class c = loadedClasses.get(name);
		if (c == null) {
            try {
                c = super.loadClass(name);               
            } catch(Throwable t) {
                c = root.loadClass(name);
            }
            loadedClasses.put(name, c);
		}
		return c;
	}

    private boolean shouldLoadFromParent(String className) {
        for (String prefix : packagesToLoadFromParent) {
            if (className.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

	protected PermissionCollection getPermissions(CodeSource codesource) {
		Permissions permissions = new Permissions();
		permissions.add(new AllPermission());
		return permissions;
	}

	public URL getResource(String name) {

		URL c = loadedResources.get(name);

		if (c == null) {
			c = findResource(name);
			loadedResources.put(name, c);
		}

		if (c == null) {
			c = super.getResource(name);
		}

		return c;
	}


}

