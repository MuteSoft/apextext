/*
 * RenameDocumentEventHandler.java
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
package org.apex.base.event;

import org.apex.base.common.FlowStopException;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.data.UIDataModel;
import org.apex.base.menu.SaveAsFileMenu;
import org.apex.base.menu.SaveFileMenu;
import org.apex.base.ui.text.RenameDocumentModel;
import java.io.File;

/**
 * An event handler to rename a document.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class RenameDocumentEventHandler extends UIEventHandler {

    /**
     * Renames the document to a new target name. Document's absolute path
     * remains same. It saves the document (if currently unsaved) before renaming.
     * @param dataModel The rename document model.
     * @throws FlowStopException
     */
    @Override
    @SuppressWarnings("unchecked")
    public void execute(UIDataModel dataModel) throws FlowStopException {
        AbstractDocument document = getContext().getEditorProperties().
                getCurrentDocument();
        InputParams input = new InputParams();
        OutputParams output = new OutputParams();
        if (!document.isSaved()) {
            ((SaveFileMenu) MenuManager.getMenuById(MenuConstants.SAVE_FILE)).
                    doSave(document,
                    input, output);
        }
        input.clear();
        output.clear();
        File targetDocument = getTargetDocument(dataModel, document);
        input.clear();
        SaveAsFileMenu saveAsMenu =
                (SaveAsFileMenu) MenuManager.getMenuById(
                MenuConstants.SAVE_FILE_AS);
        // @TODO Instead of assuming file paths case insensitive use operating system abstraction
        // to determine it.
        if (saveAsMenu.doSaveAs(targetDocument, input, output) && !document.
                getAbsolutePath().
                equalsIgnoreCase(targetDocument.getAbsolutePath())) {
            // Delete the original file.
            document.delete();
        }
    }

    /**
     * Determines the target File from rename document model. 
     * @param dataModel The rename document model.
     * @param currentDocument The currently displayed document.
     * @return The new target {@code File}.
     */
    protected File getTargetDocument(UIDataModel dataModel,
            AbstractDocument currentDocument) {
        RenameDocumentModel renameDocModel = (RenameDocumentModel) dataModel;
        return new File(currentDocument.getDirectory(),
                renameDocModel.getDocumentName());
    }
}
