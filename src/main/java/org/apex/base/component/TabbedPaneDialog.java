/*
 * TabbedPaneDialog.java
 * Created on 11 July, 2007, 11:25 PM
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
package org.apex.base.component;

import org.apex.base.data.Tab;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import java.awt.BorderLayout;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import org.apex.base.constant.CommonConstants;

/**
 * A dialog window which uses a tabbed pane to display information.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class TabbedPaneDialog extends BasicDialog {

    /**
     * The view of dialog window.
     */
    protected ApexTabbedPane tabbedPane;

    /**
     * Creates a new instance of {@code TabbedPaneDialog}.
     */
    public TabbedPaneDialog() {
    }

    /**
     * Creates the dialog window, adds the {@code ApexTabbedPane} to it
     * and makes the dialog visible.
     * @param input Input parameters.
     * @param output Output parameters.
     */
    public void createDialog(InputParams input, OutputParams output) {
        Vector tabs = (Vector) input.get(CommonConstants.TABS);
        dialog = new JDialog(getParentWindow(), getTitle());
        dialog.setModal(isModal());
        tabbedPane = new ApexTabbedPane();
        int noOfTabs = tabs != null
                ? tabs.size()
                : 0;
        for (int iCount = 0; iCount < noOfTabs; iCount++) {
            Tab tab = (Tab) tabs.get(iCount);
            tabbedPane.addTab(tab);
        }
        tabbedPane.setSelectedIndex(0);

        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(tabbedPane);

        dialog.setSize((int) windowSize().getX(), (int) windowSize().getY());
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(getParentWindow());
        makeDialogVisible();
    }

    /**
     * Returns the view of this dialog window.
     * @return A tabbed pane.
     */
    protected JTabbedPane getTabbedPane() {
        return this.tabbedPane;
    }
}
