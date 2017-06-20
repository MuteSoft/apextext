/*
 * DiskClassLoader.java
 * Created on 1 Nov, 2007, 4:42:54 PM
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
package org.apex.base.component;

import org.apex.base.logging.Logger;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apex.base.util.FileUtil;

/**
 * A disk Java class loader. It loads a class file from file system as bytes.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class DiskClassLoader extends ClassLoader {

    /**
     * Constructs a new instance of {@code DiskClassLoader}.
     */
    public DiskClassLoader() {
    }

    /**
     * Finds the class with the specified name.
     * @param fullName The binary name of class.
     * @return The resulting {@code Class} object.
     */
    @Override
    public Class findClass(String fullName) {
        String dir = getDirectory(fullName);
        String name = getName(fullName);
        byte[] b = loadClassData(dir, name);
        return defineClass(name, b, 0, b.length);
    }

    /**
     * Determines the directory of class from binary name of class.
     * @param fullName The binary name of class.
     * @return The directory of class.
     */
    private String getDirectory(String fullName) {
        return fullName.substring(0, fullName.indexOf('.'));
    }

    /**
     * Determines the name of class from binary name of class.
     * @param fullName The binary name of class.
     * @return The name of class.
     */
    private String getName(String fullName) {
        return fullName.substring(fullName.indexOf('.') + 1);
    }

    /**
     * Loads a specified {@code Class} as bytes from specified directory.
     * @param dir The directory of class.
     * @param name The name of class.
     * @return The {@code Class} data as bytes.
     */
    private byte[] loadClassData(String dir, String name) {
        name = name.replace('.', '/').concat(".class");
        FileInputStream stream = null;
        StringBuilder data = new StringBuilder();
        byte[] temp = new byte[1000];
        try {
            stream = new FileInputStream(new File(dir, name));
            BufferedInputStream buffer = new BufferedInputStream(stream);
            int length = 0;
            while ((length = buffer.read(temp)) != -1) {
                byte[] readData = new byte[length];
                System.arraycopy(temp, 0, readData, 0, length);
                data.append(readData);
            }
        } catch (IOException ex) {
            Logger.logError(
                    "Could not load class " + dir + File.separator + name +
                    " from disk.", ex);
        } finally {
            FileUtil.closeIOStream(stream);
        }
        return data.toString().getBytes();
    }
}