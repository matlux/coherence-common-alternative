/*
 * File: LifecycleAwareEventProcessor.java
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
package com.oracle.coherence.common.events.processing;

import com.oracle.coherence.common.events.dispatching.EventDispatcher;

/**
 * A {@link LifecycleAwareEventProcessor} is a friend interface of {@link EventProcessor}s that enables those
 * that are registered with an {@link EventDispatcher} to receive callbacks/notifications during their registration 
 * lifecycle.  This allows {@link EventProcessor}s to perform necessary initialization and cleanup during according to 
 * their lifecycle.
 * 
 * @author Brian Oliver
 */
public interface LifecycleAwareEventProcessor
{

    /**
     * Called by the {@link EventDispatcher} before the {@link EventProcessor} is registered.
     * 
     * @param eventDispatcher The {@link EventDispatcher}
     */
    public void onBeforeRegistered(EventDispatcher eventDispatcher);


    /**
     * Called by the {@link EventDispatcher} after the {@link EventProcessor} was registered.
     * 
     * @param eventDispatcher The {@link EventDispatcher}
     */
    public void onAfterRegistered(EventDispatcher eventDispatcher);


    /**
     * Called by the {@link EventDispatcher} after the {@link EventProcessor} was unregistered.
     * 
     * @param eventDispatcher The {@link EventDispatcher}
     */
    public void onAfterUnregistered(EventDispatcher eventDispatcher);
}
