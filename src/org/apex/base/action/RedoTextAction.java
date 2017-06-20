/*
 * RedoTextAction.java
 * Created on February 20, 2007, 9:17 PM
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
package org.apex.base.action;

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.MenuNode;
import java.awt.event.ActionEvent;
import javax.swing.undo.CannotRedoException;
import org.apex.base.core.ActionManager;
import org.apex.base.logging.Logger;

/**
 * An action to support redo operation in ediotr.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class RedoTextAction extends CustomAction {

    /**
     * Creates a new instance of {@code RedoTextAction}.
     * @param menu The menu node.
     * @see MenuNode
     */
    public RedoTextAction(MenuNode menu) {
        super(menu);
    }

    /**
     * The operation to perform when this action is triggered.
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            EditorBase.getContext().getEditorProperties().getCurrentDocument().getUndoManager().redo();
        } catch (CannotRedoException ex) {
            Logger.logWarning("Cannot redo.", ex);
        }
        update();
        UndoTextAction undoAction =
                (UndoTextAction) ActionManager.getActionById(MenuConstants.UNDO);
        if (undoAction != null) {
            undoAction.update();
        }
    }

    /**
     * Updates this action and also updates the currently displayed document's redo-state as well.
     */
    public void update() {
        AbstractDocument file = EditorBase.getContext().getEditorProperties().getCurrentDocument();
        if (file == null) {
            return;
        }
        boolean canRedo = file.getUndoManager().canRedo();
        if (file.canRedo() != canRedo) {
            setEnabled(canRedo);
            file.setCanRedo(canRedo);
        }
    }
}
