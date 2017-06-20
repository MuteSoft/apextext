/*
 * SplashScreen.java
 * Created on 16 June, 2007, 8:11 PM 
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

import org.apex.base.logging.Logger;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import org.apex.base.constant.FontConstants;

/**
 * The splash screen which is displayed while editor loads required components
 * and realizes to screen.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class SplashScreen extends Thread {

    /**
     * Creates a new instance of {@code SplashScreen}.
     */
    public SplashScreen() {
    }

    @Override
    public void run() {
        showWelcomeScreen();
    }

    /**
     * Displays the splash screen and periodically updates
     * the screen.
     */
    private void showWelcomeScreen() {
        final java.awt.SplashScreen splash = java.awt.SplashScreen.getSplashScreen();
        if (splash == null) {
            return;
        }
        Graphics2D g = splash.createGraphics();
        if (g == null) {
            return;
        }
        for (int start = 0; start < 0; start++) {
            renderWelcomeScreen(g, start);
            splash.update();
            try {
                // Load all modules here.
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Logger.logWarning("Splash screen thread interrupted.", e);
            }
        }
        splash.close();

    }

    /**
     * Renders the welcome screen.
     * @param g The graphics object.
     * @param start The percentage of task completed.
     */
    private void renderWelcomeScreen(Graphics2D g, int start) {
        g.setComposite(AlphaComposite.Clear);        
        g.setPaintMode();
        g.setColor(Color.BLUE);
        g.setFont(FontConstants.WINDOW_TITLE_FONT);
        g.drawString("Loading. Please wait a while...", 60, 180);
    }
}
