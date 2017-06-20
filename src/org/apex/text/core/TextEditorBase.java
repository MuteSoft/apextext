/*
 * TextEditorBase.java 
 * Created on 12 Aug, 2008, 11:50:02 PM
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
package org.apex.text.core;

import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorComponents;
import org.apex.base.data.EditorProperties;
import org.apex.text.data.TextEditorProperties;

/**
 * Description goes here.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.0
 */
public class TextEditorBase extends EditorBase {

    /**
     * Default constructor.
     */
    public TextEditorBase() {        
    }

    /**
     * The main method from where the editor is launched.
     * @param args Command line arguments passed.
     */
    public static void main(String[] args) {
        TextEditorBase base = new TextEditorBase();
        base.startEditor(args);
    }

    @Override
    protected EditorProperties getEditorProperties() {
        return new TextEditorProperties();
    }

    @Override
    protected EditorComponents getEditorComponents() {
        return new EditorComponents();
    }
}
