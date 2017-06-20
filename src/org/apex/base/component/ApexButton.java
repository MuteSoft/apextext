/*
 * ApexButton.java
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
import javax.swing.Action;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * An extension of {@code JButton}. It provides handy constructors and methods
 * to easily deal with button.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexButton extends JButton {

    /**
     * Creates a new button. A default text 'Click' is set to button.
     */
    public ApexButton() {
        this("Click");
    }

    /**
     * Creates a new button with initial icon.
     * @param icon The Icon image to display on the button.
     */
    public ApexButton(Icon icon) {
        this("");
        this.setIcon(icon);
    }

    /**
     * Creates a new button with initial text.
     * @param text The text of button.
     */
    public ApexButton(String text) {
        this(text, null);
    }

    /**
     * Creates a new button and sets specified {@code action}. 
     * Subsequently, the button's properties are automatically updated
     * as the {@code Action}'s properties change. Also it adds the newly created
     * button to specified tool bar.
     * @param action The {@code Action} to set.
     * @param parent The {@code JToolBar} where to add the button.
     */
    public ApexButton(Action action, JToolBar parent) {
        this();
        if (action != null) {
            this.setAction(action);
        }
        if (parent != null) {
            parent.add(this);
        }
    }

    /**
     * Creates a button with initial text and foreground color.
     * @param text The text of button.
     * @param foreground The foreground color of button.
     */
    public ApexButton(String text, Color foreground) {
        this(text, foreground, null);
    }

    /**
     * Creates a button with initial text, foreground color and background color.
     * @param text The text of button.
     * @param foreground The foreground color of button.
     * @param background The background color of button.
     */
    public ApexButton(String text, Color foreground, Color background) {
        this(text, foreground, background, null, null);
    }

    /**
     * Creates a button with initial text, foreground color background color and a specified font.
     * @param text The text of button.
     * @param foreground The foreground color of button.
     * @param background The background color of button.
     * @param font The font of button.
     */
    public ApexButton(String text, Color foreground, Color background,
            Font font) {
        this(text, foreground, background, font, null);
    }

    /**
     * Creates a button with initial text, foreground color, background color, an icon and
     * a specified font.
     * @param text The text of button.
     * @param foreground The foreground color of button.
     * @param background The background color of button.
     * @param font The font of button.
     * @param icon The Icon image to display on the button.
     */
    public ApexButton(String text, Color foreground, Color background,
            Font font, Icon icon) {
        super(text, icon);
        if (background != null) {
            this.setBackground(background);
        }
        if (foreground != null) {
            this.setForeground(foreground);
        }
        if (font != null) {
            this.setFont(font);
        }
        this.setFocusable(false);
    }

    /**
     * Removes the display text of this button.
     * @return This button.
     */
    public ApexButton removeText() {
        this.setText(null);
        return this;
    }
}
