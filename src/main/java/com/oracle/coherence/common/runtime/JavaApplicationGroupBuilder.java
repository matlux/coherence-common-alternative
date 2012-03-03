/*
 * File: JavaApplicationGroupBuilder.java
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
 * An {@link JavaApplicationGroupBuilder} is a builder for {@link ApplicationGroup}s.  That is, collections of related
 * {@link Application}s.
 *
 * @author Brian Oliver
 */
public interface JavaApplicationGroupBuilder<A extends JavaApplication, S extends JavaApplicationSchema<A, S>,
                                             B extends JavaApplicationBuilder<A, S, B>,
                                             G extends JavaApplicationGroup<A>>
    extends ApplicationGroupBuilder<A, S, B, G>
{
    // this is deliberately empty
}
