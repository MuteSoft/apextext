/*
 * EditorMenuBar.java
 * Created on February 10, 2007, 8:10 AM
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
package org.apex.base.core;

import org.apex.base.component.ApexMenuBar;
import org.apex.base.data.EditorContext;
import org.apex.base.data.MenuNode;
import org.apex.base.logging.Logger;
import org.apex.base.settings.event.CustomToolConfigurationChangeEvent;
import org.apex.base.settings.event.CustomToolConfigurationChangeListener;
import org.apex.base.settings.event.DocTypesConfigChangeEvent;
import org.apex.base.settings.event.MenuKeyBindingConfigurationChangeEvent;
import org.apex.base.settings.event.MenuKeyBindingConfigurationChangeListener;
import org.apex.base.settings.event.ProvidedToolConfigChangeEvent;
import org.apex.base.settings.event.ProvidedToolConfigChangeListener;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.KeyStroke;
import org.apex.base.settings.event.DocTypesConfigChangeListener;

/**
 * The main menu-bar displayed at top of the editor.
 * <p>
 * Based on menu configuration creates the menu-bar dynamically.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 * @see MenuNode
 */
public class EditorMenuBar extends ApexMenuBar {

    /**
     * Creates a new instance of EditorMenuBar.
     */
    public EditorMenuBar() {
        loadMenuBar(EditorBase.getContext());
        // @TODO In below scenarios menu bar re-creation is unnecessary.
        // For example, when custom or provided tool configurations changed
        // update Tools menu and sub menu items only.
        // Update menu bar when custom tools changed.
        this.addCustomToolConfigChangeListener(new CustomToolConfigurationChangeListener() {

            public void propertyValueChanged(
                    CustomToolConfigurationChangeEvent event) {
                refreshMenuBar();
            }
        });
        // Update menu bar when configuration of provided tools changed.
        this.addProvidedToolConfigChangeListener(new ProvidedToolConfigChangeListener() {

            public void propertyValueChanged(ProvidedToolConfigChangeEvent event) {
                refreshMenuBar();
            }
        });
        // Update accelerator of affected menus.
        this.addMenuKeyBindingConfigChangeListener(new MenuKeyBindingConfigurationChangeListener() {

            public void propertyValueChanged(
                    MenuKeyBindingConfigurationChangeEvent event) {
                // Update acceleartor of actions
                Vector<String> changedMenuList = EditorBase.getContext().
                        getConfiguration().getMenuConfig().getMenuKeyBindings().
                        getAffectedMenuList();
                HashMap<String, String> bindings = EditorBase.getContext().
                        getConfiguration().getMenuConfig().getMenuKeyBindings().
                        getMenuKeyBindings();
                for (String menuId : changedMenuList) {
                    // Get the action with this menu id and update the same
                    if (ActionManager.getActionById(menuId) != null) {
                        ActionManager.getActionById(menuId).setAccelerator(KeyStroke.
                                getKeyStroke(bindings.get(menuId)));
                    }
                }
            }
        });
        // Update menu bar when document type configuration changed.
        this.addDocTypesConfigChangeListener(new DocTypesConfigChangeListener() {

            public void propertyValueChanged(DocTypesConfigChangeEvent event) {
                // Determine whether list of document types is changed. Ignore the event if it is only
                // document extension related change.
                refreshMenuBar();
            }
        });
    }

    /**
     * Refreshes the main menu-bar.
     * <p>
     * It clears the menu-bar and re-loads the same based on changed menu
     * configuration.
     */
    public void refreshMenuBar() {
        clearMenuBar();
        EditorBase.getContext().getCoreMenus().clearMenuGroups();
        loadMenuBar(EditorBase.getContext());
        this.validate();
    }

    /**
     * Removes all menus from the menu-bar.
     */
    private void clearMenuBar() {
        this.removeAll();
    }

    /**
     * Loads the main menu-bar based on core menu configuration.
     * @param context The editor context.
     */
    private void loadMenuBar(final EditorContext context) {
        Logger.logInfo("Loading editor menu bar.");
        MenuNode menus = context.getConfiguration().getMenuConfig().
                getAllAvailableMenus();
        MenuHierarchyCreator.getSharedInstance().addMenusRecursively(menus, context.
                getConfiguration().
                getMenuConfig(), this);
    }
}
