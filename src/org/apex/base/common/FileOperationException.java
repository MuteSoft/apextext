/*
 * FileOperationException.java 
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
package org.apex.base.common;

/**
 * An exception to report unwanted conditions during file operations in file system.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class FileOperationException extends FlowStopException {

    /**
     * Creates a new instance of {@code FileOperationException} with specified error code
     * and error message.
     * @param message The error message.
     * @param errorCode The error code.
     */
    public FileOperationException(String message, int errorCode) {
        super(message, errorCode);
    }

    /**
     * Creates a new instance of {@code FileOperationException} with specified error code,
     * error message and place holders.
     * @param message The error message.
     * @param errorCode The error code.
     * @param placeHolders Place holders.
     */
    public FileOperationException(String message, int errorCode,
                                  String placeHolders) {
        super(message, errorCode, placeHolders);
    }
}
