/*
 * Configuration.java
 * Created on 8 July, 2007, 11:17 PM
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

/**
 * The interface for application configuration objects.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public interface Configuration extends Cloneable {

    /**
     * Updates this configuration item from a cloned one.
     * @param clonedObject The cloned configuration.
     */
    void updateFromClone(Object clonedObject);

    /**
     * Returns the boolean that indicates whether this configuration item is configurable by user.
     * @return <code>true</code> if configurable otherwise <code>false</code>.
     */
    boolean isConfigurable();

    /**
     * Returns the name of configuration file (without extension) from where
     * configuration data to be retrieved.
     * @return The configuration file name.
     */
    String getConfigFile();

    /**
     * Returns the boolean that indicates whether this item need to be cached throughout
     * the session.
     * @return <code>true</code> if cache required; otherwise <code>false</code>.
     */
    boolean isCacheRequired();

    /**
     * Verify whether or not cache is enabled for this configuration object. In case cache is
     * not required nullify all contained configuration items including this item.
     * @return {@code true} if dispose is successful; otherwise {@code false}.
     */
    boolean disposeIfCacheNotRequired();

    /**
     * Indicates whether or not this configuration object is a leaf node in the tree.
     * @return {@code true} if leaf node otherwise returns {@code false}.
     */
    boolean isLeaf();

    /**
     * Cleans up the configuration object under any circumstance.
     */
    void remove();
}
