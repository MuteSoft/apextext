/*
 * WindowTitleBar.java
 * Created on 26 June, 2007, 12:28 AM
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

import java.awt.event.MouseEvent;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.settings.event.HighlightStyleConfigChangeEvent;
import org.apex.base.util.ImageCreator;
import org.apex.base.constant.FontConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import org.apex.base.data.HighlightCategories;
import org.apex.base.settings.AbstractConfiguration;
import org.apex.base.settings.HighlightColor;
import org.apex.base.settings.event.HighlightStyleConfigChangeListener;

/**
 * A base class to create the title bar with a title and and a close button
 * at top right end. This is similar to title bar of a frame.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public abstract class WindowTitleBar extends ApexPanel {

    /**
     * Title of the window.
     */
    private ApexLabel titleLabel;

    /**
     * Creates a new title bar with given title.
     * <p>
     * By default a close button is created and placed in title bar.
     * @param title Title of bar.
     */
    public WindowTitleBar(String title) {
        this(title, true);
    }

    /**
     * Creates a new title bar with given title and a flag that indicates
     * whether or not close button required.
     * @param title Title of bar.
     * @param buttonRequired A boolean that indicates whether or not close button is required.
     */
    public WindowTitleBar(String title,
            boolean buttonRequired) {
        createTitleBar(title, buttonRequired);
    }

    /**
     * Creates a new title bar with given title and a flag that indicates
     * whether or not close button required.
     * @param title Title of bar.
     * @param buttonRequired A boolean that indicates whether or not close button is required.
     */
    private void createTitleBar(String title,
            boolean buttonRequired) {
        this.setLayout(new BorderLayout());

        titleLabel = new ApexLabel(title);
        titleLabel.setFont(FontConstants.WINDOW_TITLE_FONT);
        HighlightColor highlightColor = EditorBase.getContext().getConfiguration().
                getStyleConfig().
                getHighlightStyle().
                getHighlightColor(HighlightCategories.WINDOW_TITLE_BAR);
        this.setBackground(highlightColor.getBackground());
        titleLabel.setForeground(highlightColor.getForeground());
        AbstractConfiguration.addHighlightStyleConfigChangeListener(new HighlightStyleConfigChangeListener() {

            public void propertyValueChanged(
                    HighlightStyleConfigChangeEvent event) {
                HighlightColor hColor = EditorBase.getContext().getConfiguration().
                        getStyleConfig().
                        getHighlightStyle().
                        getHighlightColor(HighlightCategories.WINDOW_TITLE_BAR);
                setBackground(hColor.getBackground());
                titleLabel.setForeground(hColor.getForeground());
            }
        });
        this.add(titleLabel, BorderLayout.WEST);
        Icon buttonIcon = ImageCreator.createImageIcon(EditorBase.class,
                EditorKeyConstants.TAB_CLOSE_BUTTON, "Close this tab");
        ApexButton button = new AbstractIconButton(buttonIcon) {

            protected void doOnClick(ActionEvent e) {
                executeOnClick(e);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorderPainted(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorderPainted(false);
            }
        };
        button.setBorder(BorderFactory.createLineBorder(Color.RED));
        button.setBorderPainted(false);
        //this.add(Box.createHorizontalGlue());
        if (buttonRequired) {
            this.add(button, BorderLayout.EAST);
        }
        this.setBorder(BorderFactory.createEmptyBorder(2,
                4, 2, 2));
    }

    /**
     * Performs operation when close button is pressed.
     * @param e The action event.
     */
    protected abstract void executeOnClick(ActionEvent e);

    /**
     * Returns the label of the title bar.
     * @return The title label of the bar.
     */
    public ApexLabel getTitleLabel() {
        return titleLabel;
    }
}
