/*
 * AbstractValidator.java 
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

import org.apex.base.core.EditorBase;
import org.apex.base.data.AbstractDocument;
import org.apex.base.data.EditorContext;

/**
 * The base class for all document validators. For different document types
 * separate validators are available to validate the document in a specific way.
 * <p>
 * For example, a java validator compiles the document to check the correctness of a java document. On
 * the other hand, an xml validator validates the document against a DTD or XSD.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class AbstractValidator {

    /**
     * Defines an {@code AbstractValidator} object.
     */
    public AbstractValidator() {
        super();
    }

    /**
     * Validates the given document.
     * @param document The given document.
     */
    public abstract void validate(AbstractDocument document);

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    public EditorContext getContext() {
        return EditorBase.getContext();
    }
}
