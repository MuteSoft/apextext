/*
 * DeleteLineAction.java
 * Created on March 21, 2007, 9:55 PM
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

import org.apex.base.component.TextEditor;
import org.apex.base.data.MenuNode;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import org.apex.base.logging.Logger;

/**
 * This action deletes text contained by current line of editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DeleteLineAction extends CustomAction {

    /**
     * Creates a new instance of {@code DeleteLineAction} according to
     * given menu node.
     * @param menu Node represents an editor menu.
     * @see MenuNode
     */
    public DeleteLineAction(MenuNode menu) {
        super(menu);
    }

    /**
     * Deletes the current line from editor with reference to current cursor position.
     * @param actionEvent The action event.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        TextEditor target = getTextComponent(actionEvent);
        boolean beep = true;
        if (target != null) {
            Action selectLine = target.getDefaultAction("select-line");
            selectLine.actionPerformed(actionEvent);
            int offs = 0;
            int len = 0;
            try {
                final int start = target.getSelectionStart();
                final int end = target.getSelectionEnd() + 1;
                offs = Math.min(start, end);
                len = Math.abs(end - start);
                if (offs >= 0) {
                    target.getDocument().remove(offs, len);
                    beep = false;
                }
            } catch (BadLocationException ex) {
                try {
                    target.getDocument().remove(offs, len - 1);
                    beep = false;
                } catch (BadLocationException ex1) {
                    Logger.logError("Failed to delete current line.", ex1);
                }
            }
        }
        if (beep) {
            UIManager.getLookAndFeel().provideErrorFeedback(target);
        }
    }
}
