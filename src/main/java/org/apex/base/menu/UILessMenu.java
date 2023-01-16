/*
 * UILessMenu.java
 * Created on December 25, 2006, 10:11 PM
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
package org.apex.base.menu;

import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;

/**
 * A menu item without any UI.
 * <p>
 * There are many scenarios where a menu has to be processed without 
 * showing an user interface. For example, document save operation.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class UILessMenu extends MenuTarget {

    /**
     * Creates a new instance of UILessMenu.
     */
    public UILessMenu() {
    }

    /**
     * As there is no UI, it calls a separate method {@link #execute(InputParams, OutputParams) }
     * to perform the original task.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    public final void createUI(InputParams in, OutputParams out) {
        execute(in, out);
    }

    /**
     * Performs the core task as part of menu item execution.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected abstract void execute(InputParams in, OutputParams out);
}
