/* 
 * SearchHighlight.java
 * Created on Nov 30, 2007,12:14:19 AM
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

import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Highlighter.HighlightPainter;

/**
 * A search highlight.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SearchHighlight implements Highlight {

    /**
     * The starting model offset.
     */
    private int startOffset;
    /**
     * The ending model offset.
     */
    private int endOffset;
    /**
     * The highlight painter.
     */
    private HighlightPainter painter;

    /**
     * Constructs a new instance of {@code SearchHighlight} with given starting
     * model offset, ending model offset and highlight painter.
     * @param startOffset The starting model offset.
     * @param endOffset The ending model offset.
     * @param painter A highlight painter.
     */
    public SearchHighlight(int startOffset, int endOffset, HighlightPainter painter) {
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.painter = painter;
    }

    /**
     * Gets the starting model offset for the highlight.
     * @return The starting offset >= 0.
     */
    public int getStartOffset() {
        return startOffset;
    }

    /**
     * Gets the ending model offset for the highlight.
     * @return The ending offset >= 0.
     */
    public int getEndOffset() {
        return endOffset;
    }

    /**
     * Gets the painter for the highlighter.
     * @return The highlight painter.
     */
    public HighlightPainter getPainter() {
        return painter;
    }
}