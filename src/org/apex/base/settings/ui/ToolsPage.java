/*
 * ToolsPage.java
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
package org.apex.base.settings.ui;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apex.base.component.ApexTabbedPane;
import org.apex.base.settings.MenuConfiguration;
import org.apex.base.util.TabUtil;
import org.apex.base.component.ApexPanel;

/**
 * A panel to display editor provided tools and custom tools panels in
 * a tabbed pane.
 * @author mrityunjoy_saha
 */
public class ToolsPage extends ApexPanel {

    /**
     * Creates a new instance of {@code ToolsPage}.
     * @param menuConfig The menu configuration.
     */
    public ToolsPage(
            MenuConfiguration menuConfig) {
        init(menuConfig);
    }

    /**
     * Initializes the panel by creating a tabbed pane and tabs for editor
     * provided tools and custom tools. And then adds these tabs to tabbed pane.
     * @param menuConfig The menu configuration.
     */
    public void init(final MenuConfiguration menuConfig) {
        final ApexTabbedPane tabbedPane = new ApexTabbedPane();
        tabbedPane.addTab(TabUtil.newTab("Provided", null, new ProvidedTools(menuConfig.
                getProvidedTools())));
        tabbedPane.addTab(TabUtil.newTab("Custom", null, null));
        // @TODO SDK setup will be added post 1.2 release
        //tabbedPane.addTab(TabUtil.newTab("SDK Setup", null, null));
        tabbedPane.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedComponent() == null) {
                    int selectedTabIndex = tabbedPane.getSelectedIndex();
                    switch (selectedTabIndex) {
                        case 0:
                            tabbedPane.setComponentAt(selectedTabIndex,
                                    new ProvidedTools(menuConfig.getProvidedTools()));
                            break;
                        case 1:
                            tabbedPane.setComponentAt(selectedTabIndex,
                                    new CustomTools(
                                    menuConfig.getCustomTools()));
                            break;
                    }
                }
            }
        });
        tabbedPane.setSelectedIndex(0);
        this.add(tabbedPane);
        tabbedPane.setPreferredSize(ConfigurationPage.DEFAULT_SIZE);
    }
}
