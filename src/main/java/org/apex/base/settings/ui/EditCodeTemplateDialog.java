/*
 * EditCodeTemplate.java
 * Created on Oct 23, 2007, 11:25:16 AM
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
package org.apex.base.settings.ui;

import java.awt.Point;
import javax.swing.JTable;
import org.apex.base.settings.CodeTemplatesConfiguration;

/**
 * A dialog window to display the edit code template form.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class EditCodeTemplateDialog extends SimplePanelSettingsDialog {

    /**
     * The dialog window size.
     */
     private static final Point WINDOW_SIZE = new Point(500, 400);

    /**
     * Creates a new instance of {@code EditCodeTemplateDialog} using specified code templates
     * configuration, a table which displays all code templates, selected
     * abbreviation and selected code template..
     * @param codeTemplatesConfiguration Code templates configuration.
     * @param codeTemplates A table where code templates are displayed.
     * @param selectedAbbreviation Selected abbreviation.
     * @param selectedTemplate Selected code template.
     */
    public EditCodeTemplateDialog(
            CodeTemplatesConfiguration codeTemplatesConfiguration,
            JTable codeTemplates, String selectedAbbreviation,
            String selectedTemplate) {
        panel = new EditCodeTemplate(codeTemplatesConfiguration,
                codeTemplates, selectedAbbreviation, selectedTemplate);
    }

    @Override
    public Point windowSize() {
        return WINDOW_SIZE;
    }

    @Override
    public void makeDialogVisible() {
        ((EditCodeTemplate) panel).setContainerWindow(this.dialog);
        super.makeDialogVisible();
    }

    public String getTitle() {
        return "Edit Template";
    }
}
