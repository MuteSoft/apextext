/*
 * ApexTabbedPane.java
 * Created on February 22, 2007, 9:50 PM
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

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import org.apex.base.data.Tab;
import java.awt.event.KeyEvent;
import javax.swing.InputMap;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import org.apex.base.constant.ColorConstants;
import org.apex.base.constant.FontConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.data.HighlightCategories;
import org.apex.base.event.BasicTabChangeListener;
import org.apex.base.settings.AbstractConfiguration;
import org.apex.base.settings.HighlightColor;
import org.apex.base.settings.event.HighlightStyleConfigChangeEvent;
import org.apex.base.settings.event.HighlightStyleConfigChangeListener;

/**
 * An extension of {@code JTabbedPane}. It provides handy constructors and methods
 * to easily deal with tabbed pane.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexTabbedPane extends JTabbedPane {

    /**
     * Tab index of last selected index.
     */
    private int lastSelectedIndex;
    /**
     * The font of seelcted tab title.
     */
    private Font selectionFont;
    /**
     * The foreground color of selected tab title.
     */
    private Color selectionFGColor;
    /**
     * The background color of seelcted tab component.
     */
    private Color selectionBGColor;

    /**
     * Creates a new instance of {@code ApexTabbedPane}.
     */
    public ApexTabbedPane() {
        this(TOP, true, true);
    }

    /**
     * Creates a new instance of {@code ApexTabbedPane} with given
     * tab position, default state change listener indicator and an indicator for
     * border painting.
     * @param position The placement for the tabs relative to the content.
     * @param defaultStateChangeListener A boolean that indicates whether or not default state change
     * listener to be applied.
     * @param borderPainted A boolean that indicates whether or not border to be painted.
     */
    public ApexTabbedPane(int position, boolean defaultStateChangeListener,
            boolean borderPainted) {
        this(position, FontConstants.SELECTED_TAB_TITLE_FONT,
                ColorConstants.SELECTED_TAB_FOREGROUND_COLOR,
                ColorConstants.SELECTED_TAB_BACKGROUND_COLOR,
                defaultStateChangeListener, borderPainted);
    }

    /**
     * Creates a new instance of {@code ApexTabbedPane} with the specified
     * tab placement, selection color etc.
     * @param position The placement for the tabs relative to the content.
     * @param selectionFont The font of seelcted tab title.
     * @param selectionFGColor The foreground color of selected tab title.
     * @param selectionBGColor The background color of seelcted tab component.
     * @param defaultStateChangeListener A boolean that indicates whether or not default state change
     * listener to be applied.
     * @param borderPainted A boolean that indicates whether or not border to be painted.
     */
    private ApexTabbedPane(int position, Font selectionFont,
            Color selectionFGColor,
            Color selectionBGColor, boolean defaultStateChangeListener,
            boolean borderPainted) {
        super(position, SCROLL_TAB_LAYOUT);
        HighlightColor hColor = EditorBase.getContext().getConfiguration().
                getStyleConfig().
                getHighlightStyle().
                getHighlightColor(HighlightCategories.SELECTED_TAB);
        this.selectionFont = selectionFont;
        this.selectionFGColor = hColor.getForeground() == null
                ? selectionFGColor
                : hColor.getForeground();
        this.selectionBGColor = hColor.getBackground() == null
                ? selectionBGColor
                : hColor.getBackground();
        this.setUI(new TabbedPaneUI(borderPainted));
        //this.setBorder(null);       
        this.setFocusable(false);
        // Remove default CTRL + TAB functionality (Tab Selection change to Next one)
        InputMap inputMap = this.getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.CTRL_MASK),
                " junkValue");
        if (defaultStateChangeListener) {
            this.addChangeListener(new BasicTabChangeListener());
        }
        AbstractConfiguration.addHighlightStyleConfigChangeListener(new HighlightStyleConfigChangeListener() {

            public void propertyValueChanged(
                    HighlightStyleConfigChangeEvent event) {
                HighlightColor hColor = EditorBase.getContext().getConfiguration().
                        getStyleConfig().
                        getHighlightStyle().
                        getHighlightColor(HighlightCategories.SELECTED_TAB);
                setSelectionBGColor(hColor.getBackground());
                setSelectionFGColor(hColor.getForeground());
            }
        });
    }

    @Override
    public Insets getInsets() {
        return super.getInsets();
    }

    /**
     * Adds a tab to the tabbed pane at specified position.
     * @param tab The tab to be added.
     * @param index The index where to add the given tab.
     */
    public void addTab(Tab tab, int index) {
        // Add the tab to the pane.        
        if (index < 0) {
            addTab(tab.getTitle(), tab.getIcon(), tab.getComponent(),
                    tab.getTip());
            //this.setTabComponentAt(0, tab.getTabComponent());
            if (tab.getTabComponent() != null) {
                this.setTabComponentAt(getTabCount() - 1, tab.getTabComponent());
            }
        } else {
            insertTab(tab.getTitle(), tab.getIcon(), tab.getComponent(),
                    tab.getTip(), index);
            if (tab.getTabComponent() != null) {
                this.setTabComponentAt(index, tab.getTabComponent());
            }
        }
        // For the first tab        
        if (this.getTabCount() == 1) {
            setStylesAtTab(0);
            this.setLastSelectedIndex(0);
        }
    }

    /**
     * Removes styles of a tab.
     * @param tabIndex The tab index.
     */
    public void removeStylesAtTab(int tabIndex) {
        if (this.getTabComponentAt(
                tabIndex) instanceof AbstractTabComponent) {
            AbstractTabComponent buttthisrev =
                    (AbstractTabComponent) this.getTabComponentAt(
                    tabIndex);
            if (buttthisrev != null) {
                buttthisrev.getTabTitle().setForeground(null);
                buttthisrev.getTabTitle().setFont(null);
            }
        } else {
            this.setForegroundAt(tabIndex, null);
            // Individual tab font cannot be changed.
        }
        // Clear the previously selected tab background color
        if (tabIndex >= 0) {
            this.setBackgroundAt(tabIndex,
                    null);
        }
    }

    /**
     * Sets styles of a tab.
     * @param tabIndex The tab index.
     */
    public void setStylesAtTab(int tabIndex) {
        if (this.getTabComponentAt(tabIndex) instanceof AbstractTabComponent) {
            AbstractTabComponent buttTab =
                    (AbstractTabComponent) this.getTabComponentAt(tabIndex);
            if (buttTab != null) {
                // Change the selected tab title color
                buttTab.getTabTitle().setForeground(
                        this.selectionFGColor);
                buttTab.getTabTitle().setFont(
                        this.selectionFont);
            }
        } else {
            this.setForegroundAt(tabIndex, this.selectionFGColor);
            // Individual tab font cannot be changed.
        }
        // Set the selected tab background color as well
        this.setBackgroundAt(tabIndex, this.selectionBGColor);
    }

    /**
     * Adds a tab to the tabbed pane at the end.
     * @param tab The tab to be added.
     */
    public void addTab(Tab tab) {
        addTab(tab, -1);
    }

    /**
     * Returns the last selected tab index in tabbed pane.
     * @return The last selected tab index.
     * @see #setLastSelectedIndex(int)
     */
    public int getLastSelectedIndex() {
        return lastSelectedIndex;
    }

    /**
     * Sets the last selected tab index in tabbed pane.
     * @param lastSelectedIndex An index.
     * @see #getLastSelectedIndex()
     */
    public void setLastSelectedIndex(int lastSelectedIndex) {
        this.lastSelectedIndex = lastSelectedIndex;
    }

    /**
     * Sets the tooltip text at specified tab index in tabbed pane.
     * @param index The tab index where the tooltip text should be set.
     * @param toolTipText The tooltip text to be displayed for the tab.
     * @see #getToolTipTextAt(int)
     */
    @Override
    public void setToolTipTextAt(int index, String toolTipText) {
        super.setToolTipTextAt(index, toolTipText);
    }

    /**
     * Returns background color of selected tab.
     * @return The background color of selected tab.
     * @see #setSelectionBGColor(java.awt.Color)
     */
    public Color getSelectionBGColor() {
        return selectionBGColor;
    }

    /**
     * Sets background color of selected tab.
     * @param selectionBGColor The color to be set as background color of selected tab.
     * @see #getSelectionBGColor()
     */
    public void setSelectionBGColor(Color selectionBGColor) {
        this.selectionBGColor = selectionBGColor;
    }

    /**
     * Returns the foreground color of selected tab.
     * @return The foreground color of selected tab.
     * @see #setSelectionFGColor(java.awt.Color)
     */
    public Color getSelectionFGColor() {
        return selectionFGColor;
    }

    /**
     * Sets the foreground color of selected tab.
     * @param selectionFGColor The color to be set as foreground color of selected tab.
     * @see #getSelectionFGColor()
     */
    public void setSelectionFGColor(Color selectionFGColor) {
        this.selectionFGColor = selectionFGColor;
    }

    /**
     * Returns the font of selected tab.
     * @return The font of selected tab.
     * @see #setSelectionFont(java.awt.Font)
     */
    public Font getSelectionFont() {
        return selectionFont;
    }

    /**
     * Sets the font of selected tab.
     * @param selectionFont The font of selected tab.
     * @see #getSelectionFont()
     */
    public void setSelectionFont(Font selectionFont) {
        this.selectionFont = selectionFont;
    }
}
