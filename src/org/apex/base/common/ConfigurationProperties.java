/*
 * ConfigurationProperties.java 
 * Created on 4 August, 2007, 5:59 PM
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
package org.apex.base.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import org.apex.base.util.StringUtil;

/**
 * A table of key-value pairs used by configuration objects to
 * store and read user preferences.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ConfigurationProperties extends Properties {

    /**
     * Creates a new instance of {@code ConfigurationProperties}.
     */
    public ConfigurationProperties() {
    }

    /**
     * Searches for the property with the specified key in this property list. It converts the
     * key to lower case text and then searches. The method returns
     * {@code null} if the property is not found.
     * @param key The property key. 
     * @return The property value.
     * @see #setProperty(java.lang.String, java.lang.String) 
     */
    @Override
    public String getProperty(String key) {
        String retValue = null;
        if (StringUtil.isNullOrEmpty(key)) {
            return null;
        }
        key = key.trim().toLowerCase();
        retValue = super.getProperty(key);
        return retValue;
    }

    /**
     * Searches for the property with the specified key in this property list. It converts the
     * key to lower case text and then searches. The method returns the
     * default value argument if the property is not found.
     * @param key The property key.
     * @param defaultValue A default value.
     * @return The property value.
     * @see #setProperty(java.lang.String, java.lang.String) 
     */
    @Override
    public String getProperty(String key, String defaultValue) {
        String retValue = getProperty(key);
        return retValue == null
                ? defaultValue
                : retValue;
    }

    /**
     * Sets a key and value pair in the property list.
     * It converts the key to lower case text and then sets.
     * @param key The key to be placed into this property list.
     * @param value The value corresponding to {@code key}.
     * @return The previous value of the specified key in this property
     *               list, or {@code null} if it did not have one.
     * @see #getProperty(java.lang.String) 
     * @see #getProperty(java.lang.String, java.lang.String)      
     */
    @Override
    public synchronized Object setProperty(String key, String value) {
        if (key == null || key.equals("")) {
            return null;
        }
        key = key.trim().toLowerCase();
        return super.setProperty(key, value);
    }

    /**
     * Reads a property list (key and element pairs) from the input
     * byte stream. The input stream is in a simple line-oriented
     * format.
     * @param inStream The input stream.
     * @throws java.io.IOException  If an error occurred when reading from the
     *                 input stream.
     */
    @Override
    public synchronized void load(InputStream inStream) throws IOException {
        super.load(inStream);
        Enumeration e = this.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            if (key == null || key.equals("")) {
                continue;
            }
            // Fix for bug id 2128962 (sourceforge.net)
            String value = super.getProperty(key);
            this.remove(key);
            this.setProperty(key.trim().toLowerCase(), value);
        }
    }
}
