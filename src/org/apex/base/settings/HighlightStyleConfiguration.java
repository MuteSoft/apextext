/*
 * HighlightStyleConfiguration.java
 * Created on 14 July, 2007, 1:33 PM
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

/**
 * A configuration object for highlight styles.
 * <p>
 * Highlight category like 'Search' can have a hifglight color which will be used to
 * highlight search results.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class HighlightStyleConfiguration extends AbstractConfiguration {

    /**
     * A map containing highlight colors for different highlight categories.
     * In map key is highlight category and value is {@code HighlightColor}.
     */
    private HashMap<String, HighlightColor> highlightMap;

    /**
     * Creates a new instance of {@code HighlightStyleConfiguration}.
     */
    public HighlightStyleConfiguration() {
    }

    /**
     * Returns the table containing highlight colors for different highlight categories.
     * @return The table containing highlight colors for different highlight categories.
     * @see #setHighlightMap(java.util.HashMap) 
     */
    public HashMap<String, HighlightColor> getHighlightMap() {
        return highlightMap;
    }

    /**
     * Sets the table containing highlight colors for different highlight categories.
     * @param highlightMap A table containing highlight colors for different highlight categories.
     * @see #getHighlightMap() 
     */
    public void setHighlightMap(HashMap<String, HighlightColor> highlightMap) {
        this.highlightMap = highlightMap;
    }

    /**
     * Returns the highlight color for a given highlight category.
     * @param category A highlight category.
     * @return A highlight color. It returns {@code null} if given
     *               highlight category does not exist.
     */
    public HighlightColor getHighlightColor(String category) {
        return category == null
                ? null
                : highlightMap.get(category) == null
                ? new HighlightColor()
                : highlightMap.get(category);
    }

    @Override
    public String toString() {
        return "highlightMap: " + highlightMap;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Object clone() {
        HighlightStyleConfiguration clonedConfig = null;
        try {
            clonedConfig =
                    (HighlightStyleConfiguration) super.clone();
            // HashMap clone is a shallow clone. Keys and values themselves are not cloned.
            // However Vector cloning is a perfect one. The copy will contain a
            // reference to a clone of the internal data array, not a reference
            // to the original internal data array of the original object
            HashMap<String, HighlightColor> clonedHighlights =
                    (HashMap<String, HighlightColor>) this.highlightMap.clone();
            strictCopyElementsTo(clonedHighlights);
            clonedConfig.setHighlightMap(clonedHighlights);
        } catch (CloneNotSupportedException ex) {
        }
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        HighlightStyleConfiguration clonedConfig =
                (HighlightStyleConfiguration) clonedObject;
        updateHighlightColor(clonedConfig.getHighlightMap());
        fireHighlightStyleConfigurationChanged(null);
    }

    /**
     * Determines whether or not highlight colors are changed.
     * It compares highlight colors for all categories against given table of
     * highlight colors for different highlight categories.
     * @param newHighlightColors A table of highlight colors.
     * @return {@code true} if custom tools are changed; otherwise returns {@code false}.
     */
    private boolean isHighlightColorChanged(
            HashMap<String, HighlightColor> newHighlightColors) {
        if (this.highlightMap.size() != newHighlightColors.size()) {
            return true;
        }
        // Number of templates is same and also we are sure that abbreviation cannot be changed
        // for existing ones.
        Iterator categoryIterator = newHighlightColors.keySet().iterator();
        while (categoryIterator.hasNext()) {
            // Get category.
            String category = (String) categoryIterator.next();
            HighlightColor prevHighlight = this.highlightMap.get(category);
            HighlightColor newHighlight = newHighlightColors.get(category);
            if (isHighlightColorChanged(prevHighlight, newHighlight)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Copy highlight colors for all categories to a specified table.
     * Before copying, it clears the given table.
     * @param clonedHighlights A table of highlight colors.
     */
    private void strictCopyElementsTo(
            HashMap<String, HighlightColor> clonedHighlights) {
        // Clear the cloned HashMap and then start copying.
        clonedHighlights.clear();
        Iterator categories = this.highlightMap.keySet().iterator();
        while (categories.hasNext()) {
            String category = (String) categories.next();
            // Clone Highlight
            HighlightColor clonedHighlightColor =
                    (HighlightColor) this.highlightMap.get(category).clone();
            clonedHighlights.put(category, clonedHighlightColor);
        }
    }

    /**
     * Copy highlight colors for all categories from a specified table.
     * @param newHighlightColors A table of highlight colors.
     */
    private void updateHighlightColor(
            HashMap<String, HighlightColor> newHighlightColors) {
        // Since any number of new templates might have been added by user, so consider the new
        // code templates as base and update the old one. Update all pairs.
        Iterator categoryIterator = newHighlightColors.keySet().iterator();
        while (categoryIterator.hasNext()) {
            // Get category.
            String category = (String) categoryIterator.next();
            HighlightColor newHighlight = newHighlightColors.get(category);
            HighlightColor prevHighlight = this.highlightMap.get(category);
            // Category can not be added by user.
            if (isHighlightColorChanged(prevHighlight, newHighlight)) {
                this.highlightMap.put(category,
                        (HighlightColor) newHighlight.clone());
            }
        }
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof HighlightStyleConfiguration) {
            HighlightStyleConfiguration clonedConfig =
                    (HighlightStyleConfiguration) clonedObject;
            value = !isHighlightColorChanged(clonedConfig.getHighlightMap());
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash +
                (this.highlightMap != null
                ? this.highlightMap.hashCode()
                : 0);
        return hash;
    }

    public boolean isConfigurable() {
        return true;
    }

    public String getConfigFile() {
        return "HighlightStyle";
    }

    public boolean isCacheRequired() {
        return false;
    }

    public boolean disposeIfCacheNotRequired() {
        if (this.highlightMap != null) {
            for (Iterator highlightIterator = this.highlightMap.keySet().
                    iterator(); highlightIterator.hasNext();) {
                Object key = highlightIterator.next();
                if (highlightMap.get(key) != null) {
                    highlightMap.get(key).dispose();
                }
            }
            this.highlightMap.clear();
            this.highlightMap = null;
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