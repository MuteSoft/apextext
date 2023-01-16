/*
 * SearchHighlighter.java
 * Created on 3 June, 2007, 4:22 PM
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
package org.apex.base.search;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.View;

/**
 * A search highlighter.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SearchHighlighter extends DefaultHighlighter {

    /**
     * Creates a new instance of {@code SearchHighlighter}.
     */
    private SearchHighlighter() {
    }

    /**
     * The search highlight painter that fills a highlighted area with
     * a solid color.
     */
    public static class SearchHighlightPainter extends DefaultHighlightPainter {

        /**
         * Constructs a new instance of {@code SearchHighlightPainter} using
         * specified highlight color.
         * @param c The highlight color.
         */
        public SearchHighlightPainter(Color c) {
            super(c);
        }

        /**
         * Paints a portion of a highlight.
         * @param g The graphics context.
         * @param offs0 The starting model offset >= 0.
         * @param offs1 The ending model offset >= offs1.
         * @param bounds The bounding box of the view, which is not
         *              necessarily the region to paint.
         * @param c The text editor.
         * @param view View painting for.
         * @return Region drawing occured in.
         */
        @Override
        public Shape paintLayer(Graphics g, int offs0, int offs1,
                Shape bounds, JTextComponent c, View view) {
            return super.paintLayer(g, offs0, offs1, bounds, c, view);
        }
    }
}
