/*
 * MenuManager.java
 * Created on February 25, 2007, 2:18 PM
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

import org.apex.base.menu.MenuTarget;
import org.apex.base.util.InstanceCreator;
import java.util.HashMap;

/**
 * Creates editor menu targets and manages them.
 * <p>
 * A menu target is created and kept in memory when it is first referred by application. It
 * keeps all menus in a {@code HashMap} and provides API to get and add menus.
 * <p>
 * Menu target is executed when a menu is clicked.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MenuManager {

    /**
     * The table where menu targets are kept.
     */
    private static HashMap<String, MenuTarget> menus =
            new HashMap<String, MenuTarget>();
    /**
     * A mapping between menu ids and menu implementation class names.
     */
    private static HashMap<String, String> menuMap =
            new HashMap<String, String>();

    /** 
     * Creates a new instance of {@code MenuManager}.
     */
    private MenuManager() {
    }

    /**
     * Search a menu by id and returns if found.
     * @param id The unique menu id.
     * @return A {@code MenuTarget} if found; otherwise returns {@code null}.
     */
    public static MenuTarget getMenuById(String id) {
        // Get the loaded menu
        if (menus.containsKey(id)) {
            return menus.get(id);
        }
        // Menu object not found. Load this menu.
        if (menuMap.containsKey(id)) {
            return createMenu(id);
        }
        return null;
    }

    /**
     *  Register a menu to {@code MenuManager}.
     * @param id The menu id.
     * @param clazz The menu target implementation class name.
     */
    public static void addMenu(String id, String clazz) {
        menuMap.put(id, clazz);
    }
    
    /**
     * Creates a menu target object.
     * @param id The menu id.
     * @return The menu target object.
     */
    private static MenuTarget createMenu(String id) {
        Object obj = InstanceCreator.createInstance(menuMap.get(id));
        if (obj instanceof MenuTarget) {
            menus.put(id, (MenuTarget) obj);
            return (MenuTarget) obj;
        }
        return null;
    }
}
