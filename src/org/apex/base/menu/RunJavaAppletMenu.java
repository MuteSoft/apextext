/*
 * RunJavaAppletMenu.java
 * Created on 24 June, 2007, 9:13 PM
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
import org.apex.base.data.ProvidedTool;
import org.apex.base.function.Function;
import org.apex.base.function.RunJavaApplet;

/**
 * A menu target to execute a Java applet.
 * <p>
 * This component attempts to execute current Java applet irrespective
 * of document type with the help of {@link RunJavaApplet} function.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class RunJavaAppletMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code RunJavaAppletMenu}.
     */
    public RunJavaAppletMenu() {
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * It gets the {@code tool} definition from menu configuration data
     * and then calls {@link RunJavaApplet#process(InputParams, OutputParams) }
     * to execute current Java applet.
     * <p>
     * In case an associated {@code tool} is not found for this menu, Java
     * applet execution happens with default options and parameters.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void execute(InputParams in, OutputParams out) {
        doRun(in, out);
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Runs the current Java applet.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    private void doRun(InputParams in, OutputParams out) {
        String menuId = (String) in.get("MENU_ID");
        ProvidedTool tool = getContext().getConfiguration().getMenuConfig().
                getProvidedTools().getProvidedToolById(menuId);
        Function compile = new RunJavaApplet(tool);
        compile.process(in, out);
    }
}
