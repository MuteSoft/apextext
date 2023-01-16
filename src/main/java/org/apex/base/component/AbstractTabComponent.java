/*
 * AbstractTabComponent.java
 * Created on 25 June, 2007, 12:36 AM
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
package org.apex.base.component;

import java.awt.Color;
import java.awt.Component;
import org.apex.base.data.AbstractDocument;
import org.apex.base.constant.CommonConstants;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.JPanel;
import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.core.EditorBase;
import org.apex.base.data.EditorContext;

/**
 * A base class to create the component that is responsible for rendering the
 * title and/or icon for a tab in {@code ApexTabbedPane}.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public abstract class AbstractTabComponent extends JPanel implements
        MouseListener {

    /**
     * An icon to be used for close button of tab.
     */
    protected static final Icon PASSIVE_CLOSE_ICON = new CloseIcon(Color.BLACK);
    /**
     * An icon to be displayed for closed button on mouse over.
     */
    protected static final Icon ACTIVE_CLOSE_ICON = new CloseIcon(Color.RED,
            true);
    /**
     * The container of tab.
     */
    private ApexTabbedPane pane;
    /**
     * Tab title.
     */
    protected ApexLabel tabTitle;
    /**
     * Tab icon.
     */
    private ApexLabel tabIcon;
    /**
     * The close button.
     */
    private ApexButton closeButton;
    /**
     * Icon for active close button.
     */
    private Icon activeCloseIcon;
    /**
     * Icon for close button.
     */
    private Icon passiveCloseIcon;

    /**
     * Returns the icon for active close  of tab.
     * @return The active close button icon.
     * @see #setActiveCloseIcon(javax.swing.Icon)
     */
    public Icon getActiveCloseIcon() {
        return activeCloseIcon;
    }

    /**
     * Sets the icon for active close button of tab.
     * @param activeCloseIcon An icon.
     * @see #getActiveCloseIcon()
     */
    public void setActiveCloseIcon(Icon activeCloseIcon) {
        this.activeCloseIcon = activeCloseIcon;
    }

    /**
     * Returns the icon for close button of tab.
     * @return The icon for close button of tab.
     * @see #setPassiveCloseicon(javax.swing.Icon)
     */
    public Icon getPassiveCloseicon() {
        return passiveCloseIcon;
    }

    /**
     * Sets the icon for close button of tab.
     * @param passiveCloseicon An icon.
     * @see #getPassiveCloseicon()
     */
    public void setPassiveCloseicon(Icon passiveCloseicon) {
        this.passiveCloseIcon = passiveCloseicon;
    }

    /**
     * Returns the close button displayed on this tab component.
     * @return The close button of the tab.
     * @see #setCloseButton(org.apex.base.component.ApexButton)
     */
    public ApexButton getCloseButton() {
        return closeButton;
    }

    /**
     * Sets the close button to be displayed on this tab component.
     * @param closeButton A button.
     * @see #getCloseButton()
     */
    public void setCloseButton(ApexButton closeButton) {
        this.closeButton = closeButton;
    }

    /**
     * Creates a new tab component using a document. Tab title and icon are
     * extracted from document.
     * @param pane The container of tab.
     * @param file The document.
     */
    public AbstractTabComponent(ApexTabbedPane pane, AbstractDocument file) {
        this(pane);
        if (file != null) {
            setTabTitle(file.getDisplayName());
            setTabIcon(file.getDisplayIcon());
            this.setToolTipText(file.getAbsolutePath());
        }
        addComponents();
    }

    /**
     * Creates a new tab component with a given title.
     * @param pane The container of tab.
     * @param title Tab title.
     * @param icon Tab icon.
     */
    public AbstractTabComponent(ApexTabbedPane pane, String title, Icon icon) {
        this(pane);
        setTabTitle(title);
        setTabIcon(icon);
        setToolTipText(title);
        addComponents();
    }

    /**
     * Creates a new default tab component.
     * @param pane The container of tab.
     */
    private AbstractTabComponent(ApexTabbedPane pane) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.pane = pane;
    }

    /**
     * Creates  and adds components to display area of tab component. 
     * <p>
     * Typically it adds an optional icon, mandatory title and an optional close button.
     */
    protected abstract void addComponents();

    /**
     * Returns the tab title.
     * @return The tab title.
     * @see #setTabTitle(java.lang.String)      
     */
    public ApexLabel getTabTitle() {
        return this.tabTitle;
    }

    /**
     * Sets tab title.
     * @param title Tab title.
     * @see #getTabTitle()
     */
    private void setTabTitle(String title) {
        if (this.tabTitle == null) {
            this.tabTitle = new ApexLabel();
        }
        this.tabTitle.setText(CommonConstants.TAB_TITLE_EXTRA_SPACE + title
                + CommonConstants.TAB_TITLE_EXTRA_SPACE);
    }

    /**
     * Sets tab icon.
     * @param icon An image icon.
     * @see #getTabIcon() 
     */
    private void setTabIcon(Icon icon) {
        if (this.tabIcon == null) {
            this.tabIcon = new ApexLabel("");
        }
        this.tabIcon.setIcon(icon);
    }

    /**
     * Returns the parent {@code ApexTabbedPane}.
     * @return The parent {@code ApexTabbedPane}.
     */
    public ApexTabbedPane getPane() {
        return this.pane;
    }

    /**
     * Returns the tab icon.
     * @return Tab icon.
     * @see #setTabIcon(javax.swing.Icon) 
     */
    protected ApexLabel getTabIcon() {
        return this.tabIcon;
    }

    /**
     * Updates tab title.
     * @param document The document from where title to be extracted.
     */
    public void updateTabTitle(AbstractDocument document) {
        if (this.tabTitle != null && this.tabTitle.isDisplayable()) {
            this.tabTitle.setText(CommonConstants.TAB_TITLE_EXTRA_SPACE
                    + document.getDisplayName()
                    + CommonConstants.TAB_TITLE_EXTRA_SPACE);
        }
        // Change tool tip text for both tab component and tab.
        this.setToolTipText(document.getAbsolutePath());
        this.pane.setToolTipTextAt(document.getIndex(),
                document.getAbsolutePath());
    }

    /**
     * Returns the index of this tab component in container tabbed pane.
     * @return The index of this tab component.
     */
    public int getIndex() {
        return getPane().indexOfTabComponent(this);
    }

    /**
     * Updates tab icon.
     * @param document The document from where icon to be extracted.
     */
    public void updateTabIcon(AbstractDocument document) {
        if (this.tabIcon != null && this.tabIcon.isDisplayable()) {
            this.tabIcon.setIcon(document.getDisplayIcon());
        }
    }

    /**
     * Updates tab title and tab icon.
     * @param document The document from where title and icon to be extracted.
     */
    public void updateTabTitleAndIcon(AbstractDocument document) {
        updateTabTitle(document);
        updateTabIcon(document);
    }

    /**
     * Returns the editor context.
     * @return The editor context.
     */
    protected EditorContext getContext() {
        return EditorBase.getContext();
    }

    /**
     * Invoked when a mouse closeButton has been pressed on a component.
     * @param e Mouse event.
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Invoked when a mouse closeButton has been released on a component.
     * @param e Mouse event.
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     * @param e Mouse event.
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     * @param e Mouse event.
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Invoked when the mouse is clicked on the component.
     * @param e Mouse event.
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * The close icon class.
     */
    private static class CloseIcon extends org.apex.base.component.ApexIcon {

        /**
         * Color.
         */
        private Color color;
        /**
         * Indicates whether or not to draw border.
         */
        private boolean border;
        /**
         * An instance of rounded rectangle which is used to draw rounded rectangles.
         */
        private static final RoundRectangle2D.Float roundedRectangle = new RoundRectangle2D.Float();

        /**
         * Creates a new instance of {@code CloseIcon} with given color and border property.
         * @param color The color to be used to paint the icon.
         * @param border A border painting indicator.
         */
        public CloseIcon(Color color, boolean border) {
            this.color = color;
            this.border = border;
        }

        /**
         * Creates a new instance of {@code CloseIcon} with given color.
         * @param color he color to be used to paint the icon.
         */
        public CloseIcon(Color color) {
            this(color, false);
        }

        @Override
        public int getIconHeight() {
            return 6;
        }

        @Override
        public int getIconWidth() {
            return 6;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(EditorKeyConstants.ICON_GRAPHICS_STROKE);
            g2d.draw(new Line2D.Double(x, y, x + getIconWidth(),
                    y + getIconHeight()));
            g2d.draw(new Line2D.Double(x + getIconWidth(), y, x,
                    y + getIconHeight()));
            if (border) {
//                roundedRectangle.setRoundRect(x, y,
//                        10, 10, 0, 0);
//                g2d.drawRect(x, y, 10,10);
//                //g2d.draw(roundedRectangle);
            }
            g2d.setStroke(EditorKeyConstants.DEFAULT_GRAPHICS_STROKE);
        }
    }
}
