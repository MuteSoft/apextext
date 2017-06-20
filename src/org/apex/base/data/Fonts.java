/*
 * Fonts.java
 * Created on December 28, 2006, 10:20 PM 
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
package org.apex.base.data;

import java.awt.Font;
import org.apex.base.util.StringUtil;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.Vector;

/**
 * A class from where supported font family names, font styles and
 * font sizes can be accessed.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class Fonts {

    /**
     * List of font familiy names.
     */
    private static Vector<String> fontNames;
    /**
     * List of font styles.
     */
    private static Vector<String> fontStyles;
    /**
     * List of font sizes.
     */
    private static Vector<String> fontSizes;
    /**
     * Display text for regular font style.
     */
    public static final String REGULAR = "Regular";
    /**
     * Display text for bold font style.
     */
    public static final String BOLD = "Bold";
    /**
     * Display text for italic font style.
     */
    public static final String ITALIC = "Italic";
    /**
     * Display text for bold and italic font style.
     * This is combination of bold style and italic style.
     */
    public static final String BOLD_ITALIC = "Bold Italic";

    /**
     * Creates a new instance of {@code Fonts}.
     */
    private Fonts() {
    }

    /**
     * Returns a list of supported font families.
     * @return A list of supported font families.
     */
    public static Vector getFontNames() {
        if (fontNames == null) {
            fontNames = new Vector<String>();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            fontNames.addAll(Arrays.asList(ge.getAvailableFontFamilyNames()));
        }
        return fontNames;
    }

    /**
     * Returns a list of supported font styles.
     * @return A list of supported font styles.
     */
    public static Vector getFontStyles() {
        if (fontStyles == null) {
            fontStyles = new Vector<String>();
            fontStyles.add(REGULAR);
            fontStyles.add(BOLD);
            fontStyles.add(ITALIC);
            fontStyles.add(BOLD_ITALIC);
        }
        return fontStyles;
    }

    /**
     * Returns a boolean that indicates whether or not given font style
     * is valid.
     * @param fontStyle The font style.
     * @return {@code true} if given font style is valid; otherwise returns {@code false}.
     */
    public static boolean isValidFontStyle(String fontStyle) {
        if (StringUtil.isNullOrEmpty(fontStyle)) {
            return false;
        }
        return fontStyles.contains(fontStyle);
    }

    /**
     * Returns a list of supported font sizes.
     * @return A list of supported font sizes.
     */
    public static Vector getFontSizes() {
        if (fontSizes == null) {
            fontSizes = new Vector<String>();
            fontSizes.add("10");
            fontSizes.add("12");
            fontSizes.add("14");
            fontSizes.add("16");
            fontSizes.add("18");
            fontSizes.add("20");
            fontSizes.add("22");
            fontSizes.add("24");
            fontSizes.add("26");
            fontSizes.add("28");
            fontSizes.add("30");
            fontSizes.add("40");
            fontSizes.add("50");
            fontSizes.add("60");
        }
        return fontSizes;
    }

    /**
     * Determines the {@code int} value of given font style.
     * @param fontStyle A font style name.
     * @return The {@code int} value of gien font style. If font style
     *              is unknown it returns {@code Font.PLAIN}.
     */
    public static int getIntFontStyle(String fontStyle) {
        int result = 0;
        if (StringUtil.isNullOrEmpty(fontStyle)) {
            result = Font.PLAIN;
        } else if (fontStyle.trim().equalsIgnoreCase(Fonts.REGULAR)) {
            result = Font.PLAIN;
        } else if (fontStyle.trim().equalsIgnoreCase(Fonts.BOLD)) {
            result = Font.BOLD;
        } else if (fontStyle.trim().
                equalsIgnoreCase(Fonts.ITALIC)) {
            result = Font.ITALIC;
        } else if (fontStyle.trim().
                equalsIgnoreCase(Fonts.BOLD_ITALIC)) {
            result = Font.BOLD | Font.ITALIC;
        } else {
            result = Font.PLAIN;
        }
        return result;
    }
}
