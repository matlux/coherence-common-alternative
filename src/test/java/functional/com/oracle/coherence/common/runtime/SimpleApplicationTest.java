package com.oracle.coherence.common.runtime;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author Jonathan Knight
 */
public class SimpleApplicationTest {

    @Test
    public void shouldRunApplication() throws Exception {
        SimpleApplicationSchema schema = new SimpleApplicationSchema("java");
        schema.addArgument("-help");

        SimpleApplicationBuilder builder = new SimpleApplicationBuilder();
        CapturingApplicationConsole console = new CapturingApplicationConsole();
        SimpleApplication application = builder.realize(schema, "java", console);

        application.waitFor();
        int exitCode = application.exitValue();
        assertThat(exitCode, is(0));
        assertThat(console.getConsoleOutputLine(1), containsString("Usage: java [-options] class [args...]"));
    }

}
