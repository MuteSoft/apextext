/* 
 * ConfigurationOperatorSupport.java
 * Created on Dec 17, 2007,11:55:56 PM
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
package org.apex.base.data;

import org.apex.base.common.ConfigurationProperties;
import org.apex.base.core.EditorBase;
import org.apex.base.logging.Logger;
import org.apex.base.util.EditorUtil;
import org.apex.base.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * A base class to operate with configuration data.
 * <p>
 * All configuration builders and user preference updaters must
 * inherit from this class.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ConfigurationOperatorSupport implements ConfigurationOperator {

    public String getConfigurationHomeDir() {
        return EditorUtil.getApexSettingsHomeDir();
    }

    public ConfigurationFileType getConfigFileType() {
        return ConfigurationFileType.PROPERTIES;
    }

    public String getConfigFileExtension() {
        return getConfigFileType().getExtension();
    }

    /**
     * Based on configuration home directory, configuration file name and 
     * configuration file extension determines the external configuration file
     * and returns it.
     * @param fileName The file name.
     * @return The external configuration file.
     * @see #getConfigurationHomeDir() 
     * @see #getConfigFileExtension() 
     * @see FileUtil#createFileAndMissingDirs(java.lang.String, java.lang.String)
     */
    private File getExternalConfigFile(String fileName) {
        try {
            if (fileName == null) {
                return null;
            }
            File file = null;
            file = FileUtil.createFileAndMissingDirs(
                    getConfigurationHomeDir(),
                    fileName + DOT + getConfigFileExtension());
            return file;
        } catch (IOException ex) {
            Logger.logError("Could not find external configuration file: "
                    + fileName, ex);
            return null;
        }
    }

    /**
     * Creates and retrurns an {@code InputStream} to specified external configuration file.
     * @param fileName The external configuration file name.
     * @return An input stream. It returns {@code null} if resource cannot be located or loaded.
     */
    public InputStream getExternalConfigFileInputStream(String fileName) {
        FileInputStream stream = null;
        try {
            File file = getExternalConfigFile(fileName);
            stream = file == null
                    ? null
                    : new FileInputStream(file);
            return stream;
        } catch (FileNotFoundException ex) {
            Logger.logError(
                    "Could not build external configuration file input stream: "
                    + fileName, ex);
            return null;
        }
    }

    /**
     * Creates and retrurns an {@code OutputStream} to specified external configuration file.
     * @param fileName The external configuration file name.
     * @return An output stream. It returns {@code null} if resource cannot be located or loaded.
     */
    public OutputStream getExternalConfigFileOutputStream(String fileName) {
        FileOutputStream stream = null;
        try {
            File file = getExternalConfigFile(fileName);
            stream = file == null
                    ? null
                    : new FileOutputStream(file);
            return stream;
        } catch (FileNotFoundException ex) {
            Logger.logError(
                    "Could not build external configuration file output stream: "
                    + fileName, ex);
            return null;
        }
    }

    /**
     * Creates and retrurns an {@code InputStream} to specified internal (to application code) configuration file.
     * @param fileName The internal configuration file name.
     * @param clazz The refernce class for loading resource.
     * @return An input stream. It returns {@code null} if resource cannot be located or loaded.
     */
    public InputStream getInternalConfigFileInputStream(String fileName,
            Class clazz) {
        // Decide file name by locale
        String configFile = "resources/" + fileName + "_" + EditorBase.
                getContext().
                getEditorProperties().getLocale().toString() + DOT
                + getConfigFileExtension();
        InputStream inStream = clazz.getResourceAsStream(configFile);
        if (inStream == null) {
            configFile = "resources/" + fileName + DOT
                    + getConfigFileExtension();
            inStream = clazz.getResourceAsStream(
                    configFile);
        }
        return inStream;
    }

    /**
     * From given {@code inputStream} loads properties and returns it.
     * <p>
     * Given stream is not closed by this method.
     * @param inputStream The input stream.
     * @return A table of key-value pairs.
     * @throws IOException 
     */
    protected Properties loadConfigProperties(InputStream inputStream) throws
            IOException {
        if (inputStream == null) {
            return null;
        }
        Properties properties =
                new ConfigurationProperties();
        properties.load(inputStream);
        return properties;
    }
}
