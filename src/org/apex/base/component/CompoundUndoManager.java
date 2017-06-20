/*
 * CompoundUndoManager.java 
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
package org.apex.base.component;

import org.apex.base.action.RedoTextAction;
import org.apex.base.action.UndoTextAction;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.ActionManager;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

/**
 * The undo manager for text editor. It manages a list of {@code UndoableEdit}s,
 * providing a way to undo or redo the appropriate edits.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 * @see UndoManager
 */
public class CompoundUndoManager extends UndoManager implements DocumentListener {

    /**
     * The compound edit.
     */
    private CompoundEdit edit;
    /**
     * The text editor.
     */
    private JTextComponent editArea;
    /**
     * Last offset.
     */
    private int lastOffset;
    /**
     * Last length.
     */
    private int lastLength;

    /**
     * Creates a new instance of {@code CompoundUndoManager} with given
     * text editor.
     * @param editArea The text editor.
     */
    public CompoundUndoManager(JTextComponent editArea) {
        this.editArea = editArea;
        this.editArea.getDocument().addUndoableEditListener(this);
    }

    /**
     * Redoes the appropriate edits.
     * @throws javax.swing.undo.CannotRedoException If there are no edits
     *                to be redone.
     */
    @Override
    public synchronized void redo() throws CannotRedoException {
        this.editArea.getDocument().addDocumentListener(this);
        super.redo();
        this.editArea.getDocument().removeDocumentListener(this);
    }

    /**
     * Redoes the appropriate edits.
     * @throws javax.swing.undo.CannotUndoException If there are no edits
     *                to be undone.
     */
    @Override
    public synchronized void undo() throws CannotUndoException {
        this.editArea.getDocument().addDocumentListener(this);
        super.undo();
        this.editArea.getDocument().removeDocumentListener(this);
    }

    /**
     * It adds an compount edit to undo manager.
     * Also it checks for incremental edit.
     * @param e The undoable edit event.
     */
    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        AbstractDocument.DefaultDocumentEvent event =
                (AbstractDocument.DefaultDocumentEvent) e.getEdit();
        UndoTextAction undoAction =
                (UndoTextAction) ActionManager.getActionById(MenuConstants.UNDO);
        RedoTextAction redoAction =
                (RedoTextAction) ActionManager.getActionById(MenuConstants.REDO);
        if (undoAction != null) {
            undoAction.update();
        }
        if (redoAction != null) {
            redoAction.update();
        }
        // Start new one
        if (this.edit == null) {
            this.edit = startCompoundEdit(e.getEdit());
            lastLength = this.editArea.getDocument().getLength();
            return;
        }
        if (event.getType().equals(DocumentEvent.EventType.CHANGE)) {
            this.edit.addEdit(e.getEdit());
            return;
        }
        // Check for incremental edit or backspace.
        // The change in caret position and document length should be either 1 or -1.
        int offSetChange = this.editArea.getCaretPosition() - lastOffset;
        int lengthChange = this.editArea.getDocument().getLength() - lastLength;

        if (Math.abs(offSetChange) == 1 && Math.abs(lengthChange) == 1) {
            this.edit.addEdit(e.getEdit());
            lastOffset = this.editArea.getCaretPosition();
            lastLength = this.editArea.getDocument().getLength();
            return;
        }
        // If it reaches here means no incremental edit, end previous edit and start a new one
        this.edit.end();
        this.edit = startCompoundEdit(e.getEdit());

    }

    /**
     * Updates the current caret position.
     * @param e The document event.
     */
    public void insertUpdate(final DocumentEvent e) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                int offset = e.getOffset() + e.getLength();
                offset = Math.min(offset, editArea.getDocument().
                        getLength());
                editArea.setCaretPosition(offset);
            }
        });
    }

    /**
     * Updates the current caret position.
     * @param e The document event.
     */
    public void removeUpdate(DocumentEvent e) {
        editArea.setCaretPosition(e.getOffset());
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     * @param e The document event.
     */
    public void changedUpdate(DocumentEvent e) {
    }

    /**
     * Each compound edit will store a group of related incremental edits.
     * Each character types or backspaced is an incremental edit
     * @param editHappened The undoable edit.
     * @return The compound edit.
     */
    private CompoundEdit startCompoundEdit(UndoableEdit editHappened) {
        // Track caret position and document information of this compound edit
        this.lastOffset = this.editArea.getCaretPosition();
        this.lastLength = this.editArea.getDocument().getLength();
        // The compound edit stores incremental edits
        this.edit = new EditorCompoundEdit();
        this.edit.addEdit(editHappened);
        // The compound edit is added to the undo manager. All incremental edits
        // stored in the compound edit will be undone/redone at once.
        this.addEdit(this.edit);
        return this.edit;
    }

    /**
     * The compound edit object. It assembles little
     *  UndoableEdits into great big ones.
     */
    public class EditorCompoundEdit extends CompoundEdit {

        /**
         * Indicates whether or not the edit is in progress.
         * @return A boolean that indicates whether or not the edit is in progress.
         */
        @Override
        public boolean isInProgress() {
            return false;
        }

        /**
         * Undoes the edit and nullifies this edit.
         * @throws javax.swing.undo.CannotUndoException
         */
        @Override
        public void undo() throws CannotUndoException {
            // End this edit so future edits dont get absorbed by this edit.

            if (edit != null) {
                edit.end();
            }
            super.undo();
            edit = null;
        }
    }
}
