/*
 * DocumentTabChangeListener.java
 * Created on February 25, 2007, 11:20 AM
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

import org.apex.base.data.EditorContext;
import org.apex.base.util.DocumentSelection;
import org.apex.base.util.EditorUtil;
import org.apex.base.util.MenuUtil;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.DocumentWrapper;
import javax.swing.event.ChangeEvent;

/**
 * A listenet to update document selection in left hand document selector and to
 * update 'editor title' when document tab selection is changed.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class DocumentTabChangeListener extends BasicTabChangeListener {

    /**
     * Creates a new instance of {@code DocumentTabChangeListener}.
     */
    public DocumentTabChangeListener() {
    }

    /**
     * This method is called when document tab selection is changed. It selects
     * the corresponding element in left hand document selector and displays the selected document.
     * @param selectedTabIndex The selecte tab index.
     * @param e The change event.
     */
    @Override
    public void doStateChanged(int selectedTabIndex, ChangeEvent e) {
        boolean propagateSelection = getContext().getEditorProperties().
                isPropagateSelection();
        if (propagateSelection) {
            getContext().getEditorProperties().setPropagateSelection(false);
            selectDocumentInList(getContext(), selectedTabIndex);
            getContext().getEditorProperties().setPropagateSelection(true);
        }
    }

    /**
     * Selects the element at specified index of left hand document selector.
     * Also, it updates the editor title and enable status of all document sensitive menus.
     * @param context The editor context.
     * @param index The index of document at left hand document selector.
     */
    public void selectDocumentInList(EditorContext context, int index) {
        String filePath = context.getEditorComponents().getEditorBody().
                getDocsWindow().
                getDocsTabbedPane().
                getToolTipTextAt(index);
        DocumentWrapper documentWrapper =
                context.getEditorProperties().getOpenDocumentWrapper(
                filePath);
        if (documentWrapper == null || documentWrapper.getDocument() == null) {
            return;
        }
        AbstractDocument file = documentWrapper.getDocument();
        int documentIndex = file.getIndex();
        DocumentSelection.setDocumentSelectedIndex(context, documentIndex);
        EditorUtil.updateEditorProperties(context, documentWrapper);

        /**
         * This is required basically for split status. But I feel split status should not
         * be document dependent. Later design the splitter in good way to avoid such and
         * remove this call.
         */
        MenuUtil.updateMenuStatus(context, file);
    }
}
