/*
 * AddDocumentType.java
 * Created on 19 Oct, 2009, 3:36:31 PM
 *
 * Copyright (C) 2010 Mrityunjoy Saha
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

import javax.swing.JList;
import javax.swing.text.AbstractDocument;
import org.apex.base.data.AlphaNumericFilter;
import org.apex.base.data.DocumentType;
import org.apex.base.data.IDocumentType;
import org.apex.base.settings.DocumentTypesConfiguration;
import org.apex.base.settings.InvalidSettingsParameterException;
import org.apex.base.settings.SettingsMessageManager;
import org.apex.base.settings.ui.text.UIConfigurationUtility;
import org.apex.base.util.ConfigurationUtility;

/**
 * This is the view for adding new (custom) document types.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class AddDocumentType extends DocumentTypeChangeSupport {

    /**
     * Creates new form AddDocumentType.
     * @param docTypesConfig Document types configuration.
     * @param docTypesList A list where document types are displayed.
     */
    public AddDocumentType(DocumentTypesConfiguration docTypesConfig,
            JList docTypesList) {
        super(docTypesConfig, null, docTypesList);
        initComponents();
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        documentType = new javax.swing.JTextField();
        ((AbstractDocument)this.documentType.getDocument()).setDocumentFilter(new AlphaNumericFilter(15));
        jLabel2 = new javax.swing.JLabel();
        documentClass = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tokens = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        displayName = new javax.swing.JTextField();
        ((AbstractDocument)this.displayName.getDocument()).setDocumentFilter(new AlphaNumericFilter(15));
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cancel = new javax.swing.JButton();
        ok = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        defaultExtension = new javax.swing.JTextField();
        ((AbstractDocument)this.defaultExtension.getDocument()).setDocumentFilter(new AlphaNumericFilter(15));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("settings/ui/Bundle"); // NOI18N
        jLabel1.setText(bundle.getString("AddDocumentType.jLabel1.text")); // NOI18N

        documentType.setText(bundle.getString("AddDocumentType.documentType.text")); // NOI18N

        jLabel2.setText(bundle.getString("AddDocumentType.jLabel2.text")); // NOI18N

        documentClass.setText(bundle.getString("AddDocumentType.documentClass.text")); // NOI18N
        documentClass.setToolTipText(bundle.getString("AddDocumentType.documentClass.toolTipText")); // NOI18N

        jLabel3.setText(bundle.getString("AddDocumentType.jLabel3.text")); // NOI18N

        tokens.setColumns(20);
        tokens.setRows(5);
        jScrollPane1.setViewportView(tokens);

        jLabel4.setText(bundle.getString("AddDocumentType.jLabel4.text")); // NOI18N

        displayName.setText(bundle.getString("AddDocumentType.displayName.text")); // NOI18N

        jLabel5.setText(bundle.getString("AddDocumentType.jLabel5.text")); // NOI18N

        jLabel6.setText(bundle.getString("AddDocumentType.jLabel6.text")); // NOI18N

        cancel.setText(bundle.getString("AddDocumentType.cancel.text")); // NOI18N
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        ok.setText(bundle.getString("AddDocumentType.ok.text")); // NOI18N
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        jLabel7.setText(bundle.getString("AddDocumentType.jLabel7.text")); // NOI18N

        defaultExtension.setText(bundle.getString("AddDocumentType.defaultExtension.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(documentType, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(10, 10, 10))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(documentClass))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(defaultExtension, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(displayName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(39, 39, 39))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ok)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancel)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {displayName, documentType});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(documentType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(defaultExtension, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(documentClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel)
                    .addComponent(ok))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {defaultExtension, displayName, documentType});

    }// </editor-fold>//GEN-END:initComponents

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        try {
            // All fields are mandatory.
            String docTypeName = this.documentType.getText();
            String docDisplayName = this.displayName.getText();
            String docDefaultExtension = this.defaultExtension.getText();
            String docClass = this.documentClass.getText();
            String tokensList = this.tokens.getText();
            validateDocumentTypeName(docTypeName);
            validateDisplayName(docDisplayName);
            validateDefaultExtension(docDefaultExtension);
            validateDocumentClass(docClass);
            validateTokens(tokensList);
            IDocumentType customDocType = new DocumentType(docTypeName,
                    docDisplayName,
                    docDefaultExtension, docClass,
                    ConfigurationUtility.getListFromString(tokensList, ","),
                    true);
            //this.docTypesConfig.getDocumentTypes().add(customDocType);
            this.docTypesConfig.addDocumentType(customDocType);
            this.docTypesList.setModel(UIConfigurationUtility.getListModel(this.docTypesConfig.
                    getDocumentTypes()));
            this.docTypesList.setSelectedIndex(0);
            closeWindow();
        } catch (InvalidSettingsParameterException ex) {
            SettingsMessageManager.showErrorMessage(this,
                    ex.getErrorCode(),
                    ex.getPlaceHolders());
            this.documentType.requestFocus();
            this.documentType.selectAll();
        }
    }//GEN-LAST:event_okActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        closeWindow();
    }//GEN-LAST:event_cancelActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JTextField defaultExtension;
    private javax.swing.JTextField displayName;
    private javax.swing.JTextField documentClass;
    private javax.swing.JTextField documentType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton ok;
    private javax.swing.JTextArea tokens;
    // End of variables declaration//GEN-END:variables
}
