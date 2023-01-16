/*
 * ProvidedTool.java 
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
import org.apex.base.logging.Logger;

/**
 * Tool provided with editor by default.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ProvidedTool implements Tool {

    /**
     * Tool Id.
     */
    private String id;
    /**
     * Tool name.
     */
    private String name;
    /**
     * Runtime options.
     */
    private String options;
    /**
     * Runtime parameters.
     */
    private String params;
    /**
     * A flag which indicates whether at runtime options and parameters change
     * required.
     */
    private boolean runtimeParamReqd;
    /**
     * A flag which indicates whether to run this tool in an external command prompt
     * or shell.
     */
    private boolean externalConsoleReqd = false;
    /**
     * A flag which indicates whether this toll should be visible in the editor.
     */
    private boolean visible;
    /**
     * The tool description.
     */
    private String description;

    /**
     * Constructs a tool with given Id and name.
     * @param id Tool Id.
     * @param name Tool name.
     */
    public ProvidedTool(String id, String name) {
        this.id = id;
        this.name = name;
        this.visible = true;
    }

    /**
     * Constructs a tool with given Id.
     * @param id Tool Id.
     */
    public ProvidedTool(String id) {
        this(id, null);
    }

    public String getDescription() {
        return this.description == null
                ? this.name
                : this.description;
    }

    public String getOptions() {
        return this.options;
    }

    public String getParams() {
        return this.params;
    }

    public boolean isRuntimeParamRequired() {
        return this.runtimeParamReqd;
    }

    public boolean isExternalConsoleRequired() {
        return this.externalConsoleReqd;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public void setRuntimeParamRequired(boolean runtimeParamReqd) {
        this.runtimeParamReqd = runtimeParamReqd;
    }

    public void setExternalConsoleRequired(boolean externalConsoleReqd) {
        this.externalConsoleReqd = externalConsoleReqd;
    }

    @Override
    public String toString() {
        return (getOptions() == null
                ? CommonConstants.BLANK_TEXT
                : getOptions()) + INTRA_SEPARATOR +
                (getParams() == null
                ? CommonConstants.BLANK_TEXT
                : getParams()) + INTRA_SEPARATOR +
                this.isRuntimeParamRequired() + INTRA_SEPARATOR +
                // this.isExternalConsoleRequired() + INTRA_SEPARATOR +
                this.isVisible();
    }

    @Override
    public Object clone() {
        try {
            ProvidedTool clonedTool = null;
            clonedTool = (ProvidedTool) super.clone();
            return clonedTool;
        } catch (CloneNotSupportedException ex) {
            Logger.logError("Failed to clone tool: " +
                    getClass().getName(), ex);
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ProvidedTool)) {
            return false;
        }
        ProvidedTool givenTool = (ProvidedTool) obj;
        return (this.id == null
                ? givenTool.getId() == null
                : this.id.equals(givenTool.getId())) &&
                (this.name == null
                ? givenTool.getName() == null
                : this.name.equals(givenTool.getName())) &&
                (this.options == null
                ? givenTool.getOptions() == null
                : this.options.equals(givenTool.getOptions())) &&
                (this.params == null
                ? givenTool.getParams() == null
                : this.params.equals(givenTool.getParams())) &&
                (this.runtimeParamReqd == givenTool.isRuntimeParamRequired()) &&
                (this.externalConsoleReqd ==
                givenTool.isExternalConsoleRequired()) &&
                (this.visible == givenTool.isVisible());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null
                ? this.id.hashCode()
                : 0);
        hash = 23 * hash +
                (this.name != null
                ? this.name.hashCode()
                : 0);
        hash = 23 * hash + (this.options != null
                ? this.options.hashCode()
                : 0);
        hash = 23 * hash + (this.params != null
                ? this.params.hashCode()
                : 0);
        hash = 23 * hash + (this.runtimeParamReqd
                ? 1
                : 0);
        hash = 23 * hash + (this.externalConsoleReqd
                ? 1
                : 0);
        hash = 23 * hash + (this.visible
                ? 1
                : 0);
        return hash;
    }

    public String getId() {
        return this.id;
    }

    public String getExecutable() {
        return null;
    }

    /**
     * For editor provided tool this member is not used.
     * @param executable The underlying executable.
     * @see #getExecutable() 
     */
    public void setExecutable(String executable) {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void dispose() {
        this.id = null;
        this.name = null;
        this.options = null;
        this.params = null;
    }
}
