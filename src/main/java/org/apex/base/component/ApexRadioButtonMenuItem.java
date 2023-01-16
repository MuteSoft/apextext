/*
 * ApexRadioButtonMenuItem.java
 * Created on 26 June, 2007, 11:58 PM
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

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JRadioButtonMenuItem;

/**
 * An extension of {@code JRadioButtonMenuItem}. It provides handy constructors and methods
 * to easily deal with radio button menu item.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexRadioButtonMenuItem extends JRadioButtonMenuItem {

    /**
     * Creates a new instance of {@code ApexRadioButtonMenuItem} using specified action and radio button
     * selection indicator.
     * @param action The {@code Action} to set.
     * @param selected Radio button selection indicator.
     */
    public ApexRadioButtonMenuItem(Action action, boolean selected) {
        this(action, selected, null, null);
    }

    /**
     * Creates a new instance of {@code ApexRadioButtonMenuItem} using specified action. Also it adds
     * the newly created radio button menu item to specified container. 
     * @param action The {@code Action} to set.
     * @param parent The {@code ApexMenu} where to add the radio button menu item.
     */
    public ApexRadioButtonMenuItem(Action action, ApexMenu parent) {
        this(action, false, parent, null);
    }

    /**
     * Creates a new instance of {@code ApexRadioButtonMenuItem} using specified action. Also it adds
     * the newly created radio button menu item to specified container and button group. 
     * @param action The {@code Action} to set.
     * @param parent The {@code ApexMenu} where to add the radio button menu item.
     * @param group The {@code ButtonGroup} where to add the radio button menu item.
     */
    public ApexRadioButtonMenuItem(Action action, ApexMenu parent, ButtonGroup group) {
        this(action, false, parent, group);
    }

    /**
     * Creates a new instance of {@code ApexRadioButtonMenuItem} using specified action and radio
     * button selection indicator. Also it adds the newly created radio button menu item to specified
     * container and button group. 
     * @param action The {@code Action} to set.
     * @param selected Radio button selection indicator.
     * @param parent The {@code ApexMenu} where to add the radio button menu item.
     * @param group The {@code ButtonGroup} where to add the radio button menu item.
     */
    public ApexRadioButtonMenuItem(Action action, boolean selected, ApexMenu parent,
                               ButtonGroup group) {
        super(action);
        this.setSelected(selected);
        if (parent != null) {
            parent.add(this);
        }
        if (group != null) {
            group.add(this);
        }
    }

    /**
     * Creates a new instance of {@code ApexRadioButtonMenuItem} using specified action and radio
     * button selection indicator. Also it adds the newly created radio button menu item to specified
     * container and button group. 
     * @param action The {@code Action} to set.
     * @param selected Radio button selection indicator.
     * @param parent The {@code JComponent} where to add the radio button menu item.
     * @param group The {@code ButtonGroup} where to add the radio button menu item.
     */
    public ApexRadioButtonMenuItem(Action action, boolean selected,
                               JComponent parent, ButtonGroup group) {
        super(action);
        this.setSelected(selected);
        if (parent != null) {
            parent.add(this);
        }
        if (group != null) {           
            group.add(this);
        }
    }
}
