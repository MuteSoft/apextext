/*
 * LineNumberArea.java
 * Created on 3 July, 2007, 10:55 PM
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

import org.apex.base.core.EditorBase;
import org.apex.base.data.HighlightCategories;
import org.apex.base.settings.HighlightColor;
import org.apex.base.settings.EditorConfiguration;
import org.apex.base.settings.event.HighlightStyleConfigChangeEvent;
import org.apex.base.settings.event.HighlightStyleConfigChangeListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.constant.FontConstants;

/**
 * A panel to display line numbers.
 * It is always in synch with the associated text editor. Whenever a new line is introduced
 * or a line is deleted from editor line number area repaints itself to display line numbers.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class LineNumberArea extends ApexPanel {

    /**
     * The right hand side gap between panel border and line number String.
     */
    private static final int LINE_NUMBER_AREA_RIGHT_GAP = 8;
    /**
     * The text editor.
     */
    private TextEditor editArea;
    /**
     * The component which holds the text editor.
     */
    private ApexScrollPane editAreaScrollPane;

    /**
     * Creates a new instance of LineNumberArea.
     * @param lineNumberedPane The line numbered text editor which holds both
     *              text editor and scroll pane for text editor.
     */
    public LineNumberArea(LineNumberedTextEditor lineNumberedPane) {
        this.setPreferredSize(new Dimension(getPreferredWidth(),
                getPreferredWidth()));
        this.editArea = lineNumberedPane.getEditArea();
        this.editAreaScrollPane = lineNumberedPane.getEditAreaScrollPane();

        HighlightColor lineAreaColor =
                EditorBase.getContext().getConfiguration().
                getStyleConfig().getHighlightStyle().getHighlightColor(
                HighlightCategories.LINE_NUMBERS);
        this.setBackground(lineAreaColor.getBackground());
        this.setForeground(lineAreaColor.getForeground());
        this.setFont(FontConstants.LINE_NUMBER_FONT);
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
     * Invoked by Swing to draw line numbers in line number display panel.
     * @param g The Graphics context.
     */
    //@TODO - Performance issues - takes lot of time when edit area is scrolled
    // When edit area is scrolling very fast can the paiting be skipped as user won't be able to see
    // line numbers painted during that time.
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int start = editArea.viewToModel(editAreaScrollPane.getViewport().
                getViewPosition());

        int end = editArea.viewToModel(new Point(editAreaScrollPane.getViewport().
                getViewPosition().x +
                editArea.getWidth(),
                editAreaScrollPane.getViewport().
                getViewPosition().y +
                editArea.getHeight()));

        Document doc = editArea.getDocument();
        int startline = doc.getDefaultRootElement().getElementIndex(start) + 1;
        int endline = doc.getDefaultRootElement().getElementIndex(end) + 1;

        int fontHeight = g.getFontMetrics(editArea.getFont()).getHeight();
        int fontDesc = g.getFontMetrics(editArea.getFont()).getDescent();
        int starting_y = -1;

        try {
            starting_y = editArea.modelToView(start).y -
                    editAreaScrollPane.getViewport().
                    getViewPosition().y + fontHeight - fontDesc;
        } catch (BadLocationException e1) {
            // No need to log. The paint() method must return quickly.
//            Logger.logWarning(
//                    "Failed to calculate starting Y position of line number string.",
//                    e1);
        }
        int panelWidth = (int) this.getVisibleRect().getWidth() - LINE_NUMBER_AREA_RIGHT_GAP;
        for (int line = startline, y = starting_y; line <= endline; y +=
                        fontHeight, line++) {
            // If it is the continuation of same line don't paint the line number
            if (!editArea.isWordWrapEnabled()) {
                g.drawString(Integer.toString(line), panelWidth - (6 * Integer.
                        toString(line).
                        length()), y);
            } else {
                // If same line don't paint
                if(true){
                    g.drawString(Integer.toString(line), panelWidth - (6 * Integer.
                        toString(line).
                        length()), y);
                }else{
                line--;
                endline--;
                }
            }
        }
    }

    /**
     * Returns the preferred width of line number display area.
     * @return The preferred width of line number display area.
     */
    public int getPreferredWidth() {
        return EditorKeyConstants.LINE_NUMBER_AREA_WIDTH;
    }
}
