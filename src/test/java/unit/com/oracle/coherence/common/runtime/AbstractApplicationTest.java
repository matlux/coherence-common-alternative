package com.oracle.coherence.common.runtime;

import com.oracle.coherence.common.runtime.process.InternalProcess;
import com.oracle.coherence.common.tuples.Pair;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.text.StringContainsInOrder.stringContainsInOrder;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jonathan Knight
 */
public class AbstractApplicationTest {

    @Test
    public void shouldSetProcess() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        Process process = mock(Process.class);
        ApplicationConsole console = mock(ApplicationConsole.class);

        AbstractApplication application = new AbstractApplicationStub(process, applicationName, console, properties);
        assertThat(application.getProcess(), is(sameInstance(process)));
    }

    @Test
    public void shouldSetName() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        Process process = mock(Process.class);
        ApplicationConsole console = mock(ApplicationConsole.class);

        AbstractApplication application = new AbstractApplicationStub(process, applicationName, console, properties);
        assertThat(application.getName(), is(applicationName));
    }

    @Test
    public void shouldDestroyProcess() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        Process process = mock(Process.class);
        ApplicationConsole console = mock(ApplicationConsole.class);

        InputStream inputStream = mock(InputStream.class);
        InputStream errorStream = mock(InputStream.class);
        OutputStream outputStream = mock(OutputStream.class);

        when(process.getInputStream()).thenReturn(inputStream);
        when(process.getErrorStream()).thenReturn(errorStream);
        when(process.getOutputStream()).thenReturn(outputStream);

        AbstractApplication application = new AbstractApplicationStub(process, applicationName, console, properties);
        application.destroy();

        verify(process).destroy();
        verify(inputStream).close();
        verify(errorStream).close();
        verify(outputStream).close();
    }

    @Test
    public void shouldReturnExitValue() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        Process process = mock(Process.class);
        ApplicationConsole console = mock(ApplicationConsole.class);

        when(process.exitValue()).thenReturn(19);

        AbstractApplication application = new AbstractApplicationStub(process, applicationName, console, properties);
        assertThat(application.exitValue(), is(19));
    }

    @Test
    public void shouldWaitForProcess() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        Process process = mock(Process.class);
        ApplicationConsole console = mock(ApplicationConsole.class);

        AbstractApplication application = new AbstractApplicationStub(process, applicationName, console, properties);
        application.waitFor();

        verify(process).waitFor();
    }

    @Test(expected = InterruptedException.class)
    public void shouldThrowInterruptedExceptionWhenCallingWaitFor() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        Process process = mock(Process.class);
        ApplicationConsole console = mock(ApplicationConsole.class);

        when(process.waitFor()).thenThrow(new InterruptedException());

        AbstractApplication application = new AbstractApplicationStub(process, applicationName, console, properties);
        application.waitFor();
    }

    @Test
    public void shouldGetPidForInternalProcess() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        InternalProcess process = mock(InternalProcess.class);
        ApplicationConsole console = mock(ApplicationConsole.class);

        AbstractApplication application = new AbstractApplicationStub(process, applicationName, console, properties);
        assertThat(application.getPid(), is(-1L));
    }

    @Test
    public void shouldGetEnvironmentVariables() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        InternalProcess process = mock(InternalProcess.class);
        ApplicationConsole console = mock(ApplicationConsole.class);

        properties.setProperty("key-1", "value-1");
        properties.setProperty("key-2", "value-3");

        AbstractApplication application = new AbstractApplicationStub(process, applicationName, console, properties);
        assertThat(application.getEnvironmentVariables(), is(properties));
    }

    @SuppressWarnings({"InstantiatingObjectToGetClassObject"})
    @Test
    public void shouldCaptureStandardOut() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        Process process = mock(Process.class);

        InputStream inputStream = new ByteArrayInputStream("Out Test...".getBytes());
        InputStream errorStream = mock(InputStream.class);

        when(process.getInputStream()).thenReturn(inputStream);
        when(process.getErrorStream()).thenReturn(errorStream);

        ApplicationConsoleStub console = new ApplicationConsoleStub();
        new AbstractApplicationStub(process, applicationName, console, properties);

        console.waitForLineCount(3, 10000);
        Object[] args = console.lines.get(0).getY();
        assertThat(args[0], is((Object)applicationName));
        assertThat(args[1], is((Object)"out"));
        assertThat(args[3], is((Object)1L));
        assertThat(args[4], is((Object)"Out Test..."));
    }

    @SuppressWarnings({"InstantiatingObjectToGetClassObject"})
    @Test
    public void shouldTerminateStandardOut() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        Process process = mock(Process.class);

        InputStream inputStream = mock(InputStream.class);
        InputStream errorStream = mock(InputStream.class);

        when(process.getInputStream()).thenReturn(inputStream);
        when(process.getErrorStream()).thenReturn(errorStream);

        ApplicationConsoleStub console = new ApplicationConsoleStub();
        new AbstractApplicationStub(process, applicationName, console, properties);

        console.waitForLineCount(2, 10000);
        String format = console.lines.get(0).getX();
        assertThat(format, is(stringContainsInOrder(Arrays.asList("(terminated)"))));
    }

    @SuppressWarnings({"InstantiatingObjectToGetClassObject"})
    @Test
    public void shouldCaptureStandardErr() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        Process process = mock(Process.class);

        InputStream errorStream = new ByteArrayInputStream("Err Test...".getBytes());
        InputStream inputStream = mock(InputStream.class);

        when(process.getInputStream()).thenReturn(inputStream);
        when(process.getErrorStream()).thenReturn(errorStream);

        ApplicationConsoleStub console = new ApplicationConsoleStub();
        new AbstractApplicationStub(process, applicationName, console, properties);

        console.waitForLineCount(3, 10000);
        Object[] args = console.lines.get(1).getY();
        assertThat(args[0], is((Object)applicationName));
        assertThat(args[1], is((Object)"err"));
        assertThat(args[3], is((Object)1L));
        assertThat(args[4], is((Object)"Err Test..."));
    }

    @SuppressWarnings({"InstantiatingObjectToGetClassObject"})
    @Test
    public void shouldTerminateStandardErr() throws Exception {
        String applicationName = "Test-App";
        Properties properties = new Properties();
        Process process = mock(Process.class);

        InputStream inputStream = mock(InputStream.class);
        InputStream errorStream = mock(InputStream.class);

        when(process.getInputStream()).thenReturn(inputStream);
        when(process.getErrorStream()).thenReturn(errorStream);

        ApplicationConsoleStub console = new ApplicationConsoleStub();
        new AbstractApplicationStub(process, applicationName, console, properties);

        console.waitForLineCount(2, 10000);
        String format = console.lines.get(1).getX();
        assertThat(format, is(stringContainsInOrder(Arrays.asList("(terminated)"))));
    }

    private class AbstractApplicationStub extends AbstractApplication {
        private AbstractApplicationStub(Process process, String name, ApplicationConsole console, Properties environmentVariables) {
            super(process, name, console, environmentVariables);
        }
    }

    private class ApplicationConsoleStub implements ApplicationConsole {
        private List<Pair<String,Object[]>> lines = new ArrayList<Pair<String, Object[]>>();
        private final Object WAITER = new Object();
        private volatile int expectedCount;

        @Override
        public void printf(String format, Object... args) {
            synchronized (WAITER) {
                lines.add(new Pair<String, Object[]>(format, args));
                if (lines.size() >= expectedCount) {
                    WAITER.notifyAll();
                }
            }
        }

        public void waitForLineCount(int count, long time) throws InterruptedException {
            synchronized (WAITER) {
                this.expectedCount = count;
                if (lines.size() < expectedCount) {
                    WAITER.wait(time);
                }
            }
            if (lines.size() < expectedCount) {
                fail("Expected number of lines not printed expected=" + expectedCount + " actual=" + lines.size());
            }
        }
    }
}
