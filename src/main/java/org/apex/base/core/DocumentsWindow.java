/*
 * DocumentsWindow.java
 * Created on 23 June, 2007, 11:12 AM 
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
package org.apex.base.core;

import org.apex.base.data.EditorContext;
import org.apex.base.event.DocumentTabChangeListener;
import org.apex.base.component.ApexPanel;
import org.apex.base.component.ApexTabbedPane;
import java.awt.BorderLayout;

/**
 * This is the main window of editor where a {@link ApexTabbedPane} is placed.
 * All document tabs are added to this tabbed pane.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentsWindow extends ApexPanel {

    /**
     * The tabbed pane where all document tabs are added.
     */
    private ApexTabbedPane docsTabbedPane;

    /**
     * Constructs the document window.
     * @param context The editor context.
     */
    public DocumentsWindow(EditorContext context) {
        super();
        createDocumentsWindow(context);
    }

    /**
     * Creates the document window.
     * <p>
     * It places the tabbed pane at extreme north.
     * @param context The editor context.
     */
    private void createDocumentsWindow(EditorContext context) {
        this.setLayout(new BorderLayout());
        docsTabbedPane = new ApexTabbedPane(ApexTabbedPane.TOP, false, false);
        docsTabbedPane.addChangeListener(new DocumentTabChangeListener());
        this.add(docsTabbedPane);
    }

    /**
     * Returns the {@code ApexTabbedPane} which contains all document tabs.
     * @return The document tabs holder.
     */
    public ApexTabbedPane getDocsTabbedPane() {
        return docsTabbedPane;
    }
}
