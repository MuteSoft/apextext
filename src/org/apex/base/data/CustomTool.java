/*
 * CustomTool.java
 * Created on December 12, 2007, 6:10 PM
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
package org.apex.base.data;

import org.apex.base.constant.CommonConstants;																			
import org.apex.base.util.StringUtil;

/**
 * Represents a Custom Tool added by user.
 * A custom tool can be executed in the editor to run external
 * command line and GUI based tools.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CustomTool extends ProvidedTool implements Cloneable {

    /**
     * The menu target class for all custom tools.
     */
    private static final String TARGET = "org.apex.base.menu.CustomToolMenu";
    /**
     * The tool description.
     */
    private String description;
    /**
     * The tool executable.
     */
    private String executable;
    /**
     * Runtime working directory.
     */
    private String workingDir;
    /**
     * Tool mnemonic. It is useful when this tool appears as menu in the editor.
     */
    private char mnemonic = ' ';

    /**
     * Constructs a tool with given Id, name and executable.     
     * @param id Tool Id.
     * @param name Tool name.
     * @param executable Tool executable.
     */
    public CustomTool(String id, String name, String executable) {
        super(id, name);
        this.executable = executable;
    }

    /**
     * Returns the mnemonic.
     * <p>
     * It is useful when this tool appears as menu in the editor.
     * @return Tool mnemonic.
     * @see #setMnemonic(char) 
     */
    public char getMnemonic() {
        return mnemonic;
    }

    /**
     * Sets the mnemonic.
     * @param mnemonic Tool mnemonic.
     * @see #getMnemonic() 
     */
    public void setMnemonic(char mnemonic) {
        this.mnemonic = mnemonic;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CustomTool)) {
            return false;
        }
        CustomTool givenTool = (CustomTool) obj;
        return super.equals(obj) && (this.description == null
                ? givenTool.getDescription() == null
                : this.description.equals(givenTool.getDescription())) &&
                (this.executable == null
                ? givenTool.getExecutable() == null
                : this.executable.equals(givenTool.getExecutable())) &&
                (this.workingDir == null
                ? givenTool.getWorkingDir() == null
                : this.workingDir.equals(givenTool.getWorkingDir())) &&
                (this.mnemonic == givenTool.getMnemonic());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.description != null
                ? this.description.hashCode()
                : 0);
        hash = 89 * hash +
                (this.executable != null
                ? this.executable.hashCode()
                : 0);
        hash = 89 * hash +
                (this.workingDir != null
                ? this.workingDir.hashCode()
                : 0);
        hash = 89 * hash + this.mnemonic;
        return super.hashCode() + hash;
    }

    @Override
    public Object clone() {
        return (CustomTool) super.clone();
    }

    @Override
    public String toString() {
        return (getName() == null
                ? CommonConstants.BLANK_TEXT
                : getName()) + INTRA_SEPARATOR +
                (getExecutable() == null
                ? CommonConstants.BLANK_TEXT
                : getExecutable()) + INTRA_SEPARATOR +
                (getDescription() == null
                ? CommonConstants.BLANK_TEXT
                : getDescription()) + INTRA_SEPARATOR +
                (getWorkingDir() == null
                ? CommonConstants.BLANK_TEXT
                : getWorkingDir()) + INTRA_SEPARATOR +
                (getOptions() == null
                ? CommonConstants.BLANK_TEXT
                : getOptions()) + INTRA_SEPARATOR +
                (getParams() == null
                ? CommonConstants.BLANK_TEXT
                : getParams()) + INTRA_SEPARATOR +
                getMnemonic() + INTRA_SEPARATOR +
                //isExternalConsoleRequired() + INTRA_SEPARATOR +
                isRuntimeParamRequired() + INTRA_SEPARATOR;
    }

    /**
     * Constructs a menu node from tool properties and returns.
     * @return The menu node.
     */
    public MenuNode createAndFetchMenuNode() {
        if (StringUtil.isNullOrEmpty(getName()) ||
                StringUtil.isNullOrEmpty(getId())) {
            return null;
        }
        MenuNode node = new MenuNode(getName(), getId(), MenuType.CUSTOM_TOOL);
        node.setDescription(getDescription());
        node.setMnemonic(this.mnemonic);
        node.setTarget(TARGET);
        return node;
    }

    /**
     * Returns the working directory.
     * @return working directory.
     * @see #setWorkingDir(java.lang.String) 
     */
    public String getWorkingDir() {
        return this.workingDir;
    }

    /**
     * Sets the working directory.
     * @param workingDir Working directory.
     * @see #getWorkingDir() 
     */
    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    @Override
    public String getExecutable() {
        return this.executable;
    }

    @Override
    public void setExecutable(String executable) {
        this.executable = executable;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void dispose() {
        super.dispose();
        this.description = null;
        this.executable = null;
        this.workingDir = null;
    }
}
