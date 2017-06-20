/*
 * GoToLineEventHandler.java
 * Created on 4 June, 2007, 2:43 AM
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

import org.apex.base.component.TextEditor;
import org.apex.base.data.UIDataModel;
import org.apex.base.logging.Logger;
import org.apex.base.ui.text.GoToLineModel;
import java.awt.Toolkit;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;

/**
 * An event handler to navigate to a specific line in the editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GoToLineEventHandler extends UIEventHandler {

    /**
     * Creates a new instance of {@code GoToLineEventHandler}.
     */
    public GoToLineEventHandler() {
    }

    /**
     * It gets the target line number from goto line data model and then
     * navigates to that line. Also it makes the navigated line visible on screen.
     * @param inModel The goto line data model.
     * 
     */
    public void execute(UIDataModel inModel) {
        GoToLineModel dataModel = (GoToLineModel) inModel;
        int lineNumber = dataModel.getLineNumber();
        if (lineNumber == 0) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        Element root = getContext().getEditorProperties().getCurrentDocument().getDocument().
                getDefaultRootElement();
        TextEditor editArea = getContext().getEditorProperties().getCurrentDocument().getEditor();
        try {
            if (lineNumber > root.getElementCount()) {
                lineNumber = root.getElementCount();
            }
            // Element index starts from 0
            lineNumber = lineNumber - 1;
            int startOffset = root.getElement(lineNumber).getStartOffset();
            editArea.scrollRectToVisible(editArea.modelToView(startOffset));
            //Move the caret also.
            editArea.setCaretPosition(startOffset);
        } catch (BadLocationException ex) {
            Logger.logError("Failed to move the caret to line number " + dataModel.getLineNumber(),
                    ex);
        } catch (NullPointerException ex) {
            Logger.logError("Failed to move the caret to line number " + dataModel.getLineNumber(),
                    ex);
        }
    }
}