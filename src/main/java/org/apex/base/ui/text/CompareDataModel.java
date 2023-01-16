/*
 * CompareDataModel.java
 * Created on 30 June, 2007, 2:37 PM
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
package org.apex.base.ui.text;

import org.apex.base.data.UIDataModel;

/**
 * A data model used while comparing two documents.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class CompareDataModel implements UIDataModel {

    /**
     * The first comparable file name.
     */
    private String firstFile;
    /**
     * The second comparable file name.
     */
    private String secondFile;
    
    /**
     * Creates a new instance of {@code CompareDataModel}.     
     */
    public CompareDataModel(){
    }

    /**
     * Returns the first comparable file name.
     * @return The first comparable file name.
     * @see #setFirstFile(java.lang.String)      
     */
    public String getFirstFile() {
        return firstFile;
    }

    /**
     * Sets the first comparable file name.
     * @param firstFile A file name.
     * @see #getFirstFile() 
     */
    public void setFirstFile(String firstFile) {
        this.firstFile = firstFile;
    }

    /**
     * Returns the second comparable file name.
     * @return  The second comparable file name.
     * @see #setSecondFile(java.lang.String) 
     */
    public String getSecondFile() {
        return secondFile;
    }

    /**
     * Sets the second comparable file name.
     * @param secondFile A file name.
     * @see #getSecondFile() 
     */
    public void setSecondFile(String secondFile) {
        this.secondFile = secondFile;
    }
}
