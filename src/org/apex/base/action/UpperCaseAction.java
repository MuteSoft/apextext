/*
 * UpperCaseAction.java
 * Created on March 21, 2007, 1:44 AM
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

import org.apex.base.data.MenuNode;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.apex.base.logging.Logger;

/**
 * Converts the character of content to upper case that follows the
 * current caret position. If selection exists in editor, it converts the
 * selected content to upper case.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class UpperCaseAction extends CustomAction {

    /**
     * Creates a new instance of {@code UpperCaseAction}.
     * @param menu The menu node.
     * @see MenuNode
     */
    public UpperCaseAction(MenuNode menu) {
        super(menu);
    }

    /**
     * The operation to perform when this action is triggered.
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JTextComponent target = getTextComponent(e);
        boolean beep = true;
        if ((target != null) && (target.isEditable())) {
            try {
                Document doc = target.getDocument();
                Caret caret = target.getCaret();
                int dot = caret.getDot();
                int mark = caret.getMark();
                if (dot != mark) {
                    //doc.remove(Math.min(dot, mark), Math.abs(dot - mark));
                    String selectedText = target.getSelectedText();
                    target.replaceSelection(selectedText.toUpperCase());
                    beep = false;
                } else if (dot < doc.getLength()) {
                    int delChars = 1;

                    if (dot < doc.getLength() - 1) {
                        String dotChars = doc.getText(dot, 2);
                        char c0 = dotChars.charAt(0);
                        char c1 = dotChars.charAt(1);

                        if (c0 >= '\uD800' && c0 <= '\uDBFF' &&
                                c1 >= '\uDC00' && c1 <= '\uDFFF') {
                            delChars = 2;
                        }
                    }
                    //doc.remove(dot, delChars);                    
                    target.select(dot, dot + delChars);
                    String selectedText = target.getSelectedText();
                    target.replaceSelection(selectedText.toUpperCase());
                    beep = false;
                }
            } catch (BadLocationException ble) {
                Logger.logError("Failed to convert to upper case.", ble);
            }
        }
        if (beep) {
            UIManager.getLookAndFeel().provideErrorFeedback(target);
        }
    }
}
