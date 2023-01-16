/*
 * ApexMenu.java
 * Created on February 9, 2007, 6:36 PM
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

import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * An extension of {@code JMenu}. It provides handy constructors and methods
 * to easily deal with menu.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexMenu extends JMenu {

    /**
     *  The margin between the menu's border and
     * the label.
     */
    private static Insets menuItemGap = new Insets(0, 4, 0, 4);

    /**
     * Creates a new instance of {@code ApexMenu} using specified display text.
     * @param name The display text of menu.
     */
    public ApexMenu(String name) {
        this(name, ' ');
    }

    /**
     * Creates a new instance of {@code ApexMenu} using specified display text and mnemonic.
     * @param name The display text of menu.
     * @param mnemonic The keyboard mnemonic (shortcut key) for the menu.
     */
    public ApexMenu(String name, char mnemonic) {
        super(name);

        if (mnemonic != ' ') {
            this.setMnemonic(mnemonic);
        }
    }

    /**
     * Sets the margin between the menu's border and
     * the label.     
     */
    public void setDefaultMargin() {
        this.setMargin(menuItemGap);
    }

    /**
     * Creates a new instance of {@code ApexMenu} using specified display text and mnemonic. Also it adds
     * the newly created menu to specified container. 
     * @param name The display text of menu.
     * @param mnemonic The keyboard mnemonic (shortcut key) for the menu.
     * @param parent The {@code JComponent} where to add the menu.
     */
    public ApexMenu(String name, char mnemonic, JComponent parent) {
        super(name);
        if (mnemonic != ' ') {
            this.setMnemonic(mnemonic);
        }
        if (parent != null) {
            parent.add(this);
        }
    }

    /**
     * Creates a new instance of {@code ApexMenu} using specified display text, mnemonic and tooltip text. Also it adds
     * the newly created menu to specified container. 
     * @param name The display text of menu.
     * @param mnemonic The keyboard mnemonic (shortcut key) for the menu.
     * @param tooltipText The text to display in a tool tip.
     * @param parent The {@code JComponent} where to add the menu.
     */
    public ApexMenu(String name, char mnemonic, String tooltipText,
            JComponent parent) {
        super(name);
        if (mnemonic != ' ') {
            this.setMnemonic(mnemonic);
        }
        if (tooltipText != null) {
            this.setToolTipText(tooltipText);
        }
        if (parent != null) {
            if (parent instanceof JMenuBar) {
                setDefaultMargin();
            }
            parent.add(this);
        }
    }
}
