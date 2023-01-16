/*
 * EditorPopupFocusListener.java
 * Created on 1 July, 2007, 2:15 PM
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

import org.apex.base.codecompletion.CodeCompletionManager;
import org.apex.base.codecompletion.CodeCompletionPopup;
import org.apex.base.core.EditorBase;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import org.apex.base.component.DocumentQueuePopup;

/**
 * A listener to manage visibility of code completion popup and document
 * queue popup when keyboard focus on text editor changes.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class EditorPopupFocusListener extends FocusAdapter {

    /**
     * Creates a new instance of {@code EditorPopupFocusListener}.
     */
    public EditorPopupFocusListener() {
    }

    /**
     * This is called when the editor loses the keyboard focus. If the code
     * completion popup is currently visible, it hides the same. And similarly
     * if document queue popup is currently visible, it hides the same.
     * @param e The focus event.
     */
    @Override
    public void focusLost(FocusEvent e) {
        if (EditorBase.getContext().getEditorProperties().
                getCurrentDocument() == null) {
            return;
        }
        // Hide code completion popup.
        CodeCompletionPopup popup =
                CodeCompletionManager.getInstance().
                getPopup(EditorBase.getContext().getEditorProperties().
                getCurrentDocument());
        if (popup != null && popup.isVisible()) {
            popup.hide();
        }
        // Hide  document queue popup.
        DocumentQueuePopup docQueuePopup = DocumentQueuePopup.getInstance();
        if (docQueuePopup != null && docQueuePopup.isVisible()) {
            docQueuePopup.hide();
        }
    }
}
