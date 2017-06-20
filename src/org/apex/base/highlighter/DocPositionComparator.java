/*
 * DocPositionComparator.java
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

import java.util.Comparator;

/**
 * A comparator appropriate for use with Collections of
 * DocPositions.
 * @author Stephen Ostermiller
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
class DocPositionComparator implements Comparator {

    /**
     * A reference to itself.
     */
    public static final DocPositionComparator instance = new DocPositionComparator();

    /**
     * Constructs a new instance of {@code DocPositionComparator}.
     */
    private DocPositionComparator() {
    }

    /**
     * Does this Comparator equal another?
     * Since all DocPositionComparators are the same, they
     * are all equal.
     * @param obj The reference object with which to compare.
     * @return true for DocPositionComparators, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    /**
     * Compare two DocPositions.
     *
     * @param o1 first DocPosition.
     * @param o2 second DocPosition.
     * @return negative if first < second, 0 if equal, positive if first > second.
     */
    public int compare(Object o1, Object o2) {
        if (o1 instanceof DocPosition && o2 instanceof DocPosition) {
            DocPosition d1 = (DocPosition) (o1);
            DocPosition d2 = (DocPosition) (o2);
            return (d1.getPosition() - d2.getPosition());
        } else if (o1 instanceof DocPosition) {
            return -1;
        } else if (o2 instanceof DocPosition) {
            return 1;
        } else if (o1.hashCode() < o2.hashCode()) {
            return -1;
        } else if (o2.hashCode() > o1.hashCode()) {
            return 1;
        } else {
            return 0;
        }
    }
}

