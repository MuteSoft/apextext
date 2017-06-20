/*
 * LengthFilter.java 
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

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * A document filter used by editor based components to restrict length
 * of text in underlying {@code javax.swing.text.Document}.
 * <p>
 * For example, a text field can use this document filter to restrict length
 * of input text inserted by user or from application.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class LengthFilter extends DocumentFilter {

    /**
     * The text length restriction.
     */
    private int length;

    /**
     * Creates a new instance of {@code LengthFilter} using
     * specified length.
     * @param length The text length restriction.
     */
    public LengthFilter(int length) {
        this.length = length;
    }

    /**
     * Checks the length before inserting given String to data model. If validation
     * fails insertion does not happen.
     * @param fb FilterBypass That can be used to mutate Document.
     * @param offset  The offset into the document to insert the content >= 0.
     *                All positions that track change at or after the given location 
     *                will move.  
     * @param string The string to insert
     * @param attr The attributes to associate with the inserted
     *                      content.  This may be null if there are no attributes.
     * @throws BadLocationException  The given insert position is not a
     *                 valid position within the document.
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String string,
                             AttributeSet attr) throws
            BadLocationException {
        if (this.length > 0 && fb.getDocument().getLength() + string.length() >
                this.length) {
            return;
        }
        super.insertString(fb, offset, string, attr);
    }

    /**
     * Checks the length before replacing given String in data model. If validation
     * fails replacement does not happen.
     * @param fb FilterBypass That can be used to mutate Document.
     * @param offset  The offset into the document to insert the content >= 0.
     *                All positions that track change at or after the given location 
     *                will move.  
     * @param length Length of text to delete.
     * @param text The string to insert
     * @param attrs The attributes to associate with the inserted
     *                      content.  This may be null if there are no attributes.
     * @throws BadLocationException  The given insert position is not a
     *                 valid position within the document.
     */
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws
            BadLocationException {
        if (this.length > 0 && fb.getDocument().getLength() + text.length() >
                this.length) {
            return;
        }
        super.replace(fb, offset, length, text, attrs);
    }
}
