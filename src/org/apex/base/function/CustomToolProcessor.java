/* 
 * CustomToolProcessor.java
 * Created on 3 Jan, 2008,11:55:40 PM
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

import org.apex.base.data.CustomTool;
import org.apex.base.data.IDocumentType;
import org.apex.base.util.FileUtil;

/**
 * A custom tool processor.
 * <p>
 * Custom tools are added by user.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CustomToolProcessor extends ToolProcessor {

    /**
     * Constructs a custom tool processor with given tool.
     * @param tool The tool to be executed.
     */
    public CustomToolProcessor(CustomTool tool) {
        super(tool);
    }

    @Override
    public String[] getBaseCommand() {
        if (isExecutableFile(this.tool.getExecutable())) {
            return new String[]{this.tool.getExecutable()};
        } else {
            return new String[]{"cmd", "/c", this.tool.getExecutable()};
        }
    }

    @Override
    public String getWorkingDirectory() {
        return ((CustomTool) this.tool).getWorkingDir();
    }

    @Override
    public String[] getResources() {
        return null;
    }

    /**
     * Determines whether or not the underlying file in given absolute path is
     * a valid executable.
     * @param executable The path of file.
     * @return The boolean indicates whether or not the underlying file in given absolute path is
     * a valid executable.
     * @see IDocumentType#isExecutable(java.lang.String)
     */
    private boolean isExecutableFile(String executable) {
        if (FileUtil.isFileExists(executable)) {
            if (executable.indexOf(".") != -1) {
                if (getContext().getEditorProperties().getDocumentTypeBase().
                        isExecutable(executable.substring(executable.lastIndexOf(
                        ".") + 1).
                        toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }
}
