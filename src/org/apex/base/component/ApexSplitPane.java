/*
 * ApexSplitPane.java
 * Created on February 25, 2007, 11:22 AM
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

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

/**
 * An extension of {@code JSplitPane}. It provides handy constructors and methods
 * to easily deal with split pane.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexSplitPane extends JSplitPane {

    /**
     * Creates a new instance of {@code ApexSplitPane} with the specified
     * orientation and redrawing style.
     * @param orientation The orientation. It can be {@code JSplitPane.HORIZONTAL_SPLIT} or
     *               {@code JSplitPane.VERTICAL_SPLIT}.
     * @param newContinuousLayout A boolean, {@code true} for the components to 
     *                redraw continuously as the divider changes position, {@code false}
     *                to wait until the divider position stops changing to redraw
     */
    public ApexSplitPane(int orientation, boolean newContinuousLayout) {
        super(orientation, newContinuousLayout);
        this.setDividerSize(3);
        this.setBorder(BorderFactory.createEmptyBorder());
    }
}