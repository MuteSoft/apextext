/*
 * UIDialogModel.java
 * Created on 30 June, 2007, 2:31 PM
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
package org.apex.base.ui.text;

import javax.swing.JDialog;

/**
 * A data model used by dialog based user interfaces.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public interface UIDialogModel extends UIModel {

    /**
     * Sets the container dialog window.
     * @param window The container dialog window.
     * @see #getContainerWindow() 
     */
    void setContainerWindow(JDialog window);

    /**
     * Returns the container dialog window.
     * @return The container dialog window.
     * @see #setContainerWindow(javax.swing.JDialog) 
     */
    JDialog getContainerWindow();
}
