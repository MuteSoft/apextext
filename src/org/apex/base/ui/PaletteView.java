/*
 * PaletteView.java 
 * Created on 16 Jan, 2010, 9:34:30 PM
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
package org.apex.base.ui;

import java.awt.Color;
import java.util.List;
import javax.swing.BorderFactory;
import org.apex.base.component.ApexPanel;
import org.apex.base.core.EditorBase;
import org.apex.base.data.IDocumentType;
import org.apex.base.data.PaletteItem;
import org.apex.base.dnd.PaletteManager;

/**
 * The palette window.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class PaletteView extends ApexPanel {

    /**
     * Creates a new palette window.
     */
    public PaletteView() {
        this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.GRAY));
        paintPalette();
    }

    /**
     * Paints palette window.
     */
    public void paintPalette() {
        IDocumentType currentDocumentType = EditorBase.getContext().
                getEditorProperties().getCurrentDocument().getDocumentType();
        paintPalettes(PaletteManager.getPaletteItems(currentDocumentType));
    }

    /**
     * Paints list of items in the palette.
     * @param palettes List of items to be painted in the palette.
     */
    private void paintPalettes(List<PaletteItem> palettes) {
    }
}
