/*
 * MenuGroupPopup.java
 * Created on 27 Oct, 2009, 1:32:55 AM
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
package org.apex.base.core;

import java.util.HashMap;
import java.util.Map;
import org.apex.base.constant.MenuConstants;
import org.apex.base.data.EditorContext;
import org.apex.base.settings.EditorConfiguration;
import org.apex.base.settings.event.DocTypesConfigChangeEvent;
import org.apex.base.settings.event.DocTypesConfigChangeListener;

/**
 * Menu group popup menu.
 * @author mrityunjoy_saha
 * @version 1.1
 * @since Apex 1.2
 */
public class MenuGroupPopup extends PopupMenuBar {

    /**
     * List of menu group popups in different named categories.
     */
    private static Map<String, MenuGroupPopup> selfInstances = new HashMap<String, MenuGroupPopup>(
            5);
    /**
     * The parent menu id.
     */
    private String parentMenuId;

    /**
     * Creates a new instance of {@code MenuGroupPopup} with specified parent menu id.
     * @param parentMenuId A menu id.
     */
    private MenuGroupPopup(String parentMenuId) {
        this.parentMenuId = parentMenuId;
        createPopupMenuNew(EditorBase.getContext());
        if (parentMenuId.equalsIgnoreCase(MenuConstants.TYPED_DOCUMENTS)) {
            this.addDocTypesConfigChangeListener(new DocTypesConfigChangeListener() {

                public void propertyValueChanged(DocTypesConfigChangeEvent event) {
                    refreshMenuBar();
                }
            });
        }
    }

    /**
     * Creates the popup menu. This method should be overridden by sub classes.
     * @param context The editor context.
     */
    protected void createPopupMenu(EditorContext context) {
        // Do not create here.
    }

    /**
     * Creates the new popup menu.
     * @param context The editor context.
     */
    protected void createPopupMenuNew(EditorContext context) {
        addMenu(this.parentMenuId, true);
    }

    /**
     * Refreshes the main menu-bar.
     * <p>
     * It clears the menu-bar and re-loads the same based on changed menu
     * configuration.
     */
    public void refreshMenuBar() {
        clearMenuBar();
        createPopupMenuNew(EditorBase.getContext());
        this.validate();
    }

    /**
     * Removes all menus from the menu-bar.
     */
    private void clearMenuBar() {
        this.removeAll();
    }

    /**
     * Returns the menu group popup instance by parent menu id.
     * @param parentMenuId Parent menu id.
     * @return A menu group popup instance.
     */
    public static MenuGroupPopup getInstance(String parentMenuId) {
        MenuGroupPopup popup = selfInstances.get(parentMenuId);
        if (popup == null) {
            popup = new MenuGroupPopup(parentMenuId);
            selfInstances.put(parentMenuId, popup);
        }
        return popup;
    }

    /**
     * Adds a document type configuration change listener to menu-bar.
     * @param listener A document type configuration change listener.
     */
    protected void addDocTypesConfigChangeListener(
            DocTypesConfigChangeListener listener) {
        EditorConfiguration.addDocumentTypesConfigChangeListener(listener);
    }
}
