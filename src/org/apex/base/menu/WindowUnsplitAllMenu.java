/*
 * WindowUnsplitAllMenu.java
 * Created on 27 June, 2007, 11:50 PM
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

import org.apex.base.component.LineNumberedTextEditor;
import org.apex.base.data.AbstractDocument;
import org.apex.base.component.ApexTabbedPane;
import org.apex.base.constant.CommonConstants;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.component.ApexSplitPane;
import java.util.Iterator;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.ActionManager;

/**
 * A menu target to unsplit the edit area for all documents opened in editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class WindowUnsplitAllMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code WindowUnsplitAllMenu}.
     */
    public WindowUnsplitAllMenu() {
    }

    /**
     * Unsplits the edit area of all documents opened in editor. For a document, 
     * if current split status is {@code UNSPLIT} it skips the document and attempts to unsplit
     * the next document. In all other cases it determines the current component which
     * displays the original tabbed pane editor and removes the split pane.
     * @param in Input parameters.
     * @param out Output parameters.
     * @see CommonConstants#VERTICAL_SPLIT
     * @see CommonConstants#HORIZONTAL_SPLIT
     * @see CommonConstants#UNSPLIT
     */
    protected void execute(InputParams in, OutputParams out) {
        unSplitAll(in, out);
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Unsplits the edit area of all documents opened in editor. For a document, 
     * if current split status is {@code UNSPLIT} it skips the document and attempts to unsplit
     * the next document. In all other cases it determines the current component which
     * displays the original tabbed pane editor and removes the split pane.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    private void unSplitAll(InputParams in, OutputParams out) {
        ApexTabbedPane docsTabbedPane = getContext().getEditorComponents().
                getEditorBody().
                getDocsWindow().
                getDocsTabbedPane();        
        Iterator itr = getContext().getEditorProperties().
                getOpenDocumentIterator();
        // Get all the keys of the Map and count.
        while (itr.hasNext()) {
            String key = (String) itr.next();
            AbstractDocument file = getContext().getEditorProperties().getOpenDocument(key);
            if (file.getSplitStatus() == CommonConstants.UNSPLIT) {
                continue;
            }

            ApexSplitPane splitComp = (ApexSplitPane) file.getComponent();
            int tabIndex = docsTabbedPane.indexOfComponent(splitComp);

            LineNumberedTextEditor actualComp = null;
            if (file.getSplitStatus() == CommonConstants.HORIZONTAL_SPLIT) {
                actualComp = (LineNumberedTextEditor) splitComp.getLeftComponent();
            } else if (file.getSplitStatus() == CommonConstants.VERTICAL_SPLIT) {
                actualComp = (LineNumberedTextEditor) splitComp.getTopComponent();
            }

            // Set the splitpane to the tabbedpane current loop index.            
            docsTabbedPane.setComponentAt(tabIndex, actualComp);

            file.setComponent(actualComp);
            file.setSplitStatus(CommonConstants.UNSPLIT);            
        }
        ActionManager.setActionSelected(MenuConstants.UNSPLIT, true);
    }
}
