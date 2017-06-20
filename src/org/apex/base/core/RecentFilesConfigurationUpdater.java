/*
 * RecentFilesConfigurationUpdater.java 
 * Created on 15 Aug, 2010, 1:57:14 AM
 *
 * Copyright (C) 2010 Mrityunjoy Saha
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

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.apex.base.data.ConfigurationUpdateSupport;
import org.apex.base.util.StringUtil;

/**
 * Updates recent files configuration to file.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class RecentFilesConfigurationUpdater extends ConfigurationUpdateSupport {

    /**
     * Recent files configuration.
     */
    private RecentFilesConfiguration recentFilesConfig;

    /**
     * Creates a new instance of {@code RecentFilesConfigurationUpdater}.
     * @param recentFilesConfig Recent files configuration object.
     */
    public RecentFilesConfigurationUpdater(
            RecentFilesConfiguration recentFilesConfig) {
        this.recentFilesConfig = recentFilesConfig;
    }

    @Override
    public void update(Properties properties) {
        if (properties == null) {
            properties = loadConfigProperties(this.recentFilesConfig);
        }
        properties.clear();
        StringBuilder templates = new StringBuilder("");
        Map<String, String> recentFiles = this.recentFilesConfig.
                getRecentFilesList();
        int slNo = 0;
        for (Iterator<String> recentFilesIterator = recentFiles.keySet().
                iterator();
                recentFilesIterator.hasNext();) {
            slNo++;
            String absolutePath = recentFilesIterator.next();
            String displayName = recentFiles.get(absolutePath);
            if (!StringUtil.isNullOrEmpty(absolutePath)
                    && !StringUtil.isNullOrEmpty(displayName)) {
                templates.append(slNo);
                properties.setProperty(String.valueOf(slNo),
                        displayName + INTRA_FILE_PATH_SEPARATOR + absolutePath);
            }
            if (recentFilesIterator.hasNext()) {
                templates.append(INTRA_SEPARATOR);
            }
        }
        properties.setProperty(RECENT_FILES, templates.toString());
        storeConfigProperties(this.recentFilesConfig, properties);
    }
}
