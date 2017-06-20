/*
 * ConfigurationUtility.java
 * Created on 4 August, 2007, 4:28 PM
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

import java.awt.Color;
import java.util.Arrays;
import java.util.Vector;

/**
 * An utility class to deal with user preferences data.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class ConfigurationUtility {

    /**
     * Creates a new instance of {@code ConfigurationUtility}.
     */
    private ConfigurationUtility() {
    }

    /**
     * Takes color as string in 'r-g-b' format and
     * converts it to a color object.
     * @param color String representation of color.
     * @return The color object. It returns {@code null} if given String is null or parse fails.
     */
    public static Color getColor(String color) {
        Color result = null;
        if (color == null) {
            return null;
        }
        try {
            String[] rgb = color.trim().split("-");

            int r = Integer.parseInt(rgb[0].trim());
            int g = Integer.parseInt(rgb[1].trim());
            int b = Integer.parseInt(rgb[2].trim());

            result = new Color(r, g, b);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * Converts color object to String in 'r-g-b' format.
     * @param color The color object.
     * @return String representation of color.
     */
    public static String convertToString(Color color) {
        String value = null;
        if (color == null) {
            return null;
        }
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        value = r + "-" + g + "-" + b;
        return value;
    }

    /**
     * Removes start and end brackets ('[' and ']') from a String.
     * This is useful for String representation of Vector : [value1,value2,value3].
     * @param input Text to analyze.
     * @return Text after trimming brackets.
     */
    public static String removeBraces(String input) {
        String value = null;
        if (input == null) {
            return null;
        }
        if (input.indexOf("[") != -1 && input.indexOf("]") != -1) {
            // Assuming braces will appear at first position and last position.
            if (input.length() > 2) {
                value = input.substring(1, input.length() - 1);
            }
        } else {
            value = input;
        }
        return value;
    }

    /**
     * Accepts Color as String in [r=12,g=34,b=60] format
     * and returns a color object.
     * @param text The display color text.
     * @return A {@code Color} object. It returns null if given String is null or parse fails.
     */
    public static Color getColor1(String text) {
        Color value = null;
        if (text == null || text.equals("")) {
            return null;
        }
        if (text.length() < 2) {
            return null;
        }
        // Remove [] brackets
        text = text.substring(1, text.length() - 1);
        if (text == null || text.equals("")) {
            return null;
        }
        try {
            String[] rgb = text.trim().split(",");
            int r = Integer.parseInt(rgb[0].substring(rgb[0].indexOf("=") + 1));
            int g = Integer.parseInt(rgb[1].substring(rgb[1].indexOf("=") + 1));
            int b = Integer.parseInt(rgb[2].substring(rgb[2].indexOf("=") + 1));
            value = new Color(r, g, b);
        } catch (ArrayIndexOutOfBoundsException abe) {
        } catch (NumberFormatException nfe) {
        }

        return value;
    }

    /**
     * Prepares a list from a given text with the help
     * of a specified separator.
     * @param in The text to split.
     * @param separator A separator.
     * @return List of tokens obtained from given {@code String}.
     */
    public static Vector<String> getListFromString(String in,
            String separator) {
        Vector<String> result = new Vector<String>(20);
        result.addAll(Arrays.asList(StringUtil.getArrayFromString(in, separator)));
        return result;
    }

    /**
     * Prepares a upper case token list from a given text with the help
     * of a specified separator.
     * @param in The text to split.
     * @param separator A separator.
     * @return List of tokens obtained from given {@code String}.
     */
    public static Vector<String> getUpperCaseTokenListFromString(String in,
            String separator) {
        return getSpecifiedCaseTokenListFromString(in, separator, true);
    }

    /**
     * Prepares a specified (upper or lower) case token list from a given text with the help
     * of a specified separator.
     * @param in The text to split.
     * @param separator A separator.
     * @param upperCase A boolean that indicates whether tokens should be in upper case
     *                or lower case.
     * @return List of tokens obtained from given {@code String}.
     */
    private static Vector<String> getSpecifiedCaseTokenListFromString(String in,
            String separator,
            boolean upperCase) {
        Vector<String> result = new Vector<String>();
        String[] values = StringUtil.getArrayFromString(in, separator);
        if (upperCase) {
            for (String value : values) {
                result.add(value.toUpperCase());
            }
        } else {
            for (String value : values) {
                result.add(value.toLowerCase());
            }
        }
        return result;
    }

    /**
     * Prepares a lower case token list from a given text with the help
     * of a specified separator.
     * @param in The text to split.
     * @param separator A separator.
     * @return List of tokens obtained from given {@code String}.
     */
    public static Vector<String> getLowerCaseTokenListFromString(String in,
            String separator) {
        return getSpecifiedCaseTokenListFromString(in, separator, false);
    }
}
