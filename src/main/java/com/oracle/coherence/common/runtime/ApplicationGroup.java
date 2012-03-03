/*
 * File: ApplicationGroup.java
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

/**
 * An {@link ApplicationGroup} represents a collection of related {@link Application}s at runtime.
 * <p>
 * {@link ApplicationGroup}s are created using {@link ApplicationGroupBuilder}s.
 *
 * @param <A>  The type of the {@link Application}
 *
 * @author Brian Oliver
 */
public interface ApplicationGroup<A extends Application> extends Iterable<A>
{
    /**
     * Destroys all of the {@link Application}s in the {@link ApplicationGroup}.  Upon returning from this method you
     * can safely assume all {@link Application}s in the {@link ApplicationGroup} are no longer running.
     */
    public void destroy();
}
