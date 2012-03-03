/*
 * File: AbstractApplicationSchema.java
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An {@link AbstractApplicationSchema} is a base implementation of an {@link ApplicationSchema}.
 *
 * @author Brian Oliver
 */
public abstract class AbstractApplicationSchema<A extends Application, S extends ApplicationSchema<A, S>>
    implements ApplicationSchema<A, S>
{
    /**
     * The {@link PropertiesBuilder} containing the environment variables to be used when realizing
     * the {@link Application}.
     */
    private PropertiesBuilder m_propertiesBuilder;

    /**
     * The arguments for the {@link Application}.
     */
    private ArrayList<String> m_applicationArguments;


    /**
     * Construct an {@link AbstractApplicationSchema}.
     */
    public AbstractApplicationSchema()
    {
        m_propertiesBuilder    = new PropertiesBuilder();
        m_applicationArguments = new ArrayList<String>();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public PropertiesBuilder getEnvironmentVariablesBuilder()
    {
        return m_propertiesBuilder;
    }


    /**
     * Sets the specified environment variable to use an {@link Iterator} from which to retrieve it's values.
     *
     * @param name The name of the environment variable.
     *
     * @param iterator An {@link Iterator} providing values for the environment variable.
     *
     * @return The {@link ApplicationSchema} (so that we can perform method chaining).
     */
    @SuppressWarnings("unchecked")
    public S setEnvironmentVariable(String name,
                                    Iterator<?> iterator)
    {
        m_propertiesBuilder.setProperty(name, iterator);

        return (S) this;
    }


    /**
     * Sets the specified environment variable to the specified value.
     *
     * @param name The name of the environment variable.
     *
     * @param value The value of the environment variable.
     *
     * @return The {@link ApplicationSchema} (so that we can perform method chaining).
     */
    @SuppressWarnings("unchecked")
    public S setEnvironmentVariable(String name,
                                    Object value)
    {
        m_propertiesBuilder.setProperty(name, value);

        return (S) this;
    }


    /**
     * Adds/Overrides the current environment variables with those specified by the {@link PropertiesBuilder}.
     *
     * @param environmentVariablesBuilder The environment variables to add/override on the {@link ApplicationBuilder}.
     *
     * @return The {@link ApplicationSchema} (so that we can perform method chaining).
     */
    @SuppressWarnings("unchecked")
    public S setEnvironmentVariables(PropertiesBuilder environmentVariablesBuilder)
    {
        m_propertiesBuilder.addProperties(environmentVariablesBuilder);

        return (S) this;
    }


    /**
     * Clears the currently registered environment variables from the {@link ApplicationBuilder}.
     *
     * @return The {@link ApplicationSchema} (so that we can perform method chaining).
     */
    @SuppressWarnings("unchecked")
    public S clearEnvironmentVariables()
    {
        m_propertiesBuilder.clear();

        return (S) this;
    }


    /**
     * Adds an argument to use when starting the {@link Application}.
     *
     * @param argument The argument for the {@link Application}.
     */
    public void addArgument(String argument)
    {
        m_applicationArguments.add(argument);
    }


    /**
     * {@inheritDoc}
     */
    public List<String> getArguments()
    {
        return m_applicationArguments;
    }


    /**
     * Adds an argument to use when starting the {@link Application}.
     *
     * @param argument The argument for the {@link Application}.
     *
     * @return The resulting {@link ApplicationBuilder}.
     */
    @SuppressWarnings("unchecked")
    public S setArgument(String argument)
    {
        addArgument(argument);

        return (S) this;
    }
}
