package com.oracle.coherence.common.util;

import java.lang.reflect.Proxy;

import com.oracle.coherence.common.processors.CreateRemoteObjectProcessor;
import com.oracle.coherence.common.processors.InvokeMethodProcessor;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.cache.CacheEvent;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * <p>A {@link ObjectProxyFactoryTest} tests the {@link ObjectProxyFactory} class.</p>
 *
 * @author Christer Fahlgren
 */
public class ObjectProxyFactoryTest
{

    /**
     * Tests getting a proxy for an object in a named cache. Tests invoking a method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetProxy()
    {
        MockitoNamedCacheFactory.activateMockitoCacheFactoryBuilder();
        NamedCache mockCache = CacheFactory.getCache("testcache");
        Mockito.stub(mockCache.invoke(Matchers.anyString(), Matchers.isA(InvokeMethodProcessor.class))).toReturn(
            "testmessage");
        ObjectProxyFactory<MockInterface> proxyFactory = new ObjectProxyFactory("testcache", MockInterface.class);
        MockInterface proxy = proxyFactory.getProxy("key");
        String msg = proxy.getMessage();
        assertTrue(msg.equals("testmessage"));
    }


    /**
     * Tests getting a proxy for an object in a named cache. Tests invoking a method.
     */
    @SuppressWarnings("unchecked")
    @Test(expected = NullPointerException.class)
    public void testGetProxyForNullKey()
    {
        MockitoNamedCacheFactory.activateMockitoCacheFactoryBuilder();
        NamedCache mockCache = CacheFactory.getCache("testcache");
        Mockito.stub(mockCache.invoke(Matchers.anyString(), Matchers.isA(InvokeMethodProcessor.class))).toReturn(
            "testmessage");
        ObjectProxyFactory<MockInterface> proxyFactory = new ObjectProxyFactory("testcache", MockInterface.class);
        @SuppressWarnings("unused")
        MockInterface proxy = proxyFactory.getProxy(null);
    }


    /**
     * Tests creating an object of DefaultTestInterfaceImplementation and calling a method on the proxy.
     * 
     * @throws Throwable if it fails
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateRemoteObjectIfNotExists() throws Throwable
    {
        MockitoNamedCacheFactory.activateMockitoCacheFactoryBuilder();
        NamedCache mockCache = CacheFactory.getCache("testcache");
        Mockito.stub(mockCache.invoke(Matchers.anyString(), Matchers.isA(CreateRemoteObjectProcessor.class))).toReturn(
            null);
        Mockito.stub(mockCache.invoke(Matchers.anyString(), Matchers.isA(InvokeMethodProcessor.class))).toReturn(
            "testmessage");
        ObjectProxyFactory<MockInterface> proxyFactory = new ObjectProxyFactory("testcache", MockInterface.class);
        MockInterface proxy = proxyFactory.createRemoteObjectIfNotExists("key",
            DefaultMockInterfaceImplementation.class, null);
        String msg = proxy.getMessage();
        assertTrue(msg.equals("testmessage"));
    }


    /**
     * Tests creating an object of DefaultTestInterfaceImplementation that already exists.
     * 
     * @throws Throwable if it fails
     */
    @SuppressWarnings("unchecked")
    @Test(expected = IllegalStateException.class)
    public void testCreateRemoteObjectIfNotExistsThatExists() throws Throwable
    {
        MockitoNamedCacheFactory.activateMockitoCacheFactoryBuilder();
        NamedCache mockCache = CacheFactory.getCache("testcache");
        Mockito.stub(mockCache.invoke(Matchers.anyString(), Matchers.isA(CreateRemoteObjectProcessor.class))).toReturn(
            new IllegalStateException("Object already exists"));
        ObjectProxyFactory<MockInterface> proxyFactory = new ObjectProxyFactory("testcache", MockInterface.class);
        @SuppressWarnings("unused")
        MockInterface proxy = proxyFactory.createRemoteObjectIfNotExists("key",
            DefaultMockInterfaceImplementation.class, null);
    }


    /**
     * Tests destroying an object in a remote cache.
     * 
     * @throws Throwable if removing fails
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testDestroyRemoteObject() throws Throwable
    {
        MockitoNamedCacheFactory.activateMockitoCacheFactoryBuilder();
        NamedCache mockCache = CacheFactory.getCache("testcache");
        mockCache.remove(Matchers.anyString());
        ObjectProxyFactory<MockInterface> proxyFactory = new ObjectProxyFactory("testcache", MockInterface.class);
        proxyFactory.destroyRemoteObject("key");
    }


    /**
     * Tests getting a local copy of remote object.
     * 
     * @throws Throwable if retrieval fails
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testGetLocalCopyOfRemoteObject() throws Throwable
    {
        MockitoNamedCacheFactory.activateMockitoCacheFactoryBuilder();
        NamedCache mockCache = CacheFactory.getCache("testcache");
        Mockito.stub(mockCache.invoke(Matchers.anyString(), Matchers.isA(CreateRemoteObjectProcessor.class))).toReturn(
            null);
        Mockito.stub(mockCache.invoke(Matchers.anyString(), Matchers.isA(InvokeMethodProcessor.class))).toReturn(
            "testmessage");
        ObjectProxyFactory<MockInterface> proxyFactory = new ObjectProxyFactory("testcache", MockInterface.class);
        MockInterface proxy = proxyFactory.createRemoteObjectIfNotExists("key",
            DefaultMockInterfaceImplementation.class, null);
        MockInterface localcopy = proxyFactory.getLocalCopyOfRemoteObject("key");
        assertFalse(proxyFactory.isProxy(localcopy));
        assertTrue(proxyFactory.isProxy(proxy));
    }


    /**
     * Test registering a callback for changes.
     * @throws Throwable if callback registering fails
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testRegisteringCallback() throws Throwable
    {
        MockitoNamedCacheFactory.activateMockitoCacheFactoryBuilder();
        NamedCache mockCache = CacheFactory.getCache("testcache");
        Mockito.stub(mockCache.invoke(Matchers.anyString(), Matchers.isA(CreateRemoteObjectProcessor.class))).toReturn(
            null);
        Mockito.stub(mockCache.invoke(Matchers.anyString(), Matchers.isA(InvokeMethodProcessor.class))).toReturn(
            "testmessage");
        mockCache.addMapListener(Matchers.isA(MapListener.class));
        ObjectProxyFactory<MockInterface> proxyFactory = new ObjectProxyFactory("testcache", MockInterface.class);
        MockInterface proxy = proxyFactory.createRemoteObjectIfNotExists("key",
            DefaultMockInterfaceImplementation.class, null);

        ObjectCallback cb = new ObjectCallback();
        proxyFactory.registerChangeCallback(proxy, cb);
        NamedCacheObjectProxy<MockInterface> invhandler = (NamedCacheObjectProxy<MockInterface>) Proxy
            .getInvocationHandler(proxy);
        invhandler.entryUpdated(new CacheEvent(mockCache, MapEvent.ENTRY_UPDATED, "key", null, false, false));

        assertTrue(cb.getChanged());
        proxyFactory.unregisterChangeCallback(proxy, cb);
        cb.resetChanged();
        invhandler.entryUpdated(new CacheEvent(mockCache, MapEvent.ENTRY_UPDATED, "key", null, false, false));
        assertFalse(cb.getChanged());
    }


    /**
     * <p>A {@link ObjectCallback} is an implementation of {@link ObjectChangeCallback} that keeps track of whether
     * objectChanged was called.</p>
     *
     * @author Christer Fahlgren
     */
    @SuppressWarnings("unchecked")
    public static class ObjectCallback implements ObjectChangeCallback
    {

        /**
         * True if object changed.
         */
        private boolean changed;


        /**
         * Default constructor.
         */
        public ObjectCallback()
        {
            changed = false;
        }


        /**
         * Resets the changed status.
         */
        public void resetChanged()
        {
            changed = false;

        }


        /**
         * {@inheritDoc}
         */
        public void objectChanged(Object object)
        {
            changed = true;
        }


        /**
         * {@inheritDoc}
         */
        public boolean getChanged()
        {
            return changed;
        }


        /**
         * {@inheritDoc}
         */
        public void objectCreated(Object object)
        {
        }


        /**
         * {@inheritDoc}
         */
        public void objectDeleted(Object key)
        {
        }

    }
}
