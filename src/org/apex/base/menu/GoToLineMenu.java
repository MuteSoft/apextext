/*
 * GoToLineMenu.java
 * Created on May 16, 2007, 2:42 PM
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
import org.apex.base.search.ui.GoToLinePanel;
import org.apex.base.ui.text.GoToLineModel;
import java.awt.Point;

/**
 * Creates 'Go To Line' dialog and provides facility to navigate to any line in the document.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GoToLineMenu extends SimplePanelDialogMenu {

    /**
     * The data model for line navigation.
     */
    private GoToLineModel goToLineModel = new GoToLineModel();
    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(170, 110);

    /**
     * Creates a new instance of GoToLineMenu.
     */
    public GoToLineMenu() {
        this.panel = new GoToLinePanel(goToLineModel);
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Sets the container dialog window to view and then make the dialog visible.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    protected void postProcess(InputParams in, OutputParams out) {
        ((GoToLinePanel) this.panel).setContainerWindow(dialog);
        super.postProcess(in, out);
    }

    @Override
    public Point windowSize() {
        return WINDOW_SIZE;
    }

    public String getTitle() {
        return "Go To Line";
    }
}
