/* 
 * MenuConfiguration.java
 * Created on Dec 16, 2007,1:45:24 AM
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
package org.apex.base.settings;

import org.apex.base.data.CustomTool;
import org.apex.base.data.MenuNode;
import org.apex.base.data.MenuType;
import org.apex.base.logging.Logger;
import org.apex.base.settings.builder.CustomToolBuilder;
import org.apex.base.settings.builder.MenuKeyBindingBuilder;
import org.apex.base.settings.builder.ProvidedToolBuilder;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.data.ActionTarget;
import org.apex.base.data.IDocumentType;

/**
 * The menu configuration object.
 * <p>
 * It holds core menu configuration, menu-key binding configuration, tool
 * configuration etc.
 * @author Mrityunjoy Saha
 * @version 1.1
 * @since Apex 1.0
 */
public class MenuConfiguration extends AbstractConfiguration {

    /**
     * Menu-key binding configuration.
     */
    private MenuKeyBindingConfiguration menuKeyBindings;
    /**
     * Custom tool configuration.
     */
    private CustomToolConfiguration customTools;
    /**
     * Provided tool configuration.
     */
    private ProvidedToolConfiguration providedTools;

    /**
     * Creates a new instance of {@code MenuConfiguration}.
     */
    public MenuConfiguration() {
        super();
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null
                && clonedObject instanceof MenuConfiguration) {
            MenuConfiguration clonedConfig =
                    (MenuConfiguration) clonedObject;
            value = (this.menuKeyBindings == null
                    ? clonedConfig.getMenuKeyBindings() == null
                    : this.menuKeyBindings.equals(clonedConfig.
                    getMenuKeyBindings()))
                    && (this.customTools == null
                    ? clonedConfig.getCustomTools()
                    == null
                    : this.customTools.equals(clonedConfig.getCustomTools()))
                    && (this.providedTools == null
                    ? clonedConfig.getProvidedTools()
                    == null
                    : this.providedTools.equals(clonedConfig.getProvidedTools()));
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.menuKeyBindings != null
                ? this.menuKeyBindings.hashCode()
                : 0);
        hash = 71 * hash + (this.customTools != null
                ? this.customTools.hashCode()
                : 0);
        hash = 71 * hash + (this.providedTools != null
                ? this.providedTools.hashCode()
                : 0);
        return hash;
    }

    public void updateFromClone(Object clonedObject) {
        MenuConfiguration clonedConfig =
                (MenuConfiguration) clonedObject;
        this.menuKeyBindings.updateFromClone(clonedConfig.getMenuKeyBindings());
        this.customTools.updateFromClone(clonedConfig.getCustomTools());
        this.providedTools.updateFromClone(clonedConfig.getProvidedTools());
    }

    public boolean isConfigurable() {
        return (this.menuKeyBindings == null
                ? new MenuKeyBindingConfiguration().isConfigurable()
                : this.menuKeyBindings.isConfigurable())
                || (this.customTools == null
                ? new CustomToolConfiguration().isConfigurable()
                : this.customTools.isConfigurable())
                || (this.providedTools == null
                ? new ProvidedToolConfiguration().isConfigurable()
                : this.providedTools.isConfigurable());
    }

    public String getConfigFile() {
        return null;
    }

    public boolean isCacheRequired() {
        return (this.menuKeyBindings == null
                ? new MenuKeyBindingConfiguration().isCacheRequired()
                : this.menuKeyBindings.isCacheRequired())
                || (this.customTools == null
                ? new CustomToolConfiguration().isCacheRequired()
                : this.customTools.isCacheRequired())
                || (this.providedTools == null
                ? new ProvidedToolConfiguration().isCacheRequired()
                : this.providedTools.isCacheRequired());
    }

    public boolean disposeIfCacheNotRequired() {
        boolean menuKeyBindingsDisposed = false;
        boolean customToolsDisposed = false;
        boolean providedToolsDisposed = false;

        // Dispose contained config objects.       
        if (this.menuKeyBindings != null && (!this.menuKeyBindings.isLeaf()
                || !this.menuKeyBindings.isCacheRequired())) {
            menuKeyBindingsDisposed =
                    this.menuKeyBindings.disposeIfCacheNotRequired();
            if (menuKeyBindingsDisposed) {
                this.menuKeyBindings = null;
            }
        }

        if (this.customTools != null && (!this.customTools.isLeaf()
                || !this.customTools.isCacheRequired())) {
            customToolsDisposed = this.customTools.disposeIfCacheNotRequired();
            if (customToolsDisposed) {
                this.customTools = null;
            }
        }

        if (this.providedTools != null && (!this.providedTools.isLeaf()
                || !this.providedTools.isCacheRequired())) {
            providedToolsDisposed =
                    this.providedTools.disposeIfCacheNotRequired();
            if (providedToolsDisposed) {
                this.providedTools = null;
            }
        }
        return menuKeyBindingsDisposed
                && customToolsDisposed && providedToolsDisposed;
    }

    public boolean isLeaf() {
        return false;
    }

    @Override
    public String toString() {
        return " ^menuKeyBindings: " + getMenuKeyBindings()
                + " ^customTools: " + getCustomTools()
                + " ^proviedTools: " + getProvidedTools();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        MenuConfiguration clonedConfig = null;
        if (this.customTools == null) {
            this.customTools = getCustomTools();
        }
        if (this.providedTools == null) {
            this.providedTools = getProvidedTools();
        }
        if (this.menuKeyBindings == null) {
            this.menuKeyBindings = getMenuKeyBindings();
        }
        clonedConfig =
                (MenuConfiguration) super.clone();
        clonedConfig.setMenuKeyBindings((MenuKeyBindingConfiguration) this.menuKeyBindings.
                clone());
        clonedConfig.setCustomTools((CustomToolConfiguration) this.customTools.
                clone());
        clonedConfig.setProvidedTools((ProvidedToolConfiguration) this.providedTools.
                clone());
        return clonedConfig;
    }

    /**
     * Returns menu-key binding configuration.
     * @return Menu-key binding configuration.
     * @see #setMenuKeyBindings(org.apex.base.settings.MenuKeyBindingConfiguration)
     */
    public MenuKeyBindingConfiguration getMenuKeyBindings() {
        if (this.menuKeyBindings == null) {
            this.menuKeyBindings =
                    (MenuKeyBindingConfiguration) new MenuKeyBindingBuilder().
                    build();
        }
        return menuKeyBindings;
    }

    /**
     * Sets menu-key binding configuration.
     * @param menuKeyBindings Menu-key binding configuration.
     * @see #getMenuKeyBindings() 
     */
    public void setMenuKeyBindings(MenuKeyBindingConfiguration menuKeyBindings) {
        this.menuKeyBindings = menuKeyBindings;
    }

    /**
     * Returns custom tool configuration.
     * @return Custom tool configuration.
     * @see #setCustomTools(org.apex.base.settings.CustomToolConfiguration)
     */
    public CustomToolConfiguration getCustomTools() {
        if (this.customTools == null) {
            this.customTools =
                    (CustomToolConfiguration) new CustomToolBuilder().build();
        }
        return customTools;
    }

    /**
     * Sets custom tool configuration.
     * @param customTools Custom tool configuration.
     * @see #getCustomTools() 
     */
    public void setCustomTools(CustomToolConfiguration customTools) {
        this.customTools = customTools;
    }

    public void remove() {
        if (this.customTools != null) {
            this.customTools.remove();
            this.customTools = null;
        }
        if (this.providedTools != null) {
            this.providedTools.remove();
            this.providedTools = null;
        }
        if (this.menuKeyBindings != null) {
            this.menuKeyBindings.remove();
            this.menuKeyBindings = null;
        }
    }

    /**
     * It adds up custom tools to tree of core menus and then returns the root menu node.
     * @return The root menu node.
     */
    public MenuNode getAllAvailableMenus() {
        // NOTE not a good idea removing menus every time this method being called
        //remove(); 
        // TODO: Get core menus from editor context.
        MenuNode coreMenuTree = EditorBase.getContext().getCoreMenus().getMenus();
        if (coreMenuTree == null) {
            return null;
        }
        // Ensure recent files list is loaded.
        EditorBase.getContext().getEditorProperties().getRecentFiles();
        // Add typed documents
        List<IDocumentType> typedDocuments = EditorBase.getContext().
                getConfiguration().
                getGeneralConfig().getDocTypes().getDocumentTypes();
        if (typedDocuments.size() > 0) {
            MenuNode typedDocsMenu = coreMenuTree.findChildMenuById(
                    MenuConstants.TYPED_DOCUMENTS);
            // If document type are already added remove them and then add
            if (typedDocsMenu != null && typedDocsMenu.getChildCount() > 0) {
                typedDocsMenu.removeAllChildren();
            }
            if (typedDocsMenu != null) {
                MenuNode typedMenu = null;
                for (IDocumentType docType : typedDocuments) {
                    typedMenu = new MenuNode(docType.getDisplayName(),
                            docType.getName().toLowerCase(),
                            MenuType.MENUITEM);
                    typedMenu.setTargetType(ActionTarget.MENU);
                    typedMenu.setTarget("org.apex.base.menu.NewFileMenu");
                    typedDocsMenu.add(typedMenu);
                }
            }
        }
        Vector<CustomTool> customToolList =
                getCustomTools() == null
                ? null
                : this.customTools.getCustomTools();
        // Add custom tools.
        if (customToolList != null && customToolList.size() >= 1) {
            MenuNode toolsMenu = coreMenuTree.findChildMenuById(
                    MenuConstants.TOOLS);
            if (toolsMenu != null) {
                /**
                 * See whether a menu with id 'customtools' is already added. If yes then remove the same and add newly formed custom tools menu. 
                 */
                MenuNode lastChild = (MenuNode) toolsMenu.getLastChild();
                if (lastChild != null && lastChild.getId().equals(
                        MenuConstants.CUSTOM_TOOLS)) {
                    toolsMenu.remove(lastChild);
                    toolsMenu.remove((MenuNode) toolsMenu.getLastChild());
                }
                /**
                 * Create a menu named called custom tools and place all custom tools under it.
                 * Finally add this custom tools menu to 'Tools' menu.
                 */
                MenuNode customTool = new MenuNode("CustomTools",
                        MenuConstants.CUSTOM_TOOLS,
                        MenuType.MENU);
                for (CustomTool tool : customToolList) {
                    if (tool.createAndFetchMenuNode() != null) {
                        customTool.add(tool.createAndFetchMenuNode());
                    }
                }
                if (customTool.getChildCount() >= 1) {
                    toolsMenu.add(MenuNode.createSeparator());
                    toolsMenu.add(customTool);
                }
            }
        }
        applyCustomKeyBindings(coreMenuTree);
        return coreMenuTree;
    }

    /**
     * Applies custom key bindings to all menus.
     * @param coreMenuTree The root menu node.
     */
    public void applyCustomKeyBindings(MenuNode coreMenuTree) {
        if (getMenuKeyBindings() == null || getMenuKeyBindings().
                getMenuKeyBindings() == null || getMenuKeyBindings().
                getMenuKeyBindings().
                isEmpty()) {
            return;
        }
        Logger.logInfo("Applying custom key bindings.");
        applyKeyBindingsRecursively(coreMenuTree);
    }

    /**
     * Applies custom key bindings to the tree of menus recursively.
     * @param srcNode The root menu node.
     * @return {@code true} if key binding applied successfully; otherwise returns {@code false}.
     */
    private boolean applyKeyBindingsRecursively(MenuNode srcNode) {
        Enumeration menuBundle = srcNode.children();
        while (menuBundle.hasMoreElements()) {
            MenuNode menu =
                    (MenuNode) menuBundle.nextElement();
            if (menu.getChildCount() > 0) {
                if (applyKeyBindingsRecursively(menu)) {
                    return true;
                }
            } else if (!menu.getType().equals(MenuType.MENU)
                    && !menu.getType().equals(MenuType.ROOT)) {
                /**
                 * A Menu item found.
                 * If custom binding exists for this menu id, apply it.
                 * Note that to remove a binding, custom binding will have value 'deleted', which in turn
                 * will set the <code>KeyStroke</code> as null.
                 */
                if (this.getMenuKeyBindings().getMenuKeyBindings().containsKey(menu.
                        getId())) {
                    String binding =
                            this.getMenuKeyBindings().getMenuKeyBindings().
                            get(menu.getId());
                    menu.setAccelerator(binding);
                }

            }
        }
        return false;
    }

    /**
     * Returns provided tool configuration.
     * @return Provided tool configuration.
     * @see #setProvidedTools(org.apex.base.settings.ProvidedToolConfiguration)
     */
    public ProvidedToolConfiguration getProvidedTools() {
        if (this.providedTools == null) {
            this.providedTools =
                    (ProvidedToolConfiguration) new ProvidedToolBuilder().build();
        }
        return this.providedTools;
    }

    /**
     * Sets provided tool configuration.
     * @param providedTools Provided tool configuration.
     * @see #getProvidedTools()
     */
    public void setProvidedTools(ProvidedToolConfiguration providedTools) {
        this.providedTools = providedTools;
    }
}
