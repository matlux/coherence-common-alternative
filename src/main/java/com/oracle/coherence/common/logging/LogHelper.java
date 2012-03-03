package com.oracle.coherence.common.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * <p>The {@link LogHelper} is a workaround around the fact that {@link Logger} has convenience methods
 * for logging entry and exit that is hardcoded to be logging at {@link Level}.FINER. The LogHelper class
 * has static methods to do the same but with a log level of {@link Level}.FINEST</p>
 *
 * @author Christer Fahlgren
 */
public class LogHelper
{

    /**
     * Logs a message indicating that a method has been entered. A log record
     * with log level {@code Level.FINEST}, log message "ENTRY", the specified
     * source class name and source method name is submitted for logging.
     *
     * @param logger       the {@link Logger} to use 
     * @param sourceClass  the calling class name
     * @param sourceMethod the method name
     */
    public static void entering(Logger logger,
                                String sourceClass,
                                String sourceMethod)
    {
        if (!logger.isLoggable(Level.FINEST))
        {
            return;
        }
        LogRecord record = new LogRecord(Level.FINEST, "ENTRY");
        record.setLoggerName(logger.getName());
        record.setSourceClassName(sourceClass);
        record.setSourceMethodName(sourceMethod);
        logger.log(record);
    }


    /**
     * Logs a message indicating that a method has been entered. A log record
     * with log level {@code Level.FINEST}, log message "ENTRY", the specified
     * source class name, source method name and one parameter is submitted for
     * logging.
     *
     * @param logger       the {@link Logger} to use 
     * @param sourceClass  the source class name
     * @param sourceMethod the source method name
     * @param param        the parameter for the method call
     */
    public static void entering(Logger logger,
                                String sourceClass,
                                String sourceMethod,
                                Object param)
    {
        if (!logger.isLoggable(Level.FINEST))
        {
            return;
        }
        LogRecord record = new LogRecord(Level.FINEST, "ENTRY" + " {0}"); 
        record.setLoggerName(logger.getName());
        record.setSourceClassName(sourceClass);
        record.setSourceMethodName(sourceMethod);
        record.setParameters(new Object[] { param });
        logger.log(record);
    }


    /**
      * Logs a message indicating that a method has been entered. A log record
      * with log level {@code Level.FINEST}, log message "ENTRY", the specified
      * source class name, source method name and array of parameters is
      * submitted for logging.
      * @param logger       the {@link Logger} to use 
      * @param sourceClass  the calling class name
      * @param sourceMethod the method name
      * @param params       an array of parameters for the method call
      */
    @SuppressWarnings("nls")
    public static void entering(Logger logger,
                                String sourceClass,
                                String sourceMethod,
                                Object[] params)
    {
        if (!logger.isLoggable(Level.FINEST))
        {
            return;
        }
        String msg = "ENTRY";
        if (params != null)
        {
            StringBuilder msgBuffer = new StringBuilder("ENTRY");
            for (int i = 0; i < params.length; i++)
            {
                msgBuffer.append(" {").append(i).append("}");
            }
            msg = msgBuffer.toString();
        }
        LogRecord record = new LogRecord(Level.FINEST, msg);
        record.setLoggerName(logger.getName());
        record.setSourceClassName(sourceClass);
        record.setSourceMethodName(sourceMethod);
        record.setParameters(params);
        logger.log(record);
    }


    /**
     * Logs a message indicating that a method is exited. A log record with log
     * level {@code Level.FINEST}, log message "RETURN", the specified source
     * class name and source method name is submitted for logging.
     *
     * @param logger       the {@link Logger} to use
     * @param sourceClass  the calling class name
     * @param sourceMethod the method name
     */
    public static void exiting(Logger logger,
                               String sourceClass,
                               String sourceMethod)
    {
        if (!logger.isLoggable(Level.FINEST))
        {
            return;
        }
        LogRecord record = new LogRecord(Level.FINEST, "RETURN"); 
        record.setLoggerName(logger.getName());
        record.setSourceClassName(sourceClass);
        record.setSourceMethodName(sourceMethod);
        logger.log(record);
    }


    /**
     * Logs a message indicating that a method is exited. A log record with log
     * level {@code Level.FINEST}, log message "RETURN", the specified source
     * class name, source method name and return value is submitted for logging.
     *
     * @param logger       the {@link Logger} to use 
     * @param sourceClass  the calling class name
     * @param sourceMethod the method name
     * @param result       the return value of the method call
     */
    public static void exiting(Logger logger,
                               String sourceClass,
                               String sourceMethod,
                               Object result)
    {
        if (!logger.isLoggable(Level.FINEST))
        {
            return;
        }
        LogRecord record = new LogRecord(Level.FINEST, "RETURN" + " {0}"); 
        record.setLoggerName(logger.getName());
        record.setSourceClassName(sourceClass);
        record.setSourceMethodName(sourceMethod);
        record.setParameters(new Object[] { result });
        logger.log(record);
    }


    /**
     * Logs a message indicating that an exception is thrown. A log record with
     * log level {@code Level.FINEST}, log message "THROW", the specified source
     * class name, source method name and the {@code Throwable} object is
     * submitted for logging.
     *
     * @param logger       the {@link Logger} to use 
     * @param sourceClass  the calling class name
     * @param sourceMethod the method name
     * @param thrown       the {@code Throwable} object
     */
    public static void throwing(Logger logger,
                                String sourceClass,
                                String sourceMethod,
                                Throwable thrown)
    {
        if (!logger.isLoggable(Level.FINEST))
        {
            return;
        }
        LogRecord record = new LogRecord(Level.FINEST, "THROW"); //$NON-NLS-1$
        record.setLoggerName(logger.getName());
        record.setSourceClassName(sourceClass);
        record.setSourceMethodName(sourceMethod);
        record.setThrown(thrown);
        logger.log(record);
    }
}
