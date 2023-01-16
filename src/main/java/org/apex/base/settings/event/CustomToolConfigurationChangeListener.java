/*
 * CustomToolConfigurationChangeListener.java
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

import java.util.EventListener;

/**
 * The listener interface for receiving custom tool configuration change events. 
 * The class that is interested in processing a custom tool configuration change event
 * implements this interface. When the change event
 * occurs, that object's <code>propertyValueChanged</code> method is
 * invoked.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 * @see CustomToolConfigurationChangeEvent
 */
public interface CustomToolConfigurationChangeListener extends EventListener {

    /**
     * Invoked when a custom tool configuration change event occurs.
     * @param event The change event object.
     */
    void propertyValueChanged(CustomToolConfigurationChangeEvent event);
}
