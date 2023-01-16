/*
 * IConfigurationBuilder.java
 * Created on 8 July, 2007, 11:18 PM
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

import org.apex.base.data.Configuration;
import org.apex.base.data.ConfigurationModel;
import org.apex.base.data.ConfigurationOperator;
import java.util.Properties;
import java.util.PropertyResourceBundle;

/**
 * An interface to build configuration objects from user preferences
 * stored internally or in external (to code) files.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public interface IConfigurationBuilder extends ConfigurationOperator {

    /**
     * Builds the configuration object from configuration data model.
     * @param model Configuration data model.
     * @return The built configuration object.
     */
    Configuration build(ConfigurationModel model);

    /**
     * Builds the configuration object from given properties.
     * @param properties A table of key value pairs.
     * @return The built configuration object.
     */
    Configuration build(Properties properties);

    /**
     *  Builds the configuration object from given properties bundle.
     * @param budle The bundle of property resources.
     * @return The built configuration object.
     */
    Configuration build(PropertyResourceBundle budle);
}