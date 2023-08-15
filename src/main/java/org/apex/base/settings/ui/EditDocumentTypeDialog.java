/*
 * EditDocumentTypeDialog.java 
 * Created on 19 Oct, 2009, 4:19:27 PM
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
package org.apex.base.settings.ui;

import java.awt.Point;
import javax.swing.JList;
import org.apex.base.data.IDocumentType;
import org.apex.base.settings.DocumentTypesConfiguration;

/**
 * Dialog window for editing document types.
 * @author mrityunjoy_saha
 * @version 1.1
 * @since Apex 1.2
 */
public class EditDocumentTypeDialog extends SimplePanelSettingsDialog {

    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(400, 340);

    /**
     * Creates a new instance of {@code EditDocumentTypeDialog}.
     * @param docTypesConfig Document types configuration.
     * @param selectedDocumentType Selected document type.
     * @param docTypesList A list where document types are displayed.
     */
    public EditDocumentTypeDialog(DocumentTypesConfiguration docTypesConfig,
            IDocumentType selectedDocumentType,
            JList docTypesList) {
        this.panel = new EditDocumentType(docTypesConfig, selectedDocumentType,
                docTypesList);
    }

    @Override
    public Point windowSize() {
        return WINDOW_SIZE;
    }

    @Override
    public void makeDialogVisible() {
        ((EditDocumentType) panel).setContainerWindow(this.dialog);
        super.makeDialogVisible();
    }

    public String getTitle() {
        return "Edit Document Type";
    }
}
