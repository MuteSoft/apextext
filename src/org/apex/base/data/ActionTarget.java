/* 
 * ActionTarget.java
 * Created on 13 Jan, 2008,10:25:52 PM
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
 * Action targets, used to determine the target of a menu item in the application.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public enum ActionTarget {

    /**
     * Target is a menu.
     * @see org.apex.base.menu.MenuTarget
     */
    MENU,
    /**
     * Target is a custom action.
     * @see org.apex.base.action.CustomAction
     */
    CUSTOM_ACTION,
    /**
     * Target is a built in action.
     * @see org.apex.base.action.BuiltInAction
     */
    BUILT_IN_ACTION;

    /**
     * A mapping between type indicator and an action target.
     * <p>
     * The mapping is:
     * <ul>
     *  <li>BUILT_IN_ACTION : 'd' or 'D'
     *  <li>MENU: 'm' or 'M'
     *  <li>CUSTOM_ACTION: 'c' or 'C'
     * </ul>
     * @param type Type indicator character.
     * @return Action target.
     */
    public static ActionTarget getMappedTarget(char type) {
        switch (type) {
            case 'd':
            case 'D':
                return BUILT_IN_ACTION;
            case 'm':
            case 'M':
                return MENU;
            case 'c':
            case 'C':
                return CUSTOM_ACTION;
            default:
                return MENU;
        }
    }
}
