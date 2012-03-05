package com.oracle.coherence.common.runtime;

/**
 * @author Jonathan Knight
 */
public class SimpleJavaApplicationSchema
        extends AbstractJavaApplicationSchema<JavaConsoleApplication,SimpleJavaApplicationSchema>
        implements JavaApplicationSchema<JavaConsoleApplication,SimpleJavaApplicationSchema>
{

    /**
     * Construct a {@link com.oracle.coherence.common.runtime.JavaApplicationSchema} with a given application class name,
     * but using the class path of the executing application.
     *
     * @param applicationClassName The fully qualified class name of the Java application.
     */
    public SimpleJavaApplicationSchema(String applicationClassName) {
        super(applicationClassName);
    }

    /**
     * Construct a {@link com.oracle.coherence.common.runtime.JavaApplicationSchema}.
     *
     * @param applicationClassName The fully qualified class name of the Java application.
     * @param classPath            The class path for the Java application.
     */
    public SimpleJavaApplicationSchema(String applicationClassName, String classPath) {
        super(applicationClassName, classPath);
    }

}
