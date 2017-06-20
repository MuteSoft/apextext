/*
 * WriteFile.java 
 * Created on December 27, 2006, 11:17 PM
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
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Segment;
import org.apex.base.util.FileUtil;

/**
 * Writes a file to file system with a specified character set. If character
 * set is not specified, 'UTF-8' is used as default encoding.
 * <p>
 * The content to be written is fetched from given document's underlying data model.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class WriteFile extends Function {

    /**
     * Creates a new instance of WriteFile.
     */
    public WriteFile() {
    }

    /**
     * Writes a file to file system with a specified character set. If character
     * set is not specified, 'UTF-8' is used as default encoding.
     * <p>
     * For better performance uses 'Segment Cache' to read content from document's data model.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    protected void doExecute(InputParams in, OutputParams out) {
        AbstractDocument savingFile = (AbstractDocument) in.get("SAVING_FILE");
        String characterSet = in.get("CHARACTER_SET") == null ? EditorKeyConstants.DEFAULT_CHARACTER_ENCODING
                : (String) in.get("CHARACTER_SET");
        DefaultStyledDocument doc = savingFile.getDocument();
        BufferedOutputStream saveAsFileBuffStream = null;
        FileOutputStream saveAsFileStream = null;
        try {
            Logger.logInfo("Writing file '" + savingFile.getAbsolutePath() +
                    "' to disk. Encoding used: " + characterSet, getClass().
                    getName(), "doExecute");
            saveAsFileStream = new FileOutputStream(savingFile);
            saveAsFileBuffStream = new BufferedOutputStream(saveAsFileStream);
            int nleft = doc.getLength();
            Segment text = new Segment();
            int offs = 0;
            text.setPartialReturn(true);
            while (nleft > 0) {
                doc.getText(offs, nleft, text);
                saveAsFileBuffStream.write(text.subSequence(0, text.length()).
                        toString().getBytes(
                        characterSet));
                nleft -= text.count;
                offs += text.count;
            }
            saveAsFileBuffStream.flush();
            // Update the last modification time - as by default it will be current timestamp.
            savingFile.setLastSaved(savingFile.lastModified());
        } catch (IOException io) {
            Logger.logError("Error while writing document text to file system. Document: " +
                    savingFile.getAbsolutePath(), io);
        } catch (BadLocationException ble) {
            Logger.logError("Error while writing document text to file system. Document: " +
                    savingFile.getAbsolutePath(), ble);
        } finally {
            FileUtil.closeIOStream(saveAsFileBuffStream);
            FileUtil.closeIOStream(saveAsFileStream);
        }
    }

    protected void postExecute(InputParams in, OutputParams out) {
    }

    public String getName() {
        return "Write File";
    }

    protected void init(InputParams in, OutputParams out) {
    }
}
