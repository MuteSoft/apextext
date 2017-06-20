/*
 * CaretListenerImpl.java
 * Created on March 20, 2007, 11:49 PM
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

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.core.ActionManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.EditorContext;
import org.apex.base.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.text.Caret;
import javax.swing.text.Element;

/**
 * A caret listener to update the editor status bar (caret position info) and cut, copy menus
 * enable status when caret moves in editor programmatically or moved by user.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CaretListenerImpl implements javax.swing.event.CaretListener {

    /**
     * Creates a new instance of {@code CaretListenerImpl}.
     */
    public CaretListenerImpl() {
    }

    /**
     * Updates the editor status bar (caret position info) and cut, copy menus enable status
     * when caret position is changed.
     * @param caretEvent The caret event.
     */
    public void caretUpdate(CaretEvent caretEvent) {        
        updateStatusBar(caretEvent.getDot(), caretEvent.getMark());
    }

    /**
     * Updates the editor status bar (caret position info) and cut, copy menus enable status
     * using specified {@code Caret}.
     * @param caret The caret.
     */
    public static void updateStatusBar(Caret caret) {
        updateStatusBar(caret.getDot(), caret.getMark());
    }

    /**
     * Updates the editor status bar (caret position info) and cut, copy menus enable status
     * using specified caret position.
     * @param dot The dot value of caret position.
     * @param mark The mark value of caret position.
     */
    public static void updateStatusBar(final int dot, final int mark) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                Element root = null;
                try {
                    if (getContext().getEditorProperties().
                            getCurrentDocument() == null) {
                        return;
                    }
                    root = getContext().getEditorProperties().
                            getCurrentDocument().getDocument().
                            getDefaultRootElement();
                    int row = root.getElementIndex(dot);

                    // @TODO Resolve column number issue when TAB key pressed.                    
                    int column = dot - root.getElement(row).
                            getStartOffset();

                    getContext().getEditorComponents().getStatusBar().
                            setCaretPositionInfo((row + 1) + "  :  " + (column +
                            1));
                } catch (Exception e) {
                    Logger.logWarning(
                            "Failed to update line and column numbers in status bar.", e);
                    getContext().getEditorComponents().getStatusBar().
                            setCaretPositionInfo("1 : 1");
                }
                // Introduce selection listener and remove most of the operations
                if (dot == mark) {
                    getContext().getEditorComponents().getStatusBar().
                            setGeneralInfo("");
                    updateMenus(false);
                } else {
                    int start = Math.min(dot, mark);
                    int end = Math.max(dot, mark);
                    int startLineNumber = 0;
                    int endLineNumber = 0;
                    try {
                        if (root != null) {
                            startLineNumber = root.getElementIndex(start);
                            endLineNumber = root.getElementIndex(end);
                        }
                    } catch (Exception ex) {
                        Logger.logWarning(
                                "Failed to update text selection info in status bar.", ex);
                    }
                    if (startLineNumber != endLineNumber) {
                        getContext().getEditorComponents().getStatusBar().
                                setGeneralInfo("Selected " + ((endLineNumber -
                                startLineNumber) + 1) +
                                " line(s)");
                    } else {
                        getContext().getEditorComponents().getStatusBar().
                                setGeneralInfo("Selected " + (end -
                                start) +
                                " character(s)");
                    }
                    updateMenus(true);
                }
            }
        });
    }

    /**
     * When caret moves (while selecting or de-selecting text) updates
     * cut and copy enable status.
     * @param enabled A boolean that indicates whether to enable or disable
     *                cut and copy menus.
     */
    private static void updateMenus(boolean enabled) {
        // For Cut, Paste.
        ActionManager.setActionEnabled(MenuConstants.COPY, enabled);
        ActionManager.setActionEnabled(MenuConstants.CUT, enabled);
        // Update the current file object too
        AbstractDocument file = getContext().getEditorProperties().
                getCurrentDocument();
        if (file != null) {
            file.setCanCopy(enabled);
            file.setCanCut(enabled);
        }
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected static EditorContext getContext() {
        return EditorBase.getContext();
    }
}
