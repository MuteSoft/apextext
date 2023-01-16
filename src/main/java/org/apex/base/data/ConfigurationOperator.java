/*
 * ConfigurationOperator.java
 * Created on 27 Oct, 2007, 9:03:37 PM
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
 * An interface to operate with configuration data.
 * <p>
 * Operation includes building configuration objects from files and also
 * store configuration objects to files. Currently properties files are used
 * to store configuration data. However the framework supports any kind
 * of file. In future xml file will be used for this purpose.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public interface ConfigurationOperator {

    /**
     * A key to deal with document default extension.
     */
    public static final String DEFAULT_EXTENSION = "default-extension";
    /**
     * A key to deal with caret color.
     */
    public static final String CARET_COLOR = "caret-color";
    /**
     * A key to deal with caret width.
     */
    public static final String CARET_WIDTH = "caret-width";
    /**
     * The key to deal with line number area visibility.
     */
    public static final String VIEW_LINE_NUMBER = "view-line-number";
    /**
     * The key to deal with tab size.
     */
    public static final String TAB_SIZE = "tab-size";
    /**
     * The key to deal with indentation size.
     */
    public static final String INDENTATION_SIZE = "indentation-size";
    /**
     * The key to deal with code templates.
     */
    public static final String CODE_TEMPLATES = "code-templates";
    /**
     * The key to deal with document extensions.
     */
    public static final String EXTENSIONS = "extensions";
    /**
     * The key to deal with font family.
     */
    public static final String FONT_FAMILY = "font-family";
    /**
     * The key to deal with font size.
     */
    public static final String FONT_SIZE = "font-size";
    /**
     * The key to deal with font style.
     */
    public static final String FONT_STYLE = "font-style";
    /**
     * The key to deal with highlights.
     */
    public static final String HIGHLIGHT = "highlight";
    /**
     * The key to deal with menu bar.
     */
    public static final String MENUBAR = "menubar";
    /**
     * The key to deal with programming language specific syntaxes.
     */
    public static final String SYNTAX = "syntax";
    /**
     * Separator for multiple configuration data entries.
     */
    public static final String INTRA_SEPARATOR = ",";
    /**
     * Separator for multiple configuration properties.
     */
    public static final String INTER_SEPARATOR = "#";
    /**
     * The text to display when color code is undetermined.
     */
    public static final String DEFAULT_COLOR_STRING = "[]";
    /**
     * Separator for multiple configuration data entries.
     */
    public static final String INTRA_FILE_PATH_SEPARATOR = ",sep,";
    /**
     * The dot string.
     */
    public static final String DOT = ".";
    /**
     * The blank string.
     */
    public static final String BLANK = "";
    /**
     * The key to deal with code templates.
     */
    public static final String TEMPLATE = "template";
    /**
     * The key to deal with menu definitions.
     */
    public static final String DEFINITION = "-def";
    /**
     * The key to deal with tools provided with editor.
     */
    public static final String TOOLS = "tools";
    /**
     * The key to deal with custom document types.
     */
    public static final String CUSTOM_DOCUMENT_TYPES = "custom-document-types";
    /**
     * The key to deal with visibility of right margin of editor.
     */
    public static final String VIEW_RIGHT_MARGIN = "view-right-margin";
    /**
     * The key to deal with right margin size.
     */
    public static final String RIGHT_MARGIN = "right-margin";
    /**
     * The key to deal with right margin color.
     */
    public static final String RIGHT_MARGIN_COLOR = "right-margin-color";
    /**
     * The key to deal with visibility of document selector of editor.
     */
    public static final String VIEW_DOCUMENT_SELECTOR = "view-document-selector";
    /**
     * The key to deal with visibility of status bar of editor.
     */
    public static final String VIEW_STATUS_BAR = "view-status-bar";
    /**
     * The key to deal with recent files list.
     */
    public static final String RECENT_FILES = "recent-files";
    /**
     * The key to deal with recent files list.
     */
    public static final String MAX_RECENT_FILES_COUNT = "max-recent-files-count";
    /**
     * The key to deal with recent files list.
     */
    public static final String REUSE_CONSOLE = "reuse-console";

    /**
     * Returns the external (to code) directory where configuration data is stored.
     * @return  External (to code) directory where configuration data is stored.
     */
    String getConfigurationHomeDir();

    /**
     * Returns the configuration file type.    
     * @return The configuration file type.
     * @see ConfigurationFileType
     */
    ConfigurationFileType getConfigFileType();

    /**
     * Returns the configuration file extension.
     * <p>
     * For example, configuration files can be of type xml or properties etc.
     * @return The configuration file extension.
     */
    String getConfigFileExtension();
}
