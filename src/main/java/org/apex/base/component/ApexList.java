/*
 * ApexList.java
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

import java.awt.Color;
import java.awt.Font;

import java.util.Vector;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * An extension of {@code JList}. It provides handy constructors and methods
 * to easily deal with list.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexList extends JList implements ListSelectionListener {

    /**
     * Creates a new instance of {@code ApexList}. The list does not contain any element.
     */
    public ApexList() {
        this(new Vector());
    }

    /**
     * Creates a new instance of {@code ApexList} using specified list of elements.
     * @param menuList A list of elements.
     */
    public ApexList(Vector menuList) {
        this(menuList, null, null);
    }

    /**
     * Creates a new instance of {@code ApexList} using specified list of elements, foreground color and background color
     * of elements.
     * @param menuList A list of elements.
     * @param foreground Foreground color of elements in list.
     * @param background Background color of elements in list.
     */
    public ApexList(Vector menuList, Color foreground, Color background) {
        this(menuList, foreground, background, null);
    }

    /**
     * Creates a new instance of {@code ApexList} using specified list model, foreground color and background color
     * of elements.
     * @param model The list model.
     * @param foreground Foreground color of elements in list.
     * @param background Background color of elements in list.
     */
    public ApexList(ListModel model, Color foreground, Color background) {
        super(model);
        this.setSelectionBackground(background);
        this.setSelectionForeground(foreground);
        this.addListSelectionListener(this);
    }

    /**
     * Creates a new instance of {@code ApexList} using specified list of elements, foreground color and background color
     * of elements and font of elements.
     * @param menuList A list of elements.
     * @param foreground Foreground color of elements in list.
     * @param background Background color of elements in list.
     * @param font Font of elements in list.
     */
    public ApexList(Vector menuList, Color foreground, Color background,
                Font font) {
        super(menuList);
        if (background != null) {
            this.setBackground(background);
        }
        if (foreground != null) {
            this.setForeground(foreground);
        }
        if (font != null) {
            this.setFont(font);
        }
        this.addListSelectionListener(this);
    }

    /**
     * 
     * @param e 
     */
    public void valueChanged(ListSelectionEvent e) {
        task(e);
    }

    /**
     * Called whenever the value of the selection changes.
     * @param event The event that characterizes the change.
     */
    public void task(ListSelectionEvent event) {
    }

    /**
     * Selects a single cell. Does nothing if the given index is greater
     * than or equal to the model size. 
     * <p>
     * This is a convenience method to select a specific cell and at
     * the same time make the selected cell visible.
     * @param index The index of the cell to select.
     */
    @Override
    public void setSelectedIndex(int index) {
        super.setSelectedIndex(index);
        try {
            this.ensureIndexIsVisible(index);
        } catch (Exception ex) {
            // Do nothing.
        }
    }
}
