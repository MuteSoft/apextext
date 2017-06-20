/*
 * EditorBody.java
 * Created on February 12, 2007, 9:53 PM 
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

import java.lang.reflect.InvocationTargetException;
import org.apex.base.constant.MenuConstants;
import org.apex.base.data.EditorContext;
import org.apex.base.ui.StatusBar;
import org.apex.base.component.ApexPanel;
import org.apex.base.component.ApexSplitPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import org.apex.base.constant.EditorKeyConstants;

/**
 * The main editor body which contains a document selector, edit area with line numbers,
 * output window and a status bar.
 * <p>
 * This is basically main UI part of editor other than menu-bar and tool-bar.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class EditorBody extends ApexPanel {

    /**
     * Left hand document selector. May be list view or tree view or some other type of view.
     */
    private DocumentSelector docSelector;
    /**
     * Main edit area.
     */
    private DocumentsWindow docsWindow;
    /**
     * The output window.
     */
    private OutputWindow outputWindow;
    /**
     * Quick help window. It is document type sensitive.
     */
    private PaletteWindow paletteWindow;
    /**
     * The output window split pane.
     */
    private ApexSplitPane outputWindowDivider;
    /**
     * The palette window split plane.
     */
    private ApexSplitPane paletteDivider;
    /**
     * The document selector window split pane.
     */
    private ApexSplitPane docSelectorDivider;

    /**
     * Creates a new instance of EditorBody.
     */
    public EditorBody() {
        createEditorBody(EditorBase.getContext());
    }

    /**
     * Creates the editor body.
     * <p>
     * It initializes document selector, edit area, output window and status bar.
     * @param context The editor context.
     */
    private void createEditorBody(final EditorContext context) {
        /**
         * Create Document selector.
         */
        this.docSelector = DocumentSelector.getInstance(
                DocumentSelector.LIST_VIEW);
        this.docSelector.setMinimumSize(new Dimension(200, 0));
        this.docSelector.setPreferredSize(new Dimension(200, 0));
        /**
         * Create Documents window.
         */
        this.docsWindow = new DocumentsWindow(context);

        /**
         * VERTICAL_SPLIT indicates split along y axis. So we will have top and bottom component in this case.
         * HORIZONTAL_SPLIT indicates split along x axis. So we will have left and right component in this case.
         * and for both cases we don't need continuous layout.
         */
        this.docSelectorDivider = new ApexSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                false);
        this.docSelectorDivider.setLeftComponent(this.docSelector);
        this.docSelectorDivider.setRightComponent(this.docsWindow);

        this.setLayout(new BorderLayout());
        this.add(this.docSelectorDivider, BorderLayout.CENTER);
        /*
         * Create Status bar.
         */
        StatusBar statusBar = new StatusBar();
        this.add(statusBar, BorderLayout.SOUTH);
        boolean viewDocumentSelector = context.getConfiguration().
                getGeneralConfig().getGeneral().isViewDocumentSelector();
        boolean viewStatusBar = context.getConfiguration().getGeneralConfig().
                getGeneral().isViewStatusBar();
        statusBar.setVisible(viewStatusBar);
        ActionManager.setActionSelected(MenuConstants.VIEW_STATUS_BAR,
                viewStatusBar);
        this.docSelector.setVisible(viewDocumentSelector);
        ActionManager.setActionSelected(MenuConstants.VIEW_DOCUMENT_SELECTOR,
                viewDocumentSelector);
        context.getEditorComponents().setStatusBar(statusBar);
        context.getEditorComponents().setEditorBody(this);
        ActionManager.setActionSelected(MenuConstants.OUTPUT_WINDOW, false);
        ActionManager.setActionSelected(MenuConstants.PALETTE_WINDOW, false);
    }

    /**
     * Returns the output window where various results are displayed.
     * @return The output window.
     * @see #setOutputWindow(org.apex.base.core.OutputWindow)
     */
    public synchronized OutputWindow getOutputWindow() {
        if (this.outputWindow == null) {
            if (SwingUtilities.isEventDispatchThread()) {
                createOutputWindow();
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {

                        public void run() {
                            createOutputWindow();
                        }
                    });
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return this.outputWindow;
    }

    /**
     * Create the output window on demand.
     */
    private void createOutputWindow() {
        this.outputWindow = new OutputWindow();
        outputWindow.setVisible(false);
        if (this.outputWindowDivider == null) {
            this.outputWindowDivider = new ApexSplitPane(
                    JSplitPane.VERTICAL_SPLIT,
                    false);
        }
        if (this.paletteDivider != null) {
            this.outputWindowDivider.setTopComponent(this.paletteDivider);
        } else {
            this.outputWindowDivider.setTopComponent(this.docsWindow);
        }
        this.outputWindowDivider.setBottomComponent(this.outputWindow);
        this.docSelectorDivider.setRightComponent(this.outputWindowDivider);
    }

    /**
     * Create the palette window on demand.
     */
    private void createPaletteWindow() {
        this.paletteWindow = new PaletteWindow();
        this.paletteWindow.setMinimumSize(new Dimension(150, 0));
        this.paletteWindow.setPreferredSize(new Dimension(150, 0));
        this.paletteWindow.setVisible(false);
        if (this.paletteDivider == null) {
            this.paletteDivider = new ApexSplitPane(
                    JSplitPane.HORIZONTAL_SPLIT,
                    false);
        }
        this.paletteDivider.setLeftComponent(this.docsWindow);
        this.paletteDivider.setRightComponent(this.paletteWindow);
        if (this.outputWindowDivider != null) {
            this.outputWindowDivider.setTopComponent(this.paletteDivider);
            try {
                this.outputWindowDivider.setDividerLocation((int) (EditorBase.
                        getContext().
                        getEditorComponents().
                        getFrame().getHeight() * .65));
            } catch (Exception ex) {
                // Do not care if setting divider location fails.
            }
        } else {
            this.docSelectorDivider.setRightComponent(this.paletteDivider);
        }
    }

    /**
     * Sets the output window where various results are displayed.
     * @param outputWindow The output window.
     * @see #getOutputWindow()
     */
    public synchronized void setOutputWindow(OutputWindow outputWindow) {
        this.outputWindow = outputWindow;
    }

    /**
     * Returns the document window where documents are displayed.
     * @return The document window.
     * @see #setDocsWindow(org.apex.base.core.DocumentsWindow)
     */
    public synchronized DocumentsWindow getDocsWindow() {
        return docsWindow;
    }

    /**
     * Sets the document window where documents are displayed.
     * @param docsWindow The document window.
     * @see #getDocsWindow()
     */
    public synchronized void setDocsWindow(DocumentsWindow docsWindow) {
        this.docsWindow = docsWindow;
    }

    /**
     * Returns left hand document selector. The document selector may display
     * document names in a list view or tree view depending on configuration.
     * @return The left hand document selector.
     * @see #setDocSelector(org.apex.base.core.DocumentSelector)
     */
    public synchronized DocumentSelector getDocSelector() {
        return docSelector;
    }

    /**
     * Sets left hand document selector. The document selector may display
     * document names in a list view or tree view depending on configuration.
     * @param docSelector The left hand document selector.
     * @see #getDocSelector()
     */
    public synchronized void setDocSelector(DocumentSelector docSelector) {
        this.docSelector = docSelector;
    }

    /**
     * Returns the quick help window. It is document type sensitive.
     * @return The quick help window.
     * @see #setPaletteWindow(org.apex.base.core.PaletteWindow)
     */
    public synchronized PaletteWindow getPaletteWindow() {
        if (this.paletteWindow == null) {
            if (SwingUtilities.isEventDispatchThread()) {
                createPaletteWindow();
            } else {
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {

                        public void run() {
                            createPaletteWindow();
                        }
                    });
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return this.paletteWindow;
    }

    /**
     * Sets the quick help window. It is document type sensitive.
     * @param quickHelpWindow The quick help window.
     * @see #getPaletteWindow()
     */
    public synchronized void setPaletteWindow(PaletteWindow quickHelpWindow) {
        this.paletteWindow = quickHelpWindow;
    }

    @Override
    public Insets getInsets() {
        return EditorKeyConstants.EDITOR_BODY_MARGIN;
    }
}
