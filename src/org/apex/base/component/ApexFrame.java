/*
 * ApexFrame.java
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

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 * An extension of {@code JFrame}. It provides handy constructors and methods
 * to easily deal with frame. The main window of editor is an extension of {@code ApexFrame}.
 * It implements {@code WindowListener} and {@code KeyListener}.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ApexFrame extends JFrame implements WindowListener, KeyListener {

    /**
     * Constructs a new instance of {@code ApexFrame}. The frame window is
     * constructed with a blank title.
     */
    public ApexFrame() {
        this("");
    }

    /**
     * Constructs a new instance of {@code ApexFrame} using specified title.
     * @param text The frame title.
     */
    public ApexFrame(String text) {
        this(text, true, 600, 600);
    }

    /**
     * Constructs a new instance of {@code ApexFrame} using specified title, width and height.
     * @param text The frame title.
     * @param resizable A flag that indicates whether this frame is resizable by the user. 
     * @param width The frame width.
     * @param height The frame height.
     */
    public ApexFrame(String text, boolean resizable, int width, int height) {
        super(text);
        this.setPreferredSize(new Dimension(width, height));
        this.setResizable(resizable);
        this.addKeyListener(this);
        this.addWindowListener(this);
    }

    /**
     * Invoked when a key has been released.
     * @param ke The key event.
     */
    public void keyReleased(KeyEvent ke) {
    }

    /**
     * Invoked when a key has been typed.
     * @param ke The key event.
     */
    public void keyTyped(KeyEvent ke) {
    }

    /**
     * Invoked when a key has been pressed.
     * @param ke The key event.
     */
    public void keyPressed(KeyEvent ke) {
    }

    /**
     * Invoked when the user attempts to close the window
     * from the window's system menu.
     * @param we The window event.
     */
    public void windowClosing(WindowEvent we) {
    }

    /**
     * Invoked when a window has been closed as the result
     * of calling dispose on the window.
     * @param we The window event.
     */
    public void windowClosed(WindowEvent we) {
    }

    /**
     * Invoked when a window is changed from a normal to a
     * minimized state.
     * @param we The window event.
     */
    public void windowIconified(WindowEvent we) {
    }

    /**
     * Invoked the first time a window is made visible.
     * @param we The window event.
     */
    public void windowOpened(WindowEvent we) {
    }

    /**
     * Invoked when a window is changed from a minimized
     * to a normal state.
     * @param we The window event. 
     */
    public void windowDeiconified(WindowEvent we) {
    }

    /**
     * Invoked when the Window is set to be the active Window.
     * @param we The window event.
     */
    public void windowActivated(WindowEvent we) {
    }

    /**
     * Invoked when a Window is no longer the active Window.
     * @param we The window event.
     */
    public void windowDeactivated(WindowEvent we) {
    }
}