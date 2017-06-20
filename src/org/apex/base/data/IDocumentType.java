/*
 * EditorComponents.java
 * Created on January 15, 2009, 7:52 AM
 *
 * Copyright (C) 2010 Mrityunjoy Saha
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

import java.util.List;

/**
 * The interface for the document types.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public interface IDocumentType extends Cloneable {

    /**
     * Name of Java document type.
     */
    String JAVA_DOCUMENT = "JAVA";
    /**
     * Name of JSP_DOCUMENT document type.
     */
    String JSP_DOCUMENT = "JSP";
    /**
     * Name of C_DOCUMENT document type.
     */
    String C_DOCUMENT = "C";
    /**
     * Name of XML_DOCUMENT document type.
     */
    String XML_DOCUMENT = "XML";
    /**
     * Name of HTML_DOCUMENT document type.
     */
    String HTML_DOCUMENT = "HTML";
    /**
     * Name of JavaScript document type.
     */
    String JAVASCRIPT_DOCUMENT = "JS";
    /**
     * Name of PERL_DOCUMENT document type.
     */
    String PERL_DOCUMENT = "PERL";
    /**
     * Name of SQL_DOCUMENT document type.
     */
    String SQL_DOCUMENT = "SQL";
    /**
     * Name of Properties document type.
     */
    String PROPERTIES_DOCUMENT = "PROPERTIES";
    /**
     * Name of Default document type.
     */
    String DEFAULT_DOCUMENT = "TXT";

    /**
     * Returns default extension of a document type.
     * @return Default document extension.
     */
    String getDefaultExtension();

    /**
     * Returns the document class.
     * @return The document class.
     */
    String getDocumentClass();

    /**
     * Returns a complete list of supported document types.
     * <p>
     * It excludes the {@code DEFAULT_DOCUMENT} document type.
     * @return A list of supported document types.
     */
    List<IDocumentType> getDocumentTypes();

    /**
     * Finds the document type having given type name.
     * @param name The document type name.
     * @return The document type.
     */
    IDocumentType getDocumentTypeByName(String name);

    /**
     * Returns a complete list of supported document types.
     * @return A list of supported document types.
     */
    List<IDocumentType> getDocumentTypesIncludingDefault();

    /**
     * Returns display name of document type.
     * @return The display name of document type.
     */
    String getDisplayName();

    /**
     * Returns a list of file extensions considered as executable.
     * @return A list of file extensions.
     */
    List<String> getExecutableExtensions();

    /**
     * Determines whether or not a given file is executable from its
     * extension.
     * @param extension The file extension.
     * @return {@code true} if file is executable; otherwise returns {@code false}.
     */
    boolean isExecutable(String extension);

    /**
     * Returns a list of syntax token categories.
     * @return A list of syntax token categories.
     */
    List<String> getTokens();

    /**
     * Adds a document type to the list of supported document types.
     * @param documentType The document type to be added.
     */
    void add(IDocumentType documentType);

    /**
     * Removes the specified document type from the list of supported document types.
     * @param documentType The document type to be removed.
     */
    void remove(IDocumentType documentType);

    /**
     * Returns the name of document type.
     * @return The name of document type.
     */
    String getName();

    /**
     * Returns string representation of a document type.
     * @return Human readable text representation of this object.
     */
    @Override
    String toString();

    /**
     * Determines whether or not this document type is logically equivalent to
     * given document type.
     * @param docType The reference document type.
     * @return {@code true} if given document type is equivalent to this document
     * type, otherwise returns {@code false}.
     */
    @Override
    boolean equals(Object docType);

    /**
     * Determines whether or not this document type is logically equivalent to
     * a document type represented by given document type name.
     * @param docTypeName The reference document type name.
     * @return {@code true} if given document type name is logically equivalent to this document
     * type, otherwise returns {@code false}.
     */
    boolean equals(String docTypeName);

    /**
     * Checks whether the document type is built-in or added by user.
     * @return {@code true} if this document type is added by user.
     */
    boolean isCustomDocumentType();

    /**
     * Creates a clone of this document type.
     * @return The cloned document type.
     * @throws java.lang.CloneNotSupportedException
     */
    Object clone() throws CloneNotSupportedException;

    /**
     * Returns the default token used to mark the start of comment block.
     * @return The start comment token.
     */
    String getStartCommentToken();

    /**
     * Returns the default token used to mark the end of comment block.
     * @return The end comment token.
     */
    String getEndCommentToken();
}
