package com.oracle.coherence.common.runtime.process;

import com.oracle.coherence.common.classloader.ChildFirstClassLoader;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

/**
 * @author Jonathan Knight
 */
public class InProcessBuilderTest {
    private String className = ProcessRunnableStub.class.getCanonicalName();
    private String startMethod = "start";
    private String stopMethod = "stop";
    String[] args = new String[]{"A","B","C"};

    @Test
    public void shouldCreateInternalProcessWithCorrectClass() throws Exception {
        InProcessBuilder builder = new InProcessBuilder("Test", className, startMethod, stopMethod, args);
        InternalProcess process = (InternalProcess) builder.start();

        assertThat(process.getClassName(), is(className));
    }

    @Test
    public void shouldCreateInternalProcessWithCorrectStartMethod() throws Exception {
        InProcessBuilder builder = new InProcessBuilder("Test", className, startMethod, stopMethod, args);
        InternalProcess process = (InternalProcess) builder.start();

        assertThat(process.getStartMethodName(), is(startMethod));
    }

    @Test
    public void shouldCreateInternalProcessWithCorrectStopMethod() throws Exception {
        InProcessBuilder builder = new InProcessBuilder("Test", className, startMethod, stopMethod, args);
        InternalProcess process = (InternalProcess) builder.start();

        assertThat(process.getStopMethodName(), is(stopMethod));
    }

    @Test
    public void shouldCreateInternalProcessWithCorrectArgs() throws Exception {
        InProcessBuilder builder = new InProcessBuilder("Test", className, startMethod, stopMethod, args);
        InternalProcess process = (InternalProcess) builder.start();

        assertThat(process.getArgs(), is(arrayContaining("A","B","C")));
    }

    @Test
    public void shouldUseCreateClassLoaderWithCorrectClassPath() throws Exception {
        String classpath = "coherence.jar" + File.pathSeparatorChar + "coherence-common.jar";

        InProcessBuilder builder = new InProcessBuilder("Test", className, startMethod, stopMethod, args);
        builder.environment().put("CLASSPATH", classpath);

        ChildFirstClassLoader classLoader = (ChildFirstClassLoader) builder.createClassLoader();
        assertThat(classLoader.getURLs(), arrayContainingInAnyOrder(new URL("file:/coherence.jar"), new URL("file:/coherence-common.jar")));
    }

    @Test
    public void shouldCreateClassLoaderWithBulkAddedSystemProperties() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("test.1", "value.1");
        properties.setProperty("test.2", "value.2");

        InProcessBuilder builder = new InProcessBuilder("Test", className, startMethod, stopMethod, args);
        builder.systemProperties().putAll(properties);

        ChildFirstClassLoader classLoader = (ChildFirstClassLoader) builder.createClassLoader();
        assertThat(classLoader.getProperties().getProperty("test.1"), is("value.1"));
        assertThat(classLoader.getProperties().getProperty("test.2"), is("value.2"));
    }

    @Test
    public void shouldCreateClassLoaderWithIndividualAddedSystemProperties() throws Exception {
        InProcessBuilder builder = new InProcessBuilder("Test", className, startMethod, stopMethod, args);
        builder.systemProperty("test.1", "value.1");
        builder.systemProperty("test.2", "value.2");

        ChildFirstClassLoader classLoader = (ChildFirstClassLoader) builder.createClassLoader();
        assertThat(classLoader.getProperties().getProperty("test.1"), is("value.1"));
        assertThat(classLoader.getProperties().getProperty("test.2"), is("value.2"));
    }
}
