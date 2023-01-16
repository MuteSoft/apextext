/*
 * ProvidedToolUpdater.java 
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
import org.apex.base.data.ProvidedTool;
import org.apex.base.settings.ProvidedToolConfiguration;
import java.util.Properties;
import java.util.Vector;

/**
 * Stores provided tool configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ProvidedToolUpdater extends ConfigurationUpdateSupport {

    /**
     * Provided tool configuration.
     */
    private ProvidedToolConfiguration providedToolConfig;
    /**
     * Cloned provided tool configuration.
     */
    private ProvidedToolConfiguration clonedProvidedToolConfig;

    /**
     * Creates a new instance of {@code ProvidedToolUpdater} using specified
     * provided tool configuration and cloned provided tool configuration.
     * @param providedToolConfig Provided tool configuration.
     * @param clonedProvidedToolConfig Cloned provided tool configuration.
     */
    public ProvidedToolUpdater(ProvidedToolConfiguration providedToolConfig,
                               ProvidedToolConfiguration clonedProvidedToolConfig) {
        this.providedToolConfig = providedToolConfig;
        this.clonedProvidedToolConfig = clonedProvidedToolConfig;
    }

    @Override
    public void update(Properties properties) {
        this.providedToolConfig.updateFromClone(this.clonedProvidedToolConfig);
        if (properties == null) {
            properties = loadConfigProperties(this.providedToolConfig);
        }
        properties.clear();
        Vector<ProvidedTool> providedTools = this.providedToolConfig.
                getProvidedTools();
        for (int iCount = 0; iCount < providedTools.size(); iCount++) {
            ProvidedTool providedTool = providedTools.get(iCount);
            String toolId = providedTool.getId();
            properties.setProperty(toolId + DEFINITION,
                    providedTool.toString());
        }
        storeConfigProperties(providedToolConfig, properties);
    }
}
