package com.oracle.coherence.common.runtime;

/**
 * @author Jonathan Knight
 */
public class TestApp {

    public static void main(String[] args) throws Exception {
        for (String arg : args) {
            System.out.print(arg + ",");
        }
        System.out.println();

        for (String name : System.getProperties().stringPropertyNames()) {
            if (name.startsWith("test.prop.")) {
                System.out.println(name + "=" + System.getProperty(name) + ",");
            }
        }
        System.out.println();
        Thread.sleep(10000);
    }
}
