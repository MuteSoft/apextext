/*
 * PaletteItem.java 
 * Created on 16 Jan, 2010, 7:28:17 PM
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
package org.apex.base.data;

import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import org.apex.base.component.AbstractIconButton;
import org.apex.base.dnd.PaletteItemDragGestureListener;

/**
 * A representation of palette item.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class PaletteItem extends AbstractIconButton {

    /**
     * The dra listener.
     */
    private static final PaletteItemDragGestureListener dragListener = new PaletteItemDragGestureListener();

    /**
     * Creates a new instance of palette item with given text.
     * @param text Text to be displayed in palette item.
     */
    public PaletteItem(String text) {
        this(null, text);
    }

    /**
     * Creates a new instance of palette item.
     * @param iconName The icon name which is to be displayed in palette item
     * @param text Text to be displayed in palette item.
     */
    public PaletteItem(String iconName, String text) {
        super(null);
        this.setText(text);
        this.setToolTipText(text);
        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(this,
                DnDConstants.ACTION_COPY_OR_MOVE, dragListener);
    }

    @Override
    protected void doOnClick(ActionEvent e) {        
    }

    @Override
    public String toString() {
        return getText();
    }
}
