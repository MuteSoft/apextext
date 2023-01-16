/*
 * RootConfigurationBuilder.java
 * Created on 15 July, 2007, 1:49 AM
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
package org.apex.base.settings.builder;

import org.apex.base.data.ConfigurationBuilderSupport;
import org.apex.base.data.Configuration;
import org.apex.base.settings.GeneralConfiguration;
import org.apex.base.settings.EditorConfiguration;
import org.apex.base.settings.MenuConfiguration;
import org.apex.base.settings.StyleConfiguration;
import java.util.Properties;
import org.apex.base.settings.TemplateConfiguration;

/**
 * The root configuration builder of application. Builds root
 * configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class RootConfigurationBuilder extends ConfigurationBuilderSupport {

    /**
     * Builds the root configuration.
     * <p>
     * This method builds the entire configuration object tree.
     * @param properties A table of key value pairs.
     * @return The built configuration object.
     */
    @Override
    public Configuration build(Properties properties) {
        EditorConfiguration jquickConfig =
                new EditorConfiguration();

        // Build general section
        GeneralConfigurationBuilder generalConfigBuilder =
                new GeneralConfigurationBuilder();
        GeneralConfiguration generalConfig =
                (GeneralConfiguration) generalConfigBuilder.build(
                (Properties) null);
        jquickConfig.setGeneralConfig(generalConfig);

        // Build Style section
        StyleConfigurationBuilder styleConfigBuilder =
                new StyleConfigurationBuilder();
        StyleConfiguration styleConfig =
                (StyleConfiguration) styleConfigBuilder.build((Properties) null);
        jquickConfig.setStyleConfig(styleConfig);

        // Build Menu section
        MenuConfigurationBuilder menuConfigBuilder =
                new MenuConfigurationBuilder();
        MenuConfiguration menuConfig =
                (MenuConfiguration) menuConfigBuilder.build((Properties) null);
        jquickConfig.setMenuConfig(menuConfig);

        // Build document template section
        TemplateConfigurationBuilder templateConfigBuilder =
                new TemplateConfigurationBuilder();
        TemplateConfiguration templateConfig =
                (TemplateConfiguration) templateConfigBuilder.build(
                (Properties) null);
        jquickConfig.setTemplateConfig(templateConfig);

        return jquickConfig;
    }
}
