/*
 * UIConfigurationUtility.java
 * Created on 4 August, 2007, 12:24 PM 
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
package org.apex.base.settings.ui.text;

import org.apex.base.component.ColorChooserDialog;
import org.apex.base.data.CustomTool;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.data.ConfigurationOperator;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * A utility class to deal with user preferences configuration.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class UIConfigurationUtility {           

    /**
     * Creates a new instance of {@code UIConfigurationUtility}.
     */
    private UIConfigurationUtility() {
    }

    /**
     * Returns a list data model. It forms the list model based on given
     * list of data.
     * @param list A list of data.
     * @return A list data model.
     */
    public static ListModel getListModel(final List list) {
        return new AbstractListModel() {

            List tempList = list;

            public Object getElementAt(int index) {
                return tempList.get(index);
            }

            public int getSize() {
                return tempList.size();
            }
        };
    }

    /**
     * Returns a list data model for custom tools. It forms the data model
     * based on given list of custom tools.
     * @param list A list of custom tools.
     * @return A list data model.
     */
    public static ListModel getToolsListModel(final Vector<CustomTool> list) {
        return new AbstractListModel() {

            Vector<CustomTool> tempList = list;

            public Object getElementAt(int index) {
                return tempList.get(index).getName() + " - " + this.tempList.get(index).
                        getDescription();
            }

            public int getSize() {
                return tempList.size();
            }
        };
    }

    /**
     * Returns a data model for table. It forms the data model from given
     * array of elements.
     * @param data Array of data.
     * @param columnNames An array of column headers.
     * @return A table data model.
     */
    public static TableModel getDefaultTableModel(final Object[][] data,
            final String[] columnNames) {
        return new DefaultTableModel(data, columnNames);
    }

    /**
     * Converts a map to a two dimensional array.
     * @param data A table containing key-value pairs.
     * @return An array of given data.
     */
    public static String[][] convertMapTo2DArray(Map<String, String> data) {
        String[][] value = new String[data.keySet().size()][];
        Object[] keys = data.keySet().toArray();
        for (int iCount = 0; iCount < keys.length; iCount++) {
            // Create the first dimension.
            value[iCount] = new String[2];
            value[iCount][0] = (String) keys[iCount];
            value[iCount][1] = data.get(keys[iCount]);
        }
        return value;
    }

    /**
     * Returns a data model for combo box. It forms the data model
     * from given list of data.
     * @param list A list of data.
     * @return Acombo box data model.
     */
    @SuppressWarnings("unchecked")
    public static ComboBoxModel getComboModel(final List list) {
        return new DefaultComboBoxModel(new Vector(list));
    }

    /**
     * Creates the color chooser dialog.
     * @param label A label. 
     * @param comp The target box.
     */
    @SuppressWarnings("unchecked")
    public static void colorChooserHandler(JLabel label,
            JComponent comp) {
        InputParams input = new InputParams();
        input.put("TARGET_LABEL", label);
        input.put("TARGET_BOX", comp);        
        ColorChooserDialog.getSharedInstance().createDialog(input,
                new OutputParams());
    }

    /**
     * It sets given color to a specified target box and also sets the color
     * text to a specified label.
     * @param color A color.
     * @param targetLabel A label.
     * @param targetBox The target box.
     */
    public static void showColor(Color color, JLabel targetLabel,
            JComponent targetBox) {
        if (targetLabel != null) {
            String colorString =
                    color == null
                    ? ConfigurationOperator.DEFAULT_COLOR_STRING
                    : color.toString();
            targetLabel.setText(colorString.substring(colorString.indexOf("[")));
        }
        if (targetBox != null) {
            targetBox.setBackground(color);
        }
    }
}