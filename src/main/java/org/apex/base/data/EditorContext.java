/*
 * EditorContext.java
 * Created on January 1, 2007, 5:13 PM 
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
package org.apex.base.data;

import org.apex.base.settings.EditorConfiguration;
import java.util.HashMap;
import org.apex.base.core.CoreMenuBuilder;
import org.apex.base.core.CoreMenuConfiguration;

/**
 * The context of editor application.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class EditorContext extends HashMap {

    /**
     * Holds editor properties.
     */
    private EditorProperties prop;
    /**
     * Holds editor components.
     */
    private EditorComponents comp;
    /**
     * Holds editor configuration.
     */
    private EditorConfiguration configuration = new EditorConfiguration();
    /**
     * Core menu configuration.
     */
    private CoreMenuConfiguration coreMenus;

    /**
     * Creates a new editor context.
     */
    public EditorContext() {
    }

    /**
     * Sets editor properties.
     * @param prop Editor properties.
     * @see #getEditorProperties() 
     */
    public void setEditorProperties(EditorProperties prop) {
        this.prop = prop;
    }

    /**
     * Returns editor properties.
     * @return Editor properties.
     * @see #setEditorProperties(org.apex.base.data.EditorProperties)
     */
    public EditorProperties getEditorProperties() {
        return this.prop;
    }

    /**
     * Sets editor components.
     * @param comp Editor components.
     * @see #getEditorComponents() 
     */
    public void setEditorComponents(EditorComponents comp) {
        this.comp = comp;
    }

    /**
     * Returns editor components.
     * @return Editor components.
     * @see #setEditorComponents(org.apex.base.data.EditorComponents)
     */
    public EditorComponents getEditorComponents() {
        return this.comp;
    }

    /**
     * Returns editor configuration.
     * @return Editor configuration.
     * @see #setConfiguration(org.apex.base.settings.EditorConfiguration)
     */
    public EditorConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Sets editor configuration.
     * @param configuration Editor configuration.
     * @see #getConfiguration() 
     */
    public void setConfiguration(EditorConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Returns core menu configuration.
     * @return Core menu configuration.
     * @see #setCoreMenus(org.apex.base.core.CoreMenuConfiguration)
     */
    public CoreMenuConfiguration getCoreMenus() {
        if (this.coreMenus == null) {
            this.coreMenus =
                    (CoreMenuConfiguration) new CoreMenuBuilder().build();
        }
        return coreMenus;
    }

    /**
     * Sets core menu configuration.
     * @param coreMenus Core menu configuration.
     * @see #getCoreMenus() 
     */
    public void setCoreMenus(CoreMenuConfiguration coreMenus) {
        this.coreMenus = coreMenus;
    }
}
