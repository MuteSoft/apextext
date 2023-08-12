/*
 * SearchTextModel.java
 * Created on 4 June, 2007, 5:08 AM
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

import java.util.ArrayList;
import java.util.List;
import org.apex.base.data.UIDataModel;

/**
 * The data model used for searching and replacing text in editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SearchTextModel implements UIDataModel {

    /**
     * The list of search keys.
     */
    private List<String> searchKeys = new ArrayList<String>();
    /**
     * The list of replace keys.
     */
    private List<String> replaceKeys = new ArrayList<String>();
    /**
     * The key to search.
     */
    private String searchKey;
    /**
     * The replace key.
     */
    private String replaceKey;
    /**
     * A boolean that indicates whether backward search is enabled.
     */
    private boolean backwardSearch;
    /**
     * A boolean that indicates whether case sensitive search is enabled.
     */
    private boolean caseSensitive;
    /**
     * A boolean that indicates whether highlight search is enabled.
     */
    private boolean highlightSearch = true;
    /**
     * A boolean that indicates whether incremental search is enabled.
     */
    private boolean incrementalSearch = true;
    /**
     * A boolean that indicates whether regular expression search is enabled.
     */
    private boolean regularExpression;
    /**
     * A boolean that indicates whether selection search is enabled.
     */
    private boolean searchSelection;
    /**
     * A boolean that indicates whether whole words is enabled.
     */
    private boolean wholeWord;
    /**
     * A boolean that indicates whether wrap search is enabled.
     */
    private boolean wrapSearch = true;
    /**
     * The list of folders.
     */
    private List<String> folders = new ArrayList<String>();

    /**
     * Constructs a new instance of {@code SearchTextModel}.
     */
    public SearchTextModel() {
    }

    /**
     * Returns a boolean that indicates whether backward search is enabled.
     * @return {@code true} if backward search is enabled; otherwise returns {@code false}.
     */
    public boolean isBackwardSearch() {
        return backwardSearch;
    }

    /**
     * Sets a boolean that indicates whether or not backward search should be enabled.
     * @param backwardSearch A backward search indicator.
     */
    public void setBackwardSearch(boolean backwardSearch) {
        this.backwardSearch = backwardSearch;
    }

    /**
     * Returns a boolean that indicates whether case sensitive search is enabled.
     * @return {@code true} if case sensitive search is enabled; otherwise returns {@code false}.
     */
    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    /**
     * Sets a boolean that indicates whether or not case sensitive search should be enabled.
     * @param caseSensitive A case sensitive search indicator.
     */
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    /**
     * Returns the search key.
     * @return The search key.
     * @see #setSearchKey(java.lang.String) 
     */
    public String getSearchKey() {
        return searchKey;
    }

    /**
     * Sets the search key.
     * @param text A search key.
     * @see #getSearchKey() 
     * 
     */
    public void setSearchKey(String text) {
        this.searchKey = text;
    }

    /**
     * Returns a boolean that indicates whether highlight search is enabled.
     * @return {@code true} if highlight search is enabled; otherwise returns {@code false}.
     */
    public boolean isHighlightSearch() {
        return highlightSearch;
    }

    /**
     * Sets a boolean that indicates whether or not highlight search should be enabled.
     * @param highlightSearch A highlight search indicator.
     */
    public void setHighlightSearch(boolean highlightSearch) {
        this.highlightSearch = highlightSearch;
    }

    /**
     * Returns a boolean that indicates whether incremental search is enabled.
     * @return {@code true} if incremental search is enabled; otherwise returns {@code false}.
     */
    public boolean isIncrementalSearch() {
        return incrementalSearch;
    }

    /**
     * Sets a boolean that indicates whether or not incremental search should be enabled.
     * @param incrementalSearch A incremental search indicator.
     */
    public void setIncrementalSearch(boolean incrementalSearch) {
        this.incrementalSearch = incrementalSearch;
    }

    /**
     * Returns a boolean that indicates whether regular expression search is enabled.
     * @return {@code true} if regular expression search is enabled; otherwise returns {@code false}.
     */
    public boolean isRegularExpression() {
        return regularExpression;
    }

    /**
     * Sets a boolean that indicates whether or not regular expression search should be enabled.
     * @param regularExpression A regular expression search indicator.
     */
    public void setRegularExpression(boolean regularExpression) {
        this.regularExpression = regularExpression;
    }

    /**
     * Returns a boolean that indicates whether selection search is enabled.
     * @return {@code true} if selection search is enabled; otherwise returns {@code false}.
     */
    public boolean isSearchSelection() {
        return searchSelection;
    }

    /**
     * Sets a boolean that indicates whether or not selection search should be enabled.
     * @param searchSelection A selection search indicator.
     */
    public void setSearchSelection(boolean searchSelection) {
        this.searchSelection = searchSelection;
    }

    /**
     * Returns a boolean that indicates whether whole word search is enabled.
     * @return {@code true} if whole word search is enabled; otherwise returns {@code false}.
     */
    public boolean isWholeWord() {
        return wholeWord;
    }

    /**
     * Sets a boolean that indicates whether or not whole word search should be enabled.
     * @param wholeWord A whole word search indicator.
     */
    public void setWholeWord(boolean wholeWord) {
        this.wholeWord = wholeWord;
    }

    /**
     * Returns a boolean that indicates whether wrap search is enabled.
     * @return {@code true} if wrap search is enabled; otherwise returns {@code false}.
     */
    public boolean isWrapSearch() {
        return wrapSearch;
    }

    /**
     * Sets a boolean that indicates whether or not wrap search should be enabled.
     * @param wrapSearch A wrap search indicator.
     */
    public void setWrapSearch(boolean wrapSearch) {
        this.wrapSearch = wrapSearch;
    }

    /**
     * Returns a list of search keys.
     * @return A list of search keys.
     */
    public List<String> getSearchKeys() {
        return searchKeys;
    }

    /**
     * Returns a list of replace keys.
     * @return A list of replace keys.
     */
    public List<String> getReplaceKeys() {
        return replaceKeys;
    }

    /**
     * Returns the replace key.
     * @return The replace key.
     * @see #setReplaceKey(java.lang.String) 
     */
    public String getReplaceKey() {
        return replaceKey;
    }

    /**
     * Sets the replace key.
     * @param replaceKey A replace key.
     * @see #getReplaceKey() 
     */
    public void setReplaceKey(String replaceKey) {
        this.replaceKey = replaceKey;
    }

    /**
     * Returns a textual representation of this search data model.
     * @return A textual representation of this search data model.
     */
    @Override
    public String toString() {
        return "searchKey: " + searchKey + "replaceKey: " + replaceKey + "backwardSearch: "
                + backwardSearch + "caseSensitive: " + caseSensitive + "highlightSearch: "
                + highlightSearch + "incrementalSearch: " + incrementalSearch + "regularExpression: "
                + regularExpression + "searchSelection: " + searchSelection + "wholeWord: "
                + wholeWord + "wrapSearch: " + wrapSearch;
    }
}
