package com.oracle.coherence.common.runtime.process;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan Knight
 */
public class ProcessRunnableStub {
    public static final String METHOD_STATIC_START = "staticStart";
    public static final String METHOD_STATIC_START_NO_ARGS = "staticStartNoArgs";
    public static final String METHOD_STATIC_STOP = "staticStop";
    public static final String METHOD_START = "start";
    public static final String METHOD_START_NO_ARGS = "startNoArgs";
    public static final String METHOD_STOP = "stop";

    public static final List<String> methodsCalled = new ArrayList<String>();

    public static Object result;

    public static String[] argsUsed;

    public static Object staticStart(String[] args) {
        methodsCalled.add(METHOD_STATIC_START);
        argsUsed = args;
        return result;
    }

    public static Object staticStartNoArgs() {
        methodsCalled.add(METHOD_STATIC_START_NO_ARGS);
        return result;
    }

    public static Object staticStop() {
        methodsCalled.add(METHOD_STATIC_STOP);
        return result;
    }

    public Object start(String[] args) {
        methodsCalled.add(METHOD_START);
        argsUsed = args;
        return result;
    }

    public Object startNoArgs() {
        methodsCalled.add(METHOD_START_NO_ARGS);
        return result;
    }

    public Object stop() {
        methodsCalled.add(METHOD_STOP);
        return result;
    }
}
