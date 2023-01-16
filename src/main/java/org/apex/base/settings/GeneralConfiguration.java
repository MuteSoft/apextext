/*
 * GeneralConfiguration.java
 * Created on 14 July, 2007, 12:46 PM
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

import org.apex.base.settings.builder.DocumentTypesBuilder;
import org.apex.base.settings.builder.GeneralSectionBuilder;

/**
 * The general configuration object.
 * <p>
 * It holds general section configuration, document types configuration, code templates
 * configuration etc.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GeneralConfiguration extends AbstractConfiguration {

    /**
     * General section configuration.
     */
    private GeneralSectionConfiguration general;
    /**
     * Document types configuration.
     */
    private DocumentTypesConfiguration docTypes;

    /**
     * Creates a new instance of {@code GeneralConfiguration}.
     */
    public GeneralConfiguration() {
    }

    /**
     * Returns general section configuration.
     * @return General section configuration.
     * @see #setGeneral(org.apex.base.settings.GeneralSectionConfiguration)
     */
    public GeneralSectionConfiguration getGeneral() {
        if (this.general == null) {
            this.general =
                    (GeneralSectionConfiguration) new GeneralSectionBuilder().build();
        }
        return general;
    }

    /**
     * Sets general section configuration.
     * @param general General section configuration.
     * @see #getGeneral() 
     */
    public void setGeneral(GeneralSectionConfiguration general) {
        this.general = general;
    }

    /**
     * Returns document types configuration.
     * @return Document types configuration.
     * @see #setDocTypes(org.apex.base.settings.DocumentTypesConfiguration)
     */
    public DocumentTypesConfiguration getDocTypes() {
        if (this.docTypes == null) {
            this.docTypes =
                    (DocumentTypesConfiguration) new DocumentTypesBuilder().build();
        }
        return docTypes;
    }

    /**
     * Sets document types configuration.
     * @param docTypes Document types configuration.
     * @see #getDocTypes() 
     */
    public void setDocTypes(DocumentTypesConfiguration docTypes) {
        this.docTypes = docTypes;
    }

    @Override
    public String toString() {
        return "GeneralSectionConfiguration: " + general +
                "^DocumentTypesConfiguration: " + docTypes;
    }

    @Override
    public Object clone() {
        GeneralConfiguration clonedConfig = null;
        if (this.docTypes == null) {
            this.docTypes = getDocTypes();
        }
        if (this.general == null) {
            this.general = getGeneral();
        }
        try {
            clonedConfig =
                    (GeneralConfiguration) super.clone();
            clonedConfig.setDocTypes((DocumentTypesConfiguration) this.docTypes.clone());
            clonedConfig.setGeneral((GeneralSectionConfiguration) this.general.clone());
        } catch (CloneNotSupportedException ex) {
        }
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        GeneralConfiguration clonedConfig =
                (GeneralConfiguration) clonedObject;
        this.getGeneral().updateFromClone(clonedConfig.getGeneral());
        this.getDocTypes().updateFromClone(clonedConfig.getDocTypes());
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof GeneralConfiguration) {
            GeneralConfiguration clonedConfig =
                    (GeneralConfiguration) clonedObject;
            value = (this.general == null
                    ? clonedConfig.getGeneral() == null
                    : this.general.equals(clonedConfig.getGeneral())) &&
                    (this.docTypes == null
                    ? clonedConfig.getDocTypes() == null
                    : this.docTypes.equals(clonedConfig.getDocTypes()));
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.general != null
                ? this.general.hashCode()
                : 0);
        hash = 29 * hash +
                (this.docTypes != null
                ? this.docTypes.hashCode()
                : 0);
        return hash;
    }

    public boolean isConfigurable() {
        return (this.general == null
                ? new GeneralSectionConfiguration().isConfigurable()
                : this.general.isConfigurable()) ||
                (this.docTypes == null
                ? new DocumentTypesConfiguration().isConfigurable()
                : this.docTypes.isConfigurable());
    }

    public String getConfigFile() {
        return "General";
    }

    public boolean isCacheRequired() {
        return (this.general == null
                ? new GeneralSectionConfiguration().isCacheRequired()
                : this.general.isCacheRequired()) ||
                (this.docTypes == null
                ? new DocumentTypesConfiguration().isCacheRequired()
                : this.docTypes.isCacheRequired());
    }

    public boolean disposeIfCacheNotRequired() {
        boolean generalSecDisposed = false;
        boolean docTypesDisposed = false;
        // Dispose contained config objects.
        if (this.general != null && (!this.general.isLeaf() ||
                !this.general.isCacheRequired())) {
            generalSecDisposed = this.general.disposeIfCacheNotRequired();
            if (generalSecDisposed) {
                this.general = null;
            }
        }
        if (this.docTypes != null && (!this.docTypes.isLeaf() ||
                !this.docTypes.isCacheRequired())) {
            docTypesDisposed = this.docTypes.disposeIfCacheNotRequired();
            if (docTypesDisposed) {
                this.docTypes = null;
            }
        }
        return generalSecDisposed && docTypesDisposed;
    }

    public boolean isLeaf() {
        return false;
    }

    public void remove() {
        if (this.general != null) {
            this.general.remove();
            this.general = null;
        }
        if (this.docTypes != null) {
            this.docTypes.remove();
            this.docTypes = null;
        }
    }
}