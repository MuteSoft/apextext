/*
 * PaletteWindow.java
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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultStyledDocument;
import org.apex.base.component.ApexPanel;
import org.apex.base.component.ApexScrollPane;
import org.apex.base.component.TextEditor;
import org.apex.base.component.WindowTitleBar;
import org.apex.base.constant.MenuConstants;
import org.apex.base.data.EditorContext;
import org.apex.base.ui.PaletteView;

/**
 * Super class for help window (palette) for diffrent languages.
 * For example an HTML help window may contain table, html, tr, a tags etc.
 * Internal templates are maintained for this purpose. 
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class PaletteWindow extends ApexPanel {

    /**
     * The palette view.
     */
    private PaletteView paletteView;

    /**
     * Creates a new instance of {@code PaletteWindow}.
     */
    public PaletteWindow() {
        createOutputWindow();
    }

    /**
     * Creates the output window.
     * <p>
     * Adds console tab to output window.
     */
    @SuppressWarnings("unchecked")
    private void createOutputWindow() {
        this.setLayout(new BorderLayout());
        //this.setFocusable(true);
        paletteView = new PaletteView();
        //outputTabbedPane.addChangeListener(new OutputTabChangeListener());

        // Add a temporary tab. Remove this later and make it dynamic : Start.
        TextEditor resultArea = new TextEditor();
        resultArea.setDocument(new DefaultStyledDocument());
        resultArea.setTabSize(4);
        ApexScrollPane resultScrollPane = new ApexScrollPane(resultArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultScrollPane.getVerticalScrollBar().setUnitIncrement(20);

        // Create the title bar.
        JPanel titleBar = new WindowTitleBar("Palette") {

            protected void executeOnClick(ActionEvent e) {
                getContext().getEditorComponents().getEditorBody().
                        getPaletteWindow().setVisible(
                        false);
                ActionManager.setActionSelected(MenuConstants.PALETTE_WINDOW,
                        false);
            }
        };
        titleBar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
//                    if (getParent() instanceof ApexSplitPane) {
//                        ApexSplitPane splitPane = (ApexSplitPane) getParent();
//                        if (splitPane.getDividerLocation() <= 100) {
//                            splitPane.setDividerLocation((int) (EditorBase.
//                                    getContext().
//                                    getEditorComponents().
//                                    getFrame().getHeight() * .65));
//
//                        } else {
//                            splitPane.setDividerLocation(100);
//                        }
//                    }
                }
            }
        });
        this.add(titleBar, BorderLayout.NORTH);
        this.add(paletteView);
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    public EditorContext getContext() {
        return EditorBase.getContext();
    }
}
