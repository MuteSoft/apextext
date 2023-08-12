/*
 * DocumentExtensionChangeListener.java
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
package org.apex.base.event;

import org.apex.base.core.EditorBase;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.DocumentWrapper;
import org.apex.base.data.EditorContext;
import org.apex.base.data.HighlightedDocument;
import org.apex.base.data.IDocumentType;
import org.apex.base.settings.DocumentTypesConfiguration;
import org.apex.base.settings.event.DocTypesConfigChangeEvent;
import org.apex.base.settings.event.DocTypesConfigChangeListener;
import org.apex.base.util.DocumentCreator;

/**
 * A listener to update  a document when mapping between document types
 * and document extensions changed by user from 'Preferences'.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentExtensionChangeListener implements
        DocTypesConfigChangeListener {

    /**
     * The wrapper of a document.
     */
    private DocumentWrapper documentWrapper;

    /**
     * Creates a new instance of {@code DocumentExtensionChangeListener} using specified
     * document wrapper.
     * @param documentWrapper A document wrapper.
     */
    public DocumentExtensionChangeListener(
            DocumentWrapper documentWrapper) {
        this.documentWrapper = documentWrapper;
    }

    /**
     * This method is called when mapping between document types
     * and document extensions changed by user from 'Preferences'. If the
     * contained document wrapper is affected it updates the document with
     * appopriate syntax style.
     * @param event The document types configuration change event.
     */
    public void propertyValueChanged(DocTypesConfigChangeEvent event) {
        DocumentTypesConfiguration docTypesConfig =
                getContext().getConfiguration().
                getGeneralConfig().
                getDocTypes();
        // Get the document
        AbstractDocument file = this.documentWrapper.getDocument();
        // Get the document type
        IDocumentType docType = file.getDocumentType();

        // Check whether document type (where this file extension belongs to) is affected
        // Exclude document type 'DEFAULT' here, because 'DEFAULT' is not counted.        
        if (!docType.equals(IDocumentType.DEFAULT_DOCUMENT)
                && !docTypesConfig.isDocTypeAffected(docType)) {
            // Return if this document type is not affected.
            return;
        }
        // Determine the current document type for this file extension
        IDocumentType newDocType =
                docTypesConfig.getDocumentType(file.getExtension());

        // Check whether now it belongs to some other document type
        if (docType.equals(newDocType)) {
            // If no then return
            return;
        }
        // If yes then create new document type value and apply new styles
        file = DocumentCreator.verifyAndMigrateDocument(file, file.
                getAbsolutePath(), getContext());
        if (file.getDocument() instanceof HighlightedDocument) {
            ((HighlightedDocument) file.getDocument()).setHighlightStyle(file.
                    getLexer(),
                    file.getDocumentStyle());
        }
        // Fix for bug id 2128941 (sourceforge.net)
        this.documentWrapper.setDocument(file);
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }
}
