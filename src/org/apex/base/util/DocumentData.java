/*
 * DocumentData.java
 * Created on February 16, 2007, 8:10 PM 
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

import java.util.Arrays;
import java.util.HashMap;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.DocumentWrapper;
import org.apex.base.data.EditorContext;
import org.apex.base.logging.Logger;
import org.apex.base.settings.DocumentTypesConfiguration;
import java.util.Iterator;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;
import org.apex.base.component.DocumentIndexComparator;
import org.apex.base.data.IDocumentType;

/**
 * A utility class to perform operations on documents and document data.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentData {

    /**
     * Removes the current document from the master list of documents.
     * @param context The editor context.
     */
    public static void removeCurrentSelectedFileFromList(EditorContext context) {
        removeFileFromList(context, context.getEditorProperties().
                getCurrentDocument());
    }

    /**
     * Removes a specified document from the master list of documents.
     * @param context The editor context.
     * @param file The document.
     */
    public static void removeFileFromList(EditorContext context,
            AbstractDocument file) {
        // Remove from master list
        if (file.isTemporary()) {
            context.getEditorProperties().
                    removeOpenDocument(file.getName());
        } else {
            context.getEditorProperties().
                    removeOpenDocument(file.getAbsolutePath());
        }
        context.getEditorComponents().getEditorBody().getDocSelector().
                removeDocumentAt(file.getIndex());
        updateFileIndices(context, file);
    }

    /**
     * Adds a specified document to the master list of documents.
     * @param context The editor context.
     * @param documentWrapper The specified document wrapper.
     * 
     */
    public static void addFileToList(EditorContext context,
            DocumentWrapper documentWrapper) {
        AbstractDocument file = documentWrapper.getDocument();
        context.getEditorProperties().
                addOpenDocument(file.getAbsolutePath(),
                documentWrapper);
        context.getEditorComponents().getEditorBody().getDocSelector().
                addDocument(file.getDisplayName(),
                file.getAbsolutePath());
    }

    /**
     * Updates index of documents.
     * <p>
     * Every document is having an index. When a document is closed or new 
     * document opened indexes are re-allocated.
     * @param context The context
     * @param document The reference document.
     */
    private static void updateFileIndices(EditorContext context,
            AbstractDocument document) {
        Iterator files = context.getEditorProperties().getOpenDocumentIterator();
        while (files.hasNext()) {
            String fileKey = (String) files.next();
            DocumentWrapper documentWrapper =
                    context.getEditorProperties().getOpenDocumentWrapper(fileKey);
            if (documentWrapper.getDocument().getIndex()
                    > document.getIndex()) {
                documentWrapper.getDocument().setIndex(documentWrapper.
                        getDocument().
                        getIndex() - 1);
            }
        }
    }

    /**
     * Updates a document in the master list.
     * @param context The editor context.
     * @param oldDocument The old document.
     * @param newDocumentWrapper  The new document wrapper.
     * 
     * 
     */
    public static void updateDocumentInList(EditorContext context,
            AbstractDocument oldDocument,
            DocumentWrapper newDocumentWrapper) {
        context.getEditorProperties().removeOpenDocument(oldDocument.
                getAbsolutePath());
        // We are sure new document is not going to be temporary
        context.getEditorProperties().
                addOpenDocument(newDocumentWrapper.getDocument().
                getAbsolutePath(),
                newDocumentWrapper);
        // Update document selector
        context.getEditorComponents().getEditorBody().getDocSelector().
                updateDocumentAt(oldDocument.getIndex(),
                newDocumentWrapper.getDocument().
                getDisplayName(),
                newDocumentWrapper.getDocument().
                getAbsolutePath());
    }

    /**
     * Determines a document type from given extension.
     * <p>
     * If no document type found, default document type is returned.
     * @param context The editor context.
     * @param extension Document extension.
     * @return Document type.
     */
    public static IDocumentType getDocumentType(EditorContext context,
            String extension) {
        DocumentTypesConfiguration docTypesConfiguration =
                context.getConfiguration().
                getGeneralConfig().getDocTypes();

        return docTypesConfiguration.getDocumentType(extension);
    }

    /**
     * Calculates and returns the previous word of current caret position.
     * @param editArea The editor.
     * @param doc The underlying document in the editor.
     * @return Previous word of current caret position.
     */
    public static String getPreviousWord(JTextComponent editArea, Document doc) {

        int startPos = 0;
        try {
            startPos =
                    Utilities.getPreviousWord(editArea,
                    editArea.getCaretPosition());
            return doc.getText(startPos, editArea.getCaretPosition() - startPos);
        } catch (BadLocationException ex) {
            Logger.logWarning(
                    "Could not find previous word for given reference position.",
                    ex);
            return "";
        }
    }

    /**
     * Sorts a given set of documents by their index.
     * @param documents A list of documents.
     * @return A sorted array of documents.
     */
    public static AbstractDocument[] sortDocumentsByIndex(
            HashMap<String, DocumentWrapper> documents) {
        if (documents == null || documents.size() == 0) {
            return new AbstractDocument[0];
        }
        AbstractDocument[] sortedDocumentList = new AbstractDocument[documents.
                size()];
        int docCount = 0;
        for (Iterator docs = documents.keySet().iterator(); docs.hasNext();) {
            Object docPath = docs.next();
            if (documents.get(docPath) != null) {
                sortedDocumentList[docCount] = documents.get(docPath).
                        getDocument();
                docCount++;
            }

        }
        Arrays.sort(sortedDocumentList, new DocumentIndexComparator());
        return sortedDocumentList;
    }

    /**
     * Determines whether or not multiple lines selected in editor. If no selection found
     * it returns {@code true}.
     * @param context The editor context.
     * @return {@code true} if multiple lines selected in editor; otherwise returns {@code false}.
     *              If no selection found it returns {@code true}.
     */
    public static boolean isSelectedTextMultiLine(EditorContext context) {
        if (context.getEditorComponents().getStatusBar().getGeneralInfo() == null) {
            return true;
        }
        return context.getEditorComponents().getStatusBar().getGeneralInfo().
                indexOf("line") != -1;
    }

    /**
     * Creates a new instance of {@code DocumentData}.
     */
    private DocumentData() {
    }
}
