/*
 * NewFileMenu.java
 * Created on December 20, 2006, 6:16 PM
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
package org.apex.base.menu;

import java.util.Calendar;
import org.apex.base.common.Task;
import org.apex.base.constant.CommonConstants;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.DocumentType;
import org.apex.base.data.DocumentWrapper;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.logging.Logger;
import org.apex.base.settings.DocumentTemplateConfiguration;
import org.apex.base.util.DocumentCreator;
import org.apex.base.util.DocumentData;
import org.apex.base.util.DocumentSelection;
import org.apex.base.util.MenuUtil;
import org.apex.base.util.StringUtil;
import javax.swing.text.BadLocationException;
import org.apex.base.constant.MenuConstants;

/**
 * Creates a temporary document and sets the newly created document
 * as currently displayed document in editor.
 * <p>
 * Temporary documents don't exist in file system. Hence, the file name is assigned by application
 * in 'Document [n].extension' format.
 * <p>
 * If new document is a typed one, the document extension is decided from default extension of that
 * type. In case new document is not typed, default extension of application is used. Default file extension
 * of application can be changed by user.
 * <p>
 * In case of typed document if template of that document type exists, after creating the new document,
 * template is inserted to document.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class NewFileMenu extends UILessMenu {

    /**
     * Creates a new temporary document.
     */
    public NewFileMenu() {
    }

    public void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Creates a temporary document and shows on editor as currently displayed document.
     * <p>
     * The document extension is decided from type of document. In case the document
     * is of default type, default extension of application is used.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    public void execute(InputParams in, OutputParams out) {
        createNewFile(in, out);
    }

    public void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Creates a temporary document and shows on editor as currently displayed document.
     * <p>
     * The document extension is decided from type of document. In case the document
     * is of default type, default extension of application is used.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @SuppressWarnings("unchecked")
    private void createNewFile(InputParams in,
            OutputParams out) {
        if (!getContext().getEditorProperties().isAtleastOneDocumentOpen()) {
            MenuUtil.updateMenuStatus(getContext(), false);
        }
        String extension = DocumentType.DEFAULT.getDefaultExtension();
        extension = getExtension((String) in.get("MENU_ID")).toLowerCase();
        AbstractDocument newFile = DocumentCreator.createDocument("Document ["
                + getContext().getEditorProperties().
                getNextTempFileSrlNumber() + "]." + extension, getContext());
        Logger.logInfo("Creating new document: " + newFile.getName());
        newFile.setTemporary(true);
        // Initialize menu states
        newFile.getMenuState().init(getContext());

        // Create the document wrapper
        DocumentWrapper documentWrapper = new DocumentWrapper(newFile);
        in.put(CommonConstants.FILE_WRAPPER, documentWrapper);
        Task task = new CreateDocumentTask();
        // Pre process
        task.preProcess(in, out);
        // Post process
        task.postProcess(in, out);

        // Add the file to the list
        DocumentData.addFileToList(getContext(), documentWrapper);

        // Change the selection
        DocumentSelection.setDocumentSelectedIndex(getContext(),
                DocumentSelection.getSelectionIndexAfterNew(getContext()));

        // Add to document queue as well.
        getContext().getEditorProperties().getDocsQueue().add(newFile.
                getDisplayName(),
                newFile.getAbsolutePath());
        // Grab the focus
        newFile.getEditor().requestFocusInWindow();
        // After creating the document insert document template based on document type
        // @TODO do it in a separate thread
        insertDocumentTemplate(newFile);
        if (getContext().getEditorProperties().getNoOfOpenDocuments() == 1) {
            MenuUtil.updateMenuStatus(getContext(), true);
            MenuUtil.updateMenuStatus(getContext(), newFile);
        }
    }

    /**
     * Returns the default extension for a particular document type which is derived from selected
     * document type. Document type is determined from selected menu id.
     * @param menuId The selected menu Id.
     * @return Default extension of selected document type.
     */
    private String getExtension(String menuId) {
        String defaultExtension = getContext().getConfiguration().
                getGeneralConfig().getGeneral().
                getDefaultExtension();
        if (StringUtil.isNullOrEmpty(defaultExtension)) {
            defaultExtension = DocumentType.DEFAULT.getDefaultExtension();
        }
        if (StringUtil.isNullOrEmpty(menuId)) {
            return defaultExtension;
        }
        if (menuId.equalsIgnoreCase(MenuConstants.NEW_FILE)) {
            return defaultExtension;
        }
        return getContext().getEditorProperties().getDocumentTypeBase().
                getDocumentTypeByName(menuId).
                getDefaultExtension();
    }

    /**
     * It gets the document template for selected document type from configuration data
     * and inserts the same to current document data model. 
     * @param newFile The document.
     */
    // @TODO - Use a template manager to manage templates. Template manager can do following things -
    // 1. Document type based template grouping
    // 2. User defined arbitrary templates
    // 3. Pre-defined variables in templates
    private void insertDocumentTemplate(AbstractDocument newFile) {
        if (newFile.getDocumentType().equals(DocumentType.DEFAULT)) {
            // @TODO Consider template for default document types as well.
            return;
        }
        try {
            DocumentTemplateConfiguration docTemplateConfig =
                    this.getContext().getConfiguration().
                    getTemplateConfig().getDocTemplateConfig();
            // @TODO - Remove 3 lines below. These are placed only for demo purpose.
            newFile.getDocument().
                    insertString(0,
                    newFile.getDocumentType().getStartCommentToken() + " User: " + System.
                    getProperty("user.name") + newFile.getDocumentType().
                    getEndCommentToken() + "\n",
                    null);
            newFile.getDocument().
                    insertString(newFile.getDocument().getLength(),
                    newFile.getDocumentType().getStartCommentToken() + " Datetime: " + Calendar.
                    getInstance().
                    getTime() + newFile.getDocumentType().
                    getEndCommentToken() + "\n",
                    null);
            newFile.getDocument().
                    insertString(newFile.getDocument().getLength(),
                    newFile.getDocumentType().getStartCommentToken() + " File name: " + newFile.
                    getName() + newFile.getDocumentType().
                    getEndCommentToken() + "\n",
                    null);
            newFile.getDocument().
                    insertString(newFile.getDocument().getLength(),
                    docTemplateConfig.getDocTemplates().get(newFile.
                    getDocumentType()),
                    null);
        } catch (BadLocationException ex) {
            Logger.logWarning("Failed to insert template code to newly created document: " + newFile.
                    getName(), ex);
        } catch (Exception ex) {
            Logger.logWarning("Failed to insert template code to newly created document: " + newFile.
                    getName(), ex);
        } finally {
            newFile.getEditor().setCaretPosition(0);
        }
    }
}
