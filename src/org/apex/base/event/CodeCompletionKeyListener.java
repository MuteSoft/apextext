/*
 * CodeCompletionKeyListener.java
 * Created on 1 July, 2007, 1:24 PM
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

import org.apex.base.codecompletion.CodeCompletionManager;
import org.apex.base.codecompletion.CodeCompletionPopup;
import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.logging.Logger;
import org.apex.base.util.DocumentData;
import org.apex.base.component.ApexList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

/**
 * A listener to provide bidirectional navigation facility to user within code
 * completion popup list data when code completion popup is visible. 
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CodeCompletionKeyListener extends KeyAdapter {

    /**
     * Creates a new instance of {@code CodeCompletionKeyListener}.
     */
    public CodeCompletionKeyListener() {
    }

    /**
     * When code completion popup is visible this method provides navigation facility to user within
     * popup list data. Also it hides the popup window if 'Escape' key is pressed. The navigation keys
     * and their uses:
     * <ul>
     *  <li> VK_UP : To select the previous entry.
     *  <li> VK_DOWN : To select the next entry.
     *  <li> VK_ENTER : To choose an entry.
     *  <li> VK_ESCAPE : To hide the popup.
     * </ul>
     * @param e The key event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        CodeCompletionPopup popup =
                CodeCompletionManager.getInstance().
                getPopup(getContext().getEditorProperties().
                getCurrentDocument());
        if (popup != null && popup.isVisible()) {
            ApexList popupList = popup.getPopupList();
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    popup.hide();
                    break;
                case KeyEvent.VK_UP:
                    if (popupList.getSelectedIndex() == 0) {
                        popupList.setSelectedIndex(popupList.getModel().
                                getSize() -
                                1);
                        popupList.ensureIndexIsVisible(popupList.getModel().
                                getSize() - 1);
                    } else {
                        popupList.setSelectedIndex(popupList.getSelectedIndex() -
                                1);
                        popupList.ensureIndexIsVisible(popupList.getSelectedIndex() +
                                1);
                    }
                    e.consume();
                    break;
                case KeyEvent.VK_DOWN:
                    if (popupList.getSelectedIndex() ==
                            popupList.getModel().
                            getSize() - 1) {
                        popupList.setSelectedIndex(0);
                        popupList.ensureIndexIsVisible(0);
                    } else {
                        popupList.setSelectedIndex(popupList.getSelectedIndex() +
                                1);
                        popupList.ensureIndexIsVisible(popupList.getSelectedIndex() +
                                1);
                    }
                    e.consume();
                    break;
                case KeyEvent.VK_ENTER:
                    DefaultStyledDocument doc =
                            getContext().getEditorProperties().
                            getCurrentDocument().
                            getDocument();
                    try {
                        // Actually need to replace text what is already typed.
                        doc.insertString(getContext().getEditorProperties().
                                getCurrentDocument().getEditor().
                                getCaretPosition(),
                                popup.getFormattedSelectedValue(),
                                new SimpleAttributeSet());
                    } catch (BadLocationException ex) {
                        Logger.logWarning(
                                "Failed to insert code completion text.", ex);
                    }
                    e.consume();
                    popup.hide();
            }
        }
    }

    /**
     * When code completion popup is visible and a key is typed, it filters the current data
     * of code completion popup based on modified previous word (relative to
     * current caret position) and reloads the filtered data in popup.
     * @param e The key event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == ' ') {
            return;
        }
        CodeCompletionPopup popup =
                CodeCompletionManager.getInstance().
                getPopup(getContext().getEditorProperties().
                getCurrentDocument());
        if (popup != null &&
                popup.isVisible()) {
            Vector<?> modifiedData = filterCodeCompletionData(popup);
            // TODO Do not reload the entire stuff. Filter based on previous word
            popup.reload(modifiedData);
        }
    }

    /**
     * Calculates and returns the previous word of current caret position.
     * @return Previous word of current caret position.
     */
    private String getPreviousWord() {
        return DocumentData.getPreviousWord(getContext().getEditorProperties().
                getCurrentDocument().getEditor(), getContext().
                getEditorProperties().
                getCurrentDocument().getDocument());
    }

    /**
     * It filters the data in code completion popup based on previous word
     * of current caret position.
     * @param popup The code completion popup.
     * @return The filtered list of data to be displayed in code completion popup.
     */
    private Vector<?> filterCodeCompletionData(CodeCompletionPopup popup) {
        Vector<String> modifiedData = new Vector<String>();
        String prevWord = getPreviousWord();
        Vector<?> currentData = popup.getData();
        for (int iCount = 0; iCount < currentData.size(); iCount++) {
            if (((String)currentData.get(iCount)).startsWith(prevWord)) {
                modifiedData.add((String)currentData.get(iCount));
            }
        }
        return modifiedData;
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }
}
