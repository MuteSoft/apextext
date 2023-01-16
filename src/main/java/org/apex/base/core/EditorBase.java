/*
 * EditorBase.java
 * Created on January 31, 2007, 7:55 AM
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

import org.apex.base.data.EditorComponents;
import org.apex.base.data.EditorProperties;
import org.apex.base.component.DocumentChangeTracker;
import org.apex.base.constant.MenuConstants;
import org.apex.base.data.EditorContext;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.logging.Logger;
import org.apex.base.menu.OpenFileMenu;
import org.apex.base.util.EditorUtil;
import org.apex.base.component.ApexFrame;
import org.apex.base.component.ApexPanel;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.util.StringUtil;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apex.base.debug.EDTThreadCheckingRepaintManager;
import org.apex.base.util.FileUtil;

/**
 * Base class of the platform.
 * <p>
 * This class performs following tasks:
 * <ul>
 *      <li>Reads editor configuration file (config.xml) and sets default 
 *             {@code Locale}, logging parameters etc.
 *      <li>Initializes all UI components and editor context.
 *      <li>Initializes editor properties and other components.
 *      <li>Opens the default document.
 * </ul>
 * @author Mrityunjoy Saha
 * @version 1.2
 * @since Apex 1.0
 */
public abstract class EditorBase {

    /**
     * The main editor window.
     */
    private ApexFrame mainFrame;
    /**
     * The editor context.
     * Context is created once and used from various part of application.
     */
    private static EditorContext context = new EditorContext();
    /**
     * A default file chooser dialog. This file chooser dialog instance is currently not used by the application directly.
     * This is only to boost the performnace of file chooser dialog when opened for the first time.
     * <p>
     * However, from all parts of the application the same file chooser instance can be used.
     */
    private static JFileChooser defaultFileChooser = null;

    /** 
     * Creates a new instance of EditorBase.
     */
    public EditorBase() {
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    public static EditorContext getContext() {
        return context;
    }

    /**
     * Creates the editor base.
     * <p>
     * It creates editor actions and sets editor properties and other components.
     * Creates the main UI and then opens the default document. In case an absolute
     * path is passed as argument, the corresponding document will be opened from disk;
     * otherwise opens a temporary document.
     * @param args Main method arguments.
     */
    public void createBase(String[] args) {
        setEditorGlobalData();

        ActionManager.createActions();
        createUI();
        Logger.logInfo("Default actions and UI created.");
        /*
         * If a file was clicked to launch the editor open that file or create a new document
         * In case multiple files were clicked only the first file will be opened
         */
        String filePath = getFilePath(args);
        if (filePath != null) {
            ((OpenFileMenu) MenuManager.getMenuById(
                    MenuConstants.OPEN_FILE)).openFile(
                    filePath);
        } else {
            MenuManager.getMenuById(MenuConstants.NEW_FILE).
                    processMenu(new InputParams(),
                    new OutputParams());
        }
        DocumentChangeTracker.getInstance().getTimer().start();
        // This is to reduce performance bottleneck when first time file chooser dialog is opened.
        // Remove this code when JFileChooser startup becomes fast enough in swing.
        // @TODO Decide after profiling whether to do this or not
        final Timer backgroundTimer = new Timer(10000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (defaultFileChooser == null) {
                    defaultFileChooser = new JFileChooser();
                }
            }
        });
        backgroundTimer.setRepeats(false);
        backgroundTimer.start();
    }

    /**
     * Creates the editor main UI.
     * <p>
     * It creates the menu-bar, tool-bar and editor body.
     */
    private void createUI() {
        this.mainFrame.setTitle(EditorUtil.getDisplayEditorTitleBarText(null));
        this.mainFrame.setIconImage(
                context.getEditorProperties().getEditorIcon());
        EditorMenuBar menuBar = new EditorMenuBar();
        EditorToolBar toolBar = new EditorToolBar();
        context.getEditorComponents().setMenuBar(menuBar);
        context.getEditorComponents().setToolBar(toolBar);
        this.mainFrame.setJMenuBar(menuBar);
        ApexPanel editPanel = new EditorBody();
        this.mainFrame.setLayout(new BorderLayout());
        this.mainFrame.getContentPane().add(toolBar, BorderLayout.PAGE_START);
        this.mainFrame.getContentPane().add(editPanel, BorderLayout.CENTER);
        // @TODO Profile and decide whether to make the window visible and then add components
        // or add components and then make the window visible.
        this.mainFrame.setDefaultCloseOperation(ApexFrame.DO_NOTHING_ON_CLOSE);
        this.mainFrame.setExtendedState(ApexFrame.MAXIMIZED_BOTH);
        this.mainFrame.setVisible(true);
        this.mainFrame.toFront();
        // Set minimum size of the main editor window
        this.mainFrame.setMinimumSize(new Dimension(800, 600));
    }

    /**
     * Extracts a path from first argument.
     * @param args Main method arguments.
     * @return A file path.
     */
    private String getFilePath(String[] args) {
        if (args.length >= 1) {
            return args[0];
        }
        return null;
    }

    /**
     * Sets editor global data like editor global properties and editor
     * main components.
     */
    protected void setEditorGlobalData() {
        EditorProperties prop = getEditorProperties();
        EditorComponents comp = getEditorComponents();
        comp.setFrame(this.mainFrame);
        context.setEditorProperties(prop);
        context.setEditorComponents(comp);
        // Set Locale
        context.getEditorProperties().setLocale(EditorUtil.getLocale());
        Locale.setDefault(context.getEditorProperties().getLocale());
    }

    /**
     * Returns editor properties object.
     * @return The editor properties.
     */
    protected abstract EditorProperties getEditorProperties();

    /**
     * Returns editor components object.
     * @return The editor components.
     */
    protected abstract EditorComponents getEditorComponents();

    /**
     * Starts the editor.
     * @param args An array of input arguments.
     */
    protected void startEditor(final String[] args) {
        this.mainFrame = new ApexFrame() {

            /**
             * Closes all opened documents and performs clean-up
             * activities. Finally closes the editor.
             */
            @Override
            public void windowClosing(WindowEvent we) {
                if (MenuManager.getMenuById(MenuConstants.EXIT_EDITOR) != null) {
                    MenuManager.getMenuById(MenuConstants.EXIT_EDITOR).
                            processMenu(new InputParams(),
                            new OutputParams());
                } else {
                    System.exit(-1);
                }
            }
        };
        // Display Splash Screen.
        SplashScreen welcome = new SplashScreen();
        welcome.start();
        // Read application config file. This must happen before accessing logger
        readApplicationConfiguration();
        try {
            // After displaying for a specified period (actually need to load
            // required parts here) start the application.
            welcome.join();
        } catch (InterruptedException ex) {
            Logger.logWarning("Failed to display splash screen.", ex);
        }
        Logger.logInfo("Application starting.", EditorBase.class.getName(),
                "main");
        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                new EditorShutdownHook().process();
            }
        });
        // Sets global font for all swing components.        
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    // @TODO RepaintManager is used for debug purpose. After development remove this.
//                    RepaintManager.setCurrentManager(
//                            new EDTThreadCheckingRepaintManager());
                    UIManager.setLookAndFeel(UIManager.
                            getSystemLookAndFeelClassName());
                    setUIDefaults();
                    SwingUtilities.updateComponentTreeUI(mainFrame);
                } catch (InstantiationException ex) {
                    Logger.logError(
                            "Failed to load system look and feel class for UI.",
                            ex);
                } catch (IllegalAccessException ex) {
                    Logger.logError(
                            "Failed to load system look and feel class for UI.",
                            ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.logError(
                            "Failed to load system look and feel class for UI.",
                            ex);
                } catch (ClassNotFoundException ex) {
                    Logger.logError(
                            "Failed to load system look and feel class for UI.",
                            ex);
                }
                createBase(args);
                Logger.logInfo("Application started with locale: " + context.
                        getEditorProperties().
                        getLocale(), EditorBase.class.getName(), "main");
            }
        });
    }

    /**
     * This method reads properties from application configuration file
     * and puts in {@code System} as properties.
     * <p>
     * If command line argument are passed to set these System properties then
     * those are not replaced.
     * <p>
     * <strong>NOTE: </strong> When a new system properties is added modify this method and add
     * logic for that new property.
     */
    private static void readApplicationConfiguration() {
        File file = null;
        FileInputStream configInputStream = null;
        try {
            file = new File(
                    EditorKeyConstants.APPLICATION_CONFIGURATION_FILE);
            if (!file.exists() || file.length()
                    > EditorKeyConstants.APPLICATION_CONFIGURATION_FILE_MAX_SIZE) {
                throw new IOException("A suitable configuration file not found: "
                        + file);
            }
            Properties p = new Properties();
            configInputStream = new FileInputStream(file);
            p.loadFromXML(configInputStream);
            String[] propertyKeys = new String[]{
                EditorKeyConstants.DEBUG_LOG_JVM_PARAM,
                EditorKeyConstants.INFO_LOG_JVM_PARAM,
                EditorKeyConstants.ERROR_LOG_JVM_PARAM,
                EditorKeyConstants.WARNING_LOG_JVM_PARAM,
                EditorKeyConstants.LANGUAGE_JVM_PARAM,
                EditorKeyConstants.LOG_DIRECTORY_JVM_PARAM
            };
            for (int iCount = 0; iCount < propertyKeys.length; iCount++) {
                if (StringUtil.isNullOrEmpty(System.getProperty(
                        propertyKeys[iCount])) && !StringUtil.isNullOrEmpty(p.
                        getProperty(
                        propertyKeys[iCount]))) {
                    System.setProperty(propertyKeys[iCount], p.getProperty(
                            propertyKeys[iCount]));
                }
            }
        } catch (Exception ex) {
            // Don't do anything. Print Stack Trace is intentional.
            ex.printStackTrace();
            Logger.logError("Failed to load/parse application configuration file: "
                    + file, ex);
        } finally {
            FileUtil.closeIOStream(configInputStream);
        }
    }

    /**
     * Sets the font for all swing components.
     */
    public static void setUIDefaults() {
        // @TODO Apply theme here
    }
}
