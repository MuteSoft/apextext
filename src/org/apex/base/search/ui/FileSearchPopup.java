/*
 * FileSearchPopup.java 
 * Created on 19 Oct, 2009, 10:34:06 PM
 *
 * Copyright (C) 2009 Mrityunjoy Saha
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
package org.apex.base.search.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;
import org.apex.base.component.ApexLabel;
import org.apex.base.component.EditorListCellRenderer;
import org.apex.base.component.ListPopup;

/**
 * A popup to display result of dovcument search.
 * @author mrityunjoy_saha
 * @version 1.2
 * @since Apex 1.2
 */
public class FileSearchPopup extends ListPopup {

    /**
     * A reference to itself.
     */
    protected static FileSearchPopup fileSearchPopup;
    /**
     * A list of documents.
     */
    private Vector<ApexLabel> docList;

    /**
     * Creates a new instance of {@code FileSearchPopup}.
     */
    public FileSearchPopup() {
        this.popupList.setCellRenderer(new EditorListCellRenderer());
        popupPane.setPreferredSize(new Dimension(165, 300));
    }

    /**
     * Returns a singleton instance of this class.
     * @return A singleton instance.
     */
    public static FileSearchPopup getInstance() {
        if (fileSearchPopup == null) {
            fileSearchPopup = new FileSearchPopup();
        }
        return fileSearchPopup;
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

    @Override
    public void selectionTask(ListSelectionEvent event) {
    }

    @Override
    public void mouseClickedTask(MouseEvent event) {
    }

    @Override
    protected Vector<ApexLabel> populatePopupData() {
        return docList;
    }

    /**
     * Sets list data to be displayed in file search popup.
     * @param docList The document list as labels.
     */
    public void setPopupListData(Vector<ApexLabel> docList) {
        this.docList = docList;
    }

    @Override
    protected Component getParent() {
        return getContext().getEditorComponents().getFrame();
    }

    @Override
    protected int getXLocation() {
        return (int) getContext().getEditorComponents().getToolBar().
                getSearchBox().getSearchField().
                getLocationOnScreen().getX();
    }

    @Override
    protected int getYLocation() {
        return (int) getContext().getEditorComponents().getToolBar().
                getSearchBox().getSearchField().
                getLocationOnScreen().getY() + 22;
    }
}
