/*
 * PaletteItemDragGestureListener.java 
 * Created on 16 Jan, 2010, 7:37:29 PM
 *
 * Copyright (C) 2010 Mrityunjoy Saha
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
package org.apex.base.dnd;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import org.apex.base.data.PaletteItem;

/**
 * A drag listener for palette item.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class PaletteItemDragGestureListener implements DragGestureListener {

    /**
     * Creates a new instance of {@code PaletteItemDragGestureListener}.
     */
    public PaletteItemDragGestureListener() {
    }

    public void dragGestureRecognized(DragGestureEvent event) {
        Cursor cursor = null;        
        PaletteItem paletteItem = (PaletteItem) event.getComponent();        
        if (event.getDragAction() == DnDConstants.ACTION_COPY) {
            cursor = DragSource.DefaultCopyDrop;
        }
        event.startDrag(cursor, new PaletteItemTransferable(paletteItem));
    }
}
