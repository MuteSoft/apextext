/*
 * AdvancedTextSearcher.java 
 * Created on 14 Dec, 2008, 3:18:45 PM
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

import gnu.regexp.RESyntax;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;

/**
 * An advanced text searcher. This can replace basic text searcher to search text
 * in editor.
 * @author mrityunjoy_saha
 * @version 1.1
 * @since Apex 1.2
 */
public class AdvancedTextSearcher extends TextSearcherSupport {

    /**
     * Creates anew instance of {@code AdvancedTextSearcher} with specified text
     * highlight painter.
     * @param painter The text highlight painter.
     */
    public AdvancedTextSearcher(Highlighter.HighlightPainter painter) {
        super(painter);
    }

    /**
     * Search and highlight all matches.
     * @param model The search data model.
     * @return All matching highlights.
     */
    public Highlight[] highlightSearchAll(SearchTextModel model) {
        return new Highlight[0];
    }

    /**
     * Finds all matches, but don't highlight them.
     * @param model The search data model.
     * @return All matching highlights.
     */
    public Highlight[] searchAll(SearchTextModel model) {
        RESyntax syntax = null;
        return new Highlight[0];
    }

    public Highlight search(SearchTextModel model) {
        RESyntax syntax = new RESyntax();
        return null;
    }

    public Highlight searchNext(SearchTextModel model,
            int referenceCaretPosition) {
        RESyntax syntax = null;
        return null;
    }

    public Highlight searchPrevious(SearchTextModel model,
            int referenceCaretPosition) {
        return null;
    }

    public Highlight searchNextHighlight(SearchTextModel model,
            int referenceCaretPosition) {
        return null;
    }

    public Highlight searchPreviousHighlight(SearchTextModel model,
            int referenceCaretPosition) {
        return null;
    }
}
