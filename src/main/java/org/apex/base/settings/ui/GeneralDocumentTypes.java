/*
 * GeneralDocumentTypes.java
 * Created on 12 July, 2007, 12:59 AM 
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

import java.util.List;
import java.util.Vector;
import org.apex.base.component.EditorDialog;
import org.apex.base.data.IDocumentType;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.settings.DocumentTypesConfiguration;
import org.apex.base.settings.ui.text.UIConfigurationDataModel;
import org.apex.base.settings.ui.text.UIConfigurationUtility;

/**
 * The document extensions maintenance page. Using this configuration page
 * document extensions can be added or modified or removed.
 *
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GeneralDocumentTypes extends javax.swing.JPanel implements
        UIConfigurationDataModel {

    /**
     * Document types configuration.
     */
    private final DocumentTypesConfiguration docTypesConfig;
    /**
     * Selected document type.
     */
    private IDocumentType selectedDocumentType;
    /**
     * Selected document extension.
     */
    private String selectedExtension;
    /**
     * The index of selected extension.
     */
    private int selectedExtensionIndex;
    /**
     * A list of extensions for selected document type.
     */
    private Vector<String> selectedDocExtensions;

    /**
     * Creates new form {@code GeneralDocumentTypes} using given
     * document types configuration.
     * @param docTypesConfig Document types configuration.
     */
    public GeneralDocumentTypes(
            DocumentTypesConfiguration docTypesConfig) {       
        this.docTypesConfig = docTypesConfig;
        initComponents();
        applyConfiguration();
    }

    /**
     *
     */
    private void checkDocTypeListAndChangeButtonStatus() {
        if (this.docTypesList.getModel().getSize() == 0 ||
                this.docTypesList.isSelectionEmpty()) {
            this.editDocType.setEnabled(false);
            this.deleteDocType.setEnabled(false);
        } else {
            this.editDocType.setEnabled(true);
            this.deleteDocType.setEnabled(true);
        }
        // Do not allow edit and delete of in-built document types
        if (!getSelectedDocumentType().isCustomDocumentType()) {
            this.editDocType.setEnabled(false);
            this.deleteDocType.setEnabled(false);
        }
    }

    /**
     *Creates a given dialog.
     * @param dialog The dialog window.
     */
    private void createDialog(EditorDialog dialog) {
        dialog.createDialog(new InputParams(), new OutputParams());
    }

    /**
     * Returns the selected document type.
     * @return The selected document type.
     */
    private IDocumentType getSelectedDocumentType() {
        if (this.selectedDocumentType == null) {            
            return (IDocumentType) this.docTypesList.getSelectedValue();
        }
        return this.selectedDocumentType;
    }

    /**
     * Returns the list of extensions for selected document type.
     * @return The list of extensions for selected document type.
     */
    private Vector<String> getSelectedDocumentExtensions() {
        if (this.selectedDocExtensions == null) {
            return this.docTypesConfig.getExtensions(getSelectedDocumentType());
        }
        return this.selectedDocExtensions;
    }

    /**
     * Returns the index of selected document extension.
     * @return The index of selected document extension.
     */
    private int getSelectedDocumentExtensionIndex() {
        return this.selectedExtensionIndex;
    }

    /**
     * Returns the selected document extension.
     * @return The selected document extension.
     */
    private String getSelectedDocumentExtension() {
        if (this.selectedExtension == null) {
            return (String) this.extensionsList.getSelectedValue();
        }
        return this.selectedExtension;
    }

    /**
     * Returns the index of selected document type.
     * @return The index of selected document type.
     */
    private int getSelectedDocumentTypeIndex() {
        return this.docTypesList.getSelectedIndex();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane8 = new javax.swing.JScrollPane();
        docTypesList = new javax.swing.JList();
        documentTypesTitle = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        extensionsList = new javax.swing.JList();
        extensionsTitle = new javax.swing.JLabel();
        addExtension = new javax.swing.JButton();
        editExtension = new javax.swing.JButton();
        deleteExtension = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        docType = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        docExtension = new javax.swing.JLabel();
        defaultExt = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        addDocType = new javax.swing.JButton();
        editDocType = new javax.swing.JButton();
        deleteDocType = new javax.swing.JButton();

        docTypesList.setFont(new java.awt.Font("Tahoma", 1, 11));
        docTypesList.setModel(UIConfigurationUtility.getListModel(getDocumentTypes()));
        docTypesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        docTypesList.setSelectedIndex(0);
        docTypesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                documentTypeChangeHandler(evt);
            }
        });
        jScrollPane8.setViewportView(docTypesList);

        documentTypesTitle.setText("Document Types:");

        extensionsList.setModel(UIConfigurationUtility.getListModel(getDefaultExtensions()));
        extensionsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        extensionsList.setSelectedIndex(0);
        extensionsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                extensionsListValueChanged(evt);
            }
        });
        jScrollPane9.setViewportView(extensionsList);

        extensionsTitle.setText("Extensions:");

        addExtension.setText("Add");
        addExtension.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addExtensionActionPerformed(evt);
            }
        });

        editExtension.setText("Edit");
        editExtension.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editExtensionActionPerformed(evt);
            }
        });

        deleteExtension.setText("Delete");
        deleteExtension.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteExtensionActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "View", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(0, 0, 255))); // NOI18N

        jLabel1.setText("Document Type:");

        docType.setText(getSelectedDocumentType().toString());

        jLabel2.setText("Document Extension:");

        docExtension.setText(getSelectedDocumentExtension());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(docType, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(docExtension, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(docType))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(docExtension))
                .addContainerGap())
        );

        defaultExt.setForeground(new java.awt.Color(0, 0, 204));
        defaultExt.setText(getDefaultExtension());

        jLabel3.setText("Default:");

        addDocType.setText("Add");
        addDocType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDocTypeActionPerformed(evt);
            }
        });

        editDocType.setText("Edit");
        editDocType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDocTypeActionPerformed(evt);
            }
        });

        deleteDocType.setText("Delete");
        deleteDocType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteDocTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(documentTypesTitle)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addDocType)
                            .addComponent(editDocType)
                            .addComponent(deleteDocType))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(extensionsTitle)
                                    .addGap(81, 81, 81))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(defaultExt, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(deleteExtension)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(addExtension)
                                        .addComponent(editExtension))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addExtension, deleteExtension, editExtension});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addDocType, deleteDocType, editDocType});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(documentTypesTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(87, 87, 87)
                                .addComponent(addExtension)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editExtension)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteExtension)))
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(addDocType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editDocType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteDocType)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(extensionsTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(defaultExt, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addGap(9, 9, 9)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    private void documentTypeChangeHandler(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_documentTypeChangeHandler
        this.selectedDocumentType =
                (IDocumentType) this.docTypesList.getSelectedValue();
        if (this.selectedDocumentType != null) {
            checkDocTypeListAndChangeButtonStatus();
            this.defaultExt.setText(selectedDocumentType.getDefaultExtension());
            this.selectedDocExtensions =
                    this.docTypesConfig.getExtensions(selectedDocumentType);
            displayExtensions();
            this.docType.setText(selectedDocumentType.getDisplayName());
        }
    }//GEN-LAST:event_documentTypeChangeHandler

    private void addExtensionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExtensionActionPerformed
        IDocumentType selectedDocType = getSelectedDocumentType();
        AddDocumentExtensionDialog addDialog = new AddDocumentExtensionDialog(
                this.docTypesConfig, selectedDocType, this.extensionsList);
        createDialog(addDialog);
        checkExtnListAndChangeButtonStatus();
}//GEN-LAST:event_addExtensionActionPerformed

    private void editExtensionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editExtensionActionPerformed
        IDocumentType selectedDocType = getSelectedDocumentType();
        EditDocumentExtensionDialog editDialog = new EditDocumentExtensionDialog(
                this.docTypesConfig, selectedDocType, this.extensionsList);
        createDialog(editDialog);
}//GEN-LAST:event_editExtensionActionPerformed

    private void deleteExtensionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteExtensionActionPerformed
        Vector<String> extensions = getSelectedDocumentExtensions();
        extensions.removeElementAt(getSelectedDocumentExtensionIndex());
        this.extensionsList.setModel(UIConfigurationUtility.getListModel(
                extensions));
        this.extensionsList.setSelectedIndex(0);
        checkExtnListAndChangeButtonStatus();
}//GEN-LAST:event_deleteExtensionActionPerformed

    private void extensionsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_extensionsListValueChanged
        this.selectedExtension = (String) this.extensionsList.getSelectedValue();
        this.selectedExtensionIndex = this.extensionsList.getSelectedIndex();
        this.docExtension.setText(selectedExtension);
    }//GEN-LAST:event_extensionsListValueChanged

    private void editDocTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDocTypeActionPerformed
        IDocumentType selectedDocType = getSelectedDocumentType();
        EditDocumentTypeDialog editDialog = new EditDocumentTypeDialog(
                this.docTypesConfig, selectedDocType, this.docTypesList);
        createDialog(editDialog);
}//GEN-LAST:event_editDocTypeActionPerformed

    private void addDocTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDocTypeActionPerformed
        AddDocumentTypeDialog addDialog = new AddDocumentTypeDialog(
                this.docTypesConfig, this.docTypesList);
        createDialog(addDialog);
        checkDocTypeListAndChangeButtonStatus();
    }//GEN-LAST:event_addDocTypeActionPerformed

    private void deleteDocTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteDocTypeActionPerformed
        List<IDocumentType> docTypes = getDocumentTypes();
        this.docTypesConfig.removeDocumentType(docTypes.get(getSelectedDocumentTypeIndex()));
        // @TODO Once a document type is deleted other configurations dependent
        // on document type list would be out of sync. To solve this problem either
        // make sure document type list is updated at the begining or update all
        // dependent configurations here.
        this.docTypesList.setModel(UIConfigurationUtility.getListModel(
                docTypes));
        this.docTypesList.setSelectedIndex(0);
        checkDocTypeListAndChangeButtonStatus();
    }//GEN-LAST:event_deleteDocTypeActionPerformed

    /**
     * For a selected document type, it displays the list of extensions in a list.
     */
    private void displayExtensions() {
        this.defaultExt.setText(getSelectedDocumentType().getDefaultExtension());
        Vector<String> extensions = getSelectedDocumentExtensions();
        this.extensionsList.setModel(UIConfigurationUtility.getListModel(
                extensions));
        this.extensionsList.setSelectedIndex(0);
        checkExtnListAndChangeButtonStatus();
    }

    /**
     * Based on number of document extensions available in the list it enables or disables
     * add, edit, remove buttons on the page.
     */
    private void checkExtnListAndChangeButtonStatus() {
        if (this.extensionsList.getModel().getSize() == 0 ||
                this.extensionsList.isSelectionEmpty()) {
            this.editExtension.setEnabled(false);
            this.deleteExtension.setEnabled(false);
        } else {
            this.editExtension.setEnabled(true);
            this.deleteExtension.setEnabled(true);
        }
    }

    /**
     * Returns a list of document types.
     * @return A list of document types.
     */
    private List<IDocumentType> getDocumentTypes() {
        return this.docTypesConfig.getDocumentTypes();
    }

    /**
     * Returns a list of default extensions. For a document type if no extension found this
     * method is invoked to get a default list.
     * @return A list of default extensions.
     */
    private Vector<String> getDefaultExtensions() {
        return new Vector<String>();
    }

    /**
     * Returns the default extension for selected document type.
     * @return The default extension for selected document type.
     */
    private String getDefaultExtension() {
        return getSelectedDocumentType().getDefaultExtension();
    }

    public void applyConfiguration() {
        checkDocTypeListAndChangeButtonStatus();
        displayExtensions();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDocType;
    private javax.swing.JButton addExtension;
    private javax.swing.JLabel defaultExt;
    private javax.swing.JButton deleteDocType;
    private javax.swing.JButton deleteExtension;
    private javax.swing.JLabel docExtension;
    private javax.swing.JLabel docType;
    private javax.swing.JList docTypesList;
    private javax.swing.JLabel documentTypesTitle;
    private javax.swing.JButton editDocType;
    private javax.swing.JButton editExtension;
    private javax.swing.JList extensionsList;
    private javax.swing.JLabel extensionsTitle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    // End of variables declaration//GEN-END:variables
}
