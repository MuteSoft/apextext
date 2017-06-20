/*
 * CloseOtherFilesMenu.java 
 * Created on 16 Nov, 2008, 12:33:47 PM
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
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.apex.base.component.DocumentTabComponent;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.DocumentWrapper;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;

/**
 * A menu target to close all documents except currently displayed document
 * or selected document from tab.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.1
 * @see CloseFileMenu
 * @see CloseMultipleFilesMenu
 */
public class CloseOtherFilesMenu extends CloseMultipleFilesMenu {

    /**
     * Constructs a new instance of {@code CloseOtherFilesMenu}.
     */
    public CloseOtherFilesMenu() {
    }

    @Override
    protected HashMap<String, DocumentWrapper> getDocumentList(InputParams in,
            OutputParams out) {
        HashMap<String, DocumentWrapper> documentList = new HashMap<String, DocumentWrapper>();
        // Get currently displayed document or selected document from tab.
        AbstractDocument currentDocument = null;
        Object source = in.get("ACTION_SOURCE");
        if (source instanceof JMenuItem
                && ((JMenuItem) source).getParent() instanceof JPopupMenu
                && ((JPopupMenu) ((JMenuItem) source).getParent()).getInvoker() instanceof DocumentTabComponent) {
            DocumentTabComponent tabComp =
                    (DocumentTabComponent) ((JPopupMenu) ((JMenuItem) source).
                    getParent()).getInvoker();
            currentDocument = tabComp.getDocument();
        } else {
            currentDocument = getContext().getEditorProperties().
                    getCurrentDocument();
        }
        for (Iterator openedDocuments = getContext().getEditorProperties().
                getOpenDocumentIterator(); openedDocuments.hasNext();) {
            String key = (String) openedDocuments.next();
            // Filter current document.
            if (currentDocument != null && !key.equals(currentDocument.
                    getAbsolutePath())) {
                documentList.put(key, getContext().getEditorProperties().
                        getOpenDocumentWrapper(key));
            }
        }
        return documentList;
    }
}
