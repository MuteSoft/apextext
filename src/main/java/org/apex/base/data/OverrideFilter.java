/*
 * OverrideFilter.java
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
 * This filter is to support Insert/Override typing mode in edit area of editor.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 * @see TypingMode
 */
public class OverrideFilter extends DocumentFilter {

    /**
     * Creates a new instance of OverrideFilter.
     */
    public OverrideFilter() {
    }

    /**
     * Based on current typing mode set in the editor either inserts or
     * overrites given String.
     * @param fb FilterBypass That can be used to mutate Document.
     * @param offset  The offset into the document to insert the content >= 0.
     *                All positions that track change at or after the given location 
     *                will move.  
     * @param string The string to insert
     * @param attr The attributes to associate with the inserted
     *                      content.  This may be null if there are no attributes.
     * @throws BadLocationException  The given insert position is not a
     *                 valid position within the document
     */
    @Override
    public void insertString(FilterBypass fb, int offset, String string,
            AttributeSet attr) throws BadLocationException {
        TypingMode mode = fb.getDocument().getProperty("TypingMode") == null
                ? TypingMode.INSERT
                : (TypingMode) fb.getDocument().getProperty("TypingMode");
        // Fix for bug id 2895007 (sourceforge.net)
        if (mode == TypingMode.OVERWRITE && string.length() ==
                1 && offset != fb.getDocument().getLength() && !fb.getDocument().
                getText(offset, 1).equals("\n")) {
            super.replace(fb, offset, 1, string, attr);
        } else {
            super.insertString(fb, offset, string, attr);
        }
    }
}
