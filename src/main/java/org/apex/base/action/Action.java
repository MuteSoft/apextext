/* 
 * Action.java
 * Created on 13 Jan, 2008,2:10:48 PM
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
package org.apex.base.action;

import org.apex.base.component.TextEditor;
import org.apex.base.core.EditorBase;
import org.apex.base.data.MenuNode;
import org.apex.base.util.ImageCreator;
import java.awt.event.ActionEvent;
import java.util.Hashtable;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 * A base class for all editor {@code Action}s. 
 * <p>
 * An {@code Action} object can be added to multiple{@code Action}-aware containers
 * and connected to {@code Action}-capable components. The GUI controls can then be
 * activated or deactivated all at once by invoking the {@code Action} object's
 * {@code setEnabled} method.
 * <P>
 * All editor menus are built from {@code Action}.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class Action extends AbstractAction {

    /**
     *  The action Id.
     */
    protected String id;

    /**
     * The associated menu node.
     */
    private MenuNode node;

    /**
     *Creates a new instance of {@code Action} using specified menu node. A
     * menu node contains all information required to build an {@code Action}.
     * @param menu The menu node.
     * @see MenuNode     
     */
    public Action(MenuNode menu) {
        super(menu.getName(), ImageCreator.createImageIcon(EditorBase.class, "images/" +
                menu.getImage()));
        this.id = menu.getId();
        this.node=menu;
        putValue(SHORT_DESCRIPTION, menu.getDescription());
        if (menu.getMnemonic() != ' ') {
            putValue(MNEMONIC_KEY, new Integer(menu.getMnemonic()));
        }
        if (menu.getAccelerator() != null) {
            putValue(ACCELERATOR_KEY, menu.getAccelerator());
        }
    }

    /**
     * Creates a new instance of {@code Action}.
     */
    public Action() {
    }

    /**
     * Initializes the action object.
     * Action needs to be initialized particularly when it is created using no argument
     * constructor.
     * @param properties A table of key-value pairs containing {@code Action} properties.
     */
    public void initialize(Hashtable properties) {
        // Initialize this action with given data in the hash table.
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Sets the GUI component as selected or de-selected.
     * @param flag A boolean flag that indicates whether or not GUI component should
     *               be selected.
     */
    public void setSelected(boolean flag) {
        putValue(SELECTED_KEY, flag);
    }

    /**
     * Returns a boolean that indicates whether or not GUI component is selected.
     * @return A boolean that indicates whether or not GUI component is selected.
     */
    public boolean isSelected() {
        return getValue(SELECTED_KEY) == null
                ? false
                : (Boolean) getValue(SELECTED_KEY);
    }

    /**
     * Returns the accelerator key.
     * <p>
     * It is the key combination which invokes the menu item's
     * action listeners without navigating the menu hierarchy.
     * @return The accelerator key.
     * @see #setAccelerator(javax.swing.KeyStroke)
     */
    public KeyStroke getAccelerator() {
        return (KeyStroke) getValue(ACCELERATOR_KEY);
    }

    /**
     * Sets the accelerator key.
     * <p>
     * It is the key combination which invokes the menu item's
     * action listeners without navigating the menu hierarchy.
     * @param newAccelerator The accelerator key.
     * @see #getAccelerator()
     */
    public void setAccelerator(KeyStroke newAccelerator) {
        putValue(ACCELERATOR_KEY, newAccelerator);
    }

    /**
     * Determines the component to use for the action.
     * This if fetched from the source of the ActionEvent
     * if it's not null and can be narrowed.  Otherwise,
     * the last focused component is used.
     *
     * @param e The ActionEvent.
     * @return The component.
     */
    protected final TextEditor getTextComponent(ActionEvent e) {
        if (e != null) {
            Object o = e.getSource();
            if (o instanceof TextEditor) {
                return (TextEditor) o;
            }
        }
        // By default return the editor current document.
        return EditorBase.getContext().getEditorProperties().getCurrentDocument().
                getEditor();
    }

    /**
     * Returns the associated menu node object.
     * <p>
     * A menu node represents a single menu in the editor.
     * @return A menu node.
     */
    public MenuNode getMenuNode(){
        return this.node;
    }
}
