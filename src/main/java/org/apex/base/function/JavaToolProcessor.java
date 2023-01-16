/*
 * JavaToolProcessor.java
 * Created on 24 June, 2007, 6:33 PM
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

import org.apex.base.common.CommonMessageManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.IDocumentType;
import org.apex.base.data.JavaDocument;
import org.apex.base.data.Tool;

/**
 * A base class for all Java related provided tool processors.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class JavaToolProcessor extends ToolProcessor {

    /**
     * The package of given Java document.
     */
    private String packageName = null;

    /**
     * Creates a new instance of JavaToolProcessor.
     * @param tool The tool to be processed.
     */
    public JavaToolProcessor(Tool tool) {
        super(tool);
        if (getContext().getEditorProperties().getCurrentDocument().getDocumentType().equals(
                IDocumentType.JAVA_DOCUMENT)) {
            this.packageName =
                    ((JavaDocument) getContext().getEditorProperties().getCurrentDocument()).
                    getJavaPackage();
        } else {
            CommonMessageManager.showErrorMessage(getContext().getEditorComponents().getFrame(),
                    1006);
            throw new IllegalArgumentException("Not a valid Java document.");
        }
    }

    @Override
    public String[] getResources() {
        String fileName = getContext().getEditorProperties().getCurrentDocument().getName();
        AbstractDocument currentDoc = getContext().getEditorProperties().getCurrentDocument();
        if (!currentDoc.getDocumentType().equals(IDocumentType.JAVA_DOCUMENT)) {
            return new String[]{fileName};
        }
        if (packageName != null && !packageName.equals("")) {
            return new String[]{packageName.replace('.', '/') + "/" + fileName};
        }
        return new String[]{fileName};
    }

    public String getWorkingDirectory() {
        AbstractDocument currentDoc = getContext().getEditorProperties().getCurrentDocument();
        if (!currentDoc.getDocumentType().equals(IDocumentType.JAVA_DOCUMENT)) {
            return currentDoc.getDirectory();
        }
        return ((JavaDocument) getContext().getEditorProperties().getCurrentDocument()).
                getJavaDirectory(this.packageName);
    }

    /**
     * Returns the package of given Java document.
     * <p>
     * Generally currently displayed document is considered.
     * @return The package of given Java document.
     */
    protected final String getPackage() {
        return this.packageName;
    }
}
