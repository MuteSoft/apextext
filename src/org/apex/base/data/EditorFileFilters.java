/*
 * EditorFileFilters.java 
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

import org.apex.base.core.EditorBase;
import org.apex.base.settings.DocumentTypesConfiguration;
import org.apex.base.settings.EditorConfiguration;
import org.apex.base.settings.event.DocTypesConfigChangeEvent;
import org.apex.base.settings.event.DocTypesConfigChangeListener;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.apex.base.util.DocumentData;
import org.apex.base.util.FileUtil;
import org.apex.base.util.StringUtil;

/**
 * This class maintains a list of file filters which can be used by {@code JFileChooser}
 * for filtering the set of files shown to the user.
 * <p>
 * Also, it keeps track of currently selected file filter.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class EditorFileFilters {

    /**
     * An array of file filters.
     */
    private static DocumentNameExtensionFilter[] fileFilters;
    /**
     * The current filter.
     */
    private static FileFilter choice;


    static {
        EditorConfiguration.addDocumentTypesConfigChangeListener(new DocTypesConfigChangeListener() {

            public void propertyValueChanged(DocTypesConfigChangeEvent event) {
                DocumentTypesConfiguration docTypesConfig = getContext().
                        getConfiguration().
                        getGeneralConfig().getDocTypes();
                int iCount = 0;
                for (IDocumentType docType : getContext().getEditorProperties().
                        getDocumentTypeBase().
                        getDocumentTypes()) {
                    if (docTypesConfig.isDocTypeAffected(docType) && fileFilters[iCount].
                            getDocumentType().
                            equals(docType)) {
                        String defaultExtension = docType.getDefaultExtension();
                        Vector<String> extensions =
                                docTypesConfig.getExtensions(docType);

                        Vector<String> tempExtensions = new Vector<String>();
                        tempExtensions.add(defaultExtension);
                        tempExtensions.addAll(extensions);
                        fileFilters[iCount].setExtensions(tempExtensions);
                    }
                    iCount++;
                }
            }
        });
    }

    /**
     * Returns all file filters.
     * <p>
     * These file filters can be used with a {@code JFileChooser} 
     * for filtering the set of files shown to the user.
     * @return An array of file filters.
     */
    public static DocumentNameExtensionFilter[] getFileFilters() {
        if (fileFilters == null) {
            fileFilters = new DocumentNameExtensionFilter[getContext().
                    getEditorProperties().
                    getDocumentTypeBase().getDocumentTypes().size()];
            DocumentTypesConfiguration docTypesConfig = getContext().
                    getConfiguration().
                    getGeneralConfig().
                    getDocTypes();
            int iCount = 0;
            for (IDocumentType docType : getContext().getEditorProperties().
                    getDocumentTypeBase().
                    getDocumentTypes()) {
                String defaultExtension = docType.getDefaultExtension();
                Vector<String> extensions =
                        docTypesConfig.getExtensions(docType);

                Vector<String> tempExtensions = new Vector<String>();
                tempExtensions.add(defaultExtension);
                tempExtensions.addAll(extensions);

                fileFilters[iCount] = new DocumentNameExtensionFilter(
                        tempExtensions,
                        docType);
                iCount++;
            }
        }

        return fileFilters;
    }

    /**
     * Returns the currently chosen file filter.
     * @return The current file filter.
     * @see #setChoice(javax.swing.filechooser.FileFilter) 
     */
    public static FileFilter getChoice() {
        return choice;
    }

    /**
     * Sets the current file filter.
     * @param aChoice The file filter to set as current choice.
     * @see #getChoice() 
     */
    public static void setChoice(FileFilter aChoice) {
        choice = aChoice;
    }

    /**
     * Creates an instance of {@code EditorFileFilters}.
     */
    private EditorFileFilters() {
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    private static EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Adds all file filters to given {@code JFileChooser}.
     * <p>
     * If a filter choice is made before, sets that previously chosen file filter as
     * currently selected file filter. Also, the extensio of given file name is used to
     * determine the file filter to be selected.
     * @param fileChooser The file chooser component.
     * @param fileName The file name and its extension is used to determine the filter to be selected.
     */
    public static void addFileFilters(JFileChooser fileChooser, String fileName) {
        getFileFilters();
        DocumentNameExtensionFilter fileFilterToBeSelected = null;
        IDocumentType currentFileDocType = null;
        if (!StringUtil.isNullOrEmpty(fileName)) {
            String extension = FileUtil.getExtension(fileName);
            currentFileDocType = DocumentData.getDocumentType(
                    getContext(), extension);
        }
        for (DocumentNameExtensionFilter fileFilter : fileFilters) {
            fileChooser.addChoosableFileFilter(fileFilter);
            if (fileFilter.getDocumentType().equals(currentFileDocType)) {
                fileFilterToBeSelected = fileFilter;
            }
        }
        // Fix for bug id 2887828 (sourceforge.net)
        // Set the file filter to be selected
        if (fileFilterToBeSelected != null) {
            fileChooser.setFileFilter(fileFilterToBeSelected);
        } else if (choice != null && choice instanceof DocumentNameExtensionFilter) {
            fileChooser.setFileFilter(getChoice());
        } else {
            fileChooser.setFileFilter(fileChooser.getAcceptAllFileFilter());
        }
    }

    /**
     * Adds all file filters to given {@code JFileChooser}.
     * <p>
     * If a filter choice is made before, sets that previously chosen file filter as
     * currently selected file filter.
     * @param fileChooser The file chooser component.
     */
    public static void addFileFilters(JFileChooser fileChooser) {
        addFileFilters(fileChooser, null);
    }
}
