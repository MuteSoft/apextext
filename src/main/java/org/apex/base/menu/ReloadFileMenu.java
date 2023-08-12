/*
 * ReloadFileMenu.java 
 * Created on 3 Mar, 2011, 4:20:18 AM
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
package org.apex.base.menu;

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;

/**
 * Reloads the file from disk.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.3
 */
public class ReloadFileMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code ReloadFileMenu}.
     */
    public ReloadFileMenu() {
    }

    @Override
    protected void execute(InputParams in, OutputParams out) {
        AbstractDocument file = getContext().getEditorProperties().
                getCurrentDocument();
        // @TODO cleanup the previous document and resources.
        // Could this be done just before opening the document?
        if (file != null && !file.isTemporary()) {
            ((OpenFileMenu) MenuManager.getMenuById(
                    MenuConstants.OPEN_FILE)).openFileFromDisk(
                    file);
        }
    }

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    @Override
    protected void postProcess(InputParams in, OutputParams out) {
    }
}
