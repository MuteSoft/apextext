/*
 * FolderBrowserEventHandler.java 
 * Created on 30 Sep, 2010, 8:43:52 PM
 *
 * Copyright (C) 2010 Mrityunjoy Saha
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
package org.apex.base.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.text.JTextComponent;

/**
 * A folder browser event handler.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.3
 */
public class FolderBrowserEventHandler implements ActionListener {

    /**
     * The field where the folder path can be displayed.
     */
    private JComponent datatField;

    /**
     * Creates a new instance of {@code FolderBrowserEventHandler} and registers the the component
     * for folder path display.
     * @param dataField The data field where folder path can be displayed.
     */
    public FolderBrowserEventHandler(JComponent dataField) {
        this.datatField = dataField;
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser =
                new JFileChooser();
        fileChooser.setDragEnabled(true);
        fileChooser.setMultiSelectionEnabled(false);
        // Add file Filters
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.showOpenDialog(null);
        File openFile = fileChooser.getSelectedFile();
        if (openFile != null) {
            if (this.datatField instanceof JTextComponent) {
                ((JTextComponent) this.datatField).setText(openFile.
                        getAbsolutePath());
            } else if (this.datatField instanceof JComboBox) {
                ((JComboBox) this.datatField).addItem(openFile.getAbsolutePath());
            }
        }
    }
}
