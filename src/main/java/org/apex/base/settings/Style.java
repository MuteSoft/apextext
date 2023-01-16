/*
 * Style.java
 * Created on 14 July, 2007, 1:31 PM
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

import org.apex.base.util.ConfigurationUtility;
import org.apex.base.constant.CommonConstants;
import org.apex.base.util.StringUtil;
import java.awt.Color;
import java.awt.Font;
import org.apex.base.data.Fonts;

/** 
 * A {@code Style} object holds style information of a particular type
 * of syntax token. {@code Style} objects are used while doing synatx
 * highlighting on a document.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class Style implements Cloneable {

    /**
     * A separator between multiple style properties.
     */
    public static final String INTRA_SEPARATOR = ",";
    /**
     * The syntax token name.
     */
    private String syntaxName;
    /**
     * Font style.
     */
    private String fontStyle;
    /**
     * Foregorund color.
     */
    private Color foreGround;
    /**
     * Background color.
     */
    private Color backGround;

    /**
     * Creates a new instance of {@code Style} using specified synatx name.
     * @param syntaxName The syntax token name.
     */
    public Style(String syntaxName) {
        if (StringUtil.isNullOrEmpty(syntaxName)) {
            throw new IllegalArgumentException(
                    "Syntax name can not be null or empty string.");
        }
        this.syntaxName = syntaxName;
    }

    /**
     * Returns the syntax token name.
     * @return Syntax token name.
     * @see #setSyntaxName(java.lang.String) 
     */
    public String getSyntaxName() {
        return syntaxName;
    }

    /**
     * Sets the syntax token name.
     * @param syntaxName Syntax token name.
     * @see #getSyntaxName() 
     */
    public void setSyntaxName(String syntaxName) {
        this.syntaxName = syntaxName;
    }

    /**
     * Returns the font style.
     * @return The font style.
     * @see #setFontStyle(java.lang.String) 
     */
    public String getFontStyle() {
        return fontStyle;
    }

    /**
     * Sets the font style.
     * @param fontStyle The font style.
     * @see #getFontStyle() 
     */
    public void setFontStyle(String fontStyle) {
        if (!StringUtil.isNullOrEmpty(fontStyle)) {
            this.fontStyle = fontStyle;
        }
    }

    /**
     * Creates and returns a deep copy of this {@code Style} object.
     * @return A clone of this instance.
     */
    @Override
    protected Object clone() {
        Style style = null;
        try {
            style = (Style) super.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return style;
    }

    /**
     * Returns the foreground color.
     * @return The foreground color.
     * @see #setForeground(java.awt.Color) 
     */
    public Color getForeground() {
        return foreGround;
    }

    /**
     * Sets the foreground color.
     * @param color The foreground color.
     * @see #getForeground() 
     */
    public void setForeground(Color color) {
        this.foreGround = color;
    }

    /**
     * Based on font style value it determines whether or
     * not style is <b>bold</b>.
     * @return {@code true} if style is bold; otherwise returns {@code false}.
     */
    public boolean isBold() {
        if (this.fontStyle == null) {
            return false;
        }
        int intValue = Fonts.getIntFontStyle(fontStyle);
        if (intValue == Font.BOLD ||
                intValue == (Font.BOLD | Font.ITALIC)) {
            return true;
        }
        return false;
    }

    /**
     * Based on font style value it determines whether or
     * not style is <i>italic</i>.
     * @return {@code true} if style is italic; otherwise returns {@code false}.
     */
    public boolean isItalic() {
        if (this.fontStyle == null) {
            return false;
        }
        int intValue = Fonts.getIntFontStyle(fontStyle);
        if (intValue == Font.ITALIC ||
                (intValue == (Font.BOLD | Font.ITALIC))) {
            return true;
        }
        return false;
    }

    /**
     * Returns human readable text representation of this style object.
     * @return Text representation of this object.
     */
    @Override
    public String toString() {
        return (getSyntaxName() + INTRA_SEPARATOR +
                (getFontStyle() == null
                ? CommonConstants.BLANK_TEXT
                : getFontStyle()) + INTRA_SEPARATOR +
                (getForeground() == null
                ? CommonConstants.BLANK_TEXT
                : ConfigurationUtility.convertToString(getForeground())));
    }

    /**
     * Indicates whether some other style object is "equal to" this one.
     * @param obj The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     *               argument; {@code false} otherwise.
     * @see #hashCode()     
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Style) {
            Style testStyle = (Style) obj;
            return (this.syntaxName == null
                    ? testStyle.getSyntaxName() == null
                    : this.syntaxName.equals(testStyle.getSyntaxName())) &&
                    (this.fontStyle == null
                    ? testStyle.getFontStyle() == null
                    : this.fontStyle.equals(testStyle.getFontStyle())) &&
                    (this.foreGround == null
                    ? testStyle.getForeground() == null
                    : this.foreGround.equals(testStyle.getForeground()));
        }
        return false;
    }

    /**
     * Returns a hash code value for this style object.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash +
                (this.syntaxName != null
                ? this.syntaxName.hashCode()
                : 0);
        hash = 73 * hash +
                (this.fontStyle != null
                ? this.fontStyle.hashCode()
                : 0);
        hash = 73 * hash +
                (this.foreGround != null
                ? this.foreGround.hashCode()
                : 0);
        return hash;
    }

    /**
     * Returns the background color.
     * @return The background color.
     * @see #setBackground(java.awt.Color) 
     */
    public Color getBackground() {
        return backGround;
    }

    /**
     * Sets the background color.
     * @param backGround The background color.
     * @see #getBackground() 
     */
    public void setBackground(Color backGround) {
        this.backGround = backGround;
    }

    /**
     * Cleans up this style object.
     */
    public void dispose() {
        this.backGround = null;
        this.fontStyle = null;
        this.foreGround = null;
        this.syntaxName = null;
    }
}
