/*
 * File: PofField.java
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
import java.lang.reflect.Field;

/**
 * A {@link PofField} annotation is used to specify serialization information for a {@link Field} that is declared
 * on a {@link PofType}.
 *
 * @see PofType
 * 
 * @author Charlie Helin
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PofField
{

    /**
     * (Optional) The name of the {@link Field}.  When specified will be used to identify the {@link Field}
     * instead of the declared {@link Field#getName()}.
     */
    public String name() default "";


    /**
     * (Optional) Specifies the version of the enclosing {@link PofType} when the {@link Field} was added.  If
     * not specified, the default value of 0 is used as the initial version.
     */
    public int since() default 0;


    /**
      * (Optional) Specifies the required concrete type of the {@link Field} to instantiate when deserializing
      * the {@link Field}. If not specified, the declared type of the {@link Field}, ie: {@link Field#getType()} will 
      * be used.
      * <p>
      * For example:  If a {@link Field} is declared as a generic Map<?, ?>, by specifying the {@link #type()} of this
      * annotation to be a ConcurrentHashMap.class a serializer must instantiate and deserialize values into a new 
      * ConcurrentHashMap instance instead of using some other, possibly internal, Map implementation and/or the 
      * declared {@link Field} type.
      * <p>
      * Note: That when a {@link #type()} is specified, it must be assignable to the declared type of the 
      * annonated {@link Field}.
      */
    public Class<?> type() default Object.class;
}
