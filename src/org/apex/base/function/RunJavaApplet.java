/*
 * RunJavaApplet.java
 * Created on 24 June, 2007, 9:24 PM
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apex.base.data.Tool;
import org.apex.base.logging.Logger;
import org.apex.base.util.StringUtil;

/**
 * A processor to run a Java applet.
 * <p>
 * It runs the currently displayed Java class as applet.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class RunJavaApplet extends JavaToolProcessor {

    /**
     * Creates a new instance of RunJavaApplet.
     * @param tool The tool to be processed.
     */
    public RunJavaApplet(Tool tool) {
        super(tool);
    }

    public String[] getBaseCommand() {
        return new String[]{"appletviewer"};
    }

    @Override
    public String[] getResources() {
        String retValue = super.getResources()[0];
        if (!StringUtil.isNullOrEmpty(retValue)) {
            if (retValue.indexOf(".") != -1) {
                retValue = retValue.substring(0, retValue.lastIndexOf("."));
            }
            retValue = retValue.replace('/', '.');
        }
        retValue = retValue + "_Applet.html";
        createHTMLFile(retValue);
        return new String[]{retValue};
    }

    /**
     * In working directory create the html file if does not exist.
     * <p>
     * For example, if Java class name is Test and package is com.test, then corresponding html
     * name would be com.test.Test_Applet.html.
     * @param fileName The html file name. 
     */
    private void createHTMLFile(String fileName) {
        File htmlFile =
                new File(getWorkingDirectory(), fileName);
        if (htmlFile.exists()) {
            //htmlFile.delete();
            return;
        }
        FileWriter htmlWriter = null;
        BufferedWriter buffer = null;
        try {
            htmlWriter = new FileWriter(htmlFile);
            buffer =
                    new BufferedWriter(htmlWriter);
            String code = super.getResources()[0];
            if (!StringUtil.isNullOrEmpty(code) && code.indexOf(".") != -1) {
                code = code.substring(0, code.lastIndexOf("."));
            }

            buffer.write("<HTML><HEAD><TITLE>Applet</TITLE></HEAD><BODY>" +
                    "<APPLET CODE='" + code +
                    "' CODEBASE=\".\" WIDTH=600 HEIGHT=400></APPLET></BODY></HTML>");
            buffer.flush();
        } catch (IOException ex) {
            Logger.logError("Failed to create html file for applet: " + super.getResources()[0], ex);
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                }
                if (htmlWriter != null) {
                    htmlWriter.close();
                }
            } catch (IOException ex) {
            }
        }
    }
}
