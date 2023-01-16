/*
 * TabbedPaneUI.java
 * Created on February 24, 2007, 6:55 PM 
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
package org.apex.base.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import static org.apex.base.constant.EditorKeyConstants.*;

/**
 * An extension of {@code BasicTabbedPaneUI}.
 * @author Mrityunjoy Saha
 * @version 1.4
 * @since Apex 1.0
 */
public class TabbedPaneUI extends BasicTabbedPaneUI {

    /**
     * Margin of the tabbed pane.
     */
    private static final Insets TABBED_PANE_MARGIN = new Insets(0, 0, 0, 0);
    /**
     * A boolean that indicates whether or not border of the tabbed pane to be painted.
     */
    private boolean borderPainted;
    /**
     * A graphics object to draw rounded rectangles.
     */
    private static final RoundRectangle2D.Float roundedRectangle = new RoundRectangle2D.Float();

    /**
     * Creates a new instance of {@code TabbedPaneUI}.
     * @param borderPainted A boolean that indicates whether or not border of the tabbed pane to be painted.
     */
    public TabbedPaneUI(boolean borderPainted) {
        this.borderPainted = borderPainted;
    }

    /**
     * Returns the tab label shift along x axis.
     * @param tabPlacement The placement for the tabs relative to the content.
     * @param tabIndex The index in tabbed pane where tab will be added.
     * @param isSelected A boolean that indicates whether or not the tab is selected.
     * @return The tab label shift along x axis.
     */
    @Override
    protected int getTabLabelShiftX(int tabPlacement, int tabIndex,
            boolean isSelected) {
        return 0;
    }

    /**
     * Returns the tab label shift along y axis.
     * @param tabPlacement The placement for the tabs relative to the content.
     * @param tabIndex The index in tabbed pane where tab will be added.
     * @param isSelected A boolean that indicates whether or not the tab is selected.
     * @return The tab label shift along y axis.
     */
    @Override
    protected int getTabLabelShiftY(int tabPlacement, int tabIndex,
            boolean isSelected) {
        return 0;
    }

    /**
     * Calculates the tab width of tab.
     * @param tabPlacement The placement for the tabs relative to the content.
     * @param tabIndex The index in tabbed pane where tab will be added.
     * @param metrics The font metrics.
     * @return The width of a tab.
     */
    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex,
            FontMetrics metrics) {
        return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 5;
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
            int x, int y, int w,
            int h, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        if (isSelected) {
            h = h - 2;
            g2d.setStroke(TAB_GRAPHICS_STROKE);
        } else {
            //h=h+1;
            g2d.setStroke(new BasicStroke(0.75f));
        }
        Color shadow1 = null;
        Color darkShadow1 = null;
        shadow1 = shadow;
        darkShadow1 = shadow;
        g.setColor(shadow1);
        switch (tabPlacement) {
            case LEFT:
                g.drawLine(x + 1, y + h - 2, x + 1, y + h - 2); // bottom-left highlight
                g.drawLine(x, y + 2, x, y + h - 3); // left highlight
                g.drawLine(x + 1, y + 1, x + 1, y + 1); // top-left highlight
                g.drawLine(x + 2, y, x + w - 1, y); // top highlight

                g.setColor(shadow1);
                g.drawLine(x + 2, y + h - 2, x + w - 1, y + h - 2); // bottom shadow

                g.setColor(darkShadow1);
                g.drawLine(x + 2, y + h - 1, x + w - 1, y + h - 1); // bottom dark shadow
                break;
            case RIGHT:
                g.drawLine(x, y, x + w - 3, y); // top highlight
                g.setColor(shadow1);
                g.drawLine(x, y + h - 2, x + w - 3, y + h - 2); // bottom shadow
                g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 3); // right shadow

                g.setColor(darkShadow1);
                g.drawLine(x + w - 2, y + 1, x + w - 2, y + 1); // top-right dark shadow
                g.drawLine(x + w - 2, y + h - 2, x + w - 2, y + h - 2); // bottom-right dark shadow
                g.drawLine(x + w - 1, y + 2, x + w - 1, y + h - 3); // right dark shadow
                g.drawLine(x, y + h - 1, x + w - 3, y + h - 1); // bottom dark shadow
                break;
            case BOTTOM:
                roundedRectangle.setRoundRect(x + 2, y - 2, w - 5, h + 1,
                        ROUNDED_TAB_ARC_WIDTH, ROUNDED_TAB_ARC_HEIGHT);
                g2d.draw(roundedRectangle);
                break;
            case TOP:
            default:
                roundedRectangle.setRoundRect(x + 2, y, w - 5, h + 1,
                        ROUNDED_TAB_ARC_WIDTH, ROUNDED_TAB_ARC_HEIGHT);
                g2d.draw(roundedRectangle);
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        g2d.setStroke(DEFAULT_GRAPHICS_STROKE);
    }

    /**
     * Paints background of tab.
     * @param g The graphics object.
     * @param tabPlacement The tab orientation.
     * @param tabIndex Index of the
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param w Width of the tab.
     * @param h Height of the tab.
     * @param isSelected Tab selection indicator.
     */
    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement,
            int tabIndex,
            int x, int y, int w, int h,
            boolean isSelected) {
        g.setColor(
                tabPane.getBackgroundAt(tabIndex));
        if (isSelected) {
            h = h - 2;
        }
        switch (tabPlacement) {
            case LEFT:
                g.fillRect(x + 1, y + 1, w - 1, h - 3);
                break;
            case RIGHT:
                g.fillRect(x, y + 1, w - 2, h - 3);
                break;
            case BOTTOM:
                g.fillRoundRect(x + 2, y - 2, w - 5, h + 1,
                        ROUNDED_TAB_ARC_WIDTH, ROUNDED_TAB_ARC_HEIGHT);
                break;
            case TOP:
            default:
                g.fillRoundRect(x + 2, y, w - 5, h + 1, ROUNDED_TAB_ARC_WIDTH,
                        ROUNDED_TAB_ARC_HEIGHT);
        }
    }

    /**
     * Returns the insets of the content border.
     * @param tabPlacement The tab orientation.
     * @return The insets of the content border.
     */
    @Override
    protected Insets getContentBorderInsets(int tabPlacement) {
        if (borderPainted) {
            return super.getContentBorderInsets(tabPlacement);
        }
        return TABBED_PANE_MARGIN;
    }

    /**
     * Paints the content border.
     * @param g The graphics object.
     * @param tabPlacement The tab orientation.
     * @param selectedIndex Selected tab index.
     */
    @Override
    protected void paintContentBorder(
            Graphics g,
            int tabPlacement,
            int selectedIndex) {
        if (borderPainted) {
            super.paintContentBorder(g, tabPlacement, selectedIndex);
        }
    }
}
