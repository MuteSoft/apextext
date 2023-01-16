/*
 * ViewDocumentSelectorMenu.java
 * Created on 25 June, 2007, 1:53 AM
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
import org.apex.base.core.ActionManager;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.component.ApexSplitPane;

/**
 * Controls visibility of editor document selector which is displayed
 * at left hand side of editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ViewDocumentSelectorMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code ViewDocumentSelectorMenu}.
     */
    public ViewDocumentSelectorMenu() {
    }

    /**
     * If editor document selector is not visible, it makes
     * the editor document selector visible and vice-versa.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void execute(InputParams in, OutputParams out) {
        if (ActionManager.isActionSelected(MenuConstants.VIEW_DOCUMENT_SELECTOR)) {
            getContext().getEditorComponents().getEditorBody().getDocSelector().
                    setVisible(true);
            ((ApexSplitPane) getContext().getEditorComponents().getEditorBody().
                    getDocSelector().
                    getParent()).setLeftComponent(getContext().
                    getEditorComponents().getEditorBody().getDocSelector());
        } else {
            getContext().getEditorComponents().getEditorBody().getDocSelector().
                    setVisible(false);
        }
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }
}
