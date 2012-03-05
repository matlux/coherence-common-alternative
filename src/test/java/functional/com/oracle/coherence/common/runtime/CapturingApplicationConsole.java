package com.oracle.coherence.common.runtime;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan Knight
 */
public class CapturingApplicationConsole implements ApplicationConsole
{

    private List<String> lines = new ArrayList<String>();
    private final Object monitor = new Object();

    /**
     * A convenience method to write a formatted string to a console using
     * the specified format string and arguments.
     *
     * @param format A format string as described in {@link java.util.Formatter} string syntax.
     * @param args   Arguments referenced by the format specifiers in the format string. If there are more
     *               arguments than format specifiers, the extra arguments are ignored.
     *               The number of arguments is variable and may be zero.
     */
    @Override
    public synchronized void printf(String format, Object... args)
    {
        lines.add(String.format(format, args));
        notifyAll();
    }

    /**
     * @return the output that has been written to this ApplicationConsole.
     */
    public String getConsoleOutput()
    {
        return lines.toString();
    }

    public String getConsoleOutputLine(int line)
    {
        return (lines.size() > line) ? lines.get(line) : null;
    }

    /**
     * Clear the text stored in the buffer.
     */
    public synchronized void clear()
    {
        lines.clear();
    }

    public synchronized void waitForLine(long timeout) throws InterruptedException {
        if (lines.isEmpty()) {
            wait(timeout);
        }
    }
}
