/*
 * MenuConfigurationUpdater.java
 * Created on 27 Oct, 2007, 8:31:18 PM 
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
import org.apex.base.settings.MenuConfiguration;
import java.util.Properties;

/**
 * Stores menu configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MenuConfigurationUpdater extends ConfigurationUpdateSupport {

    /**
     * Menu configuration.
     */
    private MenuConfiguration menuConfig;
    /**
     * Cloned menu configuration.
     */
    private MenuConfiguration clonedMenuConfig;

    /**
     * Creates a new instance of {@code MenuConfigurationUpdater} using specified
     * menu configuration and cloned menu configuration.
     * @param menuConfig Menu configuration.
     * @param clonedMenuConfig Cloned menu configuration.
     */
    public MenuConfigurationUpdater(MenuConfiguration menuConfig,
                                    MenuConfiguration clonedMenuConfig) {
        this.menuConfig = menuConfig;
        this.clonedMenuConfig = clonedMenuConfig;
    }

    @Override
    public void update(Properties properties) {
        // @TODO provide suitable implementation to update custom tools and menu bindings.
        // Core menus must be excluded.
        if (!this.menuConfig.getCustomTools().equals(this.clonedMenuConfig.
                getCustomTools())) {
            CustomToolConfigurationUpdater customToolUpdater =
                    new CustomToolConfigurationUpdater(
                    menuConfig.getCustomTools(),
                    clonedMenuConfig.getCustomTools());
            customToolUpdater.update((Properties) null);
        }

        if (!this.menuConfig.getMenuKeyBindings().equals(this.clonedMenuConfig.
                getMenuKeyBindings())) {
            MenuKeyBindingUpdater menuKeyBindingUpdater =
                    new MenuKeyBindingUpdater(menuConfig.getMenuKeyBindings(),
                    clonedMenuConfig.getMenuKeyBindings());
            menuKeyBindingUpdater.update((Properties) null);
        }

        if (!this.menuConfig.getProvidedTools().equals(this.clonedMenuConfig.
                getProvidedTools())) {
            ProvidedToolUpdater providedToolsUpdater =
                    new ProvidedToolUpdater(menuConfig.getProvidedTools(),
                    clonedMenuConfig.getProvidedTools());
            providedToolsUpdater.update((Properties) null);
        }
    }
}
