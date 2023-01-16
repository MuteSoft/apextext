/*
 * PaletteManager.java 
 * Created on 16 Jan, 2010, 9:19:06 PM
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apex.base.data.IDocumentType;
import org.apex.base.data.PaletteItem;

/**
 * The palette manager.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class PaletteManager {

    /**
     * List of palettes for all document types.
     */
    private static Map<IDocumentType, List<PaletteItem>> palettes = new HashMap<IDocumentType, List<PaletteItem>>(
            5);

    /**
     * Creates a default instance of {@code PaletteManager}.
     */
    public PaletteManager() {
    }

    /**
     * Returns list of palette items for a given document type.
     * @param docType A document type.
     * @return A list of palette items.
     */
    public static List<PaletteItem> getPaletteItems(IDocumentType docType) {
        return PaletteManager.palettes.get(docType);
    }
}
