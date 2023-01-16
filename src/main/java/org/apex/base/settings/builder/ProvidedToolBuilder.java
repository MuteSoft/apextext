/*
 * ProvidedToolBuilder.java
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
import org.apex.base.core.EditorBase;
import org.apex.base.data.ProvidedTool;
import org.apex.base.logging.Logger;
import org.apex.base.data.Configuration;
import org.apex.base.util.ConfigurationUtility;
import org.apex.base.settings.ProvidedToolConfiguration;
import org.apex.base.util.StringUtil;
import java.util.Properties;
import java.util.Vector;
import org.apex.base.data.MenuNode;

/**
 * Builds editor provided tool configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ProvidedToolBuilder extends ConfigurationBuilderSupport {

    /**
     * The default provided tool definition.
     * Instead of maintaining internal configuration file, this definition is used for all tools.
     */
    private static final String DEFAULT_DEFINITION = ",,false,true";

    /**
     *Creates a new instance of {@code ProvidedToolBuilder}.
     */
    public ProvidedToolBuilder() {
        super();
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading provided tools configuration.");
        ProvidedToolConfiguration providedToolConfig =
                new ProvidedToolConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(providedToolConfig);
        }
        providedToolConfig.setProvidedTools(getProvidedTools(properties));
        return providedToolConfig;
    }

    /**
     * Returns a list of editor provided tools.
     * @param properties A table of key value pairs.
     * @return A list of editor provided tools.
     */
    private Vector<ProvidedTool> getProvidedTools(Properties properties) {
        /*
         * We can't simply get the list of provided tools like this. If this list is manipulated, then
         * it would not match with the original list of tools.
         */
        Vector<String> providedToolIds = EditorBase.getContext().
                getCoreMenus().
                getProvidedToolIds();
        Vector<ProvidedTool> providedTools = new Vector<ProvidedTool>(providedToolIds.
                size());
        for (String toolId : providedToolIds) {
            // Get definition of the tool and add
            ProvidedTool tool = getProvidedTool(toolId, properties);
            if (tool != null) {
                providedTools.add(tool);
            }
        }
        return providedTools;
    }

    /**
     * Create a provided tool object from configuration properties.
     * @param toolId The tool id.
     * @param properties A table of key value pairs.
     * @return The provided tool.
     */
    private ProvidedTool getProvidedTool(String toolId, Properties properties) {
        String toolDef = properties.getProperty(toolId + DEFINITION);
        if (StringUtil.isNullOrEmpty(toolId) || toolId.equalsIgnoreCase(
                MenuNode.SEPARATOR_ID)) {
            return null;
        }
        if (StringUtil.isNullOrEmpty(toolDef)) {
            toolDef = DEFAULT_DEFINITION;
        }
        String toolName = toolId;
        MenuNode toolNode = EditorBase.getContext().getCoreMenus().getMenus().
                findChildMenuById(
                toolId);
        if (toolNode != null && !StringUtil.isNullOrEmpty(toolNode.getName())) {
            toolName = toolNode.getName();
        }
        ProvidedTool tool = new ProvidedTool(toolId, toolName);
        tool.setDescription(
                ((MenuNode) toolNode.getParent()).getName() + " >> " + toolName);
        //tool.setDescription(toolNode.getDescription());
        Vector<String> toolProperties =
                ConfigurationUtility.getListFromString(toolDef, INTRA_SEPARATOR);
        for (int iCount = 0; iCount < toolProperties.size(); iCount++) {
            if (StringUtil.isNullOrEmpty(toolProperties.get(iCount))) {
                continue;
            }
            try {
                switch (iCount) {
                    case 0:
                        tool.setOptions(toolProperties.get(iCount));
                        break;
                    case 1:
                        tool.setParams(toolProperties.get(iCount));
                        break;
                    case 2:
                        tool.setRuntimeParamRequired(
                                Boolean.parseBoolean(toolProperties.get(iCount)));
                        break;
                    case 3:
                        tool.setVisible(Boolean.parseBoolean(toolProperties.get(
                                iCount)));
                        break;
                    default:
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException aob) {
                Logger.logWarning("Error while parsing provided tool node: " +
                        tool.getId(),
                        aob);
            } catch (NullPointerException npe) {
                Logger.logWarning("Error while parsing provided tool node: " +
                        tool.getId(),
                        npe);
            } catch (Exception ex) {
                Logger.logWarning("Error while parsing provided tool node: " +
                        tool.getId(),
                        ex);
            }
        }
        return tool;
    }
}