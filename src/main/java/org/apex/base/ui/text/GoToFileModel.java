/* 
 * GoToFileModel.java
 * Created on 18 Nov, 2007,12:48:34 AM 
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
 * A data model for navigation between files.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GoToFileModel implements UIDataModel {

    /**
     * The selected file name.
     */
    private String selectedFileName;
    /**
     * The selected file absolute path.
     */
    private String selectedFilePath;
    
    /**
     * Creates a new instance of {@code GoToFileModel}.
     */
    public GoToFileModel(){
    }
    /**
     * Returns the name of selected file to be displayed.
     * @return Name of selected file.
     * @see #setSelectedFileName(java.lang.String) 
     */
    public String getSelectedFileName() {
        return selectedFileName;
    }

    /**
     * Sets the name of selected file to be displayed.
     * @param selectedFileName Name of selected file.
     * @see #getSelectedFileName() 
     */
    public void setSelectedFileName(String selectedFileName) {
        this.selectedFileName = selectedFileName;
    }

    /**
     * Returns the path of selected file to be displayed.
     * @return Path of selected file.
     * @see #setSelectedFilePath(java.lang.String) 
     */
    public String getSelectedFilePath() {
        return selectedFilePath;
    }

    /**
     * Sets the path of selected file to be displayed.
     * @param selectedFilePath Path of selected file.
     * @see #getSelectedFilePath() 
     */
    public void setSelectedFilePath(String selectedFilePath) {
        this.selectedFilePath = selectedFilePath;
    }
}
