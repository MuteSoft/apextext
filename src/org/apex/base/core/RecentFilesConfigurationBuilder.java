/*
 * RecentFilesConfigurationBuilder.java 
 * Created on 15 Aug, 2010, 12:26:22 AM
 *
 * Copyright (C) 2009 Mrityunjoy Saha
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
package org.apex.base.core;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.apex.base.data.Configuration;
import org.apex.base.data.ConfigurationBuilderSupport;
import org.apex.base.logging.Logger;
import org.apex.base.util.ConfigurationUtility;
import org.apex.base.util.StringUtil;

/**
 * Builds recent files configuration from file.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class RecentFilesConfigurationBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code RecentFilesConfigurationBuilder}.
     */
    public RecentFilesConfigurationBuilder() {
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading recent files configuration.");
        RecentFilesConfiguration recentFilesConfig =
                new RecentFilesConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(recentFilesConfig);
        }
        String codeTemplates = properties.getProperty(RECENT_FILES);
        List<String> fileIndices = ConfigurationUtility.getListFromString(
                codeTemplates,
                INTRA_SEPARATOR);
        Collections.reverse(fileIndices);
        if (codeTemplates != null) {
            for (String codeTemplate : fileIndices) {
                if (StringUtil.isNullOrEmpty(codeTemplate) || StringUtil.
                        isNullOrEmpty(properties.getProperty(codeTemplate))) {
                    continue;
                }
                String[] recentFileData = properties.getProperty(codeTemplate).
                        split(INTRA_FILE_PATH_SEPARATOR);
                if (recentFileData.length == 2) {
                    recentFilesConfig.addRecentFile(recentFileData[1],
                            recentFileData[0]);
                }
            }
        }
        return recentFilesConfig;
    }
}
