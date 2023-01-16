/*
 * MenuKeyBindingPage.java 
 * Created on 3 Dec, 2009, 12:11:47 AM
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

import org.apex.base.component.ApexPanel;
import org.apex.base.component.ApexTabbedPane;
import org.apex.base.settings.MenuConfiguration;
import org.apex.base.util.TabUtil;

/**
 * A view for for assigning shotcut keys to editor menus..
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class MenuKeyBindingPage extends ApexPanel {

    /**
     * Creates a new instance of {@code MenuKeyBindingPage} using given general
     * configuration.
     * @param menuConfig Menu configuration.
     */
    public MenuKeyBindingPage(
            MenuConfiguration menuConfig) {
        init(menuConfig);
    }

    /**
     * Initializes the general configuration page.
     * @param menuConfig Menu configuration.
     */
    private void init(final MenuConfiguration menuConfig) {
        final ApexTabbedPane tabbedPane = new ApexTabbedPane();
        tabbedPane.addTab(TabUtil.newTab("Keymap", null, new MenuKeyBindingSection(
                menuConfig)));
        tabbedPane.setSelectedIndex(0);
        this.add(tabbedPane);
        tabbedPane.setPreferredSize(ConfigurationPage.DEFAULT_SIZE);
    }
}
