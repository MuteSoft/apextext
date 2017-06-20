/*
 * DocumentClass.java
 * Created on June 26, 2009, 11:56 PM
 *
 * Copyright (C) 2009 Mrityunjoy Saha
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

import org.apex.base.highlighter.style.DocumentStyle;
import org.apex.base.validator.AbstractValidator;

/**
 * An interface for document classes supported by editor platform.
 * @version 1.0
 * @since Apex 1.1
 * @author mrityunjoy_saha
 */
public interface DocumentClass {

    /**
     * Returns the lexer associated with this document.
     * <p>
     * The lexer is responsible to highlight (syntax highlighting) the underlying document.
     * @return The lexer associated with this document.
     */
    Object getLexer();

    /**
     * Returns the associated validator of this document.
     * <p>
     * The validator is responsible to validate the underlying document.
     * An XML validator may check whether the document is well formed and a Java validator
     * may compile the class to validate the document.
     * @return The validator associated with this document.
     */
    AbstractValidator getValidator();

    /**
     * Returns the document type.
     * @return Document type.
     * @see DocumentType
     */
    IDocumentType getDocumentType();

    /**
     * Returns the object containing syntax style information for this document.
     * <p>
     * The associated lexer of this document uses this syntax style information object for
     * syntax highlighting.
     * @return The object containing syntax style information.
     */
    DocumentStyle getDocumentStyle();
}
