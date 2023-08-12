/*
 * HighlightedDocument.java 
 *
 * This file is part of the programmer editor demo
 * Copyright (C) 2001-2005 Stephen Ostermiller
 * http://ostermiller.org/contact.pl?regarding=Syntax+Highlighting
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

import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import org.apex.base.highlighter.DocumentReader;
import org.apex.base.highlighter.SyntaxHighlighter;
import org.apex.base.highlighter.lexer.Lexer;
import org.apex.base.highlighter.style.DocumentStyle;
import org.apex.base.logging.Logger;
import org.apex.base.settings.EditorConfiguration;
import org.apex.base.settings.event.DocTypesConfigChangeListener;
import org.apex.base.settings.event.SyntaxStyleConfigChangeListener;

/**
 * A document that supports syntax highlighting. It can be marked up with character and paragraph 
 * styles in a manner similar to the Rich Text Format.
 * @author Stephen Ostermiller
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class HighlightedDocument extends DefaultStyledDocument {

    /**
     * A reader wrapped around the document so that the document can be fed into
     * the lexer.
     */
    private final DocumentReader documentReader;
    /**
     * If non-null, all is drawn with this style (no lexing).
     */
    private AttributeSet globalStyle = null;
    /**
     * The lexer that tells us what colors different words should be.
     */
    private Lexer syntaxLexer;
    /**
     * A thread that handles the actual coloring.
     */
    private final SyntaxHighlighter colorer;
    /**
     * A lock for modifying the document, or for actions that depend on the
     * document not being modified.
     */
    private final Object docLock = new Object();
    /**
     * The syntax highlighting style info.
     */
    private DocumentStyle syntaxStyle;

    /**
     * Create a new {@code HighlightedDocument}.
     */
    public HighlightedDocument() {
        // Start the thread that does the coloring.
        colorer = new SyntaxHighlighter(this);
        colorer.start();
        // create the new document.
        documentReader = new DocumentReader(this);
    }

    /**
     * Color or recolor the entire document.
     */
    public void colorAll() {
        color(0, getLength());
    }

    /**
     * Color a section of the document. The actual coloring will start somewhere
     * before the requested position and continue as long as needed.
     *
     * @param position
     *            the starting point for the coloring.
     * @param adjustment
     *            amount of text inserted or removed at the starting point.
     */
    public void color(int position, int adjustment) {
        colorer.color(position, adjustment);
    }

    /**
     * Sets the syntax lexer and syntax highlighting style info for this document.
     * @param lexer The syntax lexer.
     * @param style The syntax highlighting style info.
     */
    public void setHighlightStyle(Object lexer, DocumentStyle style) {
        this.syntaxStyle = style;
        if (lexer == null) {
            this.syntaxLexer = null;
            SwingUtilities.invokeLater(() -> {
                setCharacterAttributes(0,
                        getLength(),
                        new SimpleAttributeSet(),
                        true);
            });
            return;
        }
        Class source = (Class) lexer;
        Class[] parms = {Reader.class};
        Object[] args = {documentReader};
        try {
            @SuppressWarnings("unchecked")
            Constructor cons = source.getConstructor(parms);
            syntaxLexer = (Lexer) cons.newInstance(args);
            globalStyle = null;
            colorAll();
        } catch (SecurityException e) {
            Logger.logWarning("SecurityException.", e);
        } catch (NoSuchMethodException e) {
            Logger.logWarning("NoSuchMethodException.", e);
        } catch (InstantiationException e) {
            Logger.logWarning("InstantiationException.", e);
        } catch (InvocationTargetException e) {
            Logger.logWarning("InvocationTargetException.", e);
        } catch (IllegalAccessException e) {
            Logger.logWarning("IllegalAccessException.", e);
        }
    }

    /**
     * Inserts some content into the document. Inserting content causes a document
     * lock to be held while the actual changes are taking place.
     * <p>
     * Based on current typing mode set in editor either it inserts or overrites content.      
     * @param offs The starting offset >= 0.
     * @param str The string to insert; does nothing with null/empty strings.
     * @param a The attributes for the inserted content.
     * @throws javax.swing.text.BadLocationException The given insert position is not a valid 
     *               position within the document.
     * @see TypingMode
     */
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws
            BadLocationException {
        if ((str == null) || (str.length() == 0)) {
            return;
        }
        synchronized (docLock) {
            TypingMode mode = (TypingMode) getProperty("TypingMode");
            // Fix for bug id 2895007 (sourceforge.net)
            if (mode == TypingMode.OVERWRITE && str.length() == 1 && !getText(
                    offs, 1).equals("\n") && offs != getLength()) {
                remove(offs, 1);
                super.insertString(offs, str, a);
            } else {
                super.insertString(offs, str, a);
            }
            color(offs, str.length());
            documentReader.update(offs, str.length());
        }
    }

    /**
     * Removes some content from the document. Removing content causes a document
     * lock to be held while the actual changes are taking place.
     * @param offs The starting offset >= 0.
     * @param len The number of characters to remove >= 0.
     * @throws javax.swing.text.BadLocationException The given remove position is not a valid 
     *               position within the document.
     */
    @Override
    public void remove(int offs, int len) throws BadLocationException {
        synchronized (docLock) {
            super.remove(offs, len);
            color(offs, -len);
            documentReader.update(offs, -len);
        }
    }

    /**    
     * Ensures color in a segment of document starting at specified offset for
     * a number of specified characters. It is called when a document update happens.
     * It causes a document lock to be held while the actual changes are taking place.
     * @param offs The starting offset >= 0.
     * @param len The number of characters to remove >= 0.
     * @throws javax.swing.text.BadLocationException The given position is not a valid 
     *               position within the document.
     */
    public void ensureColor(int offs, int len) throws BadLocationException {
        synchronized (docLock) {
            color(offs, len);
            documentReader.update(offs, len);
        }
    }

    /* Configuration Change Support  - Start */
    /**
     * Adds a syntax style configuration change listener to this document.
     * @param listener A syntax style configuration change listener.
     */
    public void addSyntaxStyleConfigChangeListener(
            SyntaxStyleConfigChangeListener listener) {
        EditorConfiguration.addSyntaxStyleConfigChangeListener(listener);
    }

    /**
     * Adds a document type configuration change listener to this document.
     * @param listener A document type configuration change listener.
     */
    public void addDocumentTypesConfigChangeListener(
            DocTypesConfigChangeListener listener) {
        EditorConfiguration.addDocumentTypesConfigChangeListener(listener);
    }

    /* Configuration Change Support - End */
    /**
     * Returns the document reader.
     * @return The document reader.
     */
    public DocumentReader getDocumentReader() {
        return documentReader;
    }

    /**
     * Returns the document lock. It is used while updating document style or content.
     * @return The document lock.
     */
    public Object getDocumentLock() {
        return docLock;
    }

    /**
     * Returns the syntax lexer to be used to perform lexical analysis on this document.
     * @return The syntax lexer. 
     */
    public Lexer getSyntaxLexer() {
        return syntaxLexer;
    }

    /**
     * Returns the global style. This is used when no synatx highlighting is not specified.
     * @return The global style.
     */
    public AttributeSet getGlobalStyle() {
        return globalStyle;
    }

    /**
     * Returns the syntax highlighting style info to be used for this document.
     * @return The synatx highlighting style info.
     * @see DocumentStyle
     */
    public DocumentStyle getSyntaxStyle() {
        return syntaxStyle;
    }
}
