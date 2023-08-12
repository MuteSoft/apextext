/*
 * Command.java
 * Created on 23 June, 2007, 12:06 AM
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A command object holds commands, working directory and environmental variables
 * required to run an executable.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class Command {

    /**
     * List of commands.
     */
    private List<String> command = new ArrayList<String>();
    /**
     * List of environmental variables.
     */
    private List<String> envp = new ArrayList<String>();
    /**
     * Inherited list of environment variables.
     * Initialize the list only when it is required.
     */
    // Fix for bug id 2432839 (sourceforge.net)
    private static List<String> inheritedEnvp = null;
    /**
     * Working directory of this executable {@code Command}.
     */
    private String workingDir;
    /**
     * The name of the tool.
     */
    private String toolName;

    /**
     * Constructs a new Command object with given commands.      
     * @param command Commands.
     */
    public Command(String... command) {
        this(command, null);
    }

    /**
     * Constructs a new Command object with given commands and 
     * working directory.
     * @param command Commands.
     * @param workingDir Working directory.
     */
    public Command(String[] command, String workingDir) {
        setCommand(command);
        this.workingDir = workingDir;
    }

    /**
     * Returns the list of commands associated with this executable.
     * @return List of commands.
     * @see #setCommand(java.lang.String[]) 
     */
    public List<String> getCommand() {
        return this.command;
    }

    /**
     * Returns list of commands as array.
     * @return Commands as array.
     */
    public String[] getCommandAsArray() {
        return getCommand().toArray(new String[]{});
    }

    /**
     * Returns the working directory of this executable {@code Command}.
     * @return Working directory.
     * @see #setWorkingDir(java.lang.String) 
     */
    public String getWorkingDir() {
        return workingDir;
    }

    /**
     * Sets the working directory of this executable {@code Command}.
     * @param workingDir Working directory.
     * @see #getWorkingDir() 
     */
    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    /**
     * Returns a list of environmental variables.
     * @return List of environmental variables.
     * @see #setEnvp(java.lang.String[]) 
     */
    public List<String> getEnvp() {
        return envp;
    }

    /**
     * Returns environmental variables as array.
     * If environment variables are added for the sub-process then the
     * inherited list is added to the returned list. Otherwise, null is returned
     * to inherit the default set of environment variables.
     * @return Environmental variables as array.
     */
    public String[] getEnvpAsArray() {
        List<String> defaultEnvp = getInheritedEnvp();        
        // Fix for bug id 2432839 (sourceforge.net)
        if (getEnvp() == null || getEnvp().isEmpty()) {
            return null;
        }
        //List<String> defaultEnvp = getInheritedEnvp();
        getEnvp().addAll(defaultEnvp);
        return getEnvp().toArray(new String[]{});
    }

    /**
     * Sets commands for this executable {@code Command}.
     * @param command An array of commands.
     * @see #getCommand() 
     */
    public void setCommand(String[] command) {
        if (command != null) {
            this.command.clear();
            addCommand(command);
        }
    }

    /**
     * Adds a single or multiple commands to the existing list of commands.
     * <p>
     * It appends specified commands at the end of existing list.
     * @param command One or multiple commands.
     */
    public void addCommand(String... command) {
        if (command != null) {
            for (int iCount = 0; iCount < command.length; iCount++) {
                if (command[iCount] != null) {
                    this.command.add(command[iCount]);
                }
            }
        }
    }

    /**
     * Sets environmental variables.
     * @param envp An array of environmental variables.
     * @see #getEnvp() 
     */
    public void setEnvp(String[] envp) {
        if (envp != null) {
            this.envp.clear();
            //this.envp.addAll(Arrays.asList(envp));
            for (int iCount = 0; iCount < envp.length; iCount++) {
                if (envp[iCount] != null) {
                    this.envp.add(envp[iCount]);
                }
            }
        }
    }

    /**
     * Returns a string representation of the object.
     * @return String representation of the object.
     */
    @Override
    public String toString() {
        return "command: " + this.command
                + "envp: " + this.envp
                + "workingdir: " + this.workingDir;
    }

    /**
     * Initialize the list of inherited list of environmental variables.
     * @return The list of environmental variables.
     */
    private List<String> getInheritedEnvp() {
        if (inheritedEnvp == null || inheritedEnvp.isEmpty()) {
            Map<String, String> defaultEnvp = System.getenv();
            inheritedEnvp = new ArrayList<String>(defaultEnvp.size());
            for (Iterator<String> envpIt = defaultEnvp.keySet().iterator();
                    envpIt.hasNext();) {
                String key = envpIt.next();
                inheritedEnvp.add(key + "=" + defaultEnvp.get(key));
            }
        }
        return inheritedEnvp;
    }

    /**
     * Returns the source tool name.
     * @return The tool name.
     * @see #setToolName(java.lang.String)
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * Sets the source tool name.
     * @param toolName The tool name.
     * @see #getToolName()
     */
    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    /**
     * Forms and returns the display string of this command.
     * @return The display string of this command.
     */
    public String toDisplayString() {
        return this.toolName;
    }
}
