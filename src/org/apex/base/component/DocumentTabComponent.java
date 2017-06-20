/*
 * DocumentTabComponent.java
 * Created on February 22, 2007, 10:26 PM  
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

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.ActionManager;
import org.apex.base.data.AbstractDocument;
import org.apex.base.core.PopupMenuManager;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.event.PopupMenuBarListener;
import org.apex.base.core.MenuManager;
import org.apex.base.logging.Logger;
import org.apex.base.menu.CloseFileMenu;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;

/**
 * A tab component that is responsible for rendering the
 * title and/or icon for a document tab in {@code ApexTabbedPane}.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DocumentTabComponent extends AbstractTabComponent implements
        MouseListener {

    /**
     * Creates a new tab component using a document. Tab title and icon are
     * extracted from document.
     * @param pane The container of tab.
     * @param file The document.
     */
    public DocumentTabComponent(ApexTabbedPane pane, AbstractDocument file) {
        super(pane, file);
    }

    /**
     * Creates  and adds components to display area of tab component. 
     * <p>
     * It adds an icon, title and a close closeButton to tab component.
     */
    protected void addComponents() {
        if (this.getPane() == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        setOpaque(false);
        this.add(getTabIcon());
        this.add(getTabTitle());       
        this.setActiveCloseIcon(ACTIVE_CLOSE_ICON);
        this.setPassiveCloseicon(PASSIVE_CLOSE_ICON);
        ApexButton closeButton = new AbstractIconButton(getPassiveCloseicon()) {

            protected void doOnClick(ActionEvent e) {
                AbstractDocument document = getDocument();
                if (document != null) {
                    // Document Closing Event. Check for unsaved document here.
                    CloseFileMenu closeFile =
                            (CloseFileMenu) MenuManager.getMenuById(
                            MenuConstants.CLOSE_FILE);
                    closeFile.closeFile(document);
                }
            }
        };
        // closeButton.setBorder(BorderFactory.createRaisedBevelBorder());
        this.setCloseButton(closeButton);
        this.add(closeButton);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 3));
        this.addMouseListener(this);
        // To handle mouse right click on document tab.
        this.addMouseListener(new PopupMenuBarListener(
                PopupMenuManager.DOCUMENT_TAB_POPUP));
    }

    /**
     * Based on number of clicks different actions are performed.     
     * If it is a single click the document tab is selected and hence it makes 
     * the corresponding document visible. In case of double click visibility of left hand
     * document selector is altered
     * @param e The mouse event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {

            ActionManager.setActionSelected(MenuConstants.VIEW_DOCUMENT_SELECTOR,
                    !ActionManager.isActionSelected(
                    MenuConstants.VIEW_DOCUMENT_SELECTOR));
            MenuManager.getMenuById(MenuConstants.VIEW_DOCUMENT_SELECTOR).
                    processMenu(
                    new InputParams(),
                    new OutputParams());
        } else {
            int i = getIndex();
            if (i != -1) {
                this.getPane().setSelectedIndex(i);
                Logger.logInfo("Document tab selected: " + i,
                        getClass().getName(),
                        "mouseClicked");
            }
        }
    }

    /**
     * Returns the associated document.
     * @return The document.
     */
    public AbstractDocument getDocument() {
        int selectedTabIndex = getIndex();
        if (selectedTabIndex >= 0 && selectedTabIndex < getPane().getTabCount()) {
            String toolTip = getContext().getEditorComponents().getEditorBody().
                    getDocsWindow().
                    getDocsTabbedPane().
                    getToolTipTextAt(selectedTabIndex);
            return getContext().getEditorProperties().getOpenDocument(toolTip);
        }
        return null;

    }
}
