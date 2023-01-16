/* 
 * CoreMenuConfiguration.java
 * Created on Dec 16, 2007,1:44:48 AM
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

import java.util.Enumeration;
import org.apex.base.data.Configuration;
import org.apex.base.data.MenuNode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/**
 * A configuration object for core menus.
 * <p>
 * Core menus are displayed in editor main menu-bar, edit area popup menu-bar, document tab popup menu-bar and 
 * editor main tool-bar.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class CoreMenuConfiguration implements Configuration {

    /**
     * Core menus.
     */
    private MenuNode menus;
    /**
     * Tool bar menus are not configurable.
     */
    private Vector<String> toolBarMenus = new Vector<String>(10);
    /**
     * Document edit area menu bar.
     */
    private Vector<String> docEditAreaMenus = new Vector<String>(10);
    /**
     * Document tab menu bar.
     */
    private Vector<String> docTabMenus = new Vector<String>(10);
    /**
     * Stores various menu groups. These groups can be referred by their id.
     * A menu group is in fact a bunch of menu id stored in a Vector.
     */
    private HashMap<String, ButtonGroup> menuGroups =
            new HashMap<String, ButtonGroup>();
    /**
     * Contains a list of provided tools.
     */
    private Vector<String> providedToolIds = new Vector<String>();

    /**
     * Creates a new instance of {@code CoreMenuConfiguration}.
     */
    public CoreMenuConfiguration() {
        super();
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object clonedObject) {
        /* @TODO Update the implementation later.
         * As of now it will work perfectly as this object is not configurable.
         */
        boolean value = true;
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.menus != null
                ? this.menus.hashCode()
                : 0);
        hash = 89 * hash + (this.toolBarMenus != null
                ? this.toolBarMenus.hashCode()
                : 0);
        hash = 89 * hash + (this.docEditAreaMenus != null
                ? this.docEditAreaMenus.hashCode()
                : 0);
        hash = 89 * hash + (this.docTabMenus != null
                ? this.docTabMenus.hashCode()
                : 0);
        hash = 89 * hash + (this.menuGroups != null
                ? this.menuGroups.hashCode()
                : 0);
        hash = 89 * hash + (this.providedToolIds != null
                ? this.providedToolIds.hashCode()
                : 0);
        return hash;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        CoreMenuConfiguration clonedConfig = null;
        clonedConfig = (CoreMenuConfiguration) super.clone();
        MenuNode clonedMenuNode =
                (MenuNode) (this.menus).clone();
        clonedConfig.setMenus(clonedMenuNode);        
        clonedConfig.setMenuGroups(this.menuGroups);
        clonedConfig.setToolBarMenus(this.toolBarMenus);
        clonedConfig.setDocEditAreaMenus(this.docEditAreaMenus);
        clonedConfig.setDocTabMenus(this.docTabMenus);
        clonedConfig.setProvidedToolIds(this.providedToolIds);
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        CoreMenuConfiguration clonedConfig =
                (CoreMenuConfiguration) clonedObject;       
        this.setMenus(clonedConfig.getMenus());
        this.setMenuGroups(clonedConfig.getMenuGroups());
        this.setToolBarMenus(clonedConfig.getToolBarMenus());
        this.setDocEditAreaMenus(clonedConfig.getDocEditAreaMenus());
        this.setDocTabMenus(clonedConfig.getDocTabMenus());
        this.setProvidedToolIds(clonedConfig.getProvidedToolIds());
    }

    public boolean isConfigurable() {
        return false;
    }

    public String getConfigFile() {
        return "menu/CoreMenu";
    }

    public boolean isCacheRequired() {
        return false;
    }

    public boolean disposeIfCacheNotRequired() {
        this.setMenus(null);
        if (this.getMenuGroups() != null) {
            this.getMenuGroups().clear();
            this.setMenuGroups(null);
        }
        if (this.getToolBarMenus() != null) {
            this.toolBarMenus.clear();
            this.setToolBarMenus(null);
        }
        if (this.getDocEditAreaMenus() != null) {
            this.docEditAreaMenus.clear();
            this.setDocEditAreaMenus(null);
        }
        if (this.getDocTabMenus() != null) {
            this.docTabMenus.clear();
            this.setDocTabMenus(null);
        }
        if (this.getProvidedToolIds() != null) {
            this.providedToolIds.clear();
            this.setProvidedToolIds(null);
        }
        return true;
    }

    public boolean isLeaf() {
        return true;
    }

    @Override
    public String toString() {
        return "menus: " + getMenus() + "^toolBarMenus: " + getToolBarMenus()
                + "^docEditAreaMenus: " + getDocEditAreaMenus()
                + "^docTabMenus: " + getDocTabMenus()
                + "^menuGroups: " + getMenuGroups()
                + "^providedTools" + getProvidedToolIds();
    }

    /**
     * Returns core menus.
     * @return Core menus.
     * @see #setMenus(org.apex.base.data.MenuNode)
     * 
     */
    public MenuNode getMenus() {
        return menus;
    }

    /**
     * Sets core menus.
     * @param menus Root menu node.
     * @see #getMenus() 
     */
    public void setMenus(MenuNode menus) {
        this.menus = menus;
    }

    /**
     * Returns a list of menus to be shown in editor tool-bar.
     * @return A list of menus to be shown in editor tool-bar.
     * @see #setToolBarMenus(java.util.Vector) 
     */
    public Vector<String> getToolBarMenus() {
        return toolBarMenus;
    }

    /**
     * Sets a list of menus to be shown in editor tool-bar.
     * @param toolBarMenus A list of menus to be shown in editor tool-bar.
     * @see #getToolBarMenus() 
     */
    public void setToolBarMenus(Vector<String> toolBarMenus) {
        this.toolBarMenus = toolBarMenus;
    }

    /**
     * Returns a mapping between menu ids and button groups. This is typically N:1 mapping.
     * @return A mapping between menu ids and button groups.
     * @see #setMenuGroups(java.util.HashMap) 
     */
    public HashMap<String, ButtonGroup> getMenuGroups() {
        return menuGroups;
    }

    /**
     * Clears button groups.
     */
    public void clearMenuGroups() {
        for (Iterator groupIt = getMenuGroups().keySet().iterator(); groupIt.
                hasNext();) {
            ButtonGroup buttonGroup = getMenuGroups().get(
                    (String) groupIt.next());
            Enumeration buttons = buttonGroup.getElements();
            while (buttons.hasMoreElements()) {
                AbstractButton button = (AbstractButton) buttons.nextElement();
                buttonGroup.remove(button);
            }
        }
    }

    /**
     * Sets a mapping between menu ids and button groups.
     * @param menuGroups A mapping between menu ids and button groups.
     * @see #getMenuGroups() 
     */
    public void setMenuGroups(HashMap<String, ButtonGroup> menuGroups) {
        this.menuGroups = menuGroups;
    }

    public void remove() {
        disposeIfCacheNotRequired();
    }

    /**
     * Returns a list of menus to be shown in edit area popup menu-bar.
     * @return A list of menus to be shown in edit area popup menu-bar.
     * @see #setDocEditAreaMenus(java.util.Vector) 
     */
    public Vector<String> getDocEditAreaMenus() {
        return docEditAreaMenus;
    }

    /**
     * Sets a list of menus to be shown in edit area popup menu-bar.
     * @param docEditAreaMenus A list of menus to be shown in edit area popup menu-bar.
     * @see #getDocEditAreaMenus() 
     */
    public void setDocEditAreaMenus(Vector<String> docEditAreaMenus) {
        this.docEditAreaMenus = docEditAreaMenus;
    }

    /**
     * Returns a list of menus to be shown in document tab popup menu-bar.
     * @return A list of menus to be shown in document tab popup menu-bar.
     * @see #setDocTabMenus(java.util.Vector) 
     */
    public Vector<String> getDocTabMenus() {
        return docTabMenus;
    }

    /**
     * Sets a list of menus to be shown in document tab popup menu-bar.
     * @param docTabMenus A list of menus to be shown in document tab popup menu-bar.
     * @see #getDocTabMenus() 
     */
    public void setDocTabMenus(Vector<String> docTabMenus) {
        this.docTabMenus = docTabMenus;
    }

    /**
     * Returns a list of tools provided with editor by default.
     * @return A list of tools provided with editor by default.
     * @see #setProvidedToolIds(java.util.Vector)
     */
    public Vector<String> getProvidedToolIds() {
        return providedToolIds;
    }

    /**
     * Sets a list of tools provided with editor by default.
     * @param providedToolIds A list of tools provided with editor by default.
     * @see #getProvidedToolIds()
     */
    public void setProvidedToolIds(Vector<String> providedToolIds) {
        this.providedToolIds = providedToolIds;
    }
}
