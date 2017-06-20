/*
 * CustomToolChangeSupport.java
 * Created on December 12, 2007, 04:15 PM
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

import org.apex.base.constant.CommonConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.data.CustomTool;
import org.apex.base.data.EditorContext;
import org.apex.base.settings.CustomToolConfiguration;
import org.apex.base.settings.InvalidSettingsParameterException;
import org.apex.base.ui.text.UIDialogModel;
import org.apex.base.util.StringUtil;
import java.io.File;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * A helper class for add and edit custom tool UI pages.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CustomToolChangeSupport extends JPanel implements UIDialogModel {

    /**
     * Custom tool configuration.
     */
    protected CustomToolConfiguration customToolConfig;
    /**
     * The selected custom tool.
     */
    protected CustomTool selectedTool;
    /**
     * A list where custom tools are displayed.
     */
    protected JList toolList;
    /**
     * The container dialog window.
     */
    protected JDialog containerWindow;

    /**
     * Creates a new instance of {@code CustomToolChangeSupport} using specified
     * custom tool configuration, selected custom tool and a list where custom tools are
     * displayed.
     * @param customToolConfig Custom tool configuration.
     * @param selectedTool The selected custom tool.
     * @param toolList A list where custom tools are displayed.
     */
    public CustomToolChangeSupport(CustomToolConfiguration customToolConfig,
            CustomTool selectedTool, JList toolList) {
        this.customToolConfig = customToolConfig;
        this.selectedTool = selectedTool;
        this.toolList = toolList;
    }

    /**
     * Closes the dialog window.
     */
    protected void closeWindow() {
        if (getContainerWindow() != null) {
            getContainerWindow().setVisible(false);
        }
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
     * Returns the selected custom tool.
     * @return The selected custom tool.
     */
    protected CustomTool getSelectedTool() {
        return this.selectedTool;
    }

    /**
     * Returns a list of available custom tools.
     * @return A list of available custom tools.
     */
    protected Vector<CustomTool> getCustomTools() {
        return this.customToolConfig.getCustomTools();
    }

    /**
     * Returns the display text for selected tool's mnemonic.
     * @return The display text for tool mnemonic.
     */
    protected String getToolMnemonic() {
        return (this.selectedTool == null || this.selectedTool.getMnemonic() ==
                ' ')
                ? CommonConstants.BLANK_TEXT
                : String.valueOf(this.selectedTool.getMnemonic());
    }

    /**
     * Returns the display text for working directory of selected tool.
     * @return The display text for working directory of selected tool.
     */
    protected String getToolWorkingDirectory() {
        return (this.selectedTool == null ||
                this.selectedTool.getWorkingDir() == null)
                ? CommonConstants.BLANK_TEXT
                : this.selectedTool.getWorkingDir();
    }

    /**
     * Returns the display text for runtime options of selected tool.
     * @return The display text for runtime options of selected tool.
     */
    protected String getOptions() {
        return (this.selectedTool == null || this.selectedTool.getOptions() ==
                null)
                ? CommonConstants.BLANK_TEXT
                : this.selectedTool.getOptions();
    }

    /**
     * Returns the display text for runtime input parameters of tool.
     * @return The display text for runtime input parameters of tool.
     */
    protected String getInputParams() {
        return (this.selectedTool == null || this.selectedTool.getParams() ==
                null)
                ? CommonConstants.BLANK_TEXT
                : this.selectedTool.getParams();
    }

    /**
     * Returns the display text for selected tool's executable.
     * @return The display text for selected tool's executable.
     */
    protected String getToolExecutable() {
        return (this.selectedTool == null ||
                this.selectedTool.getExecutable() == null)
                ? CommonConstants.BLANK_TEXT
                : this.selectedTool.getExecutable();
    }

    /**
     * Returns the display text for selected tool's description.
     * @return The display text for selected tool's description.
     */
    protected String getToolDescription() {
        return (this.selectedTool == null ||
                this.selectedTool.getDescription() == null)
                ? CommonConstants.BLANK_TEXT
                : this.selectedTool.getDescription();
    }

    /**
     * Returns the display text for selected tool's name.
     * @return The display text for selected tool's name.
     */
    protected String getToolName() {
        return (this.selectedTool == null || this.selectedTool.getName() == null)
                ? CommonConstants.BLANK_TEXT
                : this.selectedTool.getName();
    }

    /**
     * Returns the display text for selected tool's Id.
     * @return The display text for selected tool's Id.
     */
    protected String getToolId() {
        return (this.selectedTool == null || this.selectedTool.getId() == null)
                ? CommonConstants.BLANK_TEXT
                : this.selectedTool.getId();
    }

    /**
     * Returns a boolean that indicates whether or not user has to be prompted
     * for input parameters while executing the selected tool.
     * @return {@code true} if user has to be prompted for input parameters
     *               while executing the selected tool; otherwise returns {@code false}.
     */
    protected boolean isInputParamRequired() {
        return this.selectedTool == null
                ? false
                : this.selectedTool.isRuntimeParamRequired();
    }

    /**
     * Returns a boolean that indicates whether or not external console to be used
     * while executing the selected tool.
     * @return {@code true} if external console to be used
     *               while executing the selected tool; otherwise returns {@code false}.
     */
    protected boolean isExternalConsoleRequired() {
        return this.selectedTool == null
                ? false
                : this.selectedTool.isExternalConsoleRequired();
    }

    /**
     * Validates tool id.
     * @param id Tool Id.
     * @throws InvalidSettingsParameterException If given tool Id is null or blank or a duplicate one.
     */
    public void validateId(String id) throws InvalidSettingsParameterException {
        // Check for null or blank value
        if (StringUtil.isNullOrEmpty(id)) {
            throw new InvalidSettingsParameterException("Blank Id.", 1006);
        }
        id = id.trim();
        // Check for duplicate id
        Vector<CustomTool> tools = getCustomTools();
        for (CustomTool tool : tools) {
            if (id.equalsIgnoreCase(tool.getId())) {
                throw new InvalidSettingsParameterException("Id already exists.",
                        1007,
                        "TOOL_ID='" + id + "'");
            }
        }
        // Fix for bug id 2128943 (sourceforge.net)
        // Verify whether this 'id' exists in editor menu list. In editor menu ids
        // are unique
        if (getContext().getCoreMenus().getMenus().findChildMenuById(id) != null) {
            throw new InvalidSettingsParameterException("Id already exists.",
                    1007,
                    "TOOL_ID='" + id + "'");
        }
    }

    /**
     * Validates tool name.
     * @param name Tool name.
     * @throws InvalidSettingsParameterException If given tool name is null or blank or a duplicate one.
     */
    public void validateName(String name) throws
            InvalidSettingsParameterException {
        // Check for null or blank value
        if (StringUtil.isNullOrEmpty(name)) {
            throw new InvalidSettingsParameterException("Blank name.", 1008);
        }
        name = name.trim();
        if (name.equalsIgnoreCase(getToolName())) {
            return;
        }
        // Check for duplicate name
        Vector<CustomTool> tools = getCustomTools();
        for (CustomTool tool : tools) {
            if (name.equalsIgnoreCase(tool.getName())) {
                throw new InvalidSettingsParameterException(
                        "Name already exists.",
                        1009,
                        "TOOL_NAME='" + name + "'");
            }
        }
    }

    /**
     * Validates whether given executable is a valid file.
     * Allow commands to be executed and at the same time validate
     * strictly if given executable is a file.
     * @param executable The executable entered by user.
     * @throws InvalidSettingsParameterException If given executable is null or blank or invalid.
     */
    public void validateExecutable(String executable) throws
            InvalidSettingsParameterException {
        // Check for null or blank value
        if (StringUtil.isNullOrEmpty(executable)) {
            throw new InvalidSettingsParameterException("Blank executable.",
                    1010);
        }
        executable = executable.trim();
        // Check existence of given executable file
        if (executable.indexOf('.') == -1) {
            // Not a file and must be an executable command
            return;
//            throw new InvalidSettingsParameterException("Invalid executable.",
//                    1011);
        }
        String extension = executable.substring(executable.lastIndexOf('.') + 1);
        if (!getContext().getEditorProperties().getDocumentTypeBase().isExecutable(extension)) {
            throw new InvalidSettingsParameterException("Invalid executable.",
                    1011);
        }
        File execFile = new File(executable);
        if (!execFile.exists()) {
            throw new InvalidSettingsParameterException("Invalid executable.",
                    1012);
        }
    }
}