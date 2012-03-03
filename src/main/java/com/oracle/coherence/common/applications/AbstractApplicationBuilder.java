/*
 * File: AbstractApplicationBuilder.java
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
package com.oracle.coherence.common.applications;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.oracle.coherence.common.resourcing.ResourcePreparer;

/**
 * An {@link AbstractApplicationBuilder} is a base implementation of an {@link ApplicationBuilder}s that provides
 * access and management of environment variables, together with arguments for the said {@link ApplicationBuilder}s.
 * <p>
 * NOTE: This class is now deprecated.  As a replacement please use the com.oracle.coherence.common.runtime package
 * for controlling application processes and coherence servers.
 *
 * @param <A> The type of {@link Application} that the {@link AbstractApplicationBuilder} will create.
 * @param <T> The type of the {@link AbstractApplicationBuilder} from which default configuration may be retrieved.
 *
 * @see Application
 * @see ApplicationBuilder
 * 
 * @author Brian Oliver
 */
@Deprecated
public abstract class AbstractApplicationBuilder<A extends Application, T extends ApplicationBuilder<A, ?>> implements
        ApplicationBuilder<A, T>
{

    /**
     * The {@link PropertiesBuilder} containing the environment variables to be used when realizing 
     * the {@link Application}.
     */
    private PropertiesBuilder environmentVariablesBuilder;

    /**
     * The arguments for the application.
     */
    private ArrayList<String> arguments;

    /**
     * The {@link ResourcePreparer} for the {@link Application} that we'll use to ensure the application is prepared and 
     * ready for use once built.  If this is <code>null</code> then we simply return the built {@link Application}.
     */
    private ResourcePreparer<A> preparer;


    /**
     * Standard Constructor.
     */
    public AbstractApplicationBuilder()
    {
        environmentVariablesBuilder = new PropertiesBuilder();
        arguments = new ArrayList<String>();
        preparer = null;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public PropertiesBuilder getEnvironmentVariablesBuilder()
    {
        return environmentVariablesBuilder;
    }


    /**
     * Sets the specified environment variable to use an {@link Iterator} from which to retrieve it's values
     * when the {@link ApplicationBuilder} is realized.
     * 
     * @param name The name of the environment variable.
     * 
     * @param iterator An {@link Iterator} providing values for the environment variable.
     * 
     * @return The modified {@link ApplicationBuilder} (so that we can perform method chaining).
     */
    @SuppressWarnings("unchecked")
    public T setEnvironmentVariable(String name,
                                    Iterator<?> iterator)
    {
        environmentVariablesBuilder.setProperty(name, iterator);
        return (T) this;
    }


    /**
     * Sets the specified environment variable to the specified value that is then used when the 
     * {@link ApplicationBuilder} is realized.
     * 
     * @param name The name of the environment variable.
     * 
     * @param value The value of the environment variable.
     * 
     * @return The modified {@link ApplicationBuilder} (so that we can perform method chaining).
     */
    @SuppressWarnings("unchecked")
    public T setEnvironmentVariable(String name,
                                    Object value)
    {
        environmentVariablesBuilder.setProperty(name, value);
        return (T) this;
    }


    /**
     * Adds/Overrides the current environment variables with those specified by the {@link PropertiesBuilder}. 
     * 
     * @param environmentVariablesBuilder The environment variables to add/override on the {@link ApplicationBuilder}.
     * 
     * @return The modified {@link ApplicationBuilder} (so that we can perform method chaining).
     */
    @SuppressWarnings("unchecked")
    public T setEnvironmentVariables(PropertiesBuilder environmentVariablesBuilder)
    {
        environmentVariablesBuilder.addProperties(environmentVariablesBuilder);
        return (T) this;
    }


    /**
     * Clears the currently registered environment variables from the {@link ApplicationBuilder}.
     * 
     * @return The modified {@link ApplicationBuilder} (so that we can perform method chaining).
     */
    @SuppressWarnings("unchecked")
    public T clearEnvironmentVariables()
    {
        environmentVariablesBuilder.clear();
        return (T) this;
    }


    /**
     * Adds an argument to use when starting the {@link Application}.
     * 
     * @param argument The argument for the {@link Application}.
     */
    public void addArgument(String argument)
    {
        arguments.add(argument);
    }


    /**
     * Returns the list of arguments defined for the {@link Application}.
     * 
     * @return {@link List} of {@link String}s.
     */
    public List<String> getArguments()
    {
        return arguments;
    }


    /**
     * Adds an argument to use when starting the {@link Application}.
     * 
     * @param argument The argument for the {@link Application}.
     * 
     * @return The resulting {@link ApplicationBuilder}.
     */
    @SuppressWarnings("unchecked")
    public T setArgument(String argument)
    {
        addArgument(argument);
        return (T) this;
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public T setApplicationPreparer(ResourcePreparer<A> preparer)
    {
        this.preparer = preparer;
        return (T) this;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ResourcePreparer<A> getApplicationPreparer()
    {
        return preparer;
    }
}
