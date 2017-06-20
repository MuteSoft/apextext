/* 
 * MenuConfigurationBuilder.java
 * Created on Dec 17, 2007,2:59:48 AM
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
import org.apex.base.settings.CustomToolConfiguration;
import org.apex.base.settings.MenuConfiguration;
import org.apex.base.settings.MenuKeyBindingConfiguration;
import java.util.Properties;

/**
 * Builds menu configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MenuConfigurationBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code MenuConfigurationBuilder}.
     */
    public MenuConfigurationBuilder() {
        super();
    }

    @Override
    public Configuration build(Properties properties) {
        MenuConfiguration menuConfig = new MenuConfiguration();

        MenuKeyBindingBuilder menuKeyBindingBuilder =
                new MenuKeyBindingBuilder();
        MenuKeyBindingConfiguration menuKeyBindingConfig =
                (MenuKeyBindingConfiguration) menuKeyBindingBuilder.build(
                (Properties) null);
        menuConfig.setMenuKeyBindings(menuKeyBindingConfig);

        CustomToolBuilder customToolsBuilder = new CustomToolBuilder();
        CustomToolConfiguration customToolsConfig =
                (CustomToolConfiguration) customToolsBuilder.build(
                (Properties) null);
        menuConfig.setCustomTools(customToolsConfig);

        return menuConfig;
    }
}
