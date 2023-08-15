/*
 * TextEditorProperties.java 
 * Created on 15 Aug, 2008, 11:26:16 AM
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
package org.apex.text.data;

import java.awt.Image;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorProperties;
import org.apex.base.util.ImageCreator;
import org.apex.text.constant.TextEditorKeyConstants;
import org.apex.text.core.TextEditorBase;

/**
 * Description goes here.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.0
 */
public class TextEditorProperties extends EditorProperties {

    /**
     * Default constructor.
     */
    public TextEditorProperties() {
    }

    /**
     * Returns title of editor.
     * @return Title of editor.
     */
    @Override
    public String getEditorTitle() {
        return TextEditorKeyConstants.EDITOR_TITLE;
    }

    /**
     * Returns the version of editor.
     * @return The editor version.
     */
    @Override
    public String getEditorVersion() {
        return TextEditorKeyConstants.EDITOR_VERSION;
    }

    /**
     * Returns the icon image of editor.
     * @return The icon image of editor.
     */
    @Override
    public Image getEditorIcon() {
        Image icon = ImageCreator.createImageIcon(
                org.apex.base.core.EditorBase.class,
                EditorKeyConstants.EDITOR_ICON, "Apex").getImage();
        return icon;
    }

    @Override
    public Class<? extends EditorBase> getEditorBaseClass() {
        return TextEditorBase.class;
    }

    @Override
    public String getEditorSupportUrl() {
        return TextEditorKeyConstants.ONLINE_SUPPORT_URL;
    }

    @Override
    public String getEditorSourceRepoUrl() {
        return TextEditorKeyConstants.SOURCE_CODE_REPO_URL;
    }
}
