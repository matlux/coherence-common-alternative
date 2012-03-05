package com.oracle.coherence.common.runtime;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

/**
 * @author Jonathan Knight
 */
public class AbstractApplicationSchemaTest {

    @Test
    public void shouldSetExecutable() throws Exception {
        SimpleApplicationSchema schema = new SimpleApplicationSchema("test.sh");
        assertThat(schema.getExecutableName(), is("test.sh"));
    }

    @Test
    public void shouldSetDirectory() throws Exception {
        File directory = new File("/tmp");
        SimpleApplicationSchema schema = new SimpleApplicationSchema("test.sh");
        schema.setWorkingDirectory(directory);
        assertThat(schema.getWorkingDirectory(), is(directory));
    }

    @Test
    public void shouldSetEnvironmentVariable() throws Exception {
        SimpleApplicationSchema schema = new SimpleApplicationSchema("test.sh")
                                                .setEnvironmentVariable("test", "value");

        assertThat((String)schema.getEnvironmentVariablesBuilder().getProperty("test"), is("value"));
    }

    @Test
    public void shouldSetEnvironmentVariables() throws Exception {
        PropertiesBuilder environment= new PropertiesBuilder()
                                              .setProperty("key-1", "value-1")
                                              .setProperty("key-2", "value-2");

        SimpleApplicationSchema schema = new SimpleApplicationSchema("test.sh")
                                                .setEnvironmentVariables(environment);

        assertThat((String) schema.getEnvironmentVariablesBuilder().getProperty("key-1"), is("value-1"));
        assertThat((String)schema.getEnvironmentVariablesBuilder().getProperty("key-2"), is("value-2"));
    }

    @Test
    public void shouldSetEnvironmentVariablesFromIterator() throws Exception {
        SimpleApplicationSchema schema = new SimpleApplicationSchema("test.sh");

        Iterator iterator = Arrays.asList("value-1", "value-2").iterator();
        schema.setEnvironmentVariable("test", iterator);
        assertThat((Iterator) schema.getEnvironmentVariablesBuilder().getProperty("test"), is(iterator));
    }

    @Test
    public void shouldClearEnvironmentVariables() throws Exception {
        SimpleApplicationSchema schema = new SimpleApplicationSchema("test.sh")
                                                .setEnvironmentVariable("test", "value");

        schema.clearEnvironmentVariables();
        assertThat(schema.getEnvironmentVariablesBuilder().size(), is(0));
    }

    @Test
    public void shouldSetCloneEnvironmentFlagToTrue() throws Exception {
        SimpleApplicationSchema schema = new SimpleApplicationSchema("test.sh")
                                                .cloneEnvironmentVariables(true);

        assertThat(schema.shouldCloneEnvironment(), is(true));
    }

    @Test
    public void shouldSetCloneEnvironmentFlagToFalse() throws Exception {
        SimpleApplicationSchema schema = new SimpleApplicationSchema("test.sh")
                                                .cloneEnvironmentVariables(false);

        assertThat(schema.shouldCloneEnvironment(), is(false));
    }

    @Test
    public void shouldDefaultCloneEnvironmentFlagToTrue() throws Exception {
        SimpleApplicationSchema schema = new SimpleApplicationSchema("test.sh");

        assertThat(schema.shouldCloneEnvironment(), is(false));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void shouldSetArguments() throws Exception {
        SimpleApplicationSchema schema = new SimpleApplicationSchema("test.sh");

        schema.setArgument("arg1");
        schema.setArgument("arg2");

        assertThat(schema.getArguments(), contains("arg1", "arg2"));
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void shouldAddArguments() throws Exception {
        SimpleApplicationSchema schema = new SimpleApplicationSchema("test.sh");

        schema.addArgument("arg1");
        schema.addArgument("arg2");

        assertThat(schema.getArguments(), contains("arg1", "arg2"));
    }

}
