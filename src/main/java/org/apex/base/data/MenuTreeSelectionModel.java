/*
 * MenuTreeSelectionModel.java
 * Created on 8 August, 2007, 11:43 PM
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
package org.apex.base.data;

import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeSelectionModel;

/**
 * A data model to represent the current state of the selection for
 * the tree component.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MenuTreeSelectionModel extends DefaultTreeSelectionModel {

    /**
     * Creates a new instance of {@code MenuTreeSelectionModel}.
     */
    public MenuTreeSelectionModel() {
    }

    /**
     * Returns the default single tree selection mode.
     * @return The tree selection mode.
     */
    @Override
    public int getSelectionMode() {
        return TreeSelectionModel.SINGLE_TREE_SELECTION;
    }
}
