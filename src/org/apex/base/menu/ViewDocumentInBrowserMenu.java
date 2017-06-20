/*
 * ViewDocumentInBrowserMenu.java
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

import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.logging.Logger;
import java.awt.Desktop;
import java.io.File;
import java.net.URI;

/**
 * It opens the current document in user's browser application.
 * <p>
 * Non-persistent (temporary) documents do not have this facility.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ViewDocumentInBrowserMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code ViewDocumentInBrowserMenu}.
     */
    public ViewDocumentInBrowserMenu() {
    }

    /**
     * It opens the current document in user's browser application. Based on
     * document type it decides the registered browser application and launches
     * the same.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    @Override
    protected void execute(InputParams in, OutputParams out) {
        // Get current document URI.
        AbstractDocument currentDocument =
                getContext().getEditorProperties().getCurrentDocument();
        browse(currentDocument);
    }

    /**
     * It opens the given document in user's browser application. Based on
     * document type it decides the registered browser application and launches
     * the same.
     * @param file A document to be opened in browser window.
     */
    public void browse(File file) {
        Logger.logInfo("Opening in web browser. Document: " + file);
        // Don't open temporary files, they don't exist in hard disk drive.
        if (file == null || !file.exists()) {
            Logger.logInfo("Document: [" + file + "] does not exist.");
            return;
        }
        browse(file.toURI());
    }

    /**
     * It opens the given URI in user's browser application. Based on
     * URI it decides the registered browser application and launches
     * the same.
     * @param uri URI to beopened in browser window.
     */
    public void browse(URI uri) {
        if (uri == null) {
            Logger.logError(
                    "Provided URI is null and hence not attepmting to open in browser",
                    null);
            return;
        }
        try {
            // Open the current document in default web browser
            if (!Desktop.isDesktopSupported()) {
                return;
            }
            /* @TODO Following method opening the document in current registered application
             * (for the current document type) instead of user's default browser.
             */
            Desktop.getDesktop().browse(uri);
        } catch (Exception ex) {
            Logger.logError("Failed to open in web browser. URI: "
                    + uri.getRawPath(), ex);
        }
    }

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    @Override
    protected void postProcess(InputParams in, OutputParams out) {
    }
}
