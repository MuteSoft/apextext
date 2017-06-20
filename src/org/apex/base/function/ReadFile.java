/*
 * ReadFile.java
 * Created on 1 July, 2007, 12:31 PM
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

import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.logging.Logger;
import org.apex.base.util.FileUtil;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;

/**
 * Reads a file from file system with a specified character set. If character
 * set is not specified, 'UTF-8' is used as default encoding.
 * <p>
 * After reading the file, content is inserted to given document's underlying data model.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ReadFile extends Function {

    /**
     * The length of intermediary data storage.
     */
    private final int bufferSize = 2048;
    /**
     * The intermediary data storage.
     */
    private byte[] inputData = new byte[bufferSize];

    /**
     * Creates a new instance of ReadFile.
     */
    public ReadFile() {
    }

    /**
     * Reads a file from file system with a specified character set. If character
     * set is not specified, 'UTF-8' is used as default encoding.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void doExecute(InputParams in, OutputParams out) {
        final AbstractDocument file = (AbstractDocument) in.get("OPENING_FILE");
        final String characterSet = in.get("CHARACTER_SET") == null ? EditorKeyConstants.DEFAULT_CHARACTER_ENCODING
                : (String) in.get("CHARACTER_SET");
        final DefaultStyledDocument doc = file.getDocument();
        final SimpleAttributeSet simpleAttr = new SimpleAttributeSet();
        final StringBuilder data = new StringBuilder("");
        BufferedInputStream openFileBuffStream = null;
        FileInputStream openFileStream = null;
        try {
            Logger.logInfo("Loading file '" + file.getAbsolutePath() +
                    "' from disk. Encoding used: " + characterSet, getClass().
                    getName(), "doExecute");
            openFileStream = new FileInputStream(file);
            openFileBuffStream = new BufferedInputStream(openFileStream,
                    bufferSize);
            int count = 0;
            while ((count = openFileBuffStream.read(inputData, 0, bufferSize)) !=
                    -1) {
                data.append(new String(inputData, 0, count,
                        characterSet));
            }
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    try {
                        // Clear the document
                        doc.remove(0, doc.getLength());
                        doc.insertString(doc.getLength(), data.toString(),
                                simpleAttr);
                        // Remove all undoable edits happened during opening a file from disk
                        file.getUndoManager().discardAllEdits();
                        // Mark the file as saved
                        FileUtil.markAsSaved(getContext(), file);
                        /* After rendering the edit area, scroll it to top position */
                        file.getEditor().setCaretPosition(0);
                        // Update the last modification time - as by default it will be current timestamp.
                        file.setLastSaved(file.lastModified());
                    } catch (BadLocationException ex) {
                        Logger.logError("Error while loading document text to editor. Document: " +
                                file.getAbsolutePath(), ex);
                    }
                }
            });
        //file.getEditor().scrollRectToVisible(file.getEditor().modelToView(0));            
        } catch (FileNotFoundException ex) {
            Logger.logError("Error while loading document text to editor. Document: " +
                    file.getAbsolutePath(), ex);
        } catch (IOException ex) {
            Logger.logError("Error while loading document text to editor. Document: " +
                    file.getAbsolutePath(), ex);
        } catch (InterruptedException ie) {
            Logger.logError("Error while loading document text to editor. Document: " +
                    file.getAbsolutePath(), ie);
        } catch (InvocationTargetException ite) {
            Logger.logError("Error while loading document text to editor. Document: " +
                    file.getAbsolutePath(), ite);
        } finally {
            data.delete(0, data.length());
            FileUtil.closeIOStream(openFileBuffStream);
            FileUtil.closeIOStream(openFileStream);
        }
    }

    protected void postExecute(InputParams in, OutputParams out) {
    }

    protected String getName() {
        return "Read File";
    }

    protected void init(InputParams in, OutputParams out) {
    }
}
