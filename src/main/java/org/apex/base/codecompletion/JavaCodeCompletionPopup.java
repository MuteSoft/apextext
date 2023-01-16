/*
 * JavaCodeCompletionPopup.java
 * Created on 1 Nov, 2007, 1:39:48 PM
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
package org.apex.base.codecompletion;

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.InputParams;
import org.apex.base.data.JavaDocument;
import org.apex.base.logging.Logger;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;

/**
 * A code completion popup for Java document.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class JavaCodeCompletionPopup extends CodeCompletionPopup {

    /**
     * Constructs a new instance of {@code JavaCodeCompletionPopup}.
     */
    public JavaCodeCompletionPopup() {
    }

    /**
     * Populates list of code completion elements to be displayed in popup window.
     * It compiles the document to obtain the list of elements.
     * @return The list of code completion elements to be displayed in popup window.
     */
    @SuppressWarnings("unchecked")
    protected Vector<String> populateCodeCompletionData() {
        Vector<String> populatedData =
                new Vector<String>();
        // @TODO Optimization:: Check timestamp and then compile.
        // compile the current file
        try {
            InputParams input = new InputParams();
            input.put("RESULT_AREA_TO_BE_SHOWN", false);
            MenuManager.getMenuById(MenuConstants.COMPILE_JAVA).
                    processMenu(input, null);
        } catch (Exception ex) {
            Logger.logError(
                    "Could not compile Java file: " + getContext().
                    getEditorProperties().getCurrentDocument().
                    getAbsolutePath(),
                    ex);
        }
        try {
            // Get current directory
            // We are sure only java documents will invoke this code
            JavaDocument currentDoc = (JavaDocument) getContext().
                    getEditorProperties().
                    getCurrentDocument();
            // load the class
            // Create a File object on the root of the directory containing the class file
            File file = new File(currentDoc.getJavaDirectory());
            Logger.logInfo("Loading directory: " + file.getAbsolutePath());

            // Convert File to a URL
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            // Create a new class loader with the directory
            ClassLoader cl = new URLClassLoader(urls);
            String classToBeLoaded = currentDoc.getJavaPackage().equals("")
                    ? currentDoc.getClassName()
                    : currentDoc.getJavaPackage() + "."
                    + currentDoc.getClassName();
            Logger.logInfo("Loading class: " + classToBeLoaded);
            Class cls = cl.loadClass(classToBeLoaded);
            // Add code completion data
            // Get methods
            Method[] methods = cls.getMethods();
            // Get fields
            Field[] fields = cls.getFields();
            // Add fields
            for (int iCount = 0; iCount < fields.length; iCount++) {
                populatedData.add(fields[iCount].getName() + " - "
                        + fields[iCount].getType());
            }
            // Add methods
            for (int iCount = 0; iCount < methods.length; iCount++) {
                StringBuilder parameters = new StringBuilder("(");
                Class[] params = methods[iCount].getParameterTypes();
                for (int jCount = 0; jCount < params.length; jCount++) {
                    parameters.append(params[jCount].getSimpleName());
                    if (jCount < params.length - 1) {
                        parameters.append(", ");
                    }
                }
                parameters.append(")");
                populatedData.add(methods[iCount].getName()
                        + parameters.toString() + " - " + methods[iCount].
                        getReturnType().
                        getSimpleName());
            }
        } catch (Exception ex) {
            Logger.logError(
                    "Could not load class or failed to extract class metadata.",
                    ex);
            populatedData.clear();
            populatedData.add("Class Information Unavailable");
        }
        return populatedData;
    }

    @Override
    public String getFormattedSelectedValue() {
        String selectedVal = super.getFormattedSelectedValue();
        String[] splitValues = selectedVal.split("-");
        if (splitValues.length == 2) {
            if (splitValues[1].trim().equalsIgnoreCase("void")) {
                return splitValues[0] + ";";
            } else {
                String type = splitValues[1].trim();
                if (type.startsWith("class") || type.startsWith("interface")) {
                    type = type.substring(type.indexOf(" "));
                }
                return type.trim() + " var = " + splitValues[0].trim() + ";";
            }
        }
        return selectedVal;
    }
}
