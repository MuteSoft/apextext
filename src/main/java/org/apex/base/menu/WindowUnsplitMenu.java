/*
 * WindowUnsplitMenu.java
 * Created on 27 June, 2007, 9:52 AM
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

import java.awt.Color;
import org.apex.base.component.LineNumberedTextEditor;
import org.apex.base.component.ApexTabbedPane;
import org.apex.base.constant.CommonConstants;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.component.ApexSplitPane;

/**
 *  A menu target to unsplit the edit area of editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class WindowUnsplitMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code WindowUnsplitMenu}.
     */
    public WindowUnsplitMenu() {
    }

    /**
     * Unsplits the edit area. If current split status is {@code UNSPLIT}
     * this method returns immediately. In all other cases it determines the current component
     * which displays the original tabbed pane editor and removes the split pane.
     * @param in Input parameters.
     * @param out Output parameters.
     * @see CommonConstants#VERTICAL_SPLIT
     * @see CommonConstants#HORIZONTAL_SPLIT
     * @see CommonConstants#UNSPLIT
     */
    protected void execute(InputParams in, OutputParams out) {
        if (getContext().getEditorProperties().getCurrentDocument().
                getSplitStatus()
                == CommonConstants.UNSPLIT) {
            return;
        }
        unSplit(in, out);
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Unsplits the edit area. It determines the current component which
     * displays the original tabbed pane editor and removes the split pane.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    private void unSplit(InputParams in, OutputParams out) {
        ApexTabbedPane docsTabbedPane = getContext().getEditorComponents().
                getEditorBody().
                getDocsWindow().
                getDocsTabbedPane();

        ApexSplitPane splitComp = (ApexSplitPane) getContext().
                getEditorProperties().
                getCurrentDocument().
                getComponent();
        LineNumberedTextEditor actualComp = null;
        if (getContext().getEditorProperties().getCurrentDocument().
                getSplitStatus()
                == CommonConstants.HORIZONTAL_SPLIT) {
            actualComp = (LineNumberedTextEditor) splitComp.getLeftComponent();
        } else if (getContext().getEditorProperties().getCurrentDocument().
                getSplitStatus()
                == CommonConstants.VERTICAL_SPLIT) {
            actualComp = (LineNumberedTextEditor) splitComp.getTopComponent();
        }

        // Set the splitpane to the tabbedpane current index.
        int selectedIndex = docsTabbedPane.getSelectedIndex();
        docsTabbedPane.setComponentAt(selectedIndex, actualComp);

        getContext().getEditorProperties().getCurrentDocument().setComponent(
                actualComp);
        getContext().getEditorProperties().getCurrentDocument().setSplitStatus(
                CommonConstants.UNSPLIT);
        getContext().getEditorProperties().getCurrentDocument().getEditor().
                invalidate();
        getContext().getEditorProperties().getCurrentDocument().getEditor().
                validate();
        getContext().getEditorProperties().getCurrentDocument().getEditor().
                repaint();        
    }
}
