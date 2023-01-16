/*
 * SaveAllFilesMenu.java
 * Created on March 19, 2007, 10:06 PM
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
package org.apex.base.menu;

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.util.DateTime;
import java.util.Iterator;

/**
 * Saves all unsaved documents opened in editor.
 * <p>
 * It does not consider non-persistent (temporary) documents.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SaveAllFilesMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code SaveAllFilesMenu}.
     */
    public SaveAllFilesMenu() {
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Saves all unsaved documents opened in editor. It skips
     * non-persistent (temporary) documents.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void execute(InputParams in, OutputParams out) {
        saveAllUnsavedFilesToDisk(in, out);
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Saves all unsaved documents opened in editor. It skips
     * non-persistent (temporary) documents.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    private void saveAllUnsavedFilesToDisk(InputParams in, OutputParams out) {
        Iterator itr = getContext().getEditorProperties().
                getOpenDocumentIterator();
        // Get all the keys of the Map and count.
        while (itr.hasNext()) {
            String key = (String) itr.next();
            AbstractDocument file = getContext().getEditorProperties().
                    getOpenDocument(key);
            if (!file.isTemporary() && (file.isSaved() == false || (file.isSaved() == true
                    && DateTime.isFileExternallyModified(file)))) {
                SaveFileMenu saveFileMenu =
                        (SaveFileMenu) MenuManager.getMenuById(
                        MenuConstants.SAVE_FILE);
                saveFileMenu.doSave(file, in, out);

            }
        }
    }
}
