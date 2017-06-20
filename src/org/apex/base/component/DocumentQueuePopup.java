/*
 * DocumentQueuePopup.java
 * Created on 1 July, 2007, 10:12 AM
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
package org.apex.base.component;

import org.apex.base.constant.ColorConstants;
import org.apex.base.data.AbstractDocument;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;

/**
 * A list type popup window to display the document queue. A document queue 
 * contains a list of documents opened in editor; sorted by documents last access time. 
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentQueuePopup extends ListPopup {

    /**
     * A reference to itself.
     */
    protected static DocumentQueuePopup docQueuePopup;

    /**
     * Creates a new instance of {@code DocumentQueuePopup}.
     */
    public DocumentQueuePopup() {
    }

    /**
     * Returns a singleton instance of this class.
     * @return A singleton instance.
     */
    public static DocumentQueuePopup getInstance() {
        if (docQueuePopup == null) {
            docQueuePopup = new DocumentQueuePopup();
        }
        return docQueuePopup;
    }

    public void selectionTask(ListSelectionEvent event) {
    }

    protected Component getParent() {
        return getContext().getEditorComponents().getFrame();
    }

    public void mouseClickedTask(MouseEvent event) {
    }

    /**
     * Hides the popup window. Sets the popup visibility indicator boolean value to {@code false}.
     * <p>
     * The data in document queue is kept as it is.
     */
    @Override
    public void hide() {
        if (popup != null) {
            popup.hide();
            this.visible = false;
        }
    }

    @Override
    public void show() {
        super.show();
        if (this.popupList != null && this.popupList.getModel().getSize() >= 2) {
            this.popupList.setSelectedIndex(1);
        }
    }

    protected int getXLocation() {
        return (int) getContext().getEditorComponents().getFrame().
                getLocationOnScreen().getX()
                + 300;
    }

    protected int getYLocation() {
        return (int) getContext().getEditorComponents().getFrame().
                getLocationOnScreen().getY()
                + 300;
    }

    @Override
    protected int getLayoutOrientation() {
        return ApexList.VERTICAL_WRAP;
    }

    /**
     * Populates list data from elemnts in document queue.
     * @return The list of elements to be displayed in popup window.
     */
    protected Vector<String> populatePopupData() {
        Vector<String> docList = this.getContext().getEditorProperties().
                getDocsQueue().getEntries();
        return docList;
    }

    /**
     * Returns the selected file in document queue.
     *   @return The selected file in document queue.
     */
    public AbstractDocument getSelectedFile() {
        int selectedIndex = this.popupList.getSelectedIndex();        
        if (selectedIndex > 0 && selectedIndex < this.popupList.getModel().
                getSize()) {
            String filePath =
                    this.getContext().getEditorProperties().
                    getDocsQueue().getAdditionalInfoAt(
                    selectedIndex);
            return this.getContext().getEditorProperties().getOpenDocument(
                    filePath);
        }
        return null;
    }

    /**
     * Indicates whether or not navigation required between list items.
     * @return {@code true} if navigation required; otherwise returns {@code false}.
     */
    public boolean isNavigationRequired() {
        AbstractDocument currentFile = this.getContext().getEditorProperties().
                getCurrentDocument();
        AbstractDocument selectedFile = getSelectedFile();
        if (selectedFile == null || currentFile.getAbsolutePath().equals(selectedFile.
                getAbsolutePath())) {
            return false;
        }
        return true;
    }

    @Override
    protected Color getDisplayTextColor() {
        return ColorConstants.DOCUMENT_QUEUE_FOREGROUND_COLOR;
    }

    @Override
    protected boolean isDisplayTextBold() {
        return false;
    }

    @Override
    protected int getVisibleRowCount() {
        return 20;
    }
}
