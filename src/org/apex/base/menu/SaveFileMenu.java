/*
 * SaveFileMenu.java
 * Created on December 20, 2006, 6:22 PM
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
import org.apex.base.function.Function;
import org.apex.base.function.WriteFile;
import org.apex.base.logging.Logger;
import org.apex.base.util.FileUtil;

/**
 * Saves a document in file system.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SaveFileMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code SaveFileMenu}.
     */
    public SaveFileMenu() {
    }

    public void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Saves the current document in file system. If the current document is non-persistent
     * in file system it calls {@link SaveAsFileMenu#processMenu(InputParams, OutputParams)}
     * to display a file chooser dialog and then saves the document in file system.
     * @param in Input parameters.
     * @param out Output parameters.
     * @see #doSave(AbstractDocument, InputParams, OutputParams) 
     */
    public void execute(InputParams in, OutputParams out) {
        doSave(getContext().getEditorProperties().getCurrentDocument(), in, out);
    }

    public void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Saves the given document in file system. If the given document is non-persistent
     * in file system it calls {@link SaveAsFileMenu#processMenu(InputParams, OutputParams)}
     * to display a file chooser dialog and then saves the document in file system.
     * @param file The document to save.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @SuppressWarnings("unchecked")
    public void doSave(AbstractDocument file, InputParams in, OutputParams out) {
        if (file == null) {
            Logger.logInfo(
                    "Document 'save' operation cancelled. Target document is null");
            return;
        }
        if (!file.isTemporary()) {
            if (!FileUtil.isDocumentWritable(file)) {
                MenuMessageManager.showErrorMessage(getContext().
                        getEditorComponents().getFrame(), 1009, "FILENAME=" +
                        file.getAbsolutePath());
                return;
            }
            /*
             * Fix for bug id 2071972 (sourceforge.net).
             * While saving large document if editor takes more than 'document change tracking' interval,
             * editor shows irrelevant document upate confirmation message. To avoid that document's last
             * saved time is updated even before attempting to save the document to disk.
             */
            file.setLastSaved(file.lastModified());
            /* Write the file to disk -Start */
            in.put("SAVING_FILE", file);
            Function write = new WriteFile();
            write.process(in, new OutputParams());
            /* Write the file to disk -End */
            // Mark the file as saved.
            FileUtil.markAsSaved(getContext(), file);
            out.put("SAVED_FILE", file);
        } else {
            in.put("SOURCE_FILE", file);
            MenuManager.getMenuById(MenuConstants.SAVE_FILE_AS).
                    processMenu(in, out);
        }
    }
}
