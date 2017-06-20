/*
 * SearchFile.java 
 * Created on 19 Oct, 2009, 10:11:43 PM
 *
 * Copyright (C) 2009 Mrityunjoy Saha
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

import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import org.apex.base.component.ApexLabel;
import org.apex.base.core.EditorBase;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.EditorContext;
import org.apex.base.event.GoToFileEventHandler;
import org.apex.base.ui.text.GoToFileModel;
import org.apex.base.util.StringUtil;

/**
 * A class to search a file name in a given list.
 * <p>
 * The search is case insensitive and allows wild card characters - "*" and "?".
 * @author mrityunjoy_saha
 * @version 1.2
 * @since Apex 1.2
 */
public class SearchFile {

    /**
     * The data model for goto file.
     */
    private GoToFileModel model = new GoToFileModel();
    /**
     * A goto file event handler.
     */
    private GoToFileEventHandler goToFileEvent;
    /**
     * Cache the pattern obtained from search string.
     */
    private Pattern searchPattern;

    /**
     * Constructs a new instance of {@code SearchFile}.
     */
    public SearchFile() {
        goToFileEvent = new GoToFileEventHandler();
    }

    /**
     * Returns the editor context.
     * @return Th editor context.
     */
    public EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Filters the document list based on search text.
     * <p>
     * Filtration supports wild card characters - * and ?
     * @param searchString The search text.
     * @return The filtered list of documents.
     */
    public Vector<ApexLabel> filterFileList(String searchString) {
        createSearchPattern(searchString);
        if (StringUtil.isNullOrEmpty(searchString)) {
            return new Vector<ApexLabel>();
        }
        Vector<ApexLabel> filteredList = new Vector<ApexLabel>();        
        for (Iterator docsIterator = getContext().getEditorProperties().
                getOpenDocumentIterator(); docsIterator.hasNext();) {
            String docName = (String) docsIterator.next();
            AbstractDocument document = getContext().getEditorProperties().getOpenDocument(docName);
            ApexLabel label = null;
            if (isMatched(document.getDisplayName())) {
                label = new ApexLabel(document.getDisplayName());
                label.setToolTipText(docName);
                filteredList.add(label);
            }
        }
        return filteredList;
    }

    /**
     * Navigates to a target document selected by user from list.
     */
    public void performGoToFile() {
        goToFileEvent.execute(this.model);
    }

    /**
     * Returns the data object for file search operation.
     * @return The data model used for file search file operation.
     */
    public GoToFileModel getModel() {
        return this.model;
    }

    /**
     * Returns the default data model for document list.
     * @return The data model for document list.
     */
    public ListModel getFileListModel() {
        return getFileListModel(new Vector<ApexLabel>());
    }

    /**
     * Returns the data model for document list.
     * @param list List of files.
     * @return The data model for document list.
     */
    public ListModel getFileListModel(final Vector<ApexLabel> list) {
        return new AbstractListModel() {

            Vector<ApexLabel> tempList = list;

            public Object getElementAt(int index) {
                return tempList.get(index);
            }

            public int getSize() {
                return tempList.size();
            }
        };
    }

    /**
     * Creates the search pattern based on search string.
     * <p>
     * The pattern is created once for a given search string and the same
     * pattern object is used to search the list.
     * @param pattern The search string.
     * @since Apex 1.2
     */
    private void createSearchPattern(String pattern) {
        pattern = pattern + "*";
        pattern = pattern.replace("*", ".*");
        pattern = pattern.replaceAll("\\?", ".?");
        this.searchPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }

    /**
     * Determines whether or not search key is part of a given document display name.
     * <p>
     * It checks whether the document name starts with search key.
     * @param displayName A document display name.
     * @return {@code true} if search key is part of given document display name; otherwise
     *               returns {@code false}.
     */
    private boolean isMatched(String displayName) {
        Matcher searchStringMatcher = this.searchPattern.matcher(displayName);
        return searchStringMatcher.matches();
    }
}
