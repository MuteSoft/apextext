/*
 * PropertyManager.java 
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

import java.io.FileInputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.PropertyResourceBundle;

/**
 * This class is used to read properties from a properties file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class PropertyManager {

    /**
     * A table containing property resource bundles.
     */
    static HashMap<String, PropertyResourceBundle> propertyMap = null;

    /**
     * This method loads a property file, makes a PropertyResourceBundle and finally puts in
     * a HashMap.
     * @param filename The property file name.
     * @throws java.io.IOException 
     */
    private static void readPropFile(String filename) throws IOException {
        FileInputStream fr = new FileInputStream(filename);
        PropertyResourceBundle prb = new PropertyResourceBundle(fr);
        propertyMap.put(filename, prb);
    }

    /**
     * This method returns property value for a given property in a given properties file.
     * Instead of loading the properties file everytime, we are using PropertyResourceBundle which
     * gives the flexibility of load the file once and get anytime any property from that
     * properties file.
     * @param filename The property file name.
     * @param property The property key.
     * @throws java.io.IOException
     * @return The property value.
     */
    public static String getProperty(String filename, String property) throws
            IOException {
        String propertyValue = "";
        if (propertyMap == null) {
            propertyMap = new HashMap<String, PropertyResourceBundle>();
        }
        PropertyResourceBundle prb =
                propertyMap.get(filename);
        if (prb == null) {
            readPropFile(filename);
            prb = propertyMap.get(filename);
        }
        propertyValue = (String) prb.handleGetObject(property);
        return propertyValue;
    }

    /**
     * Creates a new instance of {@code PropertyManager}.
     */
    private PropertyManager() {
    }
}
