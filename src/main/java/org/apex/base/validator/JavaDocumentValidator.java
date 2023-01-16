/*
 * JavaDocumentValidator.java 
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

import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.AbstractDocument;

/**
 * A Java document validator.
 * <p>
 * It compiles the Java class to check the validity.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class JavaDocumentValidator extends AbstractValidator {

    /**
     * Creates a new instance of JavaDocumentValidator.
     */
    public JavaDocumentValidator() {
        super();
    }

    @Override
    public void validate(AbstractDocument document) {
        // compile the current file        
        MenuManager.getMenuById(MenuConstants.COMPILE_JAVA).
                processMenu(null, null);
    }
}
