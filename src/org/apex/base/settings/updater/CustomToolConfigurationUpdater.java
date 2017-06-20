/*
 * CustomToolConfigurationUpdater.java
 * Created on December 12, 2007, 08:05 PM
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
package org.apex.base.settings.updater;

import org.apex.base.data.ConfigurationUpdateSupport;
import org.apex.base.data.CustomTool;
import org.apex.base.settings.CustomToolConfiguration;
import java.util.Properties;
import java.util.Vector;

/**
 * Stores custom tool configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CustomToolConfigurationUpdater extends ConfigurationUpdateSupport {

    /**
     * Custom tool configuration.
     */
    private CustomToolConfiguration customToolConfig;
    /**
     * Cloned custom tool configuration.
     */
    private CustomToolConfiguration clonedCustomToolConfig;

    /**
     * Creates a new instance of {@code CustomToolConfigurationUpdater} using specified
     * custom tool configuration and cloned custom tool configuration.
     * @param customToolConfig Custom tool configuration.
     * @param clonedCustomToolConfig Cloned custom tool configuration
     */
    public CustomToolConfigurationUpdater(
            CustomToolConfiguration customToolConfig,
            CustomToolConfiguration clonedCustomToolConfig) {
        this.customToolConfig = customToolConfig;
        this.clonedCustomToolConfig = clonedCustomToolConfig;
    }

    @Override
    public void update(Properties properties) {
        this.customToolConfig.updateFromClone(this.clonedCustomToolConfig);
        if (properties == null) {
            properties = loadConfigProperties(this.customToolConfig);
        }
        properties.clear();
        Vector<CustomTool> customTools = this.customToolConfig.getCustomTools();
        StringBuilder toolIds = new StringBuilder("");
        for (int iCount = 0; iCount < customTools.size(); iCount++) {
            CustomTool customTool = customTools.get(iCount);
            String toolId = customTool.getId();
            toolIds.append(toolId);
            properties.setProperty(toolId + DEFINITION,
                    customTool.toString());
            if (iCount < customTools.size() - 1) {
                toolIds.append(INTRA_SEPARATOR);
            }
        }
        properties.setProperty(TOOLS, toolIds.toString());
        storeConfigProperties(customToolConfig, properties);
    }
}
