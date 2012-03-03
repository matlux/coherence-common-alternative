/*
 * File: EmptyTest.java
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
import org.junit.Assert;
import org.junit.Test;

/**
 * The {@link EmptyTest} is designed as a place holder for JUnit Tests.  This ensures the continuous build system
 * does not fail when JUnit test folders are empty.
 * <p>
 * NOTE: Test folders are empty in circumstances where existing tests have not yet been migrated from the 
 * internal Oracle test framework to something that is publically executable. 
 * 
 * @author Brian Oliver
 */
public class EmptyTest
{
    
    @Test
    public void testNothing()
    {
        //deliberately empty
        Assert.assertTrue(true);
    }
}
