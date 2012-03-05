package com.oracle.coherence.common.runtime;

import java.util.Properties;

/**
 * @author Jonathan Knight
 */
public class SimpleApplication extends AbstractApplication
{
    public SimpleApplication(Process process, String name, ApplicationConsole console, Properties environmentVariables)
    {
        super(process, name, console, environmentVariables);
    }

}
