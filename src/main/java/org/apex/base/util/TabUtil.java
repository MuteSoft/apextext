/*
 * TabUtil.java
 * Created on February 22, 2007, 10:08 PM 
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
package org.apex.base.util;

import org.apex.base.data.Tab;
import org.apex.base.data.AbstractDocument;
import org.apex.base.component.AbstractTabComponent;
import org.apex.base.component.ApexScrollPane;
import org.apex.base.component.ApexTabbedPane;
import java.awt.Component;
import javax.swing.Icon;

/**
 * A utility class to create and update {@code Tab}s.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class TabUtil {

    /**
     * Creates a {@code Tab} using given title, icon and tab component.
     * @param mTitle Tab title.
     * @param mIcon Tab icon.
     * @param mComponent Tab component.
     * @return A {@code Tab}.
     */
    public static Tab newTab(String mTitle, Icon mIcon, Component mComponent) {
        return newTab(mTitle, mIcon, mComponent, null);
    }

    /**
     * Creates a {@code Tab} using given title, icon, tab component and
     * tab rendering component.
     * @param mTitle Tab title.
     * @param mIcon Tab icon.
     * @param mComponent Tab component.
     * @param tabComponent The component used to render the tab.
     * @return A {@code Tab}.
     */
    public static Tab newTab(String mTitle, Icon mIcon, Component mComponent,
                             Component tabComponent) {
        return newTab(mTitle, mIcon, mComponent, mTitle, tabComponent);
    }

    /**
     * Creates a {@code Tab} using given title, icon, tab component, tooltip text and
     * tab rendering component.
     * @param mTitle Tab title.
     * @param mIcon Tab icon.
     * @param mComponent Tab component.
     * @param tipText Tab tooltip text.
     * @param tabComponent The component used to render the tab.
     * @return A {@code Tab}.
     */
    public static Tab newTab(String mTitle, Icon mIcon, Component mComponent,
                             String tipText,
                             Component tabComponent) {
        Tab tab = new Tab(mTitle, mIcon, mComponent, tipText, tabComponent);
        return tab;
    }

    /**
     * A given {@code Tab} is added to the given tabbed pane.
     * @param docsTabSelector A tabbed pane.
     * @param tab The {@code Tab} to add.
     */
    public static void createAndAddTab(ApexTabbedPane docsTabSelector, Tab tab) {
        docsTabSelector.addTab(tab);
    }

    /**
     * Creates a {@code Tab} using given title, icon and tab component. The created {@code Tab}
     * is added to the given tabbed pane.
     * @param docsTabSelector A tabbed pane.
     * @param mTitle Tab title.
     * @param mIcon Tab icon.
     * @param mComponent Tab component.
     * @see #createAndAddTab(ApexTabbedPane, Tab)
     */
    public static void createAndAddTab(ApexTabbedPane docsTabSelector, String mTitle,
                                       Icon mIcon,
                                       Component mComponent) {
        createAndAddTab(docsTabSelector, newTab(mTitle, mIcon, mComponent));
    }

    /**
     * Remove a tab from a tabbed pane from specified index.
     * @param tabbedPane A tabbed pane.
     * @param index Index of tab.
     */
    public static void removeTab(ApexTabbedPane tabbedPane, int index) {
        tabbedPane.remove(index);
    }

    /**
     * Removes all tabs from a tabbed pane.
     * @param tabbedPane A tabbed pane.
     */
    public static void removeAllTabs(ApexTabbedPane tabbedPane) {
        tabbedPane.removeAll();
    }

    /**
     * Update the document tab title.
     * @param tabbedPane A tabbed pane.
     * @param file The document.
     */
    public static void updateTabTitle(ApexTabbedPane tabbedPane,
                                      AbstractDocument file) {
        AbstractTabComponent buttonTab = (AbstractTabComponent) tabbedPane.
                getTabComponentAt(file.getIndex());
        if (buttonTab != null) {
            buttonTab.updateTabTitle(file);
        }
    }

    /**
     * Updates document tab icon.
     * @param tabbedPane A tabbed pane.
     * @param file The document .
     */
    public static void updateTabIcon(ApexTabbedPane tabbedPane,
                                     AbstractDocument file) {
        AbstractTabComponent buttonTab = (AbstractTabComponent) tabbedPane.
                getTabComponentAt(file.getIndex());
        if (buttonTab != null) {
            buttonTab.updateTabIcon(file);
        }
    }

    /**
     * In case we have multiple tabs with same title it returns the first one.
     * @param tabbedPane A tabbed pane.
     * @param title The tab title text.
     * @return The component associated with a tab having given tooltip text.
     */
    public static Component getComponentByTitle(ApexTabbedPane tabbedPane,
                                                String title) {
        int tabCount = tabbedPane.getTabCount();
        int iCount = 0;
        for (; iCount < tabCount; iCount++) {
            if (tabbedPane.getTitleAt(iCount).equalsIgnoreCase(title)) {
                break;
            }
        }
        ApexScrollPane scrollPane = (ApexScrollPane) tabbedPane.getComponent(iCount);
        return scrollPane.getComponent(0);
    }

    /**
     * In case we have multiple tabs with same tooltip it returns the first one.
     * @param tabbedPane A tabbed pane.
     * @param toolTip The tooltip text of a tab.
     * @return The component associated with a tab having given tooltip text.
     */
    public static Component getComponentByToolTip(ApexTabbedPane tabbedPane,
                                                  String toolTip) {
        int tabCount = tabbedPane.getTabCount();
        int iCount = 0;
        for (; iCount < tabCount; iCount++) {
            if (tabbedPane.getToolTipTextAt(iCount).equalsIgnoreCase(toolTip)) {
                break;
            }
        }
        ApexScrollPane scrollPane = (ApexScrollPane) tabbedPane.getComponent(iCount);
        return scrollPane.getComponent(0);
    }

    /**
     * Creates a new instance of {@code TabUtil}.
     */
    private TabUtil() {
    }
}
