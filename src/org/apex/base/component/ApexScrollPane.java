/*
 * ApexScrollPane.java
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

import javax.swing.JComponent;
import javax.swing.JScrollPane;

/**
 * Provides a scrollable view of a lightweight component.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexScrollPane extends JScrollPane {

    /**
     * Creates a new instance of {@code ApexScrollPane}. By default it sets vertical
     * and horizontal scrollbar policies as {@code VERTICAL_SCROLLBAR_AS_NEEDED} and
     * {@code HORIZONTAL_SCROLLBAR_AS_NEEDED} correspondingly.
     * @see JScrollPane#VERTICAL_SCROLLBAR_AS_NEEDED
     * @see JScrollPane#HORIZONTAL_SCROLLBAR_AS_NEEDED
     */
    public ApexScrollPane() {
        this(null, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Creates a new instance of {@code ApexScrollPane} using specified horizontal
     * and vertical scrollbar policies.
     * @param comp The component to display in the scrollpanes viewport.
     * @param vert An integer that specifies the vertical
     *                scrollbar policy.
     * @param horz An integer that specifies the horizontal
     *                scrollbar policy.
     */
    public ApexScrollPane(JComponent comp, int vert, int horz) {
        super(comp, vert, horz);
    }
}
