/*
 * ApexPanel.java
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


import javax.swing.JPanel;

/**
 * An extension of {@code JPanel}. It provides handy constructors and methods
 * to easily deal with panel.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexPanel extends JPanel {

    /**
     * Creates a new instance of {@code ApexPanel}.
     */
    public ApexPanel() {
        this((String) null);
    }

    /**
     * Creates a new instance of {@code ApexPanel} using specified
     * tootip text.
     * @param text The tooltip text.
     */
    public ApexPanel(String text) {
        this(text, null);
    }

    /**
     * Creates a new instance of {@code ApexPanel} using specified background color.
     * @param background The background color of {@code ApexPanel}.
     */
    public ApexPanel(Color background) {
        this("", background);
    }

    /**
     * Creates a new instance of {@code ApexPanel} using specified tooltip text and background color.
     * @param text The tooltip text.
     * @param background The background color of {@code ApexPanel}.
     */
    public ApexPanel(String text, Color background) {
        super();
        if (text != null) {
            this.setToolTipText(text);
        }
        if (background != null) {
            this.setBackground(background);
        }
    }
}
