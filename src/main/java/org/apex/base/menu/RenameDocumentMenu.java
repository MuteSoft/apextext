/*
 * RenameDocumentMenu.java
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
package org.apex.base.menu;

import java.awt.Point;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.ui.RenameDocumentView;
import org.apex.base.ui.text.RenameDocumentModel;

/**
 * Creates 'Rename document dialog and provides facility to rename a document.
 *
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class RenameDocumentMenu extends SimplePanelDialogMenu {

    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(350, 100);
    /**
     * The data model for rename document.
     */
    private RenameDocumentModel renameDocModel = new RenameDocumentModel();

    /**
     * Creates a new instance of {@code RenameDocumentMenu}. Creates the user interface
     * for 'Rename document dialog.
     */
    public RenameDocumentMenu() {
        panel = new RenameDocumentView(renameDocModel);
    }

    public void preProcess(InputParams in, OutputParams out) {
        AbstractDocument currentDocument = getContext().getEditorProperties().
                getCurrentDocument();
        if (currentDocument != null && !currentDocument.isTemporary()) {
            ((RenameDocumentView) this.panel).initialize(currentDocument);
        }
    }

    /**
     * Sets the container dialog window to view and then make the dialog visible.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    public void postProcess(InputParams in, OutputParams out) {
        ((RenameDocumentView) this.panel).setContainerWindow(dialog);
        super.postProcess(in, out);
    }

    @Override
    public Point windowSize() {
        return WINDOW_SIZE;
    }

    @Override
    public boolean isModal() {
        return true;
    }

    public String getTitle() {
        return "Rename Document";
    }
}
