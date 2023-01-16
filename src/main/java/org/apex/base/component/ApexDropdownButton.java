/*
 * ApexDropdownButton.java 
 * Created on 27 Oct, 2009, 10:03:08 PM
 *
 * Copyright (C) 2009 Mrityunjoy Saha
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

/**
 * A dropdown button component class. A button is split into two parts. The
 * first part shows the default action and the second part shows an arrow. When
 * mouse is over the component it shows a separation line between two parts. The arrow
 * can be placed at right or bottom. When arrow is clicked, a popup menu is displayed
 * with all available menu items.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class ApexDropdownButton extends JButton implements MouseListener {

    /**
     * Shadow color constant.
     */
    private static Color shadow = UIManager.getColor("controlShadow");
    /**
     * Dark shadow color constant.
     */
    private static Color darkShadow = Color.BLACK;
    /**
     * Highlight color constant.
     */
    private static Color highlight = UIManager.getColor("controlLtHighlight");
    /**
     * Gap between text and arrow button.
     */
    private int arrowGap = 12;
    /**
     * Size of the arrow.
     */
    private int arrowSize = 3;
    /**
     * A boolean that indicates whether or not a separator line between text and
     * arrow button to be drawn.
     */
    private boolean separatorLineDrawn = false;
    /**
     * The popupup menu which is displayed when arrow button is clciked.
     */
    private JPopupMenu popup;
    /**
     * Position od arrow button. It can be right or bottom.
     */
    private int arrowOrientation = RIGHT_ORIENTED_ARROW;
    /**
     * Key for right oriented arrow button.
     */
    public static final int RIGHT_ORIENTED_ARROW = 0;
    /**
     * Key for bottom oriented arroe button.
     */
    public static final int BOTTOM_ORIENTED_ARROW = 1;

    /**
     * Creates a new instance of dropdown button with given text and popup menu.
     * <p>
     * By default arrow is positioned at right side of the button.
     * @param label The text to be displayed in the button.
     * @param popup The popup menu to be displayed on click of arrow button.
     */
    public ApexDropdownButton(String label, JPopupMenu popup) {
        this(label, popup, RIGHT_ORIENTED_ARROW);
    }

    /**
     * Creates a new instance of dropdown button with given text,
     * arrow orientation and popup menu.
     * @param label The text to be displayed in the button.
     * @param popup The popup menu to be displayed on click of arrow button.
     * @param arrowOrientation The position of the arrow button.
     * @see #RIGHT_ORIENTED_ARROW
     * @see #BOTTOM_ORIENTED_ARROW
     */
    public ApexDropdownButton(String label, JPopupMenu popup,
            int arrowOrientation) {
        super(label);
        this.popup = popup;
        initialize(arrowOrientation);
    }

    /**
     * Creates a new instance of dropdown button with given action and popup menu.
     * The provided action is associated with the button.
     * <P>
     * By default arrow is positioned at right side of the button.
     * @param action The action associated with the button.
     * @param popup The popup menu to be displayed on click of arrow button.
     */
    public ApexDropdownButton(Action action, JPopupMenu popup) {
        this(action, popup, RIGHT_ORIENTED_ARROW);
    }

    /**
     * Creates a new instance of dropdown button with given action, arrow position and popup menu.
     * The provided action is associated with the button.
     * @param action The action associated with the button.
     * @param popup The popup menu to be displayed on click of arrow button.
     * @param arrowOrientation The position of the arrow button.
     * @see #RIGHT_ORIENTED_ARROW
     * @see #BOTTOM_ORIENTED_ARROW
     */
    public ApexDropdownButton(Action action, JPopupMenu popup,
            int arrowOrientation) {
        super(action);
        this.popup = popup;
        initialize(arrowOrientation);
    }

    /**
     * Initializes the dropdown button. It determines the gap between button text
     * and arrow. Also, it registers listeners to the button.
     * @param arrowOrientation The position of the arrow button.
     */
    private void initialize(int arrowOrientation) {
        if (arrowOrientation != RIGHT_ORIENTED_ARROW && arrowOrientation != BOTTOM_ORIENTED_ARROW) {
            throw new IllegalArgumentException(
                    "Arrow orientation must be one of - BOTTOM_ORIENTED_ARROW and RIGHT_ORIENTED_ARROW");
        }
        this.arrowOrientation = arrowOrientation;
        if (this.arrowOrientation == RIGHT_ORIENTED_ARROW) {
            this.arrowGap = 12;
        } else if (this.arrowOrientation == BOTTOM_ORIENTED_ARROW) {
            this.arrowGap = 8;
        }
        this.addMouseListener(this);
        this.setFocusable(false);
    }

    @Override
    public Insets getInsets() {
        Insets margin = super.getInsets();
        if (arrowOrientation == RIGHT_ORIENTED_ARROW) {
            margin.right = margin.right + arrowGap;
        } else if (arrowOrientation == BOTTOM_ORIENTED_ARROW) {
            margin.bottom = margin.bottom + arrowGap;
        }
        return margin;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension originalSize = super.getPreferredSize();
        if (separatorLineDrawn) {
            g.setColor(shadow);
            if (this.arrowOrientation == RIGHT_ORIENTED_ARROW) {
                int separatorLineX = originalSize.width - arrowGap - 1;
                g.drawLine(separatorLineX, 1, separatorLineX,
                        originalSize.height - 2);
            } else if (this.arrowOrientation == BOTTOM_ORIENTED_ARROW) {
                int separatorLineY = originalSize.height - arrowGap - 4;
                g.drawLine(0, separatorLineY, originalSize.width,
                        separatorLineY);
            }
        }
        int arrowX = 0;
        int arrowY = 0;
        if (this.arrowOrientation == RIGHT_ORIENTED_ARROW) {
            arrowX = (originalSize.width - arrowGap) + arrowGap / 2 - arrowSize / 2;
            arrowY = (originalSize.height / 2) - (arrowSize / 2);
        }
        if (this.arrowOrientation == BOTTOM_ORIENTED_ARROW) {
            arrowX = (originalSize.width / 2) - (arrowSize / 2);
            arrowY = (originalSize.height - arrowGap) + arrowGap / 2 - arrowSize / 2 - 2;
        }
        paintTriangle(g, arrowX, arrowY, arrowSize, SOUTH,
                isEnabled());
    }

    /**
     * Paints a triangle.
     * <p>
     * This method is copied from javax.swing.plaf.basic.BasicArrowButton.
     *@param g the {@code Graphics} to draw to
     * @param x the x coordinate
     * @param y the y coordinate
     * @param size the size of the triangle to draw
     * @param direction the direction in which to draw the arrow;
     *        one of {@code SwingConstants.NORTH},
     *        {@code SwingConstants.SOUTH}, {@code SwingConstants.EAST} or
     *        {@code SwingConstants.WEST}
     * @param isEnabled whether or not the arrow is drawn enabled
     */
    private void paintTriangle(Graphics g, int x, int y, int size,
            int direction, boolean isEnabled) {
        Color oldColor = g.getColor();
        int mid, i, j;

        j = 0;
        size = Math.max(size, 2);
        mid = (size / 2) - 1;

        g.translate(x, y);
        if (isEnabled) {
            g.setColor(darkShadow);
        } else {
            g.setColor(shadow);
        }

        switch (direction) {
            case NORTH:
                for (i = 0; i < size; i++) {
                    g.drawLine(mid - i, i, mid + i, i);
                }
                if (!isEnabled) {
                    g.setColor(highlight);
                    g.drawLine(mid - i + 2, i, mid + i, i);
                }
                break;
            case SOUTH:
                if (!isEnabled) {
                    g.translate(1, 1);
                    g.setColor(highlight);
                    for (i = size - 1; i >= 0; i--) {
                        g.drawLine(mid - i, j, mid + i, j);
                        j++;
                    }
                    g.translate(-1, -1);
                    g.setColor(shadow);
                }

                j = 0;
                for (i = size - 1; i >= 0; i--) {
                    g.drawLine(mid - i, j, mid + i, j);
                    j++;
                }
                break;
            case WEST:
                for (i = 0; i < size; i++) {
                    g.drawLine(i, mid - i, i, mid + i);
                }
                if (!isEnabled) {
                    g.setColor(highlight);
                    g.drawLine(i, mid - i + 2, i, mid + i);
                }
                break;
            case EAST:
                if (!isEnabled) {
                    g.translate(1, 1);
                    g.setColor(highlight);
                    for (i = size - 1; i >= 0; i--) {
                        g.drawLine(j, mid - i, j, mid + i);
                        j++;
                    }
                    g.translate(-1, -1);
                    g.setColor(shadow);
                }

                j = 0;
                for (i = size - 1; i >= 0; i--) {
                    g.drawLine(j, mid - i, j, mid + i);
                    j++;
                }
                break;
        }
        g.translate(-x, -y);
        g.setColor(oldColor);
    }

    public void mouseClicked(MouseEvent e) {
        if (!isEnabled()) {
            return;
        }
        if (((this.arrowOrientation == BOTTOM_ORIENTED_ARROW) && (e.getComponent().
                getHeight() - e.getY() <= arrowGap + 2)) || ((this.arrowOrientation == RIGHT_ORIENTED_ARROW) && (e.
                getComponent().getWidth() - e.getX() <= arrowGap + 2))) {
            if (popup != null) {
                popup.show(this, 0, this.getHeight());
            }
        }
    }

    @Override
    protected void fireActionPerformed(ActionEvent event) {
        Point mousePosition = getMousePosition();
        if (mousePosition != null) {
            if (((this.arrowOrientation == BOTTOM_ORIENTED_ARROW) && (this.
                    getHeight() - mousePosition.getY() <= arrowGap + 2)) || ((this.arrowOrientation == RIGHT_ORIENTED_ARROW) && (this.
                    getWidth() - mousePosition.getX() <= arrowGap + 2))) {
                return;
            }
        }
        super.fireActionPerformed(event);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        separatorLineDrawn = true;
    }

    public void mouseExited(MouseEvent e) {
        separatorLineDrawn = false;
    }

    /**
     * Returns the gap between button text and arrow.
     * @return The gap between button text and arrow.
     * @see #setArrowGap(int)
     */
    public int getArrowGap() {
        return arrowGap;
    }

    /**
     * Sets the gap between button text and arrow.
     * @param arrowGap Gap in pixel.
     * @see #getArrowGap()
     */
    public void setArrowGap(int arrowGap) {
        this.arrowGap = arrowGap;
        repaint();
    }

    /**
     * Returns the position of the arrow.
     * @return The position of the arrow.
     * @see #setArrowOrientation(int)
     * @see #RIGHT_ORIENTED_ARROW
     * @see #BOTTOM_ORIENTED_ARROW
     */
    public int getArrowOrientation() {
        return arrowOrientation;
    }

    /**
     * Sets the position of the arrow.
     * @param arrowOrientation The position of the arrow.
     * @see #getArrowOrientation()
     * @see #RIGHT_ORIENTED_ARROW
     * @see #BOTTOM_ORIENTED_ARROW
     */
    public void setArrowOrientation(int arrowOrientation) {
        this.arrowOrientation = arrowOrientation;
        repaint();
    }

    /**
     * Returns the arrow size. The size is in pixel.
     * @return The arrow size.
     * @see #setArrowSize(int)
     */
    public int getArrowSize() {
        return arrowSize;
    }

    /**
     * Sets the arroe size.
     * @param arrowSize The arrow size in pixel.
     * @see #getArrowSize()
     */
    public void setArrowSize(int arrowSize) {
        this.arrowSize = arrowSize;
        repaint();
    }
}

