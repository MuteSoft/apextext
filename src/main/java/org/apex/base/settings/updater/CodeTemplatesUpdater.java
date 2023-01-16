/*
 * CodeTemplatesUpdater.java
 * Created on 27 Oct, 2007, 8:29:38 PM
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
import org.apex.base.settings.CodeTemplatesConfiguration;
import org.apex.base.util.StringUtil;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeMap;

/**
 * Stores code templates configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CodeTemplatesUpdater extends ConfigurationUpdateSupport {

    /**
     * Code templates configuration.
     */
    private CodeTemplatesConfiguration codeTemplatesConfig;
    /**
     * Cloned code templates configuration.
     */
    private CodeTemplatesConfiguration clonedCodeTemplatesConfig;

    /**
     * Creates a new instance of {@code CodeTemplatesUpdater} using specified
     * code templates configuration and cloned code templates configuration.
     * @param codeTemplatesConfig Code templates configuration.
     * @param clonedCodeTemplatesConfig Cloned code templates configuration.
     */
    public CodeTemplatesUpdater(CodeTemplatesConfiguration codeTemplatesConfig,
                                CodeTemplatesConfiguration clonedCodeTemplatesConfig) {
        this.codeTemplatesConfig = codeTemplatesConfig;
        this.clonedCodeTemplatesConfig = clonedCodeTemplatesConfig;
    }

    @Override
    public void update(Properties properties) {
        this.codeTemplatesConfig.updateFromClone(this.clonedCodeTemplatesConfig);
        if (properties == null) {
            properties = loadConfigProperties(this.codeTemplatesConfig);
        }
        StringBuilder templates = new StringBuilder("");
        TreeMap<String, String> codeTemplates =
                codeTemplatesConfig.getCodeTemplates();
        for (Iterator codeTemplatesIterator = codeTemplates.keySet().iterator();
                codeTemplatesIterator.hasNext();) {
            String abbreviation = (String) codeTemplatesIterator.next();
            String template = codeTemplates.get(abbreviation);
            if (!StringUtil.isNullOrEmpty(abbreviation) &&
                    !StringUtil.isNullOrEmpty(template)) {
                templates.append(abbreviation);
                properties.setProperty(abbreviation, template);
            }
            if (codeTemplatesIterator.hasNext()) {
                templates.append(INTRA_SEPARATOR);
            }
        }
        properties.setProperty(CODE_TEMPLATES, templates.toString());
        storeConfigProperties(this.codeTemplatesConfig, properties);
    }
}
