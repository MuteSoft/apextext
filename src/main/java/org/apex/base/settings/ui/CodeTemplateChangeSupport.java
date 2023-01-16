/*
 * CodeTemplateChangeSupport.java
 * Created on Oct 23, 2007, 11:31:11 AM 
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

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.settings.CodeTemplatesConfiguration;
import org.apex.base.settings.InvalidSettingsParameterException;
import org.apex.base.ui.text.UIDialogModel;
import org.apex.base.util.StringUtil;
import java.util.Iterator;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * A helper class for add and edit code template UI pages.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CodeTemplateChangeSupport extends JPanel implements UIDialogModel {

    /**
     * The container dialog window.
     */
    protected JDialog containerWindow;
    /**
     * Selected abbreviation.
     */
    private String selectedAbbreviation;
    /**
     * Selected code template.
     */
    private String selectedTemplate;
    /**
     *  A table where code templates are displayed.
     */
    protected JTable codeTemplates;
    /**
     * Code templates configuration.
     */
    protected CodeTemplatesConfiguration codeTemplatesConfiguration;

    /**
     * Creates a new instance of {@code CodeTemplateChangeSupport} using specified
     * code templates configuration, a table where code templates are displayed, selected
     * abbreviation and selected code template.
     * @param codeTemplatesConfiguration Code templates configuration.
     * @param codeTemplates A table where code templates are displayed.
     * @param selectedAbbreviation Selected abbreviation.
     * @param selectedTemplate Selected code template.
     */
    public CodeTemplateChangeSupport(
            CodeTemplatesConfiguration codeTemplatesConfiguration,
            JTable codeTemplates, String selectedAbbreviation,
            String selectedTemplate) {
        this.codeTemplatesConfiguration = codeTemplatesConfiguration;
        this.codeTemplates = codeTemplates;
        this.selectedAbbreviation = selectedAbbreviation;
        this.selectedTemplate = selectedTemplate;
    }

    public void setContainerWindow(JDialog window) {
        this.containerWindow = window;
    }

    public JDialog getContainerWindow() {
        return this.containerWindow;
    }

    public EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Closes the dialog window.
     */
    protected void closeWindow() {
        getContainerWindow().setVisible(false);
    }

    /**
     * Returns the selected abbreviation.
     * @return The selected abbreviation.
     */
    public String getSelectedAbbreviation() {
        return this.selectedAbbreviation;
    }

    /**
     * Returns the selected code template.
     * @return The selected code template.
     */
    public String getSelectedTemplate() {
        return this.selectedTemplate;
    }

    /**
     * Validates the abbreviation entered by user.
     * @param enteredAbbreviation The abbreviation.
     * @throws InvalidSettingsParameterException If given abbreviation is null or blank or a duplicate one.
     */
    protected void validateAbbreviation(String enteredAbbreviation) throws
            InvalidSettingsParameterException {
        if (StringUtil.isNullOrEmpty(enteredAbbreviation)) {
            throw new InvalidSettingsParameterException("Invalid abbreviation.",
                    1004);
        }
        // Check for duplicate abbreviation
        Iterator templatesIterator = this.codeTemplatesConfiguration.
                getCodeTemplates().keySet().
                iterator();
        while (templatesIterator.hasNext()) {
            if (enteredAbbreviation.equalsIgnoreCase((String) templatesIterator.
                    next())) {
                throw new InvalidSettingsParameterException(
                        "Duplicate abbreviation.", 1013,
                        "ABBREVIATION='" + enteredAbbreviation + "'");
            }
        }
    }

    /**
     * Validates the code template entered by user.
     * @param enteredTemplate The code template.
     * @throws InvalidSettingsParameterException If given code template is wither null or blank.
     */
    protected void validateTemplate(String enteredTemplate) throws
            InvalidSettingsParameterException {
        if (StringUtil.isNullOrEmpty(enteredTemplate)) {
            throw new InvalidSettingsParameterException("Invalid template.",
                    1005);
        }
    }
}
