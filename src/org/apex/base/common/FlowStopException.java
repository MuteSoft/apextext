/*
 * FlowStopException.java
 * Created on 21 Oct, 2007, 12:48:02 AM
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
 * An exception that provides information on a flow error. {@code FlowStopException}
 * provides information like error code, error message and additional message.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class FlowStopException extends Exception {

    /**
     * The error code.
     */
    private int errorCode;
    /**
     * Place holders.
     */
    private String placeHolders;

    /**
     * Creates an new instance of {@code FlowStopException} using
     * specified message and error code.
     * @param message The error message.
     * @param errorCode The error code.
     */
    public FlowStopException(String message, int errorCode) {
        this(message, errorCode, (Throwable) null);
    }

    /**
     * Creates an new instance of {@code FlowStopException} using
     * specified message, error code and place holders.
     * @param message The error message.
     * @param errorCode The error code.
     * @param placeHolders Place holders.
     */
    public FlowStopException(String message, int errorCode, String placeHolders) {
        this(message, errorCode, (Throwable) null);
        this.placeHolders = placeHolders;
    }

    /**
     * Creates an new instance of {@code FlowStopException} using
     * specified message, error code and {@code Throwable}.
     * @param message The error message.
     * @param errorCode The error code.
     * @param t The throwable.
     */
    public FlowStopException(String message, int errorCode, Throwable t) {
        super(message, t);
        this.errorCode = errorCode;
    }

    /**
     * Returns the error code.
     * @return The error code.
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Returns place holders.
     * @return Place holders.
     */
    public String getPlaceHolders() {
        return placeHolders;
    }
}