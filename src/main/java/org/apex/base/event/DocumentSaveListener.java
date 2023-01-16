/*
 * DocumentSaveListener.java
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
package org.apex.base.event;

import org.apex.base.data.DocumentWrapper;
import org.apex.base.data.EditorContext;
import org.apex.base.util.FileUtil;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A listener to track document changes. If a saved document is changed
 * it enables the 'Save' menu to indicate document is unsaved now. For a unsaved
 * document, change in document content has no effect.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentSaveListener implements DocumentListener {

    /**
     * The editor context.
     */
    private EditorContext context;
    /**
     * The document wrapper.
     */
    private DocumentWrapper documentWrapper;

    /**
     * Creates a new instance of {@code DocumentSaveListener} using specified document
     * wrapper and editor context.
     * @param context The editor context.
     * @param documentWrapper A document wrapper.
     * 
     */
    public DocumentSaveListener(EditorContext context,
                                DocumentWrapper documentWrapper) {
        this.context = context;
        this.documentWrapper = documentWrapper;
    }

    /**
     * Marks the document as unsaved.
     * @param e The document event.
     */
    public void insertUpdate(DocumentEvent e) {
        FileUtil.markAsUnsaved(context, this.documentWrapper.getDocument());
    }

    /**
     * Marks the document as unsaved.
     * @param e The document event.
     */
    public void removeUpdate(DocumentEvent e) {
        FileUtil.markAsUnsaved(context, this.documentWrapper.getDocument());
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     * @param e The document event.
     */
    public void changedUpdate(DocumentEvent e) {
    }
}