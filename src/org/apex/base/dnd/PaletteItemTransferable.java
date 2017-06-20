/*
 * PaletteItemTransferable.java
 * Created on 16 Jan, 2010, 7:27:07 PM
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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import org.apex.base.data.PaletteItem;

/**
 * A tranferable palette item.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class PaletteItemTransferable implements Transferable {

    /**
     * A data flavor for palette items.
     */
    public static DataFlavor paletteItemFlavor =
            new DataFlavor(PaletteItem.class, "A Palette Item");
    /**
     * An array of all supported data flavors by the palette.
     */
    protected static DataFlavor[] supportedFlavors = {
        paletteItemFlavor,
        DataFlavor.stringFlavor,};
    /**
     * A palette item.
     */
    private PaletteItem paletteItem;

    /**
     * Creates a new instance of {@code PaletteItemTransferable} with specified palette item.
     * @param paletteItem A palette item.
     */
    public PaletteItemTransferable(PaletteItem paletteItem) {
        this.paletteItem = paletteItem;
    }

    /**
     * Returns an array of transfer data flavors.
     * @return An array of transfer data flavors.
     */
    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        if (flavor.equals(paletteItemFlavor)
                || flavor.equals(DataFlavor.stringFlavor)) {
            return true;
        }
        return false;
    }

    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException {
        if (flavor.equals(paletteItemFlavor)) {
            return paletteItem;
        } else if (flavor.equals(DataFlavor.stringFlavor)) {
            return paletteItem.toString();
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }
}
