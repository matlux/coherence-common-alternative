package com.oracle.coherence.common.util;

import java.lang.reflect.Method;

import com.oracle.coherence.common.processors.InvokeMethodProcessor;
import com.tangosol.net.NamedCache;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

/**
 * <p>A {@link NamedCacheObjectProxyTest} testing the dynamic proxy Invocation handler. </p>
 *
 * @author Christer Fahlgren
 */
public class NamedCacheObjectProxyTest
{

    /**
     * Tests invocation using the proxy.
     * 
     * @throws Throwable if invocation fails.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testInvoke() throws Throwable
    {
        NamedCache mockCache = mock(NamedCache.class);
        MockInterface obj = mock(MockInterface.class);
        Method meth = MockInterface.class.getMethod("getMessage", (Class<?>[]) null);
        stub(mockCache.invoke(isA(String.class), isA(InvokeMethodProcessor.class))).toReturn("testmessage");
        NamedCacheObjectProxy proxy = new NamedCacheObjectProxy("key", mockCache);
        Object result = proxy.invoke(obj, meth, null);
        assertTrue(result.equals("testmessage"));
    }


    /**
     * Tests an entry deleted event and its propagation to a callback.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testEntryDeleted()
    {
        ObjectChangeCallback mockcb = mock(ObjectChangeCallback.class);
        mockcb.objectDeleted(isA(String.class));
        NamedCache mockCache = mock(NamedCache.class);
        mockCache.addMapListener(isA(MapListener.class), eq("key"), eq(false));
        MapEvent evt = mock(MapEvent.class);
        stub(evt.getKey()).toReturn("key");
        NamedCacheObjectProxy proxy = new NamedCacheObjectProxy("key", mockCache);
        proxy.registerChangeCallback(mockcb);
        proxy.entryDeleted(evt);
        verify(mockcb).objectDeleted(eq("key"));
    }


    /**
     * Tests an entry inserted event and its propagation to a callback.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testEntryInserted()
    {
        ObjectChangeCallback mockcb = mock(ObjectChangeCallback.class);
        mockcb.objectDeleted(isA(String.class));
        NamedCache mockCache = mock(NamedCache.class);
        mockCache.addMapListener(isA(MapListener.class), eq("key"), eq(false));
        MapEvent evt = mock(MapEvent.class);
        stub(evt.getKey()).toReturn("key");
        stub(evt.getNewValue()).toReturn("value");
        NamedCacheObjectProxy proxy = new NamedCacheObjectProxy("key", mockCache);
        proxy.registerChangeCallback(mockcb);
        proxy.entryInserted(evt);
        verify(mockcb).objectCreated(eq("value"));
    }


    /**
     * Tests an entry updated event and its propagation to a callback.
     * 
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testEntryUpdated()
    {
        ObjectChangeCallback mockcb = mock(ObjectChangeCallback.class);
        mockcb.objectDeleted(isA(String.class));
        NamedCache mockCache = mock(NamedCache.class);
        mockCache.addMapListener(isA(MapListener.class), eq("key"), eq(false));
        MapEvent evt = mock(MapEvent.class);
        stub(evt.getKey()).toReturn("key");
        stub(evt.getNewValue()).toReturn("value");
        NamedCacheObjectProxy proxy = new NamedCacheObjectProxy("key", mockCache);
        proxy.registerChangeCallback(mockcb);
        proxy.entryUpdated(evt);
        verify(mockcb).objectChanged(eq("value"));
    }
}
