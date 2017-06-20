/*
 * GeneralSectionBuilder.java
 * Created on 14 July, 2007, 1:42 PM
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
import org.apex.base.settings.GeneralSectionConfiguration;
import org.apex.base.util.StringUtil;
import java.util.Properties;

/**
 * Builds general section configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.2
 * @since Apex 1.0
 */
public class GeneralSectionBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code GeneralSectionBuilder}.
     */
    public GeneralSectionBuilder() {
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading general section configuration.");
        GeneralSectionConfiguration generalSecConfig =
                new GeneralSectionConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(generalSecConfig);
        }
        generalSecConfig.setDefaultExtension(properties.getProperty(
                DEFAULT_EXTENSION));
        generalSecConfig.setCaretColor(ConfigurationUtility.getColor(properties.
                getProperty(
                CARET_COLOR)));
        //generalSecConfig.setCaretWidth(StringUtil.getInt(properties.getProperty(CARET_WIDTH)));
        if (properties.getProperty(
                VIEW_LINE_NUMBER) != null) {
            generalSecConfig.setViewLineNumber(Boolean.parseBoolean(properties.
                    getProperty(
                    VIEW_LINE_NUMBER)));
        }
        generalSecConfig.setTabSize(StringUtil.getInt(properties.getProperty(
                TAB_SIZE)));
        //generalSecConfig.setIndentationSize(StringUtil.getInt(properties.getProperty(INDENTATION_SIZE)));
        if (properties.getProperty(
                VIEW_RIGHT_MARGIN) != null) {
            generalSecConfig.setViewRightMargin(Boolean.parseBoolean(properties.
                    getProperty(
                    VIEW_RIGHT_MARGIN)));
        }
        generalSecConfig.setRightMargin(StringUtil.getInt(properties.getProperty(
                RIGHT_MARGIN)));
        generalSecConfig.setRightMarginColor(ConfigurationUtility.getColor(properties.
                getProperty(
                RIGHT_MARGIN_COLOR)));
        if (properties.getProperty(
                VIEW_DOCUMENT_SELECTOR) != null) {
            generalSecConfig.setViewDocumentSelector(Boolean.parseBoolean(properties.
                    getProperty(
                    VIEW_DOCUMENT_SELECTOR)));
        }
        if (properties.getProperty(
                VIEW_STATUS_BAR) != null) {
            generalSecConfig.setViewStatusBar(Boolean.parseBoolean(properties.
                    getProperty(
                    VIEW_STATUS_BAR)));
        }
        generalSecConfig.setMaxRecentFilesCount(StringUtil.getInt(properties.
                getProperty(
                MAX_RECENT_FILES_COUNT)));
        if (properties.getProperty(
                REUSE_CONSOLE) != null) {
            generalSecConfig.setReuseConsole(Boolean.parseBoolean(properties.
                    getProperty(
                    REUSE_CONSOLE)));
        }
        return generalSecConfig;
    }
}
