/*
 * FileUtil.java
 * Created on May 28, 2007, 7:44 PM
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
package org.apex.base.util;

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.ActionManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.EditorContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import org.apex.base.logging.Logger;

/**
 * A utility class to deal with files in file system and also to deal with opned documents
 * in editor.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class FileUtil {

    /**
     * Constructs a new instance of {@code FileUtil}.
     */
    private FileUtil() {
    }

    /**
     * Returns user's home directory.
     * @return User's home directory.
     */
    public static String getUserHomeDir() {
        return System.getProperty("user.home");
    }

    /**
     * Creates the specified file and creates all missing directories. In case given file name
     * is null or empty string, it creates all missing directories and does not attempt to create the new file.
     * @param path The path of file.
     * @param name The name of file.
     * @return The created {@code File}. It returns {@code null} if file can not be located or failed to create the parent directories and 
     *              target file.
     * @throws java.io.IOException
     */
    public static File createFileAndMissingDirs(String path,
            String name) throws
            IOException {
        File file = null;
        boolean isFileNameNullOrEmpty = StringUtil.isNullOrEmpty(name);
        if (isFileNameNullOrEmpty) {
            file = new File(path);
        } else {
            file = new File(path, name);
        }
        if (!file.exists()) {
            try {
                if (isFileNameNullOrEmpty) {
                    if (!file.mkdir()) {
                        throw new IOException("Failed to create the directory.");
                    }
                } else {
                    file.createNewFile();
                }
            } catch (IOException ex) {
                // One or more parent directories don't exist. Create them.
                if (!file.getParentFile().mkdirs()) {
                    throw new IOException(
                            "File not found or could not access. Path: " + path +
                            " Name: " + name);
                }
                // Parent directories are created. Now create the file or the directory.
                if (isFileNameNullOrEmpty) {
                    if (!file.mkdir()) {
                        throw new IOException("Failed to create the directory.");
                    }
                } else {
                    file.createNewFile();
                }
            }
        }
        return file;
    }

    /**
     * Marks a document as saved.
     * @param context The editor context.
     * @param file The docuent to be marked as saved.
     */
    public static void markAsSaved(EditorContext context, AbstractDocument file) {
        if (!file.isSaved()) {
            // Disable Save Icon.
            ActionManager.setActionEnabled(MenuConstants.SAVE_FILE, false);
            file.setSaved(true);
            // Update Tab title. Before changing tab title change the files save state.
            EditorUtil.updateDocumentTitle(context, file);
        /* @TODO Setting last saved time to current system timestamp may create problem
         * when system date is changed intentionally to a past date or future date.
         * This may break synchronisation between AbstractDocument.getLastsaved() and File.lastModified().
         */
        // file.setLastSaved(DateTime.getNowMillis());
        //file.setLastSaved(file.lastModified());
        }
    }

    /**
     * Marks a document as unsaved.
     * @param context The editor context.
     * @param file The docuent to be marked as unsaved.
     */
    public static void markAsUnsaved(EditorContext context,
            AbstractDocument file) {
        if (file.isSaved()) {
            // Enable Save Icon.
            ActionManager.setActionEnabled(MenuConstants.SAVE_FILE, true);
            file.setSaved(false);
            // Update Tab title. Before changing tab title change the files save state.
            EditorUtil.updateDocumentTitle(context, file);
        }
    }

    /**
     * Returns the file extension from specified file pathname.
     * @param absolutePath File pathname.
     * @return File extension.
     */
    public static String getExtension(String absolutePath) {
        if (absolutePath.indexOf(".") == -1) {
            return "";
        }
        return absolutePath.substring(absolutePath.lastIndexOf(".") + 1);
    }

    /**
     * Returns the file extension from specified document.
     * @param file A document.
     * @return File extension.
     */
    public static String getExtension(AbstractDocument file) {
        return getExtension(file.getAbsolutePath());
    }

    /**
     * Determines whether or not the file representated by given pathname exists in file system.
     * @param path The pathname.
     * @return {@code true} if the file exists; otherwise returns {@code false}.
     */
    public static boolean isFileExists(String path) {
        return path == null
                ? false
                : new File(path).exists();
    }

    /**
     * Closes an {@code InputStream} stream. It returns immediately if given IO stream is {@code null}.
     * @param stream An {@code InputStream}.
     */
    public static void closeIOStream(InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception ex) {
                Logger.logWarning("Failed to close input stream.", ex);
            } finally {
                stream = null;
            }
        }
    }

    /**
     * Closes an {@code OutputStream} stream. It returns immediately if given IO stream is {@code null}.
     * @param stream An {@code OutputStream}.
     */
    public static void closeIOStream(OutputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception ex) {
                Logger.logWarning("Failed to close output stream.", ex);
            } finally {
                stream = null;
            }
        }
    }

    /**
     * Closes an {@code Reader} stream. It returns immediately if given IO stream is {@code null}.
     * @param stream An {@code Reader}.
     */
    public static void closeIOStream(Reader stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception ex) {
                Logger.logWarning("Failed to close input stream.", ex);
            } finally {
                stream = null;
            }
        }
    }

    /**
     * Closes an {@code Writer} stream. It returns immediately if given IO stream is {@code null}.
     * @param stream An {@code Writer}.
     */
    public static void closeIOStream(Writer stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception ex) {
                Logger.logWarning("Failed to close output stream.", ex);
            } finally {
                stream = null;
            }
        }
    }

    /**
     * Determines whether given document is readable.
     * @param file A file.
     * @return {@code true} if the file is readable; otherwise returns {@code false}.
     */
    public static boolean isDocumentReadable(File file) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            return true;
        } catch (Exception ex) {
            Logger.logWarning("Document '" + file + "' is not accessible.", null);
            return false;
        } finally {
            closeIOStream(inputStream);
        }
    }

    /**
     * Determines whether given document is writable.
     * @param file A file.
     * @return {@code true} if the file is writable; otherwise returns {@code false}.
     */
    public static boolean isDocumentWritable(File file) {
        FileOutputStream outputStream = null;
        boolean exists = file.exists();
        try {
            outputStream = new FileOutputStream(file);
            return true;
        } catch (Exception ex) {
            Logger.logWarning("Document '" + file + "' is not accessible.", null);
            return false;
        } finally {
            closeIOStream(outputStream);
            // If file was not existing in case of successful scenario a new file is created. Delete it.
            if (!exists) {
                file.delete();
            }
        }
    }
}
