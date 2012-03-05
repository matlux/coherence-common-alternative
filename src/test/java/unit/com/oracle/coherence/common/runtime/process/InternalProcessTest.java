package com.oracle.coherence.common.runtime.process;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jonathan Knight
 */
public class InternalProcessTest {

    @Test
    @SuppressWarnings({"unchecked"})
    public void shouldStartProcess() throws Exception {
        final Class clazz = ProcessRunnableStub.class;
        final String className = clazz.getCanonicalName();
        final String startMethodName = ProcessRunnableStub.METHOD_STATIC_START;
        final String stopMethodName = ProcessRunnableStub.METHOD_STATIC_STOP;
        final String[] args = new String[0];

        final ClassLoader classLoader = mock(ClassLoader.class);
        when(classLoader.loadClass(className)).thenReturn(clazz);

        InternalProcess process = new InternalProcess("Test", classLoader, className, startMethodName, stopMethodName, args);

        ProcessRunnableStub.methodsCalled.clear();
        process.start();

        verify(classLoader).loadClass(className);
        assertThat(ProcessRunnableStub.methodsCalled, is(Collections.singletonList(startMethodName)));
        InProcessRunner runnable = process.getStartRunnable();
        assertThat(runnable.getClassLoader(), sameInstance(classLoader));
        assertThat(runnable.getClassName(), is(className));
        assertThat(runnable.getMethodName(), is(startMethodName));
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void shouldStartProcessOnlyOnce() throws Exception {
        final Class clazz = ProcessRunnableStub.class;
        final String className = clazz.getCanonicalName();
        final String startMethodName = ProcessRunnableStub.METHOD_STATIC_START;
        final String stopMethodName = ProcessRunnableStub.METHOD_STATIC_STOP;
        final String[] args = new String[0];

        final ClassLoader classLoader = mock(ClassLoader.class);
        when(classLoader.loadClass(className)).thenReturn(clazz);

        InternalProcess process = new InternalProcess("Test", classLoader, className, startMethodName, stopMethodName, args);

        ProcessRunnableStub.methodsCalled.clear();
        process.start();
        InProcessRunner runner = process.getStartRunnable();
        process.start();

        assertThat(ProcessRunnableStub.methodsCalled, is(Collections.singletonList(startMethodName)));
        assertThat(process.getStartRunnable(), sameInstance(runner));
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void shouldStopProcess() throws Exception {
        final Class clazz = ProcessRunnableStub.class;
        final String className = clazz.getCanonicalName();
        final String startMethodName = ProcessRunnableStub.METHOD_STATIC_START;
        final String stopMethodName = ProcessRunnableStub.METHOD_STATIC_STOP;
        final String[] args = new String[0];

        final ClassLoader classLoader = mock(ClassLoader.class);
        when(classLoader.loadClass(className)).thenReturn(clazz);

        InternalProcess process = new InternalProcess("Test", classLoader, className, startMethodName, stopMethodName, args);

        ProcessRunnableStub.methodsCalled.clear();
        process.destroy();

        verify(classLoader).loadClass(className);
        assertThat(ProcessRunnableStub.methodsCalled, is(Collections.singletonList(stopMethodName)));
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void shouldCallStopOnCorrectInstance() throws Exception {
        final Class clazz = ProcessRunnableStub.class;
        final String className = clazz.getCanonicalName();
        final String startMethodName = ProcessRunnableStub.METHOD_START;
        final String stopMethodName = ProcessRunnableStub.METHOD_STOP;
        final String[] args = new String[0];

        final ClassLoader classLoader = mock(ClassLoader.class);
        when(classLoader.loadClass(className)).thenReturn(clazz);

        ProcessRunnableStub stub = mock(ProcessRunnableStub.class);
        InProcessRunner runner = mock(InProcessRunner.class);
        when(runner.getInstance()).thenReturn(stub);

        InternalProcess process = new InternalProcess("Test", classLoader, className, startMethodName, stopMethodName, args);
        process.setStartRunnable(runner);
        process.destroy();

        verify(stub).stop();
        verify(classLoader).loadClass(className);
        assertThat(process.getStartRunnable(), CoreMatchers.<Object>nullValue());
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void shouldInvokeCorrectStaticMethodOnCorrectClass() throws Exception {
        final Class clazz = ProcessRunnableStub.class;
        final String className = clazz.getCanonicalName();
        final String methodName = ProcessRunnableStub.METHOD_STATIC_START;

        final ClassLoader classLoader = mock(ClassLoader.class);
        when(classLoader.loadClass(className)).thenReturn(clazz);

        InternalProcess process = new InternalProcess("Test", classLoader, null, null, null, null);

        Object result = new Object();
        ProcessRunnableStub.methodsCalled.clear();
        ProcessRunnableStub.result = result;

        Object invokeResult = process.invoke(className, methodName);

        verify(classLoader).loadClass(className);
        assertThat(ProcessRunnableStub.methodsCalled, is(Collections.singletonList(methodName)));
        assertThat(invokeResult, sameInstance(result));
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void shouldInvokeCorrectNonStaticMethodOnCorrectClass() throws Exception {
        final Class clazz = ProcessRunnableStub.class;
        final String className = clazz.getCanonicalName();
        final String methodName = ProcessRunnableStub.METHOD_START;

        final ClassLoader classLoader = mock(ClassLoader.class);
        when(classLoader.loadClass(className)).thenReturn(clazz);

        InternalProcess process = new InternalProcess("Test", classLoader, null, null, null, null);

        Object result = new Object();
        ProcessRunnableStub.methodsCalled.clear();
        ProcessRunnableStub.result = result;

        Object invokeResult = process.invoke(className, methodName);

        verify(classLoader).loadClass(className);
        assertThat(ProcessRunnableStub.methodsCalled, is(Collections.singletonList(methodName)));
        assertThat(invokeResult, sameInstance(result));
    }
}
