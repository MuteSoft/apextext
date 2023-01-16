/*
 * DeleteFileMenu.java
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

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import javax.swing.JOptionPane;

/**
 * A menu target to delete a file from file system.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DeleteFileMenu extends UILessMenu {

    /**
     * Deletes a file from file system.
     * <p>
     * Shows a confirmation dialog before deleting the file. And also
     * it closes the document from editor.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    protected void execute(InputParams in, OutputParams out) {
        deleteFile(in, out);
    }

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    @Override
    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Deletes a file from file system.
     * <p>
     * Shows a confirmation dialog before deleting the file. And also
     * it closes the document from editor.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @SuppressWarnings("unchecked")
    private void deleteFile(InputParams in, OutputParams out) {
        AbstractDocument currentDocument = getContext().getEditorProperties().getCurrentDocument();
        // check whether there is no current document
        if (currentDocument == null) {
            return;
        }
        // Show the warning whether user is sure that the currently displyed file should be deleted.
        int selectedOption = MenuMessageManager.showConfirmMessage(getContext().getEditorComponents().
                getFrame(),
                1001, "FILENAME=" +
                currentDocument.getName());
        switch (selectedOption) {
            case JOptionPane.OK_OPTION:
                InputParams input = new InputParams();
                input.put(CloseFileMenu.CLOSE_WARNING_TO_BE_DISPLYED, false);
                MenuManager.getMenuById(MenuConstants.CLOSE_FILE).
                        processMenu(input,
                        new OutputParams());
                // Check whether this is temporary file which does not exist in file system                
                if (!currentDocument.isTemporary()) {
                    currentDocument.delete();
                }
                break;
            case JOptionPane.CANCEL_OPTION:
                // Do Nothing.
                break;
            default:
            // Do Nothing. Not expecting to reach here                
            }
    }
}
