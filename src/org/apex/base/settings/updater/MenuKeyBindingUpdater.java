/*
 * MenuKeyBindingUpdater.java 
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
import org.apex.base.settings.MenuKeyBindingConfiguration;
import java.util.Properties;
import java.util.Vector;

/**
 * Stores menu-key binding configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MenuKeyBindingUpdater extends ConfigurationUpdateSupport {

    /**
     * Menu-key binding configuration.
     */
    private MenuKeyBindingConfiguration menuKeyBindingConfig;
    /**
     * Cloned menu-key binding configuration.
     */
    private MenuKeyBindingConfiguration clonedMenuKeyBindingConfig;

    /**
     * Creates a new instance of {@code MenuKeyBindingUpdater} using specified
     * menu-key binding configuration and cloned menu-key binding configuration.
     * @param menuKeyBindingConfig Menu-key binding configuration.
     * @param clonedMenuKeyBindingConfig Cloned menu-key binding configuration.
     */
    public MenuKeyBindingUpdater(
            MenuKeyBindingConfiguration menuKeyBindingConfig,
            MenuKeyBindingConfiguration clonedMenuKeyBindingConfig) {
        this.menuKeyBindingConfig = menuKeyBindingConfig;
        this.clonedMenuKeyBindingConfig = clonedMenuKeyBindingConfig;
    }

    @Override
    public void update(Properties properties) {
        this.menuKeyBindingConfig.updateFromClone(
                this.clonedMenuKeyBindingConfig);
        if (properties == null) {
            properties = loadConfigProperties(this.menuKeyBindingConfig);
        }
        Vector<String> changedMenuList =
                this.menuKeyBindingConfig.getAffectedMenuList();
        for (String menuId : changedMenuList) {
            if (this.menuKeyBindingConfig.getMenuKeyBindings().
                    get(menuId) == null) {
                properties.setProperty(menuId + "-bnd", "deleted");
            } else {
                properties.setProperty(menuId + "-bnd", this.menuKeyBindingConfig.getMenuKeyBindings().
                        get(menuId));
            }
        }
        storeConfigProperties(this.menuKeyBindingConfig, properties);
    }
}
