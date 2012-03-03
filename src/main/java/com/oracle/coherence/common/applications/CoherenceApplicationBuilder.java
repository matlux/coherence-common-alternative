package com.oracle.coherence.common.applications;

/**
 * A {@link CoherenceApplicationBuilder} is a specialized {@link AbstractCoherenceJavaApplicationBuilder} that's designed
 * explicitly for realizing Coherence applications with custom main classes.
 * <p>
 * NOTE: This class is now deprecated.  As a replacement please use the com.oracle.coherence.common.runtime package
 * for controlling application processes and coherence servers.
 * 
 * @author narliss
 */
@Deprecated
public class CoherenceApplicationBuilder extends AbstractCoherenceJavaApplicationBuilder<CoherenceApplicationBuilder>
{
    /**
     * Create a {@link CoherenceApplicationBuilder} for the specified class.
     * 
     * @param applicationClassName the main class to instantiate when this builder is realized
     */
    public CoherenceApplicationBuilder(String applicationClassName)
    {
        super(applicationClassName);
    }
    
    /**
     * Create a {@link CoherenceApplicationBuilder} for the specified class.
     * 
     * @param applicationClassName  the main class to instantiate when this builder is realized
     * @param classPath             the classPath to use when this build is realized
     */
    public CoherenceApplicationBuilder(String applicationClassName,
                                       String classPath)
    {
        super(applicationClassName, classPath);
    }
}
