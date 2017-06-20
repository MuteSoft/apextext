/* 
 * DocumentWrapper.java
 * Created on 23 Nov, 2007,11:56:17 PM
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

/**
 * A wrapper around a document object.
 * <p>
 * Consider a scenario where a document is passed to multiple listeners and
 * later document is changed. It is very difficult to update document in all listeners. To solve this problem
 * instead of  document, a document wrapper is passed to listeners and within listener document is
 * always accessed from wrapper. Anytime the contained
 * document can be changed without sending any notification to listeners; automatically 
 * document is refreshed in all listeners, the moment  document is changed in wrapper
 * from a single place.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentWrapper {

    /**
     * The contained document.
     */
    private AbstractDocument document;

    /**
     * Constructs a new {@code DocumentWrapper} with given
     * document.
     * @param document The document to be wrapped.
     */
    public DocumentWrapper(AbstractDocument document) {
        this.document = document;
    }

    /**
     * Returns the document.
     * @return The contained document.
     * @see #setDocument(org.apex.base.data.AbstractDocument)
     */
    public AbstractDocument getDocument() {
        return document;
    }

    /**
     * Sets the document.
     * @param document The contained document.
     * @see #getDocument() 
     */
    public void setDocument(AbstractDocument document) {
        this.document = document;
    }
}
