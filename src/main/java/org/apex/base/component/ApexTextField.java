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

import javax.swing.JTextField;

/**
 * An extension of {@code JTextField}. It provides handy constructors and methods
 * to easily deal with text field.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class ApexTextField extends JTextField {

    /**
     * Creates a new default instance of {@code ApexTextField}.
     */
    public ApexTextField() {
        this("");
    }

    /**
     * Creates a new instance of {@code ApexTextField} with given text.
     * @param text The text to be displayed in text field.
     */
    public ApexTextField(String text) {
        this(text, 10);
    }

    /**
     * Creates a new instance of {@code ApexTextField} with specified size (number of columns).
     * @param size The size of the text field.
     */
    public ApexTextField(int size) {
        this("", size);
    }

    /**
     * Creates a new instance of {@code ApexTextField} with specified text and size (number of columns).
     * @param text The text to be displayed in text field.
     * @param size The size of the text field.
     */
    public ApexTextField(String text, int size) {
        super(text, size);
    }
}
