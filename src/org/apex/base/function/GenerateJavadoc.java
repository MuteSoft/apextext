/*
 * GenerateJavadoc.java
 * Created on 24 June, 2007, 10:27 PM
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

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.data.Tool;
import org.apex.base.menu.ViewDocumentInBrowserMenu;
import java.io.File;

/**
 * Generates Javadoc for currently displayed Java class.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GenerateJavadoc extends JavaToolProcessor {

    /**
     * Creates a new instance of GenerateJavadoc.
     * @param tool The tool to be processed.
     */
    public GenerateJavadoc(Tool tool) {
        super(tool);
    }

    public String[] getBaseCommand() {
        return new String[]{"javadoc"};
    }

    /**
     * Opens generated Javadoc in default browser of user.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    public void postExecute(InputParams in, OutputParams out) {
        super.postExecute(in, out);
        if (isSuccessful()) {
            // Open the root html file of generated Javadoc
            File file = new File(getWorkingDirectory(), "index.html");
            ((ViewDocumentInBrowserMenu) MenuManager.getMenuById(
                    MenuConstants.VIEW_IN_WEB_BROWSER)).browse(file);
        }
    }
}
