/*
 * FolderSearchMenu.java
 * Created on 30 Sep, 2010, 6:29:58 PM
 *
 * Copyright (C) 2010 Mrityunjoy Saha
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
import org.apex.base.search.SearchTextUtility;
import org.apex.base.search.ui.FolderSearchView;

/**
 * Creates "Search Folder" dialog and provides facility to find text in a
 * folder.
 * <p>
 * It provides following features:
 * <ul>
 * <li>Optional subdirectory search</li>
 * <li>Directory filter</li>
 * <li>File filter</li>
 * <li>All matching lines</li>
 * <li>All matching files only</li>
 * </ul>
 * It highlights all the occurrences when a file is opened from the result list.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.3
 */
public class FolderSearchMenu extends SimplePanelDialogMenu {

    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(520, 260);

    /**
     * Creates a new instance of {@code FolderSearchMenu}.
     */
    public FolderSearchMenu() {
        panel = new FolderSearchView(SearchTextUtility.getModel());
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
        ((FolderSearchView) this.panel).setContainerWindow(dialog);
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
        return "Folder Search";
    }

    @Override
    protected Point getLocation() {
        return this.getTopRightLocation();
    }
}
