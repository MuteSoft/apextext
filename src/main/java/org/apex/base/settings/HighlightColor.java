/*
 * HighlightColor.java
 * Created on 14 July, 2007, 1:36 PM
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
package org.apex.base.settings;

import java.awt.Color;

/**
 * A highlight color object encapsulates foreground color and background color.
 * Highlight category like 'Search' can have a hifglight color which will be used to
 * highlight search results.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class HighlightColor implements Cloneable {

    /**
     * Foreground color of highlight.
     */
    private Color foreground;
    /**
     * Background color of highlight.
     */
    private Color background;

    /**
     * Creates a new instance of {@code HighlightColor}.
     */
    public HighlightColor() {
    }

    /**
     * Returns the highlight foreground color.
     * @return The highlight foreground color.
     * @see #setForeground(java.awt.Color) 
     */
    public Color getForeground() {
        return foreground;
    }

    /**
     * Sets the highlight foreground color.
     * @param foreground The highlight foreground color.
     * @see #getForeground() 
     */
    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    /**
     * Returns the highlight background color.
     * @return The highlight background color.
     * @see #setBackground(java.awt.Color) 
     */
    public Color getBackground() {
        return background;
    }

    /**
     * Sets the highlight background color.
     * @param background The highlight background color.
     * @see #getBackground() 
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    /**
     * Creates and returns a deep copy of this {@code HighlightColor} object.
     * @return A clone of this instance.
     */
    @Override
    protected Object clone() {
        HighlightColor highlight = null;
        try {
            highlight = (HighlightColor) super.clone();
        //highlight.setBackground(background.);
        } catch (CloneNotSupportedException ex) {
        }
        return highlight;
    }

    /**
     * Returns human readable text representation of this highlight color object.
     * @return Text representation of this object.
     */
    @Override
    public String toString() {
        return "foreground: " + foreground + "^background: " + background;
    }

    /**
     * Indicates whether some other highlight color object is "equal to" this one.
     * @param obj The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     *               argument; {@code false} otherwise.
     * @see #hashCode()     
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof HighlightColor) {
            HighlightColor testHighlight =
                    (HighlightColor) obj;
            return (this.background == null
                    ? testHighlight.getBackground() == null
                    : this.background.equals(testHighlight.getBackground())) &&
                    (this.foreground == null
                    ? testHighlight.getForeground() == null
                    : this.foreground.equals(testHighlight.getForeground()));
        }
        return false;
    }

    /**
     * Returns a hash code value for this highlight color object.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash +
                (this.foreground != null
                ? this.foreground.hashCode()
                : 0);
        hash = 61 * hash +
                (this.background != null
                ? this.background.hashCode()
                : 0);
        return hash;
    }

    /**
     * Cleans up this highlight color object.
     */
    public void dispose() {
        this.background = null;
        this.foreground = null;
    }
}