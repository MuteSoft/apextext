/*
 * EDTThreadCheckingRepaintManager.java
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
package org.apex.base.debug;

import java.awt.Component;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.JComponent;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;

/**
 * This class is used to debug event dispatch thread and UI painting related operations.
 * Logging is not done in this class as this class is used only for development purpose.
 * <p> 
 * <strong>Warning: </strong>Use this class only for development purpose.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class EDTThreadCheckingRepaintManager extends RepaintManager {

    /**
     * Holds the number of tab keys.
     */
    private int tabCount = 0;
    /**
     * A boolean that indicates whether component is showing on screen.
     */
    private boolean checkIsShowing = false;

    /**
     * Creates a new instance of {@code EDTThreadCheckingRepaintManager}.
     */
    public EDTThreadCheckingRepaintManager() {
        super();
    }

    /**
     * Creates a new instance of {@code EDTThreadCheckingRepaintManager} using 
     * on screen component visibility indicator.
     * @param checkIsShowing A boolean that indicates whether component is showing
     *                on screen.
     */
    public EDTThreadCheckingRepaintManager(boolean checkIsShowing) {
        super();
        this.checkIsShowing = checkIsShowing;
    }

    @Override
    public synchronized void addInvalidComponent(JComponent jComponent) {
        checkThread(jComponent);
        super.addInvalidComponent(jComponent);
    }

    /**
     * 
     * @param c
     */
    private void checkThread(JComponent c) {
        if (!SwingUtilities.isEventDispatchThread() && checkIsShowing(c)) {
            System.out.println("----------Wrong Thread START");
            System.out.println(getStracktraceAsString(new Exception()));
            dumpComponentTree(c);
            System.out.println("----------Wrong Thread END");
        }
    }

    /**
     * 
     * @param e
     * @return
     */
    private String getStracktraceAsString(Exception e) {
        ByteArrayOutputStream byteArrayOutputStream =
                new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        e.printStackTrace(printStream);
        printStream.flush();
        return byteArrayOutputStream.toString();
    }

    /**
     * 
     * @param c
     * @return
     */
    private boolean checkIsShowing(JComponent c) {
        if (this.checkIsShowing == false) {
            return true;
        } else {
            return c.isShowing();
        }
    }

    @Override
    public synchronized void addDirtyRegion(JComponent jComponent, int i,
                                            int i1, int i2, int i3) {
        checkThread(jComponent);
        super.addDirtyRegion(jComponent, i, i1, i2, i3);
    }

    /**
     * 
     * @param c
     */
    private void dumpComponentTree(Component c) {
        System.out.println("----------Component Tree");
        resetTabCount();
        for (; c != null; c = c.getParent()) {
            printTabIndent();
            System.out.println(c);
            printTabIndent();
            System.out.println("Showing:" + c.isShowing() + " Visible: " +
                    c.isVisible());
            incrementTabCount();
        }
    }

    /**
     * 
     */
    private void resetTabCount() {
        this.tabCount = 0;
    }

    /**
     * 
     */
    private void incrementTabCount() {
        this.tabCount++;
    }

    /**
     * 
     */
    private void printTabIndent() {
        for (int i = 0; i < this.tabCount; i++) {
            System.out.print("\t");
        }
    }
}
