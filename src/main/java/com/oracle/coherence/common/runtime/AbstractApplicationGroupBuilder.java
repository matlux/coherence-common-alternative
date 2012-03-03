/*
 * File: AbstractApplicationGroupBuilder.java
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

import com.oracle.coherence.common.tuples.Triple;

import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * An {@link AbstractApplicationGroupBuilder} is a base implementation of an {@link ApplicationGroupBuilder}.
 *
 * @author Brian Oliver
 */
public abstract class AbstractApplicationGroupBuilder<A extends Application, S extends ApplicationSchema<A, S>,
                                                      B extends ApplicationBuilder<A, S, B>,
                                                      G extends ApplicationGroup<A>>
    implements ApplicationGroupBuilder<A, S, B, G>
{
    /**
     * The map of {@link ApplicationBuilder}s to create applications, keyed by application prefix name.
     */
    protected LinkedHashMap<String, Triple<B, S, Integer>> m_builders;


    /**
     * Construct an {@link AbstractApplicationGroupBuilder}.
     */
    public AbstractApplicationGroupBuilder()
    {
        m_builders = new LinkedHashMap<String, Triple<B, S, Integer>>();
    }


    /**
     * {@inheritDoc}
     */
    public void addBuilder(B bldrApplication,
                           S schema,
                           String sApplicationPrefix,
                           int cRequiredInstances)
    {
        m_builders.put(sApplicationPrefix, new Triple<B, S, Integer>(bldrApplication, schema, cRequiredInstances));
    }


    /**
     * {@inheritDoc}
     */
    public G realize(ApplicationConsole console) throws java.io.IOException
    {
        // build a list of applications
        LinkedList<A> applications = new LinkedList<A>();

        for (String prefix : m_builders.keySet())
        {
            Triple<B, S, Integer> triple             = m_builders.get(prefix);
            B                     builder            = triple.getX();
            S                     schema             = triple.getY();
            int                   cRequiredInstances = triple.getZ();

            for (int i = 0; i < cRequiredInstances; i++)
            {
                String applicationName = String.format("%s-%d", prefix, i);

                applications.add(builder.realize(schema, applicationName, console));
            }
        }

        return createApplicationGroup(applications);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public G realize() throws IOException
    {
        return realize(new NullApplicationConsole());
    }


    /**
     * Create an {@link ApplicationGroup} based on the specified collection of {@link Application}s.
     *
     * @param applications  The collection of {@link Application}s.
     *
     * @return An {@link ApplicationGroup} implementation.
     */
    abstract protected G createApplicationGroup(List<A> applications);
}
