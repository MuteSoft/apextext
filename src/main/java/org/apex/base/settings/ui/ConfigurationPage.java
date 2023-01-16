/*
 * ConfigurationPage.java
 * Created on 1 August, 2007, 8:47 PM 
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

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.settings.EditorConfiguration;
import org.apex.base.ui.text.UIDialogModel;
import org.apex.base.component.ApexPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * The main configuration page.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ConfigurationPage extends JPanel implements UIDialogModel {

    /**
     * The container dialog window.
     */
    private JDialog containerWindow;
    /**
     * The footer page.
     */
    private SettingsFooterPage footerPage;
    /**
     * Default size of all configuration option pages.
     */
    public static final Dimension DEFAULT_SIZE = new Dimension(500, 390);
    //public static final Dimension DEFAULT_SIZE = null;

    /**
     * Creates a new instance of {@code ConfigurationPage}.
     */
    public ConfigurationPage() {
        init();
    }

    public void setContainerWindow(JDialog window) {
        this.containerWindow = window;
        footerPage.setContainerWindow(window);
    }

    public JDialog getContainerWindow() {
        return this.containerWindow;
    }

    /**
     * Initializes the entire configuration page.
     */
    private void init() {
        // Get the cloned EditorConfiguration
        EditorConfiguration clonedConfig =
                (EditorConfiguration) getContext().getConfiguration().
                clone();
        // @TODO - Tune general page creation.
        // General Page.
        GeneralPage generalPage =
                new GeneralPage(clonedConfig.getGeneralConfig());
        generalPage.setName("card1");
        // Footer Page.
        footerPage =
                new SettingsFooterPage(getContext().getConfiguration(),
                clonedConfig);
        ApexPanel cards = new ApexPanel();
        cards.setLayout(new CardLayout());
        cards.add(generalPage, "card1");
        // Menu Page.
        SettingsMenuPage settingsMenu =
                new SettingsMenuPage(cards, clonedConfig);
        this.setLayout(new BorderLayout());
        this.add(settingsMenu, BorderLayout.PAGE_START);
        this.add(cards, BorderLayout.CENTER);
        this.add(footerPage, BorderLayout.SOUTH);
    }

    public EditorContext getContext() {
        return EditorBase.getContext();
    }
}
