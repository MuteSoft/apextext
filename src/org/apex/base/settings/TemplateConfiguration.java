/*
 * TemplateConfiguration.java 
 * Created on 4 Dec, 2009, 12:37:12 AM
 *
 * Copyright (C) 2009 Mrityunjoy Saha
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

import org.apex.base.settings.builder.CodeTemplatesBuilder;
import org.apex.base.settings.builder.DocumentTemplateBuilder;

/**
 * Template configuration base class.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class TemplateConfiguration extends AbstractConfiguration {

    /**
     * Code templates configuration.
     */
    private CodeTemplatesConfiguration codeTemplate;
    /**
     * Document templates configuration.
     */
    private DocumentTemplateConfiguration docTemplateConfig;

    /**
     * Creates a new instance of {@code TemplateConfiguration}.
     */
    public TemplateConfiguration() {
    }

    /**
     * Returns code templates configuration.
     * @return Code templates configuration.
     * @see #setCodeTemplate(org.apex.base.settings.CodeTemplatesConfiguration)
     */
    public CodeTemplatesConfiguration getCodeTemplate() {
        if (this.codeTemplate == null) {
            this.codeTemplate =
                    (CodeTemplatesConfiguration) new CodeTemplatesBuilder().build();
        }
        return codeTemplate;
    }

    /**
     * Sets code templates configuration.
     * @param codeTemplate Code templates configuration.
     * @see #getCodeTemplate()
     */
    public void setCodeTemplate(CodeTemplatesConfiguration codeTemplate) {
        this.codeTemplate = codeTemplate;
    }

    /**
     * Returns document templates configuration.
     * @return Document templates configuration.
     * @see #setDocTemplateConfig(org.apex.base.settings.DocumentTemplateConfiguration)
     */
    public DocumentTemplateConfiguration getDocTemplateConfig() {
        if (this.docTemplateConfig == null) {
            this.docTemplateConfig =
                    (DocumentTemplateConfiguration) new DocumentTemplateBuilder().build();
        }
        return docTemplateConfig;
    }

    /**
     * Sets document templates configuration.
     * @param docTemplateConfig Document templates configuration.
     * @see #getDocTemplateConfig()
     */
    public void setDocTemplateConfig(
            DocumentTemplateConfiguration docTemplateConfig) {
        this.docTemplateConfig = docTemplateConfig;
    }

    @Override
    public String toString() {
        return "DocumentTemplate: " + docTemplateConfig +
                "^CodeTemplatesConfiguration: " + codeTemplate;
    }

    @Override
    public Object clone() {
        TemplateConfiguration clonedConfig = null;
        if (this.codeTemplate == null) {
            this.codeTemplate = getCodeTemplate();
        }
        if (this.docTemplateConfig == null) {
            this.docTemplateConfig = getDocTemplateConfig();
        }
        try {
            clonedConfig =
                    (TemplateConfiguration) super.clone();
            clonedConfig.setCodeTemplate((CodeTemplatesConfiguration) this.codeTemplate.clone());
            clonedConfig.setDocTemplateConfig((DocumentTemplateConfiguration) this.docTemplateConfig.
                    clone());
        } catch (CloneNotSupportedException ex) {
        }
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        TemplateConfiguration clonedConfig =
                (TemplateConfiguration) clonedObject;
        this.getDocTemplateConfig().updateFromClone(clonedConfig.getDocTemplateConfig());
        this.getCodeTemplate().updateFromClone(clonedConfig.getCodeTemplate());
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof GeneralConfiguration) {
            TemplateConfiguration clonedConfig =
                    (TemplateConfiguration) clonedObject;
            value = (this.docTemplateConfig == null
                    ? clonedConfig.getDocTemplateConfig() == null
                    : this.docTemplateConfig.equals(clonedConfig.getDocTemplateConfig())) &&
                    (this.codeTemplate == null
                    ? clonedConfig.getCodeTemplate() == null
                    : this.codeTemplate.equals(clonedConfig.getCodeTemplate()));
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.docTemplateConfig != null
                ? this.docTemplateConfig.hashCode()
                : 0);
        hash = 29 * hash +
                (this.codeTemplate != null
                ? this.codeTemplate.hashCode()
                : 0);
        return hash;
    }

    public boolean isConfigurable() {
        return (this.docTemplateConfig == null
                ? new GeneralSectionConfiguration().isConfigurable()
                : this.docTemplateConfig.isConfigurable()) ||
                (this.codeTemplate == null
                ? new CodeTemplatesConfiguration().isConfigurable()
                : this.codeTemplate.isConfigurable());
    }

    public String getConfigFile() {
        return "General";
    }

    public boolean isCacheRequired() {
        return (this.docTemplateConfig == null
                ? new GeneralSectionConfiguration().isCacheRequired()
                : this.docTemplateConfig.isCacheRequired()) ||
                (this.codeTemplate == null
                ? new CodeTemplatesConfiguration().isCacheRequired()
                : this.codeTemplate.isCacheRequired());
    }

    public boolean disposeIfCacheNotRequired() {
        boolean docTemplatesDisposed = false;
        boolean codeTemplatesDisposed = false;
        // Dispose contained config objects.
        if (this.docTemplateConfig != null && (!this.docTemplateConfig.isLeaf() ||
                !this.docTemplateConfig.isCacheRequired())) {
            docTemplatesDisposed = this.docTemplateConfig.disposeIfCacheNotRequired();
            if (docTemplatesDisposed) {
                this.docTemplateConfig = null;
            }
        }
        if (this.codeTemplate != null && (!this.codeTemplate.isLeaf() ||
                !this.codeTemplate.isCacheRequired())) {
            codeTemplatesDisposed =
                    this.codeTemplate.disposeIfCacheNotRequired();
            if (codeTemplatesDisposed) {
                this.codeTemplate = null;
            }
        }
        return docTemplatesDisposed && codeTemplatesDisposed;
    }

    public boolean isLeaf() {
        return false;
    }

    public void remove() {
        if (this.docTemplateConfig != null) {
            this.docTemplateConfig.remove();
            this.docTemplateConfig = null;
        }
        if (this.codeTemplate != null) {
            this.codeTemplate.remove();
            this.codeTemplate = null;
        }
    }
}
