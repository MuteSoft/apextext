/*
 * CloseFileMenu.java
 * Created on February 14, 2007, 9:24 PM
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

import javax.swing.text.BadLocationException;
import org.apex.base.component.DocumentTabComponent;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.logging.Logger;
import org.apex.base.util.DocumentData;
import org.apex.base.util.DocumentSelection;
import org.apex.base.util.EditorUtil;
import org.apex.base.util.MenuUtil;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import org.apex.base.data.HighlightedDocument;

/**
 * A menu target to close a specified document or current document from editor.
 * <p>
 * Before closing the document it determines whether document is unsaved and provides
 * confirmation message to user based on that and {@code CLOSE_WARNING_TO_BE_DISPLYED}
 * value in input parameters.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CloseFileMenu extends UILessMenu {

    /**
     * Indicates the document is closed successfully.
     */
    public static final int CLOSED = 0;
    /**
     * Indicates the close operation is cancelled.
     */
    public static final int CLOSE_CANCELLED = 1;
    /**
     * Indicates whether for unsaved document confirmation message to be
     * displayed before closing it.
     */
    public static final String CLOSE_WARNING_TO_BE_DISPLYED =
            "CLOSE_WARNING_TO_BE_DISPLYED";

    /**
     * Creates a new instance of CloseFileMenu.
     */
    public CloseFileMenu() {
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Calls {@link #closeFile(InputParams, OutputParams)} to close
     * the document.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void execute(InputParams in, OutputParams out) {
        closeFile(in, out);
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Closes the current document.
     * <p>
     * It calls {@link #closeFile(AbstractDocument, InputParams, OutputParams) }
     * method which performs actual close operation.
     * @param in Input parameters.
     * @param out Output parameters.
     * @return The close status.
     */
    public int closeFile(InputParams in, OutputParams out) {
        // Check whether a different tab other than current document is selected
        Object source = in.get("ACTION_SOURCE");
        if (source instanceof JMenuItem
                && ((JMenuItem) source).getParent() instanceof JPopupMenu
                && ((JPopupMenu) ((JMenuItem) source).getParent()).getInvoker() instanceof DocumentTabComponent) {
            DocumentTabComponent tabComp =
                    (DocumentTabComponent) ((JPopupMenu) ((JMenuItem) source).
                    getParent()).getInvoker();
            AbstractDocument file = tabComp.getDocument();
            if (file != null) {
                return closeFile(file, in, out);
            }
        }
        if (getContext().getEditorProperties().getCurrentDocument() == null) {
            return CLOSE_CANCELLED;
        }
        return closeFile(getContext().getEditorProperties().getCurrentDocument(),
                in, out);
    }

    /**
     * Before closing the document it determines whether document is unsaved and provides
     * confirmation message to user based on that and {@code CLOSE_WARNING_TO_BE_DISPLYED}
     * value in input parameters.
     * @param file The document to be closed.
     * @param in Input parameters.
     * @param out Output parameters.
     * @return The close status.
     */
    public int closeFile(AbstractDocument file, InputParams in,
            OutputParams out) {
        Logger.logInfo("Closing document: " + file.getAbsolutePath());
        // Assume that if CLOSE_WARNING_TO_BE_DISPLYED key is not found in Input then warning to be displayed
        boolean warningToBeDisplayed = in.containsKey(
                CLOSE_WARNING_TO_BE_DISPLYED)
                ? (Boolean) in.get(CLOSE_WARNING_TO_BE_DISPLYED)
                : true;
        // Show the warning if the file is not saved
        if (warningToBeDisplayed && (!file.isSaved())) {
            int selectedOption =
                    MenuMessageManager.showConfirmMessageYesNoCancel(getContext().
                    getEditorComponents().getFrame(),
                    1000, "FILENAME=" + file.getName());
            switch (selectedOption) {
                case JOptionPane.YES_OPTION:
                    // @TODO There is a thread issue. Since the save as menu is executing in a thread and 
                    // returning immediately, before saving the file it is getting closed. Hence calling individual methods
                    // to perform save as operation in current thread.
                    MenuManager.getMenuById(MenuConstants.SAVE_FILE).
                            processMenu(in, out);
                    if (out.get("SAVED_FILE") != null) {
                        file = (AbstractDocument) out.get("SAVED_FILE");
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                case JOptionPane.CANCEL_OPTION:
                    // Do Nothing.
                    return CLOSE_CANCELLED;
                default:
                    return CLOSE_CANCELLED;
            }
        }
        int selectionIndexAfterClose = DocumentSelection.
                getSelectionIndexAfterClose(getContext(),
                file);
        // Check whether closing document is currently selected.
        boolean isItSelectedTab = getContext().getEditorComponents().
                getEditorBody().
                getDocsWindow().
                getDocsTabbedPane().getSelectedComponent()
                == file.getComponent();
        DocumentData.removeFileFromList(getContext(), file);
        getContext().getEditorComponents().getEditorBody().getDocsWindow().
                getDocsTabbedPane().
                remove(file.getComponent());

        /**
         * When we close a file update the document queue as well.
         * Basically it happens during open or close file operation or
         * when we select a file randomly to view.
         */
        getContext().getEditorProperties().getDocsQueue().remove(file.
                getAbsolutePath());

        if (selectionIndexAfterClose != -1) {
            /*
             * If user is trying to close a document which is currently not selected, then do not
             * change the current selection. For example document 4 is currently selected and 
             * from tabbed pane either user presses 'close' button of 'document 2' tab or user right clicks
             * on 'document 2' tab and chooses 'close' menu.
             */
            if (isItSelectedTab) {
                DocumentSelection.setDocumentSelectedIndex(getContext(),
                        selectionIndexAfterClose);
            }
        } else {
            EditorUtil.updateEditorProperties(getContext());
            MenuUtil.updateMenuStatus(getContext(), false);
        }
        if (!file.isTemporary()) {
            getContext().getEditorProperties().getRecentFiles().
                    pushAndRefreshMenuBar(file);
        }
        cleanUp(file);
        return CLOSED;
    }

    /**
     * Closes the specified document from editor.
     * @param file The document to be closed.
     * @return The close status.
     * @see #closeFile(AbstractDocument, InputParams, OutputParams).
     */
    public int closeFile(AbstractDocument file) {
        return closeFile(file, new InputParams(), new OutputParams());
    }

    /**
     * Do cleanup after closing the document.
     * @param file The document being closed.
     */
    private void cleanUp(AbstractDocument file) {
        file.getUndoManager().discardAllEdits();
        file.getEditor().removeListeners();
        try {
            file.getEditor().getDocument().
                    remove(0, file.getEditor().getDocument().getLength());
        } catch (BadLocationException ex) {
            Logger.logError("Error while cleaning up closed document.", ex);
        }
        ((HighlightedDocument) file.getEditor().getDocument()).setHighlightStyle(
                null, null);
        file.getEditor().removeAll();
        file.getEditor().clearSearchHighlights();
        file.setEditor(null);
        file.setComponent(null);
        file.setLineNumberArea(null);
        file=null;
    }
}
