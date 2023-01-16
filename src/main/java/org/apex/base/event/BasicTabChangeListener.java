/*
 * BasicTabChangeListener.java
 * Created on 4 Jan, 2010, 11:25:42 PM
 *
 * Copyright (C) 2009 Mrityunjoy Saha
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

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apex.base.component.ApexTabbedPane;
import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;

/**
 * A base tab selection change listener.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class BasicTabChangeListener implements ChangeListener {

    /**
     * Creates a new instance of {@code BasicTabChangeListener}.
     */
    public BasicTabChangeListener() {
    }

    /**
     * This method is called when document tab selection is changed. It selects
     * the corresponding element in left hand document selector and displays the selected document.
     * @param e The change event.
     */
    public void stateChanged(ChangeEvent e) {
        ApexTabbedPane tabP = (ApexTabbedPane) e.getSource();
        int i = tabP.getSelectedIndex();
        if (i >= 0) {
            if (tabP.getLastSelectedIndex() < tabP.getTabCount()) {
                tabP.removeStylesAtTab(tabP.getLastSelectedIndex());
            }
            tabP.setStylesAtTab(i);
            doStateChanged(i, e);
            tabP.setLastSelectedIndex(i);
        }
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    public EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Perform the task on tab selection.
     * @param selectedTabIndex The selecte tab index.
     * @param e The tab selection change event.F
     */
    protected void doStateChanged(int selectedTabIndex, ChangeEvent e) {
    }
}
