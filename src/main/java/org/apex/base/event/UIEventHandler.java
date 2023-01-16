/*
 * UIEventHandler.java
 * Created on 30 June, 2007, 3:29 PM
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
package org.apex.base.event;

import org.apex.base.common.FlowStopException;
import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.data.UIDataModel;

/**
 * A base class for event handlers for events generated from different user interfaces in editor
 * application.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class UIEventHandler {

    /**
     * Creates a new instance of {@code UIEventHandler}.
     */
    public UIEventHandler() {
    }

    /**
     * Executes the event generated from user interface.
     * @param dataModel The data model captured from GUI.
     * @throws FlowStopException If event execution fails.
     */
    public abstract void execute(UIDataModel dataModel) throws FlowStopException;

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }
}
