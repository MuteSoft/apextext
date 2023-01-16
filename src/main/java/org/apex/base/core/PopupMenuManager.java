/*
 * PopupMenuManager.java
 * Created on May 31, 2007, 4:16 PM
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

/**
 * A factory of popup menu-bar.
 * <p>
 * Based on popup type requested it returns an appropriate implementation of popup
 * menu-bar.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class PopupMenuManager {

    /**
     * Indicator for edit area popup menu-bar.
     */
    public static final int EDIT_AREA_POPUP = 0;
    /**
     * Indicator for document tab popup menu-bar.
     */
    public static final int DOCUMENT_TAB_POPUP = 1;
    /**
     * The edit area popup menu-bar instance.
     */
    private static PopupMenuBar editAreaPopupMenuBar;
    /**
     * The document tab popup menu-bar instance.
     */
    private static PopupMenuBar docTabPopupMenuBar;

    /**
     * Based on popup type requested it returns an appropriate implementation of popup
     * menu-bar.
     * @return  An appropriate implementation of popup menu-bar.
     * @param popupType Requested popup type.
     * @see #EDIT_AREA_POPUP
     * @see #DOCUMENT_TAB_POPUP 
     */
    public static PopupMenuBar getInstance(int popupType) {
        PopupMenuBar popupMenuBar = null;
        switch (popupType) {
            case EDIT_AREA_POPUP:
                if (editAreaPopupMenuBar == null) {
                    editAreaPopupMenuBar = new EditAreaPopupMenuBar();                    
                }
                popupMenuBar = editAreaPopupMenuBar;
                break;
            case DOCUMENT_TAB_POPUP:
                if (docTabPopupMenuBar == null) {
                    docTabPopupMenuBar = new DocumentTabPopupMenuBar();                    
                }
                popupMenuBar = docTabPopupMenuBar;
                break;
        }
        return popupMenuBar;
    }

    /**
     * Constructs a new instance of {@code PopupMenuManager}.
     */
    private PopupMenuManager() {
    }
}

