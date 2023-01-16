/*
 * ViewPaletteMenu.java 
 * Created on 2 Jan, 2010, 6:36:26 PM
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

import org.apex.base.component.ApexSplitPane;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.ActionManager;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;

/**
 * Controls visibility of palette window.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class ViewPaletteMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code ViewPaletteMenu}.
     */
    public ViewPaletteMenu() {
    }

    /**
     * If editor output window is not visible, it makes
     * the output window visible and vice-versa.
     * @param in Input parameters.
     * @param out Output parameters.
     * @see ApexSplitPane
     */
    protected void execute(InputParams in, OutputParams out) {
        if (ActionManager.isActionSelected(MenuConstants.PALETTE_WINDOW)) {
            getContext().getEditorComponents().getEditorBody().getPaletteWindow().
                    setVisible(true);
            /* Not a good idea to set the component again.
             * But as of now component is not visible, but it exists in
             * the screen.
             */
            // Give 25% of the screen width;
            ((ApexSplitPane) getContext().getEditorComponents().getEditorBody().
                    getPaletteWindow().
                    getParent()).setDividerLocation((int) (getContext().
                    getEditorComponents().
                    getFrame().getWidth() * .75));
        } else {
            getContext().getEditorComponents().getEditorBody().getPaletteWindow().
                    setVisible(
                    false);
        }
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }
}
