/*
 * GeneralSectionConfiguration.java
 * Created on 14 July, 2007, 12:51 PM
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

import org.apex.base.constant.EditorKeyConstants;
import org.apex.base.util.StringUtil;
import java.awt.Color;

/**
 * A configuration object for general section. General section includes
 * settings of cursor color, text indentation, view line number display area option etc.
 * @author Mrityunjoy Saha
 * @version 1.3
 * @since Apex 1.0
 */
public class GeneralSectionConfiguration extends AbstractConfiguration {

    /**
     * Default extension for temporary documents.
     */
    private String defaultExtension = "";
    /**
     * Caret width. Default caret width is 2.
     */
    private int caretWidth = 2;
    /**
     * Caret color.
     */
    private Color caretColor = Color.BLUE;
    /**
     * A boolean that indicates whether or not line number display area should be visible.
     */
    private boolean viewLineNumber = true;
    /**
     * The tab size in editor.
     */
    private int tabSize = 4;
    /**
     * The indentation size in editor.
     */
    private int indentationSize = 4;
    /**
     * A boolean that indicates whether or not right margin of editor should be visible or not.
     */
    private boolean viewRightMargin = true;
    /**
     * The right margin size.
     */
    private int rightMargin = 80;
    /**
     * The right margin color.
     */
    private Color rightMarginColor = Color.ORANGE;
    /**
     * A boolean that indicates whether or not status bar of editor should be visible or not.
     */
    private boolean viewStatusBar = true;
    /**
     * A boolean that indicates whether or not document selector of editor should be visible or not.
     */
    private boolean viewDocumentSelector = false;
    /**
     * Maximum recent files count.
     */
    private int maxRecentFilesCount = EditorKeyConstants.MAX_RECENT_FILES;
    /**
     * A boolean that indicates whether or not consoles are reused.
     */
    private boolean reuseConsole = true;

    /**
     * Creates a new instance of {@code GeneralSectionConfiguration}.
     */
    public GeneralSectionConfiguration() {
    }

    /**
     * Returns the default extension for temporary documents.
     * @return The default extension for temporary documents.
     * @see #setDefaultExtension(java.lang.String) 
     */
    public String getDefaultExtension() {
        return defaultExtension;
    }

    /**
     * Sets the default extension for temporary documents.
     * @param extension A document extension.
     * @see #getDefaultExtension() 
     */
    public void setDefaultExtension(String extension) {
        if (!StringUtil.isNullOrEmpty(extension)) {
            this.defaultExtension = extension;
        } else {
            this.defaultExtension = "";
        }
    }

    /**
     * Returns the width of caret.
     * @return The width of caret.
     * @see #setCaretWidth(int) 
     */
    public int getCaretWidth() {
        return caretWidth;
    }

    /**
     * Sets the width of caret.
     * @param caretWidth The width of caret.
     * @see #getCaretWidth() 
     */
    public void setCaretWidth(int caretWidth) {
        if (caretWidth > 0) {
            this.caretWidth = caretWidth;
        }
    }

    /**
     * Returns the color of caret.
     * @return The color of caret.
     * @see #setCaretColor(java.awt.Color) 
     */
    public Color getCaretColor() {
        return caretColor;
    }

    /**
     * Sets the color of caret.
     * @param caretColor The color of caret.
     * @see #getCaretColor() 
     */
    public void setCaretColor(Color caretColor) {
        if (caretColor != null) {
            this.caretColor = caretColor;
        }
    }

    @Override
    public String toString() {
        return "defaultExtension: " + defaultExtension + "^caretWidth: "
                + caretWidth
                + "^caretColor: " + caretColor
                + "^viewLineNumber: "
                + viewLineNumber + "^tabSize: " + tabSize + "^indentationSize: "
                + indentationSize + "^viewRightMargin: " + viewRightMargin + "^rightMargin: " + rightMargin
                + "^rightMarginColor: " + rightMarginColor + "^viewDocumentSelector: " + viewDocumentSelector
                + "^viewStatusBar: " + viewStatusBar + "^maxRecentFilesCount: " + maxRecentFilesCount
                + "^reuseConsole: " + reuseConsole;
    }

    @Override
    public Object clone() {
        GeneralSectionConfiguration clonedConfig = null;
        try {
            clonedConfig =
                    (GeneralSectionConfiguration) super.clone();
        } catch (CloneNotSupportedException ex) {
        }
        return clonedConfig;
    }

    public void updateFromClone(Object clonedObject) {
        GeneralSectionConfiguration clonedConfig =
                (GeneralSectionConfiguration) clonedObject;
        this.setDefaultExtension(clonedConfig.getDefaultExtension());
        this.setCaretColor(clonedConfig.getCaretColor());
        this.setCaretWidth(clonedConfig.getCaretWidth());
        this.setViewLineNumber(clonedConfig.isViewLineNumber());
        this.setTabSize(clonedConfig.getTabSize());
        this.setIndentationSize(clonedConfig.getIndentationSize());
        this.setViewRightMargin(clonedConfig.isViewRightMargin());
        this.setRightMargin(clonedConfig.getRightMargin());
        this.setRightMarginColor(clonedConfig.getRightMarginColor());
        this.setViewDocumentSelector(clonedConfig.isViewDocumentSelector());
        this.setViewStatusBar(clonedConfig.isViewStatusBar());
        this.setMaxRecentFilesCount(clonedConfig.getMaxRecentFilesCount());
        this.setReuseConsole(clonedConfig.isReuseConsole());
        fireGeneralSectionConfigurationChanged(null);
    }

    @Override
    public boolean equals(Object clonedObject) {
        boolean value = false;
        if (clonedObject != null
                && clonedObject instanceof GeneralSectionConfiguration) {
            GeneralSectionConfiguration clonedConfig =
                    (GeneralSectionConfiguration) clonedObject;
            value = !isChanged(this.getDefaultExtension(), clonedConfig.
                    getDefaultExtension())
                    && !isChanged(this.getCaretColor(),
                    clonedConfig.getCaretColor())
                    && !isChanged(this.getCaretWidth(),
                    clonedConfig.getCaretWidth())
                    && !isChanged(this.isViewLineNumber(),
                    clonedConfig.isViewLineNumber())
                    && !isChanged(this.getTabSize(),
                    clonedConfig.getTabSize())
                    && !isChanged(this.getIndentationSize(),
                    clonedConfig.getIndentationSize())
                    && !isChanged(this.isViewRightMargin(),
                    clonedConfig.isViewRightMargin())
                    && !isChanged(this.getRightMargin(),
                    clonedConfig.getRightMargin())
                    && !isChanged(this.getRightMarginColor(),
                    clonedConfig.getRightMarginColor())
                    && !isChanged(this.isViewDocumentSelector(),
                    clonedConfig.isViewDocumentSelector())
                    && !isChanged(this.isViewStatusBar(),
                    clonedConfig.isViewStatusBar())
                    && !isChanged(this.getMaxRecentFilesCount(),
                    clonedConfig.getMaxRecentFilesCount())
                    && !isChanged(this.isReuseConsole(),
                    clonedConfig.isReuseConsole());
        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.defaultExtension != null
                ? this.defaultExtension.hashCode()
                : 0);
        hash = 79 * hash + this.caretWidth;
        hash = 79 * hash
                + (this.caretColor != null
                ? this.caretColor.hashCode()
                : 0);
        hash = 79 * hash + (this.viewLineNumber
                ? 1
                : 0);
        hash = 79 * hash + this.tabSize;
        hash = 79 * hash + this.indentationSize;
        hash = 79 * hash + (this.viewRightMargin
                ? 1
                : 0);
        hash = 79 * hash + this.rightMargin;
        hash = 79 * hash
                + (this.rightMarginColor != null
                ? this.rightMarginColor.hashCode()
                : 0);
        hash = 79 * hash + (this.viewDocumentSelector
                ? 1
                : 0);
        hash = 79 * hash + (this.viewStatusBar
                ? 1
                : 0);
        hash = 79 * hash + this.maxRecentFilesCount;
        hash = 79 * hash + (this.reuseConsole
                ? 1
                : 0);
        return hash;
    }

    /**
     * Returns a boolean that indicates whether or not line number
     * display area is displayed in editor by default.
     * @return {@code true} if line number display area is displayed by default;
     *               otherwise returns {@code false}.
     */
    public boolean isViewLineNumber() {
        return viewLineNumber;
    }

    /**
     * Sets a boolean that indicates whether or not line number
     * display area should be displayed in editor by default.
     * @param viewLineNumber Line number display area visibility indicator.
     */
    public void setViewLineNumber(boolean viewLineNumber) {
        this.viewLineNumber = viewLineNumber;
    }

    /**
     * Returns the size of tab. It is number of spaces for a tab ('\t') character.
     * @return The size of tab.
     * @see #setTabSize(int) 
     */
    public int getTabSize() {
        return tabSize;
    }

    /**
     * Sets the size of tab.
     * @param tabSize The size of tab.
     * @see #getTabSize() 
     */
    public void setTabSize(int tabSize) {
        if (tabSize > 0) {
            this.tabSize = tabSize;
        }
    }

    /**
     * Returns the indentation size.
     * @return The indentation size.
     * @see #setIndentationSize(int) 
     */
    public int getIndentationSize() {
        return indentationSize;
    }

    /**
     * Sets the indentation size.
     * @param indentationSize The indentation size.
     * @see #getIndentationSize() 
     */
    public void setIndentationSize(int indentationSize) {
        if (indentationSize > 0) {
            this.indentationSize = indentationSize;
        }
    }

    public boolean isConfigurable() {
        return true;
    }

    public String getConfigFile() {
        return "GeneralSection";
    }

    public boolean isCacheRequired() {
        return true;
    }

    public boolean disposeIfCacheNotRequired() {
        this.defaultExtension = null;
        this.caretColor = null;
        this.caretWidth = 0;
        this.indentationSize = 0;
        this.tabSize = 0;
        this.viewLineNumber = false;
        this.viewRightMargin = false;
        this.rightMargin = 0;
        this.rightMarginColor = null;
        this.viewDocumentSelector = false;
        this.viewStatusBar = false;
        this.maxRecentFilesCount = 0;
        this.reuseConsole = false;
        return true;
    }

    public boolean isLeaf() {
        return true;
    }

    public void remove() {
        disposeIfCacheNotRequired();
    }

    /**
     * Returns the right margin size.
     * @return The right margin size.
     * @see #setRightMargin(int)
     */
    public int getRightMargin() {
        return rightMargin;
    }

    /**
     * Sets the right margin size.
     * @param rightMargin The right margin size.
     * @see #getRightMargin()
     */
    public void setRightMargin(int rightMargin) {
        if (rightMargin > 0) {
            this.rightMargin = rightMargin;
        }
    }

    /**
     * Returns the color of right margin.
     * @return The right margin color.
     * @see #setRightMarginColor(java.awt.Color)
     */
    public Color getRightMarginColor() {
        return rightMarginColor;
    }

    /**
     * Sets the color of right margin.
     * @param rightMarginColor The right margin color.
     * @see #getRightMarginColor()
     */
    public void setRightMarginColor(Color rightMarginColor) {
        if (rightMarginColor != null) {
            this.rightMarginColor = rightMarginColor;
        }
    }

    /**
     * Returns a boolean indicating whether or not right margin should be visible in text editor.
     * @return {@code true} if the right margin is visible; otherwise returns {@code false}.
     * @see #setViewRightMargin(boolean)
     */
    public boolean isViewRightMargin() {
        return viewRightMargin;
    }

    /**
     * Sets the right margin visibility indicator.
     * @param viewRightMargin A boolean for right margin visibility indiactor.
     * @see #isViewRightMargin()
     */
    public void setViewRightMargin(boolean viewRightMargin) {
        this.viewRightMargin = viewRightMargin;
    }

    /**
     * Returns a boolean indicating whether or not document selector should be visible in text editor.
     * @return {@code true} if the document selector is visible; otherwise returns {@code false}.
     * @see #setViewDocumentSelector(boolean)
     */
    public boolean isViewDocumentSelector() {
        return viewDocumentSelector;
    }

    /**
     * Sets the document selector visibility indicator.
     * @param viewDocumentSelector A boolean for document selector visibility indiactor.
     * @see #isViewDocumentSelector()
     */
    public void setViewDocumentSelector(boolean viewDocumentSelector) {
        this.viewDocumentSelector = viewDocumentSelector;
    }

    /**
     * Returns a boolean indicating whether or not status bar should be visible in text editor.
     * @return {@code true} if the status bar is visible; otherwise returns {@code false}.
     * @see #setViewStatusBar(boolean)
     */
    public boolean isViewStatusBar() {
        return viewStatusBar;
    }

    /**
     * Sets the status bar visibility indicator.
     * @param viewStatusBar A boolean for status bar visibility indiactor.
     * @see #isViewStatusBar()
     */
    public void setViewStatusBar(boolean viewStatusBar) {
        this.viewStatusBar = viewStatusBar;
    }

    /**
     * Returns the maximum number of recent files.
     * @return The maximum recent files allowed count.
     * @see #setMaxRecentFilesCount(int)
     */
    public int getMaxRecentFilesCount() {
        return maxRecentFilesCount;
    }

    /**
     * Sets the maximum recent files count.
     * @param maxRecentFilesCount The maximum recent files allowed count.
     * @see #getMaxRecentFilesCount()
     */
    public void setMaxRecentFilesCount(int maxRecentFilesCount) {
        if (maxRecentFilesCount > 0) {
            this.maxRecentFilesCount = maxRecentFilesCount;
        }
    }

    /**
     * Returns whether or not consoles are reusable.
     * @return {@code true} if the consoles should be reused; otherwise returns {@code false}.
     * @see #setReuseConsole(boolean)
     */
    public boolean isReuseConsole() {
        return reuseConsole;
    }

    /**
     * Sets the reusability indicator of console.
     * @param reuseConsole The console reusability indicator.
     * @see #isReuseConsole()
     */
    public void setReuseConsole(boolean reuseConsole) {
        this.reuseConsole = reuseConsole;
    }
}
