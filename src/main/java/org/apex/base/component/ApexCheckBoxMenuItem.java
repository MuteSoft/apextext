/*
 * ApexCheckBoxMenuItem.java
 * Created on February 17, 2007, 3:16 PM
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
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * An extension of {@code JCheckBoxMenuItem}. It provides handy constructors and methods
 * to easily deal with check box menu item.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexCheckBoxMenuItem extends JCheckBoxMenuItem {

    /**
     * Creates a new instance of {@code ApexCheckBoxMenuItem} using specified
     * name.
     * @param name The label of check box.
     */
    public ApexCheckBoxMenuItem(String name) {
        this(name, ' ');
    }

    /**
     * Creates a new instance of {@code ApexCheckBoxMenuItem} using specified
     * name and mnemonic.
     * @param name The label of check box.
     * @param mnemonic The keyboard mnemonic (shortcut key) for the menu.
     */
    public ApexCheckBoxMenuItem(String name, char mnemonic) {
        this(name, mnemonic, null);
    }

    /**
     * Creates a new instance of {@code ApexCheckBoxMenuItem} using specified
     * {@code action}. Also it adds the newly created check box menu item to
     * specified menu.
     * @param action The {@code Action} to set.
     * @param parent The {@code ApexMenu} where to add the check box menu item.
     */
    public ApexCheckBoxMenuItem(Action action, ApexMenu parent) {
        this("", ' ', parent);
        this.setAction(action);
    }

    /**
     * Creates a new instance of {@code ApexCheckBoxMenuItem} using specified
     * {@code action}. Also it adds the newly created check box menu item to
     * specified container.
     * @param action The {@code Action} to set.
     * @param parent The {@code JComponent} where to add the check box menu item.
     */
    public ApexCheckBoxMenuItem(Action action, JComponent parent) {
        this("", ' ');
        this.setAction(action);
        if (parent != null) {
            parent.add(this);
        }
    }

    /**
     * Creates a new instance of {@code ApexCheckBoxMenuItem} using specified
     * name and mnemonic. Also it adds the newly created check box menu item to
     * specified menu.
     * @param name The label of check box.
     * @param mnemonic The keyboard mnemonic (shortcut key) for the menu.
     * @param parent The {@code ApexMenu} where to add the check box menu item.
     */
    public ApexCheckBoxMenuItem(String name, char mnemonic, ApexMenu parent) {
        this(name, mnemonic, null, null, parent);
    }

    /**
     * Creates a new instance of {@code ApexCheckBoxMenuItem} using specified
     * name and icon. Also it adds the newly created check box menu item to
     * specified menu.
     * @param name The label of check box.
     * @param icon The image icon for this menu item.
     * @param parent The {@code ApexMenu} where to add the check box menu item.
     */
    public ApexCheckBoxMenuItem(String name, Icon icon, ApexMenu parent) {
        this(name, ' ', icon, parent);
    }

    /**
     * Creates a new instance of {@code ApexCheckBoxMenuItem} using specified
     * name, mnemonic and accelerator. Also it adds the newly created check box menu item to
     * specified menu.
     * @param name The label of check box.
     * @param mnemonic The keyboard mnemonic (shortcut key) for the menu.
     * @param accelrtr The key combination which invokes the menu item's
     *              action listeners without navigating the menu hierarchy.
     * @param parent The {@code ApexMenu} where to add the check box menu item.
     */
    public ApexCheckBoxMenuItem(String name, char mnemonic, KeyStroke accelrtr, ApexMenu parent) {
        this(name, mnemonic, null, accelrtr, parent);
    }

    /**
     * Creates a new instance of {@code ApexCheckBoxMenuItem} using specified
     * name, mnemonic and icon. Also it adds the newly created check box menu item to
     * specified menu.
     * @param name The label of check box.
     * @param mnemonic The keyboard mnemonic (shortcut key) for the menu.
     * @param icon The image icon for this menu item.
     * @param parent The {@code ApexMenu} where to add the check box menu item.
     */
    public ApexCheckBoxMenuItem(String name, char mnemonic, Icon icon, ApexMenu parent) {
        this(name, mnemonic, icon, null, parent);
    }

    /**
     * Creates a new instance of {@code ApexCheckBoxMenuItem} using specified
     * name, mnemonic, icon and accelerator. Also it adds the newly created check box menu item to
     * specified menu.
     * @param name The label of check box.
     * @param mnemonic The keyboard mnemonic (shortcut key) for the menu.
     * @param icon The image icon for this menu item.
     * @param accelrtr The key combination which invokes the menu item's
     *              action listeners without navigating the menu hierarchy.
     * @param parent The {@code ApexMenu} where to add the check box menu item.
     */
    public ApexCheckBoxMenuItem(String name, char mnemonic, Icon icon, KeyStroke accelrtr,
            ApexMenu parent) {
        super(name, icon);
        if (mnemonic != ' ') {
            this.setMnemonic(mnemonic);
        }
        if (parent != null) {
            parent.add(this);
        }
        if (accelrtr != null) {
            this.setAccelerator(accelrtr);
        }
    }
}
