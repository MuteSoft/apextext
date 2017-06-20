/*
 * MenuKeyBindingBuilder.java
 * Created on 15 July, 2007, 1:48 AM
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
import org.apex.base.logging.Logger;
import org.apex.base.data.Configuration;
import org.apex.base.settings.MenuKeyBindingConfiguration;
import org.apex.base.util.StringUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

/**
 * Builds menu-key binding configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MenuKeyBindingBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code MenuKeyBindingBuilder}.
     */
    public MenuKeyBindingBuilder() {
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading menu-key bindings configuration.");
        MenuKeyBindingConfiguration keyMapConfig =
                new MenuKeyBindingConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(keyMapConfig);
        }
        keyMapConfig.setMenuKeyBindings(getMenuKeyBindings(properties));
        return keyMapConfig;
    }

    /**
     * Builds and returns menu-key bindings.
     * @param properties A table of key value pairs.
     * @return A table containing menu-key bindings.
     */
    private HashMap<String, String> getMenuKeyBindings(Properties properties) {
        HashMap<String, String> menuKeyBindings = new HashMap<String, String>();
        for (Iterator propIterator = properties.stringPropertyNames().iterator(); propIterator.
                hasNext();) {
            String propName = (String) propIterator.next();
            if (!StringUtil.isNullOrEmpty(properties.getProperty(propName))) {
                /**
                 * Expecting in format:
                 * menuid-bnd=control pressed M
                 */
                menuKeyBindings.put(propName.substring(0, propName.length() - 4),
                        properties.getProperty(propName));
            }
        }
        return menuKeyBindings;
    }
}
