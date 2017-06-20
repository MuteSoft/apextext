/*
 * GenerateJavadocPackage.java
 * Created on 24 June, 2007, 10:45 PM
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
import org.apex.base.data.IDocumentType;

/**
 * Javadoc generator for a Java package.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class GenerateJavadocPackage extends GenerateJavadoc {

    /**
     * Creates a new instance of GenerateJavadocPackage.
     * @param tool The tool to be executed.
     */
    public GenerateJavadocPackage(Tool tool) {
        super(tool);
    }

    @Override
    public String[] getBaseCommand() {
        return new String[]{"javadoc"};
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
        if (!packageName.equals("")) {
            packageName = packageName.replace('.', '/');
            return new String[]{packageName + "/*.java"};
        }
        return new String[]{fileName};
    }
}
