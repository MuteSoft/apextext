/*
 * EditorKeyConstants.java
 * Created on November 11, 2005, 1:57 PM
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Insets;

/**
 * This class contains all key constants used by editor application.
 * <p>
 * These constants can change editors appearance and functionality. 
 * @author Mrityunjoy Saha
 * @version 1.4
 * @since Apex 1.0
 */
public class EditorKeyConstants {

    /**
     * Document change tracker verifies the currently displayed document status
     * in file system every {@code DOCUMENT_CHANGE_TRACKING_INTERVAL} .
     * <P>
     * Time in milliseconds.
     */
    public static final int DOCUMENT_CHANGE_TRACKING_INTERVAL = 3000;
    /**
     * Document change tracking tolerance. If difference between document last saved time
     * in editor and document last modification time is less than or equals to this value
     * the change is not considered.
     * <p>
     * Time in milliseconds.
     */
    public static final int DOCUMENT_CHANGE_TRACKING_TOLERANCE = 100;
    /**
     * Blinking rate of caret in edit area.
     * <p>
     * This determines if and how fast the caret blinks, commonly used as one
     * way to attract attention to the caret.
     */
    public static final int CARET_BLINK_RATE = 500;
    /**
     * Edit area margin.
     */
    public static final Insets EDIT_AREA_MARGIN = new Insets(0, 10, 0, 0);
    /**
     * The margin for both list and tree based document selectors.
     */
    public static final Insets EDITOR_BODY_MARGIN = new Insets(0, 3, 0, 3);
    /**
     * The margin for both list and tree based document selectors.
     */
    public static final Insets LIST_DOCUMENT_SELECTOR_MARGIN = new Insets(2, 5,
            2, 2);
    /**
     * Background color of lines in edit area.
     * <p>
     * This color cannot be changed by user from preferences.
     */
    public static final Color EDIT_AREA_LINE_BACKGROUND_COLOR = new Color(255,
            255, 204);
    /**
     * File name where common messages are stored.
     */
    public static final String COMMON_MESSAGES =
            "org.apex.base.common.resources.Messages";
    /**
     * File name where menu messages are stored.
     */
    public static final String MENU_MESSAGES =
            "org.apex.base.menu.resources.Messages";
    /**
     * Default character encoding used by editor.
     * <p>
     * If no encoding is specified the default encoding is used to read and write files.
     */
    public static final String DEFAULT_CHARACTER_ENCODING = "UTF-8";
    /**
     * File name where settings messages are stored.
     */
    public static final String SETTINGS_MESSAGES =
            "org.apex.base.settings.resources.Messages";
    /**
     * The path of tab close button icon.
     */
    public static final String TAB_CLOSE_BUTTON = "images/closeButton_14.gif";
    /**
     * The property name to specify the editor UI language.
     */
    public static final String LANGUAGE_JVM_PARAM = "apex.lang";
    /**
     * The property name to enable console log.
     */
    public static final String CONSOLE_LOG_JVM_PARAM = "apex.console";
    /**
     * The property name to specify log directory.
     */
    public static final String LOG_DIRECTORY_JVM_PARAM = "apex.log.dir";
    /**
     * The property name to enable information log.
     */
    public static final String INFO_LOG_JVM_PARAM = "apex.info";
    /**
     * The property name to enable warning log.
     */
    public static final String WARNING_LOG_JVM_PARAM = "apex.warning";
    /**
     * The property name to enable error log.
     */
    public static final String ERROR_LOG_JVM_PARAM = "apex.error";
    /**
     * The property name to enable debug log.
     */
    public static final String DEBUG_LOG_JVM_PARAM = "apex.debug";
    /**
     * Application configuration properties file.
     */
    public static final String APPLICATION_CONFIGURATION_FILE =
            "config.cfg";
    /**
     *  Application configuration properties file maximum allowed size in bytes.
     */
    public static final long APPLICATION_CONFIGURATION_FILE_MAX_SIZE = 2000;
    /**
     * When a process is run, data from input (to program) stream and error stream
     * are pulled by threads. A pause is given after each pull operation.
     * Delay is in millisecond.
     */
    public static final int RESULT_AREA_MESSAGE_PULL_DELAY = 5;
    /**
     * Path of editor icon.
     */
    public static final String EDITOR_ICON = "images/apex.png";
    /**
     * Width of line number display area.
     */
    public static final int LINE_NUMBER_AREA_WIDTH = 40;
    /**
     * Number of undo allowed.
     */
    public static final int NUMBER_OF_UNDO_ALLOWED = 1000;
    /**
     * Path of application logo.
     */
    public static final String APPLICATION_SHORT_LOGO = "images/apexlogo-short.png";
    /**
     * The maximum limit of log file size in bytes. Currently it is 5 MB.
     */
    public static final int MAX_LOG_FILE_SIZE = 1024 * 1024 * 5;
    /**
     * The maximum number of concurrent log files. When the number of files reaches the maximum
     * allowed number, logging data is overridden.
     */
    public static final int MAX_LOG_FILE = 10;
    /**
     * The maximum number of files in recent files list.
     */
    public static final int MAX_RECENT_FILES = 5;
    /**
     * The default icon for all document types.
     */
    public static final String DEFAULT_FILE_ICON = "images/file-2.gif";
    /**
     * Rounded tab arc height.
     */
    public static final int ROUNDED_TAB_ARC_HEIGHT = 20;
    /**
     * Rounded tab arc width.
     */
    public static final int ROUNDED_TAB_ARC_WIDTH = 20;
    /**
     * Default graphics stroke used for painting.
     */
    public static final BasicStroke DEFAULT_GRAPHICS_STROKE = new BasicStroke(
            1f);
    /**
     * Graphics stroke used for tab painting.
     */
    public static final BasicStroke TAB_GRAPHICS_STROKE = new BasicStroke(1.2f);
    /**
     * Graphics stroke used for icon painting.
     */
    public static final BasicStroke ICON_GRAPHICS_STROKE = new BasicStroke(1.1f);
    /**
     * Icon to be displayed in document window tab.
     */
    public static final String OUTPUT_WINDOW_TAB_ICON = "images/console-2.png";
    /**
     *  Usual file maximum allowed size in bytes.
     */
    public static final long USUAL_FILE_MAX_SIZE = 2 * 1024 * 1024;
      /**
     * Start icon to be displayed in console.
     */
    public static final String CONSOLE_START_ICON = "images/start-1.gif";
      /**
     * Stop icon to be displayed in console.
     */
    public static final String CONSOLE_STOP_ICON = "images/stop-3.gif";
      /**
     * Clear icon to be displayed in console.
     */
    public static final String CONSOLE_CLEAR_ICON = "images/clear-4.gif";

    /**
     * Constructs a new instance of EditorKeyConstants.
     */
    private EditorKeyConstants() {
    }
}
