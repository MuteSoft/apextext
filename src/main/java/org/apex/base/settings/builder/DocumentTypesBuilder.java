/*
 * DocumentTypesBuilder.java
 * Created on 15 July, 2007, 1:38 AM
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

import java.util.ArrayList;
import org.apex.base.data.ConfigurationBuilderSupport;
import org.apex.base.constant.CommonConstants;
import org.apex.base.logging.Logger;
import org.apex.base.data.Configuration;
import org.apex.base.util.ConfigurationUtility;
import org.apex.base.settings.DocumentTypesConfiguration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import org.apex.base.core.EditorBase;
import org.apex.base.data.DocumentType;
import org.apex.base.data.IDocumentType;
import org.apex.base.util.StringUtil;

/**
 * Builds document types configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentTypesBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code DocumentTypesBuilder}.
     */
    public DocumentTypesBuilder() {
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading document type configuration.");
        DocumentTypesConfiguration docTypesConfig =
                new DocumentTypesConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(docTypesConfig);
        }
        // Add custom document types to master list.
        List<IDocumentType> customDocTypes = getCustomDocumentTypes(properties);
        for (IDocumentType docType : customDocTypes) {
            if (docType != null) {
                EditorBase.getContext().getEditorProperties().
                        getDocumentTypeBase().add(docType);
                docTypesConfig.addDocumentType(docType);
            }
        }
        docTypesConfig.setDocTypeExtnMap(getDocumentTypeExtns(properties));
        return docTypesConfig;
    }

    /**
     * Constructs the list of custom document types from configuration file.
     * @param properties A table of key value pairs.
     * @return List of custom document types.
     */
    private List<IDocumentType> getCustomDocumentTypes(Properties properties) {
        List<IDocumentType> customDocTypes = new ArrayList<IDocumentType>(5);
        Vector<String> customDocTypeNames = ConfigurationUtility.
                getListFromString(properties.getProperty(CUSTOM_DOCUMENT_TYPES),
                INTRA_SEPARATOR);
        IDocumentType customDocType = null;
        for (String customDocTypeId : customDocTypeNames) {
            customDocType = getDocumentType(customDocTypeId, properties);
            if (customDocType != null) {
                customDocTypes.add(customDocType);
            }
        }
        return customDocTypes;
    }

    /**
     * Create a custom document type object from configuration properties.
     * @param customDocTypeId The document type id.
     * @param properties A table of key value pairs.
     * @return A custom document type.
     */
    private IDocumentType getDocumentType(String customDocTypeId,
            Properties properties) {
        if (StringUtil.isNullOrEmpty(customDocTypeId)) {
            return null;
        }
        String docTypeDef = properties.getProperty(customDocTypeId + DEFINITION);
        Vector<String> docTypeProperties =
                ConfigurationUtility.getListFromString(docTypeDef,
                INTER_SEPARATOR);
        IDocumentType docType = null;
        String docTypeName = "";
        String defaultExtension = "";
        String displayName = "";
        String documentClass = "";
        String tokens = "";
        for (int iCount = 0; iCount < docTypeProperties.size(); iCount++) {
            if (StringUtil.isNullOrEmpty(docTypeProperties.get(iCount))) {
                continue;
            }
            try {
                switch (iCount) {
                    case 0:
                        docTypeName = docTypeProperties.get(iCount).trim();
                        break;
                    case 1:
                        displayName = docTypeProperties.get(iCount).trim();
                        break;
                    case 2:
                        defaultExtension = docTypeProperties.get(iCount).trim().
                                toLowerCase();
                        break;
                    case 3:
                        documentClass = docTypeProperties.get(iCount).trim();
                        break;
                    case 4:
                        tokens = docTypeProperties.get(iCount).trim();
                        break;
                    default:
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException aob) {
                Logger.logWarning("Error while parsing custom tool node: " +
                        customDocTypeId, aob);
            } catch (NullPointerException npe) {
                Logger.logWarning("Error while parsing custom tool node: " +
                        customDocTypeId, npe);
            } catch (Exception ex) {
                Logger.logWarning("Error while parsing custom tool node: " +
                        customDocTypeId, ex);
            }
        }
        docType = new DocumentType(docTypeName, displayName, defaultExtension,
                documentClass,
                ConfigurationUtility.getListFromString(tokens, INTRA_SEPARATOR),
                true);
        return docType;
    }

    /**
     * Builds and returns a table containing document extensions for different document types.
     * @param properties A table of key value pairs.
     * @return A table containing document extensions for different document types.
     */
    private HashMap<IDocumentType, Vector<String>> getDocumentTypeExtns(
            Properties properties) {
        HashMap<IDocumentType, Vector<String>> documentTypes =
                new HashMap<IDocumentType, Vector<String>>();
        for (IDocumentType documentType : EditorBase.getContext().
                getEditorProperties().
                getDocumentTypeBase().getDocumentTypes()) {
            String extensions =
                    properties.getProperty(documentType +
                    CommonConstants.WORD_SEPARATOR +
                    EXTENSIONS);
            if (extensions == null || extensions.equals("")) {
                documentTypes.put(documentType,
                        new Vector<String>());
                continue;
            }
            // @TODO Validate document extensions while loading.
            documentTypes.put(documentType,
                    ConfigurationUtility.getLowerCaseTokenListFromString(
                    extensions,
                    INTRA_SEPARATOR));
        }
        return documentTypes;
    }
}
