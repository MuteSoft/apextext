/* 
 * GoToFileEventHandler.java
 * Created on 18 Nov, 2007,1:05:56 AM
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
package org.apex.base.event;

import org.apex.base.data.UIDataModel;
import org.apex.base.ui.text.GoToFileModel;
import org.apex.base.util.StringUtil;

/**
 * An event handler to navigate between documents. The selected document in
 * 'Go To File' dialog window is displayed in editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GoToFileEventHandler extends UIEventHandler {

    /**
     * Constructs a new instance of {@code GoToFileEventHandler}.
     */
    public GoToFileEventHandler() {
    }

    /**
     * Gets the fully qualified name of selected document from goto file data model
     * and then calls {@link #goTo(java.lang.String)} method to navigate to
     * that document.
     * @param dataModel The goto file data model.
     */
    @Override
    public void execute(UIDataModel dataModel) {
        GoToFileModel goToFileModel = (GoToFileModel) dataModel;
        String selectedFilePath = goToFileModel.getSelectedFilePath();
        goTo(selectedFilePath);
    }

    /**
     * Navigates to a specified document.
     * @param qualifiedPath The fully qualified name of document.
     * @return {@code true} if navigation is successful; otherwise returns {@code false}.
     */
    public boolean goTo(String qualifiedPath) {
        if (StringUtil.isNullOrEmpty(qualifiedPath)) {
            return false;
        }
        if (!this.getContext().getEditorProperties().isDocumentOpen(
                qualifiedPath)) {
            return false;
        }
        int fileIndex = this.getContext().getEditorProperties().getOpenDocument(
                qualifiedPath).
                getIndex();
        this.getContext().getEditorComponents().getEditorBody().getDocSelector().
                selectDocumentAt(fileIndex);
        return true;
    }
}
