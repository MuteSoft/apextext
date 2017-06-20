/* 
 * MenuNode.java
 * Created on Dec 10, 2007,1:48:29 AM 
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
package org.apex.base.data;

import org.apex.base.util.MenuUtil;
import org.apex.base.util.StringUtil;
import java.util.Enumeration;
import javax.swing.ButtonGroup;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A menu item represents an editor menu in hierarchical menu structure.
 * When a menu tree is displayed, a {@code MenuNode} is added to a
 * {@code JTree} as a node.
 * <p>
 *  A tree node may have at most one parent and 0 or more children.
 * {@code MenuNode} provides operations for examining and modifying a
 * node's parent and children and also operations for examining the tree that
 * the node is a part of.  A node's tree is the set of all nodes that can be
 * reached by starting at the node and following all the possible links to
 * parents and children.  A node with no parent is the root of its tree; a
 * node with no children is a leaf.
 * <p>
 * Menus are maintained and displayed in a tree structure. A menu item contains 
 * all of its ancestors, a target (either action or menu), an image and acclerator.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 * @see DefaultMutableTreeNode
 * @see MenuType
 * @see ActionTarget
 */
public class MenuNode extends DefaultMutableTreeNode {

    /**
     * The id of a separator menu.
     */
    public static String SEPARATOR_ID = "separator";
    /**
     * The display text of separator menu.
     */
    public static String SEPARATOR_NAME = "------------";
    /**
     * The name of menu.
     */
    private String name = "";
    /**
     * The keyboard mnemonic (shortcut key) for the menu.
     */
    private char mnemonic = ' ';
    /**
     * The key combination which invokes the menu item's
     * action listeners without navigating the menu hierarchy.
     */
    private KeyStroke accelerator;
    /**
     * The type of this menu item.
     * @see MenuType
     */
    private MenuType type = MenuType.MENUITEM;
    /**
     * The path of image icon.
     */
    private String image;
    /**
     * A short description.
     */
    private String description;
    /**
     * The Id.
     */
    private String id;
    /**
     * The target name.
     */
    private String target;
    /**
     * The target type.
     * @see ActionTarget
     */
    private ActionTarget targetType = ActionTarget.MENU;
    /**
     * The button group where this menu belongs.
     */
    private ButtonGroup group;
    /**
     * The image path of pressed image. This is particularly useful for menus
     * put in the toolbar.
     */
    private String pressedImage;

    /**
     * Creates a new menu node with specified type, id and name.
     * @param name The display name of menu.
     * @param id The menu Id.
     * @param type The menu type.
     * @throws IllegalArgumentException If name and/or id passed is null or blank.
     * @see MenuType
     */
    public MenuNode(String name, String id, char type) {
        this(name, id, MenuType.getMappedType(type));
    }

    /**
     * Creates a new menu node with specified {@code MenuType}, id and name.
     * @param name The display name of menu.
     * @param id The menu Id.
     * @param type The menu type.
     * @throws IllegalArgumentException If name and/or id passed is null or blank.
     * @see MenuType
     */
    public MenuNode(String name, String id, MenuType type) {
        if (StringUtil.isNullOrEmpty(name) || StringUtil.isNullOrEmpty(id)) {
            throw new IllegalArgumentException("Illegal name and/or id.");
        }
        this.name = name;
        this.id = id;
        this.type = type;
    }

    /**
     * Returns the display name of menu.
     * @return The menu name.
     * @see #setName(java.lang.String) 
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the display name of menu.
     * @param name The menu name.
     * @see #getName() 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the keyboard mnemonic (shortcut key) for the menu.
     * @return The keyboard mnemonic (shortcut key) for the menu.
     * @see #setMnemonic(char) 
     */
    public char getMnemonic() {
        return mnemonic;
    }

    /**
     * Sets the keyboard mnemonic (shortcut key) for the menu.
     * @param mnemonic The keyboard mnemonic (shortcut key) for the menu.
     * @see #getMnemonic()
     */
    public void setMnemonic(char mnemonic) {
        this.mnemonic = mnemonic;
    }

    /**
     * Returns the accelerator key of this menu.
     * <p>
     * The key combination which invokes the menu item's
     * action listeners without navigating the menu hierarchy.
     * @return The accelerator.
     * @see #setAccelerator(javax.swing.KeyStroke)
     * @see #setAccelerator(java.lang.String) 
     */
    public KeyStroke getAccelerator() {
        return accelerator;
    }

    /**
     * Returns the accelerator key of this menu as String.
     * <p>
     * The key combination which invokes the menu item's
     * action listeners without navigating the menu hierarchy.
     * @return The accelerator.
     * @see #setAccelerator(javax.swing.KeyStroke)
     * @see #setAccelerator(java.lang.String) 
     */
    public String getAcceleratorAsString() {
        return accelerator == null
                ? null
                : accelerator.toString();
    }

    /**
     * Sets the accelerator key of this menu.
     * <p>
     * The key combination which invokes the menu item's
     * action listeners without navigating the menu hierarchy.
     * @param accelerator The accelerator key.
     * @see #getAccelerator() 
     * @see #getAcceleratorAsString() 
     */
    public void setAccelerator(KeyStroke accelerator) {
        this.accelerator = accelerator;
    }

    /**
     * Sets the accelerator key of this menu from given String.
     * <p>
     * The key combination which invokes the menu item's
     * action listeners without navigating the menu hierarchy.
     * @param accelerator The accelerator String representation.
     * @see #getAccelerator() 
     * @see #getAcceleratorAsString()
     */
    public void setAccelerator(String accelerator) {
        this.accelerator = KeyStroke.getKeyStroke(accelerator);
    }

    /**
     * Returns the menu type.
     * @return The type of menu.
     * @see #setType(org.apex.base.data.MenuType)
     * @see #setType(char)     
     */
    public MenuType getType() {
        return type;
    }

    /**
     * Sets the type of menu.
     * @param type The type of menu.
     * @see MenuType#getMappedType(char) 
     * @see #getType() 
     */
    public void setType(char type) {
        this.type = MenuType.getMappedType(type);
    }

    /**
     * Sets the type of menu.
     * @param type The type of menu.
     * @see MenuType
     * @see #getType() 
     */
    public void setType(MenuType type) {
        this.type = type;
    }

    /**
     * Returns the human readable text represntation of this menu node.
     * @return The text represntation of menu node.
     */
    @Override
    public String toString() {
        return name + (accelerator == null
                ? ""
                : "  [" +
                MenuUtil.getDisplayableAcceleratorText(accelerator) + "]");
    }

    /**
     * Returns relative path of image. 
     * @return The image path of menu.
     * @see #setImage(java.lang.String)
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image path for this menu node.
     * @param imagePath The image path of menu.
     * @see #getImage()
     */
    public void setImage(String imagePath) {
        this.image = imagePath;
    }

    /**
     * Returns the short description of this menu node.
     * @return A short description of menu.
     * @see #setDescription(java.lang.String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the short description of this menu node.
     * @param description A short description of menu.
     * @see #getDescription() 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the menu Id.
     * @return The menu Id.
     * @see #setId(java.lang.String)
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the menu Id.
     * @param id The menu Id.
     * @see #getId()
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Creates a separator menu node.
     * @return The separator menu node.
     */
    public static MenuNode createSeparator() {
        return new MenuNode(SEPARATOR_NAME, SEPARATOR_ID, MenuType.SEPARATOR);
    }

    /**
     * Determines whether this menu node is a separator.
     * @return {@code true} if this menu node is a separator; otherwise returns {@code false}.
     */
    public boolean isSeparator() {
        return this.getId().equals(SEPARATOR_ID) &&
                this.getType().equals(MenuType.SEPARATOR);
    }

    /**
     * Clones this menu node by deeply copying this menu node and all its ancestors
     * and returns the clone.
     * @return The cloned menu node.
     */
    @Override
    public Object clone() {
        MenuNode clonedMenuNode =
                (MenuNode) super.clone();
        addMenusRecursively(this, clonedMenuNode);
        return clonedMenuNode;
    }

    /**
     * Copies the source menu node and all its ancestors to a
     * destination menu node.
     * @param srcNode The source menu node.
     * @param destNode The destination menu node.
     * @return The boolean that indicates whether or not copy is successful.
     */
    private boolean addMenusRecursively(
            MenuNode srcNode, MenuNode destNode) {
        Enumeration menuBundle = srcNode.children();
        while (menuBundle.hasMoreElements()) {
            MenuNode menu =
                    (MenuNode) menuBundle.nextElement();
            MenuNode child = new MenuNode(menu.getName(), menu.getId(),
                    menu.getType());
            child.setDescription(menu.getDescription());
            child.setAccelerator(menu.getAcceleratorAsString());
            child.setImage(menu.getImage());
            child.setMnemonic(menu.getMnemonic());
            child.setGroup(menu.getGroup());
            child.setTargetType(menu.getTargetType());
            child.setTarget(menu.getTarget());
            child.setPressedImage(menu.getPressedImage());
            destNode.add(child);
            if (menu.getChildCount() > 0) {
                if (addMenusRecursively(menu, child)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Recursively parse this node and find the child node with given id.
     * @param id Id of child node to be searched.
     * @return The child menu node if found or {@code null} if child
     *               menu with given id is not found.
     */
    public MenuNode findChildMenuById(String id) {
        if (StringUtil.isNullOrEmpty(id)) {
            return null;
        }
        if (id.equals(SEPARATOR_ID)) {
            return createSeparator();
        }
        MenuNode foundChild = findChildRecursively(id, this);
        return foundChild;
    }

    /**
     * Recursively parses the given reference node and finds the child node with given id.
     * @param id Id of child node to be searched.
     * @param node The reference menu Id.
     * @return The child menu node if found or {@code null} if child
     *               menu with given id is not found.
     */
    private MenuNode findChildRecursively(String id, MenuNode node) {
        Enumeration menuBundle = node.children();
        while (menuBundle.hasMoreElements()) {
            MenuNode menu =
                    (MenuNode) menuBundle.nextElement();
            if (id.equals(menu.getId())) {
                return menu;
            }
            if (menu.getChildCount() > 0) {
                MenuNode result = findChildRecursively(id, menu);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * Returns the target name of this menu.
     * @return The target name.
     * @see #setTarget(java.lang.String)
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the target name of this menu.
     * @param target The target name.
     * @see #getTarget()
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Returns the target type of this menu.
     * @return The target type.
     * @see ActionTarget
     * @see #setTargetType(org.apex.base.data.ActionTarget)
     */
    public ActionTarget getTargetType() {
        return targetType;
    }

    /**
     * Sets the target type of this menu.
     * @param targetType The target type.
     * @see ActionTarget
     * @see #getTargetType()
     */
    public void setTargetType(ActionTarget targetType) {
        this.targetType = targetType;
    }

    /**
     * Returns the button group where this menu belongs.
     * @return The button group.
     * @see ButtonGroup
     * @see #setGroup(javax.swing.ButtonGroup) 
     */
    public ButtonGroup getGroup() {
        return group;
    }

    /**
     * Sets the button group where this menu belongs.
     * @param group The button group.
     * @see ButtonGroup
     * @see #getGroup() 
     */
    public void setGroup(ButtonGroup group) {
        this.group = group;
    }

    /**
     * Returns the path of pressed icon of this menu.
     * @return The pressed icon path.
     * @see #setPressedImage(java.lang.String)
     */
    public String getPressedImage() {
        return pressedImage;
    }

    /**
     * Sets the path of pressed icon of this menu.
     * @param pressedImage The pressed icon path.
     * @see #getPressedImage()
     */
    public void setPressedImage(String pressedImage) {
        this.pressedImage = pressedImage;
    }
}
