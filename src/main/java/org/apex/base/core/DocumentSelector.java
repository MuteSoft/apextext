/*
 * DocumentSelector.java
 * Created on February 12, 2007, 9:54 PM
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
package org.apex.base.core;

import java.awt.Insets;
import org.apex.base.data.EditorContext;
import org.apex.base.component.ApexPanel;
import java.util.Vector;

/**
 * The left hand document selector of the editor. It makes all opened
 * documents or projects visible to user. This can be a simple list or a tree kind
 * complex structure.
 * @author Mrityunjoy Saha
 * @version 1.2
 * @since Apex 1.0
 */
public abstract class DocumentSelector extends ApexPanel {

    /**
     * Indicates document selector shows documents in a list.
     */
    public static final int LIST_VIEW = 0;
    /**
     * Indicates document selector shows documents in a tree.
     */
    public static final int TREE_VIEW = 1;

    /**
     * Creates a new instance of DocumentSelector with current editor context.
     */
    protected DocumentSelector() {
        createDocumentSelector();
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    public EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Creates the view of document selector.
     */
    protected abstract void createDocumentSelector();

    /**
     * Based on requested type returns a suitable implementation of 
     * document selector.     
     * @param type Document selector view type.
     * @return A concrete implementation of document selector.
     * @see #LIST_VIEW
     * @see #TREE_VIEW
     */
    public static DocumentSelector getInstance(int type) {
        DocumentSelector docSelector = null;
        switch (type) {
            case LIST_VIEW:
                docSelector = new ListDocumentSelector();
                break;
            case TREE_VIEW:
                ;
        }
        return docSelector;
    }

    @Override
    public Insets getInsets() {
        //return EditorKeyConstants.DOCUMENT_SELECTOR_MARGIN;
        return super.getInsets();
    }

    /**
     * Returns fully qualified name of selected document.
     * @return Fully qualified name of selected document.
     */
    public abstract String getSelectedDocumentFullName();

    /**
     * Returns display name of selected document.
     * @return Display name of selected document.
     */
    public abstract String getSelectedDocumentDisplayName();

    /**
     * Returns a list of fully qualified names of opened documents.
     * <p>
     * This list may not be ordered or sorted.
     * @return A list of fully qualified names of opened documents.
     */
    public abstract Vector<String> getDocumentFullNames();

    /**
     * Returns a list of display names of opened documents.
     * <p>
     * This list may not be ordered or sorted.
     * @return A list of display names of opened documents.
     */
    public abstract Vector<String> getDocumentDisplayNames();

    /**
     * Returns a list of fully qualified names of opened documents.
     * <p>
     * This list maintains the order in which documents are displayed.
     * @return A list of fully qualified names of opened documents.
     */
    public abstract Vector<String> getSortedDocumentFullNames();

    /**
     * Returns a list of display names of opened documents.
     * <p>
     * This list maintains the order in which documents are displayed.
     * @return A list of display names of opened documents.
     */
    public abstract Vector<String> getSortedDocumentDisplayNames();

    /**
     * Returns the number of elements present in the document selector.
     * @return Number of elements present in the document selector.
     */
    public abstract int getDocumentListSize();

    /**
     * Adds a document to the document selector.
     * @param name Document display name.
     * @param path Fully qualified document name.
     */
    public abstract void addDocument(String name, String path);

    /**
     * Removes document from a specified position.
     * @param documentIndex Position in the document selector.
     */
    public abstract void removeDocumentAt(int documentIndex);

    /**
     * Replaces document in a specified position.
     * @param documentIndex Position in the document selector.
     * @param name New document display name.
     * @param path New fully qualified document name.
     */
    public abstract void updateDocumentAt(int documentIndex, String name,
            String path);

    /**
     * Selects a document at the specified index.
     * @param documentIndex Position in the document selector.
     */
    public abstract void selectDocumentAt(int documentIndex);

    /**
     * Removes all documents from the document selector.
     */
    public abstract void removeDocuments();
}