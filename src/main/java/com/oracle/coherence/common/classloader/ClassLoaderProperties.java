package com.oracle.coherence.common.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * An extension of the {@link java.util.Properties} class that is used in combination with {@link PropertyIsolatingClassLoader}
 * to isolate System property sets to a specific classloader. This allows code that makes use of System properties
 * to be run in sand box where it will not affect the System properties of other code in the same JVM.
 *
 * @author <a href="jk@thegridman.com">Jonathan Knight</a>
 */
public class ClassLoaderProperties extends Properties {
    private static ClassLoaderProperties sInstance = new ClassLoaderProperties();

    public static ClassLoaderProperties getInstance() { return sInstance; }

    public static void use() {
        System.setProperties(ClassLoaderProperties.getInstance());
    }

    private Properties systemProperties;

    private ClassLoaderProperties() {
        systemProperties = System.getProperties();
    }

    Properties getPropertiesToUse() {
        Properties props = null;
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        while(props == null && cl != null) {
            if (cl instanceof PropertyIsolatingClassLoader) {
                props = ((PropertyIsolatingClassLoader)cl).getProperties();
            } else {
                cl = cl.getParent();
            }
        }

        if (props == null) {
            props = systemProperties;
        }

        return props;
    }

    @Override
    public Object setProperty(String key, String value) {
        return getPropertiesToUse().setProperty(key, value);
    }

    @Override
    public void load(Reader reader) throws IOException {
        getPropertiesToUse().load(reader);
    }

    @Override
    public void load(InputStream inStream) throws IOException {
        getPropertiesToUse().load(inStream);
    }

    @Override
    public void store(Writer writer, String comments) throws IOException {
        getPropertiesToUse().store(writer, comments);
    }

    @Override
    public void store(OutputStream out, String comments) throws IOException {
        getPropertiesToUse().store(out, comments);
    }

    @Override
    public void loadFromXML(InputStream in) throws IOException {
        getPropertiesToUse().loadFromXML(in);
    }

    @Override
    public void storeToXML(OutputStream os, String comment) throws IOException {
        getPropertiesToUse().storeToXML(os, comment);
    }

    @Override
    public void storeToXML(OutputStream os, String comment, String encoding) throws IOException {
        getPropertiesToUse().storeToXML(os, comment, encoding);
    }

    @Override
    public String getProperty(String key) {
        return getPropertiesToUse().getProperty(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return getPropertiesToUse().getProperty(key, defaultValue);
    }

    @Override
    public Enumeration<?> propertyNames() {
        return getPropertiesToUse().propertyNames();
    }

    @Override
    public Set<String> stringPropertyNames() {
        return getPropertiesToUse().stringPropertyNames();
    }

    @Override
    public void list(PrintStream out) {
        getPropertiesToUse().list(out);
    }

    @Override
    public void list(PrintWriter out) {
        getPropertiesToUse().list(out);
    }

    @Override
    public int size() {
        return getPropertiesToUse().size();
    }

    @Override
    public boolean isEmpty() {
        return getPropertiesToUse().isEmpty();
    }

    @Override
    public Enumeration<Object> keys() {
        return getPropertiesToUse().keys();
    }

    @Override
    public Enumeration<Object> elements() {
        return getPropertiesToUse().elements();
    }

    @Override
    public boolean contains(Object value) {
        return getPropertiesToUse().contains(value);
    }

    @Override
    public boolean containsValue(Object value) {
        return getPropertiesToUse().containsValue(value);
    }

    @Override
    public boolean containsKey(Object key) {
        return getPropertiesToUse().containsKey(key);
    }

    @Override
    public Object get(Object key) {
        return getPropertiesToUse().get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return getPropertiesToUse().put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return getPropertiesToUse().remove(key);
    }

    @Override
    public void putAll(Map<?, ?> t) {
        getPropertiesToUse().putAll(t);
    }

    @Override
    public void clear() {
        getPropertiesToUse().clear();
    }

    @Override
    public String toString() {
        return getPropertiesToUse().toString();
    }

    @Override
    public Set<Object> keySet() {
        return getPropertiesToUse().keySet();
    }

    @Override
    public Set<Map.Entry<Object, Object>> entrySet() {
        return getPropertiesToUse().entrySet();
    }

    @Override
    public Collection<Object> values() {
        return getPropertiesToUse().values();
    }

    @Override
    public boolean equals(Object o) {
        return getPropertiesToUse().equals(o);
    }

    @Override
    public int hashCode() {
        return getPropertiesToUse().hashCode();
    }
}
