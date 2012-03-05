/*
 * File: AbstractApplicationGroup.java
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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * An {@link AbstractApplicationGroup} is a base implementation of an {@link ApplicationGroup}.
 *
 * @param <A> The type of {@link Application} that belongs to the {@link ApplicationGroup}.
 *
 * @author Brian Oliver
 */
public abstract class AbstractApplicationGroup<A extends Application> implements ApplicationGroup<A>
{
    /**
     * The collection of {@link Application}s that belong to the {@link ApplicationGroup}.
     */
    protected LinkedHashMap<String,A> m_applications;


    /**
     * Constructs an {@link AbstractApplicationGroup} given a list of {@link Application}s.
     *
     * @param applications  The list of {@link Application}s in the {@link ApplicationGroup}.
     */
    public AbstractApplicationGroup(List<A> applications)
    {
        m_applications = new LinkedHashMap<String,A>();
        for (A application : applications)
        {
            m_applications.put(application.getName(), application);
        }
    }

    /**
     * Returns the application in this group with the given name or null
     * if no application has been realized with the given name.
     * @param name - the name of the application to get
     *
     * @return the application in this group with the given name or null
     * if no application has been realized with the given name.
     */
    public A getApplication(String name)
    {
        return m_applications.get(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<A> iterator()
    {
        return m_applications.values().iterator();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy()
    {
        for (A application : m_applications.values())
        {
            if (application != null)
            {
                application.destroy();
            }
        }
    }
}
