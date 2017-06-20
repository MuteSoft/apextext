/*
 * PrintUtil.java
 * Created on December 20, 2006, 6:20 PM 
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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import javax.swing.JTextPane;

/**
 * A utility class to print a document displayed in text editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class PrintUtil implements Printable {

    /**
     * The text editor's size.
     */
    private Dimension dimension;
    /**
     * The text editor.
     */
    private JTextPane textPane;

    /**
     * Sets the size of text editor.
     * @param dimension Size of text editor.
     */
    public void setSize(Dimension dimension) {
        this.dimension = dimension;
    }

    /**
     * Sets the text editor.
     * @param textPane The text editor.
     */
    public void setPane(JTextPane textPane) {
        this.textPane = textPane;
    }

    /**
     * Prints the page at the specified index into the specified 
     * {@code Graphics} context in the specified format.
     * @param graphics The context into which the page is drawn.
     * @param pageFormat The size and orientation of the page being drawn.
     * @param pageIndex The zero based index of the page to be drawn.
     * @return {@code Printable.PAGE_EXISTS} if the page is rendered successfully
     *               or {@code Printable.NO_SUCH_PAGE} if {@code pageIndex} specifies a
     *               non-existent page.
     */
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        /* get component width and table height */

        double compWidth = dimension.width;
        double compHeight = dimension.height;

        /* get page width and page height */
        double pageWidth = pageFormat.getImageableWidth();
        double pageHeight = pageFormat.getImageableHeight();
        double scale = pageWidth / compWidth;

        /* calculate the no. of pages to print */
        final int totalNumPages = (int) Math.ceil((scale * compHeight) /
                pageHeight)-1;
        if (pageIndex > totalNumPages) {
            return (NO_SUCH_PAGE);
        } // end if
        else {
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            g2d.translate(0f, 0f);
            g2d.translate(0f, -pageIndex * pageHeight);
            g2d.scale(scale, scale);
            textPane.paint(g2d);
            return (PAGE_EXISTS);
        } // end else

    }
}
