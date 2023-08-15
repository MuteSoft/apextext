/*
 * ColorChooserDialog.java
 * Created on 11 July, 2007, 11:33 PM
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

import java.awt.Color;
import java.awt.Point;
import javax.swing.JComponent;
import javax.swing.JLabel;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.settings.ui.SimplePanelSettingsDialog;
import org.apex.base.ui.ColorChooser;

/**
 * A dialog window used to display color picker.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class ColorChooserDialog extends SimplePanelSettingsDialog {

    /**
     * The singleton instance of this class.
     */
    private static ColorChooserDialog selfInstance;
    /**
     * The dialog window size.
     */
    private static final Point WINDOW_SIZE = new Point(470, 430);

    /**
     * Creates a new instance of {@code ColorChooserDialog}.
     */
    private ColorChooserDialog() {
        panel = new ColorChooser();
    }

    /**
     * Returns the singleton instance of this class.
     * @return The singleton instance of this class.
     */
    public static ColorChooserDialog getSharedInstance() {
        if (selfInstance == null) {
            selfInstance = new ColorChooserDialog();
        }
        return selfInstance;
    }

    public String getTitle() {
        return "Select Color";
    }

    @Override
    public void makeDialogVisible() {
        // Set the container window (dialog) for the current panel displayed.
        ((ColorChooser) panel).setContainerWindow(dialog);
        super.makeDialogVisible();
    }

    @Override
    public void createDialog(InputParams input, OutputParams output) {
        // Set Target Label where the Color Information will be printed.
        JLabel targetLabel = (JLabel) input.get("TARGET_LABEL");
        JComponent targetBox = (JComponent) input.get("TARGET_BOX");
        ((ColorChooser) this.panel).setTargetLabel(targetLabel);
        ((ColorChooser) this.panel).setTargetBox(targetBox);
        super.createDialog(input, output);
    }

    @Override
    public Point windowSize() {
        return WINDOW_SIZE;
    }

    /**
     * Returns the selected color.
     * @return Selected color.
     */
    public Color getSelectedColor() {
        return ((ColorChooser) this.panel).getSelectedColor();
    }
}
