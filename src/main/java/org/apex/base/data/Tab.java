/*
 * Tab.java
 * Created on December 25, 2006, 10:38 PM
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

import org.apex.base.constant.CommonConstants;
import java.awt.Component;
import javax.swing.Icon;

/**
 * A tab which can be added to {@link org.apex.base.component.ApexTabbedPane}.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class Tab {

    /**
     * Tab title.
     */
    private String title;
    /**
     * Tab icon.
     */
    private Icon icon;
    /**
     * Associated component with this tab. Generally this is a {@code JPanel}.
     */
    private Component component;
    /**
     * Tab tooltip.
     */
    private String tip;
    /**
     * Underlying tab component.
     */
    private Component tabComponent;

    /**
     * Constructs an {@code Tab} with given title, icon, component, tooltip
     * and tab component.
     * @param mTitle Tab title.
     * @param mIcon Tab icon.
     * @param mComponent Associated component.
     * @param mTip Tab tooltip.
     * @param tabComponent Underlying tab component.
     */
    public Tab(String mTitle, Icon mIcon, Component mComponent, String mTip,
            Component tabComponent) {
        this.title = CommonConstants.TAB_TITLE_EXTRA_SPACE + mTitle + CommonConstants.TAB_TITLE_EXTRA_SPACE;
        this.icon = mIcon;
        this.component = mComponent;
        this.tip = mTip;
        this.tabComponent = tabComponent;
    }

    /**
     * Constructs an {@code Tab} with given title and component.
     * Tab title is considered as tab tooltip.
     * @param mTitle Tab title.
     * @param mComponent Tab component.
     */
    public Tab(String mTitle, Component mComponent) {
        this(mTitle, null, mComponent, mTitle, null);
    }

    /**
     * Constructs an {@code Tab} with given title, component
     * and tab component.
     * Tab title is considered as tab tooltip.
     * @param mTitle Tab title.
     * @param mComponent Associated component.
     * @param tabComponent Underlying tab component.
     */
    public Tab(String mTitle, Component mComponent, Component tabComponent) {
        this(mTitle, null, mComponent, mTitle, tabComponent);
    }

    /**
     * Constructs an {@code Tab} with given title, icon and component.
     * Tab title is considered as tab tooltip.
     * @param mTitle Tab title.
     * @param mIcon Tab icon.
     * @param mComponent Associated component.
     */
    public Tab(String mTitle, Icon mIcon, Component mComponent) {
        this(mTitle, mIcon, mComponent, mTitle, null);
    }

    /**
     * Constructs an {@code Tab} with given title, icon, component
     * and tab component.
     * Tab title is considered as tab tooltip.
     * @param mTitle Tab title.
     * @param mIcon Tab icon.
     * @param mComponent Associated component.
     * @param tabComponent Underlying component.
     */
    public Tab(String mTitle, Icon mIcon, Component mComponent, Component tabComponent) {
        this(mTitle, mIcon, mComponent, mTitle, tabComponent);
    }

    /**
     * Sets tab title.
     * @param mTitle Tab title.
     * @see #getTitle() 
     */
    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    /**
     * Returns tab title.
     * @return Tab title.
     * @see #setTitle(java.lang.String) 
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets tab icon.
     * @param mIcon Tab icon. 
     * @see #getIcon() 
     */
    public void setIcon(Icon mIcon) {
        this.icon = mIcon;
    }

    /**
     * Returns tab icon.
     * @return Tab icon.
     * @see #setIcon(javax.swing.Icon) 
     */
    public Icon getIcon() {
        return this.icon;
    }

    /**
     * Sets associated component.
     * <p>
     * This component is displayed when this tab is selected in a tabbed pane. 
     * @param mComponent An associated component.
     * @see #getComponent() 
     */
    public void setComponent(Component mComponent) {
        this.component = mComponent;
    }

    /**
     * Returns the associated component.
     *  <p>
     * This component is displayed when this tab is selected in a tabbed pane. 
     * @return Associated component.
     * @see #setComponent(java.awt.Component) 
     */
    public Component getComponent() {
        return this.component;
    }

    /**
     * Sets tab tooltip.
     * @param mTip Tab tooltip.
     * @see #getTip() 
     */
    public void setTip(String mTip) {
        this.tip = mTip;
    }

    /**
     * Returns tab tooltip.
     * @return Tab tooltip.
     * @see #setTip(java.lang.String) 
     */
    public String getTip() {
        return this.tip;
    }

    /**
     * Returns Underlying tab component.
     * <P>
     * This component is the display of this tab.
     * @return Underlying tab component.
     * @see #setTabComponent(java.awt.Component) 
     */
    public Component getTabComponent() {
        return tabComponent;
    }

    /**
     * Sets underlying tab component.
     * <P>
     * This component is the display of this tab.
     * @param tabComponent A tab component.
     * @see #getTabComponent() 
     */
    public void setTabComponent(Component tabComponent) {
        this.tabComponent = tabComponent;
    }
}
