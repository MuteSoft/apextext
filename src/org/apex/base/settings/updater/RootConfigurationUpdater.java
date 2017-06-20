/*
 * RootConfigurationUpdater.java
 * Created on 27 Oct, 2007, 8:29:18 PM 
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
package org.apex.base.settings.updater;

import org.apex.base.data.ConfigurationUpdateSupport;
import org.apex.base.settings.EditorConfiguration;
import java.util.Properties;

/**
 * The root user preferences updater of application. Stores all changeable
 * configurations to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class RootConfigurationUpdater extends ConfigurationUpdateSupport {

    /**
     * The root configuration object of application.
     */
    private EditorConfiguration config;
    /**
     * The cloned root configuration object of application.
     */
    private EditorConfiguration clonedConfig;

    /**
     * Constructs a new instance of {@code RootConfigurationUpdater} 
     * with given root configuration and cloned root configuration.
     * @param config The root configuration.
     * @param clonedConfig The cloned root configuration.
     */
    public RootConfigurationUpdater(EditorConfiguration config,
            EditorConfiguration clonedConfig) {
        this.config = config;
        this.clonedConfig = clonedConfig;
    }

    @Override
    public void update(Properties properties) {
        // Update general section
        GeneralConfigurationUpdater generalConfigUpdater =
                new GeneralConfigurationUpdater(config.getGeneralConfig(),
                clonedConfig.getGeneralConfig());
        generalConfigUpdater.update((Properties) null);
        // Update style section
        StyleConfigurationUpdater styleConfigUpdater =
                new StyleConfigurationUpdater(config.getStyleConfig(),
                clonedConfig.getStyleConfig());
        styleConfigUpdater.update((Properties) null);
        // Update menu section
        MenuConfigurationUpdater menuConfigUpdater =
                new MenuConfigurationUpdater(config.getMenuConfig(),
                clonedConfig.getMenuConfig());
        menuConfigUpdater.update((Properties) null);

        if (!this.config.getTemplateConfig().equals(this.clonedConfig.getTemplateConfig())) {
            // Update document template section
            TemplateConfigurationUpdater templateConfigUpdater =
                    new TemplateConfigurationUpdater(config.getTemplateConfig(),
                    clonedConfig.getTemplateConfig());
            templateConfigUpdater.update((Properties) null);
        }
    }
}
