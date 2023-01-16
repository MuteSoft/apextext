/*
 * MenuSaveFile.java
 * Created on December 20, 2006, 6:17 PM
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
package org.apex.base.menu;

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.DocumentWrapper;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.function.Function;
import org.apex.base.function.WriteFile;
import org.apex.base.logging.Logger;
import org.apex.base.util.DocumentCreator;
import org.apex.base.util.DocumentData;
import org.apex.base.util.DocumentSelection;
import org.apex.base.util.EditorUtil;
import org.apex.base.util.FileUtil;
import org.apex.base.util.MenuUtil;
import org.apex.base.component.ApexFrame;
import org.apex.base.data.EditorFileFilters;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import org.apex.base.data.DocumentNameExtensionFilter;
import org.apex.base.data.DocumentType;
import org.apex.base.data.IDocumentType;
import org.apex.base.util.StringUtil;

/**
 * Saves a non-persistent document in file system. It displays a file chooser where
 * user can browse the local file system and also network files.
 * From this file chooser user can choose a single file target to save the document.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class SaveAsFileMenu extends BasicUIMenu {

    /**
     * Key used to retrieve save file indicator flag from input.
     */
    public static final String SAVE_TO_DISK = "SAVE_TO_DISK";
    /**
     * Key used to validate target indicator flag from input.
     */
    public final static String VALIDATE_TARGET = "VALIDATE_TARGET";

    /**
     * Creates a new instance of {@code SaveAsFileMenu}.
     */
    public SaveAsFileMenu() {
    }

    public void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Creates a file chooser where user can browse the file system and choose a target file
     * to save the document.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @SuppressWarnings(value = "unchecked")
    public void createUI(InputParams in,
            OutputParams out) {
        ApexFrame frame = getContext().getEditorComponents().getFrame();
        AbstractDocument sourceDocument =
                in.get("SOURCE_FILE") == null
                ? getContext().getEditorProperties().getCurrentDocument()
                : (AbstractDocument) in.get("SOURCE_FILE");
        String directoryToBeOpened = (sourceDocument == null || sourceDocument.
                isTemporary())
                ? getContext().getEditorProperties().getDirectoryToBeOpened()
                : sourceDocument.getDirectory();
        JFileChooser fileChooser = new JFileChooser(directoryToBeOpened, FileSystemView.
                getFileSystemView());
        fileChooser.setMultiSelectionEnabled(false);
        String fileName = sourceDocument.getName();
        // Set the default file name
        fileChooser.setSelectedFile(new File(directoryToBeOpened, fileName));
        // Fix for bug id 2887828 (sourceforge.net)
        // Add file Filters           
        EditorFileFilters.addFileFilters(fileChooser);
        int chosenOption = fileChooser.showDialog(frame, "Save As");
        EditorFileFilters.setChoice(fileChooser.getFileFilter());
        if (chosenOption == JFileChooser.APPROVE_OPTION
                && fileChooser.getSelectedFile() != null) {
            // Assign file extension if not provided by user
            File selectedFile = fileChooser.getSelectedFile();
            // Fix for bug id 2887828 (sourceforge.net)
            String extension = FileUtil.getExtension(selectedFile.
                    getAbsolutePath());
            IDocumentType docType = DocumentType.getDefaultDocumentType();
            if (StringUtil.isNullOrEmpty(extension)) {
                if (fileChooser.getFileFilter() instanceof DocumentNameExtensionFilter) {
                    docType = ((DocumentNameExtensionFilter) fileChooser.
                            getFileFilter()).getDocumentType();
                }
                String newFileName = selectedFile.getName() + "." + docType.
                        getDefaultExtension().
                        toLowerCase();
                selectedFile = new File(selectedFile.getParent(), newFileName);
            }

            getContext().getEditorProperties().setDirectoryToBeOpened(fileChooser.
                    getCurrentDirectory().getPath());
            in.put("SAVING_FILE", selectedFile);
            in.put("SOURCE_FILE", sourceDocument);
        }
    }

    /**
     * Calls {@link #doSaveAs(File, InputParams, OutputParams)} to save the document
     * in given target.
     * @param in Input parameters.
     * @param out Output parameters.
     * @see #doSaveAs(File, InputParams, OutputParams) 
     */
    public void postProcess(InputParams in, OutputParams out) {
        File selectedFile =
                (File) in.get("SAVING_FILE");
        AbstractDocument sourceDocument = (AbstractDocument) in.get(
                "SOURCE_FILE");
        out.remove("SAVED_FILE");
        if (selectedFile != null) {
            doSaveAs(sourceDocument, selectedFile, in, out);
        }
    }

    /**
     * Saves the current document at target file absolute path. It checkes whether specified
     * target file is writable and also checks validity of the target file.
     * @param targetDocument The target absolute path.
     * @param in Input parameters.
     * @param out Output parameters.
     * @return {@code true} if the current file is saved; otherwise returns {@code false}.
     */
    public boolean doSaveAs(File targetDocument, InputParams in,
            OutputParams out) {
        return doSaveAs(getContext().
                getEditorProperties().
                getCurrentDocument(), targetDocument, in, out);
    }

    /**
     * Saves the specified document at target file absolute path. It checks whether specified
     * target file is writeable and also checks validity of the target file.
     * @param sourceDocument The document to be saved.
     * @param targetDocument The target absolute path.
     * @param in Input parameters.
     * @param out Output parameters.
     * @return {@code true} if file is saved; otherwise returns {@code false}.
     */
    @SuppressWarnings("unchecked")
    public boolean doSaveAs(AbstractDocument sourceDocument, File targetDocument,
            InputParams in,
            OutputParams out) {
        if (targetDocument == null) {
            Logger.logInfo(
                    "Document 'save as' operation cancelled. Target document is null");
            return false;
        }
        boolean saveToDisk = in.containsKey(SAVE_TO_DISK)
                ? (Boolean) in.get(SAVE_TO_DISK)
                : true;
        if (saveToDisk) {
            if (!FileUtil.isDocumentWritable(targetDocument)) {
                MenuMessageManager.showErrorMessage(getContext().
                        getEditorComponents().getFrame(), 1009, "FILENAME="
                        + targetDocument.getAbsolutePath());
                return false;
            }
        }
        Logger.logInfo(
                "Saving document to disk. Document: " + targetDocument.
                getAbsolutePath());
        boolean validateTarget = in.containsKey(VALIDATE_TARGET)
                ? (Boolean) in.get(VALIDATE_TARGET)
                : true;
        if (validateTarget && !isTargetDocumentValid(sourceDocument,
                targetDocument)) {
            return false;
        }
        // Do use perfect cloning of the current file object in future.
        AbstractDocument selectedFile = DocumentCreator.verifyAndMigrateDocument(
                sourceDocument,
                targetDocument.getAbsolutePath(), getContext());
        // Mark the document as non-temporary
        selectedFile.setTemporary(false);
        DocumentWrapper currentDocumentWrapper = getContext().
                getEditorProperties().
                getCurrentDocumentWrapper();
        AbstractDocument oldDocument = currentDocumentWrapper.getDocument();
        currentDocumentWrapper.setDocument(selectedFile);
        if (saveToDisk) {
            saveToDisk(selectedFile, in, out);
        }
        //Update Document List. Pass old and new document wrappers.
        DocumentData.updateDocumentInList(getContext(),
                oldDocument,
                currentDocumentWrapper);

        // Set highlight style only if document type has been changed
        if (!oldDocument.getDocumentType().equals(selectedFile.getDocumentType())) {
            // Update document tab icon as well
            EditorUtil.updateDocumentIcon(getContext(), selectedFile);
            selectedFile.getDocument().
                    setHighlightStyle(selectedFile.getLexer(),
                    selectedFile.getDocumentStyle());
        }

        // Update Tab.
        EditorUtil.updateDocumentTitle(getContext(), selectedFile);
        // Replace the document in queue.
        getContext().getEditorProperties().getDocsQueue().replace(oldDocument.
                getAbsolutePath(),
                selectedFile.getDisplayName(),
                selectedFile.getAbsolutePath());

        // Select this file in the document list.
        DocumentSelection.setDocumentSelectedIndex(getContext(),
                selectedFile.getIndex());
        // Update Editor properties.
        EditorUtil.updateEditorProperties(getContext(),
                currentDocumentWrapper);

        // Mark the file as saved
        FileUtil.markAsSaved(getContext(), selectedFile);

        MenuUtil.updateMenuStatus(getContext(), selectedFile);
        // Overrite the writable status in status bar.
        getContext().getEditorComponents().getStatusBar().setWritableStatus(true);
        // Override the writable status in editor.
        selectedFile.getEditor().setEditable(true);
        out.put("SAVED_FILE", selectedFile);
        return true;
    }

    /**
     * Saves the specified document in file system.
     * @param selectedFile The file to be saved.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @SuppressWarnings("unchecked")
    private void saveToDisk(AbstractDocument selectedFile, InputParams in,
            OutputParams out) {
        in.put("SAVING_FILE", selectedFile);
        Function write = new WriteFile();
        write.process(in, out);
    }

    /**
     * Validates whether or not a specified absolute path is valid. If a document does
     * not exist it returns {@code true}. If target absolute path exists it displays
     * confirmation message to user whether or not user wishesh to overrite the existing file.
     * <p>
     * In case target absolute path exists it checks whether or not the same is opened in this
     * editor session. Based on this it shows a close confirmation message.
     * <p>
     * At any stage user does not accpet the confirmation it returns {@code false}.
     * @param sourceDocument The source document.
     * @param selectedFile The file to be validated.
     * @return {@code true} if target file is valid; otherwise returns {@code false}.
     */
    @SuppressWarnings("unchecked")
    private boolean isTargetDocumentValid(AbstractDocument sourceDocument,
            File selectedFile) {
        int confirmation = 0;
        boolean isValid = true;
        // Check whether this file already exists.
        if (selectedFile.exists()) {
            confirmation = MenuMessageManager.showConfirmMessage(getContext().
                    getEditorComponents().getFrame(), 1003, "FILENAME="
                    + selectedFile.getAbsolutePath());
            // If user wishes to overwrite the document then check whether existing document is open in current session.
            if (confirmation == JOptionPane.OK_OPTION) {
                isValid = true;
                if (getContext().getEditorProperties().isDocumentOpen(selectedFile.
                        getAbsolutePath())) {
                    // Check whether absolute path same as current document's absolute path
                    if (!sourceDocument.getAbsolutePath().equalsIgnoreCase(selectedFile.
                            getAbsolutePath())) {
                        // Show close warning.
                        confirmation = MenuMessageManager.showConfirmMessage(getContext().
                                getEditorComponents().getFrame(), 1004,
                                "FILENAME=" + selectedFile.getAbsolutePath());
                        if (confirmation == JOptionPane.OK_OPTION) {
                            isValid = true;
                            // Close previously opened document with same absolute path.
                            InputParams input = new InputParams();
                            input.put(
                                    CloseFileMenu.CLOSE_WARNING_TO_BE_DISPLYED,
                                    false);
                            ((CloseFileMenu) MenuManager.getMenuById(
                                    MenuConstants.CLOSE_FILE)).closeFile(getContext().
                                    getEditorProperties().getOpenDocument(selectedFile.
                                    getAbsolutePath()),
                                    input,
                                    null);
                        } else {
                            isValid = false;
                        }
                    }
                }
            } else {
                isValid = false;
            }
        }
        return isValid;
    }
}
