/*
 * MenuUtil.java
 * Created on 27 June, 2007, 7:38 PM
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
package org.apex.base.util;

import org.apex.base.core.ActionManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.constant.CommonConstants;
import org.apex.base.constant.MenuConstants;
import org.apex.base.data.EditorContext;
import org.apex.base.data.MenuNode;
import org.apex.base.data.TypingMode;
import org.apex.base.event.CaretListenerImpl;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.List;
import javax.swing.KeyStroke;

/**
 * A utility class to deal with editor menus. It provides useful methods to enable or disable
 * menus when currently displayed document is changed.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MenuUtil {

    /**
     * Updates enable status of specified list of menus. It parses the menu tree recursively
     * and enables or disables menus based on specified {@code enable} boolean value.
     * @param srcNode The root menu node.
     * @param documentInsensitiveMenus A list of document insensitive menus.
     * @param enable A boolean that indicates whether to enable or disable document
     *              insensitive menus.
     * @return {@code true} if update operation is successful; otherwise returns {@code false}.
     */
    private static boolean updateMenuStatusRecursively(MenuNode srcNode,
            List<String> documentInsensitiveMenus,
            boolean enable) {
        Enumeration menuBundle = srcNode.children();
        while (menuBundle.hasMoreElements()) {
            MenuNode menu =
                    (MenuNode) menuBundle.nextElement();
            if (ActionManager.getActionById(menu.getId()) != null
                    && (!documentInsensitiveMenus.contains(menu.getId()) && !documentInsensitiveMenus.
                    contains(((MenuNode) menu.getParent()).getId()))) {
                ActionManager.getActionById(menu.getId()).setEnabled(enable);
            }
            if (menu.getChildCount() > 0) {
                if (updateMenuStatusRecursively(menu,
                        documentInsensitiveMenus, enable)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates a new instance of {@code MenuUtil}.
     */
    private MenuUtil() {
    }

    /**
     * Updates menu status as per specified documents eligibility.
     * @param context The editor context.
     * @param file The document.
     */
    public static void updateMenuStatus(EditorContext context,
            AbstractDocument file) {
        // Update split status
        int splitStatus = file.getSplitStatus();
        /**
         * Only three valid split status possible.
         * so not including default case.
         */
        switch (splitStatus) {
            case CommonConstants.HORIZONTAL_SPLIT:
                ActionManager.setActionSelected(MenuConstants.HORIZONTAL_SPLIT,
                        true);
                break;
            case CommonConstants.VERTICAL_SPLIT:
                ActionManager.setActionSelected(MenuConstants.VERTICAL_SPLIT,
                        true);
                break;
            case CommonConstants.UNSPLIT:               
                    ActionManager.setActionSelected(MenuConstants.UNSPLIT, true);               
                break;
        }
        // Update 'In Web Browser' menu
        ActionManager.setActionEnabled(MenuConstants.VIEW_IN_WEB_BROWSER, !file.
                isTemporary());
        ActionManager.setActionEnabled(MenuConstants.RENAME_DOCUMENT, !file.
                isTemporary());
        ActionManager.setActionEnabled(MenuConstants.MOVE_DOCUMENT, !file.
                isTemporary());
        ActionManager.setActionEnabled(MenuConstants.DELETE_FILE, !file.
                isTemporary());
        // Update Cut status        
        ActionManager.setActionEnabled(MenuConstants.CUT, file.canCut());
        // Update Copy status        
        ActionManager.setActionEnabled(MenuConstants.COPY, file.canCopy());
        // Update Save status
        ActionManager.setActionEnabled(MenuConstants.SAVE_FILE, !file.isSaved());

        // Update Undo status
        ActionManager.setActionEnabled(MenuConstants.UNDO, file.canUndo());
        // Update Redo status
        ActionManager.setActionEnabled(MenuConstants.REDO, file.canRedo());
        // Read only status
        ActionManager.setActionSelected(MenuConstants.READ_ONLY, !file.getEditor().
                isEditable());
        ActionManager.setActionEnabled(MenuConstants.READ_ONLY, !file.
                isTemporary());
        // Update Status Bar        
        CaretListenerImpl.updateStatusBar(file.getEditor().getCaret());
        context.getEditorComponents().getStatusBar().setTypingMode((TypingMode) file.
                getDocument().
                getProperty("TypingMode"));
        context.getEditorComponents().getStatusBar().setWritableStatus(file.
                isTemporary() | file.canWrite());
        context.getEditorComponents().getStatusBar().setWritableStatusEnabled(!file.
                isTemporary());
    }

    /**
     * It coverts a {@code KeyStroke} object to conventional display text.
     * For example, 'control pressed X' is converted to 'Ctrl + X'.
     * @param accelerator A {@code KeyStroke}.
     * @return A conventional display text for a key stroke.
     */
    public static String getDisplayableAcceleratorText(KeyStroke accelerator) {
        if (accelerator == null) {
            return "";
        }
        String acceleratorText = "";
        int modifiers = accelerator.getModifiers();
        if (modifiers > 0) {
            acceleratorText = KeyEvent.getKeyModifiersText(modifiers);
            acceleratorText += "+";
        }

        int keyCode = accelerator.getKeyCode();
        if (keyCode != 0) {
            acceleratorText += KeyEvent.getKeyText(keyCode);
        } else {
            acceleratorText += accelerator.getKeyChar();
        }
        return acceleratorText;
    }

    /**
     * This method to be called when there is no document to be displayed with input value 
     * 'false' and also when the 1st document is displayed with input value 'true'.
     * @param context The editor context.
     * @param enable A boolean that indicates whether to enable or disable document
     *              insensitive menus.
     */
    public static void updateMenuStatus(EditorContext context,
            boolean enable) {
        List<String> documentInsensitiveMenus =
                MenuConstants.getDocumentInsensitiveMenus();
        MenuNode menus = context.getCoreMenus().
                getMenus();
        updateMenuStatusRecursively(menus, documentInsensitiveMenus, enable);
        // @TODO do not remove configuration object from memory, it will decrease performance
        //context.getConfiguration().getMenuConfig().remove();        
        // Fix for bug id 2128967 (sourceforge.net)
        context.getEditorComponents().getStatusBar().setTypingModeEnabled(enable);
        context.getEditorComponents().getStatusBar().setWritableStatusEnabled(
                enable);
        context.getEditorComponents().getStatusBar().setCaretPositionInfo(null);
        context.getEditorComponents().getStatusBar().setGeneralInfo(null);
    }
}
