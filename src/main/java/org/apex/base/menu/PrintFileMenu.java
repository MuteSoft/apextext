/*
 * PrintFileMenu.java
 * Created on December 20, 2006, 6:19 PM
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

import org.apex.base.data.AbstractDocument;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.logging.Logger;
import org.apex.base.util.PrintUtil;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import org.apex.base.component.TextEditor;

/**
 * A menu target to print a document. It shows a print dialog where
 * user can change printing preferences before printing the document.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 * @see PrintUtil  
 */
// @TODO Execute print in a different thread. Currently process initiated by print menu blocks the editor.
// @TODO Consider default page header, footer and margin while printing. Also, provide
// option to user to change these properties.
public class PrintFileMenu extends UILessMenu {

    /**
     * Creates a new instance of {@code PrintFileMenu}.
     */
    public PrintFileMenu() {
    }

    public void preProcess(InputParams in, OutputParams out) {
    }

    /**
     * Prints the current document. It shows a print dialog where
     * user can change print preferences before printing the document.
     * @param in Input parameters.
     * @param out Output parameters.
     */
    public void execute(InputParams in, OutputParams out) {
        doPrint(getContext().getEditorProperties().getCurrentDocument());
    }

    public void postProcess(InputParams in, OutputParams out) {
    }

    /**
     * Prints a specified document. It shows a print dialog where
     * user can change print preferences before printing the document.
     * @param file The document to print.
     */
    private void doPrint(AbstractDocument file) {
        TextEditor pane = file.getEditor();
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PrintUtil printUtil = new PrintUtil();
        printUtil.setSize(pane.getSize());
        printUtil.setPane(pane);
        //printUtil.setText(pane.getText());
        try {
            printerJob.setPrintable(printUtil);
            if (printerJob.printDialog()) {
                printerJob.print();
            }
        } catch (PrinterException pe) {
            Logger.logError("Failed to print document: " +
                    file.getAbsolutePath(), pe);
        } catch (Exception ex) {
            Logger.logError("Failed to print document: " +
                    file.getAbsolutePath(), ex);
        }
    }
}
