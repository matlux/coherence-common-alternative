package com.oracle.coherence.common.util;

import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheFactoryBuilder;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.Service;
import com.tangosol.run.xml.XmlElement;

/**
 * This is a utility class derived from an Easymock variant of this class in main Coherence.

 * This utility class to ease the creation of mock {@link NamedCache} instances
 * using Mockito. This utility can be used like this:
 * <p>
 * For general use with {@link CacheFactory}:
 * <pre>
 *   MockitoNamedCacheFactory.activateMockitoCacheFactoryBuilder();
 *   NamedCache mockCache = CacheFactory.getCache("mock");
 *   // mockCache is backed by Mockito
 *   ...
 * </pre>
 *
 * @author pp  2010.02.05
 * @author Christer Fahlgren 2010.05.18
 */
public class MockitoNamedCacheFactory implements ConfigurableCacheFactory
{

    /**
     * {@inheritDoc}
     */
    public XmlElement getConfig()
    {
        throw new UnsupportedOperationException();
    }


    /**
     * {@inheritDoc}
     */
    public void setConfig(XmlElement xmlConfig)
    {
        throw new UnsupportedOperationException();
    }


    /**
     * {@inheritDoc}
     */
    public synchronized NamedCache ensureCache(String sCacheName,
                                               ClassLoader loader)
    {
        NamedCache mockNamedCache = m_mockNamedCacheMap.get(sCacheName);
        if (mockNamedCache == null)
        {
            mockNamedCache = mock(NamedCache.class);
            m_mockNamedCacheMap.put(sCacheName, mockNamedCache);
        }
        return mockNamedCache;
    }


    /**
     * {@inheritDoc}
     */
    public void releaseCache(NamedCache cache)
    {
    }


    /**
     * {@inheritDoc}
     */
    public void destroyCache(NamedCache cache)
    {
    }


    /**
     * {@inheritDoc}
     */
    public Service ensureService(String sServiceName)
    {
        throw new UnsupportedOperationException();
    }


    // ----- inner classes --------------------------------------------------

    /**
    * Stub implementation of CacheFactoryBuilder that returns an instance
    * of StubCacheFactory.
    */
    public static class MockitoNamedCacheFactoryBuilder implements CacheFactoryBuilder
    {

        /**
         * {@inheritDoc}
         */
        public ConfigurableCacheFactory getConfigurableCacheFactory(ClassLoader loader)
        {
            return m_cacheFactory;
        }


        /**
         * {@inheritDoc}
         */
        public ConfigurableCacheFactory getConfigurableCacheFactory(String sConfigURI,
                                                                    ClassLoader loader)
        {
            return m_cacheFactory;
        }


        /**
         * {@inheritDoc}
         */
        public void setCacheConfiguration(ClassLoader loader,
                                          XmlElement xmlConfig)
        {
            throw new UnsupportedOperationException();
        }


        /**
         * {@inheritDoc}
         */
        public void setCacheConfiguration(String sConfigURI,
                                          ClassLoader loader,
                                          XmlElement xmlConfig)
        {
            throw new UnsupportedOperationException();
        }


        /**
         * {@inheritDoc}
         */
        public void releaseAll(ClassLoader loader)
        {
            throw new UnsupportedOperationException();
        }


        /**
         * {@inheritDoc}
         */
        public void release(ConfigurableCacheFactory factory)
        {
            throw new UnsupportedOperationException();
        }

        /**
         * The {@link ConfigurableCacheFactory} to use.
         */
        private final ConfigurableCacheFactory m_cacheFactory = new MockitoNamedCacheFactory();
    }


    // ----- helper methods -------------------------------------------------

    /**
    * Helper method to configure {@link CacheFactory} to return a
    * MockNamedCache.
    */
    public static void activateMockitoCacheFactoryBuilder()
    {
        CacheFactory.setCacheFactoryBuilder(new MockitoNamedCacheFactoryBuilder());
    }

    // ----- member variables -----------------------------------------------

    /**
    * Map of mock NamedCache instances keyed by cache name.
    */
    private Map<String, NamedCache> m_mockNamedCacheMap = new HashMap<String, NamedCache>();
}
