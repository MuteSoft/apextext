/*
 * GoToFileView.java
 * Created on 18 November, 2007, 12:47 AM
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
package org.apex.base.search.ui;

import org.apex.base.data.EditorContext;
import org.apex.base.ui.text.UIDialogModel;
import org.apex.base.component.ApexLabel;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import org.apex.base.core.EditorBase;
import org.apex.base.search.SearchFile;

/**
 * A form where user can type in few characters to navigate to a document. Based on input
 * text it shows a list of matching documents. From the list user can select a document to
 * make it visible.
 * <p>
 * Note that it performs incremental search.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GoToFileView extends javax.swing.JPanel implements UIDialogModel {

    /**
     * The container dialog window.
     */
    private JDialog containerWindow;
    /**
     * A file search helper.
     */
    private SearchFile searchFile;

    /** 
     * Creates new form {@code GoToFileView}. It initializes components and also
     * initializes the goto file event handler.
     */
    public GoToFileView() {
        this.searchFile = new SearchFile();
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        foundList = new javax.swing.JList();
        this.foundList.setCellRenderer(new org.apex.base.component.EditorListCellRenderer());
        ok = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        filePath = new javax.swing.JTextField();

        jLabel1.setText("Name (case insensitive and allows wildcards \"*\",\"?\") :");

        name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameKeyReleased(evt);
            }
        });

        jLabel2.setText("Found:");

        foundList.setModel(this.searchFile.getFileListModel());
        foundList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                foundListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(foundList);

        ok.setText("Ok");
        ok.setEnabled(false);
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        jLabel3.setText("Location:");

        filePath.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ok)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancel))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filePath, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancel, ok});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(filePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ok)
                    .addComponent(cancel))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        performGoToFile();
}//GEN-LAST:event_okActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        this.getContainerWindow().setVisible(false);
    }//GEN-LAST:event_cancelActionPerformed

    private void nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameKeyReleased
        if (!evt.isActionKey()) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                performGoToFile();
            } else {
                this.foundList.setModel(this.searchFile.getFileListModel(this.searchFile.
                        filterFileList(this.name.getText())));
                if (this.foundList.getModel().getSize() > 0) {
                    this.ok.setEnabled(true);
                    this.foundList.setSelectedIndex(0);
                } else {
                    this.ok.setEnabled(false);
                }
            }
        } else {
            if (!(this.foundList.getModel().getSize() > 1)) {
                return;
            }
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (this.foundList.getSelectedIndex() - 1 < 0) {
                        this.foundList.setSelectedIndex(this.foundList.getModel().
                                getSize() - 1);
                    } else {
                        this.foundList.setSelectedIndex(this.foundList.
                                getSelectedIndex() - 1);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (this.foundList.getSelectedIndex() + 1
                            >= this.foundList.getModel().getSize()) {
                        this.foundList.setSelectedIndex(0);
                    } else {
                        this.foundList.setSelectedIndex(this.foundList.
                                getSelectedIndex() + 1);
                    }
                    break;
            }
        }
    }//GEN-LAST:event_nameKeyReleased

private void foundListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_foundListValueChanged
    if (evt.getValueIsAdjusting()) {
        return;
    }
    if (this.foundList.getSelectedValue() == null) {
        this.filePath.setText(null);
        this.searchFile.getModel().setSelectedFileName(null);
        this.searchFile.getModel().setSelectedFilePath(null);
    } else {
        this.filePath.setText(((ApexLabel) this.foundList.getSelectedValue()).
                getToolTipText());
        this.searchFile.getModel().setSelectedFileName(((ApexLabel) this.foundList.
                getSelectedValue()).getText());
        this.searchFile.getModel().setSelectedFilePath(((ApexLabel) this.foundList.
                getSelectedValue()).getToolTipText());
    }
}//GEN-LAST:event_foundListValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JTextField filePath;
    private javax.swing.JList foundList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name;
    private javax.swing.JButton ok;
    // End of variables declaration//GEN-END:variables

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
     * Navigates to selected file.
     */
    private void performGoToFile() {
        this.searchFile.performGoToFile();
        this.getContainerWindow().setVisible(false);
    }
}