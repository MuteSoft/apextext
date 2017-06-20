/*
 * CompileJavaPackage.java
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

import org.apex.base.data.AbstractDocument;
import org.apex.base.data.IDocumentType;
import org.apex.base.data.JavaDocument;
import org.apex.base.data.Tool;

/**
 * A Java package compiler.
 * <p>
 * It compiles the package of currently displayed Java class.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CompileJavaPackage extends JavaToolProcessor {

    /**
     * Creates a new instance of CompileJavaPackage.
     * @param tool The tool to be processed.
     */
    public CompileJavaPackage(Tool tool) {
        super(tool);
    }

    public String[] getBaseCommand() {
        return new String[]{"javac"};
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
        String packageName =
                ((JavaDocument) getContext().getEditorProperties().
                getCurrentDocument()).getJavaPackage();
        if (!packageName.equals("")) {
            packageName = packageName.replace('.', '/');
            return new String[]{packageName + "/*.java"};
        }
        return new String[]{fileName};
    }
}
