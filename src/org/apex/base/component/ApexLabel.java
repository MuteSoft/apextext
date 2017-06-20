/*
 * ApexLabel.java
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

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * An extension of {@code JLabel}. It provides handy constructors and methods
 * to easily deal with label.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexLabel extends JLabel {

    /**
     * Constructs a new {@code ApexLabel}. A default text 'ApexLabel'
     * is displayed as label text.
     */
    public ApexLabel() {
        this("Label");
    }

    /**
     * Constructs a new {@code ApexLabel} using specified text.
     * @param text The text to be displayed by the label.
     */
    public ApexLabel(String text) {
        this(text, null);
    }

    /**
     * Constructs a new {@code ApexLabel} using specified icon.
     * @param icon The icon this component will display.
     */
    public ApexLabel(Icon icon) {
        super(icon);
    }

    /**
     * Constructs a new {@code ApexLabel} using specified text and foreground text.
     * @param text The text to be displayed by the label.
     * @param foreground The foreground color of label.
     */
    public ApexLabel(String text, Color foreground) {
        this(text, foreground, null);
    }

    /**
     * Constructs a new {@code ApexLabel} using specified text, foreground and background color.
     * @param text The text to be displayed by the label.
     * @param foreground The foreground color of label.
     * @param background The background color of label.
     */
    public ApexLabel(String text, Color foreground, Color background) {
        this(text, foreground, background, null, null);
    }

    /**
     * Constructs a new {@code ApexLabel} using specified text, foreground and background color and font.
     * @param text The text to be displayed by the label.
     * @param foreground The foreground color of label.
     * @param background The background color of label.
     * @param font The font of the label.
     */
    public ApexLabel(String text, Color foreground, Color background, Font font) {
        this(text, foreground, background, font, null);
    }

    /**
     * Constructs a new {@code ApexLabel} using specified text, foreground and background color, font and
     * icon.
     * @param text The text to be displayed by the label.
     * @param foreground The foreground color of label.
     * @param background The background color of label.
     * @param font The font of the label.
     * @param icon The icon this component will display.
     */
    public ApexLabel(String text, Color foreground, Color background, Font font,
            Icon icon) {
        super(text);
        this.setIcon(icon);
        if (background != null) {
            this.setBackground(background);
        }
        if (foreground != null) {
            this.setForeground(foreground);
        }
        if (font != null) {
            this.setFont(font);
        }
    }

    /**
     * Returns a string representation of this component and its values.
     * @return A string representation of this component.
     */
    @Override
    public String toString() {
        return this.getText();
    }
}
