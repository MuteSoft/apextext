/*
 * StyleConfigurationUpdater.java
 * Created on 27 Oct, 2007, 8:31:53 PM
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
package org.apex.base.settings.updater;

import org.apex.base.data.ConfigurationUpdateSupport;
import org.apex.base.settings.StyleConfiguration;
import java.util.Properties;

/**
 * Stores style configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class StyleConfigurationUpdater extends ConfigurationUpdateSupport {

    /**
     * Style configuration.
     */
    private StyleConfiguration styleConfig;
    /**
     * Cloned style configuration.
     */
    private StyleConfiguration clonedStyleConfig;

    /**
     * Creates a new instance of {@code StyleConfigurationUpdater} using specified
     * style configuration and cloned style configuration.
     * @param styleConfig The style configuration.
     * @param clonedStyleConfig The cloned style configuration.
     */
    public StyleConfigurationUpdater(StyleConfiguration styleConfig,
                                     StyleConfiguration clonedStyleConfig) {
        this.styleConfig = styleConfig;
        this.clonedStyleConfig = clonedStyleConfig;
    }

    @Override
    public void update(Properties properties) {
        if (!this.styleConfig.getFontStyle().equals(this.clonedStyleConfig.
                getFontStyle())) {
            FontStyleUpdater fontStyleUpdater =
                    new FontStyleUpdater(styleConfig.getFontStyle(),
                    clonedStyleConfig.getFontStyle());
            fontStyleUpdater.update((Properties) null);
        }

        if (!this.styleConfig.getSyntaxStyle().equals(this.clonedStyleConfig.
                getSyntaxStyle())) {
            SyntaxStyleUpdater syntaxStyleUpdater =
                    new SyntaxStyleUpdater(styleConfig.getSyntaxStyle(),
                    clonedStyleConfig.getSyntaxStyle());
            syntaxStyleUpdater.update((Properties) null);
        }

        if (!this.styleConfig.getHighlightStyle().equals(this.clonedStyleConfig.
                getHighlightStyle())) {
            HighlightStyleUpdater highlightStyleUpdater =
                    new HighlightStyleUpdater(styleConfig.getHighlightStyle(),
                    clonedStyleConfig.getHighlightStyle());
            highlightStyleUpdater.update((Properties) null);
        }
    }
}
