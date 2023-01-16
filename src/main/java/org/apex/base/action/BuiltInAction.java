/* 
 * BuiltInAction.java
 * Created on 13 Jan, 2008,2:06:25 PM
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

import org.apex.base.data.MenuNode;
import java.awt.event.ActionEvent;

/**
 * An extension of {@code org.apex.action.Action} to deal with
 * buit in swing framework actions.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class BuiltInAction extends Action {

    /**
     * Name of swing framework provided action name.
     */
    private String builtActionName;

    /**
     * Constructs a new {@code BuiltInAction} using specified menu node. A
     * menu node contains all information required to build an {@code Action}.
     * @param menu The menu node.
     * @see MenuNode
     */
    public BuiltInAction(MenuNode menu) {
        super(menu);
        this.builtActionName = menu.getTarget();
    }

    /**
     * Constructs a new {@code BuiltInAction}.
     */
    public BuiltInAction() {
    }

    /**
     * Gets the {@code Action} from list of installed actions by name and calls
     * actionPerformed() method on that {@code Action}.
     * @param e The action event.
     */
    public void actionPerformed(ActionEvent e) {
        getTextComponent(e).getDefaultAction(getBuiltActionName()).actionPerformed(e);
    }

    /**
     * Returns the name of the action.
     * @return The action name.
     */
    public String getBuiltActionName() {
        return builtActionName;
    }
}
