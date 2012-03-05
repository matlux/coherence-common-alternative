/*
 * File: SystemApplicationConsole.java
 * 
 * Copyright (c) 2010. All Rights Reserved. Oracle Corporation.
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

/**
 * A {@link com.oracle.coherence.common.runtime.ErrorApplicationConsole} is an implementation
 * of an {@link com.oracle.coherence.common.runtime.ApplicationConsole}.
 *
 * @author Brian Oliver
 */
public class ErrorApplicationConsole implements ApplicationConsole
{

    /**
     * Standard Constructor (without a prefix).
     */
    public ErrorApplicationConsole()
    {
        //deliberately empty
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void printf(String format,
                       Object... args)
    {
        System.err.printf(format, args);
    }
}
