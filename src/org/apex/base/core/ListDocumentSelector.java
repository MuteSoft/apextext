/*
 * ListDocumentSelector.java 
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

import java.awt.Component;
import java.awt.Insets;
import javax.swing.JList;
import org.apex.base.component.EditorListCellRenderer;
import org.apex.base.component.WindowTitleBar;
import org.apex.base.constant.ColorConstants;
import org.apex.base.constant.MenuConstants;
import org.apex.base.data.InputParams;
import org.apex.base.data.DocumentSelectorListModel;
import org.apex.base.data.OutputParams;
import org.apex.base.data.SortedListModel;
import org.apex.base.data.SortedListModel.SortOrder;
import org.apex.base.dnd.FileAndTextTransferHandler;
import org.apex.base.event.DocumentListSelectionListener;
import org.apex.base.component.ApexLabel;
import org.apex.base.component.ApexList;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.constant.FontConstants;

/**
 * The left hand document selector of the editor. It displays a 'list view' of
 * documents.
 * <p>
 * A {@code SortedListModel} is used internally to sort elements of document
 * selector in ascending order.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class ListDocumentSelector extends DocumentSelector {

    /**
     * The component to display documents in a list. 
     */
    private ApexList docList;
    /**
     * The unsorted document list model.
     */
    private DocumentSelectorListModel documentListModel;
    /**
     * The sorted document list model.
     */
    private SortedListModel sortedDocumentListModel;

    /**
     * Creates a new instance of {@code ListDocumentSelector}.
     */
    public ListDocumentSelector() {
    }

    /**
     * Creates the document selector.
     * <p>
     * Creates a display area containing a {@code WindowTitleBar} and
     * {@code ApexList}.
     */
    protected void createDocumentSelector() {
        this.setLayout(new BorderLayout());
        this.documentListModel = new DocumentSelectorListModel();
        this.sortedDocumentListModel = new SortedListModel(
                getDocumentListModel(),
                SortOrder.ASCENDING);
        this.docList = new ApexList(this.getSortedDocumentListModel(),
                ColorConstants.DOCUMENT_SELECTOR_FOREGROUND_COLOR,
                ColorConstants.DOCUMENT_SELECTOR_BACKGROUND_COLOR) {

            @Override
            public Insets getInsets() {
                return EditorKeyConstants.LIST_DOCUMENT_SELECTOR_MARGIN;
            }
        };
        this.getDocList().removeListSelectionListener(getDocList());
        this.getDocList().addListSelectionListener(new DocumentListSelectionListener(
                getContext()));
        this.getDocList().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.getDocList().setFocusable(false);
        this.getDocList().setCellRenderer(new EditorListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list,
                    Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                Component comp = super.getListCellRendererComponent(list, value,
                        index, isSelected,
                        cellHasFocus);
                if (comp instanceof JLabel) {
                    JLabel label = (JLabel) comp;
                    if (isSelected) {
                        label.setFont(FontConstants.DOCUMENT_SELECTOR_FONT);
                    } else {
                        label.setFont(null);
                    }
                }
                return comp;
            }
        });
        //this.docList.setFont(new IdeaFont("Verdana", IdeaFont.PLAIN, 11));        
        // For Drag and Drop Support
        this.getDocList().setDragEnabled(true);
        this.getDocList().setTransferHandler(new FileAndTextTransferHandler());

        JScrollPane docSelectorPane = new JScrollPane(getDocList(),
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Create the title bar.
        JPanel titleBar = new WindowTitleBar("Files") {

            protected void executeOnClick(ActionEvent e) {
                ActionManager.setActionSelected(
                        MenuConstants.VIEW_DOCUMENT_SELECTOR, false);
                MenuManager.getMenuById(MenuConstants.VIEW_DOCUMENT_SELECTOR).
                        processMenu(new InputParams(),
                        new OutputParams());
            }
        };
        this.add(titleBar, BorderLayout.NORTH);
        this.add(docSelectorPane);
    }

    @Override
    public String getSelectedDocumentFullName() {
        String docName = null;
        int selectedIndex = this.getDocList().getSelectedIndex();
        if (selectedIndex >= 0) {
            int indexAtDocumentModel =
                    this.getSortedDocumentListModel().toUnsortedModelIndex(
                    selectedIndex);
            docName =
                    this.getDocumentListModel().getAdditionalInfoAt(
                    indexAtDocumentModel);
        }
        return docName;
    }

    @Override
    public String getSelectedDocumentDisplayName() {
        String docName = null;
        int selectedIndex = this.getDocList().getSelectedIndex();
        if (selectedIndex >= 0) {
            int indexAtDocumentModel =
                    this.getSortedDocumentListModel().toUnsortedModelIndex(
                    selectedIndex);
            docName =
                    (String) this.getDocumentListModel().getElementAt(
                    indexAtDocumentModel);
        }
        return docName;
    }

    @Override
    public Vector<String> getDocumentFullNames() {
        Vector<String> result = new Vector<String>();

        for (int iCount = 0; iCount < this.getDocumentListModel().size();
                iCount++) {
            result.add(this.getDocumentListModel().getAdditionalInfoAt(iCount));
        }
        return result;
    }

    @Override
    public Vector<String> getDocumentDisplayNames() {
        Vector<String> result = new Vector<String>();

        for (int iCount = 0; iCount < this.getDocumentListModel().size();
                iCount++) {
            result.add((String) this.getDocumentListModel().elementAt(iCount));
        }
        return result;
    }

    @Override
    public Vector<String> getSortedDocumentFullNames() {
        Vector<String> result = new Vector<String>();

        for (int iCount = 0; iCount < this.getSortedDocumentListModel().size();
                iCount++) {
            int unsortedIndex =
                    this.getSortedDocumentListModel().toUnsortedModelIndex(
                    iCount);
            result.add(this.getDocumentListModel().getAdditionalInfoAt(
                    unsortedIndex));
        }
        return result;
    }

    @Override
    public Vector<String> getSortedDocumentDisplayNames() {
        Vector<String> result = new Vector<String>();

        for (int iCount = 0; iCount < this.getSortedDocumentListModel().size();
                iCount++) {
            result.add(((ApexLabel) this.getSortedDocumentListModel().
                    getElementAt(
                    iCount)).getText());
        }
        return result;
    }

    @Override
    public int getDocumentListSize() {
        return this.getDocumentListModel().size();
    }

    @Override
    public void addDocument(String name, String path) {
        this.getDocumentListModel().addElement(name, path);
    }

    @Override
    public void removeDocumentAt(int documentIndex) {
        this.getDocumentListModel().removeElementAt(documentIndex);
    }

    @Override
    public void updateDocumentAt(int documentIndex, String name,
            String path) {
        this.getDocumentListModel().updateElementAt(documentIndex, name, path);
    }

    @Override
    public void selectDocumentAt(int documentIndex) {
        int sortedIndex =
                this.getSortedDocumentListModel().toSortedModelIndex(
                documentIndex);
        this.getDocList().setSelectedIndex(sortedIndex);
    }

    @Override
    public void removeDocuments() {
        this.getDocumentListModel().removeAllElements();
    }

    /**
     * Returns the {@code ApexList} component used to display document names.
     * @return The {@code ApexList} component used to display document names.
     */
    public ApexList getDocList() {
        return this.docList;
    }

    /**
     * Returns the unsorted document model.
     * @return The unsorted document model.
     */
    public DocumentSelectorListModel getDocumentListModel() {
        return this.documentListModel;
    }

    /**
     * Returns the sorted document model.
     * @return The sorted document model.
     */
    public SortedListModel getSortedDocumentListModel() {
        return this.sortedDocumentListModel;
    }
}
