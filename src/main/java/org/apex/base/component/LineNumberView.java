/*
 * LineNumberView.java
 * Created on 30 Dec, 2009, 9:38:02 PM
 *
 * Copyright (C) 2009 Mrityunjoy Saha
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SizeSequence;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import org.apex.base.constant.FontConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.data.HighlightCategories;
import org.apex.base.settings.EditorConfiguration;
import org.apex.base.settings.HighlightColor;
import org.apex.base.settings.event.HighlightStyleConfigChangeEvent;
import org.apex.base.settings.event.HighlightStyleConfigChangeListener;

/**
 * LineNumberView is a simple line-number gutter that works correctly
 * even when lines are wrapped in the associated text component.  This
 * is meant to be used as the RowHeaderView in a JScrollPane that
 * contains the associated text component.  Example usage:
 *<pre>
 *   JTextArea ta = new JTextArea();
 *   ta.setLineWrap(true);
 *   ta.setWrapStyleWord(true);
 *   JScrollPane sp = new JScrollPane(ta);
 *   sp.setRowHeaderView(new LineNumberView(ta));
 *</pre>
 *
 * @author Alan Moore
 * @author Mrityunjoy Saha
 */
public class LineNumberView extends JComponent {
    // This is for the border to the right of the line numbers.
    // There's probably a UIDefaults value that could be used for this.

    /**
     * Border color of line numbers area.
     */
    private static final Color BORDER_COLOR = Color.GRAY;
    /**
     * The template of width.
     */
    private static final int WIDTH_TEMPLATE = 99999;
    /**
     * The margin of line number area.
     */
    private static final int MARGIN = 3;
    /**
     * Font metrics.
     */
    private FontMetrics viewFontMetrics;
    /**
     * Maximum width of the number.
     */
    private int maxNumberWidth;
    /**
     * Width of the component.
     */
    private int componentWidth;
    /**
     * Top inset of the line numbers.
     */
    private int textTopInset;
    /**
     * Text font ascent.
     */
    private int textFontAscent;
    /**
     * Text font height.
     */
    private int textFontHeight;
    /**
     * The text component.
     */
    private JTextComponent text;
    /**
     * Size sequence.
     */
    private SizeSequence sizes;
    /**
     * Start line.
     */
    private int startLine = 0;
    /**
     * Indicates whether the structure is changed.
     */
    private boolean structureChanged = true;

    /**
     * Construct a LineNumberView and attach it to the given text component.
     * The LineNumberView will listen for certain kinds of events from the
     * text component and update itself accordingly.
     * @param text The text component.
     */
    public LineNumberView(JTextComponent text) {
        if (text == null) {
            throw new IllegalArgumentException("Text component cannot be null");
        }
        this.text = text;
        updateCachedMetrics();
        UpdateHandler handler = new UpdateHandler();
        text.getDocument().addDocumentListener(handler);
        text.addPropertyChangeListener(handler);
        text.addComponentListener(handler);
        Font viewFont = FontConstants.LINE_NUMBER_FONT;
        this.viewFontMetrics = getFontMetrics(viewFont);
        this.maxNumberWidth = viewFontMetrics.stringWidth(String.valueOf(
                WIDTH_TEMPLATE));
        this.componentWidth = 2 * MARGIN + maxNumberWidth;
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BORDER_COLOR));
        HighlightColor lineAreaColor =
                EditorBase.getContext().getConfiguration().
                getStyleConfig().getHighlightStyle().getHighlightColor(
                HighlightCategories.LINE_NUMBERS);
        this.setBackground(lineAreaColor.getBackground());
        this.setForeground(lineAreaColor.getForeground());
        EditorConfiguration.addHighlightStyleConfigChangeListener(new HighlightStyleConfigChangeListener() {

            public void propertyValueChanged(
                    HighlightStyleConfigChangeEvent event) {
                // Highlight styles changed. May be line number area colors are also changed.
                HighlightColor lineAreaColor =
                        EditorBase.getContext().getConfiguration().
                        getStyleConfig().getHighlightStyle().getHighlightColor(
                        HighlightCategories.LINE_NUMBERS);
                setBackground(lineAreaColor.getBackground());
                setForeground(lineAreaColor.getForeground());
            }
        });
    }

    /**
     * Schedule a repaint because one or more line heights may have changed.
     *
     * @param startLine the line that changed, if there's only one
     * @param structureChanged if <tt>true</tt>, ignore the line number and
     *     update all the line heights.
     */
    private void viewChanged(int startLine, boolean structureChanged) {
        this.startLine = startLine;
        this.structureChanged = structureChanged;
        revalidate();
        repaint();
    }

    /**
     * Update the line heights as needed.
     * @param g The graphics object.
     */
    private void updateSizes(Graphics g) {
        if (startLine < 0) {
            return;
        }
        if (structureChanged || sizes == null) {
            int count = getAdjustedLineCount();
            sizes = new SizeSequence(count);
            for (int i = 0; i < count; i++) {
                sizes.setSize(i, getLineHeight(g, i));
            }
            structureChanged = false;
        } else {
            sizes.setSize(startLine, getLineHeight(g, startLine));
        }
        startLine = -1;
    }

    /* Copied from javax.swing.text.PlainDocument */
    /**
     * Returns adjusted line count.
     * @return Adjusted line count.
     */
    private int getAdjustedLineCount() {
        // There is an implicit break being modeled at the end of the
        // document to deal with boundary conditions at the end.  This
        // is not desired in the line count, so we detect it and remove
        // its effect if throwing off the count.
        Element map = text.getDocument().getDefaultRootElement();
        int n = map.getElementCount();
        Element lastLine = map.getElement(n - 1);
        if ((lastLine.getEndOffset() - lastLine.getStartOffset()) > 1) {
            return n;
        }
        return n - 1;
    }

    /**
     * Get the height of a line from the JTextComponent.
     * @param g The graphics object.
     * @param index the line number.
     * @return The line height.
     */
    private int getLineHeight(Graphics g, int index) {
        int lastPos = sizes.getPosition(index) + textTopInset;
        int height = textFontHeight;
        try {
            Element map = text.getDocument().getDefaultRootElement();
            int lastChar = map.getElement(index).getEndOffset() - 1;
            Rectangle r = text.modelToView(lastChar);
            height = (r.y - lastPos) + r.height;
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        return height;
        //return g.getFontMetrics(this.text.getFont()).getHeight();
    }

    /**
     * Cache some values that are used a lot in painting or size
     * calculations. Also ensures that the line-number font is not
     * larger than the text component's font (by point-size, anyway).
     */
    private void updateCachedMetrics() {
        Font textFont = text.getFont();
        FontMetrics fm = getFontMetrics(textFont);
        this.textFontHeight = fm.getHeight();
        this.textFontAscent = fm.getAscent();
        this.textTopInset = text.getInsets().top;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(componentWidth, text.getHeight());
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        updateCachedMetrics();
    }

    @Override
    public void paintComponent(Graphics g) {
        updateSizes(g);
        Rectangle clip = g.getClipBounds();
        g.setColor(getBackground());
        g.fillRect(clip.x, clip.y, clip.width, clip.height);
        g.setColor(getForeground());
        int base = clip.y - textTopInset;
        int first = sizes.getIndex(base);
        int last = sizes.getIndex(base + clip.height);
        String tempText = "";
        for (int i = first; i <= last; i++) {
            tempText = String.valueOf(i + 1);
            int x = MARGIN + maxNumberWidth - viewFontMetrics.stringWidth(
                    tempText);
            int y = sizes.getPosition(i) + textFontAscent + textTopInset;
            g.drawString(tempText, x, y);
        }
    }

    /**
     * The document change tracker and line number component update handler.
     */
    class UpdateHandler extends ComponentAdapter
            implements PropertyChangeListener, DocumentListener {

        /**
         * The text component was resized.
         * @param evt The component event.
         */
        @Override
        public void componentResized(ComponentEvent evt) {
            viewChanged(0, true);
        }

        /**
         * A bound property was changed on the text component. Properties
         * like the font, border, and tab size affect the layout of the
         * whole document, so we invalidate all the line heights here.
         */
        public void propertyChange(PropertyChangeEvent evt) {
            Object oldValue = evt.getOldValue();
            Object newValue = evt.getNewValue();
            String propertyName = evt.getPropertyName();
            if ("document".equals(propertyName)) {
                if (oldValue != null && oldValue instanceof Document) {
                    ((Document) oldValue).removeDocumentListener(this);
                }
                if (newValue != null && newValue instanceof Document) {
                    ((Document) newValue).addDocumentListener(this);
                }
            }
            updateCachedMetrics();
            viewChanged(0, true);
        }

        /**
         * Text was inserted into the document.
         * @param evt The document event.
         */
        public void insertUpdate(DocumentEvent evt) {
            update(evt);
        }

        /**
         * Text was removed from the document.
         * @param evt The document event.
         */
        public void removeUpdate(DocumentEvent evt) {
            update(evt);
        }

        /**
         * Text attributes were changed.  In a source-code editor based on
         * StyledDocument, attribute changes should be applied automatically
         * in response to inserts and removals.  Since we're already
         * listening for those, this method should be redundant, but YMMV.
         * @param evt The document event.
         */
        public void changedUpdate(DocumentEvent evt) {
            //update(evt);
        }

        /**         
         * If the edit was confined to a single line, invalidate that
         * line's height.  Otherwise, invalidate them all.
         * @param evt The document event.
         */
        private void update(DocumentEvent evt) {
            Element map = text.getDocument().getDefaultRootElement();
            int line = map.getElementIndex(evt.getOffset());
            DocumentEvent.ElementChange ec = evt.getChange(map);
            viewChanged(line, ec != null);
        }
    }
}
