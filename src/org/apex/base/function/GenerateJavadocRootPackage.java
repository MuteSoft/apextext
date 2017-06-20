/*
 * GenerateJavadocRootPackage.java
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

import org.apex.base.data.AbstractDocument;
import org.apex.base.data.Tool;
import org.apex.base.util.StringUtil;
import org.apex.base.data.IDocumentType;

/**
 * Generates Javadoc for root package of currently displayed Java class.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class GenerateJavadocRootPackage extends GenerateJavadoc {

    /**
     * Creates a new instance of GenerateJavadocRootPackage.
     * @param tool The tool to be processed.
     */
    public GenerateJavadocRootPackage(Tool tool) {
        super(tool);
    }

    @Override
    public String[] getBaseCommand() {
        return new String[]{"javadoc", "-subpackages"};
    }

    @Override
    public String[] getResources() {
        String fileName = getContext().getEditorProperties().getCurrentDocument().
                getName();
        AbstractDocument currentDoc = getContext().getEditorProperties().
                getCurrentDocument();
        if (!currentDoc.getDocumentType().equals(IDocumentType.JAVA_DOCUMENT)) {
            return new String[]{fileName};
        }
        String packageName = getPackage();
        if (!StringUtil.isNullOrEmpty(packageName)) {
            if (packageName.indexOf(".") != -1) {
                return new String[]{packageName.substring(0, packageName.indexOf(
                            "."))
                        };
            } else {
                return new String[]{packageName};
            }
        }
        return new String[]{fileName};
    }
}
