/*
 * StyleConfiguration.java
 * Created on 14 July, 2007, 1:23 PM
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

import org.apex.base.settings.builder.FontStyleBuilder;
import org.apex.base.settings.builder.HighlightStyleBuilder;
import org.apex.base.settings.builder.SyntaxStyleBuilder;

/**
 * The style configuration object.
 * <p>
 * It holds font style configuration, syntax style configuration, highlight style
 * configuration etc.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class StyleConfiguration extends AbstractConfiguration {

    /**
     * Font style configuration.
     */
    private FontStyleConfiguration fontStyle;
    /**
     * Syntax style configuration.
     */
    private SyntaxStyleConfiguration syntaxStyle;
    /**
     * Highlight style configuration.
     */
    private HighlightStyleConfiguration highlightStyle;

    /**
     * Creates a new instance of {@code StyleConfiguration}.
     */
    public StyleConfiguration() {
    }

    /**
     * Returns font style configuration.
     * @return Font style configuration.
     * @see #setFontStyle(org.apex.base.settings.FontStyleConfiguration)
     */
    public FontStyleConfiguration getFontStyle() {
        if (this.fontStyle == null) {
            this.fontStyle =
                    (FontStyleConfiguration) new FontStyleBuilder().build();
        }
        return fontStyle;
    }

    /**
     * Sets font style configuration.
     * @param fontStyle Font style configuration.
     * @see #getFontStyle() 
     */
    public void setFontStyle(FontStyleConfiguration fontStyle) {
        this.fontStyle = fontStyle;
    }

    /**
     * Returns syntax style configuration.
     * @return Syntax style configuration.
     * @see #setSyntaxStyle(org.apex.base.settings.SyntaxStyleConfiguration)
     */
    public SyntaxStyleConfiguration getSyntaxStyle() {
        if (this.syntaxStyle == null) {
            this.syntaxStyle =
                    (SyntaxStyleConfiguration) new SyntaxStyleBuilder().build();
        }
        return syntaxStyle;
    }

    /**
     * Sets syntax style configuration.
     * @param syntaxStyle Syntax style configuration.
     * @see #getSyntaxStyle() 
     */
    public void setSyntaxStyle(SyntaxStyleConfiguration syntaxStyle) {
        this.syntaxStyle = syntaxStyle;
    }

    /**
     * Returns highlight style configuration.
     * @return Highlight style configuration.
     * @see #setHighlightStyle(org.apex.base.settings.HighlightStyleConfiguration)
     */
    public HighlightStyleConfiguration getHighlightStyle() {
        if (this.highlightStyle == null) {
            this.highlightStyle =
                    (HighlightStyleConfiguration) new HighlightStyleBuilder().
                    build();
        }
        return highlightStyle;
    }

    /**
     * Sets highlight style configuration.
     * @param highlightStyle Highlight style configuration.
     * @see #getHighlightStyle() 
     */
    public void setHighlightStyle(HighlightStyleConfiguration highlightStyle) {
        this.highlightStyle = highlightStyle;
    }

    @Override
    public String toString() {
        return "FontStyleConfiguration: " + fontStyle +
                "^SyntaxStyleConfiguration: " + syntaxStyle +
                "^HighlightStyleConfiguration: " + highlightStyle;
    }

    @Override
    public Object clone() {
        StyleConfiguration clonedConfig = null;
        if (this.fontStyle == null) {
            this.fontStyle = getFontStyle();
        }
        if (this.syntaxStyle == null) {
            this.syntaxStyle = getSyntaxStyle();
        }
        if (this.highlightStyle == null) {
            this.highlightStyle = getHighlightStyle();
        }
        try {
            clonedConfig = (StyleConfiguration) super.clone();
            clonedConfig.setFontStyle((FontStyleConfiguration) this.fontStyle.
                    clone());
            clonedConfig.setHighlightStyle((HighlightStyleConfiguration) this.highlightStyle.
                    clone());
            clonedConfig.setSyntaxStyle((SyntaxStyleConfiguration) this.syntaxStyle.
                    clone());
        } catch (CloneNotSupportedException ex) {
        }
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        StyleConfiguration clonedConfig =
                (StyleConfiguration) clonedObject;
        this.getFontStyle().updateFromClone(clonedConfig.getFontStyle());
        this.getSyntaxStyle().updateFromClone(clonedConfig.getSyntaxStyle());
        this.getHighlightStyle().
                updateFromClone(clonedConfig.getHighlightStyle());
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof StyleConfiguration) {
            StyleConfiguration clonedConfig =
                    (StyleConfiguration) clonedObject;
            value =
                    (this.fontStyle == null
                    ? clonedConfig.getFontStyle() ==
                    null
                    : this.fontStyle.equals(clonedConfig.getFontStyle())) &&
                    (this.syntaxStyle == null
                    ? clonedConfig.getSyntaxStyle() == null
                    : this.syntaxStyle.equals(clonedConfig.getSyntaxStyle())) &&
                    (this.highlightStyle == null
                    ? clonedConfig.getHighlightStyle() == null
                    : this.highlightStyle.equals(
                    clonedConfig.getHighlightStyle()));
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash +
                (this.fontStyle != null
                ? this.fontStyle.hashCode()
                : 0);
        hash = 53 * hash +
                (this.syntaxStyle != null
                ? this.syntaxStyle.hashCode()
                : 0);
        hash =
                53 * hash +
                (this.highlightStyle != null
                ? this.highlightStyle.hashCode()
                : 0);
        return hash;
    }

    public boolean isConfigurable() {
        return (this.fontStyle == null
                ? new FontStyleConfiguration().isConfigurable()
                : this.fontStyle.isConfigurable()) ||
                (this.syntaxStyle == null
                ? new SyntaxStyleConfiguration().isConfigurable()
                : this.syntaxStyle.isConfigurable()) ||
                (this.highlightStyle == null
                ? new HighlightStyleConfiguration().isConfigurable()
                : this.highlightStyle.isConfigurable());
    }

    public String getConfigFile() {
        return "Style";
    }

    public boolean isCacheRequired() {
        return (this.fontStyle == null
                ? new FontStyleConfiguration().isCacheRequired()
                : this.fontStyle.isCacheRequired()) ||
                (this.syntaxStyle == null
                ? new SyntaxStyleConfiguration().isCacheRequired()
                : this.syntaxStyle.isCacheRequired()) ||
                (this.highlightStyle == null
                ? new HighlightStyleConfiguration().isCacheRequired()
                : this.highlightStyle.isCacheRequired());
    }

    public boolean disposeIfCacheNotRequired() {
        boolean fontStyleDisposed = false;
        boolean syntaxStyleDisposed = false;
        boolean highlightStyleDisposed = false;
        // Dispose contained config objects.
        if (this.fontStyle != null &&
                (!this.fontStyle.isLeaf() || !this.fontStyle.isCacheRequired())) {
            fontStyleDisposed = this.fontStyle.disposeIfCacheNotRequired();
            if (fontStyleDisposed) {
                this.fontStyle = null;
            }
        }
        if (this.syntaxStyle != null && (!this.syntaxStyle.isLeaf() ||
                !this.syntaxStyle.isCacheRequired())) {
            syntaxStyleDisposed = this.syntaxStyle.disposeIfCacheNotRequired();
            if (syntaxStyleDisposed) {
                this.syntaxStyle = null;
            }
        }
        if (this.highlightStyle != null && (!this.highlightStyle.isLeaf() ||
                !this.highlightStyle.isCacheRequired())) {
            highlightStyleDisposed =
                    this.highlightStyle.disposeIfCacheNotRequired();
            if (highlightStyleDisposed) {
                this.highlightStyle = null;
            }
        }
        return fontStyleDisposed && syntaxStyleDisposed &&
                highlightStyleDisposed;
    }

    public boolean isLeaf() {
        return false;
    }

    public void remove() {
        if (this.fontStyle != null) {
            this.fontStyle.remove();
            this.fontStyle = null;
        }
        if (this.syntaxStyle != null) {
            this.syntaxStyle.remove();
            this.syntaxStyle = null;
        }
        if (this.highlightStyle != null) {
            this.highlightStyle.remove();
            this.highlightStyle = null;
        }
    }
}