/*
 * SyntaxStyleBuilder.java
 * Created on 15 July, 2007, 1:46 AM
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
package org.apex.base.settings.builder;

import org.apex.base.data.ConfigurationBuilderSupport;
import org.apex.base.logging.Logger;
import org.apex.base.data.Configuration;
import org.apex.base.util.ConfigurationUtility;
import org.apex.base.settings.Style;
import org.apex.base.settings.SyntaxStyleConfiguration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import org.apex.base.core.EditorBase;
import org.apex.base.data.IDocumentType;

/**
 * Builds syntax style configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SyntaxStyleBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code SyntaxStyleBuilder}.
     */
    public SyntaxStyleBuilder() {
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading syntax style configuration.");
        SyntaxStyleConfiguration syntaxStyleConfig =
                new SyntaxStyleConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(syntaxStyleConfig);
        }
        HashMap<IDocumentType, Vector<Style>> loadedSyntaxStyles =
                getSyntaxStyles(properties);
        syntaxStyleConfig.setSyntaxStyleMap(addMissingTokenStyles(
                loadedSyntaxStyles));
        return syntaxStyleConfig;
    }

    /**
     * When syntax style information is read from file, there is a chance that
     * styles are not ordered or some style information is missing. Based on
     * given list of syntax categories it adjusts style information in given list of
     * styles.
     * @param styles A list of styles for a document type.
     * @param tokensForDocumentType A list of syntax categories for a document type.
     */
    private void addMissingTokenStyles(List<Style> styles,
            List<String> tokensForDocumentType) {
        for (int iCount = 0; iCount < tokensForDocumentType.size(); iCount++) {
            String token = tokensForDocumentType.get(iCount);
            int tokenIndex = getTokenStyleIndex(token, styles);
            if (tokenIndex == -1) {
                // Token is not present.
                styles.add(iCount, getDefaultStyle(token));
            } else {
                // Token present.
                // Now check whether location of token
                // in container vector is right.
                if (tokenIndex != iCount) {
                    // Token style found at bad location.
                    Style style = styles.get(tokenIndex);
                    // Delete from bad location and insert
                    // at desired location in container vector.
                    styles.remove(tokenIndex);
                    styles.add(iCount, style);
                }
            }
        }
    }

    /**
     * Returns a default style.
     * @param syntaxName Syntax category name.
     * @return A {@code Style}.
     */
    private Style getDefaultStyle(String syntaxName) {
        return new Style(syntaxName);
    }

    /**
     * Builds and returns a table containing syntax styles for different document types.
     * @param properties A table of key value pairs.
     * @return A table containing syntax styles for different document types.
     */
    private HashMap<IDocumentType, Vector<Style>> getSyntaxStyles(
            Properties properties) {
        HashMap<IDocumentType, Vector<Style>> result =
                new HashMap<IDocumentType, Vector<Style>>();
        for (IDocumentType documentType : EditorBase.getContext().getEditorProperties().
                getDocumentTypeBase().
                getDocumentTypesIncludingDefault()) {
            Vector<Style> styles =
                    new Vector<Style>();
            String syntaxStyles =
                    properties.getProperty(documentType +
                    DOT + SYNTAX);
            if (syntaxStyles != null && !syntaxStyles.equals("")) {
                for (String syntaxStyle : ConfigurationUtility.getListFromString(
                        syntaxStyles,
                        INTER_SEPARATOR)) {
                    if (syntaxStyle == null || syntaxStyle.equals("")) {
                        continue;
                    }
                    addSyntaxStyle(syntaxStyle, styles);
                }
            }
            result.put(documentType, styles);
        }
        return result;
    }

    /**
     * When syntax style information is read from file, there is a chance that
     * styles are not ordered or some style information is missing. For each document type
     * it adjusts missing style information.
     * @param loadedSyntaxStyles A table containing syntax style for different document types.
     * @return A table containing syntax style for different document types.
     */
    private HashMap<IDocumentType, Vector<Style>> addMissingTokenStyles(
            HashMap<IDocumentType, Vector<Style>> loadedSyntaxStyles) {
        Iterator styleIterator = loadedSyntaxStyles.keySet().iterator();
        while (styleIterator.hasNext()) {
            IDocumentType docType = (IDocumentType) styleIterator.next();
            Vector<Style> stylesForDocType = loadedSyntaxStyles.get(docType);
            addMissingTokenStyles(stylesForDocType,
                    docType.getTokens());
        }
        return loadedSyntaxStyles;
    }

    /**
     * Adds a style at the end of the specified list of styles.
     * @param style A {@code Style}.
     * @param styles A list of {@code Style}s.
     */
    private void addSyntaxStyle(Style style,
            Vector<Style> styles) {
        styles.add(style);
    }

    /**
     * For a given syntax category it builds the {@code Style} and then
     * adds the style at the end of the specified list of styles.
     * @param syntaxStyle A syntax category.
     * @param result A list of {@code Style}s.
     */
    private void addSyntaxStyle(String syntaxStyle,
            Vector<Style> result) {
        Style style = getStyle(syntaxStyle);
        if (style != null) {
            addSyntaxStyle(style, result);
        }
    }

    /**
     * Builds and returns a syntax style for given syntax category.
     * @param syntaxStyle A syntax category.
     * @return A {@code Style}.
     */
    private Style getStyle(String syntaxStyle) {
        Style style = null;
        if (syntaxStyle != null && !syntaxStyle.equals("")) {
            Vector<String> tokens =
                    ConfigurationUtility.getListFromString(syntaxStyle,
                    INTRA_SEPARATOR);
            try {
                String token = tokens.get(0);
                style = new Style(token);
                style.setFontStyle(tokens.get(1));
                style.setForeground(ConfigurationUtility.getColor(tokens.get(2)));
            } catch (ArrayIndexOutOfBoundsException abe) {
                // Do Nothing. Allow style information to be retained
                // till it is creatred.
                //Logger.logWarning("Failed to build style from text: " + syntaxStyle, abe);
            }
        }
        return style;
    }

    /**
     * Determines the index of a given syntax category in the specified list
     * of syntax styles.
     * @param token A syntax category.
     * @param styles A list of syntax styles.
     * @return The index of given syntax category in the list of syntax styles. 
     *               If token style is not foiund it returns {@literal  -1}.
     */
    private int getTokenStyleIndex(String token,
            List<Style> styles) {
        int index = -1;
        for (int iCount = 0; iCount < styles.size(); iCount++) {
            Style style = styles.get(iCount);
            if (style.getSyntaxName().equals(token)) {
                index = iCount;
                break;
            }
        }
        return index;
    }
}