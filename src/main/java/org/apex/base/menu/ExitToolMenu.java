/*
 * ExitToolMenu.java
 * Created on December 27, 2006, 11:56 PM
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

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.logging.Logger;

/**
 * A menu target, executed when the application exits normally.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ExitToolMenu extends UILessMenu {

    /**
     * Creates a new instance of ExitToolMenu.
     */
    public ExitToolMenu() {
    }

    public void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Closes all open documents and then shuts down the application.
     * @param in Input parameters.
     * @param out Output parameters. 
     */
    public void execute(InputParams in, OutputParams out) {
        doExit(in, out);
    }

    public void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Closes all open documents and then shuts down the application.
     * @param in Input parameters.
     * @param out Output parameters. 
     */
    private void doExit(InputParams in, OutputParams out) {

        try {
            int closeStatus = ((CloseAllFilesMenu) MenuManager.getMenuById(
                    MenuConstants.CLOSE_ALL_FILES)).closeAll(in, out);
            if (closeStatus == CloseAllFilesMenu.CLOSED_ALL) {
                shutDown();
            }
        } catch (Exception ex) {
            Logger.logError("Error while closing the application.", ex);
            // In case there is exception forcefully close the editor, otherwise user may face problem.
            shutDown();
        }
    }

    /**
     * Shuts down the application.
     */
    private void shutDown() {
        try {
            Logger.logInfo("Closing the application.");
            getContext().getEditorComponents().getFrame().dispose();
            //getContext().getConfiguration().disposeIfCacheNotRequired();
            getContext().getConfiguration().remove();
            System.exit(0);
        } catch (Exception ex) {
            Logger.logError("Error while closing the application.", ex);
            System.exit(-1);
        }
    }
}
