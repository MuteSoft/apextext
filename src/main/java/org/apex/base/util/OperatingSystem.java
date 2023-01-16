/*
 * OperatingSystem.java 
 * Created on 5 Aug, 2010, 12:57:35 AM
 *
 * Copyright (C) 2010 Mrityunjoy Saha
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

import java.io.File;

/**
 * An abstraction for the host operating system.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public abstract class OperatingSystem {

    /**
     * Default operating system. This is the operating system used when host
     * operating system could not be detected.
     */
    private static OperatingSystem defaultOS;
    /**
     * Windows operating system.
     */
    private static OperatingSystem windowsOS;
    /**
     * Unix and unix based operating systems.
     */
    private static OperatingSystem unixOS;
    /**
     * Mac operating system.
     */
    private static OperatingSystem macOS;

    /**
     * Creates a new instance of {@code OperatingSystem}.
     */
    protected OperatingSystem() {
    }

    /**
     * Returns line separator {@code String} for the operating system.
     * @return Line separator {@code String}.
     */
    public abstract String getLineSeparator();

    /**
     * Returns the file separator for the operating system.
     * @return The file separator.
     */
    public String getFileSeparator() {
        return File.separator;
    }

    /**
     * Returns whether or not file path is case sensitive in the host operating system. For example,
     * in UNIX system file path is case sensitive where in WINDOWS file name and paths are case insensitive.
     * @return {@code true} if file path is case sensitive; otherwise returns {@code false}.
     */
    public abstract boolean isFilePathCaseSensitive();

    /**
     * Detects and returns the host operating system.
     * @return The host operating system.
     */
    public static OperatingSystem getOperatingSystem() {
        String osName = System.getProperty("os.name");
        if (osName != null) {
            if (osName.indexOf("Windows") != -1) {
                if (windowsOS == null) {
                    windowsOS = new WindowsOperatingSystem();
                }
                return windowsOS;
            } else {
                if (unixOS == null) {
                    unixOS = new UnixOperatingSystem();
                }
                return unixOS;
            }
        }
        if (defaultOS == null) {
            defaultOS = new DefaultOperatingSystem();
        }
        return defaultOS;
    }
}
