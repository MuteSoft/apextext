/*
 * SyntaxStyle.java
 * Created on May 30, 2007, 6:56 PM
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
package org.apex.base.highlighter.style;

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.settings.Style;
import org.apex.base.settings.SyntaxStyleConfiguration;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.apex.base.data.IDocumentType;

/**
 * A base class for document type based synatx styles.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentStyle {

    /**
     * A table containing syntax style information for different
     * syntax categories.
     */
    private Map<String, AttributeSet> styles =
            new HashMap<String, AttributeSet>();
    /**
     * The associated document type.
     */
    private IDocumentType documentType;

    /**
     * Constructs a new instance of {@code DocumentStyle}.
     * @param documentType The document type.
     */
    public DocumentStyle(IDocumentType documentType) {
        this.documentType = documentType;
        addStyles();
    }

    /**
     * Constructs a new instance of {@code DocumentStyle}.
     * This constructor to be used only by subclasses.
     */
    protected DocumentStyle() {
    }

    /**
     * Returns the syntax style configuration.
     * @return The syntax style configuration.
     */
    private SyntaxStyleConfiguration getSyntaxStyleConfig() {
        return getContext().getConfiguration().getStyleConfig().getSyntaxStyle();
    }

    /**
     * Returns the synatx style information for a specified syntax category.
     * @param styleName The syntax style category.
     * @return The syntax style information.
     */
    public AttributeSet getStyle(String styleName) {
        AttributeSet style = styles.get(styleName);
        if (style != null) {
            return style;
        }
        return SimpleAttributeSet.EMPTY;
    }

    /**
     * For a given syntax style category adds the style information to
     * the table using specified background color, foreground color, bold
     * attribute and italic attribute.  
     * @param name The syntax category name.
     * @param bg The background color.
     * @param fg The foreground color.
     * @param bold A boolean that indicates whether bold attribute to be applied.
     * @param italic A boolean that indicates whether italic attribute to be applied.
     */
    protected void addStyle(String name, Color bg, Color fg, boolean bold,
            boolean italic) {
        SimpleAttributeSet style = new SimpleAttributeSet();
        if (bg != null) {
            StyleConstants.setBackground(style, bg);
        }
        if (fg != null) {
            StyleConstants.setForeground(style, fg);
        }
        StyleConstants.setBold(style, bold);
        StyleConstants.setItalic(style, italic);

        StyleConstants.setLeftIndent(style, 2.0f);
        this.styles.put(name, style);
    }

    /**
     * Adds a list of {@code Style}s. 
     * @param styles A list of {@code Style}s.
     */
    private void addStyles(Vector<Style> styles) {
        for (Style style : styles) {
            addStyle(style.getSyntaxName(), style.getBackground(),
                    style.getForeground(),
                    style.isBold(), style.isItalic());
        }
    }

    /**
     * Adds style information retrieved from synatx style configuration
     * based on associated document type.
     */
    public void addStyles() {
        addStyles(getSyntaxStyleConfig().getStylesForDocumentType(getDocType()));
    }

    /**
     * Returns the associated document type.
     * @return The associated document type.
     */
    public IDocumentType getDocType() {
        return this.documentType;
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }
}
