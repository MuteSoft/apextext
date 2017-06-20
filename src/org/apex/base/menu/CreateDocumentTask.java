/*
 * CreateDocumentTask.java
 * Created on 23 June, 2007, 1:46 AM
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
package org.apex.base.menu;

import org.apex.base.data.AbstractDocument;
import org.apex.base.component.CompoundUndoManager;
import org.apex.base.common.Task;
import org.apex.base.component.LineNumberedTextEditor;
import org.apex.base.constant.CommonConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.data.HighlightedDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.util.TabUtil;
import org.apex.base.component.DocumentTabComponent;
import org.apex.base.component.ApexTabbedPane;
import org.apex.base.component.TextEditor;
import org.apex.base.data.DocumentWrapper;
import org.apex.base.event.DocumentExtensionChangeListener;
import org.apex.base.event.DocumentSyntaxStyleChangeListener;
import org.apex.base.event.DocumentSaveListener;

/**
 * Creates a document and associates it with a suitable editor.
 * <p>
 * Creates a tab for newly created document and adds the same to editors tabbed pane. The synatx
 * highlighter, code completion action, line number display etc. are installed to document's editor. 
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CreateDocumentTask implements Task {

    /**
     * Creates a new instance of CreateDocumentTask.
     */
    public CreateDocumentTask() {
    }

    /**
     * Creates a document and associates it with a suitable editor.
     * <p>
     * Creates a tab for newly created document and adds the same to editors tabbed pane. The synatx
     * highlighter, code completion action, line number display etc. are installed to document's editor. 
     * @param in Input parameters.
     * @param out Output parameters.
     */
    public void preProcess(InputParams in, OutputParams out) {
        final EditorContext context = EditorBase.getContext();
        final DocumentWrapper documentWrapper =
                (DocumentWrapper) in.get(CommonConstants.FILE_WRAPPER);
        AbstractDocument file = documentWrapper.getDocument();
        ApexTabbedPane tabbedPane =
                context.getEditorComponents().getEditorBody().
                getDocsWindow().
                getDocsTabbedPane();
        final HighlightedDocument doc =
                new HighlightedDocument();
        // Set the highlight style
        doc.setHighlightStyle(file.getLexer(),
                file.getDocumentStyle());

        //  To support line number.       
        LineNumberedTextEditor lineNumberedTextPane =
                new LineNumberedTextEditor(doc);
        TextEditor editArea = lineNumberedTextPane.getEditArea();
        String toolTip = null;
        if (file.isTemporary()) {
            toolTip = file.getName();
        } else {
            toolTip = file.getAbsolutePath();
        }
        tabbedPane.addTab(TabUtil.newTab(file.getName(),
                null, lineNumberedTextPane, toolTip,
                new DocumentTabComponent(tabbedPane, file)));

        file.setEditor(editArea);
        file.setLineNumberArea(lineNumberedTextPane.getLineNoArea());
        file.setComponent(lineNumberedTextPane);
    }

    public void postProcess(InputParams in, OutputParams out) {
        final EditorContext context = EditorBase.getContext();
        final DocumentWrapper documentWrapper =
                (DocumentWrapper) in.get(CommonConstants.FILE_WRAPPER);
        AbstractDocument file = documentWrapper.getDocument();
        HighlightedDocument doc = (HighlightedDocument) file.getEditor().
                getDocument();
        LineNumberedTextEditor lineNumberedTextPane = (LineNumberedTextEditor) file.
                getComponent();
        // Menu State change for document change
        doc.addDocumentListener(new DocumentSaveListener(context,
                documentWrapper));

        // Add Document Type - Extension mapping Change Listener
        doc.addDocumentTypesConfigChangeListener(new DocumentExtensionChangeListener(
                documentWrapper));
        // Add Syntax Style Listener
        doc.addSyntaxStyleConfigChangeListener(new DocumentSyntaxStyleChangeListener(
                documentWrapper));
        TextEditor editArea = lineNumberedTextPane.getEditArea();
        // Create the undo manager for this edit area
        CompoundUndoManager undoManager =
                new CompoundUndoManager(editArea);
        file.setUndoManager(undoManager);
        editArea.addListeners();
        //file.setLastSaved(DateTime.getNow());
        // Request focus for the main edit area when a document is just created.
        editArea.requestFocusInWindow();

        // If the file is read only make the edit area non-editable.
        if (!file.isTemporary() && !file.canWrite()) {
            editArea.setEditable(false);
        }
    }
}
