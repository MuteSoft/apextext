/*
 * SimplePanelDialogMenu.java
 * Created on December 25, 2006, 10:12 PM
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
import org.apex.base.component.ApexDialog;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * A base menu target to produce a dialog window which uses
 * a panel to display information. 
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public abstract class SimplePanelDialogMenu extends BasicDialogMenu {

    /**
     * The panel to be displayed in dialog window.     
     */
    protected JPanel panel;

    /**
     * Creates a new instance of {@code SimplePanelDialogMenu}.
     *
     */
    public SimplePanelDialogMenu() {
    }

    /**
     * Creates a dialog window.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    public void createDialog(InputParams in, OutputParams out) {
        if (this.panel != null && !this.panel.isShowing()) {
            dialog = new ApexDialog(getParentWindow(), getTitle());
            dialog.setModal(isModal());
            dialog.getContentPane().setLayout(new BorderLayout());
            dialog.getContentPane().add(panel);

            dialog.setSize((int) windowSize().getX(), (int) windowSize().getY());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(getParentWindow());
            dialog.setIconImage(getIconImage());
        }
    }

    /**
     * Makes the UI dialog window visible.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void postProcess(InputParams in, OutputParams out) {
        dialog.setVisible(true);
    }
}
