/*
 * SelectableLabel.java 
 * Created on 25 Dec, 2009, 6:56:29 PM
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

import javax.swing.UIManager;

/**
 * The label component which can be selected by user.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class SelectableLabel extends ApexTextField {

    /**
     * Creates a new instance of {@code SelectableLabel}.
     */
    public SelectableLabel() {
        this("");
    }

    /**
     * Creates a new instance of {@code SelectableLabel} with given label.
     * @param text The text to be displayed as label.
     */
    public SelectableLabel(String text) {
        super(text);
        this.setEditable(false);
        this.setBorder(null);
        this.setForeground(UIManager.getColor("Label.foreground"));
        this.setFont(UIManager.getFont("Label.font"));
    }
}
