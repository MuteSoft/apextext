/*
 * ColorConstants.java 
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

import java.awt.Color;

/**
 * Contains all color constants used by editor application.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class ColorConstants {

    /**
     * Background color of selected tab.
     */
    public static final Color SELECTED_TAB_BACKGROUND_COLOR = new Color(255, 0,
            0);
    /**
     * Foreground color of selected tab.
     */
    public static final Color SELECTED_TAB_FOREGROUND_COLOR = new Color(255, 255,
            0);
    /**
     *  Background color of selected menu on user preferences page.
     */
    public static final Color SELECTED_SETTINGS_OPTION_BACKGROUND_COLOR =
            new Color(234, 234, 238);
    /**
     * Foreground color of selected menu on user preferences page.
     */
    public static final Color SELECTED_SETTINGS_OPTION_FOREGROUND_COLOR =
            Color.BLUE;
    /**
     * Foreground color of document name in left hand document selector.
     */
    public static final Color DOCUMENT_SELECTOR_FOREGROUND_COLOR = Color.RED;
    /**
     * Background color of document names in left hand document selector.
     */
    public static final Color DOCUMENT_SELECTOR_BACKGROUND_COLOR = null;
    /**
     * Foreground color of document names in document queue.
     */
    public static final Color DOCUMENT_QUEUE_FOREGROUND_COLOR = Color.BLACK;
    /**
     * Tab border color.
     */
    public static final Color TAB_BORDER = new Color(71, 40, 215);

    /**
     * Creates a new instance of {@code ColorConstants}.
     */
    private ColorConstants() {
    }
}
