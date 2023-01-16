/*
 * RecentFiles.java 
 * Created on 14 Feb, 2010, 9:47:47 PM
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
package org.apex.base.data;

import java.util.ArrayDeque;
import java.util.Iterator;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.core.RecentFilesConfiguration;
import org.apex.base.util.EditorUtil;

/**
 * The list of files opened in editor recently. It
 * maintains a list of fixed number of files and removes
 * older ones.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class RecentFiles {

    /**
     * The maximum number of files in the list.
     */
    private int numberOfFiles;
    /**
     * The list of files.
     */
    private ArrayDeque<Entry> files;
    /**
     * Recent files node.
     */
    private MenuNode recentFilesNode;
    /**
     * The self instance.
     */
    private static RecentFiles selfInstance;
    /**
     * Recent files configuration.
     */
    private RecentFilesConfiguration recentFilesConfig;

    /**
     * Creates a new instance of {@code RecentFiles} with given file count.
     * @param numberOfFiles Maximum number of recent files to be allowed.
     */
    private RecentFiles(int numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
        this.files = new ArrayDeque<Entry>(this.numberOfFiles);
    }

    /**
     * Initializes recent files list.
     * @param recentFilesConfig Recent files configuration.
     */
    public void init(RecentFilesConfiguration recentFilesConfig) {
        this.recentFilesConfig = recentFilesConfig;
        for (Iterator<String> recentFilesIterator = recentFilesConfig.
                getRecentFilesList().
                keySet().iterator();
                recentFilesIterator.hasNext();) {
            String absolutePath = recentFilesIterator.next();
            String displayName = recentFilesConfig.getRecentFilesList().get(
                    absolutePath);
            push(absolutePath, displayName);
        }
    }

    /**
     * It updates the recent files configuration with current document queue
     * and then returns the configuration object.
     * @return Up-to-date recent files configuration.
     */
    public RecentFilesConfiguration getUpdatedRecentFilesConfiguration() {
        this.recentFilesConfig.getRecentFilesList().clear();
        for (Iterator<Entry> filesIt = files.iterator(); filesIt.hasNext();) {
            Entry entry = filesIt.next();
            this.recentFilesConfig.addRecentFile(entry.getAbsolutePath(), entry.
                    getDisplayName());
        }
        return this.recentFilesConfig;
    }

    /**
     * Returns recent files configuration.
     * @return Recent files configuration.
     */
    public RecentFilesConfiguration getRecentFilesConfiguration() {
        return this.recentFilesConfig;
    }

    /**
     * Returns the singleton instance of this class.
     * @return Singleton instance of this class.
     */
    public static RecentFiles getSharedInstance() {
        if (selfInstance == null) {
            int maxRecentFiles = EditorBase.getContext().getConfiguration().
                    getGeneralConfig().getGeneral().getMaxRecentFilesCount();
            maxRecentFiles = maxRecentFiles == 0
                    ? EditorKeyConstants.MAX_RECENT_FILES
                    : maxRecentFiles;
            selfInstance = new RecentFiles(maxRecentFiles);
        }
        return selfInstance;
    }

    /**
     * Returns the size of the queue.
     * @return The size of the queue.
     */
    public int getSize() {
        return this.files.size();
    }

    /**
     * Returns the list of recent files. Each entry contains the display name and
     * absolute path of file.
     * @return An array of recent files.
     */
    public Entry[] getFiles() {
        return this.files.toArray(new Entry[0]);
    }

    /**
     * Adds the given document to recent document queue.
     * @param document The document to be added.
     */
    public void push(AbstractDocument document) {
        push(document.getAbsolutePath(), document.getName());
    }

    /**
     * Adds the given document to recent document queue. And in addition
     * to that it refreshes the menu bar as well.
     * @param document The document to be added.
     */
    public void pushAndRefreshMenuBar(AbstractDocument document) {
        push(document);
        // @TODO Don't refresh the entire menu bar. Instead only refresh recent files menu.
        EditorBase.getContext().getEditorComponents().getMenuBar().
                refreshMenuBar();
    }

    /**
     * Adds the given file represented by path and display name to the recent document
     * queue. And in addition to that it refreshes the menu bar as well.
     * @param path The absolute path of the file.
     * @param displayName Display name of file
     */
    public void pushAndRefreshMenuBar(String path, String displayName) {
        push(path, displayName);
        // @TODO Don't refresh the entire menu bar. Instead only refresh recent files menu.
        EditorBase.getContext().getEditorComponents().getMenuBar().
                refreshMenuBar();
    }

    /**
     * Adds the given file represented by path and display name to the recent document
     * queue.
     * @param path The absolute path of the file.
     * @param displayName Display name of file.
     */
    private void push(String path, String displayName) {
        boolean isExisting = isExisting(path, displayName);
        if (isExisting) {
            // @TODO Instead of returning, remove the existing entry and add the new entry at the top.
            return;
        }
        Entry toBeRemoved = null;
        if (this.getSize() + 1 > numberOfFiles) {
            toBeRemoved = this.files.pollLast();
        }
        // @TODO Following code requires optimization. For example,
        // 1. Do not search recent files node everytime
        // 2. Do not refresh the entire menu bar. Instead, refersh the recent files menu only
        // Add the menu and refresh the menubar
        if (recentFilesNode == null) {
            recentFilesNode = EditorBase.getContext().getCoreMenus().
                    getMenus().findChildMenuById("recentfiles");
        }
        if (toBeRemoved != null) {
            recentFilesNode.remove(EditorBase.getContext().getCoreMenus().
                    getMenus().findChildMenuById(toBeRemoved.getAbsolutePath()));
        }
        this.files.offerFirst(new Entry(displayName, path));
        MenuNode fileNode = createMenuNode();
        fileNode.setTargetType(ActionTarget.MENU);
        fileNode.setTarget("org.apex.base.menu.OpenFileMenu");
        recentFilesNode.insert(fileNode, 0);
    }

    /**
     * Creates the menu node.
     * @return The newly created menu node.
     */
    private MenuNode createMenuNode() {
        StringBuilder displayName = new StringBuilder();
        String path = this.files.peekFirst().getAbsolutePath();
        if (path.length() > 50) {
            displayName.append(path.substring(0, 10)).append("...");
            displayName.append(path.substring(path.length() - 25));
        } else {
            displayName.append(path);
        }
        MenuNode fileNode = new MenuNode(displayName.toString(),
                this.files.peekFirst().getAbsolutePath(),
                MenuType.MENUITEM);
        return fileNode;
    }

    /**
     * Determines if the document is existing in recent files list.
     * @param path The file path.
     * @param displayName Document display name.
     * @return {@code true} if the document is present in recent files list; otherwise returns {@code false}.
     */
    private boolean isExisting(String path, String displayName) {
        boolean isDocumentPathCaseSensitive = EditorUtil.isFilePathCaseSensitive();
        if (isDocumentPathCaseSensitive) {
            return files.contains(new Entry(displayName, path));
        }
        for (Iterator docIt = files.iterator(); docIt.hasNext();) {
            Entry documentKey = (Entry) docIt.next();
            if (documentKey.getAbsolutePath().equalsIgnoreCase(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The object stored in recent files list.
     */
    public class Entry {

        /**
         * Display name of the document.
         */
        private String displayName;
        /**
         * Absolute path of the document.
         */
        private String absolutePath;

        /**
         * Creates a new {@code Entry}.
         * @param displayName The display name of recent file entry.
         * @param absolutePath The absolute path of recent file entry.
         */
        public Entry(String displayName, String absolutePath) {
            this.displayName = displayName;
            this.absolutePath = absolutePath;
        }

        /**
         * Returns the absolute path of the document.
         * @return The absolute path of the document.
         * @see #setAbsolutePath(java.lang.String)
         */
        public String getAbsolutePath() {
            return absolutePath;
        }

        /**
         * Sets the absolute path of the document.
         * @param absolutePath The absolute path of the document.
         * @see #getAbsolutePath()
         */
        public void setAbsolutePath(String absolutePath) {
            this.absolutePath = absolutePath;
        }

        /**
         * Returns the display name of the document.
         * @return The display name of the document.
         * @see #setDisplayName(java.lang.String)
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         * Sets the display name of the document.
         * @param displayName The display name of the document.
         * @see #getDisplayName()
         */
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return "displayName: " + displayName + " ^absolutePath: " + absolutePath;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Entry) {
                Entry anotherEntry = (Entry) obj;
                return this.getAbsolutePath().equals(anotherEntry.
                        getAbsolutePath());
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 97 * hash + (this.displayName != null
                    ? this.displayName.hashCode()
                    : 0);
            hash = 97 * hash + (this.absolutePath != null
                    ? this.absolutePath.hashCode()
                    : 0);
            return hash;
        }
    }
}
