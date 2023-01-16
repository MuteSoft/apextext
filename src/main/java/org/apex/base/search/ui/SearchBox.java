/*
 * SearchBox.java 
 * Created on 19 Oct, 2009, 11:07:20 PM
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.apex.base.component.ApexLabel;
import org.apex.base.component.ApexList;
import org.apex.base.component.ApexTextField;
import org.apex.base.core.EditorBase;
import org.apex.base.search.SearchFile;

/**
 * A search box to search files. This search box can search files by their name
 * and display the matched files in a popup.
 * <p>
 * The search box can be pinned anywhere in the editor.
 * @author mrityunjoy_saha
 * @version 1.1
 * @since Apex 1.2
 */
// @TODO up and down Key navigation does not work. Also, look at focus issues.
public class SearchBox implements KeyListener {

    /**
     * The search box.
     */
    private ApexTextField searchBox;
    /**
     * A file search helper.
     */
    private SearchFile searchFile;
    /**
     * The popup to display the list of filtered files.
     */
    private FileSearchPopup searchPopup = null;
    /**
     * Default search text.
     */
    private static final String DEFAULT_SEARCH_TEXT = "Search Files...";

    /**
     * Creates a new instance of {@code SearchBox}.
     */
    public SearchBox() {
        this.searchBox = new ApexTextField(20);
        this.searchBox.setFocusable(false);
        this.searchBox.setText(DEFAULT_SEARCH_TEXT);
        this.searchBox.setForeground(Color.GRAY);
        this.searchFile = new SearchFile();
        this.searchBox.setMaximumSize(new Dimension(200, 20));
        this.searchBox.setPreferredSize(new Dimension(200, 15));
        this.searchBox.setEditable(true);
        this.searchBox.addKeyListener(this);
        this.searchBox.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                searchBox.setFocusable(true);
                searchBox.requestFocusInWindow();
            }
        });
        this.searchBox.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {
                if (!getPopup().isVisible()) {
                    searchBox.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (!getPopup().isVisible()) {
                    searchBox.setText(DEFAULT_SEARCH_TEXT);
                    searchBox.setFocusable(false);
                }
            }
        });
    }

    /**
     * Returns the search box.
     * @return The search box.
     */
    public ApexTextField getSearchField() {
        return this.searchBox;
    }

    /**
     * Returns the associated popup.
     * @return The search popup.
     */
    private FileSearchPopup getPopup() {
        if (searchPopup == null) {
            searchPopup = FileSearchPopup.getInstance();
        }
        return searchPopup;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    // @TODO UP, DOWN keys not working on search box. When pressed twice UP
    // and DOWN keys working as expected. Find the place where key event is
    // getting consumed.
    public void keyPressed(KeyEvent evt) {
        FileSearchPopup popup = getPopup();
        ApexList popupList = popup.getPopupList();
        if (!evt.isActionKey()) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                performGoToFile();
            } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                hidePopup();
            } else {
                if (popup.isVisible()) {
                    popupList.setModel(this.searchFile.getFileListModel(this.searchFile.
                            filterFileList(this.searchBox.getText())));
                } else {
                    popup.setPopupListData(this.searchFile.filterFileList(this.searchBox.
                            getText()));
                    if (popup != null) {
                        popup.show();
                    }
                }
                if (popupList.getModel().getSize() > 0) {
                    popupList.setSelectedIndex(0);
                }
            }
        } else {
            if (!(popupList.getModel().getSize() > 1)) {
                return;
            }
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (popupList.getSelectedIndex() - 1 < 0) {
                        popupList.setSelectedIndex(popupList.getModel().
                                getSize() - 1);
                    } else {
                        popupList.setSelectedIndex(
                                popupList.getSelectedIndex() - 1);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (popupList.getSelectedIndex() + 1
                            >= popupList.getModel().getSize()) {
                        popupList.setSelectedIndex(0);
                    } else {
                        popupList.setSelectedIndex(
                                popupList.getSelectedIndex() + 1);
                    }
                    break;
            }
        }
    }

    /**
     * Navigates to selected file.
     */
    private void performGoToFile() {
        FileSearchPopup popup = getPopup();
        if (popup != null) {
            this.searchFile.getModel().setSelectedFileName(((ApexLabel) popup.
                    getPopupList().
                    getSelectedValue()).getText());
            this.searchFile.getModel().setSelectedFilePath(((ApexLabel) popup.
                    getPopupList().
                    getSelectedValue()).getToolTipText());
            this.searchFile.performGoToFile();
            hidePopup();
            EditorBase.getContext().getEditorProperties().getCurrentDocument().
                    getEditor().
                    requestFocusInWindow();
        }
    }

    /**
     * Hides the popup.
     */
    private void hidePopup() {
        FileSearchPopup popup = getPopup();
        if (popup != null && popup.isVisible()) {
            popup.hide();
            this.searchBox.setFocusable(false);
        }
    }
}
