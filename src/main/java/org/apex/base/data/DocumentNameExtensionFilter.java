/* 
 * DocumentNameExtensionFilter.java
 * Created on 20 Nov, 2007,4:51:34 AM
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
package org.apex.base.data;

import java.io.File;
import java.util.Vector;
import javax.swing.filechooser.FileFilter;

/**
 * An implementation of {@code FileFilter} that filters using a
 * specified set of extensions. The extension for a file is the
 * portion of the file name after the last ".". Files whose name does
 * not contain a "." have no file name extension.
 * <p>
 * File name extension comparisons are case insensitive.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentNameExtensionFilter extends FileFilter {

    /**
     * Known extensions.
     */
    private Vector<String> extensions;
    /**
     * Document type.
     */
    private IDocumentType docType;

    /**
     * Creates a {@code DocumentNameExtensionFilter} with the specified
     * description and file name extensions. The returned {@code
     * DocumentNameExtensionFilter} will accept all directories and any
     * file with a file name extension contained in {@code extensions}.
     * @param extensions The accepted file name extensions.
     * @param docType The document type.
     * 
     */
    public DocumentNameExtensionFilter(Vector<String> extensions,
            IDocumentType docType) {
        this.extensions = extensions;
        this.docType = docType;
    }

    /**
     * Sets file extensions.
     * @param extensions The accepted file name extensions.
     */
    public void setExtensions(Vector<String> extensions) {
        this.extensions = extensions;
    }

    /**
     * Returns boolean indicating whether or not  the given file
     * is accepted by this filter.
     * @param file The file.
     * @return {@code true} if given file should be accepted; otherwise returns {@code false}.
     */
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        // Ok, its a regular file, so check the extension
        String name = file.getName();
        // File does not have an extension
        if (name.indexOf('.') == -1) {
            return false;
        }
        String extension = name.substring(name.lastIndexOf('.') + 1);       
        if (extensions.contains(extension.toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     *  The description of this filter. For example: "Java Documents"
     * @return The description of this filter.
     */
    public String getDescription() {
        StringBuilder description = new StringBuilder(this.docType + " (");
        int noOfExtns = this.extensions.size();
        for (int iCount = 0; iCount < noOfExtns; iCount++) {
            description.append("*." + this.extensions.get(iCount));
            if (iCount < noOfExtns - 1) {
                description.append(",");
            }
        }
        description.append(")");
        return description.toString();
    }

    /**
     * Returns the document type.
     * @return The document type.
     */
    public IDocumentType getDocumentType() {
        return this.docType;
    }
}
