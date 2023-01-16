/*
 * ApexMenuItem.java
 * Created on February 9, 2007, 6:39 PM  
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
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

/**
 * An extension of {@code ApexMenuItem}. It provides handy constructors and methods
 * to easily deal with menu item.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexMenuItem extends JMenuItem {

    /**
     * Creates a new instance of {@code ApexMenuItem} using specified display text.
     * @param name The display text of menu item.
     */
    public ApexMenuItem(String name) {
        this(name, ' ');
    }

    /**
     * Creates a new instance of {@code ApexMenuItem} using specified display text
     * and mnemonic.
     * @param name The display text of menu item.
     * @param mnemonic The keyboard mnemonic (shortcut key) for the menu item.
     */
    public ApexMenuItem(String name, char mnemonic) {
        this(name, mnemonic, null);
    }

    /**
     * Creates a new instance of {@code ApexMenuItem} using specified action. Also it adds
     * the newly created menu item to specified container. 
     * @param action The {@code Action} to set.
     * @param parent The {@code Menu} where to add the menu item.
     */
    public ApexMenuItem(Action action, ApexMenuItem parent) {
        //this("", ' ', parent);
        this.setAction(action);
    }

    /**
     * Creates a new instance of {@code ApexMenuItem} using specified action. Also it adds
     * the newly created menu item to specified container. 
     * @param action The {@code Action} to set.
     * @param parent The {@code JComponent} where to add the menu item.
     */
    public ApexMenuItem(Action action, JComponent parent) {
        this("", ' ');
        this.setAction(action);
        if (parent != null) {
            parent.add(this);
        }
    }

    /**
     * Creates a menu item with specified action.
     * @param action The {@code Action} to set. 
     */
    public ApexMenuItem(Action action) {        
        this.setAction(action);
    }

    /**
     * Creates a new instance of {@code ApexMenuItem} using specified action. Also it adds
     * the newly created menu item to specified container. 
     * @param action The {@code Action} to set.
     * @param parent The {@code JPopupMenu} where to add the menu item.
     */
    public ApexMenuItem(Action action, JPopupMenu parent) {
        super(action);
        if (parent != null) {
            parent.add(this);
        }
    }

    /**
     * Creates a new instance of {@code ApexMenuItem} using specified display text
     * and mnemonic. Also it adds the newly created menu item to specified container. 
     * @param name The display text of menu item.
     * @param mnemonic The keyboard mnemonic (shortcut key) for the menu item.
     * @param parent The {@code ApexMenuItem} where to add the menu item.
     */
    public ApexMenuItem(String name, char mnemonic, ApexMenuItem parent) {
        this(name, mnemonic, null, null, parent);
    }

    /**
     * 
     * Creates a new instance of {@code ApexMenuItem} using specified display text
     * and icon. Also it adds the newly created menu item to specified container. 
     * @param name The display text of menu item.
     * @param icon The icon of the {@code ApexMenuItem}.
     * @param parent The {@code ApexMenuItem} where to add the menu item.
     */
    public ApexMenuItem(String name, Icon icon, ApexMenuItem parent) {
        this(name, ' ', icon, parent);
    }

    /**
     * Creates a new instance of {@code ApexMenuItem} using specified display text,
     * mnemonic and accelerator. Also it adds
     * the newly created menu item to specified container. 
     * @param name The display text of menu item.
     * @param mnemonic The keyboard mnemonic for the {@code ApexMenuItem}.
     * @param accelrtr The key combination which invokes the menu item's
     *                action listeners without navigating the menu hierarchy.
     * @param parent The {@code ApexMenuItem} where to add the menu item.
     */
    public ApexMenuItem(String name, char mnemonic, KeyStroke accelrtr,
            ApexMenuItem parent) {
        this(name, mnemonic, null, accelrtr, parent);
    }

    /**
     * Creates a new instance of {@code ApexMenuItem} using specified display text, mnemonic
     * and icon. Also it adds the newly created menu item to specified container. 
     * @param name The display text of menu item.
     * @param mnemonic The keyboard mnemonic for the {@code ApexMenuItem}.
     * @param icon The icon of the {@code ApexMenuItem}.
     * @param parent The {@code ApexMenuItem} where to add the menu item.
     */
    public ApexMenuItem(String name, char mnemonic, Icon icon, ApexMenuItem parent) {
        this(name, mnemonic, icon, null, parent);
    }

    /**
     * Creates a new instance of {@code ApexMenuItem} using specified display text, mnemonic
     * icon and accelerator. Also it adds the newly created menu item to specified container. 
     * @param name The display text of menu item.
     * @param mnemonic The keyboard mnemonic for the {@code ApexMenuItem}.
     * @param icon The icon of the {@code ApexMenuItem}.
     * @param accelrtr The key combination which invokes the menu item's
     *                action listeners without navigating the menu hierarchy.
     * @param parent The {@code ApexMenuItem} where to add the menu item.
     */
    public ApexMenuItem(String name, char mnemonic, Icon icon, KeyStroke accelrtr,
            ApexMenuItem parent) {
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

    @Override
    public String toString() {       
        return getText();
    }
}
