/*
 * ActionManager.java
 * Created on February 19, 2007, 8:10 PM
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

import org.apex.base.action.Action;
import org.apex.base.action.BuiltInAction;
import org.apex.base.action.DefaultAction;
import org.apex.base.data.ActionTarget;
import org.apex.base.data.MenuNode;
import org.apex.base.logging.Logger;
import org.apex.base.settings.MenuConfiguration;
import org.apex.base.util.InstanceCreator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Creates editor actions and manages them.
 * <p>
 * An action is created and kept in memory when it is first referred by application. It
 * keeps all actions in a {@code HashMap} and provides API to get and add actions.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class ActionManager {

    /**
     * A table where actions are stored.
     */
    private static HashMap<String, Action> actions =
            new HashMap<String, Action>();

    /**
     * Creates a new instance of ActionManager.
     */
    private ActionManager() {
    }

    /**
     * Register an action to {@code ActionManager}.
     * @param id Action Id.
     * @param action Action to be added.
     */
    public static void addAction(String id, Action action) {
        if (!actions.containsKey(id)) {
            actions.put(id, action);
        }
    }

    /**
     * Search an action by Id and returns the same if found..
     * @param id Action Id.
     * @return An {@code Action} if found; otherwise returns {@code null}.
     */
    public static Action getActionById(String id) {
        // Return already loaded action.
        if (actions.containsKey(id)) {
            return actions.get(id);
        }
        // Load the default action.
        return null;
    }

    /**
     * Selects or unselects an action. In case provided action id
     * is invalid or does not exist this method does nothing.
     * @param actionId An action id.
     * @param select A boolean that indicates whether to select or unselect a menu.
     * @return {@code true} if the given action is selected or unselected successfully.
     */
    public static boolean setActionSelected(String actionId, boolean select) {
        Action action = getActionById(actionId);
        if (action == null) {
            return false;
        }
        action.setSelected(select);
        return true;
    }

    /**
     * Returns whether or not given action is selected.
     * @param actionId An action id.
     * @return {@code true} if the action is selected otherwise returns {@code false}.
     */
    public static boolean isActionSelected(String actionId) {
        Action action = getActionById(actionId);
        return action == null
                ? false
                : action.isSelected();
    }

    /**
     * Selects or unselects an action. In case provided action id
     * is invalid or does not exist this method does nothing.
     * @param actionId An action id.
     * @param enabled A boolean that indicates whether or not the given action to be enabled.
     * @return {@code true} if the given action is selected or unselected successfully.
     */
    public static boolean setActionEnabled(String actionId, boolean enabled) {
        Action action = getActionById(actionId);
        if (action == null) {
            return false;
        }
        action.setEnabled(enabled);
        return true;
    }

    /**
     * Returns whether or not given action is enabled. In case provided action id
     * is invalid or does not exist, this method returns {@code false}.
     * @param actionId An action id.
     * @return {@code true} if the action is selected otherwise returns {@code false}.
     */
    public static boolean isActionEnabled(String actionId) {
        Action action = getActionById(actionId);
        return action == null
                ? false
                : action.isEnabled();
    }

    /**
     * Creates an Action based on given menu node.
     * @param id Unique action Id .
     * @param actionClass Fully qualified action class name.
     * @param node Input menu node.
     * @return An {@code Action} if created successfully; otherwise returns {@code null}.
     * @see MenuNode
     */
    private static Action createAction2(String id, String actionClass,
            MenuNode node) {
        try {
            Class clazz = InstanceCreator.loadClass(actionClass);
            if (clazz == null) {
                return null;
            }
            @SuppressWarnings("unchecked")
            Constructor cnstrctr = clazz.getConstructor(MenuNode.class);
            Action obj = (Action) cnstrctr.newInstance(node);
            actions.put(id, obj);
            return obj;
        } catch (NoSuchMethodException ex) {
            return null;
        } catch (SecurityException ex) {
            return null;
        } catch (InstantiationException ex) {
            return null;
        } catch (IllegalAccessException ex) {
            return null;
        } catch (IllegalArgumentException ex) {
            return null;
        } catch (InvocationTargetException ex) {
            return null;
        } catch (ClassCastException cce) {
            return null;
        }
    }

    /**
     * Creates all editor actions.
     * Based on the menu tree structure and target type of each menu
     * actions are created recursively.
     * <p>
     * <strong>Warning:</strong> This method should be called only once while
     * starting the editor.
     */
    public static void createActions() {
        Logger.logInfo("Creating default actions.");
        // Add actions here.
        MenuConfiguration menuConfig = EditorBase.getContext().getConfiguration().
                getMenuConfig();
        MenuNode node = menuConfig.getAllAvailableMenus();
        addActionsRecursively(node);
    }

    /**
     * Removes all editor actions.
     */
    public static void removeActions() {
        actions.clear();
    }

    /**
     * Based on the given menu tree structure and target type of each menu
     * actions are created and registered recursively.
     * @param node The menu node.
     * @return Registration status.
     */
    private static boolean addActionsRecursively(MenuNode node) {
        Enumeration menuBundle = node.children();
        while (menuBundle.hasMoreElements()) {
            MenuNode menu =
                    (MenuNode) menuBundle.nextElement();
            if (menu.getType().isActionRequired()) {
                if (menu.getTargetType().equals(ActionTarget.BUILT_IN_ACTION)) {
                    actions.put(menu.getId(), new BuiltInAction(menu));
                } else if (menu.getTargetType().equals(ActionTarget.CUSTOM_ACTION)) {
                    actions.put(menu.getId(), createAction2(menu.getId(),
                            menu.getTarget(), menu));
                } else {
                    actions.put(menu.getId(), new DefaultAction(menu));
                }
            }
            if (menu.getChildCount() > 0) {
                if (addActionsRecursively(menu)) {
                    return true;
                }
            }
        }
        return false;
    }
}
