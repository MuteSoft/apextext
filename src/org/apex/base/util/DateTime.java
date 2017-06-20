/*
 * DateTime.java
 * Created on March 19, 2007, 10:00 PM
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
package org.apex.base.util;

import org.apex.base.data.AbstractDocument;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * A date utility class.
 * <p>
 * This is a convenient class to get current date, time and compare date, time.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DateTime {

    /**
     * Returns a {@code Timestamp} object equivalent to given time in milliseconds.
     * @param millis Time in milliseconds. 
     * @return A {@code Timestamp} object.
     */
    public static Timestamp getTime(long millis) {
        return new Timestamp(millis);
    }

    /**
     * Returns current time as {@code Timestamp} object.
     * @return Current timestamp.
     */
    public static Timestamp getNow() {
        long nowMillis = System.currentTimeMillis();
        return getTime(nowMillis);
    }

    /**
     * Returns date in a specified format.
     * @param format The date format.
     * @return Current date in specified format.
     */
    public static String getNow(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        df.setLenient(false);
        return df.format(getNow());
    }

    /**
     * Returns current date as milliseconds.
     * @return Current date as milliseconds.
     */
    public static long getNowMillis() {
        return System.currentTimeMillis();
    }

    /**
     * Returns last modification timestamp of a {@code File}.
     * @param file The document.
     * @return Last modification date.
     */
    public static Timestamp getLastModTime(AbstractDocument file) {
        return getTime(file.lastModified());
    }

    /**
     * Determines whether given document is modified by an external application.
     * <p>
     * Last saved timestamp and last modification timestamp is compared to determine this.
     * @param file The document.
     * @return {@code true} if given document is externally modified; otherwise returns {@code false}.
     */
    public static boolean isFileExternallyModified(AbstractDocument file) {
        if (file.getLastSaved() < file.lastModified()) {
            return true;
        }
        return false;
    }

    /**
     * Creates a new instance of {@code DateTime}.
     */
    private DateTime() {
    }
}
