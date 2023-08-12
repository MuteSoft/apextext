/*
 * Menu.java
 * Created on December 20, 2006, 6:33 PM
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

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;

/**
 * A target executable of editor menu. This is a base for all menu targets. 
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 * @see EditorContext
 */
public abstract class MenuTarget {

    /**
     * Constructs the menu item.
     */
    public MenuTarget() {
    }

    /**
     * Processes the menu item.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    public final void processMenu(final InputParams in, final OutputParams out) {
        getContext().getEditorComponents().getTaskProgressIndicator().reset();  
        preProcess(in, out);
        createUI(in, out);
        postProcess(in, out);        
    }

    /**
     * Performs data initialization tasks before executing the menu item.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected abstract void preProcess(InputParams in, OutputParams out);

    /**
     * Creates user interface (if required) for this menu item.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected abstract void createUI(InputParams in, OutputParams out);

    /**
     * Performs post processing tasks after executing the menu item.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected abstract void postProcess(InputParams in, OutputParams out);

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected final EditorContext getContext() {
        return EditorBase.getContext();
    }
}
