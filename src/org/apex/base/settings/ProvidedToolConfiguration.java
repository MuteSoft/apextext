/*
 * ProvidedToolConfiguration.java
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

import org.apex.base.data.ProvidedTool;
import org.apex.base.util.StringUtil;
import java.util.Vector;

/**
 * A configuration object for editor provided tools.
 * <p>
 * For a provided tool user can only change visibility and runtime options and parameters.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
// @TODO - provided tools are not loaded when settings data restored to default.
public class ProvidedToolConfiguration extends AbstractConfiguration {

    /**
     * A list of editor provided tools.
     */
    private Vector<ProvidedTool> providedTools = new Vector<ProvidedTool>(5);

    /**
     * Creates a new instance of {@code ProvidedToolConfiguration}.     
     */
    public ProvidedToolConfiguration() {
        super();
    }

    /**
     * Returns a list of available provided tools.
     * @return A list of available provided tools.
     * @see #setProvidedTools(java.util.Vector) 
     */
    public Vector<ProvidedTool> getProvidedTools() {
        if (this.providedTools == null) {
            this.providedTools = new Vector<ProvidedTool>();
        }
        return this.providedTools;
    }

    /**
     * Finds a provided tool by tool id in a list of available provided tools.
     * @param id A tool Id.
     * @return A provided tool. It returns {@code null} if tool id
     *               is not valid or provided tool is not found.
     */
    public ProvidedTool getProvidedToolById(String id) {
        if (StringUtil.isNullOrEmpty(id)) {
            return null;
        }
        Vector<ProvidedTool> tools = getProvidedTools();
        for (ProvidedTool tool : tools) {
            if (id.equals(tool.getId())) {
                return tool;
            }
        }
        return null;
    }

    /**
     * Sets a list of provided tools.
     * @param providedTools A list of provided tools.
     * @see #getProvidedTools() 
     */
    public void setProvidedTools(Vector<ProvidedTool> providedTools) {
        this.providedTools = providedTools;
    }

    public void updateFromClone(Object clonedObject) {
        ProvidedToolConfiguration clonedConfig =
                (ProvidedToolConfiguration) clonedObject;
        this.getProvidedTools().clear();
        this.getProvidedTools().addAll(clonedConfig.getProvidedTools());
        fireProvidedToolConfigurationChanged(null);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ProvidedToolConfiguration clonedConfig = null;
        clonedConfig =
                (ProvidedToolConfiguration) super.clone();
        // It does a shallow copy. Keys and values themselves are not cloned.
        @SuppressWarnings("unchecked")
        Vector<ProvidedTool> clonedProvidedTools =
                (Vector<ProvidedTool>) (this.providedTools).clone();
        strictCopyTools(clonedProvidedTools);
        clonedConfig.setProvidedTools(clonedProvidedTools);
        return clonedConfig;
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof ProvidedToolConfiguration) {
            ProvidedToolConfiguration clonedConfig =
                    (ProvidedToolConfiguration) clonedObject;
            value = !isProvidedToolsChanged(clonedConfig.getProvidedTools());
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash +
                (this.providedTools != null
                ? this.providedTools.hashCode()
                : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Provided Tools: " + providedTools;
    }

    /**
     * Determines whether or not provided tools are changed. It compares list of provided tools
     * with given list of provided tools.
     * @param newProvidedTools A list of provided tools.
     * @return {@code true} if provided tools are changed; otherwise returns {@code false}.
     */
    private boolean isProvidedToolsChanged(Vector<ProvidedTool> newProvidedTools) {
        return isProvidedToolsChanged(this.providedTools, newProvidedTools);
    }

    /**
     * Copy provided tools to a specified list. Before copying, it clears the given list.
     * @param clonedProvidedTools A list of provided tools.
     */
    private void strictCopyTools(Vector<ProvidedTool> clonedProvidedTools) {
        // Clear the given cloned custom tools container
        clonedProvidedTools.clear();
        for (ProvidedTool providedTool : this.providedTools) {
            clonedProvidedTools.add((ProvidedTool) providedTool.clone());
        }
    }

    public boolean isConfigurable() {
        return true;
    }

    public String getConfigFile() {
        return "ProvidedTool";
    }

    public boolean isCacheRequired() {
        return false;
    }

    public boolean disposeIfCacheNotRequired() {
        if (this.getProvidedTools() != null) {
            for (ProvidedTool tool : getProvidedTools()) {
                tool.dispose();
            }
            this.providedTools.clear();
            this.setProvidedTools(null);
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