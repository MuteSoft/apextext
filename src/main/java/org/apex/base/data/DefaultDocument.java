/*
 * DefaultDocument.java 
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
import org.apex.base.validator.AbstractValidator;
import org.apex.base.validator.DefaultDocumentValidator;

/**
 * A default type of document which can be viewed and edited in the application.
 * <p>
 * When a document type can not be determined the document is considered as {@code DefaultDocument}.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class DefaultDocument extends AbstractDocument {

    /**
     * The underlying document validator.
     */
    private static AbstractValidator validator;

    /**
     * Constructs a new default document with given name.
     * @param name The document name.
     * @param docType The document type.
     */
    public DefaultDocument(String name, IDocumentType docType) {
        super(name, docType);
    }

    /**
     * Constructs a default document with given path and name.
     * @param path The path of document in file system.
     * @param name The name of document.
     * @param docType The document type.
     */
    public DefaultDocument(String path, String name, IDocumentType docType) {
        super(path, name, docType);
    }

    @Override
    public AbstractDocument clone(String absolutePath) {
        return super.copyPropertiesTo(new DefaultDocument(absolutePath, getDocumentType()));
    }

    @Override
    public AbstractValidator getValidator() {
        if (validator == null) {
            validator = new DefaultDocumentValidator();
        }
        return validator;
    }

    /**
     * Syntax highlighting is not applicable for default document and hence
     * returns {@code null}.
     * @return {@code null}.
     */
    @Override
    public Object getLexer() {
        return null;
    }

    /**
     * Syntax highlighting is not applicable for default document and hence
     * returns {@code null}.
     * @return {@code null}.
     */
    @Override
    public DocumentStyle getDocumentStyle() {
        return null;
    }
}
