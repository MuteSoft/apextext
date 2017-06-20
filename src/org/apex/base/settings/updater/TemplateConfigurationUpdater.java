/*
 * TemplateConfigurationUpdater.java 
 * Created on 4 Dec, 2009, 12:53:57 AM
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
package org.apex.base.settings.updater;

import java.util.Properties;
import org.apex.base.data.ConfigurationUpdateSupport;
import org.apex.base.settings.TemplateConfiguration;

/**
 * Stores template configuration to external file.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class TemplateConfigurationUpdater extends ConfigurationUpdateSupport {

    /**
     * General configuration.
     */
    private TemplateConfiguration templateConfig;
    /**
     * Cloned general configuration.
     */
    private TemplateConfiguration clonedTemplateConfig;

    /**
     * Creates a new instance of {@code TemplateConfigurationUpdater} using specified
     * template configuration and cloned template configuration.
     * @param templateConfig Template configuration.
     * @param clonedTemplateConfig Cloned template configuration.
     */
    public TemplateConfigurationUpdater(TemplateConfiguration templateConfig,
            TemplateConfiguration clonedTemplateConfig) {
        this.templateConfig = templateConfig;
        this.clonedTemplateConfig = clonedTemplateConfig;
    }

    @Override
    public void update(Properties properties) {
        if (!this.templateConfig.getDocTemplateConfig().equals(this.clonedTemplateConfig.
                getDocTemplateConfig())) {
            // Update document template section
            DocumentTemplateUpdater docTemplateUpdater =
                    new DocumentTemplateUpdater(this.templateConfig.getDocTemplateConfig(),
                    this.clonedTemplateConfig.getDocTemplateConfig());
            docTemplateUpdater.update((Properties) null);
        }
        if (!this.templateConfig.getCodeTemplate().equals(
                this.clonedTemplateConfig.getCodeTemplate())) {
            CodeTemplatesUpdater codeTemplatesUpdater =
                    new CodeTemplatesUpdater(this.templateConfig.getCodeTemplate(),
                    this.clonedTemplateConfig.getCodeTemplate());
            codeTemplatesUpdater.update((Properties) null);
        }
    }
}

