/*
 * File: Configurator.java
 * 
 * Copyright (c) 2010. All Rights Reserved. Oracle Corporation.
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
package com.oracle.coherence.configuration;

import com.oracle.coherence.common.builders.ReflectiveBuilder;
import com.oracle.coherence.environment.extensible.ConfigurationContext;
import com.oracle.coherence.environment.extensible.ConfigurationException;
import com.oracle.coherence.environment.extensible.QualifiedName;
import com.tangosol.run.xml.XmlElement;

/**
 * A {@link Configurator} provides the ability to extract and inject annotated {@link Property}s for 
 * {@link Configurable} objects from an {@link XmlElement}.
 * <p>
 * NOTE: This class is now deprecated.  All of the methods are now available on a {@link ConfigurationContext}.
 *
 * @see Configurable
 * @see Property
 *
 * @author Brian Oliver
 */
@Deprecated
public class Configurator
{

    /**
     * <p>Sets the annotated {@link Property}s of the {@link Configurable} object with those properties
     * that are available in the provided {@link XmlElement}.</p>
     * 
     * @param object        The {@link Configurable} object that requires properties to be set.
     * @param context       The {@link ConfigurationContext} that may be used for processing XML.
     * @param qualifiedName The {@link QualifiedName} of the {@link XmlElement} we're processing.
     * @param xmlElement    The {@link XmlElement} containing the properties for the object.
     * 
     * @throws ConfigurationException if a configuration is not valid
     */
    public static void configure(Object object,
                                 ConfigurationContext context,
                                 QualifiedName qualifiedName,
                                 XmlElement xmlElement) throws ConfigurationException
    {
        //TODO: Add a warning that this method is deprecated
        context.configure(object, qualifiedName, xmlElement);
    }


    /**
     * <p>Determines if the specified property is defined in the {@link XmlElement}.</p>
     * 
     * @param propertyName  The name of the property
     * @param context       The {@link ConfigurationContext} that may be used for processing XML.
     * @param qualifiedName The {@link QualifiedName} of the {@link XmlElement}
     * @param xmlElement    The {@link XmlElement} in which to search for the property
     * 
     * @return <code>true</code> if the property is defined in the {@link XmlElement}, <code>false</code> otherwise.
     * @throws ConfigurationException if a configuration is not valid
     */
    @Deprecated
    public static boolean isPropertyDefined(String propertyName,
                                            ConfigurationContext context,
                                            QualifiedName qualifiedName,
                                            XmlElement xmlElement) throws ConfigurationException
    {
        //TODO: Add a warning that this method is deprecated
        return context.isPropertyDefined(propertyName, qualifiedName, xmlElement);
    }


    /**
     * <p>Attempts to return the value for the specified property declared within the provided {@link XmlElement}.</p>
     * 
     * <p>PRE-CONDITION: {@link #isPropertyDefined(String, ConfigurationContext, QualifiedName, XmlElement)}.</p>
     * 
     * @param <T>             The type of the Property
     * @param propertyName    The name of the property
     * @param propertyType    The type of the property
     * @param propertySubType If the propertyType is generic, like a {@link ReflectiveBuilder}, the genericType may be used for further type checking.  Set to null if not required.
     * @param context         The {@link ConfigurationContext} that may be used for processing XML.
     * @param qualifiedName   The {@link QualifiedName} of the {@link XmlElement} we're processing.
     * @param xmlElement      The {@link XmlElement} containing the properties for the object.
     * 
     * @return the mandatory property
     * @throws ConfigurationException if a configuration is not valid
     */
    public static <T> T getMandatoryProperty(String propertyName,
                                             Class<T> propertyType,
                                             Class<?> propertySubType,
                                             ConfigurationContext context,
                                             QualifiedName qualifiedName,
                                             XmlElement xmlElement) throws ConfigurationException
    {
        //TODO: Add a warning that this method is deprecated
        return (T)context.getMandatoryProperty(propertyName, propertyType, propertySubType, qualifiedName, xmlElement);
    }


    /**
     * <p>Attempts to return the value for the specified property declared within the provided {@link XmlElement}.</p>
     * 
     * @param <T>           The type of the Property
     * @param propertyName  The name of the property
     * @param propertyType  The type of the property
     * @param context       The {@link ConfigurationContext} that may be used for processing XML.
     * @param qualifiedName The {@link QualifiedName} of the {@link XmlElement} we're processing.
     * @param xmlElement    The {@link XmlElement} containing the properties for the object.
     *
     * @return the mandatory property
     * @throws ConfigurationException if a configuration is not valid
     */
    public static <T> T getMandatoryProperty(String propertyName,
                                             Class<T> propertyType,
                                             ConfigurationContext context,
                                             QualifiedName qualifiedName,
                                             XmlElement xmlElement) throws ConfigurationException
    {
        //TODO: Add a warning that this method is deprecated
        return (T)context.getMandatoryProperty(propertyName, propertyType, null, qualifiedName, xmlElement);
    }


    /**
     * <p>Attempts to return the value for the optional property declared within the provided {@link XmlElement}.</p>
     * 
     * @param <T>             The type of the optional property
     * @param propertyName    The name of the property
     * @param propertyType    The type of the property
     * @param propertySubType If the propertyType is generic, like a {@link ReflectiveBuilder}, the genericType may be used for further type checking.  Set to {@link Void} if not required.
     * @param defaultValue    The returned value if no property is found
     * @param context         The {@link ConfigurationContext} that may be used for processing XML.
     * @param qualifiedName   The {@link QualifiedName} of the {@link XmlElement} we're processing.
     * @param xmlElement      The {@link XmlElement} containing the properties for the object.
     * 
     * @return the optional property
     * @throws ConfigurationException if a configuration is not valid
     */
    public static <T> T getOptionalProperty(String propertyName,
                                            Class<T> propertyType,
                                            Class<?> propertySubType,
                                            T defaultValue,
                                            ConfigurationContext context,
                                            QualifiedName qualifiedName,
                                            XmlElement xmlElement) throws ConfigurationException
    {
        //TODO: Add a warning that this method is deprecated
        return (T)context.getOptionalProperty(propertyName, propertyType, propertySubType, defaultValue, qualifiedName,
            xmlElement);
    }


    /**
     * <p>Attempts to return the value for the optional property declared within the provided {@link XmlElement}.</p>
     * 
     * @param <T>           The type of the optional property
     * @param propertyName  The name of the property
     * @param propertyType  The type of the property
     * @param defaultValue  The returned value if no property is found
     * @param context       The {@link ConfigurationContext} that may be used for processing XML.
     * @param qualifiedName The {@link QualifiedName} of the {@link XmlElement} we're processing.
     * @param xmlElement    The {@link XmlElement} containing the properties for the object.
     * 
     * @return the optional property
     * @throws ConfigurationException if a configuration is not valid
     */
    public static <T> T getOptionalProperty(String propertyName,
                                            Class<T> propertyType,
                                            T defaultValue,
                                            ConfigurationContext context,
                                            QualifiedName qualifiedName,
                                            XmlElement xmlElement) throws ConfigurationException
    {
        //TODO: Add a warning that this method is deprecated
        return (T)context.getOptionalProperty(propertyName, propertyType, defaultValue, qualifiedName, xmlElement);
    }
}
