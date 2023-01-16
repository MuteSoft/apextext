/*
 * ApexTextPane.java
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

import javax.swing.Action;
import javax.swing.KeyStroke;
import org.apex.base.data.TypingMode;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

/**
 * An extension of {@code JTextPane}. It provides handy constructors and methods
 * to easily deal with text pane.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexTextPane extends JTextPane {

    /**
     * A key used to refer typing mode.
     */
    public static final String TYPING_MODE = "TypingMode";
    /**
     * The caret color.
     */
    //private Color originalCaretColor;
    /**
     * The caret width.
     */
    private int originalCaretWidth;
    /**
     * Indiacates whether or not word-wrapping is enabled.
     */
    private boolean wordWrapEnabled;

    /**
     * Creates a new instance of {@code ApexTextPane}.
     */
    public ApexTextPane() {
    }

    /**
     * Creates a new instance of {@code ApexTextPane} using specified document.
     * @param doc The document.
     */
    public ApexTextPane(StyledDocument doc) {
        super(doc);        
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
     * It changes the current typing mode. If current typing
     * mode is INSERT, it changes it to OVERRITE and vice-versa.
     * Also it updates the caret color and width.
     */
    public void toggleTypingMode() {
        TypingMode mode = getDocument().getProperty("TypingMode") == null
                ? TypingMode.INSERT
                : (TypingMode) getDocument().getProperty("TypingMode");
        if (mode == TypingMode.INSERT) {
            this.originalCaretWidth = (Integer) this.getClientProperty(
                    "caretWidth");
            // Fix for bug id 2895007 (sourceforge.net)
            //this.originalCaretColor = this.getCaretColor();
            updateCaretWidth(5);
            // Fix for bug id 2895007 (sourceforge.net)
            //updateCaretColor(Color.BLACK);
            getDocument().putProperty(TYPING_MODE, TypingMode.OVERWRITE);
        } else {
            getDocument().putProperty("TypingMode", TypingMode.INSERT);
            updateCaretWidth(this.originalCaretWidth);
            // Fix for bug id 2895007 (sourceforge.net)
            //updateCaretColor(this.originalCaretColor);
        }
    }

    /**
     * Updates caret width.
     * @param caretWidth The caret width.
     */
    public void updateCaretWidth(int caretWidth) {
        TypingMode mode = getDocument().getProperty("TypingMode") == null
                ? TypingMode.INSERT
                : (TypingMode) getDocument().getProperty("TypingMode");
        if (mode == TypingMode.INSERT) {
            this.putClientProperty("caretWidth", caretWidth);
        } else {
            this.originalCaretWidth = caretWidth;
        }
    }

    /**
     * Updates caret color.
     * @param caretColor The caret color.
     */
    public void updateCaretColor(Color caretColor) {
        // Fix for bug id 2895007 (sourceforge.net)
        this.setCaretColor(caretColor);
//        TypingMode mode = getDocument().getProperty("TypingMode") == null
//                ? TypingMode.INSERT
//                : (TypingMode) getDocument().getProperty("TypingMode");
//        if (mode == TypingMode.INSERT) {
//            this.setCaretColor(caretColor);
//        } else {
//            this.originalCaretColor = caretColor;
//        }
    }

    /**
     * Returns true if a viewport should always force the width of this 
     * {@code Scrollable} to match the width of the viewport.  
     * @return {@code true} if a viewport should force the Scrollables width to
     *                match its own, {@code false} otherwise.
     */
    @Override
    public boolean getScrollableTracksViewportWidth() {
        return isWordWrapEnabled()
                ? true
                : (getSize().width < getParent().getWidth());
    }

    /**
     * Returns a boolean value indicating whether or not word-wrap is currently enabled.
     * @return {@code true} if word-wrap is enabled; otherwise returns {@code false}.
     * @see #setWordWrapEnabled(boolean)
     */
    public boolean isWordWrapEnabled() {
        return wordWrapEnabled;
    }

    /**
     * Sets the word-wrap value based on which word-wrap is enabled or disabled.
     * @param wordWrapEnabled Word-wrap enable flag.
     * @see #isWordWrapEnabled()
     */
    public void setWordWrapEnabled(boolean wordWrapEnabled) {
        this.wordWrapEnabled = wordWrapEnabled;
    }

    /**
     * Sets the size of text pane.
     * @param d The dimension which encapsulates width and height of
     *                this text pane.
     */
    @Override
    public void setSize(Dimension d) {
        Dimension pSize = getParent().getSize();
        if (d.width < pSize.width) {
            super.setSize(pSize.width, d.height);
        } else {
            super.setSize(d);
        }
    }

    /**
     * Sets the tab size.
     * @param size The tab size.
     */
    public void setTabSize(int size) {
        FontMetrics fm = this.getFontMetrics(this.getFont());
        int charWidth = fm.charWidth(' ');
        int tabWidth = charWidth * size;

        TabStop[] tabs = new TabStop[100];
        for (int j = 0; j < tabs.length; j++) {
            int tab = j + 1;
            tabs[j] = new TabStop(tab * tabWidth);
        }

        TabSet tabSet = new TabSet(tabs);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setTabSet(attributes, tabSet);
        // TODO Fix the caret height https://github.com/MuteSoft/apextext/issues/5
        //StyleConstants.setLineSpacing(attributes, 1.0f);
        int length = this.getDocument().getLength();

        try {
            this.getStyledDocument().setParagraphAttributes(0, length,
                    attributes,
                    false);
        } catch (Exception e) {
            // Do nothing.
        }
    }
}
