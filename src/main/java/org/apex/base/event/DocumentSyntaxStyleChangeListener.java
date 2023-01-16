/*
 * DocumentSyntaxStyleChangeListener.java
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
import org.apex.base.data.HighlightedDocument;
import org.apex.base.data.IDocumentType;
import org.apex.base.settings.SyntaxStyleConfiguration;
import org.apex.base.settings.event.SyntaxStyleConfigChangeEvent;
import org.apex.base.settings.event.SyntaxStyleConfigChangeListener;

/**
 * A listener to update syntax style of a document when syntax style settings is changed
 * by user from 'Preferences'.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentSyntaxStyleChangeListener implements SyntaxStyleConfigChangeListener {

    /**
     * The document wrapper.
     */
    private DocumentWrapper documentWrapper;

    /**
     * Constructs a new instance of {@code DocumentSyntaxStyleChangeListener} using
     * specified document wrapper.
     * @param documentWrapper A document wrapper containing the document.
     */
    public DocumentSyntaxStyleChangeListener(
            DocumentWrapper documentWrapper) {
        this.documentWrapper = documentWrapper;
    }

    /**
     * Based on type of associated document, it determines whether or not the
     * document is affected as a result of syntax style settings change. If associated
     * document is affected then syntax style is re-applied to the document,
     * otherwise this method has no effect.
     * @param event The syntax style configuration change event.
     */
    public void propertyValueChanged(SyntaxStyleConfigChangeEvent event) {
        SyntaxStyleConfiguration syntaxStyleConfig =
                EditorBase.getContext().getConfiguration().
                getStyleConfig().
                getSyntaxStyle();
        // Get the document
        AbstractDocument file = this.documentWrapper.getDocument();
        // Get the document type
        IDocumentType docType = file.getDocumentType();

        // Check whether document type is affected
        if (!syntaxStyleConfig.isDocTypeAffected(docType)) {
            // Return if this document type is not affected.
            return;
        }
        // Update Styles
        if (file.getDocument() instanceof HighlightedDocument) {
            HighlightedDocument document = (HighlightedDocument) file.
                    getDocument();
            document.getSyntaxStyle().addStyles();
            // Apply Style
            document.setHighlightStyle(file.getLexer(),
                    file.getDocumentStyle());
        }
    }
}
