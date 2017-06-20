/*
 * ConfigurationChangeEvent.java
 * Created on 9 September, 2007, 8:50 AM
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

import java.util.EventObject;

/**
 * An event, generated when a configuration object is changed programmatically or by user
 * from preferences.
 * <p>
 * The event is passed to all registered listeners.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class ConfigurationChangeEvent extends EventObject {

    /**
     * Constructs a configuration change event with given event source.
     * @param source The event source.
     */
    public ConfigurationChangeEvent(Object source) {
        super(source);
    }

    /**
     * Returns the event source - configuration object. 
     * @return The event source.
     */
    public final Object getConfiguration() {
        return getSource();
    }
}
