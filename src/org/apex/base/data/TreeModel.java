/*
 * TreeModel.java
 * Created on 8 August, 2007, 11:39 PM
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

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * A simple tree data model that uses TreeNodes.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class TreeModel extends DefaultTreeModel {

    /**
     * Creates a new instance of {@code TreeModel}.
     * @param root The root node.
     */
    public TreeModel(TreeNode root) {
        super(root);
    }

    /**
     * Returns the root of the tree.  Returns null only if the tree has
     * no nodes.
     *
     * @return  The root of the tree.
     */
    @Override
    public Object getRoot() {
        Object retValue;

        retValue = super.getRoot();

        return retValue;
    }

    /**
     * Returns the child of {@code parent} at index {@code index} in the parent's
     * child array. 
     * @param parent A node in the tree, obtained from this data source.
     * @param index An existing index.
     * @return The child of {@code parent} at index {@code index}.
     */
    @Override
    public Object getChild(Object parent, int index) {
        Object retValue;

        retValue = super.getChild(parent, index);
        return retValue;
    }

    /**
     * Returns the number of children of {@code parent}.  Returns 0 if the node
     * is a leaf or if it has no children.
     * @param parent A node in the tree, obtained from this data source.
     * @return The number of children of the node {@code parent}.
     */
    @Override
    public int getChildCount(Object parent) {
        int retValue;

        retValue = super.getChildCount(parent);
        return retValue;
    }

    /**
     * Returns the index of child in parent.
     * @param parent A node in the tree, obtained from this data source.
     * @param child The node we are searching for.
     * @return The index of the child in the parent, or -1
     *              if either the parent or the child is {@code null}.
     */
    @Override
    public int getIndexOfChild(Object parent, Object child) {
        int retValue;

        retValue = super.getIndexOfChild(parent, child);
        return retValue;
    }

    /**
     * Returns whether the specified node is a leaf node.
     * @param node The node to check.
     * @return {@code true} if the node is a leaf node.
     */
    @Override
    public boolean isLeaf(Object node) {
        boolean retValue;

        retValue = super.isLeaf(node);
        return retValue;
    }
}
