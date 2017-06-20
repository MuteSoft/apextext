/* 
 * BufferOverflowException.java
 * 
 * Copyright (C) 2002 Stephen Ostermiller
 * http://ostermiller.org/contact.pl?regarding=Java+Utilities
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
package org.apex.base.highlighter.util;

import java.io.IOException;

/**
 * An indication that there was a buffer overflow.
 *
 * @author Stephen Ostermiller
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class BufferOverflowException extends IOException {

    /**
     * Create a new {@code BufferOverflowException}.
     *
     * @since ostermillerutils 1.00.00
     */
    public BufferOverflowException() {
        super();
    }

    /**
     * Create a new Exception with the given message.
     *
     * @param msg Error message.
     *
     * @since ostermillerutils 1.00.00
     */
    public BufferOverflowException(String msg) {
        super(msg);
    }
}
