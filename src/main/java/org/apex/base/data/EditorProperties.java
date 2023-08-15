/*
 * EditorProperties.java
 * Created on December 20, 2006, 6:22 PM
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

import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import org.apex.base.core.EditorBase;
import org.apex.base.core.RecentFilesConfiguration;
import org.apex.base.core.RecentFilesConfigurationBuilder;
import org.apex.base.util.EditorUtil;

/**
 * Holds global data of edior. For example, it contains
 * a list of documents opened in the editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class EditorProperties {

    /**
     * Wrapper of currently displayed document.
     */
    private DocumentWrapper currentDocumentWrapper;
    /**
     * List of opened files.
     */
    private HashMap<String, DocumentWrapper> openDocumentsList =
            new HashMap<String, DocumentWrapper>();
    /**
     * One up sequence number for temporary files.
     */
    private int noOfTempFiles = 0;
    /**
     * A queue of documents based on last accessed timestamp.
     */
    private DocumentQueue docsQueue = new DocumentQueue(10);
    /**
     * A flag which indicates whether to propagate selection when a document is selected
     * either from left hand document selector or document tab.
     */
    private boolean propagateSelection = true;
    /**
     * Locale of editor. 
     */
    private Locale locale = Locale.US;
    /**
     * Used by FileChooser dialog while opening or saving a file.
     */
    private String directoryToBeOpened;
    /**
     * The list of recent files.
     */
    private RecentFiles recentFiles;

    /**
     * Returns currently displayed document.
     * @return Currently displayed document.
     */
    public synchronized AbstractDocument getCurrentDocument() {
        if (this.getCurrentDocumentWrapper() == null) {
            return null;
        }
        return this.getCurrentDocumentWrapper().getDocument();
    }

    /**
     * Increments serial number of temporary documents.
     * And then returns next serial number of temporary documents.
     * @return Next serial number of temporary documents.
     */
    public synchronized int getNextTempFileSrlNumber() {
        this.noOfTempFiles++;
        return this.noOfTempFiles - 1;
    }

    /**
     * Returns a table of currently opened documents.
     * @return A table of currently opened documents.
     */
    // Fix for bug id 2352984 (sourceforge.net)
    // This method is made private and a set of public methods are added to retrieve, remove, inquire
    // about open documents.
    private synchronized HashMap<String, DocumentWrapper> getOpenDocumentsList() {
        return openDocumentsList;
    }

    /**
     * Returns an iterator created from open document list keysets.
     * @return An iterator created from open document list.
     */
    public Iterator getOpenDocumentIterator() {
        return getOpenDocumentsList().keySet().iterator();
    }

    /**
     * Returns the document wrapper represented by given absolute path from the open document list.
     * @param absolutePath The absolute path of the file.
     * @return The document wrapper if exists in open document list else returns {@code null}.
     */
    public DocumentWrapper getOpenDocumentWrapper(String absolutePath) {
        boolean isDocumentPathCaseSensitive = EditorUtil.isFilePathCaseSensitive();
        if (isDocumentPathCaseSensitive) {
            return getOpenDocumentsList().get(absolutePath);
        }
        for (Iterator docIt = getOpenDocumentsList().keySet().iterator(); docIt.
                hasNext();) {
            String documentKey = (String) docIt.next();
            if (documentKey.equalsIgnoreCase(absolutePath)) {
                return getOpenDocumentsList().get(documentKey);
            }
        }
        return null;
    }

    /**
     * Returns the document represented by given absolute path from the open document list.
     * @param absolutePath The absolute path of the file.
     * @return The document if exists in open document list else returns {@code null}.
     */
    public AbstractDocument getOpenDocument(String absolutePath) {
        DocumentWrapper documentWrapper = getOpenDocumentWrapper(absolutePath);
        return documentWrapper == null
                ? null
                : documentWrapper.getDocument();
    }

    /**
     * Determines whether the document represented by given absolute path is open in the editor.
     * @param absolutePath The absolute path of the file.
     * @return {@code true} if the document represented by the given absolute path is open in editor;
     *              otherwise returns {@code false}.
     */
    public boolean isDocumentOpen(String absolutePath) {
        return getOpenDocument(absolutePath) != null;
    }

    /**
     * Removes the document from open document list.
     * @param absolutePath The absolute path of the file.
     * @return {@code true} if document is removed successfully; otherwise returns {@code false}.
     */
    public boolean removeOpenDocument(String absolutePath) {
        boolean isDocumentPathCaseSensitive = EditorUtil.isFilePathCaseSensitive();
        if (isDocumentPathCaseSensitive) {
            getOpenDocumentsList().remove(absolutePath);
        } else {
            for (Iterator docIt = getOpenDocumentsList().keySet().iterator(); docIt.
                    hasNext();) {
                String documentKey = (String) docIt.next();
                if (documentKey.equalsIgnoreCase(absolutePath)) {
                    getOpenDocumentsList().remove(documentKey);
                    break;
                }
            }
        }
        return true;
    }

    /**
     * Adds a document to open document list. It replaces if a document with same absolute path
     * exists already.
     * @param absolutePath The absolute path of the file.
     * @param documentWrapper The document wrapper.
     */
    public void addOpenDocument(String absolutePath,
            DocumentWrapper documentWrapper) {
        AbstractDocument document = getOpenDocument(absolutePath);
        if (document != null) {
            removeOpenDocument(absolutePath);
        }
        getOpenDocumentsList().put(absolutePath, documentWrapper);
    }

    /**
     * Determines whether or not at least one document is open in the editor.
     * @return {@code true} if at least one document is open; otherwise returns {@code false}.
     */
    public boolean isAtleastOneDocumentOpen() {
        return getNoOfOpenDocuments() > 0;
    }

    /**
     * Returns number of open documents.
     * @return Number of documents open in editor.
     */
    public int getNoOfOpenDocuments() {
        return getOpenDocumentsList().size();
    }

    /**
     * Returns the document queue.
     * This queue contains documents based on their last accessed timestamp.
     * @return The document queue.
     */
    public synchronized DocumentQueue getDocsQueue() {
        return docsQueue;
    }

    /**
     * Returns the Locale being used by editor.
     * @return The {@code Locale} used by this editor.
     * @see #setLocale(java.util.Locale) 
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Sets the Locale to be used by editor.
     * <p>
     * <strong>Warning:</strong> This method must be called only when
     * the application is just starting and not realized yet. At runtime change of {@code Locale}
     * may not be effective.
     * @param locale The {@code Locale} to be used by this editor.
     * @see #getLocale() 
     */
    public void setLocale(Locale locale) {
        if (locale != null) {
            this.locale = locale;
        }
    }

    /**
     * Returns whether document selection (either from left hand document selector or
     * document tab) should be propagated.
     * @return {@code true} if selection should be propagated, {@code false} otherwise.
     */
    public boolean isPropagateSelection() {
        return propagateSelection;
    }

    /**
     * Returns whether document selection (either from left hand document selector or
     * document tab) should be propagated.
     * @param propagateSelection Document selection propagation indicator.
     */
    public void setPropagateSelection(boolean propagateSelection) {
        this.propagateSelection = propagateSelection;
    }

    /**
     * Returns the wrapper of currently displayed document.
     * @return The wrapper of currently displayed document.
     * @see #setCurrentDocumentWrapper(org.apex.base.data.DocumentWrapper)
     */
    public synchronized DocumentWrapper getCurrentDocumentWrapper() {
        return currentDocumentWrapper;
    }

    /**
     * Sets the wrapper of currently displayed document.
     * @param currentDocumentWrapper The wrapper of currently displayed document.
     * @see #getCurrentDocumentWrapper() 
     */
    public synchronized void setCurrentDocumentWrapper(
            DocumentWrapper currentDocumentWrapper) {
        this.currentDocumentWrapper = currentDocumentWrapper;
    }

    /**
     * Returns the directory to be opened in FileChooser dialog.
     * This is used by FileChooser dialog while opening or saving a file.
     * @return Directory to be opened in FileChooser dialog.
     * @see #setDirectoryToBeOpened(java.lang.String) 
     */
    public synchronized String getDirectoryToBeOpened() {
        return directoryToBeOpened;
    }

    /**
     * Sets the directory to be opened in FileChooser dialog.
     * This is used by FileChooser dialog while opening or saving a file.
     * @param directoryToBeOpened The directory to be opened in FileChooser dialog.
     * @see #getDirectoryToBeOpened() 
     */
    public synchronized void setDirectoryToBeOpened(String directoryToBeOpened) {
        this.directoryToBeOpened = directoryToBeOpened;
    }

    /**
     * Returns title of editor.
     * @return Title of editor.
     */
    public abstract String getEditorTitle();

    /**
     * Returns the version of editor.
     * @return The editor version.
     */
    public abstract String getEditorVersion();

    /**
     * Returns the icon image of editor.
     * @return The icon image of editor.
     */
    public abstract Image getEditorIcon();

    /**
     * Returns the support URL.
     * @return The support page URL of the editor.
     */
    public abstract String getEditorSupportUrl();

    /**
     * Returns the URL of the source code repository.
     *
     * @return The source code repository URL of the editor.
     */
    public abstract String getEditorSourceRepoUrl();

    /**
     * Returns the base class.
     * @return The editor base class.
     */
    public abstract Class<? extends EditorBase> getEditorBaseClass();

    /**
     * Returns a shared default instance of document type base class.
     * <p>
     * This method can be overriten by sub systems to support a desired
     * set of document types. In sub systems make sure to return shared instance.
     * @return An instance of document type base class.
     */
    public IDocumentType getDocumentTypeBase() {
        return DocumentType.getSharedInstance();
    }

    /**
     * Returns the list of recent files.
     * @return The list of recent files.
     */
    public RecentFiles getRecentFiles() {
        if (recentFiles == null) {
            this.recentFiles = RecentFiles.getSharedInstance();
            this.recentFiles.init((RecentFilesConfiguration) new RecentFilesConfigurationBuilder().
                    build());
        }
        return this.recentFiles;
    }
}
