/*
 * IConfigurationUpdater.java
 * Created on 27 Oct, 2007, 8:26:24 PM
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

import org.apex.base.data.ConfigurationModel;
import org.apex.base.data.ConfigurationOperator;
import java.util.Properties;
import java.util.PropertyResourceBundle;

/**
 * An interface to update user preferences to external (to code) files.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public interface IConfigurationUpdater extends ConfigurationOperator {

    /**
     * Updates configuration file with given properties table.
     * @param properties A table of key value pairs.
     */
    void update(Properties properties);

    /**
     *  Updates configuration file with given configuration model.
     * @param model The configuration data model.
     *
     */
    void update(ConfigurationModel model);

    /**
     *  Updates configuration file with given properties bundle.
     * @param budle The bundle of property resources.
     *
     */
    void update(PropertyResourceBundle budle);
}