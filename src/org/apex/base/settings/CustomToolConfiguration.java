/*
 * CustomToolConfiguration.java
 * Created on December 12, 2007, 7:11 PM
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

import org.apex.base.data.CustomTool;
import org.apex.base.util.StringUtil;
import java.util.Vector;

/**
 *  A configuration object for custom tools.
 * <p>
 * User can add custom tools from user preferences and these tools are displayed along with editor provided tools.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CustomToolConfiguration extends AbstractConfiguration {

    /**
     * A list of custom tools.
     */
    private Vector<CustomTool> customTools = new Vector<CustomTool>(5);

    /**
     *Creates a new instance of {@code CustomToolConfiguration}.
     */
    public CustomToolConfiguration() {
        super();
    }

    /**
     * Returns a list of available custom tools.
     * @return A list of available custom tools.
     * @see #setCustomTools(java.util.Vector) 
     */
    public Vector<CustomTool> getCustomTools() {
        if (this.customTools == null) {
            this.customTools = new Vector<CustomTool>();
        }
        return this.customTools;
    }

    /**
     * Finds a custom tool by tool id in a list of available custom tools.
     * @param id Tool Id.
     * @return A custom tool. It returns {@code null} if tool id is not valid or custom tool
     *               is not found.
     */
    public CustomTool getCustomToolById(String id) {
        if (StringUtil.isNullOrEmpty(id)) {
            return null;
        }
        Vector<CustomTool> tools = getCustomTools();
        for (CustomTool tool : tools) {
            if (id.equals(tool.getId())) {
                return tool;
            }
        }
        return null;
    }

    /**
     * Sets a list of custom tools.
     * @param customTools A list of custom tools.
     * @see #getCustomTools()     
     */
    public void setCustomTools(Vector<CustomTool> customTools) {
        this.customTools = customTools;
    }

    public void updateFromClone(Object clonedObject) {
        CustomToolConfiguration clonedConfig =
                (CustomToolConfiguration) clonedObject;
        this.getCustomTools().clear();
        this.getCustomTools().addAll(clonedConfig.getCustomTools());
        fireCustomToolConfigurationChanged(null);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        CustomToolConfiguration clonedConfig = null;
        clonedConfig =
                (CustomToolConfiguration) super.clone();
        // It does a shallow copy. Keys and values themselves are not cloned.
        @SuppressWarnings("unchecked")
        Vector<CustomTool> clonedCustomTools =
                (Vector<CustomTool>) (this.customTools).clone();
        strictCopyTools(clonedCustomTools);
        clonedConfig.setCustomTools(clonedCustomTools);
        return clonedConfig;
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof CustomToolConfiguration) {
            CustomToolConfiguration clonedConfig =
                    (CustomToolConfiguration) clonedObject;
            value = !isCustomToolsChanged(clonedConfig.getCustomTools());
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash +
                (this.customTools != null
                ? this.customTools.hashCode()
                : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Custom Tools: " + customTools;
    }

    /**
     * Determines whether or not custom tools are changed. It compares list of custom tools
     * with given list of custom tools.
     * @param newCustomTools A list of custom tools.
     * @return {@code true} if custom tools are changed; otherwise returns {@code false}.
     */
    private boolean isCustomToolsChanged(Vector<CustomTool> newCustomTools) {
        return isCustomToolsChanged(this.customTools, newCustomTools);
    }

    /**
     * Copy custom tools to a specified list. Before copying, it clears the given list.
     * @param clonedCustomTools A list of custom tools.
     */
    private void strictCopyTools(Vector<CustomTool> clonedCustomTools) {
        // Clear the given cloned custom tools container
        clonedCustomTools.clear();
        for (CustomTool customTool : this.customTools) {
            clonedCustomTools.add((CustomTool) customTool.clone());
        }
    }

    public boolean isConfigurable() {
        return true;
    }

    public String getConfigFile() {
        return "CustomTool";
    }

    public boolean isCacheRequired() {
        return false;
    }

    public boolean disposeIfCacheNotRequired() {
        if (this.customTools != null) {
            for (CustomTool tool : this.customTools) {
                tool.dispose();
            }
            this.customTools.clear();
            this.customTools = null;
        }
        return true;
    }

    public boolean isLeaf() {
        return true;
    }

    public void remove() {
        disposeIfCacheNotRequired();
    }
}
