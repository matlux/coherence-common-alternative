/*
 * File: LogApplicationConsole.java
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

import java.io.FileWriter;
import java.io.IOException;

/**
 * A {@link LogApplicationConsole} is an implementation of an {@link ApplicationConsole} that logs output to a file.
 *
 * @author Christer Fahlgren
 */
public class LogApplicationConsole implements ApplicationConsole
{
    /**
     * The {@link FileWriter} used to write to a file.
     */
    private FileWriter fw;


    /**
     * Standard Constructor.
     *
     * @param fileName the file name of the log file
     *
     * @throws IOException if opening the file fails
     */
    public LogApplicationConsole(String fileName) throws IOException
    {
        try
        {
            this.fw = new FileWriter(fileName, true);
        }
        catch (IOException e)
        {
            e.printStackTrace();

            throw e;
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void printf(String format,
                       Object... args)
    {
        String formattedString = String.format(format, args);

        try
        {
            fw.write(formattedString);
            fw.flush();
        }
        catch (IOException e)
        {
            // failing to log is serious, but we have no outlet but to log the occurrence to stdout
            System.out.println("Exception occured while writing to " + fw + " the exception was " + e);
            e.printStackTrace();
        }
    }
}
