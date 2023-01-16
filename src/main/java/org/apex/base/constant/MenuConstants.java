/*
 * MenuConstants.java
 * Created on February 12, 2007, 6:42 PM
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

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all menu constants used by editor application.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MenuConstants {

    /**
     * New document menu Id.
     */
    public static final String NEW_FILE = "new";
    /**
     * Open document menu Id.
     */
    public static final String OPEN_FILE = "open";
    /**
     * Close document menu Id.
     */
    public static final String CLOSE_FILE = "close";
    /**
     * Close all documents menu Id.
     */
    public static final String CLOSE_ALL_FILES = "closeall";
    /**
     * Delete document menu Id.
     */
    public static final String DELETE_FILE = "deletefile";
    /**
     * Save document menu Id.
     */
    public static final String SAVE_FILE = "save";
    /**
     * 'Save document As' menu Id.
     */
    public static final String SAVE_FILE_AS = "saveas";
    /**
     * Save all documents menu Id.
     */
    public static final String SAVE_ALL_FILES = "saveall";
    /**
     * Print document menu Id.
     */
    public static final String PRINT_FILE = "print";
    /**
     * Exit editor menu Id.
     */
    public static final String EXIT_EDITOR = "exit";
    /**
     * Undo content menu Id.
     */
    public static final String UNDO = "undo";
    /**
     * Redo content menu Id.
     */
    public static final String REDO = "redo";
    /**
     * Cut content menu Id.
     */
    public static final String CUT = "cut";
    /**
     * Copy content menu Id.
     */
    public static final String COPY = "copy";
    /**
     * Paste content menu Id.
     */
    public static final String PASTE = "paste";
    /**
     * Delete selected text menu Id.
     */
    public static String DELETE_SELECTION = "selection";
    /**
     * Delete current line menu Id.
     */
    public static String DELETE_LINE = "currentline";
    /**
     * Delete next word menu Id.
     */
    public static String DELETE_WORD = "nextword";
    /**
     * Invert case menu Id.
     */
    public static final String INVERT_CASE = "invertcase";
    /**
     * Convert to upper case menu Id.
     */
    public static final String UPPER_CASE = "uppercase";
    /**
     * Convert to lower case menu Id.
     */
    public static final String LOWER_CASE = "lowercase";
    /**
     * View line number for a document menu Id.
     */
    public static final String VIEW_LINE_NUMBER = "linenumbers";
    /**
     * View document selector menu Id.
     */
    public static final String VIEW_DOCUMENT_SELECTOR = "documentselector";
    /**
     * View status bar menu Id.
     */
    public static final String VIEW_STATUS_BAR = "statusbar";
    /**
     * View properties of a document menu Id.
     */
    public static final String VIEW_DOCUMENT_PROPERTIES =
            "documentproperties";
    /**
     * Find text menu Id.
     */
    public static final String FIND = "find";
    /**
     * Find next match menu Id.
     */
    public static final String FIND_NEXT = "findnext";
    /**
     * Find previous match menu Id.
     */
    public static final String FIND_PREVIOUS = "findprevious";
    /**
     * Replace text menu Id.
     */
    public static final String REPLACE = "replace";
    /**
     * Navigate to a line menu Id.
     */
    public static final String GO_TO_LINE = "gotoline";
    /**
     * Navigate to a document menu Id.
     */
    public static String GO_TO_FILE = "gotofile";
    /**
     * Compile Java class menu Id.
     */
    public static final String COMPILE_JAVA = "compilejava";
    /**
     * Compile Java package menu Id.
     */
    public static final String COMPILE_JAVA_PAKAGE = "compilejavapackage";
    /**
     * Run Java application menu Id.
     */
    public static final String RUN_JAVA_APPLICATION = "runjavaapp";
    /**
     * Run Java applet menu Id.
     */
    public static final String RUN_JAVA_APPLET = "runjavaapplet";
    /**
     * Generate Javadoc for a Java class menu Id.
     */
    public static final String GENERATE_JAVADOC = "generatejavadoc";
    /**
     * Generate Javadoc for a Java package menu Id.
     */
    public static final String GENERATE_JAVADOC_PACKAGE =
            "generatejavadocpackage";
    /**
     * Split a document horizontally menu Id.
     */
    public static final String HORIZONTAL_SPLIT = "horizontalsplit";
    /**
     * Split a document vertically menu Id.
     */
    public static final String VERTICAL_SPLIT = "verticalsplit";
    /**
     * Unsplit a document menu Id.
     */
    public static final String UNSPLIT = "unsplit";
    /**
     * Unsplit all documents menu Id.
     */
    public static final String UNSPLIT_ALL = "unsplitall";
    /**
     * View output window menu Id.
     */
    public static final String OUTPUT_WINDOW = "output";
    /**
     * View palette window menu Id.
     */
    public static final String PALETTE_WINDOW = "palette";
    /**
     * User preferences menu Id.
     */
    public static final String PREFERENCES = "preferences";
    /**
     * Help menu Id.
     */
    public static final String HELP = "helptopics";
    /**
     * About tool menu Id.
     */
    public static final String ABOUT = "about";
    /**
     * View a document in web browser menu Id.
     */
    public static String VIEW_IN_WEB_BROWSER = "inwebbrowser";
    /**
     * Rename document menu Id.
     */
    public static String RENAME_DOCUMENT = "rename";
    /**
     * Move document menu Id.
     */
    public static String MOVE_DOCUMENT = "move";
    /**
     * Read only menu id.
     */
    public static final String READ_ONLY = "readonly";
    /**
     * The tools menu.
     */
    public static final String TOOLS = "tools";
    /**
     * The custom tools menu.
     */
    public static final String CUSTOM_TOOLS = "customtools";
    /**
     * The typed documents menu.
     */
    public static final String TYPED_DOCUMENTS = "newtypeddocuments";
    /**
     * The recent files menu.
     */
    public static final String RECENT_FILES = "recentfiles";
    /**
     * The report bug menu.
     */
    public static final String REPORT_BUG = "reportbug";
    /**
     * The request feature menu.
     */
    public static final String REQUEST_FEATURE = "requestfeature";
    /**
     * The online help menu.
     */
    public static final String ONLINE_HELP = "onlinehelp";
     /**
     * The stop run menu.
     */
    public static final String STOP_RUN = "stop";
    /**
     * A list of menus which are not document sensitive.
     */
    private static List<String> documentInsensitiveMenus;

    /**
     * Creates a new instance of {@code MenuConstants}.
     */
    private MenuConstants() {
    }

    /**
     * Returns a list of document insensitive menus.
     * @return A list of document insensitive menus.
     */
    public static List<String> getDocumentInsensitiveMenus() {
        if (documentInsensitiveMenus == null) {
            documentInsensitiveMenus = new ArrayList<String>();
            documentInsensitiveMenus.add(NEW_FILE);
            documentInsensitiveMenus.add(TYPED_DOCUMENTS);
            documentInsensitiveMenus.add(RECENT_FILES);
            documentInsensitiveMenus.add(OPEN_FILE);
            documentInsensitiveMenus.add(EXIT_EDITOR);
            documentInsensitiveMenus.add(VIEW_STATUS_BAR);
            documentInsensitiveMenus.add(VIEW_DOCUMENT_SELECTOR);
            documentInsensitiveMenus.add(OUTPUT_WINDOW);
            documentInsensitiveMenus.add(PREFERENCES);
            documentInsensitiveMenus.add(ONLINE_HELP);
            documentInsensitiveMenus.add(REPORT_BUG);
            documentInsensitiveMenus.add(REQUEST_FEATURE);
            documentInsensitiveMenus.add(STOP_RUN);
            documentInsensitiveMenus.add(ABOUT);
            
        }
        return documentInsensitiveMenus;
    }
}
