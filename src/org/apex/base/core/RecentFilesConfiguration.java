/*
 * RecentFilesConfiguration.java 
 * Created on 14 Aug, 2010, 11:06:12 PM
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

import java.util.HashMap;
import java.util.Map;
import org.apex.base.data.Configuration;
import org.apex.base.util.StringUtil;

/**
 * A representation of recent files configuration.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class RecentFilesConfiguration implements Configuration {

    /**
     * List of recent files. Key is file path and value is display name.
     */
    private Map<String, String> recentFilesList = new HashMap<String, String>();

    /**
     * Creates a new instance of {@code RecentFilesConfiguration}.
     */
    public RecentFilesConfiguration() {
    }

    public void updateFromClone(Object clonedObject) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isConfigurable() {
        return true;
    }

    public String getConfigFile() {
        return "RecentFiles";
    }

    public boolean isCacheRequired() {
        return true;
    }

    public boolean disposeIfCacheNotRequired() {
        this.recentFilesList.clear();
        return true;
    }

    public boolean isLeaf() {
        return true;
    }

    /**
     * Adds a recent file entry.
     * @param path The absolute path of the file.
     * @param displayName The display name of the file.
     */
    public void addRecentFile(String path, String displayName) {
        if (!StringUtil.isNullOrEmpty(path) && !StringUtil.isNullOrEmpty(
                displayName)) {
            this.recentFilesList.put(path, displayName);
        }
    }

    /**
     * Removes a recent file specified by its path.
     * @param path The path of the recent file.
     */
    public void removeRecentFile(String path) {
        if (!StringUtil.isNullOrEmpty(path)) {
            this.recentFilesList.remove(path);
        }
    }

    /**
     * Returns recent files list.
     * <p>
     * Key is file path and values is display name.
     * @return Recent files list.
     */
    public Map<String, String> getRecentFilesList() {
        return recentFilesList;
    }

    public void remove() {
        disposeIfCacheNotRequired();
    }
}
