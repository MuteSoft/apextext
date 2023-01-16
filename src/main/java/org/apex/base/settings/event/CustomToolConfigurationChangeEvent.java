/*
 * CustomToolConfigurationChangeEvent.java
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
package org.apex.base.settings.event;

import org.apex.base.settings.CustomToolConfiguration;

/**
 * Custom tool configuration change event, generated when a definition of custom tool is
 * changed programmatically or by user from preferences.
 * <p>
 * The event is passed to all registered listeners.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CustomToolConfigurationChangeEvent extends ConfigurationChangeEvent {

    /**
     * Constructs a custom tool configuration change event with given event source.
     * @param customToolConfig The event source.
     */
    public CustomToolConfigurationChangeEvent(CustomToolConfiguration customToolConfig) {
        super(customToolConfig);
    }
}
