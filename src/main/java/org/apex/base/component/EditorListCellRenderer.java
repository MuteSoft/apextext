/* 
 * EditorListCellRenderer.java
 * Created on 18 Nov, 2007,2:04:18 AM
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
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * A list cell renderer to display {@code JLabel} in the list. 
 * It provides a way to show tool tip text for list elements.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class EditorListCellRenderer extends DefaultListCellRenderer {

    /**
     * Constructs a new instance of {@code EditorListCellRenderer}.
     */
    public EditorListCellRenderer() {
        super();
    }

    /**
     * Return a component that has been configured to display the specified
     * value. That component's {@code paint} method is then called to
     * "render" the cell. It sets tool tip text for displayed list element.
     * @param list The JList we're painting.
     * @param value The value returned by list.getModel().getElementAt(index).
     * @param index The cells index.
     * @param isSelected True if the specified cell was selected.
     * @param cellHasFocus True if the specified cell has the focus.
     * @return A component whose paint() method will render the specified value.
     */
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        Component comp = super.getListCellRendererComponent(list, value, index, isSelected,
                cellHasFocus);
        if (value instanceof JLabel) {
            ((JLabel) comp).setToolTipText(((JLabel) value).getToolTipText());
        }
//        if (value instanceof ApexMenuItem) {
//            ((ApexMenuItem) comp).setToolTipText(((ApexMenuItem) value).getToolTipText());
//        }
        return comp;
    }
}
