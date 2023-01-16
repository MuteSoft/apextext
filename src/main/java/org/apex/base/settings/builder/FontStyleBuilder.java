/*
 * FontStyleBuilder.java
 * Created on 15 July, 2007, 1:46 AM
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
package org.apex.base.settings.builder;

import org.apex.base.data.ConfigurationBuilderSupport;
import org.apex.base.logging.Logger;
import org.apex.base.data.Configuration;
import org.apex.base.settings.FontStyleConfiguration;
import org.apex.base.util.StringUtil;
import java.util.Properties;

/**
 * Builds font style configuration object from file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class FontStyleBuilder extends ConfigurationBuilderSupport {

    /**
     * Creates a new instance of {@code FontStyleBuilder}.
     */
    public FontStyleBuilder() {
    }

    @Override
    public Configuration build(Properties properties) {
        Logger.logInfo("Loading font style configuration.");
        FontStyleConfiguration fontStyleConfig =
                new FontStyleConfiguration();
        if (properties == null) {
            properties = loadConfigProperties(fontStyleConfig);
        }
        fontStyleConfig.setName(properties.getProperty(FONT_FAMILY));
        fontStyleConfig.setStyle(properties.getProperty(FONT_STYLE));
        fontStyleConfig.setSize(StringUtil.getInt(properties.getProperty(
                FONT_SIZE)));
        return fontStyleConfig;
    }
}
