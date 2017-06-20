/*
 * EditorShutdownHook.java 
 * Created on 15 Aug, 2010, 2:32:44 AM
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

import java.util.Properties;
import org.apex.base.data.RecentFiles;

/**
 * The shutdown hook of the editor. The process method of this class is
 * called when editor shuts down. Note that, if editor shuts down abnormally
 * process method may not be called.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class EditorShutdownHook {

    /**
     * Creates a new instance of {@code EditorShutdownHook}.
     */
    public EditorShutdownHook() {
    }

    /**
     * Process shut down tasks.
     */
    public void process() {
        // Store recent files list.
        new RecentFilesConfigurationUpdater(RecentFiles.getSharedInstance().
                getUpdatedRecentFilesConfiguration()).update((Properties) null);
    }
}
