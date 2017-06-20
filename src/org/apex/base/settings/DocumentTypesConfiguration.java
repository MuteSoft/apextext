/*
 * DocumentTypesConfiguration.java
 * Created on 14 July, 2007, 1:05 PM
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

import java.util.ArrayList;
import org.apex.base.data.DocumentType;
import org.apex.base.util.StringUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.apex.base.core.EditorBase;
import org.apex.base.data.IDocumentType;

/**
 * A configuration object for document types to document extensions mapping.
 * <p>
 * For each supported document type a default extension is provided. On top of this user
 * can add any number of extensions to a document type and also user can edit or remove
 * extensions .
 * @author Mrityunjoy Saha
 * @version 1.2
 * @since Apex 1.0
 */
public class DocumentTypesConfiguration extends AbstractConfiguration {

    /**
     * A table containing document types and their associated extensions.
     * Key is document Type and value contains documnet extensions.
     */
    private HashMap<IDocumentType, Vector<String>> docTypeExtnMap;
    /**
     * The master list of document types. This list must be always in sync with
     * The document type list maintained by {@code IDocumentType} implementations.
     */
    private List<IDocumentType> documentTypes = null;
    /**
     * Capture a list of modified/affected document types. This
     * list must be cleared every time user chooses to update/view
     * settings page.
     */
    private Vector<IDocumentType> affectedDocTypes =
            new Vector<IDocumentType>();

    /**
     * Creates a new instance of {@code DocumentTypesConfiguration}.
     */
    public DocumentTypesConfiguration() {
        if (this.documentTypes == null) {
            this.documentTypes = new ArrayList<IDocumentType>();
            this.documentTypes.addAll(EditorBase.getContext().
                    getEditorProperties().
                    getDocumentTypeBase().getDocumentTypes());
        }
    }

    /**
     * Add a document type to affected/updated document types list.
     * @param docType Document type.
     */
    public void addAffectedDocType(IDocumentType docType) {
        if (!this.affectedDocTypes.contains(docType)) {
            this.affectedDocTypes.add(docType);
        }
    }

    /**
     * Clears the list of affected/ updated document types.
     */
    public void clearAffectedDocTypeList() {
        if (this.affectedDocTypes != null) {
            this.affectedDocTypes.clear();
        }
    }

    /**
     * Checks whether extensions of a given document type is updated by user.
     * @param docType The document type.
     * @return {@code true} if extensions of specified document type is changed; otherwise
     *                returns {@code false}.
     */
    public boolean isDocTypeAffected(IDocumentType docType) {
        return this.affectedDocTypes.contains(docType);
    }

    /**
     * Returns a table containing document types and their associated extensions.
     * @return A table containing document types and their associated extensions.
     * @see #setDocTypeExtnMap(java.util.HashMap)
     */
    public HashMap<IDocumentType, Vector<String>> getDocTypeExtnMap() {
        return docTypeExtnMap;
    }

    /**
     * Sets a table containing document types and their associated extensions.
     * @param docTypeExtnMap A table containing document types and their associated extensions.
     * @see #getDocTypeExtnMap()
     */
    public void setDocTypeExtnMap(
            HashMap<IDocumentType, Vector<String>> docTypeExtnMap) {
        this.docTypeExtnMap = docTypeExtnMap;
    }

    /**
     * Returns document extensions for a given document type.
     * @param docType Document type.
     * @return A list of document extensions.
     */
    public Vector<String> getExtensions(IDocumentType docType) {
        if (this.docTypeExtnMap == null) {
            return new Vector<String>();
        }
        if (docTypeExtnMap.containsKey(docType)) {
            return docTypeExtnMap.get(docType);
        } else {
            return new Vector<String>();
        }
    }

    /**
     * Returns the complete list of document types. This list includes
     * custom document types as well.
     * @return The complete list of document types.
     * @see #setDocumentTypes(java.util.List)
     */
    public List<IDocumentType> getDocumentTypes() {        
        if (this.documentTypes == null) {
            this.documentTypes = new ArrayList<IDocumentType>();
            this.documentTypes.addAll(EditorBase.getContext().
                    getEditorProperties().
                    getDocumentTypeBase().getDocumentTypes());
        }
        return this.documentTypes;
    }

    /**
     * Sets list of document types.
     * @param documentTypes A list of document types.
     * @see #getDocumentTypes()
     */
    public void setDocumentTypes(List<IDocumentType> documentTypes) {
        this.documentTypes = documentTypes;
    }

    /**
     * Adds a document type to the list.
     * <p>
     * Document type added by user is added to the list.
     * @param docType A document type.
     */
    public void addDocumentType(IDocumentType docType) {
        if (!this.documentTypes.contains(docType)) {
            this.documentTypes.add(docType);
            if (this.docTypeExtnMap != null) {
                this.docTypeExtnMap.put(docType, new Vector<String>(1));
            }
        }
    }

    /**
     * Removes a document type from the list.
     * @param docType A document type.
     */
    public void removeDocumentType(IDocumentType docType) {
        if (this.documentTypes.contains(docType)) {
            this.documentTypes.remove(docType);
            this.docTypeExtnMap.remove(docType);
        }
    }

    /**
     * Returns a boolean that indicates whether or not given document type contains given
     * document extension.
     * @param docType Document type.
     * @param extension Document extension.
     * @return {@code true} if given extension is mapped to given document type;
     *                otherwise returns {@code false}.
     */
    public boolean containsExtension(IDocumentType docType, String extension) {
        extension = extension.toLowerCase();
        return getExtensions(docType).contains(extension);
    }

    /**
     * Returns document type for a given document extension. If specified
     * document extension is not mapped to any document type, it returns {@code null}.
     * @param extension Document extension.
     * @return Document type containing the extension.
     */
    public IDocumentType getDocumentType(String extension) {
        IDocumentType defaultDocType = DocumentType.DEFAULT;
        if (StringUtil.isNullOrEmpty(extension) || this.docTypeExtnMap == null) {
            return defaultDocType;
        }
        for (Iterator docs = this.docTypeExtnMap.keySet().iterator(); docs.
                hasNext();) {
            IDocumentType docType = (IDocumentType) docs.next();
            if (docType.getDefaultExtension().equalsIgnoreCase(extension) || containsExtension(
                    docType,
                    extension)) {
                return docType;
            }
        }
        return defaultDocType;
    }

    @Override
    public String toString() {
        return "docTypes: " + documentTypes + "docTypeExtnMap: " + docTypeExtnMap;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Object clone() {
        DocumentTypesConfiguration clonedConfig = null;
        try {
            clonedConfig =
                    (DocumentTypesConfiguration) super.clone();
            // Clone document types
            List<IDocumentType> clonedDocTypes = new ArrayList<IDocumentType>();
            strictCopyElementsTo(clonedDocTypes);
            clonedConfig.setDocumentTypes(clonedDocTypes);
            // HashMap clone is a shallow clone. Keys and values themselves are not cloned.
            // However Vector cloning is a perfect one. The copy will contain a
            // reference to a clone of the internal data array, not a reference
            // to the original internal data array of the original object
            HashMap<IDocumentType, Vector<String>> clonedDocTypeExtns =
                    (HashMap<IDocumentType, Vector<String>>) (this.docTypeExtnMap).
                    clone();
            strictCopyElementsTo(clonedDocTypeExtns);
            clonedConfig.setDocTypeExtnMap(clonedDocTypeExtns);
        } catch (CloneNotSupportedException ex) {
        }
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        DocumentTypesConfiguration clonedConfig =
                (DocumentTypesConfiguration) clonedObject;
        // Update document types
        updateDocumentTypes(clonedConfig.getDocumentTypes());
        // Update document extensions
        updateDocTypeExtns(clonedConfig.getDocTypeExtnMap());
        fireDocumentTypesConfigurationChanged(null);
    }

    /**
     * Determines whether or not mapping between document types and document extensions 
     * is changed.
     * @param newDocTypeExtns A table containing mapping between document types
     *                and document extensions.
     * @return {@code true} if mapping between document types and document extensions is
     *                changed; otherwise returns {@code false}.
     */
    private boolean isDocTypeExtnChanged(
            HashMap<IDocumentType, Vector<String>> newDocTypeExtns) {
        if (docTypeExtnMap.size() != newDocTypeExtns.size()) {
            return true;
        }
        // Both are having equal number of document types and exactly same document types.
        Iterator docsIterator = newDocTypeExtns.keySet().iterator();
        while (docsIterator.hasNext()) {
            IDocumentType docType = (IDocumentType) docsIterator.next();
            Vector<String> prevExtensions = docTypeExtnMap.get(docType);
            Vector<String> newExtensions = newDocTypeExtns.get(docType);
            if (isChanged(prevExtensions, newExtensions)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether or not list of document types changed.
     * <p>
     * This method checks only custom document  types as in-built document types
     * can not be updated by application user.
     * @param newDocTypes List of new document types.
     * @return {@code true} if a new document type is added or a document type is modified or deleted.
     */
    private boolean isDocTypesChanged(List<IDocumentType> newDocTypes) {
        // Check whether number of document types changed
        if (this.documentTypes.size() != newDocTypes.size()) {
            return true;
        }
        // Both are having equal number of document types and exactly same document types.
        for (IDocumentType newDocType : newDocTypes) {
            if (newDocType.isCustomDocumentType()) {
                // Document type is added.
                if (!this.documentTypes.contains(newDocType)) {
                    return true;
                } else {
                    for (IDocumentType oldDocType : this.documentTypes) {
                        // Document type is deleted.
                        if (!newDocTypes.contains(oldDocType)) {
                            return true;
                        }
                        // Document type is updated.
                        if (newDocType.equals(oldDocType)) {
                            // Check other properties.
                            if (isDocumentTypeChanged(oldDocType, newDocType)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Clones document types.
     * @param clonedDocTypes A list where cloned document types are copied.
     */
    private void strictCopyElementsTo(List<IDocumentType> clonedDocTypes) {
        clonedDocTypes.clear();
        for (IDocumentType docType : this.documentTypes) {
            try {
                clonedDocTypes.add((IDocumentType) (docType).clone());
            } catch (CloneNotSupportedException ex) {
            }
        }
    }

    /**
     *  Copy document extensions to a specified table.
     * @param clonedDocTypes A table containing mapping between document types
     *                and document extensions.
     */
    private void strictCopyElementsTo(
            HashMap<IDocumentType, Vector<String>> clonedDocTypes) {
        // Clear the cloned HashMap and then start copying.
        clonedDocTypes.clear();
        Iterator docTypes = docTypeExtnMap.keySet().iterator();
        while (docTypes.hasNext()) {
            IDocumentType docType = (IDocumentType) docTypes.next();
            @SuppressWarnings(value = "unchecked")
            Vector<String> types =
                    (Vector<String>) docTypeExtnMap.get(docType).clone();
            clonedDocTypes.put(docType, types);
        }
    }

    /**
     * Copy document extensions from a specified table.
     * <strong>This method assumes that the list of document types in this object
     * is up to date. It means any change in document type list made by user is already
     * updated in the list of document types.</strong>
     * @param newDocTypeExtns A table containing mapping between document types
     *                and document extensions.
     */
    private void updateDocTypeExtns(
            HashMap<IDocumentType, Vector<String>> newDocTypeExtns) {
        // Clear the affected/updated document types list
        clearAffectedDocTypeList();
        // List of document types is considered as base to update data from clone.        
        for (IDocumentType docType : this.documentTypes) {
            Vector<String> prevExtensions = this.docTypeExtnMap.get(docType);
            Vector<String> newExtensions = newDocTypeExtns.get(docType);
            if (isChanged(prevExtensions, newExtensions)) {
                // New document type added
                if (!this.docTypeExtnMap.containsKey(docType) || prevExtensions == null) {
                    prevExtensions = new Vector<String>(newExtensions);
                    this.docTypeExtnMap.put(docType, prevExtensions);
                } else if (!newDocTypeExtns.containsKey(docType)) {
                    // Document type removed
                    this.docTypeExtnMap.remove(docType);
                } else if (newExtensions == null) {
                    this.docTypeExtnMap.put(docType, new Vector<String>());
                } else {
                    // Extensions updated
                    // Clear the original list of extensions.
                    prevExtensions.clear();
                    // Copy new (modified by user) list of extensions.
                    prevExtensions.addAll(newExtensions);
                }
                //Add this document type to affected document type list
                addAffectedDocType(docType);
            }
        }
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null
                && clonedObject instanceof DocumentTypesConfiguration) {
            DocumentTypesConfiguration clonedConfig =
                    (DocumentTypesConfiguration) clonedObject;
            value = !isDocTypesChanged(clonedConfig.getDocumentTypes()) && !isDocTypeExtnChanged(clonedConfig.
                    getDocTypeExtnMap());
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.docTypeExtnMap != null
                ? this.docTypeExtnMap.hashCode()
                : 0);
        return hash;
    }

    public boolean isConfigurable() {
        return true;
    }

    public String getConfigFile() {
        return "DocumentType";
    }

    public boolean isCacheRequired() {
        return true;
    }

    public boolean disposeIfCacheNotRequired() {
        if (this.affectedDocTypes != null) {
            this.affectedDocTypes.clear();
            this.affectedDocTypes = null;
        }
        // Dispose document type list
        if (this.documentTypes != null) {
            this.documentTypes.clear();
            this.documentTypes = null;
        }
        // Dispose document extensions
        if (this.docTypeExtnMap != null) {
            for (Iterator docTypesIterator =
                    this.docTypeExtnMap.keySet().iterator(); docTypesIterator.
                    hasNext();) {
                this.docTypeExtnMap.get((IDocumentType) docTypesIterator.next()).
                        clear();
            }
            this.docTypeExtnMap.clear();
            this.docTypeExtnMap = null;
        }
        return true;
    }

    public boolean isLeaf() {
        return true;
    }

    public void remove() {
        disposeIfCacheNotRequired();
    }

    /**
     * Determines whether or not properties of a document type is changed.
     * @param oldDocType The old document type.
     * @param newDocType The new document type.
     * @return {@code true} if properties of the old document type is changed, otherwise
     * returns {@code false}.
     */
    private boolean isDocumentTypeChanged(IDocumentType oldDocType,
            IDocumentType newDocType) {
        if (!oldDocType.getDefaultExtension().equals(newDocType.
                getDefaultExtension())
                || !oldDocType.getDocumentClass().equals(newDocType.
                getDocumentClass())
                || !oldDocType.getTokens().equals(newDocType.getTokens())) {
            return true;
        }
        return false;
    }

    /**
     * Updates document types to master list.
     * @param newDocTypes New list of document types.
     */
    private void updateDocumentTypes(List<IDocumentType> newDocTypes) {
        // Update the document types in the main list
        for (IDocumentType newDocType : newDocTypes) {
            if (newDocType.isCustomDocumentType()) {
                // Add any new document type to master list
                if (!this.documentTypes.contains(newDocType)) {
                    this.documentTypes.add(newDocType);
                    // Add this document type to affected document type list
                    addAffectedDocType(newDocType);
                } else {
                    for (IDocumentType oldDocType : this.documentTypes) {
                        // Remove any deleted document type from the master list
                        if (!newDocTypes.contains(oldDocType)) {
                            this.documentTypes.remove(oldDocType);
                            addAffectedDocType(oldDocType);
                        }
                        // Handle modified document types.
                        if (newDocType.equals(oldDocType)) {
                            if (isDocumentTypeChanged(oldDocType, newDocType)) {
                                int index = this.documentTypes.indexOf(
                                        oldDocType);
                                this.documentTypes.remove(index);
                                this.documentTypes.add(index, newDocType);
                                addAffectedDocType(newDocType);
                            }
                        }
                    }
                }
            }
        }
    }
}
