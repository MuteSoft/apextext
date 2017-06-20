/*
 * SplittedComponent.java
 * Created on 28 June, 2007, 2:14 AM  
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

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JSplitPane;

/**
 * A component which holds a split pane and also provides provision
 * to easily set components at both sides of divider.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SplittedComponent extends ApexPanel {

    /**
     * The split pane.
     */
    private ApexSplitPane bodySplitPane;

    /**
     * Creates a new instance of {@code SplittedComponent}.
     */
    public SplittedComponent() {
        this(null, null, 0);
    }

    /**
     * Creates a new instance of {@code SplittedComponent} using specified components
     * and divider location. By default it does horizontal split.
     * @param left The left component.
     * @param right The right component.
     * @param dividerLocation The divider location.
     */
    public SplittedComponent(Component left,
                             Component right, int dividerLocation) {
        this(JSplitPane.HORIZONTAL_SPLIT, left, right, dividerLocation);
    }

    /**
     * Creates a new instance of {@code SplittedComponent} using specified orientation,
     * components and divider location.
     * @param orientation The orientation. It can be {@code JSplitPane.HORIZONTAL_SPLIT}
     *              or {@code JSplitPane.VERTICAL_SPLIT}.
     * @param left The left or top component.
     * @param right The right or bottom component.
     * @param dividerLocation The divider location.
     */
    public SplittedComponent(int orientation, Component left,
                             Component right, int dividerLocation) {
        createComparatorBody(orientation, left, right, dividerLocation);
    }

    /**
     * Creates the split pane and sets components at both sides of the divider.
     * @param orientation The orientation. It can be {@code JSplitPane.HORIZONTAL_SPLIT}
     *              or {@code JSplitPane.VERTICAL_SPLIT}.
     * @param left The left component.
     * @param right The right component.
     * @param dividerLocation The divider location.
     */
    private void createComparatorBody(int orientation,
                                      Component left, Component right,
                                      int dividerLocation) {
        this.setLayout(new BorderLayout());
        bodySplitPane = new ApexSplitPane(orientation, false);
        bodySplitPane.setLeftComponent(left);
        bodySplitPane.setRightComponent(right);
        if (dividerLocation != 0) {
            bodySplitPane.setDividerLocation(dividerLocation);
        }
        this.add(bodySplitPane);
    }

    /**
     * Returns the split pane.
     * @return The split pane.
     */
    public ApexSplitPane getSplitPane() {
        return bodySplitPane;
    }
}
