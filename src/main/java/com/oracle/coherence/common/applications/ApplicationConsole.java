/*
 * File: ApplicationConsole.java
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
package com.oracle.coherence.common.applications;
 
import java.util.Formatter;

/**
 * An {@link ApplicationConsole} provides standard out and err output facilities to an executing application.
 * <p>
 * NOTE: This class is now deprecated.  As a replacement please use the com.oracle.coherence.common.runtime package
 * for controlling application processes and coherence servers.
 *
 * @author Brian Oliver
 */
@Deprecated
public interface ApplicationConsole
{

    /**
     * A convenience method to write a formatted string to this console's output stream using 
     * the specified format string and arguments.
     * 
     * @param format    A format string as described in {@link Formatter} string syntax.
     * @param args      Arguments referenced by the format specifiers in the format string. If there are more 
     *                  arguments than format specifiers, the extra arguments are ignored. 
     *                  The number of arguments is variable and may be zero.
     */
    public void printf(String format,
                       Object... args);
}
