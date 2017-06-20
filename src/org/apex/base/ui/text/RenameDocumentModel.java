/*
 * RenameDocumentModel.java 
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
package org.apex.base.ui.text;

import org.apex.base.data.UIDataModel;

/**
 * A data model used to rename document.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class RenameDocumentModel implements UIDataModel {

    /**
     * The target document name.
     */
    private String documentName;

    /**
     * Returns the target document name.
     * @return The target document name.
     * @see #setDocumentName(java.lang.String) 
     */
    public String getDocumentName() {
        return documentName;
    }

    /**
     * Sets the target document name.
     * @param documentName The target document name.
     * @see #getDocumentName() 
     */
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
}
