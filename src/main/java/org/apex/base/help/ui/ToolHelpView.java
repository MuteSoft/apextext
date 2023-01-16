/*
 * ToolHelpView.java
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
package org.apex.base.help.ui;

import org.apex.base.component.SplittedComponent;
import org.apex.base.help.HelpTopic;
import org.apex.base.help.HelpTopicsBuilder;
import org.apex.base.component.ApexFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import org.apex.base.core.EditorBase;

/**
 * The editor help window.
 * @author mrityunjoy_saha
 */
public class ToolHelpView extends ApexFrame {

    /**
     * Creates a new instance of {@code ToolHelpView}.
     */
    public ToolHelpView() {
        initComponents(loaHelpTopics());

    }

    /**
     * Builds all help topics from internal (to code) configuration file.
     * @return The root help topic.
     */
    private HelpTopic loaHelpTopics() {
        HelpTopicsBuilder helpTopicsBuilder = new HelpTopicsBuilder();
        return helpTopicsBuilder.build();
    }

    /**
     * Initializes the editor help window.
     * @param helpTopic A help topic to be displayed by default.
     */
    private void initComponents(HelpTopic helpTopic) {
        this.setLayout(new BorderLayout());


        //this.getContentPane().add(new HelpHeaderPage(), BorderLayout.NORTH);
        HelpDataPage rightComponent = new HelpDataPage();
        HelpMenuPage leftComponent = new HelpMenuPage(helpTopic, rightComponent.getDataPane());
        leftComponent.setMinimumSize(new Dimension(200, 0));
        SplittedComponent splittedBody = new SplittedComponent(leftComponent,
                rightComponent,
                0);

        this.getContentPane().add(splittedBody, BorderLayout.CENTER);

        this.setTitle(getWindowTitle());
        this.setIconImage(EditorBase.getContext().getEditorProperties().getEditorIcon());
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        this.setMinimumSize(new Dimension(700,
                600));
        this.setLocation(new Point(200, 100));

        this.setVisible(true);
        this.toFront();
        splittedBody.getSplitPane().setDividerLocation(0.35);
    }

    /**
     * Returns the title of help window.
     * @return The title of help window.
     */
    private String getWindowTitle() {
        return EditorBase.getContext().getEditorProperties().getEditorTitle() + " Help";
    }
}
