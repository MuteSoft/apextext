/* 
 * DocumentTemplateConfiguration.java
 * Created on 19 Nov, 2007,11:33:53 PM
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
package org.apex.base.settings;

import java.util.HashMap;
import java.util.Iterator;
import org.apex.base.data.IDocumentType;

/**
 *  A configuration object for document templates.
 * <p>
 * Supported document types are pre-defined. User can add code template for a document type. The
 * code template is inserted when a new temporary document is created in editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentTemplateConfiguration extends AbstractConfiguration {

    /**
     * Document Templates.
     */
    private HashMap<IDocumentType, String> docTemplates;

    /**
     * Creates a new instance of {@code DocumentTemplateConfiguration}.
     */
    public DocumentTemplateConfiguration() {
        super();
    }

    /**
     * Returns document templates.
     * @return Document templates.
     * @see #setDocTemplates(java.util.HashMap)
     */
    public HashMap<IDocumentType, String> getDocTemplates() {
        return docTemplates;
    }

    /**
     * Sets document templates.
     * @param docTemplates Document templates.
     * @see #getDocTemplates() 
     */
    public void setDocTemplates(HashMap<IDocumentType, String> docTemplates) {
        this.docTemplates = docTemplates;
    }

    @Override
    public String toString() {
        return "Document Templates: " + this.docTemplates;
    }

    public void updateFromClone(Object clonedObject) {
        DocumentTemplateConfiguration clonedConfig =
                (DocumentTemplateConfiguration) clonedObject;
        updateDocTemplates(clonedConfig.getDocTemplates());
    // we can fire the configuration change event if required.        
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DocumentTemplateConfiguration clonedConfig = null;
        try {
            clonedConfig =
                    (DocumentTemplateConfiguration) super.clone();
            // It does a shallow copy. Keys and values themselves are not cloned.
            @SuppressWarnings("unchecked")
            HashMap<IDocumentType, String> clonedDocTemplates =
                    (HashMap<IDocumentType, String>) (this.docTemplates).clone();
            // Commenting below line because TreeMap contains String only. So shallow copy
            // is not a problem in this case.
            //strictCopyElementsTo(clonedDocTemplates);
            clonedConfig.setDocTemplates(clonedDocTemplates);
        } catch (CloneNotSupportedException ex) {
        }
        return clonedConfig;
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof DocumentTemplateConfiguration) {
            DocumentTemplateConfiguration clonedConfig =
                    (DocumentTemplateConfiguration) clonedObject;
            value = !isDocTemplatesChanged(clonedConfig.getDocTemplates());
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.docTemplates != null
                ? this.docTemplates.hashCode()
                : 0);
        return hash;
    }

    /**
     * Determines whether or not custom tools are changed. It compares document templates
     * with given document templates.
     * @param newDocTemplates Document templates.
     * @return {@code true} if document templates are changed; otherwise returns {@code false}.
     */
    private boolean isDocTemplatesChanged(
            HashMap<IDocumentType, String> newDocTemplates) {
        Iterator docsIterator = newDocTemplates.keySet().iterator();
        while (docsIterator.hasNext()) {
            IDocumentType docType = (IDocumentType) docsIterator.next();
            String prevTemplate = this.docTemplates.get(docType);
            String newTemplate = newDocTemplates.get(docType);
            if (isChanged(prevTemplate, newTemplate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Copy document templates from a specified table.
     * @param newDocTemplates Document templates.
     */
    private void updateDocTemplates(
            HashMap<IDocumentType, String> newDocTemplates) {
        Iterator docsIterator = newDocTemplates.keySet().iterator();
        while (docsIterator.hasNext()) {
            IDocumentType docType = (IDocumentType) docsIterator.next();
            String prevTemplate = this.docTemplates.get(docType);
            String newTemplate = newDocTemplates.get(docType);
            if (isChanged(prevTemplate, newTemplate)) {
                this.docTemplates.put(docType, newTemplate);
            }
        }
    }

    public boolean isConfigurable() {
        return true;
    }

    public String getConfigFile() {
        return "DocumentTemplate";
    }

    public boolean isCacheRequired() {
        return false;
    }

    public boolean disposeIfCacheNotRequired() {
        if (this.docTemplates != null) {
            this.docTemplates.clear();
            this.docTemplates = null;
        }
        return true;
    }

    public boolean isLeaf() {
        return true;
    }

    public void remove() {
        disposeIfCacheNotRequired();
    }
}
