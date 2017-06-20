/* 
 * DocumentTemplateBuilder.java
 * Created on 20 Nov, 2007,12:33:00 AM
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
package org.apex.base.settings.builder;

import org.apex.base.data.ConfigurationBuilderSupport;
import org.apex.base.constant.CommonConstants;
import org.apex.base.logging.Logger;
import org.apex.base.data.Configuration;
import org.apex.base.settings.DocumentTemplateConfiguration;
import java.util.HashMap;
import java.util.Properties;
import org.apex.base.core.EditorBase;
import org.apex.base.data.IDocumentType;

/**
 * Builds document template configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentTemplateBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code DocumentTemplateBuilder}.
     */
    public DocumentTemplateBuilder() {
        super();
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading document templates configuration.");
        DocumentTemplateConfiguration docTemplateConfig =
                new DocumentTemplateConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(docTemplateConfig);
        }
        docTemplateConfig.setDocTemplates(getDocTemplates(properties));
        return docTemplateConfig;
    }

    /**
     * Builds and returns a table containing document template for different document types.
     * @param properties A table of key value pairs.
     * @return A table containing document template for different document types.
     */
    private HashMap<IDocumentType, String> getDocTemplates(Properties properties) {
        HashMap<IDocumentType, String> docTemplates =
                new HashMap<IDocumentType, String>();
        for (IDocumentType docType : EditorBase.getContext().getEditorProperties().
                getDocumentTypeBase().
                getDocumentTypesIncludingDefault()) {
            String template = properties.getProperty(docType.getDisplayName() + DOT +
                    TEMPLATE,
                    CommonConstants.BLANK_TEXT);
            docTemplates.put(docType, template);
        }
        return docTemplates;
    }
}
