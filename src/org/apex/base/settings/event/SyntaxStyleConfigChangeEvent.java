/*
 * SyntaxStyleConfigChangeEvent.java
 * Created on 30 Oct, 2007, 1:22:39 AM
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

import org.apex.base.settings.SyntaxStyleConfiguration;

/**
 * Syntax style configuration change event, generated when syntax style information
 * for a document type is changed programmatically or by user from preferences.
 * <p>
 * The event is passed to all registered listeners.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SyntaxStyleConfigChangeEvent extends ConfigurationChangeEvent {

    /**
     * Constructs a syntax style configuration change event with given event source.
     * @param syntaxStyleConfig The event source.
     */
    public SyntaxStyleConfigChangeEvent(SyntaxStyleConfiguration syntaxStyleConfig) {
        super(syntaxStyleConfig);
    }
}