/*
 * GeneralConfigurationBuilder.java
 * Created on 15 July, 2007, 1:37 AM
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
import org.apex.base.settings.CodeTemplatesConfiguration;
import org.apex.base.data.Configuration;
import org.apex.base.settings.DocumentTypesConfiguration;
import org.apex.base.settings.GeneralConfiguration;
import org.apex.base.settings.GeneralSectionConfiguration;
import java.util.Properties;

/**
 * Builds general configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GeneralConfigurationBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code GeneralConfigurationBuilder}.
     */
    public GeneralConfigurationBuilder() {
    }

    @Override
    public Configuration build(Properties properties) {
        GeneralConfiguration generalConfig =
                new GeneralConfiguration();
        GeneralSectionBuilder generalSecBuilder =
                new GeneralSectionBuilder();
        GeneralSectionConfiguration generalSectionConfig =
                (GeneralSectionConfiguration) generalSecBuilder.build(
                (Properties) null);
        generalConfig.setGeneral(generalSectionConfig);

        DocumentTypesBuilder docTypesBuilder =
                new DocumentTypesBuilder();
        DocumentTypesConfiguration docTypesConfig =
                (DocumentTypesConfiguration) docTypesBuilder.build(
                (Properties) null);
        generalConfig.setDocTypes(docTypesConfig);

        return generalConfig;
    }
}
