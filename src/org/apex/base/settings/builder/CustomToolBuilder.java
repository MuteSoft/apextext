/*
 * CustomToolBuilder.java
 * Created on December 12, 2007, 08:04 PM
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
package org.apex.base.settings.builder;

import org.apex.base.data.ConfigurationBuilderSupport;
import org.apex.base.data.CustomTool;
import org.apex.base.logging.Logger;
import org.apex.base.data.Configuration;
import org.apex.base.util.ConfigurationUtility;
import org.apex.base.settings.CustomToolConfiguration;
import org.apex.base.util.StringUtil;
import java.util.Properties;
import java.util.Vector;

/**
 * Builds custom tool configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CustomToolBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code CustomToolBuilder}.
     */
    public CustomToolBuilder() {
        super();
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading custom tools configuration.");
        CustomToolConfiguration customToolConfig = new CustomToolConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(customToolConfig);
        }
        customToolConfig.setCustomTools(getCustomTools(properties));
        return customToolConfig;
    }

    /**
     * Returns a list of custom tools.
     * @param properties A table of key value pairs.
     * @return A list of custom tools.
     */
    private Vector<CustomTool> getCustomTools(Properties properties) {
        Vector<CustomTool> customTools = new Vector<CustomTool>(5);
        String tools = properties.getProperty(TOOLS);
        for (String toolId : ConfigurationUtility.getListFromString(tools,
                INTRA_SEPARATOR)) {
            if (StringUtil.isNullOrEmpty(toolId)) {
                continue;
            }
            // Get definition of the tool and add
            CustomTool customTool = getCustomTool(toolId, properties);
            if (customTool != null) {
                customTools.add(customTool);
            }
        }
        return customTools;
    }

    /**
     * Builds and returns a custom tool for given tool Id.     
     * @param toolId A tool Id.
     * @param properties A table of key value pairs.
     * @return A custom tool. It returns {@code null} if it fails to build the custom tool.
     */
    private CustomTool getCustomTool(String toolId, Properties properties) {
        if (StringUtil.isNullOrEmpty(toolId)) {
            return null;
        }
        String toolDef = properties.getProperty(toolId + DEFINITION);
        Vector<String> toolProperties =
                ConfigurationUtility.getListFromString(toolDef, INTRA_SEPARATOR);
        String name = "";
        String executable = "";
        try {
            name = toolProperties.get(0);
            executable = toolProperties.get(1);
        } catch (ArrayIndexOutOfBoundsException aobx) {
            Logger.logError("Could not find basic definition of custom tool id: " +
                    toolId,
                    aobx);
            // Name and executable are mandatory
            return null;
        }
        CustomTool node = new CustomTool(toolId, name, executable);
        for (int iCount = 2; iCount < toolProperties.size(); iCount++) {
            if (StringUtil.isNullOrEmpty(toolProperties.get(iCount))) {
                continue;
            }
            try {
                switch (iCount) {
                    case 2:
                        node.setDescription(toolProperties.get(iCount));
                        break;
                    case 3:
                        node.setWorkingDir(toolProperties.get(iCount));
                        break;
                    case 4:
                        node.setOptions(toolProperties.get(iCount));
                        break;
                    case 5:
                        node.setParams(toolProperties.get(iCount));
                        break;
                    case 6:
                        node.setMnemonic(toolProperties.get(iCount).charAt(0));
                        break;
                    case 7:
                        node.setRuntimeParamRequired(Boolean.parseBoolean(toolProperties.get(
                                iCount)));
                        break;
                    default:
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException aob) {
                Logger.logWarning("Error while parsing custom tool node: " +
                        toolId, aob);
            } catch (NullPointerException npe) {
                Logger.logWarning("Error while parsing custom tool node: " +
                        toolId, npe);
            } catch (Exception ex) {
                Logger.logWarning("Error while parsing custom tool node: " +
                        toolId, ex);
            }
        }
        return node;
    }
}
