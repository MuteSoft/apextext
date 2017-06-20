/*
 * GeneralSectionConfigChangeEvent.java
 * Created on Oct 30, 2007, 6:11:08 PM
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

import org.apex.base.settings.GeneralSectionConfiguration;

/**
 * General section configuration change event, generated when view line number, default extension, caret
 * color etc are changed programmatically or by user from preferences.
 * <p>
 * The event is passed to all registered listeners.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GeneralSectionConfigChangeEvent extends ConfigurationChangeEvent {

    /**
     * Constructs a general section configuration change event with given event source.
     * @param genSecConfig The event source.
     */
    public GeneralSectionConfigChangeEvent(GeneralSectionConfiguration genSecConfig) {
        super(genSecConfig);
    }
}