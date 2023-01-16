/*
 * JavaDocument.java
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

import org.apex.base.highlighter.lexer.JavaLexer;
import org.apex.base.highlighter.lexer.JavaToken;
import org.apex.base.highlighter.style.DocumentStyle;
import org.apex.base.highlighter.style.DocumentLexers;
import org.apex.base.logging.Logger;
import org.apex.base.validator.AbstractValidator;
import org.apex.base.validator.JavaDocumentValidator;
import java.io.FileInputStream;
import java.io.IOException;
import org.apex.base.codecompletion.CodeCompletionPopup;
import org.apex.base.codecompletion.JavaCodeCompletionPopup;

/**
 * A Java document which can be viewed and edited in the application.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class JavaDocument extends AbstractDocument {

    /**
     * The underlying document validator.
     */
    private static AbstractValidator validator;
    /**
     * The syntax style information object.
     */
    private static DocumentStyle style;
    /**
     * The Java code completion popup.
     */
    private static CodeCompletionPopup codeCompletionPopup;

    /**
     * Constructs a new Java document with given name.
     * @param name The document name.
     * @param docType The document type.
     */
    public JavaDocument(String name, IDocumentType docType) {
        super(name, docType);
    }

    /**
     * Constructs a Java document with given path and name.
     * @param path The path of document in file system.
     * @param name The name of document.
     * @param docType The document type.
     */
    public JavaDocument(String path, String name, IDocumentType docType) {
        super(path, name, docType);
    }

    @Override
    public boolean isTemporary() {
        return super.isTemporary();
    }

    @Override
    public AbstractDocument clone(String absolutePath) {
        return super.copyPropertiesTo(new JavaDocument(absolutePath, getDocumentType()));
    }

    @Override
    public AbstractValidator getValidator() {
        if (validator == null) {
            validator = new JavaDocumentValidator();
        }
        return validator;
    }

    @Override
    public Object getLexer() {
        return DocumentLexers.JAVA_LEXER;
    }

    @Override
    public DocumentStyle getDocumentStyle() {
        if (style == null) {
            style = new DocumentStyle(getDocumentType());
        }
        return style;
    }

    @Override
    public CodeCompletionPopup getCodeCompletionPopup() {
        if (codeCompletionPopup == null) {
            codeCompletionPopup = new JavaCodeCompletionPopup();
        }
        return codeCompletionPopup;
    }

    /**
     * Original directory of this document is supressed further
     * to consider the Java package of underlying Java document. Package part
     * is eliminated from directory string and returned.
     * <p>
     * If Java package is blank or {@code null} returns the original directory
     * of this document.
     * @param packageName Java package of this document.
     * @return The Java directory.
     */
    public String getJavaDirectory(String packageName) {
        String directory = getDirectory();
        if (packageName != null && !packageName.equals("")) {
            packageName = packageName.replace('.', '\\');
            if (directory.indexOf(packageName) != -1) {
                // Fix for bug id 2071970 (sourceforge.net)
                return directory.substring(0, directory.lastIndexOf(packageName));
            }
        }
        return directory;
    }

    /**
     * Original directory of this document is supressed further
     * to consider the Java package of underlying Java document. Package part
     * is eliminated from directory string and returned.
     * <p>
     * First it calls {@link #getJavaPackage() } method to fetch the Java package of this document.
     * <p>
     * If Java package is blank or {@code null} returns the original directory
     * of this document.
     * @return The Java directory.
     */
    public String getJavaDirectory() {
        return getJavaDirectory(getJavaPackage());
    }

    /**
     * Determines the Java package of underlying Java document.
     * <p>
     * It uses {@code JavaLexer} and {@code JavaToken} to do lexical
     * analysis of document content.
     * @return The Java package.
     */
    public String getJavaPackage() {
        StringBuilder packageName = new StringBuilder("");
        try {
            JavaLexer lexer = new JavaLexer(new FileInputStream(this.getAbsoluteFile()));
            JavaToken t = null;
            /*
             * Get the line content and get string between package and ';'
             * Get the first reserved word in this document. If the reserved word is 'package' follow below steps
             * otherwise return. Steps:
             * 1. Stop at separator ';' or comments or next reserved word which comes first.
             * 2. Immediatety after package statement if comment or a reserved word found before ';' consider packageName does
             * not exist.
             * 3. In valid scenario get each identifier in between (separate them by '.') and form the package name.
             */
            while ((t = (JavaToken) lexer.getNextToken()) != null) {
                if (t.isReservedWord()) {
                    if (!t.getContents().equals("package")) {
                        break;
                    }
                    //boolean isIdentifierExpected = true;
                    while ((t = (JavaToken) lexer.getNextToken()) != null) {
                        if (t.isIdentifier()) {
                            packageName.append(t.getContents());
                        } else if (t.isSeparator() &&
                                t.getContents().equals(".")) {
                            packageName.append(".");
                        }
                        if (t.isComment() || t.isReservedWord() || (t.isSeparator() && t.getContents().
                                equals(";"))) {
                            if (!t.isSeparator()) {
                                packageName.delete(0, packageName.length());
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            packageName.delete(0, packageName.length());
            Logger.logError("Failed to determine package for java docuement: " +
                    this.getAbsolutePath(), ex);
        } catch (Exception ex) {
            packageName.delete(0, packageName.length());
            Logger.logError("Failed to determine package for java docuement: " +
                    this.getAbsolutePath(), ex);
        }
        return packageName.toString().trim();
    }

    /**
     * Determines the Java class name.
     * @return The name of this Java class.
     */
    public String getClassName() {
        String fileName = getName();
        if (fileName.indexOf('.') != -1) {
            return fileName.substring(0, fileName.lastIndexOf('.'));
        }

        return fileName;
    }
}
