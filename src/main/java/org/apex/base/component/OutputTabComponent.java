/*
 * OutputTabComponent.java
 * Created on 25 June, 2007, 12:44 AM 
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

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import org.apex.base.common.CommonMessageManager;
import org.apex.base.core.OutputWindow;

/**
 *  A tab component that is responsible for rendering the
 * title and/or icon for a output window tab in {@code ApexTabbedPane}.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class OutputTabComponent extends AbstractTabComponent {

    /**
     * Creates a new instance of {@code OutputTabComponent} with given title.
     * @param pane The container of tab.
     * @param title Tab title.
     * @param tabIcon An icon object to be used for the tab.
     */
    public OutputTabComponent(ApexTabbedPane pane, String title, Icon tabIcon) {
        super(pane, title, tabIcon);
    }

    /**
     * Creates  and adds components to display area of tab component. 
     * <p>
     * It adds title and a stop button to tab component.
     */
    protected void addComponents() {
        if (this.getPane() == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        setOpaque(false);
        this.add(getTabIcon());
        this.add(getTabTitle());        
        this.setActiveCloseIcon(ACTIVE_CLOSE_ICON);
        this.setPassiveCloseicon(PASSIVE_CLOSE_ICON);
        ApexButton closeButton = new AbstractIconButton(getPassiveCloseicon()) {

            protected void doOnClick(ActionEvent e) {
                int selectedIndex = getIndex();
                OutputWindow outWindow = getContext().getEditorComponents().
                        getEditorBody().
                        getOutputWindow();
                if (outWindow.getConsoleAt(selectedIndex).isProcessRunning()) {
                    // Show warning
                    int choice = CommonMessageManager.showConfirmMessage(getContext().
                            getEditorComponents().getFrame(), 1007);
                    if (choice == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }
                getContext().getEditorComponents().getEditorBody().
                        getOutputWindow().removeConsole(selectedIndex);
            }
        };
        //closeButton.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setCloseButton(closeButton);
        this.add(closeButton);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 3));
        this.addMouseListener(this);
    }

    /**
     * Based on number of clicks different actions are performed.
     * If it is a single click the document tab is selected and hence it makes
     * the corresponding document visible. In case of double click visibility of left hand
     * document selector is altered
     * @param e The mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int i = getIndex();
        if (i != -1) {
            this.getPane().setSelectedIndex(i);
        }
    }
}
