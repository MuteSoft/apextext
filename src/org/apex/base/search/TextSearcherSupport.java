/* 
 * TextSearcherSupport.java
 * Created on Nov 27, 2007,8:56:19 PM
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
package org.apex.base.search;

import org.apex.base.component.TextEditor;
import javax.swing.text.Highlighter;

/**
 * A base class for defining new text searchers implementing {@code TextSearcher}.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class TextSearcherSupport implements TextSearcher {

    /**
     * The text editor.
     */
    private TextEditor editArea;
    /**
     * The highlight painter.
     */
    private Highlighter.HighlightPainter painter;

    /**
     * Creates a new instance of {@code TextSearcherSupport} using specified
     * highlight painter.
     * @param painter A highlight painter.
     */
    public TextSearcherSupport(Highlighter.HighlightPainter painter) {
        this.painter = painter;
    }

    public TextEditor getEditArea() {
        return editArea;
    }

    public Highlighter.HighlightPainter getPainter() {
        return painter;
    }

    public void setPainter(Highlighter.HighlightPainter painter) {
        this.painter = painter;
    }

    public void setEditArea(TextEditor editArea) {
        this.editArea = editArea;
    }
}
