/*
 * ReadOnlyMenu.java 
 * Created on 1 Nov, 2008, 5:52:05 PM
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

import org.apex.base.core.ActionManager;
import org.apex.base.constant.MenuConstants;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.logging.Logger;

/**
 * A menu for marking or unmarking a document read only.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ReadOnlyMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code ReadOnlyMenu}.
     */
    public ReadOnlyMenu() {
    }

    @Override
    protected void execute(InputParams in, OutputParams out) {
        AbstractDocument file = getContext().getEditorProperties().
                getCurrentDocument();
        if (file != null) {
            if (file.isTemporary()) {
                ActionManager.setActionSelected(MenuConstants.READ_ONLY, false);
                return;
            }
            try {
                if (ActionManager.isActionSelected(MenuConstants.READ_ONLY)) {
                    file.setReadOnly();
                    file.getEditor().setEditable(false);
                    file.getEditor().getCaret().setVisible(false);
                    getContext().getEditorComponents().getStatusBar().
                            setWritableStatus(false);
                } else {
                    file.setWritable(true);
                    file.getEditor().setEditable(true);
                    file.getEditor().getCaret().setVisible(true);
                    getContext().getEditorComponents().getStatusBar().
                            setWritableStatus(true);
                }


            } catch (Exception ex) {
                Logger.logError("Coulld not change read only status of file: "
                        + file.getAbsolutePath(), ex);
            }
        }
    }

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    @Override
    protected void postProcess(InputParams in, OutputParams out) {
    }
}
