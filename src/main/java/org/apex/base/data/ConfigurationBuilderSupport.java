/*
 * AbstractConfigurationBuilder.java
 * Created on 15 July, 2007, 1:29 AM
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
import java.util.Properties;
import java.util.PropertyResourceBundle;
import org.apex.base.core.CoreMenuConfiguration;
import org.apex.base.core.EditorBase;
import org.apex.base.util.FileUtil;

/**
 * An implementation of {@code IConfigurationBuilder}. All configuration builder classes
 * must use this class as base.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class ConfigurationBuilderSupport extends ConfigurationOperatorSupport
        implements IConfigurationBuilder {

    public Configuration build(Properties properties) {
        return null;
    }

    public Configuration build(PropertyResourceBundle budle) {
        return null;
    }

    public Configuration build(ConfigurationModel model) {
        return null;
    }

    /**
     * A helper method which calls appropriate build() method
     * based on configuration file type.
     * @return The built configuration object.
     */
    public final Configuration build() {
        ConfigurationFileType fileType = getConfigFileType();
        switch (fileType) {
            case PROPERTIES:
                return build((Properties) null);
            case XML:
                return build((ConfigurationModel) null);
            default:
                return build((Properties) null);
        }
    }

    /**
     * Loads configuration data. In case specified configuration object is changeable
     * by user it attempts to load configuration data from external configuration file. In case load
     * operation fails it attempts to load configuration data from internal (to code)
     * configuration file. On the other hand, if specified configuration object is not changeable
     * it attempts to load configuration data from internal (to code) configuration file.
     * @param config The configuration object.
     * @return A table of configuration data.
     */
    protected Properties loadConfigProperties(Configuration config) {
        if (config == null || config.getConfigFile() == null) {
            return null;
        }
        Properties prop = null;
        boolean isConfigurable = config.isConfigurable();
        InputStream stream = null;
        try {
            if (isConfigurable) {
                // Configurable. So attempt to load external configuration data.
                stream =
                        getExternalConfigFileInputStream(config.getConfigFile());
                prop = loadConfigProperties(stream);
                if (prop != null && !prop.isEmpty()) {
                    return prop;
                }
            }
            /**
             * Either external configuration data could not be loaded or
             * not configurable. Hence load internal configuration data.
             */
            Class referenceClass = config.getClass();
            if (config instanceof CoreMenuConfiguration) {
                referenceClass = EditorBase.getContext().getEditorProperties().getEditorBaseClass();
            }
            stream =
                    getInternalConfigFileInputStream(config.getConfigFile(), referenceClass);
            prop = loadConfigProperties(stream);

        } catch (IOException ioe) {
            Logger.logError("Could not load configuration file: " + config.getConfigFile(),
                    ioe);
        } finally {
            // Close the stream.
            FileUtil.closeIOStream(stream);
        }
        return (prop == null || prop.isEmpty())
                ? new ConfigurationProperties()
                : prop;
    }
}
