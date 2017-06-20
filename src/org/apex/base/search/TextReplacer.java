/* 
 * TextReplacer.java
 * Created on Nov 27, 2007,12:37:09 AM
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

/**
 * The {@code TextReplacer} is a simple abstraction for replacing text. Multiple texts
 * can be replaced at the same time.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public interface TextReplacer {

    /**
     * Based on forward/backward search option calls either {@code replaceNext()} or
     * {@code replacePrevious()} to perform the text replace operation.
     * @param model Search data model.
     * @return {@code true} if replacement happens; otherwise returns {@code false}.
     * @see #replaceNext(org.apex.base.search.SearchTextModel)
     * @see #replacePrevious(org.apex.base.search.SearchTextModel)
     */
    boolean replace(SearchTextModel model);

    /**
     * Replaces the next match.
     * @param model Search data model.
     * @return {@code true} if replacement happens; otherwise returns {@code false}.
     */
    boolean replaceNext(SearchTextModel model);

    /**
     * Replaces the previous match.
     * @param model Search data model.
     * @return {@code true} if replacement happens; otherwise returns {@code false}.
     */
    boolean replacePrevious(SearchTextModel model);

    /**
     * Replaces all matches found.
     * @param model Search data model.
     * @return {@code true} if atleast one replacement takes place; otherwise returns {@code false}.
     */
    boolean replaceAll(SearchTextModel model);

    /**
     * Returns the text editor on which replace operation takes place.
     * @return The text editor on which replace operation takes place.
     * @see #setEditArea(org.apex.base.component.TextEditor)
     */
    TextEditor getEditArea();

    /**
     * Sets the text editor on which replace operation takes place.
     * @param editArea The text editor on which replace operation takes place.
     * @see #getEditArea() 
     */
    void setEditArea(TextEditor editArea);
}
