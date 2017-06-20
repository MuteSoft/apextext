/*
 * IndentTextAction.java 
 * Created on 25 Nov, 2009, 10:39:39 PM
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
package org.apex.base.action;

import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import org.apex.base.component.TextEditor;
import org.apex.base.core.EditorBase;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.MenuNode;

/**
 * Indents a block of text.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
// @TODO - One compound edit.
public class IndentTextAction extends CustomAction {

    /**
     * Creates an instance of {@code IndentTextAction} with given menu node.
     * @param menu A menu node.
     */
    public IndentTextAction(MenuNode menu) {
        super(menu);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Element root = null;
        boolean beep = true;
        AbstractDocument currentDocument = EditorBase.getContext().getEditorProperties().
                getCurrentDocument();
        if (currentDocument == null) {
            return;
        }       
        TextEditor target = getTextComponent(actionEvent);
        try {
            final int start = target.getSelectionStart();
            final int end = target.getSelectionEnd();
            int actualStart = Math.min(start, end);
            int actualEnd = Math.max(start, end);
            root = currentDocument.getDocument().
                    getDefaultRootElement();
            if (actualStart >= 0 && actualEnd >= 0) {
                int startRow = root.getElementIndex(actualStart);
                int endRow = root.getElementIndex(actualEnd);
                // Commenting in every line.
                for (; startRow <= endRow; startRow++) {
                    currentDocument.getDocument().insertString(root.getElement(startRow).
                            getStartOffset(), "\t", null);
                }
                beep = false;
            }
        } catch (BadLocationException ex) {
            // Log the error.
        }
        if (beep) {
            UIManager.getLookAndFeel().provideErrorFeedback(target);
        }
    }
}
