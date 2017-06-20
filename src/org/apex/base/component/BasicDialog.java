/*
 * BasicDialog.java
 * Created on 11 July, 2007, 10:56 PM
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

import javax.swing.JFrame;
import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.util.ImageCreator;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import javax.swing.JDialog;
import org.apex.base.constant.EditorKeyConstants;

/**
 * An implmentation of {@code EditorDialog} and base class for creating
 * dialog windows.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public abstract class BasicDialog implements EditorDialog {

    /**
     * The dialog window.
     */
    protected JDialog dialog;
    /**
     * The default dialog window size.
     */
    private static final Point DEFAULT_WINDOW_SIZE = new Point(350, 375);

    /**
     * Creates a new instance of {@code BasicDialog}.
     */
    public BasicDialog() {
    }

    public Point windowLocation() {
        return null;
    }

    public Point windowSize() {
        return DEFAULT_WINDOW_SIZE;
    }

    public Window getDialogWindow() {
        return this.dialog;
    }

    public boolean isModal() {
        return true;
    }

    /**
     * Makes the dialog window visible on screen.
     */
    public void makeDialogVisible() {
        if (this.dialog != null) {
            this.dialog.setVisible(true);
        }
    }

    /**
     * Returns the image icon for the dialog window.
     * @return The image icon for the dialog window.
     */
    protected Image getIconImage() {
        return ImageCreator.createImageIcon(EditorBase.class,
                EditorKeyConstants.EDITOR_ICON).
                getImage();
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }

    public JFrame getParentWindow() {
        return getContext().getEditorComponents().getFrame();
    }
}
