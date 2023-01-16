/* 
 * TextSearcher.java
 * Created on Nov 26, 2007,12:25:51 AM
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

import org.apex.base.component.TextEditor;
import javax.swing.text.Highlighter;

/**
 * The {@code TextSearcher} is a simple abstraction for searching text.
 * <p>
 * Supports highlight search and incremental search.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public interface TextSearcher {

    /**
     * Search and highlight all matches.
     * @param model The search data model.
     * @return All matching highlights.
     */
    Highlighter.Highlight[] highlightSearchAll(SearchTextModel model);

    /**
     * Finds all matches, but don't highlight them.
     * @param model The search data model.
     * @return All matching highlights.
     */
    Highlighter.Highlight[] searchAll(SearchTextModel model);

    /**
     * Based on forward/backward search  option calls either {@code searchNext()}
     * or {@code searchPrevious()} method to perform the search operation.
     * <p>
     * It does not highlight matching text.
     * @param model The search data model.
     * @return Matching highlight if found; otherwise returns {@code null}.
     * @see #searchNext(org.apex.base.search.SearchTextModel, int)
     * @see #searchPrevious(org.apex.base.search.SearchTextModel, int)
     */
    Highlighter.Highlight search(SearchTextModel model);

    /**
     * Finds the next match relative to given caret position.
     * <p>
     * It does not highlight matching text.
     * @param model Search data model.
     * @param referenceCaretPosition The reference cursor position.
     * @return Matching highlight if found; otherwise returns {@code null}.
     */
    Highlighter.Highlight searchNext(SearchTextModel model,
                                     int referenceCaretPosition);

    /**
     * Finds the previous match relative to given caret position.
     * <p>
     * It does not highlight matching text.
     * @param model Search data model.
     * @param referenceCaretPosition The reference cursor position.
     * @return Matching highlight if found; otherwise returns {@code null}.
     */
    Highlighter.Highlight searchPrevious(SearchTextModel model,
                                         int referenceCaretPosition);

    /**
     * Finds the next match relative to given caret position.
     * @param model Search data model.
     * @param referenceCaretPosition The reference cursor position.
     * @return Matching highlight if found; otherwise returns {@code null}.
     */
    Highlighter.Highlight searchNextHighlight(SearchTextModel model,
                                              int referenceCaretPosition);

    /**
     * Finds the previous match relative to given caret position.
     * @param model Search data model.
     * @param referenceCaretPosition The reference cursor position.
     * @return Matching highlight if found; otherwise returns {@code null}.
     */
    Highlighter.Highlight searchPreviousHighlight(SearchTextModel model,
                                                  int referenceCaretPosition);

    /**
     * Returns the text editor on which search operation takes place.
     * @return The text editor on which search operation takes place.
     * @see #setEditArea(org.apex.base.component.TextEditor)
     */
    TextEditor getEditArea();

    /**
     * Sets  the text editor on which search operation takes place.
     * @param editArea The text editor on which search operation takes place.
     * @see #getEditArea() 
     */
    void setEditArea(TextEditor editArea);

    /**
     * Returns the highlight painter.
     * @return The highlight painter.
     * @see #setPainter(javax.swing.text.Highlighter.HighlightPainter)
     */
    Highlighter.HighlightPainter getPainter();

    /**
     * Sets the highlight painter.
     * @param painter The highlight painter.
     * @see #getPainter() 
     */
    void setPainter(Highlighter.HighlightPainter painter);
}
