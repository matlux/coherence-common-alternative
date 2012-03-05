package com.oracle.coherence.common.runtime.process;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Jonathan Knight
 */
public class OutOfProcessBuilder extends JavaProcessBuilder {

    private String applicationClassName;

    /**
     * Create a new OutOfProcessBuilder that will build and run a {@link Process}
     * that runs the specified class's main method.
     *
     * @param applicationClassName - the class that this builder will run
     */
    public OutOfProcessBuilder(String executableName, String applicationClassName) {
        super(executableName);
        this.applicationClassName = applicationClassName;
    }

    /**
     * Build a {@link Process} using the specified arguments and return the
     * running instance of the built {@link Process}.
     *
     * @param name - the name of the process to build and start
     * @param options - Options (command line arguments) to pass to the process
     * @param environmentVariables - environment variables to pass to the process
     * @param systemProperties - System properties to be used by the process
     * @param classPath - the class path that the process should use
     * @param arguments - command line arguments to pass to the process
     *
     * @return the built and started process
     *
     * @throws java.io.IOException if an error occurs creating or starting the process
     */
    @Override
    public Process start() throws IOException {
        Properties systemProperties = this.systemProperties();
        for (String propertyName : systemProperties.stringPropertyNames())
        {
            String propertyValue = systemProperties.getProperty(propertyName);
            this.command().add("-D" + propertyName + (propertyValue.isEmpty() ? "" : "=" + propertyValue));
        }

        //add the applicationClassName to the command for the process
        this.command().add(applicationClassName);

        //add the arguments to the command for the process
        for (String argument : this.arguments())
        {
            command().add(argument);
        }

        //start the process
        return super.start();
    }

}
