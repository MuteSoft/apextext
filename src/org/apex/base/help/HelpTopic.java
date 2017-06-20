/* 
 * HelpTopic.java
 * Created on 17 Jan, 2008,8:42:13 PM
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
package org.apex.base.help;

import java.net.URL;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A help topic renedered in a HTML page. A particular help topic describes an unique
 * functionality of application.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class HelpTopic extends DefaultMutableTreeNode {

    /**
     * Target help page.
     */
    private String targetPage;
    /**
     * Search keys for this help topic.
     */
    private String[] metaKeys;
    /**
     * Help topic Id.
     */
    private String id;

    /**
     * Creates a new instance of {@code HelpTopic} using specified Id, name,
     * a target help page and search keys.
     * @param id The help topic Id.
     * @param name The name of help topic.
     * @param targetPage The target help page for this topic.
     * @param metaKeys Search keys for this help topic.
     */
    public HelpTopic(String id, String name, String targetPage,
            String[] metaKeys) {
        super(name);
        this.id = id;
        this.targetPage = targetPage;
        this.metaKeys = metaKeys;
    }

    /**
     * Creates a new instance of {@code HelpTopic} using specified Id, name and
     * a target help page.
     * @param id The help topic Id.
     * @param name The name of help topic.
     * @param targetPage The target help page for this topic.
     */
    public HelpTopic(String id, String name, String targetPage) {
        this(id, name, targetPage, null);
    }

    /**
     * Creates a new instance of {@code HelpTopic} using specified Id and name.
     * @param id The help topic Id.
     * @param name The name of help topic.
     */
    public HelpTopic(String id, String name) {
        this(id, name, null);
    }

    /**
     * Returns the target help page.
     * @return A target help page name.
     * @see #setTargetPage(java.lang.String) 
     */
    public String getTargetPage() {
        return targetPage;
    }

    /**
     * Sets the target help page.
     * @param targetPage A target help page name.
     * @see #getTargetPage() 
     */
    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
    }

    /**
     * Returns search keys for this help page.
     * @return An array of search keys.
     * @see #setMetaKeys(java.lang.String[]) 
     */
    public String[] getMetaKeys() {
        return metaKeys;
    }

    /**
     * Sets search keys for this help page.
     * @param metaKeys An array of search keys.
     * @see #getMetaKeys() 
     */
    public void setMetaKeys(String[] metaKeys) {
        this.metaKeys = metaKeys;
    }

    /**
     * Returns the URL of the target page.
     * @return The URL of a help page.
     */
    public URL getTargetPageURL() {
        return getURL("resources/" + getTargetPage());
    }

    /**
     * Returns the URL for a specified page.
     * @param page The help page to search.
     * @return The URL of a help page.
     */
    private URL getURL(String page) {
        URL url = HelpTopic.class.getResource(page);
        return url;
    }

    /**
     * Returns the URL of blank help page.
     * @return The URL of blank help page.
     */
    public static URL getBlankPageURL() {
        URL url = HelpTopic.class.getResource("resources/Blank.html");
        return url;
    }

    /**
     * Returns human readable text representation of this object.
     * @return The text representation of this object.
     */
    public String getString() {
        return "id: " + id + "^targetPage: " + targetPage + "^metaKeys: " +
                metaKeys + "^" + super.toString();
    }

    /**
     * Returns human readable text representation of this object.
     * @return The text representation of this object.
     */
    @Override
    public String toString() {
        return getUserObject().toString();
    }
}
