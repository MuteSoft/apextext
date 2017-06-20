/*
 * TemplatePage.java
 * Created on 3 Dec, 2009, 12:11:37 AM
 *
 * Copyright (C) 2009 Mrityunjoy Saha
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
import org.apex.base.component.ApexPanel;
import org.apex.base.component.ApexTabbedPane;
import org.apex.base.settings.TemplateConfiguration;
import org.apex.base.util.TabUtil;

/**
 * Configuration page for different type of templates.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class TemplatePage extends ApexPanel {

    /**
     * Creates a new instance of {@code TemplatePage} using given template
     * configuration.
     * @param templateConfig Template configuration.
     */
    public TemplatePage(
            TemplateConfiguration templateConfig) {
        init(templateConfig);
    }

    /**
     * Initializes the general configuration page.
     * @param templateConfig Template configuration.
     */
    private void init(final TemplateConfiguration templateConfig) {
        final ApexTabbedPane tabbedPane = new ApexTabbedPane();
        tabbedPane.addTab(TabUtil.newTab("Document Templates", null, new DocumentTemplateSection(
                templateConfig.getDocTemplateConfig())));
        tabbedPane.addTab(TabUtil.newTab("Code Templates", null, null));
        tabbedPane.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedComponent() == null) {
                    int selectedTabIndex = tabbedPane.getSelectedIndex();
                    switch (selectedTabIndex) {
                        case 0:
                            tabbedPane.setComponentAt(selectedTabIndex, new DocumentTemplateSection(
                                    templateConfig.getDocTemplateConfig()));
                            break;
                        case 1:
                            tabbedPane.setComponentAt(selectedTabIndex, new CodeTemplate(
                                    templateConfig.getCodeTemplate()));
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
