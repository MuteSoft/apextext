/*
 * CodeCompletionAction.java 
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

import org.apex.base.codecompletion.CodeCompletionManager;
import org.apex.base.codecompletion.CodeCompletionPopup;
import org.apex.base.core.EditorBase;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * An action used to support code completion on text editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CodeCompletionAction extends AbstractAction {

    /**
     * Constructs a new instance of {@code CodeCompletionAction}.
     */
    public CodeCompletionAction() {
    }

    /**
     * Based on current document's type, it gets the appropriate type of code
     * completion popup instance and finally makes the popup visible.
     * @param e The action event.
     */
    public void actionPerformed(ActionEvent e) {

        CodeCompletionPopup popup =
                CodeCompletionManager.getInstance().
                getPopup(EditorBase.getContext().getEditorProperties().
                getCurrentDocument());
        if (popup != null) {
            popup.show();
        }
    }
}
