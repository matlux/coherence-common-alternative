/*
 * File: AbstractJavaApplicationBuilder.java
 *
 * Copyright (c) 2011. All Rights Reserved. Oracle Corporation.
 *
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * This software is the confidential and proprietary information of Oracle
 * Corporation. You shall not disclose such confidential and proprietary
 * information and shall use it only in accordance with the terms of the license
 * agreement you entered into with Oracle Corporation.
 *
 * Oracle Corporation makes no representations or warranties about the
 * suitability of the software, either express or implied, including but not
 * limited to the implied warranties of merchantability, fitness for a
 * particular purpose, or non-infringement. Oracle Corporation shall not be
 * liable for any damages suffered by licensee as a result of using, modifying
 * or distributing this software or its derivatives.
 *
 * This notice may not be removed or altered.
 */

package com.oracle.coherence.common.runtime;

import com.oracle.coherence.common.events.lifecycle.LifecycleEvent;
import com.oracle.coherence.common.events.lifecycle.LifecycleStartedEvent;
import com.oracle.coherence.common.events.processing.EventProcessor;

import java.io.IOException;

import java.util.Properties;

/**
 * An {@link AbstractJavaApplicationBuilder} is the base implementation for {@link JavaApplicationBuilder}s.
 *
 * @author Brian Oliver
 */
public abstract class AbstractJavaApplicationBuilder<A extends JavaApplication, S extends JavaApplicationSchema<A, S>,
                                                     B extends JavaApplicationBuilder<A, S, B>>
    extends AbstractApplicationBuilder<A, S, B>
{
    /**
     * Constructs a {@link AbstractJavaApplicationBuilder}.
     */
    public AbstractJavaApplicationBuilder()
    {
        super();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public A realize(S schema,
                     String name,
                     ApplicationConsole console) throws IOException
    {
        // construct the process builder to start a java process
        ProcessBuilder processBuilder = new java.lang.ProcessBuilder("java");

        // we must redirect and consume output of the external process otherwise we could run out of memory
        // or worse, do something nasty to the operating system.
        processBuilder.redirectErrorStream(true);

        // add the jvm options to the operating system command
        for (String option : schema.getJVMOptions())
        {
            processBuilder.command().add("-" + option);
        }

        // determine the environment variables for the process (based on the Environment Variables Builder)
        Properties environmentVariables = schema.getEnvironmentVariablesBuilder().realize();

        // we always clear down the process environment variables as by default they are inherited from
        // the current process, which is not what we want as it doesn't allow us to create a clean environment
        processBuilder.environment().clear();

        // add the environment variables to the process
        for (String variableName : environmentVariables.stringPropertyNames())
        {
            processBuilder.environment().put(variableName, environmentVariables.getProperty(variableName));
        }

        // add the class path (it's an environment variable)
        processBuilder.environment().put("CLASSPATH", schema.getClassPath());

        // realize the system properties for the process
        Properties systemProperties = schema.getSystemPropertiesBuilder().realize();

        // add the system properties to the process
        for (String propertyName : systemProperties.stringPropertyNames())
        {
            String propertyValue = systemProperties.getProperty(propertyName);

            processBuilder.command().add("-D" + propertyName + (propertyValue.isEmpty() ? "" : "=" + propertyValue));
        }

        // add the applicationClassName to the command for the process
        processBuilder.command().add(schema.getApplicationClassName());

        // add the arguments to the command for the process
        for (String argument : schema.getArguments())
        {
            processBuilder.command().add(argument);
        }

        // start the process
        A application = createJavaApplication(processBuilder.start(),
                                              name,
                                              console,
                                              environmentVariables,
                                              systemProperties);

        // raise lifecycle event for the application
        LifecycleEvent<A> event = new LifecycleStartedEvent<A>(application);

        for (EventProcessor<LifecycleEvent<A>> processor : m_lifecycleEventProcessors)
        {
            processor.process(null, event);
        }

        return application;
    }


    /**
     * Requests the child concrete implementation of this class to construct a suitable {@link JavaApplication}
     * using the provided properties.
     *
     * @param process               The {@link Process} representing the {@link JavaApplication}.
     * @param name                  The name of the {@link JavaApplication}.
     * @param console               The {@link ApplicationConsole} that will be used for I/O by the
     *                              realized {@link Application}. This may be <code>null</code> if not required.
     * @param environmentVariables  The environment variables used when starting the {@link JavaApplication}.
     * @param systemProperties      The system properties provided to the {@link JavaApplication}
     *
     * @return An A.
     */
    protected abstract A createJavaApplication(Process process,
                                               String name,
                                               ApplicationConsole console,
                                               Properties environmentVariables,
                                               Properties systemProperties);
}
