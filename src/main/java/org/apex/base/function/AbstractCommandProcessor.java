/*
 * AbstractCommandProcessor.java
 * Created on 20 June, 2007, 11:42 PM
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
package org.apex.base.function;

import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.KeyStroke;
import org.apex.base.action.AutoIndentationAction;
import org.apex.base.action.ReadConsoleAction;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.ActionManager;
import org.apex.base.core.MenuManager;
import org.apex.base.data.Command;
import org.apex.base.logging.Logger;
import org.apex.base.util.EditorUtil;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.apex.base.component.Console;
import org.apex.base.util.FileUtil;

/**
 * The base class for all command processors and custom tool processors.
 * <p>
 * In order to execute a command or to process a tool, it creates a new {@code Process}
 * and processing result is directed to specified output window. By default result is sent
 * to editor console.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public abstract class AbstractCommandProcessor extends Function {

    /**
     * Output window where results to be directed.
     */
    private Console console;
    /**
     * A message to be displayed in output window when processing ends.
     * <p>
     * The processor name is appended before the message.
     */
    public static final String COMMAND_COMPLETION_MESG =
            "tool execution finished.";
    /**
     * A message to be displayed in output window when processing starts.
     * <p>
     * The processor name is appended before the message.
     */
    public static final String COMMAND_START_MESG =
            "tool executing...";
    /**
     * A boolean that indicates whether or not output window to be displayed.
     */
    private boolean resultAreaToBeShown = true;
    /**
     * The command to be executed.
     */
    protected Command command;
    /**
     * The style object for output results.
     */
    private static SimpleAttributeSet outputStyle = new SimpleAttributeSet();
    /**
     * The style object for error results.
     */
    private static SimpleAttributeSet errorStyle = new SimpleAttributeSet();
    /**
     * The style object for messages.
     */
    private static SimpleAttributeSet messageStyle = new SimpleAttributeSet();
    /**
     * Whether the given command execution is successful.
     */
    private boolean successful = true;

    /**
     * Creates a new instance of AbstractCommandProcessor.
     */
    static {
        StyleConstants.setForeground(outputStyle, Color.BLACK);
        StyleConstants.setForeground(errorStyle, Color.RED);
        StyleConstants.setForeground(messageStyle, Color.BLUE);
        //StyleConstants.setBold(messageStyle, true);
    }

    /**
     * Creates a new command processor.
     */
    public AbstractCommandProcessor() {
    }

    /**
     * Executes the command in a given working directory and
     * given environmental variables.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @SuppressWarnings("unchecked")
    protected void doExecute(InputParams in, OutputParams out) {
        if (!validateMandatoryData()) {
            return;
        }        
        try {
            Logger.logInfo("Executing tool: " + this.command);
            appendMessage(getCommandStartMessage());
            // Fix for bug id 2432839 (sourceforge.net)
            Process p =
                    Runtime.getRuntime().
                    exec(this.command.getCommandAsArray(),
                    this.command.getEnvpAsArray(),
                    (this.command.getWorkingDir() == null
                    || !new File(this.command.getWorkingDir()).exists())
                    ? null
                    : new File(this.command.getWorkingDir()));
            if (resultAreaToBeShown) {
                this.console.setProcess(p);
                this.console.setCommand(this.command);
                getContext().getEditorComponents().getEditorBody().
                        getOutputWindow().setTitle(this.command);
            }
            in.put("PROCESS", p);
        } catch (IOException ioe) {
            Logger.logError("IOException while executing: " + getName(),
                    ioe);
            appendError(ioe.getMessage());
        } catch (Throwable th) {
            Logger.logError("Exception while executing: " + getName(),
                    th);
            appendError(th.getMessage());
        }
    }

    /**
     * Reads the input (to program) stream and error stream from process and directs to
     * specified result area. If result area is not specified editor console is used 
     * by default.
     * <p>
     * Data from input (to program) stream and error stream
     * are pulled by separate threads. A pause is given after each pull operation.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void postExecute(InputParams in, OutputParams out) {
        final Process p = (Process) in.get("PROCESS");
        if (p == null) {
            appendMessage(getCommandCompletionMessage());
            return;
        }
        Thread error = new Thread() {

            @Override
            public void run() {
                BufferedReader errorStream = null;
                try {
                    String readData = "";
                    errorStream =
                            new BufferedReader(new InputStreamReader(p.
                            getErrorStream()));
                    while ((readData = errorStream.readLine()) != null) {
                        successful = false;
                        appendError(readData);
                        sleep(EditorKeyConstants.RESULT_AREA_MESSAGE_PULL_DELAY);
                    }
                } catch (IOException ex) {
                    Logger.logError(
                            "Error while reading error stream of process: " + p,
                            ex);
                } catch (Exception ex) {
                    Logger.logError(
                            "Error while reading output stream of process: " + p,
                            ex);
                } finally {
                    FileUtil.closeIOStream(errorStream);
                }
            }
        };
        error.start();
        Thread output = new Thread() {

            @Override
            public void run() {
                BufferedReader inStream = null;
                try {
                    String readData = "";
                    inStream =
                            new BufferedReader(new InputStreamReader(p.
                            getInputStream()));
                    while ((readData = inStream.readLine()) != null) {
                        appendOutput(readData);
                        sleep(EditorKeyConstants.RESULT_AREA_MESSAGE_PULL_DELAY);
                    }
                } catch (IOException ex) {
                    Logger.logError(
                            "Error while reading output stream of process: " + p,
                            ex);
                } catch (Exception ex) {
                    Logger.logError(
                            "Error while reading output stream of process: " + p,
                            ex);
                } finally {
                    FileUtil.closeIOStream(inStream);
                }
            }
        };
        output.start();
        // Register user input listeners Issue#3
        this.console.getResultArea().addKeyBoardAction("ReadConsoleLine", KeyStroke.getKeyStroke(
                KeyEvent.VK_ENTER,
                0),
            new ReadConsoleAction(p.getOutputStream()));
        // Ensure that output and error threads finished their execution.
        try {
            error.join();
            output.join();
        } catch (InterruptedException ex) {
            Logger.logError(
                    "Error while reading output and error streams of process: "
                    + p, ex);
        }
        appendMessage(getCommandCompletionMessage());
        if (resultAreaToBeShown) {
            this.console.setProcess(null);
        }
    }

    /**
     * Returns the process completion message to be displayed when a process ends.
     * @return The process completion message.
     */
    protected String getCommandCompletionMessage() {
        return "'" + getName() + "' " + COMMAND_COMPLETION_MESG;
    }

    /**
     * Appends error results to result area in output window.
     * @param data The text form of error data.
     */
    private void appendError(String data) {
        appendData(data, errorStyle);
    }

    /**
     * Appends messages to result area in output window.
     * @param data The text message.
     */
    private void appendMessage(String data) {
        appendData(data, messageStyle);
    }

    /**
     * Appends output results to result area in output window.
     * @param data The text form of output data.
     */
    private void appendOutput(String data) {
        appendData(data, outputStyle);
    }

    /**
     * Appends data to result area in output window.     
     * @param data The text to be appended.
     * @param attr The style to be applied to text.
     */
    private void appendData(final String data, final AttributeSet attr) {
        if (resultAreaToBeShown && console != null && console.getResultArea() != null) {
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    try {                        
                        console.getResultArea().getDocument().
                                insertString(
                                console.getResultArea().getDocument().getLength(),
                                data,
                                attr);
                        console.getResultArea().getDocument().
                                insertString(
                                console.getResultArea().getDocument().getLength(),
                                EditorUtil.getLineSeparator(), attr);
                    } catch (BadLocationException ex) {
                        Logger.logError(
                                "Failed to append data to console.",
                                ex);
                    }
                }
            });
        }
    }

    /**
     * Returns the object from where all attributes needed to run a process can be
     * extracted.
     * @return The {@code Command}.
     */
    protected abstract Command getCommand();

    /**
     * Returns base commands to be supplied to underlying process.
     * @return Base commands to be supplied to underlying process.
     */
    protected abstract String[] getBaseCommand();

    /**
     * Returns options to be supplied to underlying process.
     * @return Options to be supplied to underlying process.
     */
    protected abstract String[] getOptions();

    /**
     * Returns main resources to be supplied to underlying process.
     * @return Main resources.
     */
    protected abstract String[] getResources();

    /**
     * Returns parameters to be supplied to underlying process.
     * @return Parameters to be supplied to underlying process
     */
    protected abstract String[] getParams();

    /**
     * Returns the working directory of underlying process.
     * @return The working directory.
     */
    protected abstract String getWorkingDirectory();

    /**
     * Returns the default result area ' editor console'.
     * If output window containing result area is not visible currently, makes the
     * output window visible.
     * @return The result area.
     */
    protected Console getResultArea() {
        if (!getContext().getEditorComponents().getEditorBody().getOutputWindow().
                isVisible()) {
            ActionManager.setActionSelected(MenuConstants.OUTPUT_WINDOW, true);
            MenuManager.getMenuById(MenuConstants.OUTPUT_WINDOW).
                    processMenu(new InputParams(),
                    new OutputParams());
        }
        // Dont access by index, instead access by TITLE.
        return getContext().
                getEditorComponents().getEditorBody().
                getOutputWindow().createConsole();
    }

    /**
     * Returns the process start message to be displayed when a process ends.
     * @return The process strat message.
     */
    private String getCommandStartMessage() {
        return "'" + getName() + "' " + COMMAND_START_MESG;
    }

    /**
     * Validates mandatory objects required to start the process.
     * @return {@code true} if all validations pass; otherwise returns {@code false}.
     */
    private boolean validateMandatoryData() {
        // Validate command
        if (this.command == null) {
            return false;
        }
        return true;
    }

    /**
     * Initializes the output window and result area. When a process starts
     * the previous result area is cleared.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void init(InputParams in, OutputParams out) {
        // See whether a process is running
        resultAreaToBeShown =
                in.get("RESULT_AREA_TO_BE_SHOWN") == null
                ? true
                : (Boolean) in.get("RESULT_AREA_TO_BE_SHOWN");
        this.command = getCommand();
        this.command.setToolName(getName());
        if (resultAreaToBeShown) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    public void run() {
                        console = getResultArea();
                        console.clearResultArea();
                    }
                });
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean isSuccessful() {
        return this.successful;
    }
}
