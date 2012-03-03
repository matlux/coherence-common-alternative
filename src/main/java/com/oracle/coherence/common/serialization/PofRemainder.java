/*
 * File: PofRemainder.java
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

import java.lang.reflect.Field;

import com.oracle.coherence.common.serialization.annotations.PofType;
import com.tangosol.util.Binary;

/**
 * A {@link PofRemainder} is holds Pof stream remainder {@link Binary} and other associated evolvable 
 * type information to support the evolvability of {@link PofType}s.
 * <p>
 * To support evolution, backwards and forwards compatibility of a {@link PofType}, declare a single 
 * {@link PofRemainder} in the said {@link PofType}.  This {@link Field} will then be used to 
 * store runtime type evolution and serialization information.
 *  
 * @see PofType
 * 
 * @author Charlie Helin
 */
public final class PofRemainder
{

    /**
     * From which version of the serialized {@link PofType} was the {@link PofRemainder} read. 
     */
    private int fromVersion;

    /**
     * The unread {@link Binary} containing the remaining bytes from a Pof stream
     */
    private Binary remainder;


    /**
     * Standard Constructor.
     * 
     * @param fromVersion The version of the User Type from which the {@link PofRemainder} was created.
     * @param remainder The {@link Binary} remainder of the User Type from a Pof stream.
     */
    public PofRemainder(int fromVersion,
                        Binary remainder)
    {
        this.fromVersion = fromVersion;
        this.remainder = remainder;
    }


    /**
     * Returns the version from which the {@link PofRemainder} was created.
     * 
     * @return The integer version number
     */
    public int getFromVersion()
    {
        return fromVersion;
    }


    /**
     * The {@link Binary} remainder.
     * 
     * @return A {@link Binary} containing the remainder.
     */
    public Binary getBinary()
    {
        return remainder;
    }
}
