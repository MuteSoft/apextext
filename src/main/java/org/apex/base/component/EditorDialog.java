/*
 * EditorDialog.java
 * Created on 11 July, 2007, 10:53 PM
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

import java.awt.Point;
import java.awt.Window;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;

/**
 * An interface for creating dialog windows.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public interface EditorDialog {

    /**
     * Returns the title of dialog window.
     *
     * @return The title of dialog window.
     */
    String getTitle();

    /**
     * Returns the parent container of dialog window.
     * @return The container of dialog window.
     */
    Window getParentWindow();

    /**
     * Returns the location of dialog window on screen.
     * @return The location of dialog window on screen .
     */
    Point windowLocation();

    /**
     * Returns the size of dialog window in pixel.
     * <p>
     * The window size returned as a point which contains x and y coordinate
     * values.
     * <strong>This method will be removed in future version to avoid fixed sized windows which
     * is not appropriate in multilingual applications.</strong>
     * @return  The size of dialog window in pixel.
     */
    // @TODO Do not rely on size returned by this method. Insteaed, build logic to calculate window size
    // at runtime.
    Point windowSize();

    /**
     * Returns the dialog window object.
     * @return The dialog window.
     */
    Window getDialogWindow();

    /**
     * Creates the dialog window.
     * @param input Input parameters
     * @param output Output parameters.
     */
    void createDialog(InputParams input, OutputParams output);

    /**
     * Indicates whether the dialog window is modal.
     * @return {@code true} if this dialog window is modal; {@code false} otherwise.
     */
    boolean isModal();
}
