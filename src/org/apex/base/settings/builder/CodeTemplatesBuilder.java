/*
 * CodeTemplatesBuilder.java
 * Created on 15 July, 2007, 1:39 AM
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
import org.apex.base.logging.Logger;
import org.apex.base.settings.CodeTemplatesConfiguration;
import org.apex.base.data.Configuration;
import org.apex.base.util.ConfigurationUtility;
import java.util.Properties;
import java.util.TreeMap;
import org.apex.base.util.StringUtil;

/**
 * Builds code templates configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CodeTemplatesBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code CodeTemplatesBuilder}.
     */
    public CodeTemplatesBuilder() {
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading code templates configuration.");
        CodeTemplatesConfiguration codeTemplatesConfig =
                new CodeTemplatesConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(codeTemplatesConfig);
        }
        codeTemplatesConfig.setCodeTemplates(getCodeTemplates(properties));
        return codeTemplatesConfig;
    }

    /**
     * Builds and returns a map of code templates from given properties.
     * @param properties A table of key value pairs.
     * @return A table containing code templates.
     */
    private TreeMap<String, String> getCodeTemplates(Properties properties) {
        TreeMap<String, String> result =
                new TreeMap<String, String>();
        String codeTemplates = properties.getProperty(CODE_TEMPLATES);
        if (codeTemplates == null) {
            // Intend to return blank sorted map
            return result;
        }
        for (String codeTemplate : ConfigurationUtility.getListFromString(
                codeTemplates,
                INTRA_SEPARATOR)) {
            if (StringUtil.isNullOrEmpty(codeTemplate) || StringUtil.
                    isNullOrEmpty(properties.getProperty(codeTemplate))) {
                continue;
            }
            result.put(codeTemplate, properties.getProperty(codeTemplate));
        }
        return result;
    }
}
