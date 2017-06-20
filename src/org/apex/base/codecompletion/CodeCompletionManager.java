/*
 * CodeCompletionManager.java
 * Created on 1 Nov, 2007, 1:31:49 PM
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

import org.apex.base.data.AbstractDocument;

/**
 * The code completion manager. It maintains code completion popups for
 * different document types.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class CodeCompletionManager {

    /**
     * A reference to itself.
     */
    private static CodeCompletionManager codeCompletionManager;

    /**
     * Constructs a new instance of {@code CodeCompletionManager}.
     */
    private CodeCompletionManager() {
    }

    /**
     * Returns the code completion popup for specified document type.
     * @param doc The document.
     * @return The code completion popup.
     */
    public CodeCompletionPopup getPopup(AbstractDocument doc) {
        return doc.getCodeCompletionPopup();
    }

    /**
     * Returns a singleton instance of this class.
     * @return A singleton instance.
     */
    public static CodeCompletionManager getInstance() {
        if (codeCompletionManager == null) {
            codeCompletionManager =
                    new CodeCompletionManager();
        }
        return codeCompletionManager;
    }
}
