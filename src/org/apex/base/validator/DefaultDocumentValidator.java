/*
 * DefaultDocumentValidator.java 
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
package org.apex.base.validator;

import org.apex.base.data.AbstractDocument;

/**
 * A default document validator.
 * <p>
 * No validation is performed as document type is unknown.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DefaultDocumentValidator extends AbstractValidator {

    /**
     * Creates a new instance of DefaultDocumentValidator.
     */
    public DefaultDocumentValidator() {
        super();
    }

    @Override
    public void validate(AbstractDocument document) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
