/*
 * DocPosition.java
 * 
 * This file is part of the programmer editor demo
 * Copyright (C) 2001-2005 Stephen Ostermiller
 * http://ostermiller.org/contact.pl?regarding=Syntax+Highlighting
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
package org.apex.base.highlighter;

/**
 * A wrapper for a position in a document appropriate for storing
 * in a collection.
 * @author Stephen Ostermiller
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocPosition {

    /**
     * The actual position.
     */
    private int position;

    /**
     * Get the position represented by this DocPosition.
     *
     * @return The position.
     */
    int getPosition() {
        return position;
    }

    /**
     * Construct a DocPosition from the given offset into the document.
     *
     * @param position The position this DocObject will represent.
     */
    public DocPosition(int position) {
        this.position = position;
    }

    /**
     * Adjust this position.
     * This is useful in cases that an amount of text is inserted
     * or removed before this position.
     *
     * @param adjustment amount (either positive or negative) to adjust this position.
     * @return the DocPosition, adjusted properly.
     */
    public DocPosition adjustPosition(int adjustment) {
        position += adjustment;
        return this;
    }

    /**
     * Two DocPositions are equal iff they have the same internal position.
     * 
     * @return if this DocPosition represents the same position as another.
     * @param obj The reference object with which to compare.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DocPosition) {
            DocPosition d = (DocPosition) (obj);
            if (this.position == d.position) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.position;
        return hash;
    }

    /**
     * A string representation useful for debugging.
     *
     * @return A string representing the position.
     */
    @Override
    public String toString() {
        return "" + position;
    }
}
