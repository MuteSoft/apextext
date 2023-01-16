/*
 * ListPopup.java
 * Created on 1 July, 2007, 9:17 AM
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;

/**
 * A base class for popup windows to display list of items. A {@code ListPopup} tcan be used to display
 * a document queue for quick navigation between documents.
 * It follows singletone pattern. 
 * <p>
 * <strong>Warning: </strong>Subclasses of this class must follow singletone pattern.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class ListPopup extends BasicPopup {

    /**
     * A {@code ApexList} view having capability to display list of items.
     */
    protected ApexList popupList;
    /**
     * The scrollable view of the {@code ApexList} to be displayed in popup window.
     */
    protected ApexScrollPane popupPane;
    /**
     * ApexList of items to be displayed in popup window.
     */
    protected Vector<?> data;

    /**
     * Initializes the poup factory and the {@code ApexList} view.
     */
    @Override
    protected void init() {
        super.init();
        popupList = new ApexList() {

            @Override
            public void task(ListSelectionEvent event) {
                selectionTask(event);
            }
        };
        popupList.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    mouseClickedTask(e);
                }
            }
        });
        popupList.setFocusable(false);
        popupList.setForeground(getDisplayTextColor());
        if (isDisplayTextBold()) {
            popupList.setFont(popupList.getFont().deriveFont(Font.BOLD));
        }
        popupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        popupList.setLayoutOrientation(getLayoutOrientation());
        popupList.setVisibleRowCount(getVisibleRowCount());
        popupPane =
                new ApexScrollPane(popupList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        popupList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        popupPane.setMaximumSize(new Dimension(200,200));
    }

    /**
     * Returns the visible item count in the list.
     * @return The visible item count in the list.
     */
    protected int getVisibleRowCount() {
        return 10;
    }

    /**
     * Retruns the list cell orientation.
     * @return The cell orientattion. One of HORIZONTAL_WRAP, VERTICAL  and VERTICAL_WRAP.
     */
    protected int getLayoutOrientation() {
        return ApexList.VERTICAL;
    }

    /**
     *
     * Reloads data to be displayed in popup window. It hides the current popup window
     * if it is visible and makes the popup window visible with modified data.
     * @param modifiedData The modofied list of items.
     */
    public void reload(Vector<?> modifiedData) {
        if (isVisible()) {
            this.hide();
            if (modifiedData != null && modifiedData.size() > 0) {
                this.show(modifiedData);
            }
        }
    }

    /**
     * Invoked when a item in list is selected.
     * @param event The list selection event.
     */
    public abstract void selectionTask(ListSelectionEvent event);

    /**
     * Invoked when mouse is clicked on a list item.
     * @param event The mouse event.
     */
    public abstract void mouseClickedTask(MouseEvent event);

    /**
     * Returns the {@code ApexList} view which is displayed in popup window.
     * @return The {@code ApexList} view which is displayed in popup window.
     */
    public ApexList getPopupList() {
        return popupList;
    }

    public ApexScrollPane getComponent() {
        return popupPane;
    }

    /**
     * Populates list of elements to be displayed in popup window.
     * @return The list of elements to be displayed in popup window.
     */
    protected abstract Vector<?> populatePopupData();

    /**
     * Returns the list of elements displayed in poup window.
     * @return The list of elements displayed in poup window.
     */
    public Vector<?> getData() {
        return data;
    }

    /**
     * Shows the popup window. Sets the popup visibility indicator boolean value to {@code true}.
     * <p>
     * It calls {@link #populatePopupData() } to populate list data and then displays 
     * the list data in popup window. If no data is available, 'No Suggestion' entry is shown
     * in popup.
     */
    @Override
    public void show() {
        this.data = populatePopupData();
        show(data);
    }

    /**
     * Shows the popup window. Sets the popup visibility indicator boolean value to {@code true}.
     * It displays the list data in popup window. If no data is available, 'No Suggestion' entry is shown
     * in popup.
     * @param data The list of elements to be displayed in popup window.
     */
    private void show(Vector<?> data) {
        if (data == null) {
            data = new Vector<String>(1);
        }
        if (data.size() == 0) {
            //data.add("No Suggestion");
        }
        popupList.setListData(data);
        popupList.setSelectedIndex(0);
        /**
         * Decide the display location dynamically. As of now assumed full screen
         * and hardcoded the location.
         */
        popup = factory.getPopup(getParent(), getComponent(), getXLocation(),
                getYLocation());
        popup.show();
        this.visible = true;

    }

    /**
     * Returns the foreground color of display text in {@code ApexList}.
     * @return The foreground color of display text.
     */
    protected Color getDisplayTextColor() {
        return Color.BLACK;
    }

    /**
     * Returns whether display text in {@code ApexList} is shown as bold.
     * @return {@code true} if text in list displayed as bold.
     */
    protected boolean isDisplayTextBold() {
        return false;
    }

    /**
     * Hides the popup window. Sets the popup visibility indicator boolean value to {@code false}.
     * Also it clears the list of items.
     */
    @Override
    public void hide() {
        if (popup != null) {
            popup.hide();
            this.visible = false;
            if (this.data != null) {
                this.data.clear();
            }
        }
    }
}
