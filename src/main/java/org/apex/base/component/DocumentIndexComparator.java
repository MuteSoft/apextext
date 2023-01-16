/*
 * DocumentIndexComparator.java 
 * Created on 29 Nov, 2008, 12:39:01 AM
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
package org.apex.base.component;

import java.util.Comparator;
import org.apex.base.data.AbstractDocument;

/**
 * A comparator to compare documents by their index.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.1
 */
public class DocumentIndexComparator implements Comparator<AbstractDocument> {

    /**
     * Constructs a new instance of {@code DocumentIndexComparator}.
     */
    public DocumentIndexComparator() {
    }

    /**
     * Compares index of two given documents.
     * @param o1 The first document.
     * @param o2 The second document.
     * @return Positive value if the first document's index is greater than the second one, negative value in
     * opposite case. And returns zero if both the indexes are equal.
     */
    public int compare(AbstractDocument o1, AbstractDocument o2) {
        return o1.getIndex() - o2.getIndex();
    }
}
