package com.oracle.coherence.common.runtime;

import com.oracle.coherence.common.events.lifecycle.LifecycleStartedEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @author Jonathan Knight
 */
public class SimpleApplicationBuilder
        extends AbstractApplicationBuilder<SimpleApplication, SimpleApplicationSchema, SimpleApplicationBuilder>
{
    /**
     * Realizes an instance of an {@link com.oracle.coherence.common.runtime.Application}.
     *
     * @param schema  The {@link com.oracle.coherence.common.runtime.ApplicationSchema} to use for realizing the {@link com.oracle.coherence.common.runtime.Application}.
     * @param name    The name of the application.
     * @param console The {@link com.oracle.coherence.common.runtime.ApplicationConsole} that will be used for I/O by the
     *                realized {@link com.oracle.coherence.common.runtime.Application}. This may be <code>null</code> if not required.
     * @return An {@link com.oracle.coherence.common.runtime.Application} representing the application realized by the {@link com.oracle.coherence.common.runtime.ApplicationBuilder}.
     * @throws java.io.IOException thrown if a problem occurs while starting the application.
     */
    @Override
    public SimpleApplication realize(SimpleApplicationSchema schema, String name, ApplicationConsole console) throws IOException {
        ProcessBuilder processBuilder;
        processBuilder = new ProcessBuilder(schema.getExecutableName());

        File directory = schema.getWorkingDirectory();
        if (directory != null) {
            processBuilder.directory(directory);
        }

        // determine the environment variables for the process (based on the Environment Variables Builder)
        Properties environmentVariables = schema.getEnvironmentVariablesBuilder().realize();

        // we always clear down the process environment variables as by default they are inherited from
        // the current process, which is not what we want as it doesn't allow us to create a clean environment
        if (!schema.shouldCloneEnvironment())
        {
            processBuilder.environment().clear();
        }

        // add the environment variables to the process
        for (String variableName : environmentVariables.stringPropertyNames())
        {
            processBuilder.environment().put(variableName, environmentVariables.getProperty(variableName));
        }

        List<String> command = processBuilder.command();
        // add the arguments to the command for the process
        for (String argument : schema.getArguments())
        {
            command.add(argument);
        }

        // start the process
        SimpleApplication application = new SimpleApplication(processBuilder.start(),
                                              name,
                                              console,
                                              environmentVariables);

        // raise lifecycle event for the application
        sendEvent(new LifecycleStartedEvent<SimpleApplication>(application));

        return application;
    }
}
