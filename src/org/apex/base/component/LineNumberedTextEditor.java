/*
 * LineNumberedTextEditor.java
 * Created on 7 July, 2007, 1:00 AM
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

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.settings.FontStyleConfiguration;
import org.apex.base.settings.GeneralSectionConfiguration;
import org.apex.base.settings.event.FontStyleConfigChangeEvent;
import org.apex.base.settings.event.FontStyleConfigChangeListener;
import org.apex.base.settings.event.GeneralSectionConfigChangeEvent;
import org.apex.base.settings.event.GeneralSectionConfigChangeListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.StyledDocument;

/**
 * A component which encapsulates the text editor and the line number display area.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class LineNumberedTextEditor extends ApexPanel {

    /**
     * The text editor.
     */
    private TextEditor editArea;
    /**
     * The component which holds the text editor.
     */
    private ApexScrollPane editAreaScrollPane;
    /**
     * A panel to display line numbers.
     */
    private JComponent lineNoArea;

    /**
     * Creates a new instance of {@code LineNumberedTextEditor} using specified document.
     * @param doc The document data model for text editor.
     */
    public LineNumberedTextEditor(StyledDocument doc) {
        this(doc, true);
    }

    /**
     * Creates a new instance of {@code LineNumberedTextEditor} using specified document.
     * @param doc The document data model for text editor.
     * @param displayLineNumber A boolean indicating whether or not to display line number area.
     */
    public LineNumberedTextEditor(StyledDocument doc, boolean displayLineNumber) {
        editArea = new TextEditor(doc) {

            @Override
            public void paint(Graphics g) {
                super.paint(g);
            }
        };        
        final GeneralSectionConfiguration generalSection =
                getContext().getConfiguration().getGeneralConfig().getGeneral();
        editArea.updateCaretWidth(generalSection.getCaretWidth());
        editArea.updateCaretColor(generalSection.getCaretColor());
        // Add general configuration change listener
        editArea.addGeneralSectionConfigChangeListener(new GeneralSectionConfigChangeListener() {

            public void propertyValueChanged(
                    GeneralSectionConfigChangeEvent event) {
                /*
                 * Commenting below two lines. However, this is the correct approach. We should
                 * receive the changed data from event object. Currently this is not available.
                 */
                GeneralSectionConfiguration genSecConfig =
                        generalSection;
                editArea.updateCaretWidth(genSecConfig.getCaretWidth());
                editArea.updateCaretColor(genSecConfig.getCaretColor());
                // Update tab size.
                editArea.setTabSize(genSecConfig.getTabSize());
            }
        });
        editArea.setFont(getContext().getConfiguration().getStyleConfig().
                getFontStyle().getFont());
        editArea.setTabSize(generalSection.getTabSize());

        // Add style configuration change listener
        editArea.addFontStyleConfigChangeListener(new FontStyleConfigChangeListener() {

            public void propertyValueChanged(FontStyleConfigChangeEvent event) {
                FontStyleConfiguration fontStyleConfig =
                        getContext().getConfiguration().
                        getStyleConfig().
                        getFontStyle();
                // Update Font
                editArea.setFont(fontStyleConfig.getFont());
            }
        });
        editAreaScrollPane =
                new ApexScrollPane(editArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        editAreaScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        lineNoArea = new LineNumberView(this.getEditArea());
        editAreaScrollPane.setRowHeaderView(lineNoArea);
        this.setLayout(new BorderLayout());
        this.add(editAreaScrollPane, BorderLayout.CENTER);
    }

    /**
     * Returns the text editor.
     * @return The text editor.
     * @see #setEditArea(org.apex.base.component.TextEditor)
     */
    public TextEditor getEditArea() {
        return editArea;
    }

    /**
     * Sets the text editor.
     * @param editArea The text editor.
     * @see #getEditArea() 
     */
    public void setEditArea(TextEditor editArea) {
        this.editArea = editArea;
    }

    /**
     * Returns the scroll pane which holds the text editor.
     * @return The scroll pane which holds the text editor.
     * @see #setEditAreaScrollPane(org.apex.base.component.ApexScrollPane)
     */
    public ApexScrollPane getEditAreaScrollPane() {
        return editAreaScrollPane;
    }

    /**
     * Sets the scroll pane which holds the text editor.
     * @param editAreaScrollPane The scroll pane which holds the text editor.
     * @see #getEditAreaScrollPane()      
     */
    public void setEditAreaScrollPane(ApexScrollPane editAreaScrollPane) {
        this.editAreaScrollPane = editAreaScrollPane;
    }

    /**
     * Returns the line number display area.
     * @return The line number display area.
     * @see #setLineNoArea(javax.swing.JComponent)
     */
    public JComponent getLineNoArea() {
        return lineNoArea;
    }

    /**
     * Sets the line number display area.
     * @param lineNoArea The line number display area.
     * @see #getLineNoArea()
     */
    public void setLineNoArea(JComponent lineNoArea) {
        this.lineNoArea = lineNoArea;
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }
}
