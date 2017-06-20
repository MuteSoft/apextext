/* 
 * InstanceCreator.java
 * Created on Dec 23, 2007,2:24:51 PM
 *
 * Copyright (C) 2008 Mrityunjoy Saha
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.apex.base.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import org.apex.base.logging.Logger;

/**
 * A utility class to locate and load classes and create instances.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class InstanceCreator {

    /**
     * Creates a new instance of {@code InstanceCreator}.
     */
    private InstanceCreator() {
        super();
    }

    /**
     * Loads a class and creates an instance of loaded class.
     * @param clazz  Fully qualified class name.
     * @return An object of given class name. It returns {@code null} if failed to 
     *               load the class or failed to create instance.
     * @see #loadClass(java.lang.String)
     * @see #createInstance(java.lang.Class)
     */
    public static Object createInstance(String clazz) {
        return createInstance(loadClass(clazz));
    }

    /**
     * Loads a class and creates an instance of loaded class.
     * This method has ability to call parameterized constructors.
     * @param clazz Fully qualified class name.
     * @param argTypes An array of argument types.
     * @param args An array of argument values.
     * @return An object of given named class. It returns {@code null} if failed to
     *               load the class or failed to create instance.
     */
    public static Object createInstance(String clazz, Class[] argTypes, Object[] args) {
        return createInstance(loadClass(clazz), argTypes, args);
    }

    /**
     * Loads a class.     
     * @param clazz Fully qualified class name.
     * @return A {@code Class}.
     */
    public static Class loadClass(String clazz) {       
        if (StringUtil.isNullOrEmpty(clazz)) {
            return null;
        }
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException ex) {
            Logger.logError(
                    "The class " + clazz.toString() + " not found in classpath. The classpath value is: " + System.
                    getProperty("java.class.path"),
                    ex);
            return null;
        }
    }

    /**
     * Creates an instance of given class and returns the same.
     * It creates the instance by invoking no-argument constructor.     
     * @param clazz A class.
     * @return An object of given class. It returns {@code null} if failed to create instance.
     */
    public static Object createInstance(Class clazz) {
        try {
            return clazz == null
                    ? null
                    : clazz.newInstance();
        } catch (InstantiationException ex) {
            Logger.logError(
                    "The class " + clazz.toString() + " loading failed due to InstantiationException.",
                    ex);
            return null;
        } catch (IllegalAccessException ex) {
            Logger.logError(
                    "The class " + clazz.toString() + " loading failed due to IllegalAccessException.",
                    ex);
            return null;
        }
    }

    /**
     * Creates an instance of given class and returns the same.
     * It calls parameterized constructor to create the instance.
     * @param loadClass The class for which object is to be created.
     * @param argTypes An array of argument types.
     * @param args An array of arguments.
     * @return An object of given class. It returns {@code null} if failed to create instance.
     */
    @SuppressWarnings("unchecked")
    private static Object createInstance(Class loadClass, Class[] argTypes, Object[] args) {
        try {
            return loadClass == null
                    ? null
                    : loadClass.getConstructor(argTypes).newInstance(args);
        } catch (InstantiationException ex) {
            Logger.logError(
                    "The class " + loadClass.toString() + " loading failed due to InstantiationException.",
                    ex);
            return null;
        } catch (IllegalAccessException ex) {
            Logger.logError(
                    "The class " + loadClass.toString() + " loading failed due to IllegalAccessException.",
                    ex);
            return null;
        } catch (IllegalArgumentException ex) {
            Logger.logError(
                    "The class " + loadClass.toString() + " loading failed due to IllegalArgumentException.",
                    ex);
            return null;
        } catch (InvocationTargetException ex) {
            Logger.logError(
                    "The class " + loadClass.toString() + " loading failed due to InvocationTargetException.",
                    ex);
            return null;
        } catch (NoSuchMethodException ex) {
            Logger.logError(
                    "The class " + loadClass.toString() + " loading failed due to InstantiationException.",
                    ex);
            return null;
        } catch (SecurityException ex) {
            Logger.logError(
                    "The class " + loadClass.toString() + " loading failed due to IllegalAccessException.",
                    ex);
            return null;
        }

    }
}
