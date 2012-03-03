/*
 * File: ReflectedSerializer.java
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
package com.oracle.coherence.common.serialization;

import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofSerializer;
import com.tangosol.io.pof.PofWriter;

import java.io.IOException;

/**
 * A {@link ReflectedSerializer} is a {@link PofSerializer} implementation that has been generated at runtime
 * through the use of reflection.
 *
 * @author Charlie Helin
 */
public interface ReflectedSerializer extends PofSerializer
{

    /**
     * Determines the type (as a {@link Class}) that this {@link PofSerializer} can serialize/deserialize.
     *
     * @return The type (as a {@link Class}).
     */
    public Class<?> getType();


    /**
     * The Pof version number that will be used for serializing the type when a {@link PofRemainder#getFromVersion()}
     * is unavailable.
     *
     * @return An Integer
     */
    public int getVersion();


    /**
     * Sets (forces) the Pof version number that will be used for serializing the type when a
     * {@link PofRemainder#getFromVersion()} is unavailable.
     *
     * @param version The Pof version number.
     */
    public void setVersion(int version);


    /**
     * {@inheritDoc}
     */
    public Object deserialize(PofReader reader) throws IOException;


    /**
     * {@inheritDoc}
     */
    public void serialize(PofWriter writer,
                          Object object) throws IOException;

}