/*
 * File: LiveObject.java
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

import com.oracle.coherence.common.events.EntryEvent;

import java.util.Map.Entry;

/**
 * A {@link LiveObject} is a cache {@link java.util.Map.Entry} value that is capable of handling
 * {@link com.oracle.coherence.common.events.Event}s that occur on itself.
 * <p>
 * Essentially a {@link LiveObject} is a specialized {@link EventProcessor}, specifically for {@link EntryEvent}s.
 * <p>
 * Unlike the original LifecycleAwareCacheEntry class from Coherence Common 1.5 which where based on
 * {@link com.tangosol.util.MapEvent}s, {@link LiveObject}s are based on the new {@link EntryEvent}
 * interfaces/classes.
 * <p>
 * Note however, like Coherence Common 1.5, processing of the {@link com.oracle.coherence.common.events.Event} occurs
 * on the thread that raised the said {@link com.oracle.coherence.common.events.Event} and as such care should be taken
 * when processing the {@link com.oracle.coherence.common.events.Event} as this will occur on a Coherence
 * Service/Worker thread.  If there is any doubt as to the thread-safety of calls made by the
 * {@link EventProcessor#process(com.oracle.coherence.common.events.dispatching.EventDispatcher, Event)} method,
 * the said method should use an alternate thread for execution.
 *
 * @author Brian Oliver
 */
@SuppressWarnings("rawtypes")
public interface LiveObject<E extends Entry> extends LifecycleAwareEntry<E>
{
    /**
     * This interface is deliberately empty as it inherits the signatures of the EventProcessor.
     */
}
