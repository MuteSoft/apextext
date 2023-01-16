/*
 * DocumentSelectorListModel.java
 * Created on February 25, 2007, 9:59 PM
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

import org.apex.base.component.ApexLabel;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import org.apex.base.constant.FontConstants;

/**
 * A data model for left hand side list document selector.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentSelectorListModel extends DefaultListModel {

    /**
     * A list of additional information of documents added to document selector.
     */
    private Vector<String> additionalInfo = new Vector<String>();

    /** 
     * Creates a new instance of {@code DocumentSelectorListModel}.
     */
    public DocumentSelectorListModel() {
    }

    /**
     * Adds a document as {@code ApexLabel} to the end of list.
     * @param element The document to be added. 
     * @param additionalInfo Additional information of document. Typically this is
     *              absolute path of document in file system.
     */
    public void addElement(String element, String additionalInfo) {
        this.additionalInfo.add(additionalInfo);
        ApexLabel label = new ApexLabel(element);
        additionalInfo = additionalInfo == null
                ? element
                : additionalInfo;
        label.setToolTipText(additionalInfo);        
        super.addElement(label);
    }

    /**
     * Returns the document at the specified index.
     * @param index An index into this list.
     * @return The document at the specified index
     */
    @Override
    public Object getElementAt(int index) {
        return super.getElementAt(index);
    }

    /**
     * Deletes the document at the specified index.
     * @param index The index of document to remove.
     */
    @Override
    public void removeElementAt(int index) {
        this.additionalInfo.removeElementAt(index);
        super.removeElementAt(index);
    }

    /**
     * Sets the document at the specified {@code index} of this 
     * list to be the specified document. The previous document at that 
     * position is discarded.
     * @param index The specified index.
     * @param element The document is to be set to.
     * @param additionalInfo The additional information of document.
     */
    public void updateElementAt(int index, String element,
            String additionalInfo) {
        this.additionalInfo.setElementAt(additionalInfo, index);
        ApexLabel label = new ApexLabel(element);
        additionalInfo = additionalInfo == null
                ? element
                : additionalInfo;
        label.setToolTipText(additionalInfo);
        super.setElementAt(label, index);
    }

    /**
     * Returns additional information of document at specified index.
     * @param index The specified index.
     * @return The additional information of document.
     */
    public String getAdditionalInfoAt(int index) {
        return this.additionalInfo.get(index);
    }

    /**
     * Removes all docments from this list and sets its size to zero.
     */
    @Override
    public void removeAllElements() {
        this.additionalInfo.clear();
        super.removeAllElements();
    }
}
