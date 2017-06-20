/*
 * ApexMenuBar.java
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

import org.apex.base.settings.EditorConfiguration;
import org.apex.base.settings.event.CustomToolConfigurationChangeListener;
import org.apex.base.settings.event.MenuKeyBindingConfigurationChangeListener;
import org.apex.base.settings.event.ProvidedToolConfigChangeListener;
import javax.swing.JMenuBar;
import org.apex.base.settings.event.DocTypesConfigChangeListener;

/**
 * An extension of {@code JMenuBar}. It provides handy constructors and methods
 * to easily deal with menu-bar.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexMenuBar extends JMenuBar {

    /**
     * Creates a new instance of {@code ApexMenuBar}.
     */
    public ApexMenuBar() {
        super();
    }

    /**
     * Adds a custom tool configuration change listener to menu-bar.
     * @param listener A custom tool configuration change listener.
     */
    public void addCustomToolConfigChangeListener(
            CustomToolConfigurationChangeListener listener) {
        EditorConfiguration.addCustomToolConfigChangeListener(listener);
    }

    /**
     * Adds a menu-key binding configuration change listener to menu-bar.
     * @param listener A menu-key binding configuration change listener.
     */
    public void addMenuKeyBindingConfigChangeListener(
            MenuKeyBindingConfigurationChangeListener listener) {
        EditorConfiguration.addMenuKeyBindingConfigurationChangeListener(
                listener);
    }

    /**
     * Adds an editor provided tool configuration change listener to menu-bar.
     * @param listener An editor provided tool configuration change listener.
     */
    public void addProvidedToolConfigChangeListener(
            ProvidedToolConfigChangeListener listener) {
        EditorConfiguration.addProvidedToolConfigChangeListener(listener);
    }

    /**
     * Adds a document type configuration change listener to menu-bar.
     * @param listener A document type configuration change listener.
     */
    public void addDocTypesConfigChangeListener(
            DocTypesConfigChangeListener listener) {
        EditorConfiguration.addDocumentTypesConfigChangeListener(listener);
    }
}
