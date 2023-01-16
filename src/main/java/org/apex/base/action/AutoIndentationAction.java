/*
 * AutoIndentationAction.java 
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

import org.apex.base.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

/**
 * This action performs indentation on text being inserted to editor. 
 * @author Santosh Kumar
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class AutoIndentationAction extends AbstractAction {

    /**
     * Creates a new instance.
     */
    public AutoIndentationAction() {
        super();
    }

    /**
     * Performs left indentation based on previous line indentation information.
     * @param e The generated {@code ActionEvent}.
     * @see AbstractAction#actionPerformed(java.awt.event.ActionEvent) 
     */
    public void actionPerformed(ActionEvent e) {
        JTextComponent component = (JTextComponent) e.getSource();
        Document document = component.getDocument();
        // Return if the editor is not editable.
        if (!component.isEditable()) {
            return;
        }
        try {
            Element root = document.getDefaultRootElement();
            int row = root.getElementIndex(component.getCaretPosition());
            int startOffset = root.getElement(row).getStartOffset();
            int endOffset = root.getElement(row).getEndOffset();
            String text = document.getText(startOffset, endOffset - startOffset - 1);
            String whiteSpace = getLeadingWhiteSpace(text);
            document.insertString(component.getCaretPosition(), '\n' + whiteSpace, null);
        } catch (BadLocationException ble) {
            Logger.logWarning("AutoIndentation failed.", ble);
            try {
                document.insertString(component.getCaretPosition(), "\n", null);
            } catch (BadLocationException blen) {
                Logger.logError("AutoIndentation retry failed.", blen);
            }
        }
    }

    /**
     * Returns leading whitespaces in a given text.
     * @param text The text to be analyzed.
     * @return Leading whitespaces.
     * @see #getLeadingWhiteSpaceWidth(java.lang.String) 
     */
    private String getLeadingWhiteSpace(String text) {
        return text.substring(0, getLeadingWhiteSpaceWidth(text));
    }

    /**
     * Calculates number of whitespaces at the begining in a given text.
     * @param text The text to be analyzed.
     * @return Number of leading whitespaces.
     */
    private int getLeadingWhiteSpaceWidth(String text) {
        int whiteSpace = 0;
        while (whiteSpace < text.length()) {
            char c = text.charAt(whiteSpace);
            if (c == ' ' || c == '\t') {
                whiteSpace++;
            } else {
                break;
            }
        }
        return whiteSpace;
    }
}