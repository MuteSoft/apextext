/*
 * RectangularIcon.java 
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

/**
 * A rectangular shape icon.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class RectangularIcon extends ApexIcon {

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(getColor());
        g.fill3DRect(x, y, getIconWidth(), getIconHeight(), false);
    }

    @Override
    public int getIconHeight() {
        return 12;
    }

    @Override
    public int getIconWidth() {
        return 12;
    }

    /**
     * Returns the fill color of icon.
     * @return The fill color of icon.
     */
    protected Color getColor() {
        return Color.RED;
    }
}
