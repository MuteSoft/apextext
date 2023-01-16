/*
 * AddCustomToolDialog.java 
 * Created on December 12, 2007, 04:17 PM
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

import org.apex.base.component.SimplePanelDialog;
import org.apex.base.settings.CustomToolConfiguration;
import java.awt.Point;
import javax.swing.JList;

/**
 * A dialog window to display the add custom tool form.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class AddCustomToolDialog extends SimplePanelDialog {

    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(450, 325);

    /**
     * Creates a new instance of {@code AddCustomToolDialog} using specified custom tool configuration and
     * a list which displays all custom tools.
     * @param customToolConfig Custom tool configuration.
     * @param toolList A list which displays all available custom tools.
     */
    public AddCustomToolDialog(
            CustomToolConfiguration customToolConfig,
            JList toolList) {
        panel = new AddCustomTool(customToolConfig, toolList);
    }

    @Override
    public Point windowSize() {
        return WINDOW_SIZE;
    }

    @Override
    public void makeDialogVisible() {
        ((AddCustomTool) panel).setContainerWindow(this.dialog);
        super.makeDialogVisible();
    }

    public String getTitle() {
        return "Add";
    }
}
