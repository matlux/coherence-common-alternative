/*
 * File: NamedCacheSerializerBuilder.java
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
package com.oracle.coherence.common.builders;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.oracle.coherence.configuration.expressions.Constant;
import com.oracle.coherence.configuration.expressions.Expression;
import com.oracle.coherence.configuration.parameters.ParameterProvider;
import com.tangosol.io.ExternalizableLite;
import com.tangosol.io.Serializer;
import com.tangosol.io.pof.PofReader;
import com.tangosol.io.pof.PofWriter;
import com.tangosol.io.pof.PortableObject;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.ExternalizableHelper;

/**
 * A {@link NamedCacheSerializerBuilder} is a {@link ParameterizedBuilder} for a {@link Serializer}
 * for a specific {@link NamedCache}.
 *
 * @author Brian Oliver
 */
@SuppressWarnings("serial")
public class NamedCacheSerializerBuilder implements ParameterizedBuilder<Serializer>, ExternalizableLite,
        PortableObject
{

    /**
     * An {@link Expression} that will yeild the name of the cache from which to determine the serializer.
     */
    private Expression m_exprCacheName;


    /**
     * Required for {@link ExternalizableLite} and {@link PortableObject}.
     */
    public NamedCacheSerializerBuilder()
    {
        //SKIP: deliberately empty
    }


    /**
     * Standard Constructor (using an explicit cache name)
     * 
     * @param cacheName The name of the cache.
     */
    public NamedCacheSerializerBuilder(String cacheName)
    {
        m_exprCacheName = new Constant(cacheName);
    }


    /**
     * Standard Constructor (using an expression)
     * 
     * @param exprCacheName An {@link Expression} to determine the cache name
     */
    public NamedCacheSerializerBuilder(Expression exprCacheName)
    {
        m_exprCacheName = exprCacheName;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Serializer realize(ParameterProvider parameterProvider)
    {
        return CacheFactory.getCache(m_exprCacheName.evaluate(parameterProvider).getString()).getCacheService()
            .getSerializer();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean realizesClassOf(Class<?> clazz,
                                   ParameterProvider parameterProvider)
    {
        return clazz.isAssignableFrom(NamedCache.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readExternal(DataInput in) throws IOException
    {
        m_exprCacheName = (Expression) ExternalizableHelper.readObject(in);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeExternal(DataOutput out) throws IOException
    {
        ExternalizableHelper.writeObject(out, m_exprCacheName);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void readExternal(PofReader reader) throws IOException
    {
        m_exprCacheName = (Expression) reader.readObject(1);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeExternal(PofWriter writer) throws IOException
    {
        writer.writeObject(1, m_exprCacheName);
    }
}
