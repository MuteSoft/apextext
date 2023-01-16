/*
 * StringUtil.java
 * Created on March 21, 2007, 11:49 PM 
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

import org.apex.base.constant.CommonConstants;

/**
 * A utility class to deal with {@code String}s.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class StringUtil {

    /**
     * Inverts case of given {@code String}.
     * @param in A {@code String}.
     * @return The inverted {@code String}.
     */
    public static String invertCase(String in) {
        StringBuilder buffer = new StringBuilder();
        char[] chars = in.toCharArray();
        for (char c : chars) {
            if (Character.isUpperCase(c)) {
                c = Character.toLowerCase(c);
            } else if (Character.isLowerCase(c)) {
                c = Character.toUpperCase(c);
            } else {
                // No need to change the case;
            }
            buffer.append(c);
        }
        return buffer.toString();
    }

    /**
     * Useful method to check whether a given {@code String} is null or blank.
     * @param str A {@code String}.
     * @return {@code true} if given {@code String} is null or blank; otherwise returns {@code false}.
     */
    public static boolean isNullOrEmpty(String str) {
        return isNull(str) || isEmpty(str);
    }

    /**
     * Useful method to check whether a given string is blank.
     * @param str A {@code String}.
     * @return {@code true} if given {@code String} is blank; otherwise returns {@code false}.
     */
    public static boolean isEmpty(String str) {
        return str.trim().equals(CommonConstants.BLANK_TEXT);
    }

    /**
     * Useful method to check whether a given string is null.
     * @param str A {@code String}.
     * @return {@code true} if given {@code String} is null; otherwise returns {@code false}.
     */
    public static boolean isNull(String str) {
        return str == null;
    }

    /**
     * Returns the integer value of given {@code String}. A default value of '0'
     * is returned if given {@code String} is not a number or null or blank.
     * @param str A {@code String}.
     * @return The integer value represented by the argument in decimal.
     */
    public static int getInt(String str) {
        return getInt(str, 0);
    }

    /**
     * Returns the integer value of given {@code String}. The provided default value
     * is returned if given {@code String} is not a number or null or blank.
     * @param str A {@code String}.
     * @param defaultValue A default value.
     * @return The integer value represented by the argument in decimal.
     */
    public static int getInt(String str, int defaultValue) {
        if (isNullOrEmpty(str)) {
            return defaultValue;
        }
        int value = 0;
        try {
            value = Integer.parseInt(str);
            return value;
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * Checks whether a given string is numeric (contains only numbers).
     * @param text A {@code String}.
     * @return {@code true} if given string contains only numbers;
     *               otherwise returns {@code false}.
     */
    public static boolean isNumeric(String text) {
        if (isNullOrEmpty(text)) {
            return false;
        }
        for (int iCount = 0; iCount < text.length(); iCount++) {
            if (!Character.isDigit(text.charAt(iCount))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether a given string contains only alphabets and numbers.
     * @param text A {@code String}.
     * @return {@code true} if given string contains only alphabets and numbers;
     *               otherwise returns {@code false}.
     */
    public static boolean isAlphaNumeric(String text) {
        if (isNullOrEmpty(text)) {
            return false;
        }
        for (int iCount = 0; iCount < text.length(); iCount++) {
            if (!Character.isLetterOrDigit(text.charAt(iCount))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether a given string contains only alphabets, spaces and numbers.
     * @param text A {@code String}.
     * @return {@code true} if given string contains only alphabets, spaces and numbers;
     *               otherwise returns {@code false}.
     */
    public static boolean isAlphaNumericOrSpace(String text) {
        if (isNull(text)) {
            return false;
        }
        for (int iCount = 0; iCount < text.length(); iCount++) {
            if (!Character.isLetterOrDigit(text.charAt(iCount)) && !Character.
                    isSpaceChar(text.charAt(iCount))) {
                return false;
            }
        }
        return true;
    }

    /**
     * A given {@code String} is parsed by a given separator to split it into parts.
     * Then it forms an array of parts and returns it.
     * @param in A {@code String}.
     * @param separator The separator.
     * @return An array of parts.
     */
    public static String[] getArrayFromString(String in, String separator) {
        if (isNullOrEmpty(in)) {
            return new String[0];
        }
        return in.split(separator);
    }

    /**
     * Creates a new instance of {@code StringUtil}.
     */
    private StringUtil() {
    }
}
