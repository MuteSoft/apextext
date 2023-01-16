/*
 * CCodeCompletionPopup.java 
 * Created on 1 Nov, 2007, 1:40:36 PM
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
package org.apex.base.codecompletion;

import java.util.Vector;

/**
 * A code completion popup for C document.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CCodeCompletionPopup extends CodeCompletionPopup {

    /**
     * Constructs a new instance of {@code CCodeCompletionPopup}.
     */
    public CCodeCompletionPopup() {
    }

    protected Vector<String> populateCodeCompletionData() {
        Vector<String> keys = new Vector<String>();
        keys.add("void");
        keys.add("main");
        return keys;
    }
}
