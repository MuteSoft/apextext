/*
 * ApexTextArea.java
 *
 * Copyright (C) 2010 Mrityunjoy Saha
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

import javax.swing.JTextArea;

/**
 * An extension of {@code JTextArea}. It provides handy constructors and methods
 * to easily deal with text area.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class ApexTextArea extends JTextArea {

    /**
     * Creates a new default instance of {@code ApexTextArea}.
     */
    public ApexTextArea() {
        this("");
    }

    /**
     * Creates a new instance of {@code ApexTextArea} with given text.
     * @param text The text to be displayed in text area.
     */
    public ApexTextArea(String text) {
        this(text, 2, 20);
    }

    /**
     * Creates a new instance of {@code ApexTextArea} with specified number of rows and columns.
     * @param rows The number of rows of text area.
     * @param columns The number of columns of text area.
     */
    public ApexTextArea(int rows, int columns) {
        this("", rows, columns);
    }

    /**
     * Creates a new instance of {@code ApexTextArea} with specified text, number of rows and columns.
     * @param text The text to be displayed in text area.
     * @param rows The number of rows of text area.
     * @param columns The number of columns of text area.
     */
    public ApexTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
    }
}
