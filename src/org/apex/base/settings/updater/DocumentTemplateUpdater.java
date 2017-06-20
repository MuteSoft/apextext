/* 
 * DocumentTemplateUpdater.java
 * Created on 20 Nov, 2007,12:46:00 AM
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
package org.apex.base.settings.updater;

import org.apex.base.data.ConfigurationUpdateSupport;
import org.apex.base.settings.DocumentTemplateConfiguration;
import java.util.Properties;
import org.apex.base.core.EditorBase;
import org.apex.base.data.IDocumentType;

/**
 * Stores document template configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentTemplateUpdater extends ConfigurationUpdateSupport {

    /**
     * Document template configuration.
     */
    private DocumentTemplateConfiguration docTemplateConfig;
    /**
     * Cloned document template configuration.
     */
    private DocumentTemplateConfiguration clonedDocTemplateConfig;

    /**
     * Creates a new instance of {@code DocumentTemplateUpdater} using specified
     * document template configuration and cloned document template configuration.
     * @param docTemplateConfig Document template configuration.
     * @param clonedDocTemplateConfig Cloned document template configuration.
     */
    public DocumentTemplateUpdater(
            DocumentTemplateConfiguration docTemplateConfig,
            DocumentTemplateConfiguration clonedDocTemplateConfig) {
        this.docTemplateConfig = docTemplateConfig;
        this.clonedDocTemplateConfig = clonedDocTemplateConfig;
    }

    @Override
    public void update(Properties properties) {
        this.docTemplateConfig.updateFromClone(this.clonedDocTemplateConfig);
        if (properties == null) {
            properties = loadConfigProperties(this.docTemplateConfig);
        }
        for (IDocumentType docType : EditorBase.getContext().getEditorProperties().getDocumentTypeBase().
                getDocumentTypesIncludingDefault()) {
            String template =
                    this.docTemplateConfig.getDocTemplates().get(docType);
            if (template == null) {
                template = BLANK;
            }
            properties.setProperty(docType.getDisplayName() + DOT + TEMPLATE, template);
        }
        storeConfigProperties(this.docTemplateConfig, properties);
    }
}
