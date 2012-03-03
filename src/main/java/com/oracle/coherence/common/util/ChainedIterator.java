/*
 * File: ChainedIterator.java
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

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A {@link ChainedIterator} makes an ordered collection of other {@link Iterator}s appear and operate 
 * as a single {@link Iterator}.
 *
 * @author Brian Oliver
 */
public class ChainedIterator<E> implements Iterator<E>
{

    private LinkedList<Iterator<E>> iterators;


    /**
     * Standard Constructor.
     */
    public ChainedIterator()
    {
        iterators = new LinkedList<Iterator<E>>();
    }


    /**
     * Standard Constructor (using var args)
     * 
     * @param iterators An array of {@link Iterator}s.
     */
    public ChainedIterator(Iterator<E>... iterators)
    {
        this.iterators.addAll(Arrays.asList(iterators));
    }


    /**
     * Adds the specified {@link Iterator} to the {@link ChainedIterator} (as the last)
     * 
     * @param iterator An {@link Iterator}
     * 
     * @return A {@link ChainedIterator}.
     */
    public ChainedIterator<E> addIterator(Iterator<E> iterator)
    {
        this.iterators.addLast(iterator);

        return this;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext()
    {
        while (iterators.size() > 0 && !iterators.get(0).hasNext())
        {
            iterators.removeFirst();
        }

        return iterators.size() > 0;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public E next()
    {
        if (hasNext())
        {
            return iterators.get(0).next();
        }
        else
        {
            throw new IndexOutOfBoundsException("Attempted to iterator past the end of an ChainedIterator");
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("Can't remove from an ChainedIterator");
    }
}
