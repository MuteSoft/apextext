/*
 * GeneralSectionUpdater.java
 * Created on 27 Oct, 2007, 8:30:46 PM
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
import org.apex.base.util.ConfigurationUtility;
import org.apex.base.settings.GeneralSectionConfiguration;
import java.util.Properties;

/**
 * Stores general section configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class GeneralSectionUpdater extends ConfigurationUpdateSupport {

    /**
     * General section configuration.
     */
    private GeneralSectionConfiguration genSecConfig;
    /**
     * Cloned general section configuration.
     */
    private GeneralSectionConfiguration clonedGenSecConfig;

    /**
     * Creates a new instance of {@code GeneralSectionUpdater} using specified
     * general section configuration and cloned general section configuration.
     * @param genSecConfig General section configuration.
     * @param clonedGenSecConfig Cloned general section configuration.
     */
    public GeneralSectionUpdater(GeneralSectionConfiguration genSecConfig,
            GeneralSectionConfiguration clonedGenSecConfig) {
        this.genSecConfig = genSecConfig;
        this.clonedGenSecConfig = clonedGenSecConfig;
    }

    @Override
    public void update(Properties properties) {
        this.genSecConfig.updateFromClone(this.clonedGenSecConfig);
        if (properties == null) {
            properties = loadConfigProperties(this.genSecConfig);
        }
        properties.setProperty(DEFAULT_EXTENSION, this.genSecConfig.
                getDefaultExtension());
        properties.setProperty(CARET_COLOR,
                ConfigurationUtility.convertToString(this.genSecConfig.
                getCaretColor()));
//        properties.setProperty(CARET_WIDTH,
//                String.valueOf(this.genSecConfig.getCaretWidth()));
        properties.setProperty(VIEW_LINE_NUMBER,
                String.valueOf(genSecConfig.isViewLineNumber()));
        properties.setProperty(TAB_SIZE,
                String.valueOf(genSecConfig.getTabSize()));
//        properties.setProperty(INDENTATION_SIZE,
//                String.valueOf(genSecConfig.getIndentationSize()));
        properties.setProperty(VIEW_RIGHT_MARGIN,
                String.valueOf(genSecConfig.isViewRightMargin()));
        properties.setProperty(RIGHT_MARGIN,
                String.valueOf(genSecConfig.getRightMargin()));
        properties.setProperty(RIGHT_MARGIN_COLOR,
                ConfigurationUtility.convertToString(this.genSecConfig.
                getRightMarginColor()));
        properties.setProperty(VIEW_DOCUMENT_SELECTOR,
                String.valueOf(genSecConfig.isViewDocumentSelector()));
        properties.setProperty(VIEW_STATUS_BAR,
                String.valueOf(genSecConfig.isViewStatusBar()));
        properties.setProperty(REUSE_CONSOLE,
                String.valueOf(genSecConfig.isReuseConsole()));
        properties.setProperty(MAX_RECENT_FILES_COUNT,
                String.valueOf(genSecConfig.getMaxRecentFilesCount()));
        storeConfigProperties(genSecConfig, properties);
    }
}
