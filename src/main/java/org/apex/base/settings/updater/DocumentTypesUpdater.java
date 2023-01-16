/*
 * DocumentTypesUpdater.java
 * Created on 27 Oct, 2007, 8:29:55 PM
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
import org.apex.base.settings.DocumentTypesConfiguration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import org.apex.base.data.IDocumentType;
import org.apex.base.util.ConfigurationUtility;

/**
 * Stores document types configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentTypesUpdater extends ConfigurationUpdateSupport {

    /**
     * Document types configuration.
     */
    private DocumentTypesConfiguration docTypesConfig;
    /**
     * Cloned document types configuration.
     */
    private DocumentTypesConfiguration clonedDocTypesConfig;

    /**
     * Creates a new instance of {@code DocumentTypesUpdater} using specified
     * document types configuration and cloned document types configuration.
     * @param docTypesConfig Document types configuration.
     * @param clonedDocTypesConfig Cloned document types configuration. 
     */
    public DocumentTypesUpdater(DocumentTypesConfiguration docTypesConfig,
            DocumentTypesConfiguration clonedDocTypesConfig) {
        this.docTypesConfig = docTypesConfig;
        this.clonedDocTypesConfig = clonedDocTypesConfig;
    }

    @Override
    public void update(Properties properties) {
        this.docTypesConfig.updateFromClone(this.clonedDocTypesConfig);
        if (properties == null) {
            properties = loadConfigProperties(this.docTypesConfig);
        }
        StringBuilder docTypeData = new StringBuilder("");
        StringBuilder docTypeList = new StringBuilder("");
        List<IDocumentType> docTypes = this.docTypesConfig.getDocumentTypes();
        for (IDocumentType docType : docTypes) {
            if (docType.isCustomDocumentType()) {
                // Clear the data to be ready with next document type.
                docTypeData.delete(0, docTypeData.length());
                properties.setProperty(docType.getName().toLowerCase() +
                        DEFINITION,
                        docType.getName() + INTER_SEPARATOR + docType.getDisplayName() + INTER_SEPARATOR + docType.getDefaultExtension() + INTER_SEPARATOR + docType.getDocumentClass() + INTER_SEPARATOR + ConfigurationUtility.removeBraces(docType.getTokens().
                        toString()));
                docTypeList.append(docType.getName().toLowerCase() + INTRA_SEPARATOR);
            }
        }
        if (docTypeList != null && docTypeList.length() != 0) {
            String customDocTypes = docTypeList.toString().trim();
            customDocTypes = customDocTypes.substring(0, customDocTypes.lastIndexOf(INTRA_SEPARATOR));
            properties.setProperty(
                    CUSTOM_DOCUMENT_TYPES,
                    docTypeList.toString());
        }
        StringBuilder extensionData = new StringBuilder("");
        HashMap<IDocumentType, Vector<String>> docTypeExtnMap =
                this.docTypesConfig.getDocTypeExtnMap();
        for (Iterator docs = docTypeExtnMap.keySet().iterator(); docs.hasNext();) {
            // Clear the data to be ready with next document type.
            extensionData.delete(0, extensionData.length());
            IDocumentType docType = (IDocumentType) docs.next();
            Vector<String> extensions = docTypeExtnMap.get(docType);
            if (extensions != null && extensions.size() > 0) {
                for (int iCount = 0; iCount < extensions.size(); iCount++) {
                    String extension = extensions.get(iCount);
                    extensionData.append(extension);
                    if (iCount < extensions.size() - 1) {
                        extensionData.append(INTRA_SEPARATOR);
                    }
                }
                properties.setProperty(docType.getDisplayName().toLowerCase() +
                        CommonConstants.WORD_SEPARATOR +
                        EXTENSIONS,
                        extensionData.toString());
            } else {
                properties.remove(docType.getDisplayName().toLowerCase() +
                        CommonConstants.WORD_SEPARATOR +
                        EXTENSIONS);
            }
            storeConfigProperties(this.docTypesConfig, properties);
        }
    }
}
