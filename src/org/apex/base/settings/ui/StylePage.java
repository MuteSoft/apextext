/*
 * StylePage.java
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
import org.apex.base.settings.StyleConfiguration;
import org.apex.base.util.TabUtil;
import org.apex.base.component.ApexPanel;

/**
 * The style configuration page. 
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class StylePage extends ApexPanel {

    /**
     * Creates a new instance of {@code StylePage} using given style configuration.
     * @param styleConfig Style configuration.
     */
    public StylePage(StyleConfiguration styleConfig) {
        init(styleConfig);
    }

    /**
     * Initializes the style configuration page.
     * @param styleConfig Style configuration.
     */
    public void init(final StyleConfiguration styleConfig) {
        final ApexTabbedPane tabbedPane = new ApexTabbedPane();
        tabbedPane.addTab(TabUtil.newTab("Syntax Style", null, new SyntaxStyle(
                styleConfig.getSyntaxStyle())));
        tabbedPane.addTab(TabUtil.newTab("Font", null, null));
        tabbedPane.addTab(TabUtil.newTab("Highlighting", null, null));
        tabbedPane.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedComponent() == null) {
                    int selectedTabIndex = tabbedPane.getSelectedIndex();
                    switch (selectedTabIndex) {
                        case 1:
                            tabbedPane.setComponentAt(selectedTabIndex, new FontStyle(
                                    styleConfig.getFontStyle()));
                            break;
                        case 0:
                            tabbedPane.setComponentAt(selectedTabIndex, new SyntaxStyle(
                                    styleConfig.getSyntaxStyle()));
                            break;
                        case 2:
                            tabbedPane.setComponentAt(selectedTabIndex, new HighlightingStyle(styleConfig.
                                    getHighlightStyle()));
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
