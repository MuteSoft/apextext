/*
 * CodeTemplate.java
 * Created on 14 July, 2007, 1:20 PM
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

import java.util.Iterator;
import java.util.TreeMap;

/**
 * A configuration object for code templates.
 * <p>
 * After entering an abbreviation the corresponding code templates can be expanded by pressing
 * a specified key in the edit area.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CodeTemplatesConfiguration extends AbstractConfiguration {

    /**
     * Code Templates.
     * <p>
     * Key is abbreviation and value is template.
     */
    private TreeMap<String, String> codeTemplates;

    /** 
     * Creates a new instance of CodeTemplate.
     */
    public CodeTemplatesConfiguration() {
    }

    /**
     * Returns code templates.
     * <p>
     * Key is abbreviation and value is template.
     * @return Code templates.
     * @see #setCodeTemplates(java.util.TreeMap) 
     */
    public TreeMap<String, String> getCodeTemplates() {
        return codeTemplates;
    }

    /**
     * Sets code templates.
     * <p>
     * Key is abbreviation and value is template.
     * @param codeTemplates Code templates.
     * @see #getCodeTemplates() 
     */
    public void setCodeTemplates(TreeMap<String, String> codeTemplates) {
        this.codeTemplates = codeTemplates;
    }

    /**
     * Determines the code template for a given abbreviation.
     * @param abbreviation The abbreviation.
     * @return The code template if found; otherwise returns {@code null}.
     */
    public String getTemplate(String abbreviation) {
        if (this.codeTemplates == null) {
            return null;
        }
        return this.codeTemplates.get(abbreviation);
    }

    @Override
    public String toString() {
        return "codeTemplates: " + codeTemplates;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Object clone() {
        CodeTemplatesConfiguration clonedConfig = null;
        try {
            clonedConfig =
                    (CodeTemplatesConfiguration) super.clone();
            // It does a shallow copy. Keys and values themselves are not cloned.
            TreeMap<String, String> clonedCodeTemplates =
                    (TreeMap<String, String>) (this.codeTemplates).clone();
            // Commenting below line because TreeMap contains String only. So shallow copy
            // is not a problem in this case.
            //strictCopyElementsTo(clonedCodeTemplates);
            clonedConfig.setCodeTemplates(clonedCodeTemplates);
        } catch (CloneNotSupportedException ex) {
        }
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        CodeTemplatesConfiguration clonedConfig =
                (CodeTemplatesConfiguration) clonedObject;
        updateCodeTemplates(clonedConfig.getCodeTemplates());

    }

    /**
     * Determines whether or not code templates are changed by comparing with given code
     * templates.
     * @param newCodeTemplates New code templates.
     * @return {@code true} if changed; otherwise returns {@code false}.
     */
    private boolean isCodeTemplatesChanged(
            TreeMap<String, String> newCodeTemplates) {
        if (this.codeTemplates.size() != newCodeTemplates.size()) {
            return true;
        }
        // Number of templates is same and also we are sure that abbreviation cannot be changed
        // for existing ones.
        Iterator abbrIterator = newCodeTemplates.keySet().iterator();
        while (abbrIterator.hasNext()) {
            // Get abbreviation.
            String abbr = (String) abbrIterator.next();
            String prevTemplate = this.codeTemplates.get(abbr);
            String newTemplate = newCodeTemplates.get(abbr);
            if (isChanged(prevTemplate, newTemplate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates code templates of this configuration object from given code templates.
     * @param newCodeTemplates New code templates.
     */
    private void updateCodeTemplates(TreeMap<String, String> newCodeTemplates) {
        // Since any number of new templates might have been added by user, so consider the new
        // code templates as base and update the old one. Update all pairs.
        Iterator abbrIterator = newCodeTemplates.keySet().iterator();
        this.codeTemplates.clear();
        while (abbrIterator.hasNext()) {
            // Get abbreviation.
            String abbr = (String) abbrIterator.next();
            String newTemplate = newCodeTemplates.get(abbr);
            if (!this.codeTemplates.containsKey(abbr)) {
                this.codeTemplates.put(abbr, newTemplate);
            } else {
                String prevTemplate = this.codeTemplates.get(abbr);
                if (isChanged(prevTemplate, newTemplate)) {
                    this.codeTemplates.put(abbr, newTemplate);
                }
            }
        }
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof CodeTemplatesConfiguration) {
            CodeTemplatesConfiguration clonedConfig =
                    (CodeTemplatesConfiguration) clonedObject;
            value = !isCodeTemplatesChanged(clonedConfig.getCodeTemplates());
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash +
                (this.codeTemplates != null
                ? this.codeTemplates.hashCode()
                : 0);
        return hash;
    }

    public boolean isConfigurable() {
        return true;
    }

    public String getConfigFile() {
        return "CodeTemplate";
    }

    public boolean isCacheRequired() {
        return true;
    }

    public boolean disposeIfCacheNotRequired() {
        if (this.codeTemplates != null) {
            this.codeTemplates.clear();
            this.codeTemplates = null;
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
