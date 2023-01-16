/*
 * ConfigurationUpdateSupport.java
 * Created on 27 Oct, 2007, 8:37:26 PM
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
import org.apex.base.logging.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import org.apex.base.util.FileUtil;

/**
 * An implementation of {@code IConfigurationUpdater}. All configuration updater classes
 * must use this class as base.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class ConfigurationUpdateSupport extends ConfigurationOperatorSupport
        implements IConfigurationUpdater {

    /**
     * The message to be written to configuration files.
     */
    private static final String CONFIG_FILE_DO_NOT_MODIFY_MESG =
            "PLEASE DO NOT MODIFY MANUALLY";

    public void update(Properties properties) {
        return;
    }

    public void update(ConfigurationModel model) {
        return;
    }

    public void update(PropertyResourceBundle budle) {
        return;
    }

    /**
     * Loads a table of key-value pairs for given configuration.
     * @param config The configuration object.
     * @return A table of key-value pairs.
     */
    protected Properties loadConfigProperties(Configuration config) {
        if (config == null || config.getConfigFile() == null) {
            return null;
        }
        Properties prop = null;
        InputStream stream = null;
        try {
            // Configurable. So attempt to load external configuration data.
            stream =
                    getExternalConfigFileInputStream(config.getConfigFile());
            prop = loadConfigProperties(stream);
        } catch (IOException ex) {
            Logger.logError("Could not load configuration file: " + config.
                    getConfigFile(), ex);
        } finally {
            // Close the stream.
            FileUtil.closeIOStream(stream);
        }
        return prop == null
                ? new ConfigurationProperties()
                : prop;
    }

    /**
     * Stores a table of key-value pairs to external (to code) file.
     * <p>
     * This file name is obtained from given {@code config}.
     * @param config The configuration object.
     * @param properties A table of key-value pairs.
     */
    protected void storeConfigProperties(Configuration config,
                                         Properties properties) {
        if (config == null || config.getConfigFile() == null) {
            return;
        }
        OutputStream stream = null;
        try {
            stream =
                    getExternalConfigFileOutputStream(config.getConfigFile());
            properties.store(stream,
                    CONFIG_FILE_DO_NOT_MODIFY_MESG);
        } catch (IOException ex) {
            Logger.logError("Could not update configuration file: " + config.
                    getConfigFile(),
                    ex);
        } finally {
            FileUtil.closeIOStream(stream);
        }
    }
}
