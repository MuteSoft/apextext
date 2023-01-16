/*
 * AbstractConfiguration.java
 * Created on 15 September, 2007, 11:23 PM
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

import org.apex.base.data.Configuration;
import org.apex.base.data.CustomTool;
import org.apex.base.data.ProvidedTool;
import org.apex.base.settings.event.CustomToolConfigurationChangeEvent;
import org.apex.base.settings.event.CustomToolConfigurationChangeListener;
import org.apex.base.settings.event.DocTypesConfigChangeEvent;
import org.apex.base.settings.event.DocTypesConfigChangeListener;
import org.apex.base.settings.event.FontStyleConfigChangeEvent;
import org.apex.base.settings.event.FontStyleConfigChangeListener;
import org.apex.base.settings.event.GeneralConfigurationChangeEvent;
import org.apex.base.settings.event.GeneralConfigurationChangeListener;
import org.apex.base.settings.event.GeneralSectionConfigChangeEvent;
import org.apex.base.settings.event.GeneralSectionConfigChangeListener;
import org.apex.base.settings.event.HighlightStyleConfigChangeEvent;
import org.apex.base.settings.event.HighlightStyleConfigChangeListener;
import org.apex.base.settings.event.MenuKeyBindingConfigurationChangeEvent;
import org.apex.base.settings.event.MenuKeyBindingConfigurationChangeListener;
import org.apex.base.settings.event.ProvidedToolConfigChangeEvent;
import org.apex.base.settings.event.ProvidedToolConfigChangeListener;
import org.apex.base.settings.event.StyleConfigurationChangeEvent;
import org.apex.base.settings.event.StyleConfigurationChangeListener;
import org.apex.base.settings.event.SyntaxStyleConfigChangeEvent;
import org.apex.base.settings.event.SyntaxStyleConfigChangeListener;
import java.awt.Color;
import java.util.Vector;
import javax.swing.event.EventListenerList;

/**
 * A base class for all configuration objects in the application.
 * <p>
 * It provides listener and callback facility for configuration objects and contains commonly
 * used methods.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class AbstractConfiguration implements Configuration {

    /**
     * Master list of listeners registered to configuration objects.
     */
    private static EventListenerList listenerList =
            new EventListenerList();

    /** 
     * Creates a new instance of AbstractConfiguration.
     */
    public AbstractConfiguration() {
    }

    /**
     * Adds a {@code GeneralConfigurationChangeListener} to a configuration object.
     * @param listener The {@code GeneralConfigurationChangeListener} to be added.
     */
    public static void addGeneralConfigurationChangeListener(
            GeneralConfigurationChangeListener listener) {
        listenerList.add(GeneralConfigurationChangeListener.class, listener);
    }

    /**
     * Adds a {@code StyleConfigurationChangeListener} to a configuration object.
     * @param listener The {@code StyleConfigurationChangeListener} to be added.
     */
    public static void addStyleConfigurationChangeListener(
            StyleConfigurationChangeListener listener) {
        listenerList.add(StyleConfigurationChangeListener.class, listener);
    }

    /**
     * Adds a {@code SyntaxStyleConfigChangeListener} to a configuration object.
     * @param listener The {@code SyntaxStyleConfigChangeListener} to be added.
     */
    public static void addSyntaxStyleConfigChangeListener(
            SyntaxStyleConfigChangeListener listener) {
        listenerList.add(SyntaxStyleConfigChangeListener.class, listener);
    }

    /**
     * Adds a {@code HighlightStyleConfigChangeListener} to a configuration object.
     * @param listener The {@code HighlightStyleConfigChangeListener} to be added.
     */
    public static void addHighlightStyleConfigChangeListener(
            HighlightStyleConfigChangeListener listener) {
        listenerList.add(HighlightStyleConfigChangeListener.class, listener);
    }

    /**
     * Adds a {@code FontStyleConfigChangeListener} to a configuration object.
     * @param listener The {@code FontStyleConfigChangeListener} to be added.
     */
    public static void addFontStyleConfigChangeListener(
            FontStyleConfigChangeListener listener) {
        listenerList.add(FontStyleConfigChangeListener.class, listener);
    }

    /**
     * Adds a {@code DocTypesConfigChangeListener} to a configuration object.
     * @param listener The {@code DocTypesConfigChangeListener} to be added.
     */
    public static void addDocumentTypesConfigChangeListener(
            DocTypesConfigChangeListener listener) {
        listenerList.add(DocTypesConfigChangeListener.class, listener);
    }

    /**
     * Adds a {@code GeneralSectionConfigChangeListener} to a configuration object.
     * @param listener The {@code GeneralSectionConfigChangeListener} to be added.
     */
    public static void addGeneralSectionConfigChangeListener(
            GeneralSectionConfigChangeListener listener) {
        listenerList.add(GeneralSectionConfigChangeListener.class, listener);
    }

    /**
     * Adds a {@code CustomToolConfigurationChangeListener} to a configuration object.
     * @param listener The {@code CustomToolConfigurationChangeListener} to be added.
     */
    public static void addCustomToolConfigChangeListener(
            CustomToolConfigurationChangeListener listener) {
        listenerList.add(CustomToolConfigurationChangeListener.class, listener);
    }

    /**
     * Adds a {@code ProvidedToolConfigChangeListener} to a configuration object.
     * @param listener The {@code ProvidedToolConfigChangeListener} to be added.
     */
    public static void addProvidedToolConfigChangeListener(
            ProvidedToolConfigChangeListener listener) {
        listenerList.add(ProvidedToolConfigChangeListener.class, listener);
    }

    /**
     * Adds a {@code MenuKeyBindingConfigurationChangeListener} to a configuration object.
     * @param listener The {@code MenuKeyBindingConfigurationChangeListener} to be added.
     */
    public static void addMenuKeyBindingConfigurationChangeListener(
            MenuKeyBindingConfigurationChangeListener listener) {
        listenerList.add(MenuKeyBindingConfigurationChangeListener.class,
                listener);
    }

    /**
     * Support for reporting general configuration changes. 
     * This method can be called when general configuration has changed and it will
     * send the appropriate {@code GeneralConfigurationChangeEvent} to all registered
     * {@code GeneralConfigurationChangeListener}.
     * @param generalConfig The general configuration object.
     */
    protected void fireGeneralConfigurationChanged(
            GeneralConfiguration generalConfig) {
        Object[] listeners = listenerList.getListenerList();
        GeneralConfiguration eventGenConfig =
                generalConfig == null
                ? new GeneralConfiguration()
                : generalConfig;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] ==
                    GeneralConfigurationChangeListener.class) {
                ((GeneralConfigurationChangeListener) listeners[i + 1]).
                        propertyValueChanged(new GeneralConfigurationChangeEvent(
                        eventGenConfig));
            }
        }
    }

    /**
     * Support for reporting style configuration changes. 
     * This method can be called when style configuration has changed and it will
     * send the appropriate {@code StyleConfigurationChangeEvent} to all registered
     * {@code StyleConfigurationChangeListener}.
     * @param styleConfig The style configuration object.
     */
    protected void fireStyleConfigurationChanged(StyleConfiguration styleConfig) {
        Object[] listeners = listenerList.getListenerList();
        StyleConfiguration eventStyleConfig =
                styleConfig == null
                ? new StyleConfiguration()
                : styleConfig;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] ==
                    StyleConfigurationChangeListener.class) {
                ((StyleConfigurationChangeListener) listeners[i + 1]).
                        propertyValueChanged(new StyleConfigurationChangeEvent(
                        eventStyleConfig));
            }
        }
    }

    /**
     * Support for reporting font style configuration changes. 
     * This method can be called when font style configuration has changed and it will
     * send the appropriate {@code FontStyleConfigChangeEvent} to all registered
     * {@code FontStyleConfigChangeListener}.
     * @param fontStyleConfig The font style configuration object.
     */
    protected void fireFontStyleConfigurationChanged(
            FontStyleConfiguration fontStyleConfig) {
        Object[] listeners = listenerList.getListenerList();
        FontStyleConfiguration eventFontStyleConfig =
                fontStyleConfig == null
                ? new FontStyleConfiguration()
                : fontStyleConfig;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] ==
                    FontStyleConfigChangeListener.class) {
                ((FontStyleConfigChangeListener) listeners[i + 1]).
                        propertyValueChanged(new FontStyleConfigChangeEvent(
                        eventFontStyleConfig));
            }
        }
        fireStyleConfigurationChanged(null);
    }

    /**
     * Support for reporting syntax style configuration changes. 
     * This method can be called when syntax style configuration has changed and it will
     * send the appropriate {@code SyntaxStyleConfigChangeEvent} to all registered
     * {@code SyntaxStyleConfigChangeListener}.
     * @param synatxStyleConfig The syntax style configuration object.
     *
     */
    protected void fireSyntaxStyleConfigurationChanged(
            SyntaxStyleConfiguration synatxStyleConfig) {
        Object[] listeners = listenerList.getListenerList();
        SyntaxStyleConfiguration eventSyntaxStyleConfig =
                synatxStyleConfig == null
                ? new SyntaxStyleConfiguration()
                : synatxStyleConfig;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] ==
                    SyntaxStyleConfigChangeListener.class) {
                ((SyntaxStyleConfigChangeListener) listeners[i + 1]).
                        propertyValueChanged(new SyntaxStyleConfigChangeEvent(
                        eventSyntaxStyleConfig));
            }
        }
        fireStyleConfigurationChanged(null);
    }

    /**
     * Support for reporting highlight style configuration changes. 
     * This method can be called when highlight style configuration has changed and it will
     * send the appropriate {@code HighlightStyleConfigChangeEvent} to all registered
     * {@code HighlightStyleConfigChangeListener}.
     * @param highlightStyleConfig The highlight style configuration object.
     */
    protected void fireHighlightStyleConfigurationChanged(
            HighlightStyleConfiguration highlightStyleConfig) {
        Object[] listeners = listenerList.getListenerList();
        HighlightStyleConfiguration eventHighlightStyleConfig =
                highlightStyleConfig == null
                ? new HighlightStyleConfiguration()
                : highlightStyleConfig;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] ==
                    HighlightStyleConfigChangeListener.class) {
                ((HighlightStyleConfigChangeListener) listeners[i + 1]).
                        propertyValueChanged(new HighlightStyleConfigChangeEvent(
                        eventHighlightStyleConfig));
            }
        }
        fireStyleConfigurationChanged(null);
    }

    /**
     * Support for reporting general section configuration changes. 
     * This method can be called when general section configuration has changed and it will
     * send the appropriate {@code GeneralSectionConfigChangeEvent} to all registered
     * {@code GeneralSectionConfigChangeListener}.
     * @param genSecConfig The general section configuration object.
     */
    protected void fireGeneralSectionConfigurationChanged(
            GeneralSectionConfiguration genSecConfig) {
        Object[] listeners = listenerList.getListenerList();
        GeneralSectionConfiguration eventGenSecConfig =
                genSecConfig == null
                ? new GeneralSectionConfiguration()
                : genSecConfig;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] ==
                    GeneralSectionConfigChangeListener.class) {
                ((GeneralSectionConfigChangeListener) listeners[i + 1]).
                        propertyValueChanged(new GeneralSectionConfigChangeEvent(
                        eventGenSecConfig));
            }
        }
        fireGeneralConfigurationChanged(null);
    }

    /**
     * Support for reporting document types configuration changes. 
     * This method can be called when document types configuration has changed and it will
     * send the appropriate {@code DocTypesConfigChangeEvent} to all registered
     * {@code DocTypesConfigChangeListener}.
     * @param docTypesConfig The document types configuration object.
     */
    protected void fireDocumentTypesConfigurationChanged(
            DocumentTypesConfiguration docTypesConfig) {
        Object[] listeners = listenerList.getListenerList();
        DocumentTypesConfiguration eventDocTypesConfig =
                docTypesConfig == null
                ? new DocumentTypesConfiguration()
                : docTypesConfig;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] ==
                    DocTypesConfigChangeListener.class) {
                ((DocTypesConfigChangeListener) listeners[i + 1]).
                        propertyValueChanged(new DocTypesConfigChangeEvent(
                        eventDocTypesConfig));
            }
        }
        fireGeneralConfigurationChanged(null);
    }

    /**
     * Support for reporting custom tool configuration changes. 
     * This method can be called when custom tool configuration has changed and it will
     * send the appropriate {@code CustomToolConfigurationChangeEvent} to all registered
     * {@code CustomToolConfigurationChangeListener}.
     * @param customToolConfig The custom tool configuration object.
     */
    protected void fireCustomToolConfigurationChanged(
            CustomToolConfiguration customToolConfig) {
        Object[] listeners = listenerList.getListenerList();
        CustomToolConfiguration eventCustomToolConfig =
                customToolConfig == null
                ? new CustomToolConfiguration()
                : customToolConfig;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] ==
                    CustomToolConfigurationChangeListener.class) {
                ((CustomToolConfigurationChangeListener) listeners[i + 1]).
                        propertyValueChanged(new CustomToolConfigurationChangeEvent(
                        eventCustomToolConfig));
            }
        }
    }

    /**
     * Support for reporting provided tool configuration changes. 
     * This method can be called when provided tool configuration has changed and it will
     * send the appropriate {@code ProvidedToolConfigChangeEvent} to all registered
     * {@code ProvidedToolConfigChangeListener}.
     * @param providedToolConfig The provided tool configuration object.
     */
    protected void fireProvidedToolConfigurationChanged(
            ProvidedToolConfiguration providedToolConfig) {
        Object[] listeners = listenerList.getListenerList();
        ProvidedToolConfiguration eventProvidedToolConfig =
                providedToolConfig == null
                ? new ProvidedToolConfiguration()
                : providedToolConfig;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] ==
                    ProvidedToolConfigChangeListener.class) {
                ((ProvidedToolConfigChangeListener) listeners[i + 1]).
                        propertyValueChanged(new ProvidedToolConfigChangeEvent(
                        eventProvidedToolConfig));
            }
        }
    }

    /**
     * Support for reporting menu-key binding configuration changes. 
     * This method can be called when menu-key binding configuration has changed and it will
     * send the appropriate {@code MenuKeyBindingConfigurationChangeEvent} to all registered
     * {@code MenuKeyBindingConfigurationChangeListener}.
     * @param keyBindingConfig The menu-key binding configuration object.
     */
    protected void fireMenuKeyBindingConfigurationChanged(
            MenuKeyBindingConfiguration keyBindingConfig) {
        Object[] listeners = listenerList.getListenerList();
        MenuKeyBindingConfiguration eventKeyBindingConfig =
                keyBindingConfig == null
                ? new MenuKeyBindingConfiguration()
                : keyBindingConfig;
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] ==
                    MenuKeyBindingConfigurationChangeListener.class) {
                ((MenuKeyBindingConfigurationChangeListener) listeners[i + 1]).
                        propertyValueChanged(new MenuKeyBindingConfigurationChangeEvent(
                        eventKeyBindingConfig));
            }
        }
    }

    /**
     * Determines whether or not given {@code String} changed by comparing old and new
     * values.
     * @param prevValue Old String.
     * @param newValue New String.
     * @return {@code true} if changed; otherwise returns {@code false}.
     */
    protected boolean isChanged(String prevValue, String newValue) {
        return !(prevValue == null
                ? newValue == null
                : prevValue.equals(newValue));
    }

    /**
     * Determines whether or not given {@code Color} changed by comparing old and new
     * values.
     * @param prevValue Old Color.
     * @param newValue New Color.
     * @return {@code true} if changed; otherwise returns {@code false}.
     */
    protected boolean isChanged(Color prevValue, Color newValue) {
        return !(prevValue == null
                ? newValue == null
                : prevValue.equals(newValue));
    }

    /**
     * Determines whether or not given {@code int} changed by comparing old and new
     * values.
     * @param prevValue Old int value.
     * @param newValue New int value.
     * @return {@code true} if changed; otherwise returns {@code false}.
     */
    protected boolean isChanged(int prevValue, int newValue) {
        if (prevValue != newValue) {
            return true;
        }
        return false;
    }

    /**
     * Determines whether or not given {@code boolean} changed by comparing old and new
     * values.
     * @param prevValue Old boolean.
     * @param newValue New boolean.
     * @return {@code true} if changed; otherwise returns {@code false}.
     */
    protected boolean isChanged(boolean prevValue, boolean newValue) {
        if (prevValue != newValue) {
            return true;
        }
        return false;
    }

    /**
     * Determines whether or not given {@code Vector} of {@code String} changed by comparing old and new
     * values.
     * @param prevValue Old Vector.
     * @param newValue New Vector.
     * @return {@code true} if changed; otherwise returns {@code false}.
     */
    protected boolean isChanged(Vector<String> prevValue,
                                Vector<String> newValue) {
        return !(prevValue == null
                ? newValue == null
                : prevValue.equals(newValue));
    }

    /**
     * Determines whether or not given {@code Vector} of {@code Style} changed by comparing old and new
     * values.
     * @param prevValue Old Vector.
     * @param newValue New Vector.
     * @return {@code true} if changed; otherwise returns {@code false}.
     */
    protected boolean isStylesChanged(Vector<Style> prevValue,
                                      Vector<Style> newValue) {
        return !(prevValue == null
                ? newValue == null
                : prevValue.equals(newValue));
    }

    /**
     * Determines whether or not given {@code Vector} of {@code CustomTool} changed by comparing old and new
     * values.
     * @param prevValue Old Vector.
     * @param newValue New Vector.
     * @return {@code true} if changed; otherwise returns {@code false}.
     */
    protected boolean isCustomToolsChanged(Vector<CustomTool> prevValue,
                                           Vector<CustomTool> newValue) {
        return !(prevValue == null
                ? newValue == null
                : prevValue.equals(newValue));
    }

    /**
     * Determines whether or not given {@code Vector} of {@code ProvidedTool} changed by comparing old and new
     * values.
     * @param prevValue Old Vector.
     * @param newValue New Vector.
     * @return {@code true} if changed; otherwise returns {@code false}.
     */
    protected boolean isProvidedToolsChanged(Vector<ProvidedTool> prevValue,
                                             Vector<ProvidedTool> newValue) {
        return !(prevValue == null
                ? newValue == null
                : prevValue.equals(newValue));
    }

    /**
     * Determines whether or not given {@code HighlightColor} changed by comparing old and new
     * values.
     * @param prevValue Old highlight color.
     * @param newValue New highlight color.
     * @return {@code true} if changed; otherwise returns {@code false}.
     */
    protected boolean isHighlightColorChanged(HighlightColor prevValue,
                                              HighlightColor newValue) {
        return !(prevValue == null
                ? newValue == null
                : prevValue.equals(newValue));
    }

    /**
     * Indicates whether some other configuration object is "equal to" this one.
     * @param clonedConfig The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     *               argument; {@code false} otherwise.
     * @see #hashCode()
     */
    @Override
    public abstract boolean equals(Object clonedConfig);

    /**
     * Returns a hash code value for this configuration object. This method is 
     * supported for the benefit of hashtables such as those provided by 
     * <code>java.util.Hashtable</code>. 
     * @return A hash code value for this object.
     */
    @Override
    public abstract int hashCode();

    /**
     * Returns human readable text representation of this configuration object.
     * @return Text representation of this object.
     */
    @Override
    public abstract String toString();

    /**
     * Creates and returns a deep copy of this configuration object.
     * @return A clone of this instance.
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
