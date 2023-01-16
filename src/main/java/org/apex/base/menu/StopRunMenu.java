/*
 * StopRunMenu.java 
 * Created on 7 Jan, 2010, 10:10:57 PM
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

import java.awt.Point;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.ui.StopRunView;

/**
 * A menu for stopping one or more running processes and tools.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class StopRunMenu extends SimplePanelDialogMenu {

    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(420, 330);

    /**
     * Creates a new instance of {@code StopRunMenu}.
     */
    public StopRunMenu() {
        panel = new StopRunView();
    }

    public void preProcess(InputParams in, OutputParams out) {
        ((StopRunView) panel).refreshRunningToolList();
    }

    /**
     * Sets the container dialog window to view and then make the dialog visible.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    public void postProcess(InputParams in, OutputParams out) {
        ((StopRunView) this.panel).setContainerWindow(dialog);
        super.postProcess(in, out);
    }

    @Override
    public Point windowSize() {
        return WINDOW_SIZE;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    public String getTitle() {
        return "Stop Run";
    }
}
