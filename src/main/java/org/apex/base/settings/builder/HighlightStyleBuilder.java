/*
 * HighlightStyleBuilder.java
 * Created on 15 July, 2007, 1:47 AM
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
import org.apex.base.constant.CommonConstants;
import org.apex.base.data.HighlightCategories;
import org.apex.base.logging.Logger;
import org.apex.base.data.Configuration;
import org.apex.base.util.ConfigurationUtility;
import org.apex.base.settings.HighlightColor;
import org.apex.base.settings.HighlightStyleConfiguration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import org.apex.base.util.StringUtil;

/**
 * Builds highlight style configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class HighlightStyleBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code HighlightStyleBuilder}.
     */
    public HighlightStyleBuilder() {
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading highlight style configuration.");
        HighlightStyleConfiguration highlightStyleConfig =
                new HighlightStyleConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(highlightStyleConfig);
        }
        highlightStyleConfig.setHighlightMap(getHighlightStyles(properties));
        return highlightStyleConfig;
    }

    /**
     * Builds and returns a table containing highlight colors for different highlight
     * categories.
     * @param properties A table of key value pairs.
     * @return A table containing highlight colors for different highlight categories.
     */
    private HashMap<String, HighlightColor> getHighlightStyles(
            Properties properties) {
        HashMap<String, HighlightColor> result =
                new HashMap<String, HighlightColor>();

        for (String highlightType : HighlightCategories.getHighlightCategories()) {
            String highlightStyle =
                    properties.getProperty(highlightType +
                    CommonConstants.WORD_SEPARATOR +
                    HIGHLIGHT);
            if (highlightStyle == null || highlightStyle.equals("")) {
                result.put(highlightType, new HighlightColor());
            } else {
                addHighlightStyle(highlightStyle, highlightType, result);
            }
        }
        return result;
    }

    /**
     * Creates a new highlight color and adds the same to given table of highlight
     * colors.
     * @param highlightStyle The highlight category key.
     * @param highlightType The highlight type.
     * @param result A table containing highlight colors for different highlight categories.
     */
    private void addHighlightStyle(String highlightStyle,
                                   String highlightType,
                                   Map<String, HighlightColor> result) {
        HighlightColor highlight = new HighlightColor();
        String[] tokens =
                StringUtil.getArrayFromString(highlightStyle,
                INTRA_SEPARATOR);
        highlight.setForeground(ConfigurationUtility.getColor(tokens[0]));
        highlight.setBackground(ConfigurationUtility.getColor(tokens[1]));

        result.put(highlightType, highlight);
    }
}
