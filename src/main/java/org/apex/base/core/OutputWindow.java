/*
 * OutputWindow.java
 * Created on 21 June, 2007, 12:27 AM
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
package org.apex.base.core;

import java.lang.reflect.InvocationTargetException;
import javax.swing.event.ChangeEvent;
import org.apex.base.data.EditorContext;
import org.apex.base.util.TabUtil;
import org.apex.base.component.ApexTabbedPane;
import org.apex.base.component.WindowTitleBar;
import org.apex.base.component.OutputTabComponent;
import org.apex.base.component.ApexPanel;
import org.apex.base.constant.MenuConstants;
import org.apex.base.component.ApexSplitPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.SwingUtilities;
import org.apex.base.component.AbstractIconButton;
import org.apex.base.component.ApexButton;
import org.apex.base.component.Console;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.data.Command;
import org.apex.base.event.BasicTabChangeListener;
import org.apex.base.util.ImageCreator;

/**
 * The output window holds console, search results etc.
 * <p>
 * After performing a task if display of result is necessary, then output window
 * appears dynamically on screen and result is appended to the console.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class OutputWindow extends ApexPanel {

    /**
     * The default title of output window.
     */
    public static final String OUTPUT_WINDOW_TITLE = "Output";
    /**
     * The tabbed pane where console, search results etc. tabs are added.
     */
    private ApexTabbedPane outputTabbedPane;
    /**
     * The list of active consoles and also it includes stopped but visible consoles.
     */
    private List<Console> consoles;
    /**
     * The one up sequence number for consoles.
     */
    private static int consoleSequence = 0;
    /**
     * Conatisn start, stop, clear buttons etc. It is common for all consoles.
     */
    private ConsoleOptions consoleOptions;
    /**
     * Output window tab icon.
     */
    private static final Icon OUTPUT_WINDOW_TAB_ICON = ImageCreator.
            createImageIcon(
            EditorBase.class, EditorKeyConstants.OUTPUT_WINDOW_TAB_ICON);
    /**
     * A boolean that indicates whether or not consoles to be reused.
     */
    private static boolean reuseConsole;
    /**
     * Output window title bar.
     */
    private WindowTitleBar titleBar;

    /**
     * Creates a new instance of {@code OutputWindow}.
     */
    public OutputWindow() {
        reuseConsole = getContext().getConfiguration().getGeneralConfig().
                getGeneral().isReuseConsole();
        createOutputWindow();
    }

    /**
     * Creates the output window.
     * <p>
     * Adds console tab to output window.
     */
    @SuppressWarnings("unchecked")
    private void createOutputWindow() {
        this.setLayout(new BorderLayout());
        this.consoles = new ArrayList<Console>(3);
        outputTabbedPane = new ApexTabbedPane(ApexTabbedPane.BOTTOM, false,
                false);

        // Create the title bar.
        titleBar = new WindowTitleBar(OUTPUT_WINDOW_TITLE) {

            protected void executeOnClick(ActionEvent e) {
                getContext().getEditorComponents().getEditorBody().
                        getOutputWindow().setVisible(
                        false);
                ActionManager.setActionSelected(MenuConstants.OUTPUT_WINDOW,
                        false);
            }
        };
        outputTabbedPane.addChangeListener(new BasicTabChangeListener() {

            @Override
            protected void doStateChanged(int selectedTabIndex, ChangeEvent e) {
                // Change the button status
                Console selectedConsole = getConsoleAt(selectedTabIndex);
                setTitle(selectedConsole.getCommand());
                enableConsoleOptionButtons(!selectedConsole.isProcessRunning());
            }
        });
        titleBar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    if (getParent() instanceof ApexSplitPane) {
                        ApexSplitPane splitPane = (ApexSplitPane) getParent();
                        if (splitPane.getDividerLocation() <= 100) {
                            splitPane.setDividerLocation((int) (EditorBase.
                                    getContext().
                                    getEditorComponents().
                                    getFrame().getHeight() * .65));

                        } else {
                            splitPane.setDividerLocation(100);
                        }
                    }
                }
            }
        });
        titleBar.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        this.consoleOptions = new ConsoleOptions();
        this.add(consoleOptions, BorderLayout.WEST);
        this.add(titleBar, BorderLayout.NORTH);
        this.add(outputTabbedPane, BorderLayout.CENTER);
        // Create the default console.
        createConsole();
    }

    /**
     * Sets the title of output window.
     * @param command The command associated with the console.
     */
    public void setTitle(final Command command) {
        // Make sure it is EDT thread. If not, make sure following code executes in EDT
        if (SwingUtilities.isEventDispatchThread()) {
            setTitle1(command);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    public void run() {
                        setTitle1(command);
                    }
                });
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * Sets the title of output window.
     * @param command The command associated with the console.
     */
    private void setTitle1(Command command) {
        if (command != null) {
            titleBar.getTitleLabel().setText(OUTPUT_WINDOW_TITLE + " - " + command.
                    toDisplayString());
        } else {
            titleBar.getTitleLabel().setText(OUTPUT_WINDOW_TITLE);
        }
    }

    /**
     * Returns the tabbed pane containing console, search results etc tabs.
     * @return The {@code ApexTabbedPane}.
     */
    public ApexTabbedPane getOutputTabbedPane() {
        return outputTabbedPane;
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    public EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Creates a new console. If the consoles are reusable, it finds the first unused console
     * and returns it. In case consoles are not reusable, it searches for a console where no process
     * is associated and if no such free console exists, creates a new one.
     * <p>
     * Also, in case of reusable consoles, it automatically closes unused consoles while searching.
     * @return A console which can be used for displaying results.
     */
    public Console createConsole() {
        // In case consoles are reusable:
        // Create a new console if all opened consoles are busy running processes
        // else close stopped processes and re-use the one from that group.
        // In case consoles are not reusable, straightway create a console if a process is
        // associated with all consoles
        Console outConsole = null;
        boolean firstIdleConsoleFound = false;
        Console firstIdleConsole = null;
        for (int consoleCount = 0; consoleCount < this.consoles.size();
                consoleCount++) {
            if (!this.consoles.get(consoleCount).isProcessRunning()) {
                if (reuseConsole) {
                    if (!firstIdleConsoleFound) {
                        firstIdleConsole = this.consoles.get(consoleCount);
                        firstIdleConsoleFound = true;
                    } else {
                        removeConsole(consoleCount);
                        consoleCount--;
                    }
                } else {
                    if (this.consoles.get(consoleCount).getCommand() == null) {
                        firstIdleConsole = this.consoles.get(consoleCount);
                        firstIdleConsoleFound = true;
                        break;
                    }
                }
            }
        }
        if (firstIdleConsole != null) {
            outConsole = firstIdleConsole;
        } else {
            outConsole = addConsole();
        }
        this.outputTabbedPane.setSelectedComponent(outConsole.
                getConsoleScrollPane());
        return outConsole;
    }

    /**
     * Creates a console and add to the list.
     * @return A new console.
     */
    private Console addConsole() {
        String consoleName = "Console " + getNextConsoleSequence();
        Console console = new Console(consoleName);
        this.consoles.add(console);
        this.outputTabbedPane.addTab(TabUtil.newTab(consoleName, null, console.
                getConsoleScrollPane(),
                consoleName, new OutputTabComponent(outputTabbedPane,
                consoleName, OUTPUT_WINDOW_TAB_ICON)));
        return console;
    }

    /**
     * Removes a console from the list.
     * @param consoleSequence The sequence number of the console being removed.
     */
    public void removeConsole(int consoleSequence) {
        Console removeConsole = this.consoles.get(consoleSequence);
        this.consoles.remove(consoleSequence);
        this.outputTabbedPane.remove(removeConsole.getConsoleScrollPane());
        removeConsole.release();
        if (this.outputTabbedPane.getTabCount() == 0) {
            // Create a new console in case all the consoles are closed.
            createConsole();
        }
    }

    /**
     * Returns the currently selected console.
     * @return The selected console.
     */
    public Console getCurrentConsole() {
        int selectedTabIndex = this.outputTabbedPane.getSelectedIndex();
        return getConsoleAt(selectedTabIndex);
    }

    /**
     * Returns the console at a specified index.
     * @param consoleSequence The sequence number of the console.
     * @return Console at the specified index.
     */
    public Console getConsoleAt(int consoleSequence) {
        return this.consoles.get(consoleSequence);
    }

    /**
     * Checks whether the process associated with a console is running.
     * @param consoleSequence The sequence number of the console.
     * @return {@code true} if the process associated with the console is running;
     * otherwise returns {@code false}.
     */
    public boolean isProcessRunningAtConsole(int consoleSequence) {
        return getConsoleAt(consoleSequence).isProcessRunning();
    }

    /**
     * Returns a list of consoles where associated processes are still running.
     * @return The list of active consoles.
     */
    public List<Console> getActiveConsoles() {
        List<Console> activeConsoles = new ArrayList<Console>(5);
        for (Console console : consoles) {
            if (console.isProcessRunning()) {
                activeConsoles.add(console);
            }
        }
        return activeConsoles;
    }

    /**
     * Increments and returns the next console sequence number.
     * @return The next console sequence number.
     */
    public int getNextConsoleSequence() {
        return ++consoleSequence;
    }

    /**
     * Enables or disables console options such as start, stop, clear etc.
     * @param enable A boolean that indicates whether or not console options to be enabled.
     */
    public void enableConsoleOptionButtons(final boolean enable) {
        if (SwingUtilities.isEventDispatchThread()) {
            enableConsoleOptionButtons1(enable);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {

                    public void run() {
                        enableConsoleOptionButtons1(enable);
                    }
                });
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Enables or disables console options such as start, stop, clear etc.
     * @param enable A boolean that indicates whether or not console options to be enabled.
     */
    private void enableConsoleOptionButtons1(boolean enable) {
        this.consoleOptions.startButton.setEnabled(enable);
        this.consoleOptions.stopButton.setEnabled(!enable);
    }

    /**
     * The panel for displaying console options.
     */
    private class ConsoleOptions extends ApexPanel {

        /**
         * The start button.
         */
        private ApexButton startButton;
        /**
         * The stop button.
         */
        private ApexButton stopButton;
        /**
         * The clear button.
         */
        private ApexButton clearButton;

        /**
         * Creates a default instance of console options.
         */
        public ConsoleOptions() {
            this.setPreferredSize(new Dimension(25, 20));
            this.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1,
                    Color.LIGHT_GRAY));
            this.setLayout(new FlowLayout(FlowLayout.CENTER));
            startButton = new ConsoleOptionButton(
                    EditorKeyConstants.CONSOLE_START_ICON, "Re-run");
            startButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    getCurrentConsole().restartProcess();
                }
            });
            this.add(startButton);
            stopButton = new ConsoleOptionButton(
                    EditorKeyConstants.CONSOLE_STOP_ICON, "Stop");
            stopButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    getCurrentConsole().stopProcess();
                }
            });
            this.add(stopButton);
            clearButton = new ConsoleOptionButton(
                    EditorKeyConstants.CONSOLE_CLEAR_ICON, "Clear");
            clearButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    getCurrentConsole().clearResultArea();
                }
            });
            this.add(clearButton);
            //startButton.setEnabled(false);
            stopButton.setEnabled(false);
        }

        /**
         * Returns the clear button.
         * @return The clear button.
         */
        public ApexButton getClearButton() {
            return clearButton;
        }

        /**
         * Returns the start button.
         * @return The start button.
         */
        public ApexButton getStartButton() {
            return startButton;
        }

        /**
         * Returns the stop button.
         * @return The stop button.
         */
        public ApexButton getStopButton() {
            return stopButton;
        }

        /**
         * Console option button component.
         */
        private class ConsoleOptionButton extends AbstractIconButton {

            /**
             * Creates a new instance of {@code ConsoleOptionButton} with specified icon name and
             * tool tip text. The icons are accesses from core resources folder.
             * @param iconName The icon name to be used for the button.
             * @param toolTipText The tooltip text to be used for the button.
             */
            public ConsoleOptionButton(String iconName, String toolTipText) {
                super(ImageCreator.createImageIcon(EditorBase.class,
                        "images/" + iconName));
                //this.setIconTextGap(8);
                setBorder(BorderFactory.createLineBorder(Color.GRAY));
                setToolTipText(toolTipText);
            }

            @Override
            protected void doOnClick(ActionEvent e) {
                // do nothing
            }

            /**
             * Paint the border of button when mouse enters
             * the icon button.
             * @param e The mouse event.
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractIconButton) {
                    ((AbstractIconButton) component).setBorderPainted(true);
                }
            }

            /**
             * Remove the border when mouse exits the icon button.
             * @param e The mouse event.
             */
            @Override
            public void mouseExited(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractIconButton) {
                    ((AbstractIconButton) component).setBorderPainted(false);
                }
            }
        }
    }
}
