/*
 * CodeCompletionPopup.java
 * Created on 1 July, 2007, 1:17 PM
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
package org.apex.base.codecompletion;

import org.apex.base.component.ListPopup;
import org.apex.base.component.TextEditor;
import org.apex.base.logging.Logger;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 * The base class for code completion popups for different document types. 
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class CodeCompletionPopup extends ListPopup {

    /**
     * Creates a new instance of {@code CodeCompletionPopup}.
     */
    public CodeCompletionPopup() {
    }

    public void selectionTask(ListSelectionEvent event) {       
    }

    protected Component getParent() {
        return getContext().getEditorProperties().getCurrentDocument().getEditor();
    }

    public void mouseClickedTask(MouseEvent event) {
        if (this.isVisible()) {
            DefaultStyledDocument doc =
                    getContext().getEditorProperties().getCurrentDocument().
                    getDocument();
            try {
                doc.insertString(getContext().getEditorProperties().
                        getCurrentDocument().
                        getEditor().getCaretPosition(),
                        getFormattedSelectedValue(),
                        null);
            } catch (BadLocationException ex) {
                Logger.logWarning("Failed to perform code completion.", ex);
            }
            this.hide();
        }
    }

    /**
     * Returns formatted selected value. Subclasses should override this method to
     * put language specific code insertion. The code to be inserted can be different
     * from the code displayed in list. However, they are related and mapped.
     * @return Formatted selected value.
     */
    public String getFormattedSelectedValue() {
        return (String) this.getPopupList().getSelectedValue();
    }

    protected int getXLocation() {
        TextEditor editArea =
                getContext().getEditorProperties().getCurrentDocument().
                getEditor();
        int caretPosition = editArea.getCaretPosition();
        try {
            return (int) (editArea.getLocationOnScreen().getX() + editArea.
                    modelToView(caretPosition).
                    getX());
        } catch (BadLocationException ex) {
            Logger.logWarning(
                    "Failed to calculate X location of code completion popup.",
                    ex);
            return 0;
        }
    }

    protected int getYLocation() {
        TextEditor editArea =
                getContext().getEditorProperties().getCurrentDocument().
                getEditor();
        int caretPosition = editArea.getCaretPosition();
        try {
            return (int) (editArea.getLocationOnScreen().getY() + editArea.
                    modelToView(caretPosition).
                    getY() + editArea.getFontMetrics(editArea.getFont()).
                    getHeight());
        } catch (BadLocationException ex) {
            Logger.logWarning(
                    "Failed to calculate Y location of code completion popup.",
                    ex);
            return 0;
        }
    }

    /**
     * Populates list of code completion elements to be displayed in popup window.
     * @return The list of code completion elements to be displayed in popup window.
     */
    protected abstract Vector<String> populateCodeCompletionData();

    protected Vector<String> populatePopupData() {
        return populateCodeCompletionData();
    }
}
