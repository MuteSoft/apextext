/*
 * InvalidSettingsParameterException.java
 * Created on 21 Oct, 2007, 5:44:54 PM
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
package org.apex.base.settings;

import org.apex.base.common.FlowStopException;

/**
 * An exception that provides information on invalid user preferences. 
 * {@code InvalidSettingsParameterException} provides information like error code,
 * error message and additional message.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class InvalidSettingsParameterException extends FlowStopException {

    /**
     * Creates an new instance of {@code InvalidSettingsParameterException} using
     * specified message and error code.
     * @param message The error message.
     * @param errorCode The error code.
     */
    public InvalidSettingsParameterException(String message, int errorCode) {
        super(message, errorCode);
    }

    /**
     * Creates an new instance of {@code InvalidSettingsParameterException} using
     * specified message, error code and place holders.
     * @param message The error message.
     * @param errorCode The error code.
     * @param placeHolders Place holders.
     */
    public InvalidSettingsParameterException(String message, int errorCode,
                                             String placeHolders) {
        super(message, errorCode, placeHolders);
    }
}
