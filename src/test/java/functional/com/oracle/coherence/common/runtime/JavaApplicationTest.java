package com.oracle.coherence.common.runtime;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author Jonathan Knight
 */
public class JavaApplicationTest {

    @SuppressWarnings({"unchecked"})
    @Test
    public void shouldRunApplication() throws Exception {
        SimpleJavaApplicationSchema schema = new SimpleJavaApplicationSchema(TestApp.class.getCanonicalName())
                .setArgument("arg1")
                .setArgument("arg2")
                .setSystemProperty("prop1", "value1");

        SimpleJavaApplicationBuilder builder = new SimpleJavaApplicationBuilder();
        CapturingApplicationConsole console = new CapturingApplicationConsole();
        JavaApplication application = builder.realize(schema, "java", console);
        console.waitForLine(10000);

        application.waitFor();
        int exitCode = application.exitValue();
        assertThat(exitCode, is(0));
        System.out.println(console.getConsoleOutput());
        application.destroy();
        assertThat(console.getConsoleOutputLine(1), containsString("Usage: java [-options] class [args...]"));
    }

}
