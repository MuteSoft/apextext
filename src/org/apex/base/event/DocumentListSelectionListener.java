/*
 * DocumentListSelectionListener.java
 * Created on February 25, 2007, 1:22 PM
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
package org.apex.base.event;

import org.apex.base.data.AbstractDocument;
import org.apex.base.data.DocumentWrapper;
import org.apex.base.data.EditorContext;
import org.apex.base.util.EditorUtil;
import org.apex.base.util.MenuUtil;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * A listener to update document tab selection and 'editor title' when document selection
 * in left hand document selector is changed.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentListSelectionListener implements ListSelectionListener {

    /**
     * The editor context.
     */
    private EditorContext context;

    /** 
     * Creates a new instance of {@code DocumentListSelectionListener} using
     * editor context.
     * @param context The editor context.
     */
    public DocumentListSelectionListener(EditorContext context) {
        this.context = context;
    }

    /**
     * This method is called when selection in left hand document selector
     * is changed. It selects the corresponding document tab and displays the
     * selected document.
     * @param event The list selection event.
     */
    public void valueChanged(ListSelectionEvent event) {
        if (event.getValueIsAdjusting()) {
            // Do Nothing.
        } else {
            boolean propagateSelection = context.getEditorProperties().
                    isPropagateSelection();
            String selectedDocName = context.getEditorComponents().getEditorBody().
                    getDocSelector().
                    getSelectedDocumentFullName();
            if (selectedDocName != null && propagateSelection) {
                context.getEditorProperties().setPropagateSelection(false);
                selectTabAndDisplay(context, selectedDocName);
                context.getEditorProperties().setPropagateSelection(true);
            }
        }
    }

    /**
     * Selects a document tab having specified fully qualified document name.
     * @param context The editor context.
     * @param selectedDocName Fully qualified document name.
     * 
     */
    public void selectTabAndDisplay(EditorContext context,
            String selectedDocName) {
        DocumentWrapper documentWrapper =
                context.getEditorProperties().getOpenDocumentWrapper(
                selectedDocName);
        selectTabAndDisplay(context, documentWrapper);
    }

    /**
     * Selects a document tab containing specified document wrapper. Also, it
     * updates the editor title and enable status of all document sensitive menus.
     * @param context The editor context.
     * @param documentWrapper A document wrapper.
     */
    public void selectTabAndDisplay(EditorContext context,
            DocumentWrapper documentWrapper) {
        if (documentWrapper == null || documentWrapper.getDocument() == null) {
            return;
        }
        AbstractDocument file = documentWrapper.getDocument();
        // If splitted then component has been changed, so need to take care of that.
        context.getEditorComponents().getEditorBody().getDocsWindow().
                getDocsTabbedPane().
                setSelectedComponent(file.getComponent());
        EditorUtil.updateEditorProperties(context, documentWrapper);

        /**
         * When a file selection changes update the document queue as well.
         * Basically it happens during open or close file operation or
         * when we select a file randomly to view.
         */
        context.getEditorProperties().getDocsQueue().add(file.getDisplayName(),
                file.getAbsolutePath());

        /**
         * This is required basically for split status. But I feel split status should not
         * be document dependent. Later design the splitter in good way to avoid such and
         * remove this call.
         */
        MenuUtil.updateMenuStatus(context, file);
    }
}
