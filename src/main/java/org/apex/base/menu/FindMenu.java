/*
 * FindMenu.java
 * Created on December 28, 2006, 11:44 PM
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
import org.apex.base.search.SearchTextUtility;
import org.apex.base.search.ui.FindPanel;
import java.awt.Point;

/**
 * Creates 'Find' dialog and provides facility to find text in a document.
 * <p>
 * It provides highlight search and incremental search facility.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 *
 */
public class FindMenu extends SimplePanelDialogMenu {

    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(450, 180);

    /**
     * Creates a new instance of {@code FindMenu}.
     */
    public FindMenu() {
        panel = new FindPanel(SearchTextUtility.getModel());
    }

    public void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Sets the container dialog window to view and then make the dialog visible.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    public void postProcess(InputParams in, OutputParams out) {
        ((FindPanel) this.panel).setContainerWindow(dialog);
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
        return "Find";
    }
}
