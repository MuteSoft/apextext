/*
 * Tool.java 
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

/**
 * An interface for editor provided and custom tools.
 * <p>
 * A tool is an internal or external program which operates either independently or on documents
 * and produces result. 
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public interface Tool extends Cloneable {

    /**
     * A separator between multiple tool properties.
     */
    public static final String INTRA_SEPARATOR = ",";

    /**
     * Returns the tool Id.
     * <p>
     * Id is unique identifier of a tool.
     * @return The tool Id.
     */
    String getId();

    /**
     * Returns the tool description.
     * @return The tool description.
     * @see #setDescription(java.lang.String) 
     */
    String getDescription();

    /**
     * Returns the tool name.
     * @return The tool name.
     * @see #setName(java.lang.String) 
     */
    String getName();

    /**
     * Returns tool options.
     * <p>
     * Generally an option controls the behavior of tool execution.
     * @return Tool options.
     * @see #setOptions(java.lang.String) 
     */
    String getOptions();

    /**
     * Returns the underlying executable.
     * <p>
     * It can be an internal program name or external executable file name.
     * @return The underlying executable.
     * @see #setExecutable(java.lang.String) 
     */
    String getExecutable();

    /**
     * Returns tool parameters.
     * <p>
     * Parameters are passed to tool as arguments.
     * @return Tool parameters.
     * @see #setParams(java.lang.String) 
     */
    String getParams();

    /**
     * Returns whether to prompt for options and parameters change
     * while executing the tool.
     * @return {@code true} if options and parameters change required while executing the tool; otherwise
     *              returns {@code false}.
     */
    boolean isRuntimeParamRequired();

    /**
     * Returns whether to run this tool with an external command prompt or shell.
     * @return {@code true} if this tool should run with an external command prompt or shell.
     */
    boolean isExternalConsoleRequired();

    /**
     * Sets the tool description.
     * @param description The tool description.
     * @see #getDescription() 
     */
    void setDescription(String description);

    /**
     * Sets the tool name.
     * @param name The tool name.
     * @see #getName() 
     */
    void setName(String name);

    /**
     * Sets tool options.
     * @param options Tool options.
     * @see #getOptions() 
     */
    void setOptions(String options);

    /**
     * Sets the underlying executable.
     * @param executable The underlying executable.
     * @see #getExecutable() 
     */
    void setExecutable(String executable);

    /**
     * Sets tool parameters.
     * @param params Tool parameters.
     * @see #getParams() 
     */
    void setParams(String params);

    /**
     * Sets whether to prompt for options and parameters change
     * while executing the tool.
     * @param runtimeParamReqd Options and parameters change required indicator. 
     */
    void setRuntimeParamRequired(boolean runtimeParamReqd);

    /**
     * Sets whether to run this tool with an external command prompt or shell.
     * @param externalConsoleReqd External console required indicator.
     */
    void setExternalConsoleRequired(boolean externalConsoleReqd);

    /**
     * Returns the text representation of tool.
     * @return The text representation of tool.
     */
    @Override
    String toString();

    /**
     * Clones a tool.
     * @return The cloned tool.
     * @throws java.lang.CloneNotSupportedException 
     */
    Object clone() throws CloneNotSupportedException;

    /**
     * Checks whether given tool is same as (equals to) this tool.
     * @param tool The given tool.
     * @return {@code true} if equals; otherwise returns {@code false}.
     */
    @Override
    boolean equals( Object tool);

    /**
     * Returns the hash code.
     * @return The hash code.
     */
    @Override
    int hashCode();

    /**
     * Nullify the tool.
     */
    void dispose();

    /**
     * Returns whether the tool is visible in the editor.
     * @return {@code true} if tool is visible; otherwise returns {@code false}.
     */
    boolean isVisible();

    /**
     * Sets the visibility of tool in the application.
     * @param visible The visibility indicator.
     */
    void setVisible( boolean visible);
}
