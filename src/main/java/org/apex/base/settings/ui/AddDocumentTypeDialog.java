/*
 * AddDocumentTypeDialog.java 
 * Created on 19 Oct, 2009, 4:16:28 PM
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
import org.apex.base.settings.DocumentTypesConfiguration;

/**
 * A simple dialog to add a new document type.
 * @author mrityunjoy_saha
 * @version 1.1
 * @since Apex 1.2
 */
public class AddDocumentTypeDialog extends SimplePanelSettingsDialog {

    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(470, 370);

    /**
     * Creates a new instance of {@code AddDocumentTypeDialog}.
     * @param docTypesConfig Document types configuration.
     * @param docTypesList A list where document types are displayed.
     */
    public AddDocumentTypeDialog(DocumentTypesConfiguration docTypesConfig,
            JList docTypesList) {
        panel = new AddDocumentType(docTypesConfig, docTypesList);
    }

    @Override
    public Point windowSize() {
        return WINDOW_SIZE;
    }

    @Override
    public void makeDialogVisible() {
        ((AddDocumentType) panel).setContainerWindow(this.dialog);
        super.makeDialogVisible();
    }

    public String getTitle() {
        return "Add Document Type";
    }
}
