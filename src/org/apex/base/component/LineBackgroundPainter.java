/*
 * LineBackgroundPainter.java 
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

import org.apex.base.logging.Logger;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.View;

/**
 * Paints the line background. It can be modified to highlight any given line or multiple lines.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class LineBackgroundPainter extends DefaultHighlighter.DefaultHighlightPainter
        implements CaretListener, MouseListener, MouseMotionListener {

    /**
     * The text editor.
     */
    private JTextComponent editArea;
    /**
     * The highlighter.
     */
    private DefaultHighlighter highlighter;
    /**
     * The last highlight.
     */
    private Object lastHighlight;

    /**
     * Constructs a new instance of {@code LineBackgroundPainter} using specified text
     * editor and highlight color.
     * @param editArea The text editor.
     * @param color The highlight color.
     */
    public LineBackgroundPainter(JTextComponent editArea, Color color) {
        super(color);
        this.editArea = editArea;
        this.highlighter = (DefaultHighlighter) editArea.getHighlighter();
        this.highlighter.setDrawsLayeredHighlights(true);

        this.editArea.addCaretListener(this);
        this.editArea.addMouseListener(this);
        this.editArea.addMouseMotionListener(this);

        // At the begining highlight the first line.
        addHighlight(0);
    }

    /**
     * Called when the caret position is updated.
     * @param e The caret event.
     */
    public void caretUpdate(CaretEvent e) {
        resetHighlight();
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     * @param e The mouse event.
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     * @param e The mouse event.
     */
    public void mousePressed(MouseEvent e) {
        resetHighlight();
    }

    /**
     * Invoked when a mouse button has been released on a component.
     * @param e The mouse event.
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     * @param e The mouse event.
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     * @param e The mouse event.
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button is pressed on a component and then 
     * dragged.
     * @param e The mouse event.
     */
    public void mouseDragged(MouseEvent e) {
        resetHighlight();
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     * @param e The mouse event.
     */
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Adds a highlight in a line at specified offset.
     * @param offset The offset.
     */
    private void addHighlight(int offset) {
        try {
            lastHighlight = highlighter.addHighlight(offset, offset + 1, this);
        } catch (BadLocationException ex) {
            Logger.logWarning("Could not add line background highlight at offset: " + offset,
                    ex);
        }

    }

    /**
     * Removes the last highlight and adds the highlight
     * to current line.
     */
    private void resetHighlight() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                highlighter.removeHighlight(lastHighlight);
                Element root = editArea.getDocument().
                        getDefaultRootElement();
                // Line Number
                int lineNumber =
                        root.getElementIndex(editArea.getCaretPosition());
                int lineStartOffset = root.getElement(lineNumber).
                        getStartOffset();
                addHighlight(lineStartOffset);
            }
        });
    }

    /**
     * Paints a portion of a highlight.
     * @param g The graphics context.
     * @param offs0 The starting model offset >= 0.
     * @param offs1 The ending model offset >= offs1.
     * @param bounds The bounding box of the view, which is not
     *              necessarily the region to paint.
     * @param c The editor.
     * @param view View painting for.
     * @return region Drawing occured in.
     */
    @Override
    public Shape paintLayer(Graphics g, int offs0, int offs1,
            Shape bounds,
            JTextComponent c, View view) {
        try {
            // Use the first offset to get the line to highlight
            Rectangle lineArea = c.modelToView(offs0);
            lineArea.x = 0;
            lineArea.width = c.getSize().width;

            g.setColor(getColor());
            g.fillRect(lineArea.x, lineArea.y, lineArea.width, lineArea.height);
            return lineArea;
        } catch (BadLocationException ex) {
            Logger.logWarning("Could not paint line background at offset: " + offs0, ex);
            return null;
        }
    }
}
