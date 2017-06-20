/*
 * EditorConfiguration.java
 * Created on 14 July, 2007, 12:39 PM
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

import org.apex.base.logging.Logger;
import org.apex.base.settings.builder.TemplateConfigurationBuilder;

/**
 * The root configuration object.
 * <p>
 * It holds all top level configuration objects like core menus, document templates,
 * syntax styles etc.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class EditorConfiguration extends AbstractConfiguration {

    /**
     * General configuration.
     */
    private GeneralConfiguration generalConfig;
    /**
     * Style configuration.
     */
    private StyleConfiguration styleConfig;
    /**
     * Menu configuration.
     */
    private MenuConfiguration menuConfig;
    /**
     * Document templates configuration.
     */
    private TemplateConfiguration templateConfig;

    /**
     * Creates a new instance of {@code EditorConfiguration}.
     */
    public EditorConfiguration() {
    }

    /**
     * Nullify document templates configuration.
     * @return {@code true} if dispose is successful; otherwise {@code false}.
     */
    public boolean disposetemplateConfig() {
        boolean templateConfigDisposed = false;
        if (this.templateConfig != null &&
                (!this.templateConfig.isLeaf() ||
                !this.templateConfig.isCacheRequired())) {
            templateConfigDisposed =
                    this.templateConfig.disposeIfCacheNotRequired();
            if (templateConfigDisposed) {
                this.templateConfig = null;
            }
        }
        return templateConfigDisposed;
    }

    /**
     * Nullify general configuration.
     * @return {@code true} if dispose is successful; otherwise {@code false}.
     */
    public boolean disposeGeneralConfig() {
        boolean genConfigDisposed = false;
        // Dispose contained config objects.
        if (this.generalConfig != null &&
                (!this.generalConfig.isLeaf() ||
                !this.generalConfig.isCacheRequired())) {
            genConfigDisposed = this.generalConfig.disposeIfCacheNotRequired();
            if (genConfigDisposed) {
                this.generalConfig = null;
            }
        }
        return genConfigDisposed;
    }

    /**
     * Nullify menu configuration.
     * @return {@code true} if dispose is successful; otherwise {@code false}.
     */
    public boolean disposeMenuConfig() {
        boolean menuConfigDisposed = false;
        if (this.menuConfig != null &&
                (!this.menuConfig.isLeaf() || !this.menuConfig.isCacheRequired())) {
            menuConfigDisposed = this.menuConfig.disposeIfCacheNotRequired();
            if (menuConfigDisposed) {
                this.menuConfig = null;
            }
        }
        return menuConfigDisposed;
    }

    /**
     * Nullify style configuration.
     * @return {@code true} if dispose is successful; otherwise {@code false}.
     */
    public boolean disposeStyleConfig() {
        boolean styleConigDisposed = false;
        if (this.styleConfig != null &&
                (!this.styleConfig.isLeaf() ||
                !this.styleConfig.isCacheRequired())) {
            styleConigDisposed = this.styleConfig.disposeIfCacheNotRequired();
            if (styleConigDisposed) {
                this.styleConfig = null;
            }
        }
        return styleConigDisposed;
    }

    /**
     * Returns general configuration.
     * @return General configuration.
     * @see #setGeneralConfig(org.apex.base.settings.GeneralConfiguration)
     */
    public GeneralConfiguration getGeneralConfig() {
        if (this.generalConfig == null) {
            this.generalConfig = new GeneralConfiguration();
        }
        return generalConfig;
    }

    /**
     * Sets general configuration.
     * @param generalConfig General configuration.
     * @see #getGeneralConfig() 
     */
    public void setGeneralConfig(GeneralConfiguration generalConfig) {
        this.generalConfig = generalConfig;
    //fireGeneralConfigurationChanged(null);
    }

    /**
     * Returns style configuration.
     * @return Style configuration.
     * @see #setStyleConfig(org.apex.base.settings.StyleConfiguration)
     */
    public StyleConfiguration getStyleConfig() {
        if (this.styleConfig == null) {
            this.styleConfig = new StyleConfiguration();
        }
        return styleConfig;
    }

    /**
     * Sets style configuration.
     * @param styleConfig Style configuration.
     * @see #getStyleConfig() 
     */
    public void setStyleConfig(StyleConfiguration styleConfig) {
        this.styleConfig = styleConfig;
    }

    @Override
    public String toString() {
        return "GeneralConfiguration: " + generalConfig +
                "\nStyleConfiguration: " +
                styleConfig +
                "\nMenuConfiguration: " + menuConfig +
                "\nTemplateConfiguration: " +
                templateConfig;
    }

    @Override
    public Object clone() {
        EditorConfiguration clonedConfig = null;
        if (this.generalConfig == null) {
            this.generalConfig = getGeneralConfig();
        }
        if (this.styleConfig == null) {
            this.styleConfig = getStyleConfig();
        }
        if (this.menuConfig == null) {
            this.menuConfig = getMenuConfig();
        }
        if (this.templateConfig == null) {
            this.templateConfig = getTemplateConfig();
        }
        try {
            clonedConfig = (EditorConfiguration) super.clone();
            clonedConfig.setGeneralConfig((GeneralConfiguration) this.generalConfig.clone());
            clonedConfig.setStyleConfig((StyleConfiguration) this.styleConfig.clone());
            clonedConfig.setMenuConfig(
                    (MenuConfiguration) this.menuConfig.clone());
            clonedConfig.setTemplateConfig((TemplateConfiguration) this.templateConfig.clone());
        } catch (CloneNotSupportedException ex) {
            Logger.logError(
                    "Error while cloning multiple configuration objects.", ex);
        }
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        EditorConfiguration clonedConfig =
                (EditorConfiguration) clonedObject;
        this.getGeneralConfig().updateFromClone(clonedConfig.getGeneralConfig());
        this.getStyleConfig().updateFromClone(clonedConfig.getStyleConfig());
        this.getMenuConfig().updateFromClone(clonedConfig.getMenuConfig());
        this.getTemplateConfig().updateFromClone(clonedConfig.getTemplateConfig());
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null &&
                clonedObject instanceof EditorConfiguration) {
            EditorConfiguration clonedConfig =
                    (EditorConfiguration) clonedObject;
            value =
                    (this.generalConfig == null
                    ? clonedConfig.getGeneralConfig() == null
                    : this.generalConfig.equals(clonedConfig.getGeneralConfig())) &&
                    (this.styleConfig == null
                    ? clonedConfig.getStyleConfig() == null
                    : this.styleConfig.equals(clonedConfig.getStyleConfig())) &&
                    (this.menuConfig == null
                    ? clonedConfig.getMenuConfig() == null
                    : this.menuConfig.equals(clonedConfig.getMenuConfig())) &&
                    (this.templateConfig == null
                    ? clonedConfig.getTemplateConfig() == null
                    : this.templateConfig.equals(clonedConfig.getTemplateConfig()));
        }
        Logger.logInfo("Configuration changed: " + !value);
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.generalConfig != null
                ? this.generalConfig.hashCode()
                : 0);
        hash = 43 * hash + (this.styleConfig != null
                ? this.styleConfig.hashCode()
                : 0);
        hash = 43 * hash + (this.menuConfig != null
                ? this.menuConfig.hashCode()
                : 0);
        hash = 43 * hash + (this.templateConfig != null
                ? this.templateConfig.hashCode()
                : 0);
        return hash;
    }

    /**
     * Returns templates configuration.
     * @return Templates configuration.
     * @see #setTemplateConfig(org.apex.base.settings.TemplateConfiguration)
     */
    public TemplateConfiguration getTemplateConfig() {
        if (this.templateConfig == null) {
            this.templateConfig =
                    (TemplateConfiguration) new TemplateConfigurationBuilder().build();
        }
        return templateConfig;
    }

    /**
     * Sets templates configuration.
     * @param templateConfig Templates configuration.
     * @see #getTemplateConfig()
     */
    public void setTemplateConfig(
            TemplateConfiguration templateConfig) {
        this.templateConfig = templateConfig;
    }

    public boolean isConfigurable() {
        return (this.generalConfig == null
                ? new GeneralConfiguration().isConfigurable()
                : this.generalConfig.isConfigurable()) ||
                (this.styleConfig == null
                ? new StyleConfiguration().isConfigurable()
                : this.styleConfig.isConfigurable()) ||
                (this.menuConfig == null
                ? new MenuConfiguration().isConfigurable()
                : this.menuConfig.isConfigurable() ||
                (this.templateConfig == null
                ? new TemplateConfiguration().isConfigurable()
                : this.templateConfig.isConfigurable()));
    }

    public String getConfigFile() {
        return null;
    }

    public boolean isCacheRequired() {
        return (this.generalConfig == null
                ? new GeneralConfiguration().isCacheRequired()
                : this.generalConfig.isCacheRequired()) ||
                (this.styleConfig == null
                ? new StyleConfiguration().isCacheRequired()
                : this.styleConfig.isCacheRequired()) ||
                (this.menuConfig == null
                ? new MenuConfiguration().isCacheRequired()
                : this.menuConfig.isCacheRequired() ||
                (this.templateConfig == null
                ? new TemplateConfiguration().isCacheRequired()
                : this.templateConfig.isCacheRequired()));
    }

    public boolean disposeIfCacheNotRequired() {
        boolean generalConfigDisposed = disposeGeneralConfig();
        boolean styleConigDisposed = disposeStyleConfig();
        boolean menuConfigDisposed = disposeMenuConfig();
        boolean templateConfigDisposed = disposetemplateConfig();
        return generalConfigDisposed && styleConigDisposed &&
                menuConfigDisposed &&
                templateConfigDisposed;
    }

    public boolean isLeaf() {
        return false;
    }

    /**
     * Returns menu configuration.
     * @return Menu configuration.
     * @see #setMenuConfig(org.apex.base.settings.MenuConfiguration)
     */
    public MenuConfiguration getMenuConfig() {
        if (menuConfig == null) {
            menuConfig = new MenuConfiguration();
        }
        return menuConfig;
    }

    /**
     * Sets menu configuration.
     * @param menuConfig Menu configuration.
     * @see #getMenuConfig() 
     */
    public void setMenuConfig(MenuConfiguration menuConfig) {
        this.menuConfig = menuConfig;
    }

    public void remove() {
        if (this.generalConfig != null) {
            this.generalConfig.remove();
            this.generalConfig = null;
        }
        if (this.styleConfig != null) {
            this.styleConfig.remove();
            this.styleConfig = null;
        }
        if (this.menuConfig != null) {
            this.menuConfig.remove();
            this.menuConfig = null;
        }
        if (this.templateConfig != null) {
            this.templateConfig.remove();
            this.templateConfig = null;
        }

    }
}