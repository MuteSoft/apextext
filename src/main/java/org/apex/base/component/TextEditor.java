/*
 * TextEditor.java
 * Created on February 25, 2007, 12:22 PM  
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

import org.apex.base.action.AutoIndentationAction;
import org.apex.base.action.CodeCompletionAction;
import org.apex.base.action.CodeTemplateAction;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.core.PopupMenuManager;
import org.apex.base.data.TypingMode;
import org.apex.base.dnd.FileAndTextTransferHandler;
import org.apex.base.event.CaretListenerImpl;
import org.apex.base.event.EditorPopupFocusListener;
import org.apex.base.event.CodeCompletionKeyListener;
import org.apex.base.event.DocumentQueueKeyListener;
import org.apex.base.event.PopupMenuBarListener;
import org.apex.base.settings.EditorConfiguration;
import org.apex.base.settings.event.FontStyleConfigChangeListener;
import org.apex.base.settings.event.GeneralSectionConfigChangeEvent;
import org.apex.base.settings.event.GeneralSectionConfigChangeListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Highlighter;
import javax.swing.text.StyledDocument;
import org.apex.base.settings.GeneralSectionConfiguration;

/**
 * The base text editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class TextEditor extends ApexTextPane {

    /**
     * Search highlights.
     */
    private Highlighter.Highlight[] searchHighlights;
    /**
     * A boolean that indicates whether or not right margin of editor should be visible or not.
     */
    private boolean viewRightMargin;
    /**
     * The right margin size.
     */
    private int rightMargin;
    /**
     * The right margin color.
     */
    private Color rightMarginColor;

    /**
     * Creates a new instance of {@code TextEditor}.
     */
    public TextEditor() {
        initialize();
    }

    /**
     * Constructs a new instance of {@code TextEditor} using specified document.
     * @param doc The document.
     */
    public TextEditor(StyledDocument doc) {
        super(doc);
        initialize();
    }

    /**
     * Initializes the editor.
     */
    private void initialize() {
        GeneralSectionConfiguration genSecConfig = EditorBase.getContext().
                getConfiguration().getGeneralConfig().getGeneral();
        viewRightMargin = genSecConfig.isViewRightMargin();
        rightMargin = genSecConfig.getRightMargin();
        rightMarginColor = genSecConfig.getRightMarginColor();
        EditorConfiguration.addGeneralSectionConfigChangeListener(new GeneralSectionConfigChangeListener() {

            public void propertyValueChanged(
                    GeneralSectionConfigChangeEvent event) {
                GeneralSectionConfiguration genSecConfig = EditorBase.getContext().
                        getConfiguration().getGeneralConfig().getGeneral();
                viewRightMargin = genSecConfig.isViewRightMargin();
                rightMargin = genSecConfig.getRightMargin();
                rightMarginColor = genSecConfig.getRightMarginColor();
            }
        });
    }

    /**
     * Adds general section configuration change listener to editor.
     * @param listener A general section configuration change listener.
     */
    public void addGeneralSectionConfigChangeListener(
            GeneralSectionConfigChangeListener listener) {
        EditorConfiguration.addGeneralSectionConfigChangeListener(listener);
    }

    /**
     * Adds font style configuration change listener to editor.
     * @param listener A font style configuration change listener.
     */
    public void addFontStyleConfigChangeListener(
            FontStyleConfigChangeListener listener) {
        EditorConfiguration.addFontStyleConfigChangeListener(listener);
    }

    /**
     * Binds a key board action.
     * @param name The name of action.
     * @param keyStroke The accelerator.
     * @param action The action.
     */
    public void addKeyBoardAction(String name, KeyStroke keyStroke,
            Action action) {
        this.getInputMap(WHEN_FOCUSED).put(keyStroke, name);
        this.getActionMap().put(name, action);
    }

    /**
     * Adds various key listeners, mouse listener, focus listener etc to the editor.
     */
    public void addListeners() {
        // Create the line background painter
        new LineBackgroundPainter(
                this,
                EditorKeyConstants.EDIT_AREA_LINE_BACKGROUND_COLOR);
        this.setOpaque(true);
        this.getCaret().setBlinkRate(EditorKeyConstants.CARET_BLINK_RATE);

        // Drag and drop file support.
        this.setDragEnabled(true);
        this.setTransferHandler(new FileAndTextTransferHandler());

        // Add Caret Listener to the text pane.
        this.addCaretListener(new CaretListenerImpl());

        // Add the MouseListener to the text pane for popup menu.
        this.addMouseListener(new PopupMenuBarListener(
                PopupMenuManager.EDIT_AREA_POPUP));

        // To listen event like CTRL+TAB by custom event listener.
        this.setFocusTraversalKeysEnabled(false);
        // For code completion support
        this.addKeyListener(new CodeCompletionKeyListener());
        this.addKeyBoardAction("CodeCompletion", KeyStroke.getKeyStroke(
                KeyEvent.VK_SPACE,
                KeyEvent.CTRL_MASK), new CodeCompletionAction());
        this.addFocusListener(new EditorPopupFocusListener());
        // For code template support
        //editArea.addKeyListener(new CodeTemplateKeyListener(context));
        this.addKeyBoardAction("CodeTemplate", KeyStroke.getKeyStroke(
                KeyEvent.VK_ENTER,
                KeyEvent.CTRL_MASK), new CodeTemplateAction());
        // Document Queue support - CTRL +TAB
        this.addKeyListener(new DocumentQueueKeyListener());

        // Auto Indentation Support
        this.addKeyBoardAction("AutoIndentation", KeyStroke.getKeyStroke(
                KeyEvent.VK_ENTER,
                0),
                new AutoIndentationAction());
        // Typing Mode support
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()
                        == KeyEvent.VK_INSERT && e.getModifiers() == 0) {
                    toggleTypingMode();
                    // @TODO Status bar data should not be updated from here, instead use a listener.
                    EditorBase.getContext().getEditorComponents().getStatusBar().
                            setTypingMode((TypingMode) getDocument().
                            getProperty("TypingMode"));
                }
            }
        });
        getDocument().putProperty("TypingMode", TypingMode.INSERT);
        // @TODO Uncomment following line when palette functionality is enabled
        //new PaletteItemDropListener(this);
    }

    /**
     * Removes all listeners.
     */
    public void removeListeners() {
        this.setTransferHandler(null);

        // Remove Caret Listeners from the text pane.
        for (CaretListener listener : getCaretListeners()) {
            this.removeCaretListener(listener);
        }

        // Remove Mouse Listeners from the text pane for popup menu.
        for (MouseListener listener : getMouseListeners()) {
            this.removeMouseListener(listener);
        }

        // Remove Key Listeners from the text pane for popup menu.
        for (KeyListener listener : getKeyListeners()) {
            this.removeKeyListener(listener);
        }

        // Remove Focus Listeners from the text pane for popup menu.
        for (FocusListener listener : getFocusListeners()) {
            this.removeFocusListener(listener);
        }
        // Remove document listeners
        for (DocumentListener listener : ((DefaultStyledDocument) getDocument()).
                getDocumentListeners()) {
            ((DefaultStyledDocument) getDocument()).removeDocumentListener(
                    listener);
        }
        // Remove document listeners
        for (UndoableEditListener listener : ((DefaultStyledDocument) getDocument()).
                getUndoableEditListeners()) {
            ((DefaultStyledDocument) getDocument()).removeUndoableEditListener(
                    listener);
        }
    }

    /**
     * For a given action name it finds and returns the {@code Action}.
     * @param name The name of Action.
     * @return An action.
     */
    public Action getDefaultAction(String name) {
        return this.getActionMap().get(name); 
    }

    /**
     * Returns the list of key strokes supported by editor by default for
     * various actions.
     * @return A list of accelerators.
     */
    public List<KeyStroke> getDefaultKeyStrokes() {
        return Arrays.asList(this.getInputMap().allKeys());
    }

    /**
     * For a specified action name returns a list of associated key strokes.
     * @param name The name of action.
     * @return A list of accelerators.
     */
    public List<KeyStroke> getDefaultKeyStrokes(String name) {
        List<KeyStroke> defaultKeyStrokes = getDefaultKeyStrokes();
        ArrayList<KeyStroke> keyList = new ArrayList<KeyStroke>();
        for (KeyStroke stroke : defaultKeyStrokes) {
            if (this.getInputMap().get(stroke).toString().equals(name)) {
                keyList.add(stroke);
            }
        }
        return keyList;
    }

    /**
     * Returns search highlights.
     * @return A list of search highlights.
     * @see #setSearchHighlights(javax.swing.text.Highlighter.Highlight[])
     */
    public Highlighter.Highlight[] getSearchHighlights() {
        return searchHighlights;
    }

    /**
     * Sets search highlights.
     * @param searchHighlights Search highlights.
     * @see #getSearchHighlights() 
     */
    public void setSearchHighlights(Highlighter.Highlight[] searchHighlights) {
        this.searchHighlights = searchHighlights;
    }

    /**
     * Clears all highlights from the editor including search highlights.
     */
    public void clearSearchHighlights() {
        if (this.searchHighlights != null) {
            this.getHighlighter().removeAllHighlights();
            this.searchHighlights = null;
        }
    }

    /**
     * Returns a boolean indicating whether or not search highlights exist in the editor.
     * @return {@code true} if search highlights exist; otherwise returns {@code false}.
     */
    public boolean isSearchHighlightsEmpty() {
        return this.searchHighlights == null || searchHighlights.length == 0;
    }

    /**
     * Fetches the current color used to render the 
     * selected text.
     * @return The selected text color.
     */
    @Override
    public Color getSelectedTextColor() {
        return super.getSelectedTextColor();
    }

    /**
     * Fetches the current color used to render the 
     * selection.
     * @return The selection color.
     */
    @Override
    public Color getSelectionColor() {
        return super.getSelectionColor();
    }

    /**
     * Returns the margin between the text component's border and
     * its text.       
     * @return The margin.
     */
    @Override
    public Insets getMargin() {
        return EditorKeyConstants.EDIT_AREA_MARGIN;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (viewRightMargin) {
            g.setColor(rightMarginColor);
            int rightMarginSize = getFontMetrics(getFont()).charWidth('a') * rightMargin;
            g.drawLine(rightMarginSize, 0, rightMarginSize, getHeight());
        }
    }

}
