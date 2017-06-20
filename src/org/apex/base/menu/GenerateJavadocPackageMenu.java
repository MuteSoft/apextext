/*
 * GenerateJavadocPackageMenu.java
 * Created on 24 June, 2007, 10:44 PM
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
import org.apex.base.function.GenerateJavadocPackage;

/**
 * A menu target to generate javadoc for package of current document.
 * <p>
 * This component attempts to generate javadoc for package of current document
 * with the help of {@link GenerateJavadocPackage} function.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GenerateJavadocPackageMenu extends UILessMenu {

    /**
     * Creates a new instance of GenerateJavadocPackageMenu.
     */
    public GenerateJavadocPackageMenu() {
    }

    /**
     * It gets the {@code tool} definition from menu configuration data
     * and then calls {@link GenerateJavadocPackage#process(InputParams, OutputParams) }
     * to generate javadoc for package of current document.
     * <p>
     * In case an associated {@code tool} is not found for this menu, javadoc generation happens with
     * default options and parameters.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void execute(InputParams in, OutputParams out) {
        doGenerate(in, out);
    }

    protected void preProcess(InputParams in, OutputParams out) {
    }

    protected void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Generates javadoc for package of current document.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    private void doGenerate(InputParams in, OutputParams out) {
        String menuId = (String) in.get("MENU_ID");
        ProvidedTool tool = getContext().getConfiguration().getMenuConfig().
                getProvidedTools().getProvidedToolById(menuId);
        Function compile = new GenerateJavadocPackage(tool);
        compile.process(in, out);
    }
}
