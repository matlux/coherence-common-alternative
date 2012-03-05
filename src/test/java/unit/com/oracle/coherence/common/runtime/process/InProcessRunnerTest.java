package com.oracle.coherence.common.runtime.process;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.junit.Assert.assertThat;

/**
 * @author Jonathan Knight
 */
public class InProcessRunnerTest {

    @Before
    public void clearProcessRunnableStub() {
        ProcessRunnableStub.argsUsed = null;
        ProcessRunnableStub.methodsCalled.clear();
        ProcessRunnableStub.result = null;
    }

    @Test
    public void shouldCallStaticMethod() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String className = ProcessRunnableStub.class.getCanonicalName();
        String methodName = ProcessRunnableStub.METHOD_STATIC_START;
        Object result = new Object();
        String[] args = new String[]{"1", "2"};

        InProcessRunner runner = new InProcessRunner("Test", classLoader, className, methodName, args);
        ProcessRunnableStub.methodsCalled.clear();
        ProcessRunnableStub.result = result;
        runner.run();

        assertThat(ProcessRunnableStub.methodsCalled, is(Collections.singletonList(methodName)));
        assertThat(runner.isFinished(), is(true));
        assertThat(runner.<Object>getResult(), sameInstance(result));
        assertThat(runner.getInstance(), CoreMatchers.<Object>nullValue());
        assertThat(runner.getError(), CoreMatchers.<Throwable>nullValue());
        assertThat(ProcessRunnableStub.argsUsed, is(arrayContaining("1", "2")));
    }

    @Test
    public void shouldCallStaticNoArgsMethod() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String className = ProcessRunnableStub.class.getCanonicalName();
        String methodName = ProcessRunnableStub.METHOD_STATIC_START_NO_ARGS;
        Object result = new Object();
        String[] args = new String[]{"1", "2"};

        InProcessRunner runner = new InProcessRunner("Test", classLoader, className, methodName, args);
        ProcessRunnableStub.methodsCalled.clear();
        ProcessRunnableStub.result = result;
        runner.run();

        assertThat(ProcessRunnableStub.methodsCalled, is(Collections.singletonList(methodName)));
        assertThat(runner.isFinished(), is(true));
        assertThat(runner.<Object>getResult(), sameInstance(result));
        assertThat(runner.getInstance(), CoreMatchers.<Object>nullValue());
        assertThat(runner.getError(), CoreMatchers.<Throwable>nullValue());
        assertThat(ProcessRunnableStub.argsUsed, is(nullValue()));
    }

    @Test
    public void shouldCallNonStaticMethod() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String className = ProcessRunnableStub.class.getCanonicalName();
        String methodName = ProcessRunnableStub.METHOD_START;
        Object result = new Object();
        String[] args = new String[]{"3", "4"};

        InProcessRunner runner = new InProcessRunner("Test", classLoader, className, methodName, args);
        ProcessRunnableStub.methodsCalled.clear();
        ProcessRunnableStub.result = result;
        runner.run();

        assertThat(ProcessRunnableStub.methodsCalled, is(Collections.singletonList(methodName)));
        assertThat(runner.<Object>getResult(), sameInstance(result));
        assertThat(runner.getInstance(), instanceOf(ProcessRunnableStub.class));
        assertThat(runner.getError(), CoreMatchers.<Throwable>nullValue());
        assertThat(ProcessRunnableStub.argsUsed, is(arrayContaining("3", "4")));
    }

    @Test
    public void shouldCallNonStaticNoArgsMethod() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String className = ProcessRunnableStub.class.getCanonicalName();
        String methodName = ProcessRunnableStub.METHOD_START_NO_ARGS;
        Object result = new Object();
        String[] args = new String[]{"3", "4"};

        InProcessRunner runner = new InProcessRunner("Test", classLoader, className, methodName, args);
        ProcessRunnableStub.methodsCalled.clear();
        ProcessRunnableStub.result = result;
        runner.run();

        assertThat(ProcessRunnableStub.methodsCalled, is(Collections.singletonList(methodName)));
        assertThat(runner.<Object>getResult(), sameInstance(result));
        assertThat(runner.getInstance(), instanceOf(ProcessRunnableStub.class));
        assertThat(runner.getError(), CoreMatchers.<Throwable>nullValue());
        assertThat(ProcessRunnableStub.argsUsed, is(nullValue()));
    }

    @Test
    public void shouldUseSpecifiedInstanceToCallNonStaticMethod() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String className = ProcessRunnableStub.class.getCanonicalName();
        String methodName = ProcessRunnableStub.METHOD_START;
        Object result = new Object();
        String[] args = new String[]{"A", "B"};

        ProcessRunnableStub instance = new ProcessRunnableStub();
        ProcessRunnableStub.result = result;

        InProcessRunner runner = new InProcessRunner("Test", classLoader, className, methodName, args, instance);
        runner.run();

        assertThat(runner.<Object>getResult(), sameInstance(result));
        assertThat((ProcessRunnableStub)runner.getInstance(), sameInstance(instance));
        assertThat(runner.getError(), CoreMatchers.<Throwable>nullValue());
        assertThat(ProcessRunnableStub.methodsCalled, is(Arrays.asList(ProcessRunnableStub.METHOD_START)));
        assertThat(ProcessRunnableStub.argsUsed, is(arrayContaining("A", "B")));
    }

    @Test
    public void shouldSetErrorIfExceptionThrown() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String className = "No_Such_Class";
        String methodName = "No_Such_Method";
        String[] args = new String[0];

        InProcessRunner runner = new InProcessRunner("Test", classLoader, className, methodName, args);
        runner.run();

        assertThat(runner.getError(), instanceOf(ClassNotFoundException.class));
    }

}
