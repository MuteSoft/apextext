/*
 * BasicTextSearcher.java
 * Created on May 31, 2007, 8:18 PM
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

import org.apex.base.logging.Logger;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.Highlight;
import javax.swing.text.Utilities;
import org.apex.base.util.StringUtil;

/**
 * A basic text searcher implementation.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class BasicTextSearcher extends TextSearcherSupport {

    /**
     * Creates a new instance of {@code BasicTextSearcher}.
     * @param painter The highlight painter.
     */
    public BasicTextSearcher(Highlighter.HighlightPainter painter) {
        super(painter);
    }

    /**
     * Marks all highlights in editor.
     * @param model A data model for text search.
     * @param painter A highlight painter.
     * @return all found highlights, empty array if nothing is found
     */
    private Highlight[] markHighlights(SearchTextModel model,
            Highlighter.HighlightPainter painter) {
        Vector<Highlight> highlights = new Vector<Highlight>();
        final Highlighter highlighter = getEditArea().getHighlighter();
        // @TODO Remove only existing relevant highlights for last word
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    getEditArea().clearSearchHighlights();
                }
            });
        } catch (InterruptedException ie) {
            Logger.logWarning("Failed to clear previous highlights. Search text: " +
                    model.getSearchKey(), ie);
        } catch (InvocationTargetException ite) {
            Logger.logWarning("Failed to clear previous highlights. Search text: " +
                    model.getSearchKey(), ite);
        }

        String text = model.getSearchKey();
        if (text == null || text.equals("")) {
            return null;
        }

        String content = null;
        try {
            Document d = getEditArea().getDocument();
            // @TODO - This is heavy operation. Replace this in future version.
            content = d.getText(0, d.getLength());
        } catch (BadLocationException e) {
            Logger.logWarning(
                    "While searching failed to get text from edit area. Search text: " +
                    text, e);
            return null;
        }
        if (!model.isCaseSensitive()) {
            // @TODO - This is heavy operation. Replace this in future version.
            content = content.toLowerCase();
            text = text.toLowerCase();
        }
        int lastIndex = 0;
        int wordSize = text.length();
        // @TODO - This is heavy operation. Replace this in future version.
        while ((lastIndex = content.indexOf(text, lastIndex)) != -1) {
            int endIndex = lastIndex + wordSize;
            try {

                boolean validResult = true;
                // Here check for whole word. If the word found is not a complete word skip it.
                if (model.isWholeWord()) {
                    if (lastIndex != Utilities.getWordStart(getEditArea(),
                            lastIndex) ||
                            endIndex != Utilities.getWordEnd(getEditArea(),
                            lastIndex)) {
                        validResult = false;
                    }
                }
                if (validResult) {
                    highlights.add((Highlight) highlighter.addHighlight(
                            lastIndex,
                            endIndex,
                            painter));
                }
            } catch (BadLocationException e) {
                Logger.logWarning(
                        "Error while adding highlight for a given search text: " +
                        text, e);
            }
            lastIndex = endIndex;
        }
        Highlight[] value = new Highlight[0];
        return highlights.toArray(value);
    }

    /**
     * Finds all matches, but don't highlight them.
     * @param model The search data model.
     * @return All matching highlights.
     */
    public Highlight[] searchAll(SearchTextModel model) {
        return markHighlights(model, null);
    }

    /**
     * Search and highlight all matches.
     * @param model The search data model.
     * @return All matching highlights.
     */
    public Highlight[] highlightSearchAll(SearchTextModel model) {
        return markHighlights(model, this.getPainter());
    }

    public Highlight search(SearchTextModel model) {
        Highlight result = null;
        int currentCaretPosition = this.getEditArea().getCaretPosition();
        if (model.isHighlightSearch()) {
            if (SearchTextUtility.isSearchTextChanged()) {
                this.getEditArea().setSearchHighlights(highlightSearchAll(model));
            } else {
                // Though same text, but highlights may be removed due to edit or some other reason.
                if (this.getEditArea().isSearchHighlightsEmpty()) {
                    this.getEditArea().setSearchHighlights(highlightSearchAll(
                            model));
                }
            }
            // @TODO There can be a scenario where search text is not changed, highlight list is not empty,
            // but due to edit the locations (offset) of highlights are changed. This scenario is not handled
            // presently.
            // Really no match found.
            if (this.getEditArea().isSearchHighlightsEmpty()) {
                return null;
            }
            if (model.isBackwardSearch()) {
                result = searchPreviousHighlight(model, currentCaretPosition);
                if (result == null && model.isWrapSearch()) {
                    result = searchPreviousHighlight(model, this.getEditArea().
                            getDocument().
                            getLength());
                }
            } else {
                result = searchNextHighlight(model, currentCaretPosition);
                if (result == null && model.isWrapSearch()) {
                    result = searchNextHighlight(model, 0);
                }
            }
            return result;
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    public void run() {
                        // Clear all highlights, if exists any
                        getEditArea().clearSearchHighlights();
                    }
                });
            } catch (InterruptedException ie) {
                Logger.logWarning("Failed to clear previous highlights",
                        ie);
            } catch (InvocationTargetException ite) {
                Logger.logWarning("Failed to clear previous highlights", ite);
            }
            if (model.isBackwardSearch()) {
                result = searchPrevious(model, currentCaretPosition);
                if (result == null && model.isWrapSearch()) {
                    result = searchPrevious(model, this.getEditArea().
                            getDocument().
                            getLength());
                }
            } else {
                result = searchNext(model, currentCaretPosition);
                if (result == null && model.isWrapSearch()) {
                    result = searchNext(model, 0);
                }
            }
            return result;
        }
    }

    /**
     * Move the caret position backward or forward based on previous search text and matched result.
     * <p>
     * Instead of start searching at current caret position, start from little behind
     * in case charcters before caret position was the result of last search
     * @param d The document.
     * @param referenceCaretPosition The current caret position.
     * @param forwardSearch Indicates whether the current search is in forward or backward direction.
     * @return The updated caret position.
     */
    private int updateReferenceCaretPosition(Document d,
            int referenceCaretPosition,
            boolean forwardSearch) {
        // Instead of start searching at current caret position, start from little behind
        // in case charcters before caret position was the result of last search
        String lastSearchText = SearchTextUtility.getLastSearchText();
        String currentSearchText = SearchTextUtility.getModel().getSearchKey();
        if (StringUtil.isNullOrEmpty(lastSearchText) || lastSearchText.
                equalsIgnoreCase(
                currentSearchText)) {
            return referenceCaretPosition;
        }
        int lastSearcgTextLength = lastSearchText.length();
        try {
            if (forwardSearch) {
                if ((referenceCaretPosition - lastSearcgTextLength) > 0 && (d.
                        getText(
                        referenceCaretPosition - lastSearcgTextLength,
                        lastSearcgTextLength).equalsIgnoreCase(lastSearchText))) {
                    referenceCaretPosition = referenceCaretPosition - lastSearchText.
                            length();
                }
            } else {
                // @TODO - Fix me asap. Pending.
                if ((referenceCaretPosition + lastSearcgTextLength) < d.
                        getLength() && (d.getText(
                        referenceCaretPosition,
                        lastSearcgTextLength).equalsIgnoreCase(lastSearchText))) {
                    referenceCaretPosition = referenceCaretPosition + lastSearchText.
                            length() + 1;
                }
            }
        } catch (BadLocationException ex) {
            Logger.logWarning(
                    "Failed to move reference caret position backward.", ex);
        }
        return referenceCaretPosition;
    }

    public Highlight searchNext(SearchTextModel model,
            int referenceCaretPosition) {
        String text = model.getSearchKey();
        if (text == null || text.equals("")) {
            return null;
        }
        Document d = getEditArea().getDocument();
        referenceCaretPosition = updateReferenceCaretPosition(d,
                referenceCaretPosition, true);
        String content = null;
        try {

            // @TODO - This is heavy operation. Replace this in future version.
            content = d.getText(referenceCaretPosition, (d.getLength() -
                    referenceCaretPosition));
        } catch (BadLocationException e) {
            Logger.logWarning("Failed to get next matching text.", e);
            return null;
        }
        if (!model.isCaseSensitive()) {
            // @TODO - This is heavy operation. Replace this in future version.
            content = content.toLowerCase();
            text = text.toLowerCase();
        }
        int matchIndex = 0;
        // @TODO - This is heavy operation. Replace this in future version.
        if ((matchIndex = content.indexOf(text)) != -1) {
            return new SearchHighlight((referenceCaretPosition + matchIndex),
                    (referenceCaretPosition + matchIndex + text.length()), null);
        }
        return null;
    }

    public Highlight searchPrevious(SearchTextModel model,
            int referenceCaretPosition) {
        String text = model.getSearchKey();
        if (text == null || text.equals("")) {
            return null;
        }
        Document d = getEditArea().getDocument();
        referenceCaretPosition = updateReferenceCaretPosition(d,
                referenceCaretPosition, false);
        String content = null;
        try {
            // @TODO - This is heavy operation. Replace this in future version.
            content = d.getText(0, referenceCaretPosition);
        } catch (BadLocationException e) {
            Logger.logWarning("Failed to get previous matching text.", e);
            return null;
        }
        if (!model.isCaseSensitive()) {
            // @TODO - This is heavy operation. Replace this in future version.
            content = content.toLowerCase();
            text = text.toLowerCase();
        }

        int matchIndex = 0;
        // @TODO - This is heavy operation. Replace this in future version.
        if ((matchIndex = content.lastIndexOf(text)) != -1) {
            return new SearchHighlight(matchIndex, (matchIndex + text.length()),
                    null);
        }
        return null;
    }

    public Highlight searchNextHighlight(SearchTextModel model,
            int referenceCaretPosition) {
        Highlight[] highlights = this.getEditArea().getSearchHighlights();
        referenceCaretPosition = updateReferenceCaretPosition(getEditArea().
                getDocument(),
                referenceCaretPosition, true);
        int startIndex = 0;
        int endIndex = highlights.length - 1;
        int guessIndex = 0;
        if (endIndex == 0) {
            if (referenceCaretPosition <=
                    highlights[startIndex].getStartOffset()) {
                return highlights[startIndex];
            } else {
                return null;
            }
        }
        while (true) {
            if ((endIndex - startIndex) == 1) {
                if (referenceCaretPosition <=
                        highlights[startIndex].getStartOffset()) {
                    return highlights[startIndex];
                } else if (referenceCaretPosition <=
                        highlights[endIndex].getStartOffset()) {
                    return highlights[endIndex];
                } else {
                    return null;
                }
            } else if ((endIndex - startIndex) < 0) {
                return null;
            }
            guessIndex = (endIndex + startIndex + 1) / 2;
            if (referenceCaretPosition < highlights[guessIndex].getStartOffset()) {
                endIndex = guessIndex;
            } else if (referenceCaretPosition >
                    highlights[guessIndex].getStartOffset()) {
                startIndex = guessIndex;
            } else {
                return highlights[guessIndex + 1];
            }
        }
    }

    public Highlight searchPreviousHighlight(SearchTextModel model,
            int referenceCaretPosition) {
        Highlight[] highlights = this.getEditArea().getSearchHighlights();
        referenceCaretPosition = updateReferenceCaretPosition(getEditArea().
                getDocument(),
                referenceCaretPosition, false);
        int startIndex = 0;
        int endIndex = highlights.length - 1;
        int guessIndex = 0;
        if (endIndex == 0) {
            if (referenceCaretPosition >=
                    highlights[endIndex].getEndOffset()) {
                return highlights[startIndex];
            } else {
                return null;
            }
        }
        while (true) {
            if ((endIndex - startIndex) == 1) {
                if (referenceCaretPosition >=
                        highlights[endIndex].getEndOffset()) {
                    return highlights[endIndex];
                } else if (referenceCaretPosition >=
                        highlights[startIndex].getEndOffset()) {
                    return highlights[startIndex];
                } else {
                    return null;
                }
            } else if ((endIndex - startIndex) < 0) {
                return null;
            }
            guessIndex = (endIndex + startIndex + 1) / 2;
            if (referenceCaretPosition < highlights[guessIndex].getEndOffset()) {
                endIndex = guessIndex;
            } else if (referenceCaretPosition >
                    highlights[guessIndex].getEndOffset()) {
                startIndex = guessIndex;
            } else {
                return highlights[guessIndex - 1];
            }
        }

    }
}
