/*
 * BasicPopup.java 
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

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;
import java.awt.Component;
import javax.swing.Popup;
import javax.swing.PopupFactory;

/**
 * A base class for creating popup window. {@code Popup}s are used to
 * display a {@code Component} above all other {@code Component}s
 * in a particular containment hierarchy.
 * <p>
 * In editor application a popup window can be used to show quick menus in edit area of editor,
 * to display a document queue for quick navigation between documents etc.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class BasicPopup {

    /**
     * Factory of popup instances.
     */
    protected PopupFactory factory;
    /**
     * The popup to be displayed. 
     */
    protected Popup popup;
    /**
     * A boolean flag indicates whether the popup window is visible.
     */
    protected boolean visible;

    /**
     * Creates a popup window.
     */
    protected BasicPopup() {
        init();
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Returns the parent component of popup window.
     * @return The parent component.
     */
    protected abstract Component getParent();

    /**
     * Returns the X location of popup window on screen.
     * @return The X location on screen.
     */
    protected abstract int getXLocation();

    /**
     * Returns the Y location of popup window on screen.
     * @return The Y location on screen.
     */
    protected abstract int getYLocation();

    /**
     * Hides the popup window. Sets the popup visibility indicator boolean value to {@code false}.
     */
    public void hide() {
        if (popup != null) {
            popup.hide();
            this.visible = false;
        }
    }

    /**
     * Returns a boolean indicating whether or not the popup window is visible now.
     * @return {@code true} if popup window is visible; otherwise returns {@code false}.
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Sets a popup window visibility indicator.
     * @param visible Popup window visibility indicator.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Returns the component to display in popup window. For example, a {@code List}
     * containing document names can be displayed in a popup window.
     * @return The component to display in popup window.
     */
    protected abstract Component getComponent();

    /**
     * Shows the popup window. Sets the popup visibility indicator boolean value to {@code true}.
     *
     */
    public void show() {
        popup = factory.getPopup(getParent(), getComponent(), getXLocation(),
                getYLocation());
        popup.show();
        this.visible = true;
    }

    /**
     * Initialize the popup factory.
     */
    protected void init() {
        factory = PopupFactory.getSharedInstance();
    }
}
