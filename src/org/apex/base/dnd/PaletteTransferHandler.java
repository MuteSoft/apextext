/*
 * PaletteTransferHandler.java 
 * Created on 16 Jan, 2010, 5:51:58 PM
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
package org.apex.base.dnd;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * Palette items transfer handler.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class PaletteTransferHandler extends TransferHandler {

    /**
     * It transfers text from given text editor to the given clipboard.
     * @param comp  The component holding the data to be transferred.
     * @param clip  The clipboard to transfer the data into.
     * @param action The transfer action requested; this should
     *                be a value of either {@code COPY} or {@code MOVE}.
     * @throws java.lang.IllegalStateException  If the clipboard is currently unavailable.
     */
    @Override
    public void exportToClipboard(JComponent comp, Clipboard clip, int action)
            throws
            IllegalStateException {        
        StringSelection contents = new StringSelection(comp.getName());
        // this may throw an IllegalStateException,
        // but it will be caught and handled in the
        // action that invoked this method
        clip.setContents(contents, null);


    }

    @Override
    public void exportAsDrag(JComponent comp, InputEvent e, int action) {
        super.exportAsDrag(comp, e, action);       
    }
}
