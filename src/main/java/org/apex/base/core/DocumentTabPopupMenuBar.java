/*
 * DocumentTabPopupMenuBar.java
 * Created on May 31, 2007, 1:37 PM
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

import org.apex.base.data.EditorContext;
import org.apex.base.data.MenuNode;
import org.apex.base.component.ApexMenuItem;
import java.util.Vector;

/**
 * A popup menubar appears when right clicked on a document tab.
 * <p>
 * These menus are configurable and share configuration with editor main menus.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentTabPopupMenuBar extends PopupMenuBar {

    /**
     * Creates a new instance of DocumentTabPopupMenuBar.
     */
    public DocumentTabPopupMenuBar() {
        super();
    }

    /**
     * Based on configuration found in CoreMenu it creates
     * menus for document tab popup menu-bar.
     * @param context The editor context.
     */
    protected void createPopupMenu(EditorContext context) {
        Vector<String> docTabMenus = context.getCoreMenus().
                getDocTabMenus();
      addMenus(docTabMenus);
    }
}
