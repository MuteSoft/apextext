/*
 * OpenFileMenu.java
 * Created on December 20, 2006, 6:15 PM
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

import java.net.URISyntaxException;
import org.apex.base.data.AbstractDocument;
import org.apex.base.common.Task;
import org.apex.base.component.DocumentChangeTracker;
import org.apex.base.constant.CommonConstants;
import org.apex.base.data.DocumentWrapper;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.function.Function;
import org.apex.base.function.ReadFile;
import org.apex.base.util.DocumentCreator;
import org.apex.base.util.DocumentData;
import org.apex.base.util.DocumentSelection;
import org.apex.base.util.MenuUtil;
import org.apex.base.component.ApexFrame;
import org.apex.base.data.EditorFileFilters;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.constant.MenuConstants;
import org.apex.base.function.AdvancedReadFile;
import org.apex.base.logging.Logger;
import org.apex.base.util.FileUtil;
import org.apex.base.util.StringUtil;

/**
 * A menu target to open a document from file system.
 * <p>
 * It displays a file chooser where user can browse the local file system and also network files.
 * From this file chooser user can choose a single file or multiple files and open those in editor to view 
 * or to edit or both.
 * @author Mrityunjoy Saha
 * @version 1.3
 * @since Apex 1.0
 */
public class OpenFileMenu extends BasicUIMenu {

    /**
     * Creates a new open file menu.
     */
    public OpenFileMenu() {
    }

    public void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Creates a file chooser where user can browse the file system and choose a file to open
     * in editor.
     * <p>
     * Provides facility to choose multiple files and open those in editor at the same time.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @SuppressWarnings("unchecked")
    public void createUI(InputParams in, OutputParams out) {
        Vector<File> openFilesVect = null;
        String menuId = ((String) in.get("MENU_ID"));
        if (!StringUtil.isNullOrEmpty(menuId) && menuId.equalsIgnoreCase(
                MenuConstants.OPEN_FILE)) {
            ApexFrame frame = getContext().getEditorComponents().getFrame();
            /*
             * If the current document is temporary or there is no current document,
             * the directory to be opened is default directory else take the directory of
             * current document.
             */
            String directoryToBeOpened = (getContext().getEditorProperties().
                    getCurrentDocument() == null || getContext().
                    getEditorProperties().
                    getCurrentDocument().isTemporary())
                    ? getContext().getEditorProperties().getDirectoryToBeOpened()
                    : getContext().
                    getEditorProperties().getCurrentDocument().
                    getDirectory();
            // @TODO Decision for creating file chooser dialog is pending
            // While opening a document let us not set the document filter based on current document type
            // Setting to blank value will not apply any specific filter, rather last selected filter will be applied
            JFileChooser fileChooser =
                    new JFileChooser(directoryToBeOpened, FileSystemView.
                    getFileSystemView());
            fileChooser.setDragEnabled(true);
            fileChooser.setMultiSelectionEnabled(true);
            // Fix for bug id 2887828 (sourceforge.net)
            EditorFileFilters.addFileFilters(fileChooser);
            int choice = fileChooser.showOpenDialog(frame);
            EditorFileFilters.setChoice(fileChooser.getFileFilter());
            if (choice == JFileChooser.APPROVE_OPTION) {
                File[] openFiles = fileChooser.getSelectedFiles();
                if (openFiles != null
                        && openFiles.length != 0) {
                    openFilesVect =
                            new Vector<File>(Arrays.asList(openFiles));
                } else {
                    openFilesVect = new Vector<File>();
                }
                in.put(CommonConstants.OPEN_FILES, openFilesVect);
            }
        } else {
            openFilesVect = new Vector<File>();
            openFilesVect.add(new File(menuId));
            in.put(CommonConstants.OPEN_FILES, openFilesVect);
        }
    }

    /**
     * Verifies the list of files chosen from file chooser browser and then opens
     * them sequentially.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    public void postProcess(InputParams in, OutputParams out) {
        @SuppressWarnings("unchecked")
        Vector<File> openFiles = (Vector<File>) in.get(
                CommonConstants.OPEN_FILES);
        int openFilesSize = openFiles != null
                ? openFiles.size()
                : 0;
        DocumentChangeTracker.getInstance().getTimer().stop();
        if (openFilesSize >= 1) {
            for (int fileCount = 0; fileCount < openFilesSize; fileCount++) {
                openFile(openFiles.get(fileCount).getAbsolutePath());
            }
        }
        in.remove(CommonConstants.OPEN_FILES);
        DocumentChangeTracker.getInstance().getTimer().start();
    }

    /**
     * Verifies the given absolute path and then opens the corresponding file in editor.
     * <p>
     * If given path does not exist in file system, a confirmation message is shown to user whether
     * a new file with that absolute path should be created.
     * <p>     
     * It checks whether application has read access to given document and then attempts to
     * read the file from disk.
     * @param path The absolute path of file to be opened.
     */
    public void openFile(String path) {
        if (!getContext().getEditorProperties().isAtleastOneDocumentOpen()) {
            MenuUtil.updateMenuStatus(getContext(), false);
        }
        // @TODO Algorithm to resolve file path needs optimization in future version
        // This is required only when file is selected from JFileChooser.
        if (path.contains("file:")) {
            path = "file://" + path.substring(path.indexOf("file:") + 5);
            path = path.replace('\\', '/');
        }
        File givenfile = new File(path);
        if (!givenfile.exists()) {
            try {
                givenfile = new File(new URI(path));
                path = givenfile.getAbsolutePath();
            } catch (URISyntaxException ex) {
                // Not a valid URI. Ignore the error.
                Logger.logError(
                        "The provided file path is niether a valid path nor a valid URI.",
                        ex);
            } catch (IllegalArgumentException iae) {
                // Not a valid URI. Ignore the error.
                Logger.logError(
                        "The provided file path is niether a valid path nor a valid URI.",
                        iae);
            }
        }
        // Check whether this file exist in file system.
        if (!givenfile.exists()) {
            // Ask user whether a new file with given name should be created
            int choice = MenuMessageManager.showConfirmMessage(getContext().
                    getEditorComponents().getFrame(), 1007, "FILENAME="
                    + givenfile.getAbsolutePath());
            if (choice == JOptionPane.CANCEL_OPTION) {
                return;
            } else {
                try {
                    // Happy to create this file and then open
                    givenfile.createNewFile();
                } catch (IOException ex) {
                    MenuMessageManager.showErrorMessage(getContext().
                            getEditorComponents().getFrame(), 1008,
                            "FILENAME=" + givenfile.getAbsolutePath());
                    return;
                }
            }
        }
        // Valid file.
        if (path != null && getContext().getEditorProperties().
                isDocumentOpen(path)) {
            // This document is currently open. Select this document tab if it not currently selected
            if (!path.equalsIgnoreCase(getContext().getEditorProperties().
                    getCurrentDocument().
                    getAbsolutePath())) {
                getContext().getEditorComponents().getEditorBody().
                        getDocSelector().
                        selectDocumentAt(getContext().
                        getEditorProperties().getOpenDocument(path).
                        getIndex());
            }
            return;
        }
        AbstractDocument file = DocumentCreator.createDocument(path,
                getContext());
        // Open the file
        boolean isFileOpened = openFile(file);
        if (isFileOpened) {
            // Add to recent files list.
            // @TODO In case of bulk open refreshing menu bar for every document
            // may cause performnace issues.
//            getContext().getEditorProperties().getRecentFiles().
//                    pushAndRefreshMenuBar(file);
            if (getContext().getEditorProperties().getNoOfOpenDocuments() == 1) {
                MenuUtil.updateMenuStatus(getContext(), true);
                MenuUtil.updateMenuStatus(getContext(), file);
            }
        }
    }

    /**
     * Verifies the given document and then opens the file in editor.
     * <p>
     * Calls {@link #openFileFromDisk(AbstractDocument) } to read file content and
     * insert content to document's data model.
     * <p>
     * It checks whether application has read access to given document and then attempts to
     * read the file from disk.
     * @param file The file to be opened.
     * @return {@code true} if the document is opened successfully; otherwise returns {@code false}.
     */
    @SuppressWarnings(value = "unchecked")
    private boolean openFile(AbstractDocument file) {
        if (file == null) {
            Logger.logInfo(
                    "Document 'open' operation cancelled. Source document is null");
            return false;
        }
        if (!FileUtil.isDocumentReadable(file)) {
            MenuMessageManager.showErrorMessage(getContext().
                    getEditorComponents().getFrame(), 1010, "FILENAME="
                    + file.getAbsolutePath());
            return false;
        }
        if (file.getAbsoluteFile().length() > EditorKeyConstants.USUAL_FILE_MAX_SIZE) {
            int choice = MenuMessageManager.showConfirmMessage(getContext().
                    getEditorComponents().getFrame(), 1011, "FILENAME="
                    + file.getAbsolutePath());
            if (choice == JOptionPane.CANCEL_OPTION) {
                return false;
            }
        }
        DocumentWrapper documentWrapper = new DocumentWrapper(file);
        InputParams input = new InputParams();
        input.put(CommonConstants.FILE_WRAPPER, documentWrapper);
        Task task = new CreateDocumentTask();
        // Pre process
        task.preProcess(input, new OutputParams());
        // Initialize menu states
        file.getMenuState().init(getContext());
        file.setSaved(false);
        /* While opening large document if editor takes more than 'document change tracking' interval,
         * editor shows irrelevant document upate confirmation message. To avoid that document's last
         * saved time is updated even before attempting to open the document from disk.
         */
        file.setLastSaved(file.lastModified());
        openFileFromDisk(file);
        DocumentData.addFileToList(getContext(), documentWrapper);
        int selectionIndex =
                DocumentSelection.getSelectionIndexAfterOpen(getContext());
        DocumentSelection.setDocumentSelectedIndex(getContext(),
                selectionIndex);
        if (getContext().getEditorProperties().isAtleastOneDocumentOpen()) {
            MenuUtil.updateMenuStatus(getContext(), true);
        }
        // Add to document queue as well.
        getContext().getEditorProperties().getDocsQueue().add(file.
                getDisplayName(),
                file.getAbsolutePath());
        // Fix for bug id 2064421 (sourceforge.net)
        // Update the directory information of this document.
        getContext().getEditorProperties().setDirectoryToBeOpened(
                file.getParent());
        // Post process
        task.postProcess(input, new OutputParams());
        return true;
    }

    /**
     * Reads the file content from disk and inserts content into document's data model to
     * display in editor.
     * @param file The document to be opened.
     * @see ReadFile
     */
    @SuppressWarnings("unchecked")
    public void openFileFromDisk(
            AbstractDocument file) {
        /* Open file from disk -Start */
        InputParams input = new InputParams();
        input.put("OPENING_FILE",
                file);
        //Function read = new ReadFile();
        Function read = new AdvancedReadFile();
        read.process(input, null);
        /* Open file from disk -End */
    }
}
