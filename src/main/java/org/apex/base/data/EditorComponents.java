/*
 * EditorComponents.java
 * Created on January 15, 2007, 7:52 AM
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
package org.apex.base.data;

import org.apex.base.component.ApexFrame;
import org.apex.base.component.TaskProgressIndicator;
import org.apex.base.core.EditorBody;
import org.apex.base.core.EditorMenuBar;
import org.apex.base.core.EditorToolBar;
import org.apex.base.ui.StatusBar;

/**
 * Holds top level editor components.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class EditorComponents {

    /**
     * Main editor window. 
     */
    private ApexFrame frame;
    /**
     * Editor menu bar.
     */
    private EditorMenuBar menuBar;
    /**
     * Editor tool bar.
     */
    private EditorToolBar toolBar;
    /**
     * Editor body. It contains document selector, document window,
     * palette and output window.
     */
    private EditorBody editorBody;
    /**
     * The status bar.
     */
    private StatusBar statusBar;
    /**
     * Task progress indicator. This indicator is displayed for long running tasks.
     */
    private TaskProgressIndicator progressPanel;

    /**
     * Sets the main window of editor.
     * @param mFrame Main window of editor.
     * @see #getFrame()
     */
    public synchronized void setFrame(ApexFrame mFrame) {
        this.frame = mFrame;
    }

    /**
     * Returns the main window of editor.
     * @return Main window of editor.
     * @see #setFrame(org.apex.base.component.ApexFrame)
     */
    public synchronized ApexFrame getFrame() {
        return this.frame;
    }

    /**
     * Returns the main menu-bar.
     * @return The main menu-bar.
     * @see #setMenuBar(org.apex.base.core.EditorMenuBar)
     */
    public synchronized EditorMenuBar getMenuBar() {
        return menuBar;
    }

    /**
     * Sets the main menu-bar.
     * @param menuBar The main menu-bar.
     * @see #getMenuBar()
     */
    public synchronized void setMenuBar(EditorMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    /**
     * Returns the main tool-bar.
     * @return The main tool-bar.
     * @see #setToolBar(org.apex.base.core.EditorToolBar)
     */
    public synchronized EditorToolBar getToolBar() {
        return toolBar;
    }

    /**
     * Sets the main tool-bar.
     * @param toolBar The main tool-bar.
     * @see #getToolBar()
     */
    public synchronized void setToolBar(EditorToolBar toolBar) {
        this.toolBar = toolBar;
    }

    /**
     * Returns the status bar of editor.
     * @return The status bar.
     * @see #setStatusBar(org.apex.base.ui.StatusBar)
     */
    public synchronized StatusBar getStatusBar() {
        return statusBar;
    }

    /**
     * Sets the status bar of editor.
     * @param statusBar The status bar.
     * @see #getStatusBar()
     */
    public synchronized void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
        if (statusBar != null && this.progressPanel == null) {
            this.progressPanel = TaskProgressIndicator.getSharedInstance();
        }        
    }

    /**
     * Returns the editor body component.
     * @return The editor body.
     * @see #setEditorBody(org.apex.base.core.EditorBody)
     */
    public synchronized EditorBody getEditorBody() {
        return this.editorBody;
    }

    /**
     * Sets the editor body component.
     * @param editorBody The editor body component.
     * @see #getEditorBody()
     */
    public synchronized void setEditorBody(EditorBody editorBody) {
        this.editorBody = editorBody;
    }

    /**
     * Returns the task progress indicator. This indicator is displayed for long running tasks.
     * @return The task progress indicator.
     */
    public synchronized TaskProgressIndicator getTaskProgressIndicator() {
        return this.progressPanel;
    }
}
