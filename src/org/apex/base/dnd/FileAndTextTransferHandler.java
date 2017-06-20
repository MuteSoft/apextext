/*
 * FileAndTextTransferHandler.java
 * Created on 3 July, 2007, 11:20 PM
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
package org.apex.base.dnd;

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.logging.Logger;
import org.apex.base.menu.OpenFileMenu;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.im.InputContext;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 * This class is used to handle the transfer of a {@code java.awt.datatransfer.Transferable}
 * to and from Swing components. The {@code java.awt.datatransfer.Transferable} is used to
 * represent data that is exchanged via a cut, copy, or paste to/from a clipboard.
 * It is also used to drag and drop files from file system to editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class FileAndTextTransferHandler extends TransferHandler {

    /**
     * It obtains data flavors from given {@code Transferable}. If data flavors contain
     * a file flavor then it imports dragged file(s) from file system to editor. In case data
     * flavors conatin a string flavor it pastes the text in the editor.
     * @param comp The component to receive the transfer.
     * @param t The data to import.
     * @return {@code true} if the data was inserted into the component, {@code false} otherwise.
     */
    @Override
    public boolean importData(JComponent comp, Transferable t) {
        if (!canImport(comp, t.getTransferDataFlavors())) {
            return false;
        }
        try {
            if (hasFileFlavor(t.getTransferDataFlavors())) {
                List data = (List) t.getTransferData(
                        DataFlavor.javaFileListFlavor);
                Iterator iterator = data.iterator();
                while (iterator.hasNext()) {
                    File droppedFile = (File) iterator.next();
                    if (droppedFile.isFile()) {
                        // Open the file
                        ((OpenFileMenu) MenuManager.getMenuById(
                                MenuConstants.OPEN_FILE)).openFile(droppedFile.
                                getAbsolutePath());
                    }
                }
                return true;
            } else if (hasStringFlavor(t.getTransferDataFlavors())) {
                DataFlavor flavor = getStringFlavor(t.getTransferDataFlavors());
                if (flavor != null) {
                    InputContext ic = comp.getInputContext();
                    if (ic != null) {
                        ic.endComposition();
                    }
                    String data = (String) t.getTransferData(flavor);
                    ((JTextComponent) comp).replaceSelection(data);
                    return true;
                }
            }
        } catch (UnsupportedFlavorException ufe) {
            Logger.logWarning("Failed to transfer data/file.", ufe);
        } catch (IOException ioe) {
            Logger.logWarning("Failed to transfer data/file.", ioe);
        }
        return false;
    }

    /**
     * If given data flavors contain a string flavor it returns the same; otherwise
     * returns {@code null}.
     * @param flavors The data to import.
     * @return A string flavor.
     */
    private DataFlavor getStringFlavor(DataFlavor[] flavors) {
        if (flavors != null) {
            for (int counter = 0; counter < flavors.length; counter++) {
                if (flavors[counter].equals(DataFlavor.stringFlavor)) {
                    return flavors[counter];
                }
            }
        }
        return null;
    }

    /**
     * Indicates whether a component will accept an import of the given
     * set of data flavors prior to actually attempting to import it.
     * @param comp The component to receive the transfer.
     * @param transferFlavors The data to import.
     * @return {@code true} if the data can be inserted into the component, {@code false} otherwise.
     */
    @Override
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
        if (hasFileFlavor(transferFlavors)) {
            return true;
        }
        if (hasStringFlavor(transferFlavors)) {
            return true;
        }
        return false;
    }

    /**
     * Determines whether {@code javaFileListFlavor} is present in array of given data
     * flavors.
     * @param transferFlavors An array of data flavors.
     * @return {@code true} if array of data flavors contains
     *               {@code javaFileListFlavor}; otherwise returns {@code false}.
     */
    private boolean hasFileFlavor(DataFlavor[] transferFlavors) {
        for (int i = 0; i < transferFlavors.length; i++) {
            if (transferFlavors[i].equals(DataFlavor.javaFileListFlavor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether {@code stringFlavor} is present in array of given data
     * flavors.
     * @param transferFlavors An array of data flavors.
     * @return {@code true} if array of data flavors contains
     *               {@code stringFlavor}; otherwise returns {@code false}.
     */
    private boolean hasStringFlavor(DataFlavor[] transferFlavors) {
        for (int i = 0; i < transferFlavors.length; i++) {
            if (transferFlavors[i].equals(DataFlavor.stringFlavor)) {
                return true;
            }
        }
        return false;
    }

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
        if (comp instanceof JTextComponent) {
            JTextComponent text = (JTextComponent) comp;
            int p0 = text.getSelectionStart();
            int p1 = text.getSelectionEnd();
            if (p0 != p1) {
                try {
                    Document doc = text.getDocument();
                    String srcData = doc.getText(p0, p1 - p0);
                    StringSelection contents = new StringSelection(srcData);

                    // this may throw an IllegalStateException,
                    // but it will be caught and handled in the
                    // action that invoked this method
                    clip.setContents(contents, null);

                    if (action == TransferHandler.MOVE) {
                        doc.remove(p0, p1 - p0);
                    }
                } catch (BadLocationException ble) {
                }
            }
        }
    }
}
