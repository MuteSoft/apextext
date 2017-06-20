/*
 * EditorUtil.java
 * Created on February 16, 2007, 8:35 PM 
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
package org.apex.base.util;

import org.apex.base.constant.CommonConstants;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.DocumentWrapper;
import org.apex.base.data.EditorContext;
import java.io.File;
import java.util.Locale;
import org.apex.base.core.EditorBase;

/**
 * A utility class to perform common operations and contains methods to provide
 * global editor properties like editor title, editor version etc.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class EditorUtil {

    /**
     * Determines and returns a {@code Locale} from language code available
     * as System property.      
     * @return A {@code Locale} for a language code which is available as System property.
     *              If language is not found returns {@code null}.
     */
    public static Locale getLocale() {
        Locale locale = null;
        String language = System.getProperty(
                EditorKeyConstants.LANGUAGE_JVM_PARAM);
        if (StringUtil.isNullOrEmpty(language)) {
            return null;
        }
        if (language.equalsIgnoreCase("hi")
                || language.equalsIgnoreCase("hindi")) {
            locale = new Locale("hi", "IN");
        } else if (language.equalsIgnoreCase("ja") || language.
                equalsIgnoreCase(
                "japanese")) {
            locale = new Locale("ja", "JP");
        }
        return locale;
    }

    /**
     * Updates editor title and current document wrapper in editor's global
     * properties using specified document wrapper and editor context.
     * @param context The editor context.
     * @param documentWrapper A document wrapper.
     * 
     */
    public static void updateEditorProperties(EditorContext context,
            DocumentWrapper documentWrapper) {
        context.getEditorComponents().getFrame().setTitle(getDisplayEditorTitleBarText(documentWrapper.
                getDocument()));
        context.getEditorProperties().setCurrentDocumentWrapper(documentWrapper);
    }

    /**
     * Determines and returns editor title display text using specified document.
     * @param document A document.
     * @return Editor title display text.
     */
    public static String getDisplayEditorTitleBarText(AbstractDocument document) {
        EditorContext context = EditorBase.getContext();
        if (document == null) {
            return context.getEditorProperties().
                    getEditorTitle()
                    + " " + context.getEditorProperties().getEditorVersion();
        }
        return document.getDisplayAbsolutePath() + " - " + context.
                getEditorProperties().
                getEditorTitle()
                + " " + context.getEditorProperties().getEditorVersion();
    }

    /**
     * Updates editor title with default title (without any document name) and sets current
     * document wrapper as {@code null}. This is typically called after all documents are closed
     * in th editor. 
     * @param context The editor context.
     */
    public static void updateEditorProperties(EditorContext context) {
        context.getEditorComponents().getFrame().setTitle(context.
                getEditorProperties().
                getEditorTitle() + " "
                + context.getEditorProperties().getEditorVersion());
        context.getEditorProperties().setCurrentDocumentWrapper(null);
    }

    /**
     * Close all tabs from document tabbed pane.
     * @param context The editor context.
     */
    public static void closeAllTabs(EditorContext context) {
        TabUtil.removeAllTabs(context.getEditorComponents().getEditorBody().
                getDocsWindow().
                getDocsTabbedPane());
    }

    /**
     * Updates document tab title.
     * @param context The editor context.
     * @param file A document.
     */
    public static void updateDocumentTitle(EditorContext context,
            AbstractDocument file) {
        //Update document title in tab
        TabUtil.updateTabTitle(context.getEditorComponents().getEditorBody().
                getDocsWindow().
                getDocsTabbedPane(), file);
        // Update document title in editor title bar
        context.getEditorComponents().getFrame().setTitle(getDisplayEditorTitleBarText(
                file));
        // Update document title in document selector
        context.getEditorComponents().getEditorBody().getDocSelector().
                updateDocumentAt(file.getIndex(),
                file.getDisplayName(), file.getAbsolutePath());
        // Update document title in document queue
        context.getEditorProperties().getDocsQueue().add(file.getDisplayName(),
                file.getAbsolutePath());

    }

    /**
     * Updates document tab icon.
     * @param context The editor context.
     * @param file A document.
     */
    public static void updateDocumentIcon(EditorContext context,
            AbstractDocument file) {
        TabUtil.updateTabIcon(context.getEditorComponents().getEditorBody().
                getDocsWindow().
                getDocsTabbedPane(), file);
    }

    /**
     * Returns line separator {@code String} for the operating system.
     * @return Line separator {@code String}.
     */
    public static String getLineSeparator() {
        return OperatingSystem.getOperatingSystem().getLineSeparator();
    }

    /**
     * Returns the file separator for the operating system.
     * @return The file separator.
     */
    public static String getFileSeparator() {
        return OperatingSystem.getOperatingSystem().getFileSeparator();
    }

    /**
     * Returns whether or not file path is case sensitive in the host operating system. For example,
     * in UNIX system file path is case sensitive where in WINDOWS file name and paths are case insensitive.
     * @return {@code true} if file path is case sensitive; otherwise returns {@code false}.
     */
    public static boolean isFilePathCaseSensitive() {
        return OperatingSystem.getOperatingSystem().isFilePathCaseSensitive();
    }

    /**
     * Returns external settings home directory in file system.
     * @return External settings home directory in file system.
     */
    public static String getApexSettingsHomeDir() {
        return FileUtil.getUserHomeDir() + File.separator + ".apextext"
                + File.separator + "resources";
    }

    /**
     * Returns the note to be shown for tools runtime options and parameters.
     * @return The note to be shown for tools runtime options and parameters.
     */
    public static String getMultipleOptionsNote() {
        return "Note: separate multiple inputs by '"
                + CommonConstants.OPTIONS_SEPARATOR + "'";
    }

    /**
     * Constructs a new instance of {@code EditorUtil}.
     */
    private EditorUtil() {
    }
}
