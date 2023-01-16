/*
 * ToolProcessor.java
 * Created on 24 June, 2007, 6:32 PM
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
package org.apex.base.function;

import org.apex.base.component.RunOptionsParamsDialog;
import org.apex.base.constant.CommonConstants;
import org.apex.base.data.Command;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.data.Tool;
import org.apex.base.logging.Logger;
import org.apex.base.ui.RunOptionsParamsView;
import org.apex.base.util.StringUtil;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

/**
 * A base class for editor provided tool processors and custom tool processors.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class ToolProcessor extends AbstractCommandProcessor {

    /**
     * The tool to be processed.
     */
    protected Tool tool;

    /**
     * Creates a new instance of ToolProcessor.
     * @param tool The tool to be processed.
     */
    public ToolProcessor(Tool tool) {
        this.tool = tool;
    }

    /**
     * Returns a command which  is constructed from given {@code tool}.
     * <p>
     * If change of options and parameters required at runtime, it prompts user
     * to enter options and parameters. User can either enter options and parameters or skip.
     * @return The command.
     */
    @Override
    public final Command getCommand() {
        // @TODO External console support.
        final Command cm = new Command(
                getBaseCommand(),
                getWorkingDirectory());
        if (this.tool == null) {
            return cm;
        }
        if (this.tool.isRuntimeParamRequired()) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    public void run() {
                        RunOptionsParamsDialog inputParamsDialog =
                                new RunOptionsParamsDialog(tool);
                        inputParamsDialog.createDialog(new InputParams(),
                                new OutputParams());
                        cm.addCommand(StringUtil.getArrayFromString(((RunOptionsParamsView) inputParamsDialog.
                                getView()).getOptions(),
                                CommonConstants.OPTIONS_SEPARATOR));
                        cm.addCommand(getResources());
                        cm.addCommand(StringUtil.getArrayFromString(((RunOptionsParamsView) inputParamsDialog.
                                getView()).getParams(),
                                CommonConstants.OPTIONS_SEPARATOR));
                    }
                });
            } catch (InterruptedException ex) {
                Logger.logWarning(
                        "Failed to show options and params window while executing tool: " +
                        cm,
                        ex);
            } catch (InvocationTargetException ex) {
                Logger.logWarning(
                        "Failed to show options and params window while executing tool: " +
                        cm, ex);
            }
        } else {
            cm.addCommand(getOptions());
            cm.addCommand(getResources());
            cm.addCommand(getParams());
        }        
        return cm;
    }

    @Override
    public final String[] getOptions() {
        return StringUtil.getArrayFromString(this.tool.getOptions(),
                CommonConstants.OPTIONS_SEPARATOR);
    }

    @Override
    public final String[] getParams() {
        return StringUtil.getArrayFromString(this.tool.getParams(),
                CommonConstants.OPTIONS_SEPARATOR);
    }
    /**
     * Returns the name of this tool processor.
     * @return The name of this tool processor.
     */
    @Override
    protected String getName() {
        return this.tool == null
                ? "anonymous"
                : this.tool.getDescription();
    }
}
