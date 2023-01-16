/*
 * DocumentSelection.java
 * Created on February 15, 2007, 7:05 PM 
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

import org.apex.base.data.AbstractDocument;
import org.apex.base.data.EditorContext;

/**
 * A utility class for calculating various parameters related to document selection. 
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentSelection {

    /**
     * Calculates the next selection index immediately after closing a document.
     * @param context The editor context.
     * @param closingFile The document being closed.
     * @return The next selection index.
     */
    public static int getSelectionIndexAfterClose(EditorContext context,
            AbstractDocument closingFile) {
        int selectionIndex = 0;
        int currentIndex = closingFile.getIndex();
        if (currentIndex == context.getEditorComponents().getEditorBody().
                getDocSelector().
                getDocumentListSize() - 1) {
            selectionIndex = currentIndex - 1;
        } else {
            selectionIndex = currentIndex;
        }
        return selectionIndex;
    }

    /**
     * Calculates the next selection index immediately after opening a document.
     * @param context The editor context.
     * @return The next selection index.
     */
    public static int getSelectionIndexAfterOpen(EditorContext context) {
        int selectionIndex = 0;
        selectionIndex = context.getEditorComponents().getEditorBody().
                getDocSelector().
                getDocumentListSize() - 1;
        return selectionIndex;
    }

    /**
     * Calculates the next selection index immediately after creating a temporary document.
     * @param context The editor context.
     * @return The next selection index.
     */
    public static int getSelectionIndexAfterNew(EditorContext context) {
        int selectionIndex = context.getEditorComponents().getEditorBody().
                getDocSelector().
                getDocumentListSize() - 1;
        return selectionIndex;
    }

    /**
     * Selects a document at specified position in the document selector.
     * @param context The editor context.
     * @param documentIndex The position in the document selector.
     * 
     */
    public static void setDocumentSelectedIndex(EditorContext context,
            int documentIndex) {
        context.getEditorComponents().getEditorBody().getDocSelector().
                selectDocumentAt(
                documentIndex);
    }

    /**
     * Creates a new instance of {@code DocumentSelection}.
     */
    private DocumentSelection() {
    }
}
