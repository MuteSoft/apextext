/*
 * TreeDocumentSelector.java 
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
package org.apex.base.core;

import java.util.Vector;

/**
 * The left hand document selector of the editor. It displays a tree view of
 * documents.
 * @author mrityunjoy_saha
 */
public class TreeDocumentSelector extends DocumentSelector {

    /**
     * Constructs a new instance of {@code TreeDocumentSelector}.
     */
    public TreeDocumentSelector() {
        super();
    }

    @Override
    protected void createDocumentSelector() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getSelectedDocumentFullName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getSelectedDocumentDisplayName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector<String> getDocumentFullNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector<String> getDocumentDisplayNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector<String> getSortedDocumentFullNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Vector<String> getSortedDocumentDisplayNames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDocumentListSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addDocument(String name, String path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeDocumentAt(int documentIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateDocumentAt(int documentIndex, String name,
            String path) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void selectDocumentAt(int documentIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeDocuments() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
