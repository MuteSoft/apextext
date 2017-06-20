/*
 * PaletteItemDropListener.java 
 * Created on 16 Jan, 2010, 7:32:26 PM
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

import java.awt.Point;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import javax.swing.SwingUtilities;
import org.apex.base.component.TextEditor;
import org.apex.base.data.PaletteItem;

/**
 * A listener for the drop event of palette items.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class PaletteItemDropListener extends DropTargetAdapter {

    /**
     * Drop target.
     */
    private DropTarget dropTarget;
    /**
     * The text editor.
     */
    private TextEditor editor;

    /**
     * Creates a new instance of {@code PaletteItemDropListener} with specified editor component.
     * @param editor The text editor.
     */
    public PaletteItemDropListener(TextEditor editor) {
        this.editor = editor;
        dropTarget = new DropTarget(editor,
                DnDConstants.ACTION_COPY,
                this, true, null);
    }

    public void drop(DropTargetDropEvent event) {
        try {
            Transferable tr = event.getTransferable();
            PaletteItem paletteItem = (PaletteItem) tr.getTransferData(
                    PaletteItemTransferable.paletteItemFlavor);
            if (event.isDataFlavorSupported(
                    PaletteItemTransferable.paletteItemFlavor)) {
                event.acceptDrop(DnDConstants.ACTION_COPY);
                Point dropPoint = event.getLocation();                
                SwingUtilities.convertPointFromScreen(dropPoint, this.editor);                
                this.editor.replaceSelection(paletteItem.toString());
                event.dropComplete(true);
                return;
            }
            event.rejectDrop();
        } catch (Exception e) {
            e.printStackTrace();
            event.rejectDrop();
        }
    }
}
