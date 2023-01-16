/* 
 * MenuMessageManager.java
 * Created on 20 Nov, 2007,4:00:04 AM
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
package org.apex.base.menu;

import org.apex.base.common.BaseMessageManager;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.logging.Logger;
import java.awt.Component;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

/**
 * The message manager for editor menu related operations.
 * <p>
 * Messages are stored in properties files in different language. Based on application {@code Locale}
 * messages are loaded from appropriate file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class MenuMessageManager extends BaseMessageManager {

    /**
     * A reference to itself.
     */
    private static MenuMessageManager messageManager;
    /**
     * The property resource bundle for editor menu related messages.
     */
    private static ResourceBundle resourceBundle;

    /**
     * Constructs an instance of SettingsMessageManager.
     */
    private MenuMessageManager() {
        try {
            Logger.logInfo("Creating menu message bundle.", getClass().getName(),
                    "constructor");
            resourceBundle = ResourceBundle.getBundle(EditorKeyConstants.MENU_MESSAGES, getContext().
                    getEditorProperties().getLocale());
        } catch (MissingResourceException mse) {
            Logger.logError("Failed to load menu messages.", mse);
        }
    }

    /**
     * Displays an error message to user. 
     * @param base The parent container of message dialog window.
     * @param errorCode The error code.
     * @return Users choice.
     */
    public static int showErrorMessage(Component base, int errorCode) {
        return showErrorMessage(base, errorCode, null);
    }

    /**
     * Displays an error message to user.
     * <p>
     * Place holders in message are replaced by provided values.
     * @param base The parent container of message dialog window.
     * @param errorCode The error code. 
     * @param placeHolders Place holders in message.
     * @return Users choice.
     */
    public static int showErrorMessage(Component base, int errorCode, String placeHolders) {
        return showMessage(base, errorCode, placeHolders, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays confirmation message to user. 
     * @param base The parent container of message dialog window.
     * @param errorCode The error code.
     * @return Users choice.
     */
    public static int showConfirmMessage(Component base, int errorCode) {
        return showConfirmMessage(base, errorCode, null);
    }

    /**
     * Displays confirmation message to user.
     * <p>
     * Place holders in message are replaced by provided values.
     * @param base The parent container of message dialog window.
     * @param errorCode The error code. 
     * @param placeHolders Place holders in message.
     * @return Users choice.
     */
    public static int showConfirmMessage(Component base, int errorCode, String placeHolders) {
        return showMessage(base, errorCode, placeHolders, "Confirm",
                JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Displays confirmation message to user. 
     * <p>
     * It shows Yes, No and Cancel options to user. Default confirmation
     * message shows Ok and Cancel options.
     * @param base The parent container of message dialog window.
     * @param errorCode The error code.
     * @return Users choice. 
     */
    public static int showConfirmMessageYesNoCancel(Component base, int errorCode) {
        return showConfirmMessageYesNoCancel(base, errorCode, null);
    }

    /**
     * Displays confirmation message to user.
     * <p>
     * It shows Yes, No and Cancel options to user. Default confirmation
     * message shows Ok and Cancel options.
     * <p>
     * Place holders in message are replaced by provided values.
     * @param base The parent container of message dialog window.
     * @param errorCode The error code. 
     * @param placeHolders Place holders in message.
     * @return Users choice.
     */
    public static int showConfirmMessageYesNoCancel(Component base, int errorCode,
            String placeHolders) {
        return showMessage(base, errorCode, placeHolders, "Confirm",
                JOptionPane.YES_NO_CANCEL_OPTION);
    }

    /**
     * Displays warning message to user. 
     * @param base The parent container of message dialog window.
     * @param errorCode The error code.
     * @return Users choice.
     */
    public static int showWarningMessage(Component base, int errorCode) {
        return showWarningMessage(base, errorCode, null);
    }

    /**
     * Displays warning message to user.
     * <p>
     * Place holders in message are replaced by provided values.
     * @param base The parent container of message dialog window.
     * @param errorCode The error code. 
     * @param placeHolders Place holders in message.
     * @return Users choice.
     */
    public static int showWarningMessage(Component base, int errorCode, String placeHolders) {
        return showMessage(base, errorCode, placeHolders, "Warning",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Displays a specified type of message to user.
     * <p>
     * Message type can be error, warning, confirmation etc.
     * @param base The parent container of message dialog window.
     * @param errorCode The error code.
     * @param title Title of message dialog window.
     * @param type The message type.
     * @return Users choice.
     */
    public int showMessage(Component base, int errorCode, String title, int type) {
        return showMessage(base, errorCode, null, title, type);
    }

    /**
     * Displays a specified type of message to user.
     * <p>
     * Message type can be error, warning, confirmation etc.
     * @param base The parent container of message dialog window.
     * @param errorCode The error code.
     * @param placeHolders Place holders in message.
     * @param title Title of message dialog window.
     * @param type The message type.
     * @return Users choice.
     */
    public static int showMessage(Component base, int errorCode, String placeHolders,
            String title,
            int type) {
        return getInstance().showMessage(base, resourceBundle, errorCode, placeHolders, title, type);
    }

    /**
     * Returns a singleton instance of this class.
     * @return A singleton instance.
     */
    public static MenuMessageManager getInstance() {
        if (messageManager == null) {
            messageManager = new MenuMessageManager();
        }
        return messageManager;
    }
}
