/*
 * BaseMessageManager.java
 * Created on 21 Oct, 2007, 1:49:47 PM
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
package org.apex.base.common;

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import java.awt.Component;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.apex.base.logging.Logger;

/**
 * The base class for all message managers.
 * <p>
 * Messages are stored in properties files in different language. Based on application {@code Locale}
 * messages are loaded from appropriate file.
 * <p>
 * This class provides facility to show warning, error message, confirmation message etc. to user.
 * <p>
 * <strong>Warning: </strong>Subclasses must follow singleton pattern.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class BaseMessageManager {

    /**
     * A reference to itself.
     */
    private static BaseMessageManager messageManager;

    /**
     * Constructs an instance of {@code BaseMessageManager}.
     */
    protected BaseMessageManager() {
    }

    /**
     * Returns a singleton instance of this class.
     * @return A singleton instance.
     */
    public static BaseMessageManager getInstance() {
        if (messageManager == null) {
            messageManager = new BaseMessageManager();
        }
        return messageManager;
    }

    /**
     * Displays a specified type of message to user.
     * <p>
     * Message type can be error, warning, confirmation etc.
     * @param base The parent container of message dialog window.
     * @param bundle The property resource bundle.
     * @param errorCode The error code.
     * @param placeHolders Place holders in message.
     * @param title Title of message dialog window.
     * @param type The message type.
     * @return Users choice.
     */
    public final int showMessage(Component base, ResourceBundle bundle,
                                 int errorCode,
                                 String placeHolders,
                                 String title, int type) {
        int value = 0;
        String message = getMesage(bundle, errorCode, type);
        if (placeHolders != null && !placeHolders.equals("")) {
            message = modifyMessage(message, placeHolders);
        }
        switch (type) {
            case JOptionPane.ERROR_MESSAGE:
                JOptionPane.showMessageDialog(base, message, title,
                        JOptionPane.ERROR_MESSAGE);
                break;
            case JOptionPane.WARNING_MESSAGE:
                JOptionPane.showMessageDialog(base, message, title,
                        JOptionPane.WARNING_MESSAGE);
                break;
            case JOptionPane.QUESTION_MESSAGE:
                value = JOptionPane.showConfirmDialog(base, message, title,
                        JOptionPane.OK_CANCEL_OPTION);
                break;
            case JOptionPane.YES_NO_CANCEL_OPTION:
                value = JOptionPane.showConfirmDialog(base, message, title,
                        JOptionPane.YES_NO_CANCEL_OPTION);
                break;
            default:
                JOptionPane.showMessageDialog(base, message, title,
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
        return value;
    }

    /**
     * Get message from resource bundle for a given key. If key
     * does not exist returns default message applicable to
     * message type.
     * @param bundle The property resource bundle.
     * @param errorCode The error code to search.
     * @param type Type of message.
     * @return The message.
     */
    private String getMesage(ResourceBundle bundle, int errorCode, int type) {
        String message = getMessage(bundle, errorCode);
        if (message == null || message.equals("")) {
            switch (type) {
                case JOptionPane.ERROR_MESSAGE:
                    message = "Error!!!";
                    break;
                case JOptionPane.WARNING_MESSAGE:
                    message = "Warning!!!";
                    break;
                case JOptionPane.QUESTION_MESSAGE:
                    message = "Please confirm.";
                    break;
                default:
                    message = "Error!!!";
                    break;
            }
        }
        return message;
    }

    /**
     * Get message from given resource bundle for
     * a given key.
     * @param bundle The property resource bundle.
     * @param errorCode The error code to search.
     * @return The message. If error code not found returns {@code null}.
     */
    private String getMessage(ResourceBundle bundle, int errorCode) {
        String message = null;
        try {
            message = bundle.getString(String.valueOf(errorCode));
        } catch (MissingResourceException mse) {
            Logger.logError("Could not find message for error code: " +
                    errorCode, mse);
        }
        return message;
    }

    /**
     * Replaces placeholders in message.
     * @param message Message to be modified.
     * @param placeHolders Placeholders in message
     * @return Modified message.
     */
    private String modifyMessage(String message, String placeHolders) {
        // Replace by placeholders.
        String[] replacers = placeHolders.split(",");
        for (String replacer : replacers) {
            String[] pairs = replacer.split("=");
            String key = "";
            String value = "";
            if (pairs.length > 0) {
                key = pairs[0];
            }
            try {
                value = pairs[1];
            } catch (ArrayIndexOutOfBoundsException aob) {
                value = "";
            }
            if (value.indexOf('\\') != -1) {
                value = value.replace('\\', '/');
            }
            message = message.replaceAll("#" + key, value);
        }
        return message;
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }
}