/*
 * HighlightStyleUpdater.java
 * Created on 27 Oct, 2007, 8:31:03 PM
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
package org.apex.base.settings.updater;

import org.apex.base.data.ConfigurationUpdateSupport;
import org.apex.base.constant.CommonConstants;
import org.apex.base.util.ConfigurationUtility;
import org.apex.base.settings.HighlightColor;
import org.apex.base.settings.HighlightStyleConfiguration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

/**
 * Stores highlight style configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class HighlightStyleUpdater extends ConfigurationUpdateSupport {

    /**
     * Highlight style configuration.
     */
    private HighlightStyleConfiguration highlightStyleConfig;
    /**
     * Cloned highlight style configuration.
     */
    private HighlightStyleConfiguration clonedHighlightStyleConfig;

    /**
     * Creates a new instance of {@code HighlightStyleUpdater} using specified
     * highlight style configuration and cloned highlight style configuration.
     * @param highlightStyleConfig Highlight style configuration.
     * @param clonedHighlightStyleConfig Cloned highlight style configuration.
     */
    public HighlightStyleUpdater(
            HighlightStyleConfiguration highlightStyleConfig,
            HighlightStyleConfiguration clonedHighlightStyleConfig) {
        this.highlightStyleConfig = highlightStyleConfig;
        this.clonedHighlightStyleConfig = clonedHighlightStyleConfig;
    }

    @Override
    public void update(Properties properties) {
        this.highlightStyleConfig.updateFromClone(
                this.clonedHighlightStyleConfig);
        if (properties == null) {
            properties = loadConfigProperties(this.highlightStyleConfig);
        }
        HashMap<String, HighlightColor> highlightStyleMap =
                this.highlightStyleConfig.getHighlightMap();
        for (Iterator categories = highlightStyleMap.keySet().iterator(); categories.
                hasNext();) {
            String category = (String) categories.next();
            HighlightColor highlightColor = highlightStyleMap.get(category);
            if (highlightColor != null) {
                properties.setProperty(category.toLowerCase() +
                        CommonConstants.WORD_SEPARATOR + HIGHLIGHT,
                        ConfigurationUtility.convertToString(highlightColor.
                        getForeground()) +
                        INTRA_SEPARATOR +
                        ConfigurationUtility.convertToString(highlightColor.
                        getBackground()));
            }
        }
        storeConfigProperties(highlightStyleConfig, properties);
    }
}
