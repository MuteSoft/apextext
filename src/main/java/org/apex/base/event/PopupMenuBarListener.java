/*
 * PopupMenuBarListener.java
 * Created on August 3, 2007, 6:07 PM
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

import org.apex.base.core.PopupMenuManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A listener to display popup menu-bar.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class PopupMenuBarListener extends MouseAdapter {

    /**
     * The popup type.
     */
    private int popupType;

    /**
     * Creates a new instance of {@code PopupMenuBarListener} using specified
     * popup type.
     * @param popupType The popup type. It can be either {@code PopupMenuManager.EDIT_AREA_POPUP}
     *              or {@code PopupMenuManager.DOCUMENT_TAB_POPUP}.
     * @see PopupMenuManager#EDIT_AREA_POPUP
     * @see PopupMenuManager#DOCUMENT_TAB_POPUP
     */
    public PopupMenuBarListener(int popupType) {
        this.popupType = popupType;
    }

    /**
     * Displays the popup menu-bar.
     * @param e The mouse event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        display(e);
    }

    /**
     * Displays the popup menu-bar.
     * @param e The mouse event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        display(e);
    }

    /**
     * Displays the popup menu-bar.
     * @param e The mouse event.
     */
    protected void display(MouseEvent e) {
        if (e.isPopupTrigger()) {            
            PopupMenuManager.getInstance(popupType).show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }

    /**
     * Returns the popup type.
     * @return The popup type.
     */
    public int getPopupType() {
        return popupType;
    }
}
