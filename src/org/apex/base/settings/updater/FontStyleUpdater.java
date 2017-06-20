/*
 * FontStyleUpdater.java
 * Created on 27 Oct, 2007, 8:30:14 PM 
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
import org.apex.base.settings.FontStyleConfiguration;
import java.util.Properties;

/**
 * Stores font style configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class FontStyleUpdater extends ConfigurationUpdateSupport {

    /**
     * Font style configuration.
     */
    private FontStyleConfiguration fontStyleConfig;
    /**
     * Cloned font style configuration.
     */
    private FontStyleConfiguration clonedFontStyleConfig;

    /**
     * Creates a new instance of {@code FontStyleUpdater} using specified
     * font style configuration and cloned font style configuration.
     * @param fontStyleConfig Font style configuration.
     * @param clonedFontStyleConfig Cloned font style configuration.
     */
    public FontStyleUpdater(FontStyleConfiguration fontStyleConfig,
                            FontStyleConfiguration clonedFontStyleConfig) {
        this.fontStyleConfig = fontStyleConfig;
        this.clonedFontStyleConfig = clonedFontStyleConfig;
    }

    @Override
    public void update(Properties properties) {
        this.fontStyleConfig.updateFromClone(this.clonedFontStyleConfig);
        if (properties == null) {
            properties = loadConfigProperties(this.fontStyleConfig);
        }
        properties.setProperty(FONT_FAMILY, this.fontStyleConfig.getName());
        properties.setProperty(FONT_STYLE, this.fontStyleConfig.getStyle());
        properties.setProperty(FONT_SIZE,
                String.valueOf(this.fontStyleConfig.getSize()));

        storeConfigProperties(fontStyleConfig, properties);
    }
}
