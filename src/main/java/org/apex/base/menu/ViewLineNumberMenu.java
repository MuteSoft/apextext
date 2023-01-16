/*
 * ViewLineNumberMenu.java
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

import org.apex.base.component.ApexSplitPane;
import org.apex.base.component.LineNumberedTextEditor;
import org.apex.base.constant.CommonConstants;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.ActionManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;

/**
 * Controls visibility of line number display area of currently displayed document.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ViewLineNumberMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code ViewLineNumberMenu}.
     */
    public ViewLineNumberMenu() {
    }

    /**
     * For currently displayed document if line number area is not visible, it makes
     * the line number area visible and vice-versa.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    protected void execute(InputParams in, OutputParams out) {
        AbstractDocument currentDoc = getContext().getEditorProperties().
                getCurrentDocument();
        if (ActionManager.isActionSelected(MenuConstants.VIEW_LINE_NUMBER)) {
            if (!currentDoc.getLineNumberArea().isVisible()) {
                alterVisibility(currentDoc, true);
            }
        } else {
            if (currentDoc.getLineNumberArea().isVisible()) {
                alterVisibility(currentDoc, false);
            }
        }
    }

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    @Override
    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Alters the visibility of line number area of the editor.
     * @param currentDoc The current document.
     * @param enabled A boolean value which indicates whether to make the line number area visible.
     */
    private void alterVisibility(AbstractDocument currentDoc, boolean enabled) {
        currentDoc.getLineNumberArea().setVisible(enabled);
        // Fix for bug id 2128967 (sourceforge.net)
        LineNumberedTextEditor currentComp = null;
        ApexSplitPane splitComp = null;
        if (currentDoc.getSplitStatus() == CommonConstants.VERTICAL_SPLIT) {
            splitComp = (ApexSplitPane) getContext().getEditorProperties().
                    getCurrentDocument().getComponent();
            currentComp = (LineNumberedTextEditor) splitComp.getBottomComponent();
        } else if (currentDoc.getSplitStatus() == CommonConstants.HORIZONTAL_SPLIT) {
            splitComp = (ApexSplitPane) getContext().getEditorProperties().
                    getCurrentDocument().
                    getComponent();
            currentComp = (LineNumberedTextEditor) splitComp.getRightComponent();
        }
        if (currentComp != null) {
            currentComp.getLineNoArea().setVisible(enabled);            
        }
        currentDoc.setViewLineNumber(enabled);
    }
}
