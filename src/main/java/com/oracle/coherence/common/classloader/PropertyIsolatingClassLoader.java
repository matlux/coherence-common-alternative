package com.oracle.coherence.common.classloader;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

/**
 * A class loader that is used in combination with {@link ClassLoaderProperties}
 * to isolate System property sets to a specific classloader. This allows code that makes use of System properties
 * to be run in sand box where it will not affect the System properties of other code in the same JVM.
 *  
 * @author <a href="jk@thegridman.com">Jonathan Knight</a>
 */
public class PropertyIsolatingClassLoader extends URLClassLoader {

    private Properties properties = new Properties();

    public PropertyIsolatingClassLoader() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public PropertyIsolatingClassLoader(ClassLoader parent) {
        this(new URL[0], parent);
    }

    public PropertyIsolatingClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties props) {
        properties.putAll(props);
    }

}
