/*
 * SyntaxStyleUpdater.java
 * Created on 27 Oct, 2007, 8:32:11 PM 
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
import org.apex.base.constant.CommonConstants;
import org.apex.base.util.ConfigurationUtility;
import org.apex.base.settings.Style;
import org.apex.base.settings.SyntaxStyleConfiguration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import org.apex.base.data.IDocumentType;

/**
 * Stores syntax style configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SyntaxStyleUpdater extends ConfigurationUpdateSupport {

    /**
     *  Syntax style configuration.
     */
    private SyntaxStyleConfiguration syntaxStyleConfig;
    /**
     * Cloned syntax style configuration.
     */
    private SyntaxStyleConfiguration clonedSyntaxStyleConfig;

    /**
     * Creates a new instance of {@code SyntaxStyleUpdater} using specified
     * syntax style configuration and cloned syntax style configuration.
     * @param syntaxStyleConfig Syntax style configuration.
     * @param clonedSyntaxStyleConfig Cloned syntax style configuration. 
     */
    public SyntaxStyleUpdater(SyntaxStyleConfiguration syntaxStyleConfig,
            SyntaxStyleConfiguration clonedSyntaxStyleConfig) {
        this.syntaxStyleConfig = syntaxStyleConfig;
        this.clonedSyntaxStyleConfig = clonedSyntaxStyleConfig;
    }

    @Override
    public void update(Properties properties) {
        this.syntaxStyleConfig.updateFromClone(this.clonedSyntaxStyleConfig);
        if (properties == null) {
            properties = loadConfigProperties(this.syntaxStyleConfig);
        }
        StringBuilder syntaxStyleData = new StringBuilder("");
        HashMap<IDocumentType, Vector<Style>> synatxStyleMap =
                this.syntaxStyleConfig.getSyntaxStyleMap();
        for (Iterator docs = synatxStyleMap.keySet().iterator(); docs.hasNext();) {
            // Clear the data to be ready with next document type.
            syntaxStyleData.delete(0, syntaxStyleData.length());
            IDocumentType docType = (IDocumentType) docs.next();
            Vector<Style> styles = synatxStyleMap.get(docType);
            if (styles != null && styles.size() > 0) {
                // Using old style for loop as index is required inside the loop
                for (int iCount = 0; iCount < styles.size(); iCount++) {
                    Style style = styles.get(iCount);
                    syntaxStyleData.append(style);
                    if (iCount < styles.size() - 1) {
                        syntaxStyleData.append(INTER_SEPARATOR);
                    }
                }
                properties.setProperty(docType.toString().toLowerCase() +
                        CommonConstants.WORD_SEPARATOR +
                        SYNTAX,
                        ConfigurationUtility.removeBraces(syntaxStyleData.toString()));
            }
        }
        storeConfigProperties(syntaxStyleConfig, properties);
    }
}
