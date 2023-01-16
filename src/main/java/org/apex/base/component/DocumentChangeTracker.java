/*
 * DocumentChangeTracker.java 
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
package org.apex.base.component;

import org.apex.base.common.CommonMessageManager;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.core.MenuManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.DocumentWrapper;
import org.apex.base.data.EditorContext;
import org.apex.base.menu.OpenFileMenu;
import org.apex.base.util.FileUtil;
import org.apex.base.util.MenuUtil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * A class to track changes in document content by external applications.
 * It tracks changes of currently displayed document in a pre-defined intervals. If a document
 * is changed a confirmation message is displayed to user whether user wants to re-load
 * the document from file system.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentChangeTracker implements ActionListener {

    /**
     * A reference to itself.
     */
    private static DocumentChangeTracker docChangeTracker;
    /**
     * The timer used to generate {@code ActionEvent}s at specified intervals.
     */
    private Timer timer;

    /**
     * Creates a new instance of {@code DocumentChangeTracker}. It initializes the
     * {@code timer} which is internally used to generate {@code ActionEvent}s
     * at specified intervals.
     */
    public DocumentChangeTracker() {
        timer = new Timer(EditorKeyConstants.DOCUMENT_CHANGE_TRACKING_INTERVAL,
                this);
    }

    /**
     * Returns the timer.
     * @return A timer used to generate {@code ActionEvent}s
     * at specified intervals.
     */
    public Timer getTimer() {
        return this.timer;
    }

    /**
     * Determines whether or not a given document is changed. It compares document's
     * last saved time in editor and last modification time in file system.
     * @param document The document.
     * @return {@code true} if the document is changed; otherwise returns {@code false}.
     */
    public boolean isDocumentChanged(AbstractDocument document) {
        System.out.println("Printing: document.lastModified(): "+document.lastModified());
        System.out.println("Printing: document.getLastSaved(): "+document.getLastSaved());
        System.out.println("Printing: document.isReadWriteInProgress(): "+document.isReadWriteInProgress());
        boolean b= (Math.abs(document.lastModified() - document.getLastSaved())
                > EditorKeyConstants.DOCUMENT_CHANGE_TRACKING_TOLERANCE) && !document.
                isReadWriteInProgress();
        return b;
    }

    /**
     * It tracks changes of currently displayed document in a pre-defined intervals. If a document
     * is changed a confirmation message is displayed to user, whether user wants to re-load
     * the document from file system.
     * @param evt The action event.
     */
    public void actionPerformed(ActionEvent evt) {
        if (isEditorVisible()) {
            AbstractDocument document = getCurrentDocument();
            if (document == null || document.isTemporary()) {
                return;
            }
            if (!document.exists()) {
                getContext().getEditorProperties().getDocsQueue().remove(document.
                        getAbsolutePath());
                DocumentWrapper docWrapper = getContext().getEditorProperties().
                        getOpenDocumentWrapper(document.getAbsolutePath());
                getContext().getEditorProperties().removeOpenDocument(document.
                        getAbsolutePath());
                document.setTemporary(true);
                getContext().getEditorProperties().addOpenDocument(document.
                        getAbsolutePath(),
                        docWrapper);
                FileUtil.markAsUnsaved(getContext(), document);
                MenuUtil.updateMenuStatus(getContext(), document);
                CommonMessageManager.showWarningMessage(
                        getContext().getEditorComponents().getFrame(), 1004,
                        "FILE=" + document.getName());
            } else {
                // See whether read only status changed by external application
                boolean w = document.canWrite();
                boolean e = document.getEditor().isEditable();
                if (w != e) {
                    document.getEditor().setEditable(w);
                    document.getEditor().getCaret().setVisible(w);
                    getContext().getEditorComponents().getStatusBar().
                            setWritableStatus(w);
                }
                if (isDocumentChanged(document)) {
                    // Show Warning if user wants to reload the document
                    int choice = CommonMessageManager.showConfirmMessage(getContext().
                            getEditorComponents().getFrame(), 1003,
                            "FILE=" + document.getName());
                    // If 'NO' then quit            
                    // If yes then read the document and load
                    if (choice == JOptionPane.YES_OPTION) {
                        ((OpenFileMenu) MenuManager.getMenuById(
                                MenuConstants.OPEN_FILE)).openFileFromDisk(
                                document);
                    } else {
                        document.setLastSaved(document.lastModified());
                    }
                }
            }
        }
    }

    /**
     * Returns a singleton instance of this class.
     * @return A singleton instance.
     */
    public static DocumentChangeTracker getInstance() {
        if (docChangeTracker == null) {
            docChangeTracker = new DocumentChangeTracker();
        }
        return docChangeTracker;
    }

    /**
     * Returns the currently displayed document in editor.
     * @return The currently displayed document.
     */
    private AbstractDocument getCurrentDocument() {
        return getContext().getEditorProperties().getCurrentDocument();
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    private EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Determines whether or not the editor screen is visible.
     * @return {@code true} if edtor window is current;y cisible.
     */
    private boolean isEditorVisible() {
        return getContext().getEditorComponents().getFrame().isFocused();
    }
}
