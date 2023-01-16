/*
 * SyntaxStyleConfiguration.java
 * Created on 14 July, 2007, 1:27 PM
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
import java.util.Vector;
import org.apex.base.data.IDocumentType;

/**
 * A configuration object for syntax styles.
 * <p>
 * Syntax styles are captured for pre-defined syntax tokens for each document type.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SyntaxStyleConfiguration extends AbstractConfiguration {

    /**
     * A mapping between document types and list of syntax styles. The mapping is maintained
     * in a table where key is document type and value is list of {@code Style}s.
     */
    private HashMap<IDocumentType, Vector<Style>> docSyntaxStyleMap;
    /**
     * Capture a list of modified/affected document types. This
     * list must be cleared every time user chooses to update/view
     * settings page.
     */
    private Vector<IDocumentType> affectedDocTypes =
            new Vector<IDocumentType>();

    /**
     * Creates a new instance of {@code SyntaxStyleConfiguration}.
     */
    public SyntaxStyleConfiguration() {
    }

    /**
     * Add a document type to affected/updated document type list.
     * @param docType A document type.
     */
    public void addAffectedDocType(IDocumentType docType) {
        this.affectedDocTypes.add(docType);
    }

    /**
     * Clears the list of affected/ updated document type list.
     */
    public void clearAffectedDocTypeList() {
        if (this.affectedDocTypes != null) {
            this.affectedDocTypes.clear();
        }
    }

    /**
     * Checks whether a given document type is affected due to syntax style changes
     * by user for various document types.
     * @param docType A document type.
     * @return {@code true} if given document type is affected due to syntax style changes
     *               by user for various document types; otherwise returns {@code false}.
     */
    public boolean isDocTypeAffected(IDocumentType docType) {
        return this.affectedDocTypes.contains(docType);
    }

    /**
     * Returns the table containing syntax styles for different document types.
     * @return The table containing syntax styles for different document types.
     * @see #setSyntaxStyleMap(java.util.HashMap) 
     */
    public HashMap<IDocumentType, Vector<Style>> getSyntaxStyleMap() {
        return docSyntaxStyleMap;
    }

    /**
     * Sets the table containing syntax styles for different document types.
     * @param docSyntaxStyleMap A table containing syntax styles for different document types.
     * @see #getSyntaxStyleMap() 
     */
    public void setSyntaxStyleMap(
            HashMap<IDocumentType, Vector<Style>> docSyntaxStyleMap) {
        this.docSyntaxStyleMap = docSyntaxStyleMap;
    }

    /**
     * Returns the list of syntax styles for a given document type.
     * @param docType A document type.
     * @return A list of syntax styles. It returns an empty list of {@code Style}s if
     *               style information of a document type can not be retrieved.
     */
    public Vector<Style> getStylesForDocumentType(IDocumentType docType) {
        if (docSyntaxStyleMap.containsKey(docType)) {
            return docSyntaxStyleMap.get(docType);
        } else {
            return new Vector<Style>();
        }
    }

    /**
     * Returns a {@code Style} for a document type and a syntax category.
     * @param docType A document type.
     * @param category A syntax category.
     * @return A syntax style. It returns an empty {@code Style} if
     *               style information of a document type and synatx category can not be retrieved.
     */
    public Style getStyle(IDocumentType docType, String category) {
        Vector<Style> styles = getStylesForDocumentType(docType);
        for (Style style : styles) {
            if (style.getSyntaxName().equalsIgnoreCase(category)) {
                return style;
            }
        }
        return new Style(category);
    }

    /**
     * Returns a {@code Style} for a document type and given index of syntax category
     * in the list.
     * @param docType A document type.
     * @param categoryIndex Index of syntax category in the list.
     * @return A syntax style. It returns an empty {@code Style} if
     *               style information of a document type and synatx category can not be retrieved.
     */
    public Style getStyle(IDocumentType docType, int categoryIndex) {
        Vector<Style> styles = getStylesForDocumentType(docType);
        return styles.get(categoryIndex);
    }

    @Override
    public String toString() {
        return "docSyntaxStyleMap: " + docSyntaxStyleMap;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Object clone() {
        SyntaxStyleConfiguration clonedConfig = null;
        try {
            clonedConfig =
                    (SyntaxStyleConfiguration) super.clone();
            // HashMap clone is a shallow clone. Keys and values themselves are not cloned.
            // However Vector cloning is a perfect one. The copy will contain a
            // reference to a clone of the internal data array, not a reference
            // to the original internal data array of the original object
            HashMap<IDocumentType, Vector<Style>> clonedStyles =
                    (HashMap<IDocumentType, Vector<Style>>) (this.docSyntaxStyleMap).clone();
            strictCopyElementsTo(clonedStyles);
            clonedConfig.setSyntaxStyleMap(clonedStyles);
        } catch (CloneNotSupportedException ex) {
        }
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        SyntaxStyleConfiguration clonedConfig =
                (SyntaxStyleConfiguration) clonedObject;
        updateSyntaxStyle(clonedConfig.getSyntaxStyleMap());
        fireSyntaxStyleConfigurationChanged(null);
    }

    /**
     * Determines whether or not syntax styles are changed.
     * It compares syntax styles for all document types against given table of
     * styles for different document types.
     * @param newSyntaxStyle A table of syntax styles.
     * @return {@code true} if syntax styles are changed; otherwise returns {@code false}.
     */
    private boolean isSyntaxStyleChanged(
            HashMap<IDocumentType, Vector<Style>> newSyntaxStyle) {
        if (this.docSyntaxStyleMap.size() != newSyntaxStyle.size()) {
            return true;
        }
        // Both are having equal number of document types and exactly same document types.
        Iterator docsIterator = newSyntaxStyle.keySet().iterator();
        while (docsIterator.hasNext()) {
            IDocumentType docType = (IDocumentType) docsIterator.next();
            Vector<Style> prevStyles = this.docSyntaxStyleMap.get(docType);
            Vector<Style> newStyles = newSyntaxStyle.get(docType);
            if (isStylesChanged(prevStyles, newStyles)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Copies syntax styles for all documet types to a specified table.
     * Before copying, it clears the given table.
     * @param clonedStyles A table of syntax styles.
     */
    @SuppressWarnings(value = "unchecked")
    private void strictCopyElementsTo(
            HashMap<IDocumentType, Vector<Style>> clonedStyles) {
        // Clear the cloned HashMap and then start copying.
        clonedStyles.clear();
        Iterator docTypes = this.docSyntaxStyleMap.keySet().iterator();
        while (docTypes.hasNext()) {
            IDocumentType docType = (IDocumentType) docTypes.next();
            // Styles are not cloned here
            Vector<Style> styles =
                    (Vector<Style>) this.docSyntaxStyleMap.get(docType).clone();
            // Clone styles
            strictCopyElementsTo(docType, styles);
            clonedStyles.put(docType, styles);
        }
    }

    /**
     * Copies syntax style for a given document type to a specified list.
     * @param docType A document type.
     * @param styles A list of syntax styles.
     */
    private void strictCopyElementsTo(IDocumentType docType,
            Vector<Style> styles) {
        Vector<Style> actualStyles = this.docSyntaxStyleMap.get(docType);
        // Clear the target.
        styles.clear();
        for (Style style : actualStyles) {
            styles.add((Style) style.clone());
        }
    }

    /**
     * Copies syntax styles for all document types from a specified table.
     * @param newSyntaxStyle A table of syntax styles.
     */
    private void updateSyntaxStyle(
            HashMap<IDocumentType, Vector<Style>> newSyntaxStyle) {
        // Clear the affected/updated document types list
        clearAffectedDocTypeList();
        // We dont allow to add document type. So document types of original object
        // is considered as base to update data from clone.
        Iterator docsIterator = this.docSyntaxStyleMap.keySet().iterator();
        while (docsIterator.hasNext()) {
            IDocumentType docType = (IDocumentType) docsIterator.next();
            Vector<Style> prevStyles = this.docSyntaxStyleMap.get(docType);
            Vector<Style> newStyles = newSyntaxStyle.get(docType);
            if (isStylesChanged(prevStyles, newStyles)) {
                // Clear the original list of styles.
                prevStyles.clear();
                // Copy new (modified by user) list of styles.
                prevStyles.addAll(newStyles);
                // Add this document type to affected document type list
                if (!isDocTypeAffected(docType)) {
                    addAffectedDocType(docType);
                }
            }
        }
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof SyntaxStyleConfiguration) {
            SyntaxStyleConfiguration clonedConfig =
                    (SyntaxStyleConfiguration) clonedObject;
            value = !isSyntaxStyleChanged(clonedConfig.getSyntaxStyleMap());
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash +
                (this.docSyntaxStyleMap != null
                ? this.docSyntaxStyleMap.hashCode()
                : 0);
        return hash;
    }

    public boolean isConfigurable() {
        return true;
    }

    public String getConfigFile() {
        return "SyntaxStyle";
    }

    public boolean isCacheRequired() {
        return false;
    }

    public boolean disposeIfCacheNotRequired() {
        if (this.affectedDocTypes != null) {
            this.affectedDocTypes.clear();
            this.affectedDocTypes = null;
        }
        if (this.docSyntaxStyleMap != null) {
            for (Iterator docTypesIterator = this.docSyntaxStyleMap.keySet().
                    iterator(); docTypesIterator.hasNext();) {
                this.docSyntaxStyleMap.get(docTypesIterator.next()).clear();
            }
            this.docSyntaxStyleMap.clear();
            this.docSyntaxStyleMap = null;
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