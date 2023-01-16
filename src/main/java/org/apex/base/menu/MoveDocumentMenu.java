/*
 * MoveDocumentMenu
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
import java.io.File;
import javax.swing.JFileChooser;

/**
 * A menu target to move a document from its current directory to another directory in file system. 
 * <p>
 * Temporary documents can not be moved as these documents don't exist in file system.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MoveDocumentMenu extends BasicUIMenu {

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Creates a directory chooser where user can choose the target directory.
     * <p>
     * In directory chooser window files will not be displayed.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void createUI(InputParams in, OutputParams out) {
        JFileChooser fileChooser =
                new JFileChooser();
        fileChooser.setDragEnabled(false);
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Add file Filters        
        int choice = fileChooser.showOpenDialog(null);
        if (choice == JFileChooser.APPROVE_OPTION) {
            in.put("MOVE_TARGET_DIR", fileChooser.getSelectedFile());
        }
    }

    /**
     * Moves the document to a target directory chosen by user.
     * <p>
     * It copies the document to target location and then deletes the source.
     * <p>
     * Before moving, it saves the current document in case it is unsaved.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    protected void postProcess(InputParams in, OutputParams out) {
        File moveTarget = (File) in.get("MOVE_TARGET_DIR");
        if (moveTarget != null) {
            moveDocument(moveTarget, in, out);
        }
    }

    /**
     * Determines the target document name from target directory chosen by user and current
     * document name.
     * @param moveTargetDir Target directory chosen by user.
     * @param currentDocument The currently displayed document.
     * @return The target file.
     */
    private File getTargetDocument(File moveTargetDir,
            AbstractDocument currentDocument) {
        return new File(moveTargetDir.getAbsolutePath(),
                currentDocument.getName());
    }

    /**
     * Moves the document to a target directory.
     * <p>
     * It copies the document to target location and then deletes the source.
     * <p>
     * Before moving, it saves the current document in case it is unsaved.
     * @param moveTargetDir The target file.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @SuppressWarnings("unchecked")
    private void moveDocument(File moveTargetDir, InputParams in,
            OutputParams out) {
        AbstractDocument document = getContext().getEditorProperties().
                getCurrentDocument();
        in = in == null
                ? new InputParams()
                : in;
        out = out == null
                ? new OutputParams()
                : out;
        if (!document.isSaved()) {
            ((SaveFileMenu) MenuManager.getMenuById(MenuConstants.SAVE_FILE)).
                    doSave(
                    document,
                    in, out);
        }
        in.clear();
        out.clear();
        File targetDocument = getTargetDocument(moveTargetDir, document);
        SaveAsFileMenu saveAsMenu =
                (SaveAsFileMenu) MenuManager.getMenuById(
                MenuConstants.SAVE_FILE_AS);
        if (saveAsMenu.doSaveAs(targetDocument, in, out) && !document.
                getAbsolutePath().
                equalsIgnoreCase(targetDocument.getAbsolutePath())) {
            // Delete the original file.
            document.delete();
        }
    }
}
