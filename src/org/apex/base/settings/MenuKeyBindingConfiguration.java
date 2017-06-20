/*
 * MenuKeyBindingConfiguration.java
 * Created on 14 July, 2007, 1:37 PM
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * A configuration object for menu-key bindings.
 * <p>
 * For most of the editor menus, default key bindings are defined as part of core menu
 * definition. However, this configuration object deals with only custom key bindings.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MenuKeyBindingConfiguration extends AbstractConfiguration {

    /**
     * A table containing menu Ids and key bindings.
     */
    private HashMap<String, String> menuKeyBindings;
    /**
     * Contains a list of affected (key binding is changed) menus.
     */
    private Vector<String> affectedMenus = new Vector<String>();

    /**
     * Creates a new instance of {@code MenuKeyBindingConfiguration}.
     */
    public MenuKeyBindingConfiguration() {
    }

    /**
     * Clears the list of most recent list of affected menus
     * based on latest user configuration change.
     */
    public void clearAffectedMenuList() {
        if (this.affectedMenus != null) {
            this.affectedMenus.clear();
        }
    }

    @Override
    public String toString() {
        return "menuKeyBindings: " + getMenuKeyBindings();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object clone() throws CloneNotSupportedException {
        MenuKeyBindingConfiguration clonedConfig = null;
        clonedConfig = (MenuKeyBindingConfiguration) super.clone();
        HashMap<String, String> clonedMenuKeyBindings =
                (HashMap<String, String>) (this.getMenuKeyBindings()).clone();
        clonedConfig.setMenuKeyBindings(clonedMenuKeyBindings);
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        MenuKeyBindingConfiguration clonedConfig =
                (MenuKeyBindingConfiguration) clonedObject;
        updateMenuKeyBindings(clonedConfig.getMenuKeyBindings());
        fireMenuKeyBindingConfigurationChanged(null);
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof MenuKeyBindingConfiguration) {
            MenuKeyBindingConfiguration clonedConfig =
                    (MenuKeyBindingConfiguration) clonedObject;
            value = !isMenuKeyBindingsChanged(clonedConfig.getMenuKeyBindings());
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.menuKeyBindings != null
                ? this.menuKeyBindings.hashCode()
                : 0);
        return hash;
    }

    public boolean isConfigurable() {
        return true;
    }

    public String getConfigFile() {
        return "MenuKeyBinding";
    }

    public boolean isCacheRequired() {
        return false;
    }

    public boolean disposeIfCacheNotRequired() {
        if (this.affectedMenus != null) {
            this.affectedMenus.clear();
            this.affectedMenus = null;
        }
        if (this.menuKeyBindings != null) {
            this.menuKeyBindings.clear();
            this.menuKeyBindings = null;
        }
        return true;
    }

    public boolean isLeaf() {
        return true;
    }

    /**
     * Returns the table containing menu Ids and key bindings.
     * @return The table containing menu Ids and key bindings.
     * @see #setMenuKeyBindings(java.util.HashMap) 
     */
    public HashMap<String, String> getMenuKeyBindings() {
        return menuKeyBindings;
    }

    /**
     * Sets the table containing menu Ids and key bindings.
     * @param menuKeyBindings A table containing menu Ids and key bindings.
     * @see #getMenuKeyBindings()
     */
    public void setMenuKeyBindings(HashMap<String, String> menuKeyBindings) {
        this.menuKeyBindings = menuKeyBindings;
    }

    /**
     * Determines whether or not key bindings are changed.
     * It compares key bindings against given table of
     * key bindings for different menu Ids.
     * @param newMenuKeyBindings A table of menu-key bindings.
     * @return {@code true} if menu-key bindings are changed;
     *               otherwise returns {@code false}.
     */
    private boolean isMenuKeyBindingsChanged(
            HashMap<String, String> newMenuKeyBindings) {
        if (this.menuKeyBindings.size() != newMenuKeyBindings.size()) {
            return true;
        }
        // Number of bindings is same.
        Iterator keyBindingsIterator = newMenuKeyBindings.keySet().iterator();
        while (keyBindingsIterator.hasNext()) {
            // Get menu id.
            String menuId = (String) keyBindingsIterator.next();
            String prevBinding = this.menuKeyBindings.get(menuId);
            String newBinding = newMenuKeyBindings.get(menuId);
            if (isChanged(prevBinding, newBinding)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Copy menu-key bindings from a specified table.
     * @param newMenuKeyBindings A table of menu-key bindings. 
     */
    private void updateMenuKeyBindings(
            HashMap<String, String> newMenuKeyBindings) {
        // Clear the affected menu list
        clearAffectedMenuList();
        // Since any number of new bindings might have been added by user, so consider the new
        // menu key bindings as base and update the old one. Update all pairs.
        Iterator menuKeyBindingsIterator =
                newMenuKeyBindings.keySet().iterator();
        while (menuKeyBindingsIterator.hasNext()) {
            // Get abbreviation.
            String menuId = (String) menuKeyBindingsIterator.next();
            String newBinding = newMenuKeyBindings.get(menuId);
            if (!this.menuKeyBindings.containsKey(menuId)) {
                this.menuKeyBindings.put(menuId, newBinding);
                addAffectedMenu(menuId);
            } else {
                String prevBinding = this.menuKeyBindings.get(menuId);
                if (isChanged(prevBinding, newBinding)) {
                    this.menuKeyBindings.put(menuId, newBinding);
                    addAffectedMenu(menuId);
                }
            }
        }
    }

    public void remove() {
        disposeIfCacheNotRequired();
    }

    /**
     * Adds a menu Id to the list of affected/updated menus.
     * @param menuId A menu Id.
     */
    public void addAffectedMenu(String menuId) {
        this.affectedMenus.add(menuId);
    }

    /**
     * Returns the list of affected/updated menus.
     * @return A list of affected/updated menus.
     */
    public Vector<String> getAffectedMenuList() {
        return this.affectedMenus == null
                ? new Vector<String>()
                : this.affectedMenus;
    }
}