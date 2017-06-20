/*
 * HighlightCategories.java
 * Created on 17 July, 2007, 12:34 AM
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
package org.apex.base.data;

import java.util.Vector;

/**
 * This class contains a list of items which can be highlighted by applying a background color
 * and a foreground color. 
 * <p>
 * For example, a background color and a foreground color can be chosen for line number display area.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class HighlightCategories {

    /**
     * List of highlight categories.
     */
    private static Vector<String> highlightCatgs;
    /**
     * The Search highlight category.
     */
    public static String SEARCH = "search-results";
    /**
     * Line number display area hightlight category. 
     */
    public static String LINE_NUMBERS = "line-numbers";
    /**
     * Selected tab hightlight category.
     */
    public static String SELECTED_TAB = "selected-tab";
    /**
     * Selected tab hightlight category.
     */
    public static String WINDOW_TITLE_BAR = "window-title-bar";

    /**
     * Creates a new instance of {@code HighlightCategories}.
     */
    private HighlightCategories() {
    }

    static {
        add(SEARCH);
        add(LINE_NUMBERS);
        add(SELECTED_TAB);
        add(WINDOW_TITLE_BAR);
    }

    /**
     * Returns a list of highlight categories.
     * @return List of highlight categories.
     */
    public static Vector<String> getHighlightCategories() {
        return highlightCatgs;
    }

    /**
     * Adds a new highlight categories.
     * @param catg A new highligh category to be added to list.
     */
    public static void add(String catg) {
        if (highlightCatgs == null) {
            highlightCatgs = new Vector<String>();
        }
        highlightCatgs.add(catg);
    }

    /**
     * Removes a highlight category from list.
     * @param catg The highlight category to be removed from list.
     */
    public static void remove(String catg) {
        if (highlightCatgs != null) {
            highlightCatgs.remove(catg);
        }
    }
}
