/*
 * FindPreviousMenu.java
 * Created on May 16, 2007, 2:25 PM
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
import org.apex.base.event.FindTextEventHandler;
import org.apex.base.search.SearchTextUtility;

/**
 * Finds the previous match.
 * <p>
 * It takes the criteria and search key from previous search operation.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class FindPreviousMenu extends UILessMenu {

    /**
     * The find text event handler.
     */
    private FindTextEventHandler findEvent;

    /**
     * Creates a new instance of FindPreviousMenu.
     */
    public FindPreviousMenu() {
        findEvent = new FindTextEventHandler();
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Finds the previous match.
     * @param in Input parameters.
     * @param out Output parameters.
     * @see SearchTextUtility
     * @see FindTextEventHandler
     */
    protected void execute(InputParams in, OutputParams out) {
        SearchTextUtility.getModel().setBackwardSearch(true);
        findEvent.execute(SearchTextUtility.getModel());
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }
}
