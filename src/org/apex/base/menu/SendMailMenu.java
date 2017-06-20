/*
 * SendMailMenu.java
 * 
 * Copyright (C) 2010 Mrityunjoy Saha
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

import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.logging.Logger;
import java.awt.Desktop;
import java.net.URI;

/**
 * It opens the current document in user's default e-mail application. The document
 * is sent as attachment and subject text is pre-populated.
 * <p>
 * Non-persistent (temporary) documents are sent as text in message body.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
// @TODO Save the document (if not a temporary document) while sending as e-mail.
public class SendMailMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code SendMailMenu}.
     */
    public SendMailMenu() {
    }

    /**
     * It opens the current document in user's e-mail application.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    protected void execute(InputParams in, OutputParams out) {
        // Get current document URI.
        AbstractDocument currentDocument =
                getContext().getEditorProperties().getCurrentDocument();
        sendMail(currentDocument);
    }

    /**
     * Sends the specified document as e-mail using host computers default
     * e-mail client. If the document is temporary, the content is placed in message
     * body as text; otherwise the document is sent as attachment.
     * @param file A document to be sent as e-mail.
     */
    private void sendMail(AbstractDocument file) {        
        Logger.logInfo("Opening e-mail client. Document: " + file);
        try {
            // Open the current document in default web browser
            if (!Desktop.isDesktopSupported()) {
                return;
            }

//            URI mailURI = new URI("mailto:receiver?subject=test&amp;body=" + encodeURIcomponent(
//                    file.getEditor().getDocument().getText(0, file.getEditor().
//                    getDocument().getLength())));
            String receiver = System.getProperty("user.name");
            String subject = encodeURIcomponent("apextextfile");
            String body = encodeURIcomponent(file.getEditor().getDocument().
                    getText(0, file.getEditor().getDocument().getLength()));
            String uriString = "mailto:" + receiver + "?subject=" + subject + "&body=" + body;           
            URI mailURI = new URI(
                    uriString);
            Desktop.getDesktop().mail(mailURI);
        } catch (Exception ex) {
            Logger.logError("Failed to send mail. Document: "
                    + file.getAbsolutePath(), ex);
            ex.printStackTrace();
        }
    }

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    @Override
    protected void postProcess(InputParams in, OutputParams out) {
    }

    /** 
     * Converts a string into something you can safely insert into a URL.
     * @param s The input string.
     * @return The encoded string.
     */
    public static String encodeURIcomponent(String s) {
        StringBuilder o = new StringBuilder();
        for (char ch : s.toCharArray()) {
            o.append(toHex(ch));
        }
        return o.toString();
    }

    /**
     * Converts a character to hex character.
     * @param ch The input character.
     * @return The hex representation of the character.
     */
    private static char toHex(int ch) {
        return (char) (ch < 10
                ? '0' + ch
                : 'A' + ch - 10);
    }

    /**
     * Returns whether or not the given character is unsafe in URL.
     * @param ch The input character.
     * @return {@code true} if input character is unsafe n URL; otherwise returns {@code false}.
     */
    private static boolean isUnsafe(char ch) {
        if (ch > 128 || ch < 0) {
            return true;
        }
        return " %$&+,/:;=?@<>#\"% {".indexOf(ch) >= 0;
    }
}
