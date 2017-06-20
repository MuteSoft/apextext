/*
 * CodeTemplateAction.java 
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
package org.apex.base.action;

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import org.apex.base.logging.Logger;
import org.apex.base.settings.CodeTemplatesConfiguration;
import org.apex.base.util.DocumentData;
import org.apex.base.util.StringUtil;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;

/**
 * An action used to support code template in text editor.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CodeTemplateAction extends AbstractAction {

    /**
     * Constructs a new instance of {@code CodeTemplateAction}.
     */
    public CodeTemplateAction() {
    }

    /**
     * It determines the previous word with reference to current cursor position.
     * The previous word is considered as abbreviation and finds the corresponding
     * code template from code template configuration. If a valid code template
     * is found, abbreviation is replaced by code template.
     * @param e The action event.
     */
    public void actionPerformed(ActionEvent e) {
        try {
            if (!(e.getSource() instanceof JTextComponent)) {
                return;
            }
            JTextComponent pane = (JTextPane) e.getSource();
            DefaultStyledDocument doc =
                    (DefaultStyledDocument) pane.getDocument();
            String abbreviation = getPreviousWord();
            if (!StringUtil.isNullOrEmpty(abbreviation)) {
                String template = getTemplate(abbreviation);
                // Actually need to replace text what is already typed.
                if (!StringUtil.isNullOrEmpty(template)) {
                    doc.replace(getContext().getEditorProperties().
                            getCurrentDocument().getEditor().
                            getCaretPosition() -
                            abbreviation.length(),
                            abbreviation.length(),
                            template,
                            new SimpleAttributeSet());
                }
            }
        } catch (BadLocationException ex) {
            Logger.logWarning("Failed to expand code template.", ex);
        }
    }

    /**
     * With reference to current cursor position it determines the previous word.
     * @return The previous word with reference to current cursor position.
     */
    private String getPreviousWord() {
        return DocumentData.getPreviousWord(getContext().getEditorProperties().
                getCurrentDocument().getEditor(), getContext().getEditorProperties().
                getCurrentDocument().getDocument());
    }

    /**
     * Returns the code template for a specified abbreviation.
     * @param prevWord The abbreviation.
     * @return The code template.
     */
    private String getTemplate(String prevWord) {
        if (StringUtil.isNullOrEmpty(prevWord)) {
            return null;
        }
        CodeTemplatesConfiguration codeTemplates =
                getContext().getConfiguration().
                getTemplateConfig().
                getCodeTemplate();
        return codeTemplates.getTemplate(prevWord);
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }
}
