/*
 * Logger.java
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
package org.apex.base.logging;

import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.util.DateTime;
import org.apex.base.util.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.StreamHandler;
import org.apex.base.constant.CommonConstants;

/**
 * A log manager used to log errors, messages for various events
 * generated in the application.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class Logger {

    /**
     * The logger used to log messages.
     */
    private static java.util.logging.Logger infoLogger;
    /**
     *  The logger used to log non-severe events.
     */
    private static java.util.logging.Logger warningLogger;
    /**
     *  The logger used to log error events.
     */
    private static java.util.logging.Logger errorLogger;
    /**
     * Log files extension.
     */
    private static final String LOG_FILE_EXTENSION = "log";
    /**
     * A flag which indicates whether or not info logging is enabled.
     */
    private static boolean isInfoLogEnabled = false;
    /**
     * A flag which indicates whether or not warning logging is enabled.
     */
    private static boolean isWarningLogEnabled = false;
    /**
     * A flag which indicates whether or not error logging is enabled.
     */
    private static boolean isErrorLogEnabled = false;
    /**
     * Stores the directory where log files to be created.
     */
    private static String logDir;
    /**
     * A flag which indicates whether or not logging to console is required.
     */
    private static boolean isConsoleLoggingRequired = false;
    /**
     * Determines what kind of log handler to be used.
     * <p>
     * Possible values are file, stream, socket and memory.
     */
    private static final String LOG_HANDLER = "file";


    static {
        if (System.getProperty(EditorKeyConstants.DEBUG_LOG_JVM_PARAM) != null && System.
                getProperty(
                EditorKeyConstants.DEBUG_LOG_JVM_PARAM).
                trim().equalsIgnoreCase("true")) {
            isInfoLogEnabled = true;
            isWarningLogEnabled = true;
            isErrorLogEnabled = true;
        } else {
            isInfoLogEnabled = System.getProperty(
                    EditorKeyConstants.INFO_LOG_JVM_PARAM) != null && System.
                    getProperty(
                    EditorKeyConstants.INFO_LOG_JVM_PARAM).
                    trim().equalsIgnoreCase("true");
            isWarningLogEnabled = System.getProperty(
                    EditorKeyConstants.WARNING_LOG_JVM_PARAM) != null && System.
                    getProperty(
                    EditorKeyConstants.WARNING_LOG_JVM_PARAM).
                    trim().equalsIgnoreCase("true");
            isErrorLogEnabled = System.getProperty(
                    EditorKeyConstants.ERROR_LOG_JVM_PARAM) != null && System.
                    getProperty(
                    EditorKeyConstants.ERROR_LOG_JVM_PARAM).
                    trim().equalsIgnoreCase("true");
        }
        isConsoleLoggingRequired = System.getProperty(
                EditorKeyConstants.CONSOLE_LOG_JVM_PARAM) != null && System.
                getProperty(
                EditorKeyConstants.CONSOLE_LOG_JVM_PARAM).
                trim().equalsIgnoreCase("true");        
        logDir = System.getProperty(EditorKeyConstants.LOG_DIRECTORY_JVM_PARAM) == null
                ? System.getProperty("user.dir")
                : System.getProperty(EditorKeyConstants.LOG_DIRECTORY_JVM_PARAM);
    }

    /**
     * Returns the logger used to log messages.
     * <p>
     * It creates the destination info log file, creates the logger and initializes
     * log handlers and the log record formatter.
     * @return The message logger.
     */
    private static java.util.logging.Logger getInfoLogger() {
        if (infoLogger == null) {
            try {
                infoLogger = java.util.logging.Logger.getLogger(
                        EditorKeyConstants.INFO_LOG_JVM_PARAM);
                if (!isConsoleLoggingRequired) {
                    infoLogger.setUseParentHandlers(false);
                } else {
                    try {
                        infoLogger.getParent().getHandlers()[0].setFormatter(
                                new LogRecordFormatter());
                    } catch (Exception ex) {
                        // Do Nothing. Console logging is not vital.
                    }
                }
                infoLogger.setLevel(Level.INFO);
                // Fix for bug id 2894392 (sourceforge.net)
                Handler handler = createLogHandler(
                        EditorKeyConstants.INFO_LOG_JVM_PARAM);
                infoLogger.addHandler(handler);
            } catch (Exception ex) {
                // Setting the flag to 'false' will ensure that there will not be any unnecessary
                // attempts which are bound to fail.
                isInfoLogEnabled = false;
                ex.printStackTrace();
                return null;
            }
        }
        return infoLogger;
    }

    /**
     * Returns the logger used to log non-severe events.
     * <p>
     * It creates the destination warning log file, creates the logger and initializes
     * log handlers and the log record formatter.
     * @return The warning logger.
     */
    private static java.util.logging.Logger getWarningLogger() {
        if (warningLogger == null) {
            try {
                warningLogger = java.util.logging.Logger.getLogger(
                        EditorKeyConstants.WARNING_LOG_JVM_PARAM);
                if (!isConsoleLoggingRequired) {
                    warningLogger.setUseParentHandlers(false);
                } else {
                    try {
                        warningLogger.getParent().getHandlers()[0].setFormatter(
                                new LogRecordFormatter());
                    } catch (Exception ex) {
                        // Do Nothing.
                    }
                }
                warningLogger.setLevel(Level.WARNING);
                // Fix for bug id 2894392 (sourceforge.net)
                Handler handler = createLogHandler(
                        EditorKeyConstants.WARNING_LOG_JVM_PARAM);
                warningLogger.addHandler(handler);
            } catch (Exception ex) {
                // Setting the flag to 'false' will ensure that there will not be any unnecessary
                // attempts which are bound to fail.
                isWarningLogEnabled = false;
                ex.printStackTrace();
                return null;
            }
        }
        return warningLogger;
    }

    /**
     * Returns the logger used to log error events.
     * <p>
     * It creates the destination error log file, creates the logger and initializes
     * log handlers and the log record formatter.
     * @return The error logger.
     */
    private static java.util.logging.Logger getErrorLogger() {
        if (errorLogger == null) {
            try {
                errorLogger = java.util.logging.Logger.getLogger(
                        EditorKeyConstants.ERROR_LOG_JVM_PARAM);
                if (!isConsoleLoggingRequired) {
                    errorLogger.setUseParentHandlers(false);
                } else {
                    try {
                        errorLogger.getParent().getHandlers()[0].setFormatter(
                                new LogRecordFormatter());
                    } catch (Exception ex) {
                        // Do Nothing.
                    }
                }
                errorLogger.setLevel(Level.SEVERE);
                // Fix for bug id 2894392 (sourceforge.net)
                Handler handler = createLogHandler(
                        EditorKeyConstants.ERROR_LOG_JVM_PARAM);
                errorLogger.addHandler(handler);
            } catch (Exception ex) {
                // Setting the flag to 'false' will ensure that there will not be any unnecessary
                // attempts which are bound to fail.
                isErrorLogEnabled = false;
                ex.printStackTrace();
                return null;
            }
        }
        return errorLogger;
    }

    /**
     * Logs an event generated by application. Event can be simple information, error,
     * warning etc.     
     * @param level The log level.
     * @param mesg The log message.
     * @param th The {@code Throwable} object.
     * @param className The name of class from where information is being logged.
     * @param methodName The name of method from where information is being logged.
     */
    private static void log(Level level, String className,
            String methodName, String mesg, Throwable th) {
        // Check whether a type of logging is enabled and then log the same
        try {
            if (level.equals(Level.INFO)) {
                if (isInfoLogEnabled && getInfoLogger() != null) {
                    getInfoLogger().logp(level, className, methodName, mesg);
                    getInfoLogger().getHandlers()[0].flush();
                }
            } else if (level.equals(Level.WARNING)) {
                if (isWarningLogEnabled && getWarningLogger() != null) {
                    getWarningLogger().logp(level, className, methodName, mesg,
                            th);
                    getWarningLogger().getHandlers()[0].flush();
                }
            } else if (level.equals(Level.SEVERE)) {
                if (isErrorLogEnabled && getErrorLogger() != null) {
                    getErrorLogger().logp(level, className, methodName, mesg, th);
                    getErrorLogger().getHandlers()[0].flush();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Logs a message with source class name and method name.
     * <p>
     * The message being logged is for information only. For example, a message
     * can be a statement like  'Application started with Locale: en_US'.
     * @param mesg The message to be logged.
     * @param className The name of class from where information is being logged.
     * @param methodName The name of method from where information is being logged.
     */
    public static void logInfo(String mesg, String className, String methodName) {
        log(Level.INFO, className, methodName, mesg, null);
    }

    /**
     * Logs a message.
     * <p>
     * The message being logged is for information only. For example, a message
     * can be a statement like  'Application started with Locale: en_US'.
     * @param mesg The message to be logged.
     */
    public static void logInfo(String mesg) {
        log(Level.INFO, null, null, mesg, null);
    }

    /**
     * Logs an error event.
     * <p>
     * This method is called from application to log non-severe problems.
     * @param mesg The warning message.
     * @param th The {@code Throwable} object.
     */
    public static void logWarning(String mesg, Throwable th) {
        log(Level.WARNING, null, null, mesg, th);
    }

    /**
     * Logs an error event.
     * <p>
     * This method is called from application to log severe problems.
     * @param mesg The error message.
     * @param th The {@code Throwable} object.
     */
    public static void logError(String mesg, Throwable th) {
        log(Level.SEVERE, null, null, mesg, th);
    }

    /**
     * Creates a new Logger.
     */
    private Logger() {
    }

    /**
     * Returns the log file name for a specific log type.
     * <p>
     * It appends current date in 'ddMMyyyy' format at the end of file name.
     * @param typeName The log type. It can be info, warning, error etc.
     * @return The log file name.
     */
    private static String getLogFile(String typeName) {
        return typeName + CommonConstants.WORD_SEPARATOR + DateTime.getNow(
                "ddMMyyyy");
    }

    /**
     * Returns the log file name pattern to be used by file handler.
     * @param logDir The log file parent directory.
     * @param typeName The log type. It can be info, warning, error etc.
     * @return The log file name pattern.
     */
    private static String getLogFilePattern(String logDir, String typeName) {
        return logDir + "/" + getLogFile(typeName) + CommonConstants.WORD_SEPARATOR + "%g" + CommonConstants.WORD_SEPARATOR + "%u" + CommonConstants.WORD_SEPARATOR + LOG_FILE_EXTENSION;
    }

    /**
     * Creates a specific type of logger. Also, it assigns a suitable log formatter
     * to the handler.
     * <p>
     * It returns instance of either {@code StreamHandler} or {@code FileHandler} based on parameter.
     * Based on performance in future releases, the log handler would be altered.
     * @param typeName The type of logging.
     * @return The log handler.
     * @throws java.io.IOException
     */
    private static Handler createLogHandler(String typeName) throws IOException {
        File logFile = null;
        Handler handler = null;
        // Fix for bug id 2894392 (sourceforge.net)
        if (LOG_HANDLER.equalsIgnoreCase("file")) {
            logFile = FileUtil.createFileAndMissingDirs(getLogDirectory(), null);
            handler = new FileHandler(getLogFilePattern(
                    logFile.getAbsolutePath(), typeName),
                    EditorKeyConstants.MAX_LOG_FILE_SIZE,
                    EditorKeyConstants.MAX_LOG_FILE,
                    true);
            handler.setFormatter(new LogRecordFormatter());
        } else {
            logFile = FileUtil.createFileAndMissingDirs(
                    getLogDirectory(), getLogFile(
                    typeName));
            if (!FileUtil.isDocumentWritable(logFile)) {
                throw new IOException("Failed to create error log file.");
            }
            handler = new StreamHandler(new FileOutputStream(
                    logFile,
                    true), new LogRecordFormatter());
        }
        return handler;
    }

    /**
     * Returns the log directory.
     * @return Directory where log files are created.
     */
    private static String getLogDirectory() {
        return logDir;
    }
}
