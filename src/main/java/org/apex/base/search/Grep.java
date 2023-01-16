/*
 * Grep.java 
 * Created on 3 Oct, 2010, 4:55:42 PM
 *
 * Copyright (C) 2010 Mrityunjoy Saha
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
package org.apex.base.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apex.base.logging.Logger;

/**
 * Implementation of unix like grep functionality.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.3
 */
public class Grep {

    /**
     * Charset and decoder for UTF-8.
     */
    private static Charset charset = Charset.forName("UTF-8");
    /**
     *
     */
    private static CharsetDecoder decoder = charset.newDecoder();
    /**
     * Pattern used to parse lines.
     */
    private static Pattern linePattern = Pattern.compile(".*\r?\n");
    /**
     * The input pattern that we're looking for
     */
    private static Pattern pattern;

    /**
     * Creates a new instance of {@code Grep}.
     */
    public Grep() {
        // Do nothing.
    }

    /**
     * Compile the pattern from the command line
     * @param pat The pattern as string.
     */
    private void compile(String pat) {
        try {
            pattern = Pattern.compile(pat);
        } catch (PatternSyntaxException x) {
            Logger.logError("Invalid pattern while searching folder.", x);
        }
    }

    /**
     * Use the linePattern to break the given CharBuffer into lines, applying
     * the input pattern to each line to see if we have a match.
     * @param f The file.
     * @param cb The character buffer.
     */
    private void grep(File f, CharBuffer cb) {
        // Line matcher
        Matcher lm = linePattern.matcher(cb);
        // Pattern matcher
        Matcher pm = null;
        int lines = 0;
        while (lm.find()) {
            lines++;
            // The current line
            CharSequence cs = lm.group();
            if (pm == null) {
                pm = pattern.matcher(cs);
            } else {
                pm.reset(cs);
            }
            if (pm.find()) {
                System.out.print(f + ":" + lines + ":" + cs);
            }
            if (lm.end() == cb.limit()) {
                break;
            }
        }
    }

    /**
     * Search for occurrences of the input pattern in the given file.
     * @param f The input file.
     * @throws IOException
     */
    private void grep(File f) throws IOException {

        // Open the file and then get a channel from the stream
        FileInputStream fis = new FileInputStream(f);
        FileChannel fc = fis.getChannel();

        // Get the file's size and then map it into memory
        int sz = (int) fc.size();
        MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

        // Decode the file into a char buffer
        CharBuffer cb = decoder.decode(bb);

        // Perform the search
        grep(f, cb);

        // Close the channel and the stream
        fc.close();
    }

    /**
     *
     * @param searchDataModel
     * @param files
     */
    public void grep(SearchTextModel searchDataModel, File[] files) {
        compile(searchDataModel.getSearchKey());
        for (File f : files) {
            try {
                grep(f);
            } catch (IOException x) {
                System.err.println(f + ": " + x);
            }
        }
    }

    /**
     * Test method.
     * @param args Input arguments.
     */
    public static void main(String[] args) {
        String searchKey = "Mrityunjoy";
        String folderPath = "C:/Users/mrityunjoy_saha/Documents/apexbase/src/org/apex/base/function";
        File[] files = new File(folderPath).listFiles();
        Grep grep = new Grep();
        SearchTextModel model = new SearchTextModel();
        model.setSearchKey(searchKey);
        grep.grep(model, files);
    }
}
