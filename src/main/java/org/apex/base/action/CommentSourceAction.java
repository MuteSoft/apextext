/*
 * CommentSourceAction.java
 * Created on 24 Nov, 2009, 1:26:21 AM
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
import org.apex.base.data.IDocumentType;
import org.apex.base.data.MenuNode;
import org.apex.base.util.StringUtil;

/**
 * Uncomments a block of source code.
 * @author mrityunjoy_saha
 * @version 1.1
 * @since Apex 1.2
 */
// @TODO - One compound edit.
public class CommentSourceAction extends CustomAction {

    /**
     * Creates an instance of {@code CommentSourceAction} with given menu node.
     * @param menu A menu node.
     */
    public CommentSourceAction(MenuNode menu) {
        super(menu);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Element root = null;
        boolean beep = true;
        AbstractDocument currentDocument = EditorBase.getContext().
                getEditorProperties().
                getCurrentDocument();
        if (currentDocument == null) {
            return;
        }
        IDocumentType docType = currentDocument.getDocumentType();
        if (StringUtil.isNullOrEmpty(docType.getStartCommentToken()) && StringUtil.
                isNullOrEmpty(docType.getEndCommentToken())) {
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
                String startCommentToken = docType.getStartCommentToken();
                String endCommentToken = docType.getEndCommentToken();
                if (startCommentToken.equalsIgnoreCase(endCommentToken) || StringUtil.
                        isNullOrEmpty(endCommentToken)) {
                    // Commenting in every line.
                    for (; startRow <= endRow; startRow++) {
                        currentDocument.getDocument().insertString(root.
                                getElement(startRow).
                                getStartOffset(), startCommentToken, null);
                    }
                } else {
                    // Commenting block - start and end lines only.
                    currentDocument.getDocument().insertString(root.getElement(
                            startRow).
                            getStartOffset(), startCommentToken, null);
                    currentDocument.getDocument().insertString(root.getElement(
                            endRow).
                            getEndOffset() - 1, endCommentToken, null);
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
