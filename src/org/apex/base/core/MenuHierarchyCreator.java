/*
 * MenuHierarchyCreator.java 
 * Created on 31 Oct, 2009, 9:05:04 PM
 *
 * Copyright (C) 2009 Mrityunjoy Saha
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

import java.util.Enumeration;
import javax.swing.JComponent;
import javax.swing.JMenu;
import org.apex.base.action.DefaultAction;
import org.apex.base.component.ApexCheckBoxMenuItem;
import org.apex.base.component.ApexMenu;
import org.apex.base.component.ApexMenuItem;
import org.apex.base.component.ApexRadioButtonMenuItem;
import org.apex.base.data.MenuNode;
import org.apex.base.data.ProvidedTool;
import org.apex.base.settings.MenuConfiguration;

/**
 * Creates the menu hierarchy of editor application.
 * @author mrityunjoy_saha
 * @version 1.2
 * @since Apex 1.2
 */
public class MenuHierarchyCreator {

    /**
     * Self instance -singleton.
     */
    private static MenuHierarchyCreator selfInstance;

    /**
     * Creates a default instance of {@code MenuHierarchyCreator}.
     */
    private MenuHierarchyCreator() {
        // Do nothing.
    }

    /**
     * Adds a menu or menu item to menu-bar or menu correspondingly.
     * @param menu The menu node.
     * @param menuConfig The menu configuration object.
     * @param holder Holder, to which the item to be added.
     * @return The newly added item.
     */
    public JComponent addItem(MenuNode menu,
            MenuConfiguration menuConfig,
            JComponent holder) {     
        JComponent child = null;
        if (menu == null) {
            return null;
        }
        switch (menu.getType()) {
            /*
             * Represents a menu.
             */
            case MENU:
                child = new ApexMenu(menu.getName(), menu.getMnemonic(), menu.
                        getDescription(),
                        holder);
                break;
            /*
             * Represents a menu item.
             */
            case MENUITEM:
                if (ActionManager.getActionById(menu.getId()) == null) {
                    ActionManager.addAction(menu.getId(),
                            new DefaultAction(menu));
                }
                child = new ApexMenuItem(ActionManager.getActionById(
                        menu.getId()),
                        holder);
                break;
            /*
             * Represents a provided tool.
             */
            case PROVIDED_TOOL:
                ProvidedTool pTool = menuConfig.getProvidedTools().
                        getProvidedToolById(menu.getId());
                if (pTool != null && pTool.isVisible()) {
                    child = new ApexMenuItem(
                            ActionManager.getActionById(menu.getId()),
                            holder);
                }
                break;

            /*
             * Represents a custom tool.
             */
            case CUSTOM_TOOL:
                if (ActionManager.getActionById(menu.getId()) == null) {
                    ActionManager.addAction(menu.getId(),
                            new DefaultAction(menu));
                }
                child = new ApexMenuItem(ActionManager.getActionById(
                        menu.getId()),
                        holder);
                break;
            /*
             * Represents a checkbox menu item.
             */
            case CHECKBOX_MENUITEM:
                child = new ApexCheckBoxMenuItem(ActionManager.getActionById(menu.
                        getId()),
                        holder);
                break;
            /*
             * Represents a radio menu item.
             */
            case RADIO_MENUITEM:
                child = new ApexRadioButtonMenuItem(ActionManager.getActionById(menu.
                        getId()),
                        false, holder, EditorBase.getContext().getCoreMenus().
                        getMenuGroups().
                        get(menu.getId()));                
                break;
            /*
             *
             */
            case ROOT:
                break;
            /*
             *
             */
            case SEPARATOR:
                if (holder instanceof ApexMenu) {
                    ((JMenu) holder).addSeparator();
                } else if (holder instanceof PopupMenuBar) {
                    ((PopupMenuBar) holder).addSeparator();
                }
                break;
        }
        // Fix for bug id 2474358 (sourceforge.net)
        if (child != null) {
            child.setToolTipText(null);
        }
        return child;
    }

    /**
     * Adds menus recursively to the main menu-bar based on
     * menu configuration.
     * @param menus The prant menu node.
     * @param menuConfig The menu configuration object.
     * @param holder The root holder, menu-bar.
     */
    public void addMenusRecursively(MenuNode menus,
            MenuConfiguration menuConfig,
            JComponent holder) {
        Enumeration menuBundle = menus.children();
        while (menuBundle.hasMoreElements()) {
            MenuNode menu =
                    (MenuNode) menuBundle.nextElement();
            JComponent childHolder = addItem(menu, menuConfig, holder);
            if (menu.getChildCount() > 0) {
                addMenusRecursively(menu, menuConfig, childHolder);
            }
        }
        return;
    }

    /**
     * Returns the singleton instance of this class.
     * @return The singleton instance of this class.
     */
    public static MenuHierarchyCreator getSharedInstance() {
        if (selfInstance == null) {
            selfInstance = new MenuHierarchyCreator();
        }
        return selfInstance;
    }
}
