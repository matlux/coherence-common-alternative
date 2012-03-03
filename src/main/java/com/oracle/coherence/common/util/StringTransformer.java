/*
 * File: StringTransformer.java
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
package com.oracle.coherence.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A {@link StringTransformer} is a {@link UniformTransformer} that uses regular expressions to conditionally match and 
 * transform strings into other strings.  These are very useful for mapping cache names into other names.
 *
 * @author Brian Oliver
 */
public class StringTransformer implements UniformTransformer<String>
{

    /**
     * The regular expression pattern used to match a string.
     */
    private String matchingRegEx;

    /**
     * The regular expression pattern used to transform the matched string.
     */
    private String transformationRegEx;

    /**
     * The compiled matching pattern.
     */
    private Pattern matchingPattern;


    /**
     * Standard Constructor.
     * 
     * @param matchingRegEx         The regular expression pattern to match a string
     * @param transformationRegEx   The regular expression to transform the matched string
     */
    public StringTransformer(String matchingRegEx,
                             String transformationRegEx)
    {
        this.matchingRegEx = matchingRegEx;
        this.transformationRegEx = transformationRegEx;
        this.matchingPattern = Pattern.compile(matchingRegEx);
    }


    /**
     * Returns if the specified string may be transformed with this {@link StringTransformer}.
     * 
     * @param string The string to check for a match
     * 
     * @return <code>true</code> if the string may be transformed by this {@link StringTransformer}, 
     *         <code>false</code> otherwise.
     */
    public boolean isTransformable(String string)
    {
        return string.matches(matchingRegEx);
    }


    /**
     * Returns the transformed string given a string using the regular pattern expressions provided when creating 
     * the {@link StringTransformer}.
     * 
     * @param string The string to transform
     * 
     * @return The transformed string from the provided string
     */
    public String transform(String string)
    {
        Matcher matcher = matchingPattern.matcher(string);
        return matcher.replaceAll(transformationRegEx);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return String.format("StringTransformer{matchingRegEx=%s, transformationRegEx=%s}", matchingRegEx,
            transformationRegEx);
    }
}
