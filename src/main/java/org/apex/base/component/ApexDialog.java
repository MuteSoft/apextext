/*
 * ApexDialog.java
 * Created on December 20, 2006, 6:18 PM 
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

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 * An extension of {@code JDialog}. It provides handy constructors and methods
 * to easily deal with dialog window.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 * @see JDialog
 */
public class ApexDialog extends JDialog {

    /**
     * Creates a new instance of {@code ApexDialog} using specified title, owner
     * {@code Frame}, modality and {@code GraphicsConfiguration}.
     * @param mOwner The {@code Frame} from which the dialog is displayed.
     * @param mTitle  The {@code String} to display in the dialog's title bar.
     * @param mModal Specifies whether dialog blocks user input to other top-level 
     *                windows when shown. If {@code true}, the modality type property is set to 
     *                {@code DEFAULT_MODALITY_TYPE}, otherwise the dialog is modeless. 
     * @param mGc the {@code GraphicsConfiguration} of the target screen device.
     */
    public ApexDialog(Frame mOwner, String mTitle, boolean mModal,
                  GraphicsConfiguration mGc) {
        super(mOwner, mTitle, mModal, mGc);
    }

    /**
     * Creates a new instance of {@code ApexDialog} using specified title and owner
     * {@code Frame}. The dialog created is modeless.
     * @param mOwner The {@code Frame} from which the dialog is displayed.
     * @param mTitle The {@code String} to display in the dialog's title bar.
     */
    public ApexDialog(Frame mOwner, String mTitle) {
        this(mOwner, mTitle, false, null);
    }

    /**
     * Creates a new instance of {@code ApexDialog} using specified title, owner
     * {@code Dialog}, modality and {@code GraphicsConfiguration}.
     *
     * @param mOwner The {@code Dialog} from which the dialog is displayed.
     * @param mTitle The {@code String} to display in the dialog's title bar.
     * @param mModal Specifies whether dialog blocks user input to other
     * top-level windows when shown. If {@code true}, the modality type property
     * is set to {@code DEFAULT_MODALITY_TYPE}, otherwise the dialog is
     * modeless.
     * @param mGc the {@code GraphicsConfiguration} of the target screen device.
     */
    public ApexDialog(Dialog mOwner, String mTitle, boolean mModal,
            GraphicsConfiguration mGc) {
        super(mOwner, mTitle, mModal, mGc);
    }

    /**
     * Creates a new instance of {@code ApexDialog} using specified title and
     * owner {@code Dialog}. The dialog created is modeless.
     *
     * @param mOwner The {@code Dialog} from which the dialog is displayed.
     * @param mTitle The {@code String} to display in the dialog's title bar.
     */
    public ApexDialog(Dialog mOwner, String mTitle) {
        this(mOwner, mTitle, false, null);
    }

    /**
     * Called by the super class constructor methods to create the default
     * {@code JRootPane}.
     * @return The {@code JRootPane}.
     */
    @Override
    protected JRootPane createRootPane() {
        JRootPane pane = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
        Action actionListener = new AbstractAction() {

            public void actionPerformed(ActionEvent actionEvent) {
                setVisible(false);
            }
        };
        InputMap inputMap =
                pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        pane.getActionMap().put("ESCAPE", actionListener);

        return pane;
    }
}

