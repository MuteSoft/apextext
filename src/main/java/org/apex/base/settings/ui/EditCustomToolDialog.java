/*
 * EditCustomToolDialog.java 
 * Created on December 12, 2007, 04:18 PM
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
package org.apex.base.settings.ui;

import java.awt.Point;
import javax.swing.JList;
import org.apex.base.data.CustomTool;
import org.apex.base.settings.CustomToolConfiguration;

/**
 * A dialog window to display the edit custom tool form.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class EditCustomToolDialog extends SimplePanelSettingsDialog {

    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(450, 330);

    /**
     * Creates a new instance of {@code EditCustomToolDialog} using specified
     * custom tool configuration, selected custom tool and a list where custom tools are
     * displayed.
     * @param customToolConfig Custom tool configuration.
     * @param selectedTool The selected custom tool.
     * @param toolList A list where custom tools are displayed. .
     */
    public EditCustomToolDialog(CustomToolConfiguration customToolConfig,
            CustomTool selectedTool,
            JList toolList) {
        this.panel =
                new EditCustomTool(customToolConfig, selectedTool, toolList);
    }

    @Override
    public Point windowSize() {
        return WINDOW_SIZE;
    }

    @Override
    public void makeDialogVisible() {
        ((EditCustomTool) panel).setContainerWindow(this.dialog);
        super.makeDialogVisible();
    }

    public String getTitle() {
        return "Edit";
    }
}
