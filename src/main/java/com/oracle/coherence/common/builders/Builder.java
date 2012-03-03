/*
 * File: Builder.java
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
package com.oracle.coherence.common.builders;

/**
 * This {@link Builder} interface represents an abstraction of the classic Builder Pattern.  Any class implementing
 * this interface is considered a {@link Builder} for another class.
 * <p>
 * Note: The specifics method(s) for realizing resources from {@link Builder}s are implementation specific
 * and thus only appear on specializations of this interface.  The typical and customary name for said methods 
 * however is <code>realize</code>
 *
 * @param <T> The class of object that will be produced by the {@link Builder}
 * 
 * @author Brian Oliver
 */
public abstract interface Builder<T>
{
    //this is a marker interface. you should never have an instance of this.
}
