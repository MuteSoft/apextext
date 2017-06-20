/*
 * RunJavaAppletMenu.java
 * Created on 1 Jan, 2010, 1:20 PM
 *
 * Copyright (C) 2010 Mrityunjoy Saha
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
import org.apex.base.data.ProvidedTool;
import org.apex.base.function.CustomToolProcessor;
import org.apex.base.function.Function;
import org.apex.base.logging.Logger;

/**
 * A menu target to execute executables like bat, exe etc.
 * <p>
 * This component attempts to execute current document irrespective
 * of document type with the help of {@link CustomToolProcessor} function.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.2

 */
public class RunExecutableMenu extends UILessMenu {

    /**
     * The target tool to be executed.
     */
    private static CustomTool runExecTool = null;

    /**
     * Creates a new instance of {@code RunJavaAppletMenu}.
     */
    public RunExecutableMenu() {
    }

    protected void preProcess(InputParams in, OutputParams out) {
        if (runExecTool == null) {
            String menuId = (String) in.get("MENU_ID");
            runExecTool = new CustomTool(menuId, null, null);
        }
    }

    /**
     * It caches the {@code tool} definition
     * then calls {@link RunExecutableMenu#doRun(InputParams, OutputParams) }
     * to execute the 'Run' tool.
     * <p>
     * In case an associated {@code tool} is not found for this menu,
     * execution happens with default options and parameters.\
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void execute(InputParams in, OutputParams out) {
        doRun(in, out);
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Runs the current executable document.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    private void doRun(InputParams in, OutputParams out) {
        Logger.logInfo("Processing 'Run' tool");
        String menuId = (String) in.get("MENU_ID");
        ProvidedTool baseTool = getContext().getConfiguration().getMenuConfig().
                getProvidedTools().getProvidedToolById(menuId);
        runExecTool.setName(baseTool.getName());
        runExecTool.setDescription(baseTool.getDescription());
        runExecTool.setExecutable(getContext().getEditorProperties().
                getCurrentDocument().getAbsolutePath());
        runExecTool.setWorkingDir(getContext().getEditorProperties().
                getCurrentDocument().getDirectory());
        runExecTool.setRuntimeParamRequired(baseTool.isRuntimeParamRequired());
        Function customToolProcessor = new CustomToolProcessor(runExecTool);
        customToolProcessor.process(new InputParams(), new OutputParams());
    }
}