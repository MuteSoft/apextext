/*
 * GeneralPage.java
 * Created on 1 August, 2007, 8:55 PM
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
package org.apex.base.settings.ui;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apex.base.component.ApexTabbedPane;
import org.apex.base.settings.GeneralConfiguration;
import org.apex.base.util.TabUtil;
import org.apex.base.component.ApexPanel;

/**
 * Configuration page for general attributes.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class GeneralPage extends ApexPanel {

    /**
     * Creates a new instance of {@code GeneralPage} using given general
     * configuration.
     * @param generalConfig General configuration.
     */
    public GeneralPage(
            GeneralConfiguration generalConfig) {
        init(generalConfig);
    }

    /**
     * Initializes the general configuration page.
     * @param generalConfig General configuration.
     */
    private void init(final GeneralConfiguration generalConfig) {
        final ApexTabbedPane tabbedPane = new ApexTabbedPane();
        tabbedPane.addTab(TabUtil.newTab("General", null, new GeneralSection(
                generalConfig.getGeneral())));
        tabbedPane.addTab(TabUtil.newTab("Document Types", null, null));
        tabbedPane.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedComponent() == null) {
                    int selectedTabIndex = tabbedPane.getSelectedIndex();
                    switch (selectedTabIndex) {
                        case 0:
                            tabbedPane.setComponentAt(selectedTabIndex, new GeneralSection(
                                    generalConfig.getGeneral()));
                            break;
                        case 1:
                            tabbedPane.setComponentAt(selectedTabIndex, new GeneralDocumentTypes(
                                    generalConfig.getDocTypes()));
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
