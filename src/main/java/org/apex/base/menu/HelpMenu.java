/*
 * HelpMenu.java
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

import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.help.ui.ToolHelpView;
import javax.swing.JFrame;

/**
 * A target menu to show the main help window of editor application.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class HelpMenu extends MenuTarget {

    /**
     * The help window.
     */
    private ToolHelpView helpWindow;

    /**
     * Creates a new help menu.
     */
    public HelpMenu() {
        helpWindow = new ToolHelpView();
    }

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Makes the help window visible.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    protected void postProcess(InputParams in, OutputParams out) {
        helpWindow.setVisible(true);
    }

    /**
     * Returns the title of help window.
     * @return Title of help window.
     */
    public String getTitle() {
        return "Help";
    }

    /**
     * Returns the parent container of help window.
     * @return The container of help window.
     */
    public JFrame getParentWindow() {
        return this.getContext().getEditorComponents().getFrame();
    }

    @Override
    protected void createUI(InputParams in, OutputParams out) {
        // Nothing to do.
    }
}
