/* 
 * BasicTextReplacer.java
 * Created on Dec 1, 2007,10:18:15 AM
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

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.logging.Logger;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

/**
 * A basic text replacer implementation.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class BasicTextReplacer extends TextReplacerSupport {

    /**
     * Constructs a new instance of {@code BasicTextReplacer}.
     */
    public BasicTextReplacer() {
    }

    public boolean replace(SearchTextModel model) {
        return replace(model, true);
    }

    /**
     * Replaces the search key by replace key available in the search data model.
     * @param model The search data model.
     * @param scrollToVisible A boolean that indicates whether or not scroll is enabled to make the replacement
     * text position visible.
     * @return {@code true} if replacement successful; otherwise returns {@code false}.
     */
    private boolean replace(SearchTextModel model, boolean scrollToVisible) {
        String searchKey = model.getSearchKey();
        String replaceKey = model.getReplaceKey();
        if (!model.isCaseSensitive()) {
            searchKey = searchKey.toLowerCase();
            replaceKey = replaceKey.toLowerCase();
        }
        if (searchKey.equals(replaceKey)) {
            return false;
        }
        /*
         * Check if there is any selected text (indicates find was fired just before this event) in the document.  
         * If selection exists by checking absolute value of (dot-mark), verify the selected text against search key.
         * If the selected text is same replace it and return.
         * Based on forward or backward direction search next or search previous (without highlight)
         * and get the highlight. Finally replace the highlight. If nothing is there to replace, make a beep sound and return.
         */
        boolean selectedTextReplaced = replaceSelectedText(model);
        boolean found = false;
        if (!selectedTextReplaced) {
            // Either there is no selection in the document or selected text does not match the search key. Find text and select it. 
            found = SearchTextUtility.search(getContext(), false);
            if (found) {
                // Second attempt for text replacement
                selectedTextReplaced = replaceSelectedText(model);
            }
        }
        if (selectedTextReplaced) {
            // Naviage to next or previous match           
            found = SearchTextUtility.search(getContext(), false);
            if (!found) {
                this.getEditArea().getCaret().moveDot(this.getEditArea().
                        getCaret().getMark());
            }
        }
        return selectedTextReplaced;
    }

    /**
     * First it determines if there is any selected text in the document by checking absolute value of 
     * {@code (dot-mark)}. This checks whether or not 'find' event was fired just before this event.
     * If selection exists in editor, verify the selected text against search key. If the selected text
     * is same as search key of this event replace it and return.
     * @param model The search data model.
     * @return {@code true} if selected text is replaced; otherwise returns {@code false}.
     */
    private boolean replaceSelectedText(final SearchTextModel model) {
        String searchKey = model.getSearchKey();
        String replaceKey = model.getReplaceKey();
        if (!model.isCaseSensitive()) {
            searchKey = searchKey.toLowerCase();
            replaceKey = replaceKey.toLowerCase();
        }
        int dot = this.getEditArea().getCaret().getDot();
        int mark = this.getEditArea().getCaret().getMark();
        if (Math.abs(dot - mark) > 0 && Math.abs(dot - mark)
                == searchKey.length()) {
            // Selection exists. Get selected text and compare
            String selectedText = "";
            // Is it forward selection?
            try {
                if (dot > mark) {
                    selectedText =
                            this.getEditArea().getDocument().
                            getText(mark, searchKey.length());

                } else {
                    // it is backward
                    selectedText = this.getEditArea().getDocument().getText(dot,
                            searchKey.length());
                }
            } catch (BadLocationException ex) {
                Logger.logWarning(
                        "While replacing failed to get currently selected text.",
                        ex);
            }
            if (!model.isCaseSensitive()) {
                selectedText = selectedText.toLowerCase();
            }
            if (searchKey.equals(selectedText)) {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {

                        public void run() {
                            // Currently selected text may be replaced
                            getEditArea().replaceSelection(model.getReplaceKey());
                        }
                    });
                } catch (InterruptedException ie) {
                    Logger.logWarning("Failed to replace text.", ie);
                } catch (InvocationTargetException ite) {
                    Logger.logWarning("Failed to replace text.", ite);
                }
                return true;
            }
        }
        return false;
    }

    public boolean replaceNext(SearchTextModel model) {
        return false;
    }

    public boolean replacePrevious(SearchTextModel model) {
        return false;
    }

    public boolean replaceAll(SearchTextModel model) {
        String searchKey = model.getSearchKey();
        String replaceKey = model.getReplaceKey();
        if (!model.isCaseSensitive()) {
            searchKey = searchKey.toLowerCase();
            replaceKey = replaceKey.toLowerCase();
        }
        if (searchKey.equals(replaceKey)) {
            return false;
        }
        boolean isWrapsearch = model.isWrapSearch();
        boolean isBackwardSearch = model.isBackwardSearch();
        boolean isHighlightSearch = model.isHighlightSearch();
        model.setWrapSearch(false);
        model.setBackwardSearch(false);
        model.setHighlightSearch(false);
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    // Move the caret to begining of document
                    getEditArea().setCaretPosition(0);
                }
            });
        } catch (InterruptedException ie) {
            Logger.logWarning("While replacing failed to move caret.", ie);
        } catch (InvocationTargetException ite) {
            Logger.logWarning("While replacing failed to move caret.", ite);
        }
        boolean isReplaced = false;
        do {
            isReplaced = replace(model, false);
        } while (isReplaced);
        model.setWrapSearch(isWrapsearch);
        model.setBackwardSearch(isBackwardSearch);
        model.setHighlightSearch(isHighlightSearch);

        return isReplaced;
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    public EditorContext getContext() {
        return EditorBase.getContext();
    }
}
