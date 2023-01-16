/*
 * CommonConstants.java
 * Created on February 12, 2007, 8:05 PM
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

/**
 * Contains all common constants used by editor application.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CommonConstants {

    /**
     * A key used to refer a document.
     */
    public static final String FILE = "file";
    /**
     * A key used to refer a document wrapper.
     */
    public static final String FILE_WRAPPER = "FileWrapper";
    /**
     * Extra space after tab title text.
     */
    public static final String TAB_TITLE_EXTRA_SPACE = "  ";
    /**
     * Document unsplit status.
     */
    public static final int UNSPLIT = 0;
    /**
     * Document horizontal split status.
     */
    public static final int HORIZONTAL_SPLIT = 1;
    /**
     * Document vertical split status.
     */
    public static final int VERTICAL_SPLIT = 2;
    /**
     * A word separator.
     */
    public static final String WORD_SEPARATOR = ".";
    /**
     * Blank text.
     */
    public static final String BLANK_TEXT = "";
    /**
     * Unsaved document indicator. After document name this indicator is placed to
     * dis
     */
    public static final String UNSAVED_FILE_INDICATOR = " *";
    /**
     * A separator between multiple options and parameters.
     */
    public static final String OPTIONS_SEPARATOR = ";";
    /**
     * A key used to refer a list of files to be opened in editor.
     */
    public static final String OPEN_FILES = "openFiles";
    /**
     * A key used to refer list of tabs to be added to a tabbed pane.
     */
    public static final String TABS = "tabs";

    /**
     * Creates a new instance of {@code CommonConstants}.
     */
    private CommonConstants() {
    }
}
