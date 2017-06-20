/* 
 * DefaultAction.java 
 * Created on Dec 23, 2007,1:35:03 PM
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

import org.apex.base.core.MenuManager;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.data.MenuNode;
import org.apex.base.logging.Logger;
import java.awt.event.ActionEvent;

/**
 * A type of action which delegates the task to a menu target.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class DefaultAction extends Action {

    /**
     * Constructs a new instance of {@code DefaultAction}. A
     * menu node contains all information required to build an {@code Action}.
     * @param menu The menu node.
     */
    public DefaultAction(MenuNode menu) {
        super(menu);
        MenuManager.addMenu(this.id, menu.getTarget());
    }

    /**
     * Constructs a new instance of {@code DefaultAction}.
     */
    public DefaultAction() {
    }

    /**
     * Using the action Id it finds the menu target from
     * {@code MenuManager} and then delegates the task to
     * this menu target.
     * @param e The action event.
     */    
    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        try {
            InputParams input = new InputParams();
            input.put("ACTION_SOURCE", e.getSource());
            input.put("MENU_ID", this.id);
            Logger.logInfo("Executing menu '" + this.id + "'");
            if (MenuManager.getMenuById(this.id) == null) {
                Logger.logError("Menu class for id '" + this.id + "' not found.",
                        null);
            } else {
                MenuManager.getMenuById(this.id).processMenu(input,
                        new OutputParams());
            }
        } catch (Exception ex) {
            Logger.logError("Menu '" + this.id + "' execution failed.", ex);
        }
    }
}
