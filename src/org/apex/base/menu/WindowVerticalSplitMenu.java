/*
 * WindowVerticalSplitMenu.java
 * Created on 26 June, 2007, 8:53 AM
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

import org.apex.base.component.CompoundUndoManager;
import org.apex.base.component.LineNumberedTextEditor;
import org.apex.base.component.ApexTabbedPane;
import org.apex.base.constant.CommonConstants;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.component.ApexSplitPane;
import org.apex.base.component.TextEditor;
import javax.swing.JSplitPane;

/**
 * A menu target to split the edit area of editor into two parts vertically.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class WindowVerticalSplitMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code WindowVerticalSplitMenu}.
     */
    public WindowVerticalSplitMenu() {
    }

    /**
     * Splits the edit area vertically. If current split status is {@code VERTICAL_SPLIT}
     * this method returns immediately. In all other cases it determines the current component
     * and based on that creates top and bottom components. Finally adds these components
     * to a {@code JSplitPane}.
     * @param in Input parameters.
     * @param out Output parameters.
     * @see CommonConstants#VERTICAL_SPLIT
     * @see CommonConstants#HORIZONTAL_SPLIT
     * @see CommonConstants#UNSPLIT
     */
    protected void execute(InputParams in, OutputParams out) {
        if (getContext().getEditorProperties().getCurrentDocument().
                getSplitStatus()
                == CommonConstants.VERTICAL_SPLIT) {
            return;
        }
        doSplit();
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Splits the edit area vertically. It determines the current component
     * and based on that creates left and right components. Finally adds these components
     * to a {@code JSplitPane}.
     */
    private void doSplit() {
        LineNumberedTextEditor currentComp = null;
        if (getContext().getEditorProperties().getCurrentDocument().
                getSplitStatus()
                == CommonConstants.UNSPLIT) {
            currentComp = (LineNumberedTextEditor) getContext().
                    getEditorProperties().
                    getCurrentDocument().
                    getComponent();
        } else if (getContext().getEditorProperties().getCurrentDocument().
                getSplitStatus()
                == CommonConstants.HORIZONTAL_SPLIT) {
            ApexSplitPane splitComp = (ApexSplitPane) getContext().
                    getEditorProperties().
                    getCurrentDocument().
                    getComponent();
            currentComp = (LineNumberedTextEditor) splitComp.getLeftComponent();
        }
        ApexTabbedPane docsTabbedPane = getContext().getEditorComponents().
                getEditorBody().
                getDocsWindow().
                getDocsTabbedPane();

        // Create a new pane.        
        LineNumberedTextEditor newComp =
                new LineNumberedTextEditor(getContext().getEditorProperties().
                getCurrentDocument().getDocument(), getContext().
                getEditorProperties().
                getCurrentDocument().getMenuState().isViewLineNumber());
        TextEditor editArea = newComp.getEditArea();
        // Create the undo manager for this edit area
        new CompoundUndoManager(editArea);
        editArea.addListeners();


        // Create the vertical split pane. In Java JSplitPane.HORIZONTAL_SPLIT provides
        // top to bottom alignment.
        ApexSplitPane horizontalsplitPane = new ApexSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, false);

        // Set the splitpane to the tabbedpane current index.
        int selectedIndex = docsTabbedPane.getSelectedIndex();

        docsTabbedPane.setComponentAt(selectedIndex, horizontalsplitPane);
        horizontalsplitPane.setLeftComponent(currentComp);
        horizontalsplitPane.setRightComponent(newComp);

        horizontalsplitPane.setDividerLocation((int) (getContext().
                getEditorComponents().getFrame().
                getWidth() * .50));

        getContext().getEditorProperties().getCurrentDocument().setComponent(
                horizontalsplitPane);
        getContext().getEditorProperties().getCurrentDocument().setSplitStatus(
                CommonConstants.VERTICAL_SPLIT);
    }
}
