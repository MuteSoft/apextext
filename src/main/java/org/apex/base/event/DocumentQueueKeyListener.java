/*
 * DocumentQueueKeyListener.java
 * Created on 5 July, 2007, 1:01 AM
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

import org.apex.base.component.DocumentQueuePopup;
import org.apex.base.core.EditorBase;
import org.apex.base.util.DocumentSelection;
import org.apex.base.component.ApexList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

/**
 * A listener to provide unidirectional navigation facility to user within document queue popup list data
 * when document queue popup is visible.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class DocumentQueueKeyListener extends KeyAdapter {

    /**
     * Creates a new instance of {@code DocumentQueueKeyListener}.
     */
    public DocumentQueueKeyListener() {
    }

    /**
     * When document queue popup is visible this method provides navigation
     * facility to user within popup list data. The navigation keys and their uses:
     * <ul>
     *  <li> VK_TAB : To select the next entry.
     *  <li> CTRL key down and VK_TAB : Show the popup.
     * </ul>
     * @param e The key event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        DocumentQueuePopup popup = DocumentQueuePopup.getInstance();
        if (popup.isVisible()) {
            ApexList popupList = popup.getPopupList();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_TAB:
                    // Fix for bug id 2866442 (sourceforge.net)
                    if (e.isShiftDown()) {
                        // Backward.
                        if (popupList.getSelectedIndex() == 0) {
                            popupList.setSelectedIndex(popupList.getModel().getSize() - 1);
                        } else {
                            popupList.setSelectedIndex(popupList.getSelectedIndex() -
                                    1);
                        }
                    } else {
                        // Forward
                        if (popupList.getSelectedIndex() ==
                                popupList.getModel().getSize() - 1) {
                            popupList.setSelectedIndex(0);
                        } else {
                            popupList.setSelectedIndex(popupList.getSelectedIndex() +
                                    1);
                        }
                    }
                    e.consume();
                    break;
            }
        } else {
            if (e.isControlDown()) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_TAB:
                        popup.show();
                        break;
                }
            }
        }
    }

    /**
     * When document queue is visible on screen and CTRL key is released
     * selected document is displayed in editor and hides the document queue
     * popup.
     * @param e The key event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (KeyStroke.getKeyStrokeForEvent(e).toString().
                equalsIgnoreCase("released CONTROL")) {
            DocumentQueuePopup popup = DocumentQueuePopup.getInstance();
            if (popup != null && popup.isVisible()) {
                // Before hiding navigate to selected file
                // If same file chosen dont do anything                
                if (popup.isNavigationRequired()) {
                    if (popup.getSelectedFile() != null) {
                        DocumentSelection.setDocumentSelectedIndex(EditorBase.getContext(),
                                popup.getSelectedFile().
                                getIndex());
                    }
                }
                popup.hide();
            }
        }
    }
}