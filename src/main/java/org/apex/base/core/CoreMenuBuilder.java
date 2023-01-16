/* 
 * CoreMenuBuilder.java
 * Created on Dec 17, 2007,2:54:43 AM
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

import org.apex.base.data.ActionTarget;
import org.apex.base.data.MenuNode;
import org.apex.base.logging.Logger;
import org.apex.base.data.Configuration;
import org.apex.base.util.ConfigurationUtility;
import org.apex.base.util.StringUtil;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import javax.swing.ButtonGroup;
import org.apex.base.data.ConfigurationBuilderSupport;
import org.apex.base.data.MenuType;

/**
 * Builds core menu configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class CoreMenuBuilder extends ConfigurationBuilderSupport {

    /**
     * Stores ids of editor provided tools.
     */
    private Vector<String> providedToolIds = new Vector<String>();

    /**
     * Creates a new instance of {@code CoreMenuBuilder}.
     */
    public CoreMenuBuilder() {
        super();
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading core menu configuration.");
        CoreMenuConfiguration coreMenuConfig = new CoreMenuConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(coreMenuConfig);
        }
        // First read various menu groups and then apply group information to each
        // and every menus.
        coreMenuConfig.setMenuGroups(getMenuGroups(properties));
        coreMenuConfig.setMenus(getMenus(properties));
        coreMenuConfig.setToolBarMenus(getOtherMenus(properties, "toolbar"));
        coreMenuConfig.setDocEditAreaMenus(getOtherMenus(properties,
                "documenteditarea-menubar"));
        coreMenuConfig.setDocTabMenus(getOtherMenus(properties,
                "documenttab-menubar"));
        coreMenuConfig.setProvidedToolIds(providedToolIds);
        return coreMenuConfig;
    }

    /**
     * Builds and returns the core menu tree of editor.
     * @param properties A table of key value pairs.
     * @return The root menu node.
     */
    private MenuNode getMenus(Properties properties) {
        MenuNode parent = new MenuNode("Menus", "menus", 'x');
        addMenusRecursively("menubar", properties, parent);
        return parent;
    }

    /**
     * Forms and returns a table containing various menu groups.
     * @param properties A table of key value pairs.
     * @return A table of menu groups.
     */
    private HashMap<String, ButtonGroup> getMenuGroups(Properties properties) {
        HashMap<String, ButtonGroup> menuGroups =
                new HashMap<String, ButtonGroup>();
        String groups = properties.getProperty("groups");
        ButtonGroup bGroup = null;
        for (String group : ConfigurationUtility.getListFromString(groups,
                "-")) {
            bGroup = new ButtonGroup();
            for (String menu : ConfigurationUtility.getListFromString(group,
                    ",")) {
                menuGroups.put(menu, bGroup);
            }
        }
        return menuGroups;
    }

    /**
     * Returns a list of menus specified by parent menu Id.
     * @param properties A table of key value pairs.
     * @param parent The menu Id parent node.
     * @return A list of menus.
     */
    private Vector<String> getOtherMenus(Properties properties, String parent) {
        Vector<String> toolBarMenus = new Vector<String>(10);
        String menus = properties.getProperty(parent);
        for (String menuid : ConfigurationUtility.getListFromString(menus,
                ",")) {
            if (StringUtil.isNullOrEmpty(menuid)) {
                continue;
            } else if (menuid.equals("|")) {
                toolBarMenus.add(MenuNode.SEPARATOR_ID);
            } else {
                toolBarMenus.add(menuid);
            }
        }
        return toolBarMenus;
    }

    /**
     * Adds menus recursively starting from given parent menu.
     * @param parentMenu Id of parent menu.
     * @param properties A table of key value pairs.
     * @param parentNode The parent menu node.
     * @return {@code true} if parse is successful; otherwise returns {@code false}.
     */
    private boolean addMenusRecursively(String parentMenu,
            Properties properties,
            MenuNode parentNode) {
        String menus = properties.getProperty(parentMenu);
        for (String menuid : ConfigurationUtility.getListFromString(menus,
                ",")) {
            if (StringUtil.isNullOrEmpty(menuid)) {
                continue;
            }
            MenuNode childNode =
                    addNode(menuid, properties, parentNode);
            if (childNode == null) {
                continue;
            }
            if (hasChildren(menuid, properties)) {
                if (addMenusRecursively(menuid, properties, childNode)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Builds and returns a menu node for given menu Id.
     * @param menuid A menu Id.
     * @param properties A table of key value pairs.
     * @return The menu node. It returns {@code null} if it fails to build the menu node.
     */
    private MenuNode getMenuNode(String menuid, Properties properties) {
        if (menuid == null || menuid.equals("")) {
            return null;
        }
        if (menuid.equals("|")) {
            return MenuNode.createSeparator();
        }
        String menuDef = properties.getProperty(menuid + "-def");
        Vector<String> menuProperties =
                ConfigurationUtility.getListFromString(menuDef, ",");
        String name = "";
        char type = ' ';
        try {
            name = menuProperties.get(0);
            type = menuProperties.get(1).charAt(0);
        } catch (ArrayIndexOutOfBoundsException aobx) {
            // Name and type are mandatory
            Logger.logError("Could not find basic definition of menu id: " +
                    menuid, aobx);
            return null;
        }
        MenuNode node = new MenuNode(name, menuid, type);
        for (int iCount = 2; iCount < menuProperties.size(); iCount++) {
            if (StringUtil.isNullOrEmpty(menuProperties.get(iCount))) {
                continue;
            }
            try {
                switch (iCount) {
                    case 2:
                        node.setDescription(menuProperties.get(iCount));
                        break;
                    case 3:
                        node.setMnemonic(menuProperties.get(iCount).charAt(0));
                        break;
                    case 4:
                        node.setTargetType(ActionTarget.getMappedTarget(menuProperties.
                                get(iCount).
                                charAt(
                                0)));
                        break;
                    case 5:
                        node.setTarget(menuProperties.get(iCount));
                        break;
                    case 6:
                        node.setAccelerator(menuProperties.get(iCount));
                        break;
                    case 7:
                        node.setImage(menuProperties.get(iCount));
                        break;
                    case 8:
                        node.setPressedImage(menuProperties.get(iCount));
                        break;
                    default:
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException aob) {
                Logger.logWarning("Error while parsing menu node: " +
                        menuid, aob);
            } catch (NullPointerException npe) {
                Logger.logWarning("Error while parsing menu node: " +
                        menuid, npe);
            } catch (Exception ex) {
                Logger.logWarning("Error while parsing menu node: " +
                        menuid, ex);
            }
        }
        return node;
    }

    /**
     * Adds a menu node determined from given menu Id to a specified
     * parent menu node. 
     * @param menuid A menu Id.
     * @param properties A table of key value pairs.
     * @param parentNode The parent menu node.
     * @return The menu which is added. If it fails to add the menu node to specified parent
     *               it returns {@code null}. 
     */
    private MenuNode addNode(
            String menuid, Properties properties, MenuNode parentNode) {
        MenuNode child = getMenuNode(menuid, properties);
        if (child == null) {
            return null;
        }
        parentNode.add(child);
        if (child.getType() == MenuType.PROVIDED_TOOL) {
            this.providedToolIds.add(child.getId());
        }
        return child;
    }

    /**
     * Determines whether or not a menu node specified by a menu Id has child nodes.
     * @param menuid A menu Id.
     * @param properties A table of key value pairs.
     * @return {@code true} if menu node specified by a menu Id has child nodes; otherwise
     *               returns {@code false}.
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
