/*
 * DocumentCreator.java 
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
package org.apex.base.util;

import org.apex.base.data.AbstractDocument;
import org.apex.base.data.EditorContext;
import org.apex.base.data.IDocumentType;

/**
 * A document creation utility. Also it migrates a document from one type to another
 * when document extension changes.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class DocumentCreator {

    /**
     * Creates a new instance of {@code DocumentCreator}.
     */
    private DocumentCreator() {
    }

    /**
     * Creates a new document from given fully qualified path.
     * @param absolutePath Fully qualified path.
     * @param context The editor context.
     * @return The {@code AbstractDocument} object.
     */
    public static AbstractDocument createDocument(String absolutePath,
            EditorContext context) {
        AbstractDocument document = null;
        String extension = FileUtil.getExtension(absolutePath);
        IDocumentType documentType = DocumentData.getDocumentType(context,
                extension);       
        document = (AbstractDocument) InstanceCreator.createInstance(documentType.
                getDocumentClass(),
                new Class[]{String.class, IDocumentType.class}, new Object[]{
                    absolutePath,
                    documentType});
        // File indices should start from 0
        document.setIndex(context.getEditorComponents().getEditorBody().
                getDocSelector().
                getDocumentListSize());
        return document;
    }

    /**
     * This method verifies and migrates a document accordingly when fully qualified path
     * is changed.
     * @param oldDcument The existing document.
     * @param newAbsolutePath New fully qualified path.
     * @param context The editor context.
     * @return The new document object.
     */
    public static AbstractDocument verifyAndMigrateDocument(
            AbstractDocument oldDcument,
            String newAbsolutePath,
            EditorContext context) {
        AbstractDocument newDocument = null;
        String newExtension = FileUtil.getExtension(newAbsolutePath);
        IDocumentType newDocumentType = DocumentData.getDocumentType(context,
                newExtension);
        if (!oldDcument.getDocumentType().equals(newDocumentType)) {
            newDocument = createDocument(newAbsolutePath, context);
            oldDcument.copyPropertiesTo(newDocument);
        } else {
            newDocument = oldDcument.clone(newAbsolutePath);
        }
        newDocument.setIndex(oldDcument.getIndex());
        oldDcument = null;
        return newDocument;
    }
}
