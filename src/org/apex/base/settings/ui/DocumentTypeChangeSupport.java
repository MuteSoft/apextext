/*
 * DocumentTypeChangeSupport.java 
 * Created on 19 Oct, 2009, 4:01:41 PM
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
package org.apex.base.settings.ui;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.data.IDocumentType;
import org.apex.base.settings.DocumentTypesConfiguration;
import org.apex.base.settings.InvalidSettingsParameterException;
import org.apex.base.ui.text.UIDialogModel;
import org.apex.base.util.ConfigurationUtility;

/**
 * A support class for addition and modification of document types..
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class DocumentTypeChangeSupport extends JPanel implements
        UIDialogModel {

    /**
     * Document types configuration.
     */
    protected DocumentTypesConfiguration docTypesConfig;
    /**
     * Selected document type.
     */
    protected IDocumentType selectedDocumentType;
    /**
     * A list where document extensions are displayed.
     */
    protected JList docTypesList;
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
     * @param docTypesList A list where document types are displayed.
     */
    public DocumentTypeChangeSupport(
            DocumentTypesConfiguration docTypesConfig,
            IDocumentType selectedDocumentType, JList docTypesList) {
        this.docTypesConfig = docTypesConfig;
        this.selectedDocumentType = selectedDocumentType;
        this.docTypesList = docTypesList;
    }

    /**
     * Returns the selected document type.
     * @return The selected document type.
     */
    protected IDocumentType getSelectedDocumentType() {
        return this.selectedDocumentType;
    }

    /**
     * Returns default extension of a document type.
     * @return Default document extension.
     */
    public String getDocumentTypeName() {
        return this.selectedDocumentType.getName() == null
                ? ""
                : this.selectedDocumentType.getName();
    }

    /**
     * Returns the document class.
     * @return The document class.
     */
    public String getDocumentClass() {
        return this.selectedDocumentType.getDocumentClass() == null
                ? ""
                : this.selectedDocumentType.getDocumentClass();
    }

    /**
     * Returns the document class.
     * @return The document class.
     */
    public String getDefaultExtension() {
        return this.selectedDocumentType.getDefaultExtension() == null
                ? ""
                : this.selectedDocumentType.getDefaultExtension();
    }

    /**
     *Returns display name of document type.
     * @return The display name of document type.
     */
    public String getDisplayName() {
        return this.selectedDocumentType.getDisplayName() == null
                ? ""
                : this.selectedDocumentType.getDisplayName();
    }

    /**
     * Returns a list of syntax token categories.
     * @return A list of syntax token categories.
     */
    public String getTokens() {
        return this.selectedDocumentType.getTokens() == null
                ? ""
                : ConfigurationUtility.removeBraces(this.selectedDocumentType.
                getTokens().toString());
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
     * Validates the document type.
     * @param documentType Name of document Type.
     * @throws InvalidSettingsParameterException If document extension is null or blank or a
     *                 duplicate one.
     */
    protected void validateDocumentTypeName(String documentType) throws
            InvalidSettingsParameterException {
        // @TODO validation pending.
    }

    /**
     * Validates the default extension.
     * @param defaultExtension Default extension.
     * @throws InvalidSettingsParameterException If document extension is null or blank or a
     *                 duplicate one.
     */
    protected void validateDefaultExtension(String defaultExtension) throws
            InvalidSettingsParameterException {
        // @TODO validation pending.
    }

    /**
     * Validates the display name.
     * @param displayName Document display name.
     * @throws InvalidSettingsParameterException If document display name is null or blank or a
     *                 duplicate one.
     */
    protected void validateDisplayName(String displayName) throws
            InvalidSettingsParameterException {
        // @TODO validation pending.
    }

    /**
     * Validates the document class name.
     * @param documentClass Document class.
     * @throws InvalidSettingsParameterException If document extension is null or blank or a
     *                 duplicate one.
     */
    protected void validateDocumentClass(String documentClass) throws
            InvalidSettingsParameterException {
        // Do not allow using occupied document classes
        // @TODO validation pending.
    }

    /**
     * Validates the list of tokens.
     * @param tokens Comma separated list of tokens.
     * @throws InvalidSettingsParameterException If document extension is null or blank or a
     *                 duplicate one.
     */
    protected void validateTokens(String tokens) throws
            InvalidSettingsParameterException {
        // @TODO validation pending.
    }
}
