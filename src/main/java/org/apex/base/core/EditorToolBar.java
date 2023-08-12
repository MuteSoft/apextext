/*
 * EditorToolBar.java
 * Created on February 10, 2007, 8:15 AM
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
package org.apex.base.core;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.border.Border;
import org.apex.base.component.ApexButton;
import org.apex.base.data.EditorContext;
import org.apex.base.data.MenuNode;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JToolBar;
import org.apex.base.action.Action;
import org.apex.base.component.ApexDropdownButton;
import org.apex.base.data.MenuType;
import org.apex.base.search.ui.SearchBox;

/**
 * The main tool-bar displayed at the top and  just below the main menu-bar.
 * <p>
 * These tools are configurable and share configuration with editor main menus.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 * @see MenuNode
 */
public class EditorToolBar extends JToolBar {
    private static Border EMPTY_BORDER = BorderFactory.createEmptyBorder();

    /**
     * The search box in the toolbar.
     */
    private SearchBox searchBox;

    /**
     * Creates a new instance of EditorToolBar.
     */
    public EditorToolBar() {
        loadToolBar(EditorBase.getContext());
        this.setBorder(BorderFactory.createMatteBorder(0, 0,
                1, 0, Color.GRAY));        
    }

    /**
     * Loads the tool-bar dynamically based on main menu configuration.
     * @param context The editor context.
     */
    private void loadToolBar(final EditorContext context) {        
        // Border painting can be handled using this single line.
        this.setRollover(true);
        this.setFloatable(true);       
        Vector<String> toolBarMenus = context.getCoreMenus().
                getToolBarMenus();
        MenuNode menus = context.getConfiguration().getMenuConfig().
                getAllAvailableMenus();
        this.add(Box.createRigidArea(new Dimension(5, 5)));
        for (String menuid : toolBarMenus) {
            if (menuid.equals(MenuNode.SEPARATOR_ID)) {
                this.addSeparator();
            } else if (menus.findChildMenuById(menuid).getType().equals(
                    MenuType.MENU)) {
                // @TODO How to associate a menu with this group? 'new' is hardcoded for new documents.
                ApexDropdownButton dropdownButton = new ApexDropdownButton(ActionManager.
                        getActionById("new"),
                        MenuGroupPopup.getInstance(menuid),
                        ApexDropdownButton.RIGHT_ORIENTED_ARROW);
                this.add(dropdownButton);
            } else {
                this.add(
                        new ToolBarButton(ActionManager.getActionById(menuid),
                        this));
            }
        }
        // @TODO Add search box in next version of editor; i.e. post v1.2
//        this.add(Box.createHorizontalGlue());
//        searchBox = new SearchBox();
//        this.add(searchBox.getSearchField());
//        this.add(Box.createRigidArea(new Dimension(10, 5)));
    }

    /**
     * Returns the toolbar search box.
     * @return The toolbar search box.
     */
    public SearchBox getSearchBox() {
        return searchBox;
    }

    /**
     * Toolbar button class.
     */
    private class ToolBarButton extends ApexButton {

        /**
         * Creates a new instance of {@code ToolBarButton} with specified action for button
         * and aprent component.
         * @param action The action to be associated with the button.
         * @param parent The parent component.
         */
        public ToolBarButton(Action action, JToolBar parent) {
            super(action, parent);           
            this.removeText();           
        }

        @Override
        public Insets getMargin() {
            return super.getMargin();
        }

        @Override
        public boolean isContentAreaFilled() {
            return super.isContentAreaFilled();
        }

        @Override
        public boolean isRolloverEnabled() {
            return super.isRolloverEnabled();
        }

        @Override
        public Border getBorder() {
            return EditorToolBar.EMPTY_BORDER;
        }

        @Override
        public Insets getInsets() {
            Insets toolIconMargin = super.getInsets();
            toolIconMargin.left = toolIconMargin.left + 5;
            toolIconMargin.right = toolIconMargin.right + 5;
            return toolIconMargin;
        }
    }
}
