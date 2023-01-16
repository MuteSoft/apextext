/*
 * EditDocumentExtensionDialog.java
 * Created on 15 Oct, 2007, 2:24:39 AM
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
package org.apex.base.settings.ui;

import org.apex.base.component.SimplePanelDialog;
import org.apex.base.settings.DocumentTypesConfiguration;
import java.awt.Point;
import javax.swing.JList;
import org.apex.base.data.IDocumentType;

/**
 * A dialog window to display the edit document extension form.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class EditDocumentExtensionDialog extends SimplePanelDialog {

    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(250, 125);

    /**
     * Creates a new instance of {@code EditDocumentExtensionDialog} using specified
     * document types configuration, selected document type and a list where all available extensions 
     * for selected document type are displayed.
     * @param docTypesConfig Document types configuration.
     * @param selectedDocumentType Selected document type.
     * @param extensionsList A list where document extensions are displayed.
     */
    public EditDocumentExtensionDialog(
            DocumentTypesConfiguration docTypesConfig,
            IDocumentType selectedDocumentType, JList extensionsList) {
        panel = new EditDocumentExtension(docTypesConfig, selectedDocumentType,
                extensionsList);
    }

    @Override
    public Point windowSize() {
        return WINDOW_SIZE;
    }

    @Override
    public void makeDialogVisible() {
        ((EditDocumentExtension) panel).setContainerWindow(this.dialog);
        super.makeDialogVisible();
    }

    public String getTitle() {
        return "Edit";
    }
}
