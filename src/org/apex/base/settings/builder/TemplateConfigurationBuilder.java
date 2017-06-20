/*
 * TemplateConfigurationBuilder.java 
 * Created on 4 Dec, 2009, 12:53:37 AM
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
package org.apex.base.settings.builder;

import java.util.Properties;
import org.apex.base.data.Configuration;
import org.apex.base.data.ConfigurationBuilderSupport;
import org.apex.base.settings.CodeTemplatesConfiguration;
import org.apex.base.settings.DocumentTemplateConfiguration;
import org.apex.base.settings.TemplateConfiguration;

/**
 * Builds template configuration object from file.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class TemplateConfigurationBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code TemplateConfigurationBuilder}.
     */
    public TemplateConfigurationBuilder() {
    }

    @Override
    public Configuration build(Properties properties) {
        TemplateConfiguration templateConfig =
                new TemplateConfiguration();
        // Build document template section
        DocumentTemplateBuilder docTemplateBuilder =
                new DocumentTemplateBuilder();
        DocumentTemplateConfiguration docTemplateConfig =
                (DocumentTemplateConfiguration) docTemplateBuilder.build(
                (Properties) null);
        templateConfig.setDocTemplateConfig(docTemplateConfig);

        CodeTemplatesBuilder codeTemplatesBuilder =
                new CodeTemplatesBuilder();
        CodeTemplatesConfiguration codeTemplatesConfig =
                (CodeTemplatesConfiguration) codeTemplatesBuilder.build(
                (Properties) null);
        templateConfig.setCodeTemplate(codeTemplatesConfig);

        return templateConfig;
    }
}

