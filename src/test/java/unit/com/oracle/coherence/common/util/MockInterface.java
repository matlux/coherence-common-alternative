package com.oracle.coherence.common.util;

/**
 * <p>A {@link MockInterface} used in the unit testing of {@link ObjectProxyFactory}. </p>
 *
 * @author Christer Fahlgren
 */
public interface MockInterface
{

    /**
     * Returns a message.
     * 
     * @return a message
     */
    String getMessage();


    /**
     * Sets a message.
     * 
     * @param message the message to set
     */
    void setMessage(String message);
}
