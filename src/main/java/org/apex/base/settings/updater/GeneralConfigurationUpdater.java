/*
 * GeneralConfigurationUpdater.java
 * Created on 27 Oct, 2007, 8:30:32 PM
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
import org.apex.base.settings.GeneralConfiguration;
import java.util.Properties;

/**
 * Stores general configuration to external file.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class GeneralConfigurationUpdater extends ConfigurationUpdateSupport {

    /**
     * General configuration.
     */
    private GeneralConfiguration generalConfig;
    /**
     * Cloned general configuration.
     */
    private GeneralConfiguration clonedGeneralConfig;

    /**
     * Creates a new instance of {@code GeneralConfigurationUpdater} using specified
     * general configuration and cloned general configuration.
     * @param generalConfig General configuration.
     * @param clonedGeneralConfig Cloned general configuration.
     */
    public GeneralConfigurationUpdater(GeneralConfiguration generalConfig,
            GeneralConfiguration clonedGeneralConfig) {
        this.generalConfig = generalConfig;
        this.clonedGeneralConfig = clonedGeneralConfig;
    }

    @Override
    public void update(Properties properties) {
        if (!this.generalConfig.getGeneral().equals(this.clonedGeneralConfig.getGeneral())) {
            GeneralSectionUpdater genSecUpdater =
                    new GeneralSectionUpdater(generalConfig.getGeneral(),
                    clonedGeneralConfig.getGeneral());
            genSecUpdater.update((Properties) null);
        }

        if (!this.generalConfig.getDocTypes().equals(this.clonedGeneralConfig.getDocTypes())) {
            DocumentTypesUpdater docTypesUpdater =
                    new DocumentTypesUpdater(generalConfig.getDocTypes(),
                    clonedGeneralConfig.getDocTypes());
            docTypesUpdater.update((Properties) null);
        }
    }
}
