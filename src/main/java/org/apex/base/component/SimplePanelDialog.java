/*
 * SimplePanelDialog.java
 * Created on 11 July, 2007, 11:16 PM 
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

import java.awt.BorderLayout;
import java.awt.Window;
import javax.swing.JPanel;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.logging.Logger;

/**
 * A dialog window which uses a panel to display information.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public abstract class SimplePanelDialog extends BasicDialog {

    /**
     * The view of dialog window.
     */
    protected JPanel panel;

    /**
     * Creates a new instance of {@code SimplePanelDialog}.
     */
    public SimplePanelDialog() {
    }

    /**
     * Creates the dialog window, adds the {@code Panel} to it
     * and makes the dialog visible.
     * @param input Input parameters.
     * @param output Output parameters.
     */
    public void createDialog(InputParams input,
            OutputParams output) {        
        if (this.panel != null && !this.panel.isShowing()) {
            Window window = getParentWindow();
            if (window instanceof ApexFrame) {
                dialog = new ApexDialog((ApexFrame) getParentWindow(), getTitle());
            } else if (window instanceof ApexDialog) {
                dialog = new ApexDialog((ApexDialog) getParentWindow(), getTitle());
            } else {
                Logger.logError("The parent window type is neither an ApexFrame nor an ApexDialog", null);
                return;
            }
            dialog.setModal(isModal());
            dialog.getContentPane().setLayout(new BorderLayout());
            dialog.getContentPane().add(panel);

            dialog.setSize((int) windowSize().getX(), (int) windowSize().getY());
            dialog.setResizable(false);
            dialog.setLocationRelativeTo(getParentWindow());
            dialog.setIconImage(getIconImage());
        }
        makeDialogVisible();
    }

    /**
     * Returns the view of dialog window.
     * @return The view of dialog window.
     */
    public JPanel getView() {
        return this.panel;
    }
}
