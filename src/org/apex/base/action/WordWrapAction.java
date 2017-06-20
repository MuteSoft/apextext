/*
 * WordWrapAction.java 
 * Created on 29 Dec, 2009, 11:25:15 PM
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
package org.apex.base.action;

import java.awt.event.ActionEvent;
import org.apex.base.component.ApexTextPane;
import org.apex.base.data.MenuNode;

/**
 * Action responsible for word wrap.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class WordWrapAction extends CustomAction {

    /**
     * Creates a new instance of {@code InvertCaseAction}.
     * @param menu The menu node.
     * @see MenuNode
     */
    public WordWrapAction(MenuNode menu) {
        super(menu);
    }

    public void actionPerformed(ActionEvent e) {
        ApexTextPane target = (ApexTextPane) getTextComponent(e);
        if (target.isWordWrapEnabled()) {
            target.setWordWrapEnabled(false);
        } else {
            target.setWordWrapEnabled(true);
        }
        // @TODO Check the performance of revalidating the component tree. Note that, repaint() is not working.
        target.revalidate();
    }
}
