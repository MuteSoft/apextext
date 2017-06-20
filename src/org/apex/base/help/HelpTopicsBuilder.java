/* 
 * HelpTopicsBuilder.java
 * Created on 17 Jan, 2008,9:02:44 PM
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

import org.apex.base.common.ConfigurationProperties;
import org.apex.base.logging.Logger;
import org.apex.base.util.ConfigurationUtility;
import org.apex.base.util.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;
import org.apex.base.util.FileUtil;

/**
 * Builds a help topic from configuration file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class HelpTopicsBuilder {

    /**
     * The help pages configuration file.
     */
    private static String HELP_SET_FILE = "HelpSet.properties";

    /**
     * Creates a new instance of {@code HelpTopicsBuilder}.
     */
    public HelpTopicsBuilder() {
    }

    /**
     * It reads the help pages configuration file and based on this data
     * builds the tree of help topics.
     * @return The root help topic.
     */
    public HelpTopic build() {
        InputStream helpSetDataStream = null;
        try {
            Logger.logInfo("Loading file containing help-set definition.",
                    getClass().getName(), "build");
            helpSetDataStream =
                    HelpTopicsBuilder.class.getResourceAsStream("resources/" +
                    HELP_SET_FILE);
            if (helpSetDataStream == null) {
                return null;
            }
            Properties properties = new ConfigurationProperties();
            properties.load(helpSetDataStream);
            return getHelpTopics(properties);
        } catch (IOException ex) {
            Logger.logError(
                    "Failed to load file containing help-set definition.", ex);
            return null;
        } finally {
            FileUtil.closeIOStream(helpSetDataStream);
        }
    }

    /**
     * For a given help topic Id and definition creates a new help topic node and
     * adds the same to given parent help topic.
     * @param menuid The help topic Id.
     * @param properties The properties list contains the help pages configuration.
     * @param parentTopic The parent help topic.
     * @return A help topic.
     */
    private HelpTopic addNode(String menuid, Properties properties,
                              HelpTopic parentTopic) {
        HelpTopic child = getMenuNode(menuid, properties);
        if (child == null) {
            return null;
        }
        parentTopic.add(child);
        return child;
    }

    /**
     * Recursively adds help topics.
     * @param parentTopicId Parent help topic Id.
     * @param properties The properties list contains the help pages configuration.
     * @param parentTopic Parent help topic.
     * @return {@code true} if the build process is successful.
     */
    private boolean addTopicsRecursively(String parentTopicId,
                                         Properties properties,
                                         HelpTopic parentTopic) {
        String menus = properties.getProperty(parentTopicId);
        for (String menuid : ConfigurationUtility.getListFromString(menus,
                ",")) {
            if (StringUtil.isNullOrEmpty(menuid)) {
                continue;
            }
            HelpTopic childNode =
                    addNode(menuid, properties, parentTopic);
            if (childNode == null) {
                continue;
            }
            if (hasChildren(menuid, properties)) {
                if (addTopicsRecursively(menuid, properties, childNode)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Builds the entire tree of help topics.
     * @param properties The properties list contains the help pages configuration.
     * @return The root help topic.
     */
    private HelpTopic getHelpTopics(Properties properties) {
        HelpTopic parent = new HelpTopic("help", "Help");
        addTopicsRecursively("helpmenu", properties, parent);
        return parent;
    }

    /**
     * For a given help topic Id and definition creates a new help topic node.
     * @param menuid The help topic Id.
     * @param properties The properties list contains the help pages configuration.
     * @return A help topic.
     */
    private HelpTopic getMenuNode(String menuid, Properties properties) {
        if (menuid == null || menuid.equals("")) {
            return null;
        }
        String menuDef = properties.getProperty(menuid + "-def");
        Vector<String> menuProperties =
                ConfigurationUtility.getListFromString(menuDef, ",");
        String name = "";
        try {
            name = menuProperties.get(0);
        } catch (ArrayIndexOutOfBoundsException aobx) {
            Logger.logError("Could not find basic definition of help-topic: " +
                    menuid, aobx);
            // Name is mandatory
            return null;
        }
        HelpTopic node = new HelpTopic(menuid, name);
        for (int iCount = 1; iCount < menuProperties.size(); iCount++) {
            if (StringUtil.isNullOrEmpty(menuProperties.get(iCount))) {
                continue;
            }
            try {
                switch (iCount) {
                    case 1:
                        node.setTargetPage(menuProperties.get(1).trim());
                        break;
                    case 2:
                        node.setMetaKeys(menuProperties.get(2).split("#"));
                        break;
                    default:
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException aob) {
                Logger.logWarning("Error while parsing help-topic node: " +
                        menuid, aob);
            } catch (NullPointerException npe) {
                Logger.logWarning("Error while parsing help-topic node: " +
                        menuid, npe);
            } catch (Exception ex) {
                Logger.logWarning("Error while parsing help-topic node: " +
                        menuid, ex);
            }
        }
        return node;
    }

    /**
     * Returns a boolean indicating whether or not given help topic (specified by Id) contains
     * children topics.
     * @param menuid The help topic Id.
     * @param properties The properties list contains the help pages configuration.
     * @return {@code true} if children exists; otherwise returns {@code false}.
     */
    private boolean hasChildren(String menuid, Properties properties) {
        String childMenus = properties.getProperty(menuid);
        if (StringUtil.isNullOrEmpty(childMenus)) {
            return false;
        } else {
            return true;
        }
    }
}
