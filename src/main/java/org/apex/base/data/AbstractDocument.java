/*
 * AbstractDocument.java
 * Created on February 11, 2007, 5:38 PM
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

import org.apex.base.component.TextEditor;
import org.apex.base.constant.CommonConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.highlighter.style.DocumentStyle;
import org.apex.base.logging.Logger;
import org.apex.base.util.FileUtil;
import org.apex.base.util.ImageCreator;
import java.awt.Component;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.undo.UndoManager;
import org.apex.base.codecompletion.CodeCompletionPopup;
import org.apex.base.constant.EditorKeyConstants;

/**
 * A base class for all type of documents which can be viewed and edited in the application.
 * <p>
 * A document is assigned a unique index. Status of 'document state' sensitive menus are maintained
 * at document level. A document can be a temporary one or persistent in file system. Each document
 * is having an associated editor and line number display area.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class AbstractDocument extends File implements DocumentClass, Cloneable {

    /**
     * The associated text editor.
     */
    private TextEditor editor;
    /**
     * Tab component which represents the view of this document in document window.
     */
    private Component component;
    /**
     * Unsorted Index of this file. 
     */
    private int index;
    /**
     * The time when this file was last saved. 
     */
    private long lastSaved;
    /**
     * The associated undo manager for this document.
     */
    private UndoManager undoManager;
    /**
     * The line number display area.
     */
    private JComponent lineNumberArea;
    /**
     * Holds status of 'document sensitive' menus.
     */
    private MenuState menuState = new MenuState();
    /**
     * Indicates whether this document is currently displayed.
     */
    private boolean currentyDisplayed;
    /**
     * A flag to indicate whether this document is temporary and does not exist in file system.
     */
    private boolean temporary;
    /**
     * The document type.
     */
    private IDocumentType docType;
    /**
     * The default document icon.
     */
    private static ImageIcon DOCUMENT_ICON = ImageCreator.createImageIcon(
            EditorBase.class, EditorKeyConstants.DEFAULT_FILE_ICON);
    /**
     * A flag to identify whether document read or write operation is in progress.
     */
    private boolean readWriteInProgress;

    /**
     * Constructs a document with given name.
     * @param name The name of document.
     * @param docType The document type.
     */
    public AbstractDocument(String name, IDocumentType docType) {
        this(null, name, docType);
    }

    /**
     * Constructs a document with given file system path and name.
     * @param path The path of document in file system.
     * @param name The name of document.
     * @param docType The document type.
     */
    public AbstractDocument(String path, String name, IDocumentType docType) {
        super(path, name);
        this.docType = docType;
    }

    /**
     * Validates this document.
     * <p>
     * A document has an associated validator which is responsible to validate the document.
     * An XML validator may check whether the document is well formed and a Java validator
     * may compile the class to validate the document.
     */
    public void validate() {
        getValidator().validate(this);
    }

    /**
     * Returns the document extension.
     * <p>
     * If an extension is found returns the same, otherwise returns a blank string.
     * @return The document extension.
     */
    public String getExtension() {
        return FileUtil.getExtension(this);
    }

    /**
     * Sets the editor for this document.
     * @param mPane The text editor.
     * @see #getEditor() 
     */
    public synchronized void setEditor(TextEditor mPane) {
        this.editor = mPane;
    }

    /**
     * Returns the editor for this document.
     * @return The text editor.
     * @see #setEditor(org.apex.base.component.TextEditor)
     */
    public synchronized TextEditor getEditor() {
        return this.editor;
    }

    /**
     * Returns the underlying data model.
     * @return The underlying data model.
     */
    public synchronized DefaultStyledDocument getDocument() {
        return (DefaultStyledDocument) this.editor.getDocument();
    }

    /**
     * Returns the index of this document.
     * @return Index of this document.
     * @see #setIndex(int) i
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the index of this document.
     * @param index Index of this document.
     * @see #getIndex() i
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Clones the document.
     * <p>
     * <strong>Warning: </strong>It does not clone references which this object holds.
     * @return The cloned document.
     */
    @Override
    public Object clone() {
        AbstractDocument clone = null;
        try {
            clone = (AbstractDocument) super.clone();
        } catch (CloneNotSupportedException ce) {
            Logger.logError("Failed to clone document: "
                    + this.getAbsolutePath(), ce);
        }
        return clone;
    }

    /**
     * Creates a similar document with given absolute path.
     * <p>
     * It copies all properties from the current document to cloned document.
     * @param absolutePath The absolute path in file system.
     * @return The cloned document.
     */
    public abstract AbstractDocument clone(String absolutePath);

    /**
     * Copies all members of this document to a cloned one.
     * @param clone The cloned document.
     * @return The modified cloned document.
     */
    public final AbstractDocument copyPropertiesTo(AbstractDocument clone) {
        clone.index = this.index;
        clone.temporary = this.temporary;
        clone.editor = this.editor;
        clone.component = this.component;
        clone.lastSaved = this.lastSaved;
        clone.undoManager = this.undoManager;
        clone.lineNumberArea = this.lineNumberArea;
        clone.currentyDisplayed = this.currentyDisplayed;
        clone.menuState = this.menuState;
        return clone;
    }

    /**
     * Returns the boolean indicating whether this document is saved in editor.
     * @return {@code true} if this document is saved in editor; otherwise
     *                returns {@code false}.
     */
    public boolean isSaved() {
        return this.getMenuState().isSaved();
    }

    /**
     * Sets the specified boolean to indicate whether or not this document should be marked
     * as saved.
     * @param isSaved The save indicator.
     */
    public void setSaved(boolean isSaved) {
        this.getMenuState().setSaved(isSaved);
    }

    /**
     * Returns the time in millisecond when this document was last saved in editor.
     * @return The last saved time.
     * @see #setLastSaved(long) l
     */
    public long getLastSaved() {
        return lastSaved;
    }

    /**
     * Sets the time in millisecond when this document was last saved in editor.     
     * @param lastSaved The last saved time.
     * @see #getLastSaved()      
     */
    public void setLastSaved(long lastSaved) {
        this.lastSaved = lastSaved;
    }

    /**
     * Returns the associated undo manager.
     * @return The undo manager.
     * @see #setUndoManager(javax.swing.undo.UndoManager) 
     */
    public UndoManager getUndoManager() {
        return undoManager;
    }

    /**
     * Sets the undo manager for this document.
     * @param undoManager The undo manager.
     * @see #getUndoManager() 
     */
    public void setUndoManager(UndoManager undoManager) {
        this.undoManager = undoManager;
        this.undoManager.setLimit(EditorKeyConstants.NUMBER_OF_UNDO_ALLOWED);
    }

    /**
     * Returns the directory in file system of this document.
     * @return The directory.
     */
    public String getDirectory() {
        if (this.isTemporary()) {
            return null;
        }
        return this.getParent();
    }

    /**
     * Returns the split status of document.
     * @return The split status.
     * @see CommonConstants#UNSPLIT
     * @see CommonConstants#HORIZONTAL_SPLIT
     * @see CommonConstants#VERTICAL_SPLIT
     * @see #setSplitStatus(int) 
     */
    public int getSplitStatus() {
        return this.getMenuState().getSplitStatus();
    }

    /**
     * Sets the split status of this document. 
     * @param splitStatus The split status.
     * @see #getSplitStatus()     
     */
    public void setSplitStatus(int splitStatus) {
        this.getMenuState().setSplitStatus(splitStatus);
    }

    /**
     * Returns the tab component where this document is displayed.
     * @return The tab component.
     * @see #setComponent(java.awt.Component) 
     */
    public Component getComponent() {
        return component;
    }

    /**
     * Sets the tab component where the document is displayed.
     * @param component The tab component.
     * @see #getComponent() 
     */
    public void setComponent(Component component) {
        this.component = component;
    }

    /**
     * Returns the line number display area.
     * @return The line number display area.
     * @see #setLineNumberArea(javax.swing.JComponent)
     */
    public JComponent getLineNumberArea() {
        return lineNumberArea;
    }

    /**
     * Sets the line number display area.
     * @param lineNumberArea The line number display area.
     * @see #getLineNumberArea() l
     */
    public void setLineNumberArea(JComponent lineNumberArea) {
        this.lineNumberArea = lineNumberArea;
    }

    /**
     * Returns the boolean indicating whether line number display area is displayed.
     * @return {@code true} if line number display area is displayed; otherwise
     *                returns {@code false}.
     */
    public boolean isViewLineNumber() {
        return this.getMenuState().isViewLineNumber();
    }

    /**
     * Sets the specified boolean to indicate whether or not line number display area
     * should be displayed.
     * @param viewLineNumber Line number display indicator.
     */
    public void setViewLineNumber(boolean viewLineNumber) {
        this.getMenuState().setViewLineNumber(viewLineNumber);
    }

    /**
     * Returns the boolean indicating whether undo can be done on content of
     * underlying document.
     * @return {@code true} if underlying document is undoable; otherwise
     *                returns {@code false}.
     */
    public boolean canUndo() {
        return this.getMenuState().canUndo();
    }

    /**
     * Sets the specified boolean to indicate whether or not document should be
     * undoable.
     * @param undoable Indicates whether or not document is undoable.
     */
    public void setCanUndo(boolean undoable) {
        this.getMenuState().setCanUndo(undoable);
    }

    /**
     * Returns the boolean indicating whether redo can be done on content of
     * underlying document.
     * @return {@code true} if underlying document is redoable; otherwise
     *                returns {@code false}.
     */
    public boolean canRedo() {
        return this.getMenuState().canRedo();
    }

    /**
     * Sets the specified boolean to indicate whether or not document should be
     * redoable.
     * @param redoable Indicates whether or not document is redoable.
     */
    public void setCanRedo(boolean redoable) {
        this.getMenuState().setCanRedo(redoable);
    }

    /**
     * Returns state of 'document sensitive' menus.
     * @return State of 'document sensitive' menus.
     * @see MenuState
     */
    public MenuState getMenuState() {
        return menuState;
    }

    /**
     * Returns the boolean indicating whether user can cut content of
     * underlying document.
     * @return {@code true} if user can cut content of underlying document; otherwise
     *                returns {@code false}.
     */
    public boolean canCut() {
        return this.menuState.canCut();
    }

    /**
     * Sets the boolean to indicate whether or not user can cut content of underlying
     * document.
     * @param canCut Indicates whether or not document content can be cut.
     */
    public void setCanCut(boolean canCut) {
        this.menuState.setCanCut(canCut);
    }

    /**
     * Returns the boolean indicating whether user can copy content of
     * underlying document.
     * @return {@code true} if user can copy content of underlying document; otherwise
     *                returns {@code false}.
     */
    public boolean canCopy() {
        return this.menuState.canCopy();
    }

    /**
     * Sets the boolean to indicate whether or not user can copy content of underlying
     * document.
     * @param canCopy Indicates whether or not document content can be copied.
     */
    public void setCanCopy(boolean canCopy) {
        this.menuState.setCanCopy(canCopy);
    }

    /**
     * Returns the boolean indicating whether this document is 'currently displayed
     * document' in editor.
     * @return {@code true} if this document is currently displayed; otherwise
     *                returns {@code false}.                 
     */
    public boolean isCurrentyDisplayed() {
        return currentyDisplayed;
    }

    /**
     * Sets the boolean to indicate whether this document should be marked as
     * currently displayed document.
     * @param currentyDisplayed Boolean to indicate whether this document should be marked as
     *                currently displayed document.
     */
    public void setCurrentyDisplayed(boolean currentyDisplayed) {
        this.currentyDisplayed = currentyDisplayed;
    }

    /**
     * Returns the object containing syntax style information for this document.
     * <p>
     * The associated lexer of this document uses this syntax style information object for
     * syntax highlighting.
     * @return The object containing syntax style information.
     */
    public abstract DocumentStyle getDocumentStyle();

    /**
     * Returns the boolean indicating whether this document is non persistent in file system.
     * @return {@code true} if this document is temporary; otherwise
     *                returns {@code false}.
     */
    public boolean isTemporary() {
        return temporary;
    }

    /**
     * Sets the specified boolean to indicate whether or not this document should be marked
     * as temporary.
     * @param temporary Boolean to indicate whether or not this document should be marked
     *                as temporary.
     */
    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    /**
     * Returns displayable name of this document.
     * <p>
     * It includes documents original name and unsaves document indicator.
     * @return The displayable name of this document.
     */
    public String getDisplayName() {
        if (this.isSaved()) {
            return this.getName();
        } else {
            return this.getName() + CommonConstants.UNSAVED_FILE_INDICATOR;
        }
    }

    @Override
    public String getAbsolutePath() {
        if (this.isTemporary()) {
            return this.getName();
        } else {
            return super.getAbsolutePath();
        }
    }

    /**
     * Returns displayable absolute path of this document.
     * <p>
     * It includes documents original absolute path and unsaves document indicator.
     * @return The displayable absolute path of this document.
     */
    public String getDisplayAbsolutePath() {
        if (this.isSaved()) {
            return this.getAbsolutePath();
        } else {
            return this.getAbsolutePath()
                    + CommonConstants.UNSAVED_FILE_INDICATOR;
        }
    }

    /**
     * Returns the associated icon.
     * <p>
     * This icon can be displayed in document tab or document selector along with document name.
     * @return The associated display icon.
     */
    public ImageIcon getDisplayIcon() {
        return DOCUMENT_ICON;
    }

    public IDocumentType getDocumentType() {
        return this.docType;
    }

    /**
     * Returns code completion popup.
     * @return The code completion popup.
     */
    public CodeCompletionPopup getCodeCompletionPopup() {
        return null;
    }

    /**
     * 
     * @return 
     */
    public boolean isReadWriteInProgress() {
        return readWriteInProgress;
    }

    /**
     * 
     * @param readWriteInProgress 
     */
    public void setReadWriteInProgress(boolean readWriteInProgress) {
        this.readWriteInProgress = readWriteInProgress;
    }
}
