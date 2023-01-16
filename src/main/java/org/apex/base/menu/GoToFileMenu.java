/* 
 * GoToFileMenu.java
 * Created on 18 Nov, 2007,12:42:03 AM
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
import org.apex.base.search.ui.GoToFileView;
import java.awt.Point;

/**
 * Creates 'Go To File' dialog and provides facility to find documents and navigate to any document easily.
 * <p>
 * It provides incremental search facility.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GoToFileMenu extends SimplePanelDialogMenu {

    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(417, 375);

    /**
     * Creates a new instance of GoToFileMenu.
     */
    public GoToFileMenu() {
    }

    /**
     * Creates the panel which is displayed in the dialog window.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    protected void preProcess(InputParams in, OutputParams out) {
        this.panel = new GoToFileView();
    }

    public String getTitle() {
        return "Go To File";
    }

    /**
     * Sets the container dialog window to view and then make the dialog visible.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    protected void postProcess(InputParams in, OutputParams out) {
        ((GoToFileView) this.panel).setContainerWindow(dialog);
        super.postProcess(in, out);
    }

    @Override
    public Point windowSize() {
        return WINDOW_SIZE;
    }
}
