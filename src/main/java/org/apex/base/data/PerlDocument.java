/*
 * PerlDocument.java 
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

import org.apex.base.highlighter.style.DocumentStyle;
import org.apex.base.highlighter.style.DocumentLexers;
import org.apex.base.validator.AbstractValidator;
import org.apex.base.validator.PerlDocumentValidator;

/**
 * A Perl document which can be viewed and edited in the application.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class PerlDocument extends AbstractDocument {

    /**
     * The underlying document validator.
     */
    private static AbstractValidator validator;
    /**
     * The syntax style information object.
     */
    private static DocumentStyle style;

    /**
     * Constructs a new Perl document with given name.
     * @param name The document name.
     * @param docType The document type.
     */
    public PerlDocument(String name, IDocumentType docType) {
        super(name, docType);
    }

    /**
     * Constructs a Perl document with given path and name.
     * @param path The path of document in file system.
     * @param name The name of document.
     * @param docType The document type.
     */
    public PerlDocument(String path, String name, IDocumentType docType) {
        super(name, path, docType);
    }

    @Override
    public boolean isTemporary() {
        return super.isTemporary();
    }

    @Override
    public AbstractDocument clone(String absolutePath) {
        return super.copyPropertiesTo(new PerlDocument(absolutePath, getDocumentType()));
    }

    @Override
    public AbstractValidator getValidator() {
        if (validator == null) {
            validator = new PerlDocumentValidator();
        }
        return validator;
    }

    @Override
    public Object getLexer() {
        return DocumentLexers.PERL_LEXER;
    }

    @Override
    public DocumentStyle getDocumentStyle() {
        if (style == null) {
            style = new DocumentStyle(getDocumentType());
        }
        return style;
    }
}
