/*
 * DocumentExtensionChangeSupport.java
 * Created on 20 Oct, 2007, 7:31:42 PM
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
package org.apex.base.settings.ui;

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.settings.DocumentTypesConfiguration;
import org.apex.base.settings.InvalidSettingsParameterException;
import org.apex.base.ui.text.UIDialogModel;
import org.apex.base.util.StringUtil;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import org.apex.base.data.IDocumentType;

/**
 * A helper class for add and edit document extension UI pages.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class DocumentExtensionChangeSupport extends JPanel implements
        UIDialogModel {

    /**
     * Document types configuration.
     */
    protected DocumentTypesConfiguration docTypesConfig;
    /**
     * Selected document type.
     */
    protected IDocumentType documentType;
    /**
     * A list where document extensions are displayed.
     */
    protected JList extensionsList;
    /**
     * The container dialog window.
     */
    protected JDialog containerWindow;

    /**
     * Creates a new instance of {@code DocumentExtensionChangeSupport} using specified
     * document types configuration, selected document type and a list where all available extensions 
     * for selected document type are displayed.
     * @param docTypesConfig Document types configuration.
     * @param selectedDocumentType Selected document type.
     * @param extensionsList A list where document extensions are displayed.
     */
    public DocumentExtensionChangeSupport(
            DocumentTypesConfiguration docTypesConfig,
            IDocumentType selectedDocumentType, JList extensionsList) {
        this.docTypesConfig = docTypesConfig;
        this.documentType = selectedDocumentType;
        this.extensionsList = extensionsList;
    }

    /**
     * Returns the selected document type.
     * @return The selected document type.
     */
    protected IDocumentType getSelectedDocumentType() {
        return this.documentType;
    }

    public void setContainerWindow(JDialog window) {
        this.containerWindow = window;
    }

    public JDialog getContainerWindow() {
        return this.containerWindow;
    }

    public EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Closes the dialog window.
     */
    protected void closeWindow() {
        getContainerWindow().setVisible(false);
    }

    /**
     * Returns the list of document extensions for selected document type.
     * @return The list of document extensions.
     */
    protected Vector<String> getSelectedDocumentExtensions() {
        return getDocumentExtensions(getSelectedDocumentType());
    }

    /**
     * Returns the list of document extensions for a given document type.
     * @param documentType A document type.
     * @return The list of document extensions.
     */
    protected Vector<String> getDocumentExtensions(IDocumentType documentType) {
        return this.docTypesConfig.getExtensions(documentType);
    }

    /**
     * Validates the document extension.
     * @param extension Document extension.
     * @throws InvalidSettingsParameterException If document extension is null or blank or a
     *                 duplicate one. 
     */
    protected void validateDocumentExtension(String extension) throws
            InvalidSettingsParameterException {
        // Check whether null or blank
        if (StringUtil.isNullOrEmpty(extension)) {
            throw new InvalidSettingsParameterException(
                    "Document extension not entered.",
                    1002);
        }
        extension = extension.trim();
        // Check whether already exists under selected document type
        if (getSelectedDocumentExtensions().contains(extension.toLowerCase()) || getSelectedDocumentType().
                getDefaultExtension().equalsIgnoreCase(extension)) {
            throw new InvalidSettingsParameterException(
                    "Document extension not entered.",
                    1001,
                    "EXTENSION='" + extension + "'");
        }
        // Check whether exists under other document types
        for (IDocumentType doc : getContext().getEditorProperties().getDocumentTypeBase().
                getDocumentTypes()) {
            if (doc.equals(getSelectedDocumentType())) {
                continue;
            }
            if (getDocumentExtensions(doc).contains(extension.toLowerCase()) || doc.
                    getDefaultExtension().
                    equalsIgnoreCase(extension)) {
                throw new InvalidSettingsParameterException(
                        "Document extension not entered.",
                        1003,
                        "EXTENSION='" + extension + "',DOCUMENT_TYPE='" +
                        doc.getDisplayName() + "'");

            }
        }
    }
}