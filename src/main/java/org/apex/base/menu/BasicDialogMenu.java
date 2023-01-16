/*
 * BasicDialogMenu.java
 * Created on December 28, 2006, 11:48 PM
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

import javax.swing.JFrame;
import org.apex.base.component.EditorDialog;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import javax.swing.JDialog;

/**
 * An executable target of editor menu which displays a dialog window.
 * <p>
 * The dialog is displayed to prompt for user input. Once dialog disappears from screen
 * (on close or cancel) menu target executuion ends. On dialog window subsequent events are handled by
 * other event handlers.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class BasicDialogMenu extends MenuTarget implements EditorDialog {

    /**
     * The default dialog window size.
     */
    private static final Point DEFAULT_WINDOW_SIZE = new Point(350, 375);
    /**
     * The underlying dialog window.
     */
    protected JDialog dialog;

    /**
     * Creates a new instance of {@code BasicDialogMenu}.
     *
     */
    public BasicDialogMenu() {
    }

    /**
     * Creates the dialog window which is displayed to user.
     * @param input Input parameters.
     * @param output Output parameters.
     */
    public final void createUI(InputParams input,
            OutputParams output) {
        createDialog(input, output);
    }

    /**
     * Returns the location on screen of displayed dialog window.
     * @return The location on screen of displayed dialog window.
     */
    public Point windowLocation() {
        return null;
    }

    /**
     * Returns the size of displayed dialog window size.
     * @return The size of displayed dialog window size.
     */
    public Point windowSize() {
        return DEFAULT_WINDOW_SIZE;
    }

    /**
     * Returns the displayed dialog window.
     * @return The displayed dialog window.
     */
    public Window getDialogWindow() {
        return this.dialog;
    }

    /**
     * Returns whether the displayed dialog window is modal.
     * <P>
     * Subclasses should override this method and return appropriate value.
     * @return {@code true} if underlying dialog window is modal; otherwise returns {@code false}.
     */
    public boolean isModal() {
        return true;
    }

    /**
     * Returns the icon for displayed dialog window.
     * @return The editor icon.
     */
    protected Image getIconImage() {
        return getContext().getEditorProperties().getEditorIcon();
    }

    public JFrame getParentWindow() {
        return getContext().getEditorComponents().getFrame();
    }
}
