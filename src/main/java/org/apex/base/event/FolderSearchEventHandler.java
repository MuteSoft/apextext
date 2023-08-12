/*
 * FolderSearchEventHandler.java 
 * Created on 30 Sep, 2010, 8:27:44 PM
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
package org.apex.base.event;

import org.apex.base.data.UIDataModel;
import org.apex.base.search.SearchTextUtility;

/**
 * An event handler for folder search.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.3
 */
public class FolderSearchEventHandler extends UIEventHandler {

    /**
     * Creates a new instance of {@code FolderSearchEventHandler}.
     */
    public FolderSearchEventHandler() {
    }

    /**
     * Finds a given text in editor.
     * @param inModel A find
     */
    public void execute(UIDataModel inModel) {
        new Thread() {

            @Override
            public void run() {
                SearchTextUtility.search(getContext());
            }
        }.start();
    }
}
