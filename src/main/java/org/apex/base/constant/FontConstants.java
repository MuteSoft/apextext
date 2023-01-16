/*
 * FontConstants.java 
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
package org.apex.base.constant;

import java.awt.Font;

/**
 * Contains all font constants used by editor application.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class FontConstants {

    /**
     * Font used for window title text.
     */
    public static final Font WINDOW_TITLE_FONT = new Font("", Font.BOLD,
            11);
    /**
     * Font used for line numbers.
     */
    public static final Font LINE_NUMBER_FONT = new Font("Verdana", Font.PLAIN,
            11);
    /**
     * The global font used by all swing components in application.
     */
    public static final Font SWING_GLOBAL_FONT = new Font("Verdana", Font.PLAIN,
            11);
    /**
     * Font used for tab title text.
     */
    public static final Font SELECTED_TAB_TITLE_FONT = new Font("", Font.BOLD,
            11);
    /**
     * Font used for tab title text.
     */
    public static final Font DOCUMENT_SELECTOR_FONT = new Font("", Font.BOLD, 11);

    /**
     * 
     */
    /**
     * Creates a new instance of {@code FontConstants}.
     */
    private FontConstants() {
    }
}