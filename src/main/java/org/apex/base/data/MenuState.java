/*
 * MenuState.java 
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
package org.apex.base.data;

import org.apex.base.constant.CommonConstants;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.ActionManager;

/**
 * Holds status of different menus for a document. Every document is having its own
 * {@code MenuState} at any given time.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MenuState {

    /**
     * A boolean that indicates whether or not undo operation can be done on the document.
     */
    private boolean canUndo;
    /**
     * A boolean that indicates whether or not redo operation can be done on the document.
     */
    private boolean canRedo;
    /**
     * The current split status of the document.
     */
    private int splitStatus;
    /**
     * A boolean that indicates whether or not line number display area should be
     * displayed for the document.
     */
    private boolean viewLineNumber;
    /**
     * A boolean that indicates whether or not the document is saved.
     */
    private boolean saved;
    /**
     * A boolean that indicates whether or not the user can cut content of the document.
     */
    private boolean canCut;
    /**
     * A boolean that indicates whether or not the user can copy content of the document.
     */
    private boolean canCopy;

    /**
     * Constructs a new instance of {@code MenuState}.
     */
    public MenuState() {
    }

    /**
     * Initializes states of menu for a document.
     * @param context The editor context.
     */
    public void init(EditorContext context) {
        this.viewLineNumber = context.getConfiguration().getGeneralConfig().
                getGeneral().isViewLineNumber();
        // Update the menu state as well        
        this.splitStatus = CommonConstants.UNSPLIT;
        this.saved = true;
    }

    /**
     * Returns the split status of the document.
     * @return The split status.
     * @see CommonConstants#UNSPLIT
     * @see CommonConstants#HORIZONTAL_SPLIT
     * @see CommonConstants#VERTICAL_SPLIT
     * @see #setSplitStatus(int) 
     */
    public int getSplitStatus() {
        return splitStatus;
    }

    /**
     * Sets the split status of the document. 
     * @param splitStatus The split status.
     * @see #getSplitStatus()
     */
    public void setSplitStatus(int splitStatus) {
        this.splitStatus = splitStatus;
    }

    /**
     * Returns the boolean indicating whether line number display area is displayed.
     * @return {@code true} if line number display area is displayed; otherwise
     *                returns {@code false}.
     */
    public boolean isViewLineNumber() {
        return viewLineNumber;
    }

    /**
     * Sets the specified boolean to indicate whether or not line number display area
     * should be displayed.
     * @param viewLineNumber Line number display indicator.
     */
    public void setViewLineNumber(boolean viewLineNumber) {
        this.viewLineNumber = viewLineNumber;
    }

    /**
     * Returns the boolean indicating whether the document is saved in editor.
     * @return {@code true} if the document is saved in editor; otherwise
     *                returns {@code false}.
     */
    public boolean isSaved() {
        return saved;
    }

    /**
     * Sets the specified boolean to indicate whether or not the document should be marked
     * as saved.
     * @param saved The save indicator.
     */
    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    /**
     * Returns human readable text representation of this object.
     * @return The text representation of this object.
     */
    @Override
    public String toString() {
        return "Menu State [canUndo: " + canUndo + ", canRedo: " + canRedo +
                ",splitStatus: " + splitStatus + ", viewLineNumber: " +
                viewLineNumber + ",saved: " + saved + ",canCut: " + canCut +
                ", canCopy: " + canCopy + "]";
    }

    /**
     * Returns the boolean indicating whether undo can be done on content of
     * the document.
     * @return {@code true} if the document is undoable; otherwise
     *                returns {@code false}.
     */
    public boolean canUndo() {
        return canUndo;
    }

    /**
     * Sets the specified boolean to indicate whether or not document should be
     * undoable.
     * @param canUndo Indicates whether or not document is undoable.
     */
    public void setCanUndo(boolean canUndo) {
        this.canUndo = canUndo;
    }

    /**
     * Returns the boolean indicating whether redo can be done on content of
     * the document.
     * @return {@code true} if the document is redoable; otherwise
     *                returns {@code false}.
     */
    public boolean canRedo() {
        return canRedo;
    }

    /**
     * Sets the specified boolean to indicate whether or not document should be
     * redoable.
     * @param canRedo Indicates whether or not document is redoable.
     */
    public void setCanRedo(boolean canRedo) {
        this.canRedo = canRedo;
    }

    /**
     * Returns the boolean indicating whether user can cut content of
     * the document.
     * @return {@code true} if user can cut content of the document; otherwise
     *                returns {@code false}.
     */
    public boolean canCut() {
        return canCut;
    }

    /**
     * Sets the boolean to indicate whether or not user can cut content of the
     * document.
     * @param canCut Indicates whether or not document content can be cut.
     */
    public void setCanCut(boolean canCut) {
        this.canCut = canCut;
    }

    /**
     * Returns the boolean indicating whether user can copy content of
     * the document.
     * @return {@code true} if user can copy content of the document; otherwise
     *                returns {@code false}.
     */
    public boolean canCopy() {
        return canCopy;
    }

    /**
     * Sets the boolean to indicate whether or not user can copy content of the
     * document.
     * @param canCopy Indicates whether or not document content can be copied.
     */
    public void setCanCopy(boolean canCopy) {
        this.canCopy = canCopy;
    }
}