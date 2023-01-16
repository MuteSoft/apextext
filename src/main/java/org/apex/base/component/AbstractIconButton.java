/*
 * AbstractIconButton.java
 * Created on 26 June, 2007, 12:37 AM
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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.SwingConstants;

/**
 * A base class to create icon button with mouse rollover effect.
 * Usually this kind of icon button is used in tab component which renders a tab
 * in {@code ApexTabbedPane}.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 * @see AbstractTabComponent
 * @see ApexTabbedPane
 */
public abstract class AbstractIconButton extends ApexButton implements ActionListener, MouseListener {

    /**
     * Creates a new icon button with given icon.
     * @param icon The icon.
     */
    public AbstractIconButton(Icon icon) {
        super(icon);
        this.setVerticalTextPosition(SwingConstants.CENTER);
        setToolTipText("Close Tab");
        //Make the button looks the same for all Laf's
        //setUI(new BasicButtonUI());
        //Make it transparent
        setContentAreaFilled(false);
        //No need to be focusable
        setFocusable(false);
        //setBorder(BorderFactory.createEtchedBorder());
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setBorderPainted(false);
        //Making nice rollover effect
        addMouseListener(this);
        //we use the same listener for all buttons
        //setRolloverEnabled(true);
        //Close the proper tab by clicking the button
        addActionListener(this);
    }

    /**
     * Invoked when this icon button is pressed.
     * @param e The action event.
     */
    public void actionPerformed(ActionEvent e) {
        doOnClick(e);
    }

    /**
     * Performs operation when this icon button is pressed.
     * @param e The action event.
     */
    protected abstract void doOnClick(ActionEvent e);

    /**
     * Paint the border of button when mouse enters
     * the icon button.
     * @param e The mouse event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        Component component = e.getComponent();
        if (component instanceof ApexButton) {
            ApexButton button = (ApexButton) component;
            if (button.getParent() instanceof AbstractTabComponent) {
                AbstractTabComponent parent = (AbstractTabComponent) button.
                        getParent();
                button.setIcon(parent.getActiveCloseIcon());
            } else {
                this.setBorderPainted(true);
            }
        }
    }

    /**
     * Remove the border when mouse exits the icon button.
     * @param e The mouse event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        Component component = e.getComponent();
        if (component instanceof ApexButton) {
            ApexButton button = (ApexButton) component;
            if (button.getParent() instanceof AbstractTabComponent) {
                AbstractTabComponent parent = (AbstractTabComponent) button.
                        getParent();
                button.setIcon(parent.getPassiveCloseicon());
            } else {
                this.setBorderPainted(false);
            }
        }
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     * @param e The mouse event.
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     * @param e The mouse event.
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been released on a component.
     * @param e The mouse event.
     */
    public void mouseReleased(MouseEvent e) {
    }
}
