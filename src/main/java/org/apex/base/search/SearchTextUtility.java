/*
 * SearchTextUtility.java
 * Created on 2 June, 2007, 2:53 PM
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
import org.apex.base.data.HighlightCategories;
import org.apex.base.data.EditorContext;
import org.apex.base.logging.Logger;
import org.apex.base.settings.EditorConfiguration;
import org.apex.base.settings.event.HighlightStyleConfigChangeEvent;
import org.apex.base.settings.event.HighlightStyleConfigChangeListener;
import java.awt.Color;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter.Highlight;

/**
 * A utility class to support search and replace events.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
// @TODO Use advanced searcher and replacer. Add support for regular expression.
public class SearchTextUtility {

    /**
     * The search data model.
     */
    private static SearchTextModel model = new SearchTextModel();
    /**
     * The last search key.
     */
    private static String lastSearchText;
    /**
     * A boolean that indicates whether last search was case sensitive.
     */
    private static boolean lastSearchCaseSensitive;
    /**
     * The text searcher to use.
     */
    private static TextSearcher searcher;
    /**
     * The text replacer to use.
     */
    private static TextReplacer replacer;

    /**
     * Returns the last search key.
     * @return The last search key.
     * @see #setLastSearchText(java.lang.String) 
     */
    public static String getLastSearchText() {
        return lastSearchText;
    }

    /**
     * Sets the last search key.
     * @param aLastSearchText The last search key.
     * @see #getLastSearchText()     
     */
    public static void setLastSearchText(String aLastSearchText) {
        lastSearchText = aLastSearchText;
    }

    /**
     * Returns a boolean that indicates whether or not last search was case sensitive.
     * @return A boolean that indicates whether or not last search was case sensitive.
     */
    public static boolean isLastSearchCaseSensitive() {
        return lastSearchCaseSensitive;
    }

    /**
     * Sets a boolean that indicates whether or not last search was case sensitive.
     * @param aLastSearchCaseSensitive A case sensitive indicator.
     */
    public static void setLastSearchCaseSensitive(
            boolean aLastSearchCaseSensitive) {
        lastSearchCaseSensitive = aLastSearchCaseSensitive;
    }

    /**
     * Determines whether search key is changed between last search event
     * and this search event.
     * @return {@code true} if search key changed; otherwise returns {@code false}.
     */
    public static boolean isSearchTextChanged() {
        if (lastSearchText == null) {
            return true;
        }
        if (model.getSearchKey().equalsIgnoreCase(lastSearchText)) {
            if (isLastSearchCaseSensitive() && model.isCaseSensitive()) {
                return !model.getSearchKey().equals(lastSearchText);
            } else if (!isLastSearchCaseSensitive() && !model.
                    isCaseSensitive()) {
                return false;
            }
        }
        // By default say search text changed.
        return true;
    }

    /**
     * Searches for a key in text editor based on available search data model.
     * @param context The editor context.
     * @return {@code true} if search is successful; otherwise returns {@code false}.
     */
    public static boolean search(EditorContext context) {
        return search(context, false);
    }

    /**
     * Searches for a key in text editor based on available search data model.
     * @param context The editor context.
     * @param scrollToVisible  boolean that indicates whether or not scroll is enabled to make the replacement
     * text position visible.
     * @return {@code true} if search is successful; otherwise returns {@code false}.
     */
    static boolean search(EditorContext context, final boolean scrollToVisible) {
        final Highlight highlight = getSearcher(context).search(model);
        if (highlight == null) {
            // beep
            Toolkit.getDefaultToolkit().beep();
            return false;
        } else {
            final TextEditor editArea = context.getEditorProperties().
                    getCurrentDocument().getEditor();
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    public void run() {
                        try {
                            if (scrollToVisible) {
                                editArea.scrollRectToVisible(editArea.
                                        modelToView(highlight.getStartOffset()));
                            }
                            //Highlight matching text
                            if (model.isBackwardSearch()) {
                                editArea.setCaretPosition(
                                        highlight.getEndOffset());
                                editArea.moveCaretPosition(highlight.
                                        getStartOffset());
                            } else {
                                editArea.setCaretPosition(highlight.
                                        getStartOffset());
                                editArea.moveCaretPosition(highlight.
                                        getEndOffset());
                            }
                        } catch (BadLocationException ex) {
                            Logger.logWarning(
                                    "Could not highlight mathing text.", ex);
                        }
                    }
                });
                // Check whether search text already exists in the list, if not then add to the dropdown.
                // When same text is searched more than once only then add the text to the history list.
                if (!isSearchTextChanged()) {
                    checkAndAddItemToList(model.getSearchKeys(),
                            model.getSearchKey());
                }
                setLastSearchText(model.getSearchKey());
                setLastSearchCaseSensitive(model.isCaseSensitive());
                return true;
            } catch (InterruptedException ex) {
                Logger.logWarning("Could not highlight mathing text.", ex);
                return false;
            } catch (InvocationTargetException ex) {
                Logger.logWarning("Could not highlight mathing text.", ex);
                return false;
            }
        }
    }

    /**
     * Replaces a key in text editor based on available search data model.
     * @param context The editor context.
     * @return {@code true} if replace is successful; otherwise returns {@code false}.
     */
    public static boolean replace(EditorContext context) {
        if (!context.getEditorProperties().getCurrentDocument().getEditor().
                isEditable()) {
            return false;
        }
        TextReplacer textReplacer = getReplacer(context);
        // Check whether search text already exists in the list, if not then add to the dropdown otherwise not.
        checkAndAddItemToList(model.getReplaceKeys(), model.getReplaceKey());
        return textReplacer.replace(model);
    }

    /**
     * Replaces all occurrences of a key in text editor based on available search data model.
     * @param context The editor context.
     * @return {@code true} if replace is successful; otherwise returns {@code false}.
     */
    public static boolean replaceAll(EditorContext context) {
        if (!context.getEditorProperties().
                getCurrentDocument().getEditor().isEditable()) {
            return false;
        }
        TextReplacer textReplacer = getReplacer(context);
        // Check whether search text already exists in the list, if not then add to the dropdown otherwise not.
        //checkAndAddItemToList(model.getSearchKeys(), model.getSearchKey());
        return textReplacer.replaceAll(model);
    }

    /**
     * Verifies whether given new item existing in the given list of keys. If the new item
     * does not exist it adds the item at the top of the list.
     * @param existingKeys A lsit of keys.
     * @param newItem A new item.
     */
    public static void checkAndAddItemToList(List<String> existingKeys,
            String newItem) {
        //String newItem = model.getSearchKey();
        //Vector<String> findKeys = model.getSearchKeys();
        if (existingKeys.contains(newItem)) {
            // Remove the element because we are going to insert again at first position.
            existingKeys.remove(newItem);
        }
        // In any case set this text as first element.
        existingKeys.add(0, newItem);
    }

    /**
     * Returns the search data model.
     * @return The search data model.
     */
    public static SearchTextModel getModel() {
        return model;
    }

    /**
     * It returns the searcher to use.
     * @param context The editor context.
     * @return A suitable text searcher to search text in editor.
     */
    public static TextSearcher getSearcher(final EditorContext context) {
        if (searcher == null) {
            final Color highlightColor =
                    context.getConfiguration().getStyleConfig().
                    getHighlightStyle().
                    getHighlightColor(HighlightCategories.SEARCH).getBackground();
            searcher =
                    new BasicTextSearcher(new SearchHighlighter.SearchHighlightPainter(
                    highlightColor));
            EditorConfiguration.addHighlightStyleConfigChangeListener(new HighlightStyleConfigChangeListener() {

                public void propertyValueChanged(
                        HighlightStyleConfigChangeEvent event) {
                    final Color highlightColor =
                            context.getConfiguration().getStyleConfig().
                            getHighlightStyle().
                            getHighlightColor(HighlightCategories.SEARCH).
                            getBackground();
                    searcher.setPainter(new SearchHighlighter.SearchHighlightPainter(
                            highlightColor));
                }
            });
        }
        searcher.setEditArea(context.getEditorProperties().
                getCurrentDocument().getEditor());
        return searcher;
    }

    /**
     * It returns the replacer to use.
     * @param context The editor context.
     * @return A suitable text replacer to replace text in editor.
     */
    public static TextReplacer getReplacer(EditorContext context) {
        if (replacer == null) {
            replacer =
                    new BasicTextReplacer();
        }
        replacer.setEditArea(context.getEditorProperties().
                getCurrentDocument().getEditor());
        return replacer;
    }

    /**
     * Constructs a new instance of {@code SearchTextUtility}.
     */
    private SearchTextUtility() {
    }
}
