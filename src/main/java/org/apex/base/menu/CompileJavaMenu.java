/*
 * CompileJavaMenu.java
 * Created on December 27, 2006, 11:36 PM
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
import org.apex.base.function.CompileJava;

/**
 * A menu target to compile a single java class.
 * <p>
 * This component attempts to compile the current document irrespective of document type
 * with the help of {@link CompileJava} function.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 * @see CompileJava
 */
public class CompileJavaMenu extends UILessMenu {

    /**
     * Creates a new instance of CompileJavaMenu.
     */
    public CompileJavaMenu() {
    }

    public void preProcess(InputParams in, OutputParams out) {
    }

    public void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Compiles the current document.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    private void doCompile(InputParams in, OutputParams out) {
        String menuId = (String) in.get("MENU_ID");
        ProvidedTool tool = getContext().getConfiguration().getMenuConfig().
                getProvidedTools().getProvidedToolById(menuId);     
        Function compile = new CompileJava(tool);
        compile.process(in, out);
    }

    /**
     * Compiles the current document.
     * <p>
     * It gets the {@code tool} definition from menu configuration data
     * and then calls {@link CompileJava#process(InputParams, OutputParams)}
     * to compile the current document.
     * <p>
     * In case an associated {@code tool} is not found for this menu, compilation happens with
     * default options and parameters.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void execute(InputParams in, OutputParams out) {
        doCompile(in, out);
    }
}
