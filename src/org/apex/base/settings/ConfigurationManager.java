/*
 * ConfigurationManager.java
 * Created on 14 July, 2007, 11:35 PM
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
package org.apex.base.settings;

import org.apex.base.data.Configuration;
import org.apex.base.logging.Logger;
import org.apex.base.settings.builder.RootConfigurationBuilder;
import org.apex.base.settings.updater.RootConfigurationUpdater;
import java.util.Properties;

/**
 * Configuration manager to deal with user preferences data.It loads user preferences
 * and updates those. The rule is: Load a type of configuration object when exactly
 * required and update what exactly user modifies.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ConfigurationManager {

    /**
     * A reference to itself.
     */
    private static ConfigurationManager configManger;

    /**
     * Creates a new instance of {@code ConfigurationManager}.
     */
    private ConfigurationManager() {
    }

    /**
     *  Returns a singleton instance of this class.
     * @return A singleton instance.
     */
    public static ConfigurationManager getInstance() {
        if (configManger == null) {
            configManger = new ConfigurationManager();
        }

        return configManger;
    }

    /**
     * Loads the root configuration object.
     * @return The root configuration object.
     */
    public Configuration loadConfiguration() {
        Logger.logInfo("Loading configuration.");
        EditorConfiguration jquickConfig =
                new EditorConfiguration();
        RootConfigurationBuilder rootBuilder =
                new RootConfigurationBuilder();
        jquickConfig =
                (EditorConfiguration) rootBuilder.build((Properties) null);
        // Dispose the main (live) configuration tree.
        // @TODO Is this really required? Then what is the meaning of loading config data
        // if it is meant to be disposed?
        jquickConfig.disposeIfCacheNotRequired();
        return jquickConfig;
    }

    /**
     * Settings data is changed by user and is saved temporarily in cloned configuration objects.
     * This method copies changed settings data to original configuration object.
     * Since Configuration objects form a tree structure, its a chain of different
     * configuration object update, happens from a single place.
     * @param liveConfig The original root configuration object.
     * @param clonedConfig The cloned root configuration object.
     */
    public void updateConfiguration(Configuration liveConfig,
            Configuration clonedConfig) {
        // Write back the updated data to files (properties or xml file)
        RootConfigurationUpdater rootUpdater =
                new RootConfigurationUpdater((EditorConfiguration) liveConfig,
                (EditorConfiguration) clonedConfig);
        rootUpdater.update((Properties) null);
    }
}
