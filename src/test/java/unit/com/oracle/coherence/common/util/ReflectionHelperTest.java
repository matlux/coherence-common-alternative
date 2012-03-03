package com.oracle.coherence.common.util;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

/**
 * <p>A {@link ReflectionHelperTest}. </p>
 *
 * @author Christer Fahlgren
 */
public class ReflectionHelperTest
{
    /**
     * Tests the getCompatibleConstructor helper method.
     * 
     * @throws SecurityException         if access is not allowed
     * @throws NoSuchMethodException     if no such method exists
     * @throws IllegalArgumentException  if an argument is illegal
     * @throws InstantiationException    if instantiation fails
     * @throws IllegalAccessException    if access is not allowed
     * @throws InvocationTargetException if constructor fails
     * @throws ClassNotFoundException    if the class is not found
     */
    @Test
    public void testgetCompatibleConstructor() throws SecurityException,
        NoSuchMethodException,
        IllegalArgumentException,
        InstantiationException,
        IllegalAccessException,
        InvocationTargetException, ClassNotFoundException
    {
        Object[] parameterList = new Object[] { new Integer(100), new Integer(200) };
        Class<?>[] classList = ReflectionHelper.getClassArrayFromObjectArray(parameterList);
        Constructor<?> con = null;
        try
        {
            con = java.awt.Point.class.getDeclaredConstructor(classList);
        }
        catch (NoSuchMethodException exception)
        {
        }
        assertNull(con);
        con = ReflectionHelper.getCompatibleConstructor(java.awt.Point.class, classList);
        assertTrue(con != null);
        Object obj = ReflectionHelper.createObject(java.awt.Point.class.getName(),parameterList, Thread.currentThread().getContextClassLoader());
        assertTrue(obj.getClass().equals(java.awt.Point.class));
    }
    
    /**
     * Tests the getCompatibleConstructor helper method.
     * 
     * @throws SecurityException         if access is not allowed
     * @throws NoSuchMethodException     if no such method exists
     * @throws IllegalArgumentException  if an argument is illegal
     * @throws InstantiationException    if instantiation fails
     * @throws IllegalAccessException    if access is not allowed
     * @throws InvocationTargetException if constructor fails
     */
    @Test
    public void testgetCompatibleConstructorNoMatch() throws SecurityException,
        NoSuchMethodException,
        IllegalArgumentException,
        InstantiationException,
        IllegalAccessException,
        InvocationTargetException
    {
        Object[] parameterList = new Object[] { new Integer(100), new Boolean(false) };
        Class<?>[] classList = ReflectionHelper.getClassArrayFromObjectArray(parameterList);
        Constructor<?> con = null;
        try
        {
            con = java.awt.Point.class.getDeclaredConstructor(classList);
        }
        catch (NoSuchMethodException exception)
        {
        }
        assertNull(con);
        con = ReflectionHelper.getCompatibleConstructor(java.awt.Point.class, classList);
        assertNull(con);
    }
}
