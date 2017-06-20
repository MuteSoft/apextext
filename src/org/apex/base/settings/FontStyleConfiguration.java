/*
 * FontStyleConfiguration.java
 * Created on 14 July, 2007, 1:25 PM
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

import org.apex.base.data.Fonts;
import org.apex.base.util.StringUtil;
import java.awt.Font;

/**
 * A configuration object for font styles.
 * <p>
 * User can change font styles from 'User Preferences' menu and this font style
 * is applicable for all types of documents.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class FontStyleConfiguration extends AbstractConfiguration {

    /**
     * Font name.
     */
    private String name = "Verdana";
    /**
     * Font style e.g. Regular or Italic etc.
     */
    private String style = Fonts.REGULAR;
    /**
     * Font size.
     */
    private int size = 12;

    /**
     * Creates a new instance of {@code FontStyleConfiguration}.
     */
    public FontStyleConfiguration() {
    }

    /**
     * Returns the font family name.
     * @return The font family name.
     * @see #setName(java.lang.String) 
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the font family name.
     * @param name The font family name.
     * @see #getName()      
     */
    public void setName(String name) {
        if (!StringUtil.isNullOrEmpty(name)) {
            this.name = name;
        }
    }

    /**
     * Returns the font style.
     * @return The font style.
     * @see #setStyle(java.lang.String) 
     */
    public String getStyle() {
        return style;
    }

    /**
     * Sets the font style.
     * @param style The font style.
     * @see #getStyle() 
     */
    public void setStyle(String style) {
        if (StringUtil.isNullOrEmpty(style) && Fonts.isValidFontStyle(style)) {
            this.style = style;
        }
    }

    /**
     * Returns the font size.
     * @return The font size.
     * @see #setSize(int) 
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the {@code Font} derived from font family name, font style and font size.
     * @return A {@code Font}.
     */
    public Font getFont() {
        Font font =
                new Font(getName(),
                Fonts.getIntFontStyle(getStyle()), getSize());
        return font;
    }

    /**
     * Sets font size.
     * @param size Font size.
     * @see #getSize() 
     */
    public void setSize(int size) {
        if (size > 0) {
            this.size = size;
        }
    }

    @Override
    public String toString() {
        return "name: " + name + "^style: " + style + "^size: " + size;
    }

    @Override
    public Object clone() {
        FontStyleConfiguration clonedConfig = null;
        try {
            clonedConfig =
                    (FontStyleConfiguration) super.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        FontStyleConfiguration clonedConfig =
                (FontStyleConfiguration) clonedObject;
        this.setName(clonedConfig.getName());
        this.setSize(clonedConfig.getSize());
        this.setStyle(clonedConfig.getStyle());
        fireFontStyleConfigurationChanged(null);

    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof FontStyleConfiguration) {
            FontStyleConfiguration clonedConfig =
                    (FontStyleConfiguration) clonedObject;
            value = !isChanged(this.getName(), clonedConfig.getName()) &&
                    !isChanged(this.getSize(),
                    clonedConfig.getSize()) &&
                    !isChanged(this.getStyle(), clonedConfig.getStyle());
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + (this.name != null
                ? this.name.hashCode()
                : 0);
        hash = 61 * hash + (this.style != null
                ? this.style.hashCode()
                : 0);
        hash = 61 * hash + this.size;
        return hash;
    }

    public boolean isConfigurable() {
        return true;
    }

    public String getConfigFile() {
        return "FontStyle";
    }

    public boolean isCacheRequired() {
        return true;
    }

    public boolean disposeIfCacheNotRequired() {
        this.name = null;
        this.size = 0;
        this.style = null;
        return true;
    }

    public boolean isLeaf() {
        return true;
    }

    public void remove() {
        disposeIfCacheNotRequired();
    }
}