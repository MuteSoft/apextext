/*
 * CloseMultipleFilesMenu.java 
 * Created on 16 Nov, 2008, 7:27:00 PM
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

import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.DocumentWrapper;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.util.DocumentData;

/**
 * Base class to close multiple documents.
 * This class determines whether there is any unsaved documents and based on
 * this either displays a confirmation message or silently closes all opened documents.
 * <p>
 * To close each document it delegates the request to {@link CloseFileMenu}.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class CloseMultipleFilesMenu extends UILessMenu {

    /**
     * Indicates all documents are closed successfully.
     */
    public static final int CLOSED_ALL = 2;
    /**
     * Indicates few of all documents are closed successfully.
     */
    public static final int PARTLY_CLOSED = 3;

    /**
     * Constructs a new instance of {@code CloseMultipleFilesMenu}.
     */
    public CloseMultipleFilesMenu() {
    }

    @Override
    protected void execute(InputParams in, OutputParams out) {
        closeAll(in, out);
    }

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    @Override
    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * It verifies whether there is any unsaved document in editor. If there
     * is at least one unsaved document, it shows a confirmation message to user whether
     * user wants to save changes before closing. And then it delegates the task to
     * {@link CloseFileMenu#closeFile(InputParams, OutputParams)} 
     * to actually close the document.
     * @param in Input parameters.
     * @param out Output parameters.
     * @return The close status.
     * @see CloseFileMenu#CLOSED
     * @see CloseFileMenu#CLOSE_CANCELLED
     * @see CloseFileMenu#CLOSE_WARNING_TO_BE_DISPLYED
     */
    @SuppressWarnings("unchecked")
    public int closeAll(InputParams in, OutputParams out) {
        HashMap<String, DocumentWrapper> documentList = getDocumentList(in, out);
        // Check whether there is any opened unsaved document in the editor
        boolean isAnyUnsavedDocument = isAnyUnsavedDocument(documentList);
        if (isAnyUnsavedDocument) {
            // Show warning with options 'save none', 'choose' and 'Cancel'
            int selectedOption = MenuMessageManager.
                    showConfirmMessageYesNoCancel(getContext().
                    getEditorComponents().getFrame(),
                    1002);
            switch (selectedOption) {
                case JOptionPane.YES_OPTION:
                    break;
                case JOptionPane.NO_OPTION:
                    in.put(CloseFileMenu.CLOSE_WARNING_TO_BE_DISPLYED, false);
                    break;
                case JOptionPane.CANCEL_OPTION:
                    // Do Nothing.
                    return CloseFileMenu.CLOSE_CANCELLED;
                default:
                    return CloseFileMenu.CLOSE_CANCELLED;
            }
        }
        int noOfDocuments = documentList.size();
        int noOfDocumentsClosed = closeDocuments(documentList, in, out);
        return noOfDocuments == noOfDocumentsClosed
                ? CLOSED_ALL
                : PARTLY_CLOSED;
    }

    /**
     * Returns the list of documents to be closed.
     * @param in Input parameters.
     * @param out Output parameters.
     * @return A list of documents to be closed.
     */
    protected abstract HashMap<String, DocumentWrapper> getDocumentList(
            InputParams in,
            OutputParams out);

    /**
     * Closes a set of documents sequentially.
     * @param documentList A list of documents.
     * @param in Input parameters.
     * @param out Output parameters.
     * @return Total number of documents closed.
     */
    private int closeDocuments(HashMap<String, DocumentWrapper> documentList,
            InputParams in,
            OutputParams out) {
        CloseFileMenu closeFile =
                (CloseFileMenu) MenuManager.getMenuById(MenuConstants.CLOSE_FILE);
        int noOfDocumentsClosed = 0;
        AbstractDocument[] sortedDocuments = DocumentData.sortDocumentsByIndex(
                documentList);
        for (AbstractDocument doc : sortedDocuments) {
            // Make this document the current one
            getContext().getEditorComponents().getEditorBody().getDocSelector().
                    selectDocumentAt(doc.getIndex());
            //Close the document.
            int closeStatus = closeFile.closeFile(doc, in, out);
            if (closeStatus == CloseFileMenu.CLOSE_CANCELLED) {
                break;
            }
            noOfDocumentsClosed++;
        }
        return noOfDocumentsClosed;
    }

    /**
     * Determines whether there is any unsaved document present in given list of documents.
     * @param documentList A list of documents.
     * @return {@code true} if at least one unsaved document present in the list, otherwise 
     *              returns {@code false}.
     */
    private boolean isAnyUnsavedDocument(
            HashMap<String, DocumentWrapper> documentList) {
        boolean isAnyUnsavedDocument = false;
        for (Iterator openedDocuments = documentList.keySet().iterator();
                openedDocuments.hasNext();) {
            if (!documentList.get(
                    openedDocuments.next()).getDocument().isSaved()) {
                isAnyUnsavedDocument = true;
                break;
            }
        }
        return isAnyUnsavedDocument;
    }
}