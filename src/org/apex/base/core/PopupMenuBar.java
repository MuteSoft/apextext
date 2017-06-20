/*
 * PopupMenuBar.java
 * Created on August 3, 2007, 4:27 PM
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

import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import org.apex.base.data.EditorContext;
import org.apex.base.data.MenuNode;
import org.apex.base.data.MenuType;

/**
 * Base class for popup menu-bars.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class PopupMenuBar extends JPopupMenu {

    /**
     * Creates a new instance of PopupMenuBar.
     */
    public PopupMenuBar() {
        createPopupMenu(getContext());
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Adds childs of a menu represented by input {@code rootNode} to the holder
     * represented by given {@code holder}.
     * @param rootNode The node of root menu.
     * @param holder The holder of menu items.
     */
    private void addMenusRecursively(MenuNode rootNode, JComponent holder) {
        MenuHierarchyCreator.getSharedInstance().addMenusRecursively(rootNode, getContext().
                getConfiguration().
                getMenuConfig(), holder);
    }

    /**
     * Adds given menu and its children to specified menu items holder.
     * @param rootNode The menu node to be added.
     * @param holder The menu items holder.
     * @return Returns the component corresponding to the menu item being added.
     */
    private JComponent addMenu(MenuNode rootNode, JComponent holder) {
        return MenuHierarchyCreator.getSharedInstance().addItem(rootNode, getContext().
                getConfiguration().
                getMenuConfig(), holder);
    }

    /**
     * Adds all menu items, menus and their children recursively to this popup menu bar.
     * @param menuIds A list of menu ids.
     */
    protected void addMenus(List<String> menuIds) {
        MenuNode menus = getContext().getConfiguration().getMenuConfig().
                getAllAvailableMenus();
        for (String menuId : menuIds) {
            MenuNode menuNode = menus.findChildMenuById(menuId);
            if (menuNode.getType().equals(MenuType.MENU)) {
                JComponent parentNode = addMenu(menuNode, this);
                addMenusRecursively(menuNode, parentNode);
            } else {
                addMenu(menuNode, this);
            }
        }
    }

    /**
     * Adds given menu and its children to this popup menu bar. In case given menu node is a menu item
     * it is added directly and in case of menu it considers the {@code skipRoot} to decide
     * whether to skip the root.
     * @param menuId The menu id represents the menu node to be added.
     * @param skipRoot A boolean indicates whether to skip the root in case its a menu.
     */
    protected void addMenu(String menuId, boolean skipRoot) {
        MenuNode menus = getContext().getConfiguration().getMenuConfig().
                getAllAvailableMenus();
        MenuNode menuNode = menus.findChildMenuById(menuId);
        if (menuNode.getType().equals(MenuType.MENU)) {
            if (skipRoot) {
                addMenusRecursively(menuNode, this);
            } else {
                JComponent parentNode = addMenu(menuNode, this);
                addMenusRecursively(menuNode, parentNode);
            }
        } else {
            addMenu(menuNode, this);
        }
    }

    /**
     * Creates a popup menu-bar. Adds menus to the menu-bar.     
     * @param context The editor context.
     */
    protected abstract void createPopupMenu(EditorContext context);
}
