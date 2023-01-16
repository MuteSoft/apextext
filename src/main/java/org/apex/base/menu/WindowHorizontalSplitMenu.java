/*
 * WindowHorizontalSplitMenu.java
 * Created on 27 June, 2007, 9:50 AM
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
 * A menu target to split the edit area of editor into two parts horizontally.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class WindowHorizontalSplitMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code WindowHorizontalSplitMenu|}.
     */
    public WindowHorizontalSplitMenu() {
    }

    /**
     * Splits the edit area horizontally. If current split status is {@code HORIZONTAL_SPLIT}
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
                getSplitStatus() ==
                CommonConstants.HORIZONTAL_SPLIT) {
            return;
        }
        doSplit(in, out);
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Splits the edit area horizontally. It determines the current component
     * and based on that creates top and bottom components. Finally adds these components
     * to a {@code JSplitPane}.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    private void doSplit(InputParams in, OutputParams out) {

        LineNumberedTextEditor currentComp = null;
        if (getContext().getEditorProperties().getCurrentDocument().
                getSplitStatus() ==
                CommonConstants.UNSPLIT) {
            currentComp =
                    (LineNumberedTextEditor) getContext().getEditorProperties().
                    getCurrentDocument().
                    getComponent();
        } else if (getContext().getEditorProperties().getCurrentDocument().
                getSplitStatus() ==
                CommonConstants.VERTICAL_SPLIT) {
            ApexSplitPane splitComp = (ApexSplitPane) getContext().
                    getEditorProperties().
                    getCurrentDocument().
                    getComponent();
            currentComp = (LineNumberedTextEditor) splitComp.getTopComponent();
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

        // Create the horizontal split pane. In Java JSplitPane.VERTICAL_SPLIT provides
        // top to bottom alignment.
        ApexSplitPane verticalsplitPane = new ApexSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                false);
        // Set the splitpane to the tabbedpane current index.
        int selectedIndex = docsTabbedPane.getSelectedIndex();
        docsTabbedPane.setComponentAt(selectedIndex, verticalsplitPane);
        verticalsplitPane.setLeftComponent(currentComp);
        verticalsplitPane.setRightComponent(newComp);

        verticalsplitPane.setDividerLocation((int) (getContext().
                getEditorComponents().getFrame().
                getHeight() * .50));

        getContext().getEditorProperties().getCurrentDocument().setComponent(
                verticalsplitPane);
        getContext().getEditorProperties().getCurrentDocument().setSplitStatus(
                CommonConstants.HORIZONTAL_SPLIT);
    }
}
