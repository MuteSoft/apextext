/*
 * AddCustomTool.java
 * Created on December 12, 2007, 4:19 PM
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

import org.apex.base.data.AlphaNumericFilter;
import org.apex.base.data.AlphaNumericOrSpaceFilter;
import org.apex.base.data.CustomTool;
import org.apex.base.settings.CustomToolConfiguration;
import org.apex.base.settings.InvalidSettingsParameterException;
import org.apex.base.settings.SettingsMessageManager;
import org.apex.base.settings.ui.text.UIConfigurationUtility;
import org.apex.base.util.EditorUtil;
import org.apex.base.util.StringUtil;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.text.AbstractDocument;
import org.apex.base.event.FolderBrowserEventHandler;

/**
 * A form to add a custom tool. To add a custom tool a tool Id, name, executable etc
 * need to be entered by user. A custom tool can be executed in the editor to run external
 * command line and GUI based tools.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class AddCustomTool extends CustomToolChangeSupport {

    /**
     * Creates new form {@code AddCustomTool} using specified custom tool configuration and
     * a list which displays all custom tools.
     * @param customToolConfig Custom tool configuration.
     * @param toolList A list which displays all available custom tools.
     */
    public AddCustomTool(CustomToolConfiguration customToolConfig,
                         JList toolList) {
        super(customToolConfig, null, toolList);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        inputParamReqd = new javax.swing.JCheckBox();
        toolDescription = new javax.swing.JTextField();
        ((AbstractDocument)this.toolDescription.getDocument()).setDocumentFilter(new AlphaNumericOrSpaceFilter(50));
        toolExecutable = new javax.swing.JTextField();
        toolMnemonic = new javax.swing.JTextField();
        ((AbstractDocument)this.toolMnemonic.getDocument()).setDocumentFilter(new AlphaNumericFilter(1));
        browseExecutable = new javax.swing.JButton();
        toolName = new javax.swing.JTextField();
        ((AbstractDocument)this.toolName.getDocument()).setDocumentFilter(new AlphaNumericOrSpaceFilter(30));
        jLabel1 = new javax.swing.JLabel();
        ok = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        toolId = new javax.swing.JTextField();
        ((AbstractDocument)this.toolId.getDocument()).setDocumentFilter(new AlphaNumericFilter(6));
        jLabel5 = new javax.swing.JLabel();
        workingDir = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        browseWorkingDir = new javax.swing.JButton();
        browseWorkingDir.addActionListener(new FolderBrowserEventHandler(this.workingDir));
        defaultOptions = new javax.swing.JTextField();
        defaultInputParams = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        jLabel2.setText("Name:");

        jLabel3.setText("Description:");

        jLabel6.setText("Executable:");

        jLabel7.setText("Mnemonic:");

        inputParamReqd.setText("At runtime dynamic options and parameters required");

        browseExecutable.setText("Browse");
        browseExecutable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseExecutableActionPerformed(evt);
            }
        });

        jLabel1.setText(" ");

        ok.setText("Ok");
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

        jLabel4.setText("Id:");

        jLabel5.setText("Working Directory:");

        workingDir.setEditable(false);

        jLabel8.setText("Options:");

        jLabel9.setText("Input Parameters:");

        browseWorkingDir.setText("Browse");

        jLabel11.setText(EditorUtil.getMultipleOptionsNote());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(inputParamReqd)
                    .addComponent(jLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addComponent(toolMnemonic, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ok)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancel)
                        .addGap(8, 8, 8))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(defaultOptions, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(defaultInputParams, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(workingDir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(toolDescription, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(toolId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(toolName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(toolExecutable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(browseExecutable)
                            .addComponent(browseWorkingDir))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(toolId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(toolName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(toolDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(toolExecutable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseExecutable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(workingDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseWorkingDir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(defaultOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(defaultInputParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(toolMnemonic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inputParamReqd)
                        .addGap(34, 34, 34))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ok)
                            .addComponent(cancel))
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents
    private void browseExecutableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseExecutableActionPerformed
        JFileChooser fileChooser =
                new JFileChooser();
        fileChooser.setDragEnabled(true);
        fileChooser.setMultiSelectionEnabled(false);
        // Add file Filters        
        fileChooser.showOpenDialog(null);
        File openFile = fileChooser.getSelectedFile();
        if (openFile != null) {
            this.toolExecutable.setText(openFile.getAbsolutePath());
        }
    }//GEN-LAST:event_browseExecutableActionPerformed

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        String id = this.toolId.getText();
        String name = this.toolName.getText();
        String description = this.toolDescription.getText();
        if (StringUtil.isNullOrEmpty(description)) {
            description = name;
        }
        String executable = this.toolExecutable.getText();
        String mnemonic = this.toolMnemonic.getText();
        boolean inputRequired = this.inputParamReqd.isSelected();
        //boolean consoleReqd = this.externalConsoleReqd.isSelected();
        try {
            validateId(id);
            validateName(name);
            //validateExecutable(executable);
            CustomTool newTool = new CustomTool(id, name, executable);
            newTool.setDescription(description);
            newTool.setWorkingDir(this.workingDir.getText());
            newTool.setOptions(this.defaultOptions.getText());
            newTool.setParams(this.defaultInputParams.getText());
            //newTool.setExternalConsoleRequired(consoleReqd);
            newTool.setRuntimeParamRequired(inputRequired);
            if (!StringUtil.isNullOrEmpty(mnemonic)) {
                newTool.setMnemonic(mnemonic.charAt(0));
            }
            this.customToolConfig.getCustomTools().add(newTool);
            this.toolList.setModel(UIConfigurationUtility.getToolsListModel(this.customToolConfig.
                    getCustomTools()));
            this.toolList.setSelectedIndex(0);
            closeWindow();
        } catch (InvalidSettingsParameterException ispe) {
            SettingsMessageManager.showErrorMessage(this, ispe.getErrorCode(),
                    ispe.getPlaceHolders());
            this.toolId.requestFocus();
        }
    }//GEN-LAST:event_okActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        closeWindow();
    }//GEN-LAST:event_cancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseExecutable;
    private javax.swing.JButton browseWorkingDir;
    private javax.swing.JButton cancel;
    private javax.swing.JTextField defaultInputParams;
    private javax.swing.JTextField defaultOptions;
    private javax.swing.JCheckBox inputParamReqd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton ok;
    private javax.swing.JTextField toolDescription;
    private javax.swing.JTextField toolExecutable;
    private javax.swing.JTextField toolId;
    private javax.swing.JTextField toolMnemonic;
    private javax.swing.JTextField toolName;
    private javax.swing.JTextField workingDir;
    // End of variables declaration//GEN-END:variables
}
