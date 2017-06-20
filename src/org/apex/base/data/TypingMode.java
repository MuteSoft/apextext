/*
 * TypingMode.java 
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

/**
 * Typing mode in edit area of editor.
 * <p>
 * Typing mode can be either 'Insert' or 'Overrite'.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public enum TypingMode {

    /**
     * Insert mode.
     */
    INSERT {

        public String getDisplayText() {
            return "INS";
        }
    },
    /**
     * Overwrite mode.
     */
    OVERWRITE {

        public String getDisplayText() {
            return "OVR";
        }
    };

    /**
     * Returns the display text of a typing mode.
     * @return The display text of a typing mode.
     */
    public abstract String getDisplayText();
}
