/*
 * Console.java 
 * Created on 5 Jan, 2010, 11:07:41 PM
 *
 * Copyright (C) 2009 Mrityunjoy Saha
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
package org.apex.base.component;

import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import org.apex.base.core.EditorBase;
import org.apex.base.data.Command;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.function.AbstractCommandProcessor;
import org.apex.base.logging.Logger;

/**
 * Editor console. This is used when a tool is executed and some information has to
 * be displayed to user.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class Console {

    /**
     * The result area.
     */
    private ApexTextPane resultArea;
    /**
     * The associated command.
     */
    private Command command;
    /**
     * The result area scroll pane.
     */
    private ApexScrollPane consoleScrollPane;
    /**
     * A boolean that indicates whether or not the associated process is running.
     */
    private boolean running;
    /**
     * The process currently running on console. As of now we have one console.      
     */
    private Process process;
    /**
     * The console title.
     */
    private String title;

    /**
     * Constructs a new console with given title.
     * @param title The console title.
     */
    public Console(String title) {
        this.title = title;
        this.resultArea = new ApexTextPane(new DefaultStyledDocument());
        this.resultArea.setTabSize(4);
        this.consoleScrollPane = new ApexScrollPane(this.resultArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.consoleScrollPane.getVerticalScrollBar().setUnitIncrement(20);
    }

    /**
     * Returns the associated command.
     * @return The associated command.
     * @see #setCommand(org.apex.base.data.Command)
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Sets the associated command.
     * @param command The command to be associated.
     * @see #getCommand()
     */
    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * Returns the scrollpane of the console.
     * @return The scrollpane of the console.
     * @see #setConsoleScrollPane(org.apex.base.component.ApexScrollPane)
     */
    public ApexScrollPane getConsoleScrollPane() {
        return consoleScrollPane;
    }

    /**
     * Sets the scrollpane of the console.
     * @param consoleScrollPane The scrollpane of the console.
     * @see #getConsoleScrollPane()
     */
    public void setConsoleScrollPane(ApexScrollPane consoleScrollPane) {
        this.consoleScrollPane = consoleScrollPane;
    }

    /**
     * Returns the result area of the console.
     * @return The result area of the console.
     * @see #setResultArea(org.apex.base.component.ApexTextPane)
     */
    public ApexTextPane getResultArea() {
        return resultArea;
    }

    /**
     * Sets the result area of the console.
     * @param resultArea The result area of the console.
     * @see #getResultArea()
     */
    public void setResultArea(ApexTextPane resultArea) {
        this.resultArea = resultArea;
    }

    /**
     * Returns a boolean indicating whether or not the current process is running.
     * @return {@code true} if the associated process is running; otherwise returns {@code false}.
     */
    public boolean isProcessRunning() {
        return this.process != null && this.running;
    }

    /**
     * Stops the associated process.
     */
    public void stopProcess() {
        if (this.process != null) {
            this.process.destroy();
            setProcess(null);
        }
    }

    /**
     * Restarts the process if it is running currently. Else it starts the process.
     */
    public void restartProcess() {
        if (this.process != null) {
            stopProcess();
        }
        startProcess();
    }

    /**
     * Starts the associated process. It returns immediately if the process
     * is running currently.
     */
    public void startProcess() {
        if (isProcessRunning()) {
            return;
        }
        if (this.command != null) {
            // Execute the command.
            CommandProcessor processor = new CommandProcessor();
            processor.process(new InputParams(), new OutputParams());
        }
    }

    /**
     * Returns the associated process.
     * @return The associated current process.
     * @see #setProcess(java.lang.Process)
     */
    public Process getProcess() {
        return process;
    }

    /**
     * Sets the current process.
     * @param process A process.
     * @see #getProcess()
     */
    public void setProcess(Process process) {
        this.running = (process == null
                ? false
                : true);
        EditorBase.getContext().getEditorComponents().getEditorBody().
                getOutputWindow().enableConsoleOptionButtons(!this.running);
        this.process = process;
    }

    /**
     * Releases the resources.
     */
    public void release() {
        this.command = null;
        this.consoleScrollPane = null;
        this.resultArea = null;
        stopProcess();
    }

    /**
     * Clears the result area.
     */
    public void clearResultArea() {
        try {
            getResultArea().getDocument().
                    remove(0,
                    getResultArea().getDocument().
                    getLength());
        } catch (BadLocationException ex) {
            Logger.logError(
                    "Failed to clear the console", ex);
        }
    }

    @Override
    public String toString() {
        if (this.command == null) {
            return title;
        }
        return title + " - " + this.command.getToolName();
    }

    /**
     * A default command processor.
     */
    private class CommandProcessor extends AbstractCommandProcessor {

        @Override
        protected Command getCommand() {
            return Console.this.command;
        }

        @Override
        protected String[] getBaseCommand() {
            return null;
        }

        @Override
        protected String[] getOptions() {
            return null;
        }

        @Override
        protected String[] getResources() {
            return null;
        }

        @Override
        protected String[] getParams() {
            return null;
        }

        @Override
        protected String getWorkingDirectory() {
            return null;
        }

        @Override
        protected String getName() {
            return getCommand().getToolName();
        }

        @Override
        protected Console getResultArea() {
            return Console.this;
        }
    }
}
