/* 
 * CustomToolMenu.java
 * Created on Dec 27, 2007,11:41:32 PM
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

import org.apex.base.data.CustomTool;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.function.CustomToolProcessor;
import org.apex.base.function.Function;
import org.apex.base.logging.Logger;

/**
 * A menu target to process a custom tool added by user.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CustomToolMenu extends UILessMenu {

    /**
     * Constructs a new CustomToolMenu.
     */
    public CustomToolMenu() {
        super();
    }

    /**
     * Runs a selected tool.
     * <p>
     * Gets the tool definition from menu configuration data and
     * calls {@link CustomToolProcessor#process(InputParams, OutputParams) } method
     * to process the custom tool.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    protected void execute(InputParams in, OutputParams out) {
        runTool(getSelectedTool(in));
    }

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    @Override
    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Runs a given custom tool.
     * <p>
     * It calls {@link CustomToolProcessor#process(InputParams, OutputParams) } method
     * to process the custom tool.
     * @param tool The custom tool to be processed.
     */
    private void runTool(CustomTool tool) {
        Logger.logInfo("Processing custom tool: " + tool.getName());
        Function customToolProcessor = new CustomToolProcessor(tool);
        customToolProcessor.process(new InputParams(), new OutputParams());
    }

    /**
     * It obtains the {@code CustomTool} definition from menu configuration data.
     * @param in Input parameters.
     * @return Definition of custom tool.
     */
    private CustomTool getSelectedTool(InputParams in) {
        String toolId = (String) in.get("MENU_ID");
        return this.getContext().getConfiguration().getMenuConfig().getCustomTools().
                getCustomToolById(toolId);
    }
}
