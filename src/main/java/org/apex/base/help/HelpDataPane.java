/*
 * HelpDataPane.java
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
package org.apex.base.help;

import java.awt.Container;
import java.awt.Cursor;
import java.io.IOException;
import java.net.URL;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;

/**
 * A pane to display help pages. Help pages are accessed as URL and displayed
 * in this pane.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class HelpDataPane extends JScrollPane implements HyperlinkListener {

    /**
     * The editor pane for displaying HTML files.
     */
    private JEditorPane html;

    /**
     * Creates a new instance of {@code HelpDataPane}.
     */
    public HelpDataPane() {
        super();
        html = new JEditorPane();
        html.setEditable(false);
        html.addHyperlinkListener(this);
        html.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES,
                Boolean.TRUE);
        JViewport vp = getViewport();
        vp.add(html);

    }

    /**
     * Notification of a change relative to a
     * hyperlink.
     */
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            linkActivated(e.getURL());
        }
    }

    /**
     * Open the page available at given URL.
     * @param url The target URL.F
     */
    public void setPage(URL url) {
        linkActivated(url);
    }

    /**
     * A callback method which is called when any link in the HTML page is activated.
     * @param u The link URL.
     */
    protected void linkActivated(URL u) {
        Cursor c = html.getCursor();
        Cursor waitCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
        html.setCursor(waitCursor);
        SwingUtilities.invokeLater(new PageLoader(u, c));
    }

    /**
     * HTML page loader.
     */
    private class PageLoader implements Runnable {

        /**
         * The URL to be loaded.
         */
        private URL url;
        /**
         * The cursor.
         */
        private Cursor cursor;

        /**
         * Creates a new instance of {@code PageLoader} with specified URL and cursor.
         * @param u The URL.
         * @param c The cursor.
         */
        PageLoader(URL u, Cursor c) {
            url = u;
            cursor = c;
        }

        public void run() {
            if (url == null) {
                // restore the original cursor
                html.setCursor(cursor);

                // PENDING(prinz) remove this hack when
                // automatic validation is activated.
                Container parent = html.getParent();
                parent.repaint();
            } else {
                Document doc = html.getDocument();
                try {
                    html.setPage(url);
                } catch (IOException ioe) {
                    try {
                        html.setPage(HelpTopic.getBlankPageURL());
                    } catch (IOException io) {
                    }
                    //html.setDocument(doc);
                    getToolkit().beep();
                } finally {
                    // schedule the cursor to revert after
                    // the paint has happended.
                    url = null;
                    SwingUtilities.invokeLater(this);
                }
            }
        }
    }
}
