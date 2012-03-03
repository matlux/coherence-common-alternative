/*
 * File: PofType.java
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
package com.oracle.coherence.common.serialization.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.oracle.coherence.common.serialization.ReflectiveSerializer;

/**
 * The {@link PofType} annotation specifies that a concrete {@link Class} may be serialized using a reflective 
 * approach.
 * 
 * @see ReflectiveSerializer
 * 
 * @author Charlie Helin
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PofType
{

    /**
     * Specifies the Pof Id for the type.
     *
     * @return the Pof Id
     */
    int id();

    /**
     * (Optional) Specifies the version of the type.
     *
     * @return the version for the type, 0 is default
     */
    int version() default 0;
}
