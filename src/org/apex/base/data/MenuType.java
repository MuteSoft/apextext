/* 
 * MenuType.java
 * Created on Dec 10, 2007,1:59:03 AM 
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
package org.apex.base.data;

/**
 * Menu types, used to determine the type of a menu item while creating
 * editor main menu-bar and various popup menus.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public enum MenuType {

    /**
     * Represents a menu.
     */
    MENU {

        public boolean isActionRequired() {
            return false;
        }
    },
    /**
     * Represents a menu item.
     */
    MENUITEM {

        public boolean isActionRequired() {
            return true;
        }
    },
    /**
     * Represents a provided tool menu item.
     */
    PROVIDED_TOOL {

        public boolean isActionRequired() {
            return true;
        }
    },
    /**
     * Represents a custom tool menu item.
     */
    CUSTOM_TOOL {

        public boolean isActionRequired() {
            return true;
        }
    },
    /**
     * Represents a checkbox menu item.
     */
    CHECKBOX_MENUITEM {

        public boolean isActionRequired() {
            return true;
        }
    },
    /**
     * Represents a radio menu item.
     */
    RADIO_MENUITEM {

        public boolean isActionRequired() {
            return true;
        }
    },
    /**
     * Root of all menus, a menu-bar.
     */
    ROOT {

        public boolean isActionRequired() {
            return false;
        }
    },
    /**
     * Separator between menu items.
     */
    SEPARATOR {

        public boolean isActionRequired() {
            return false;
        }
    };

    /**
     * Returns boolean that indicates whether or not an action required
     * for a menu type.
     * @return {@code true} if action required; otherwise returns {@code false}.
     */
    public abstract boolean isActionRequired();

    /**
     * A mapping between type indicator and a menu type.
     * <p>
     * The mapping is:
     * <ul>
     *  <li>MENU: 'm' or 'M'
     *  <li>MENUITEM: 'i' or 'I'
     *  <li>PROVIDED_TOOL: 'p' or 'P'
     * <li>CUSTOM_TOOL: 'z' or 'Z'
     * <li>RADIO_MENUITEM: 'r' or 'R'
     *  <li>CHECKBOX_MENUITEM: 'c' or 'C'
     *  <li>ROOT: 'x' or 'X'
     *  <li>SEPARATOR: 's' or 'S'
     * </ul>
     * @param type Type indicator character.
     * @return The menu type.
     */
    public static MenuType getMappedType(char type) {
        switch (type) {
            case 'm':
            case 'M':
                return MenuType.MENU;
            case 'i':
            case 'I':
                return MenuType.MENUITEM;
            case 'p':
            case 'P':
                return MenuType.PROVIDED_TOOL;
            case 'z':
            case 'Z':
                return MenuType.CUSTOM_TOOL;
            case 'r':
            case 'R':
                return MenuType.RADIO_MENUITEM;
            case 'c':
            case 'C':
                return MenuType.CHECKBOX_MENUITEM;
            case 'x':
            case 'X':
                return MenuType.ROOT;
            case 's':
            case 'S':
                return MenuType.SEPARATOR;
            default:
                return MenuType.MENUITEM;
        }
    }
}
