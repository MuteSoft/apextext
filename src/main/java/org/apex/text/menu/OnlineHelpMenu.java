/*
 * OnlineHelpMenu.java 
 * Created on 14 Aug, 2010, 5:28:30 PM
 *
 * Copyright (C) 2010 Mrityunjoy Saha
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
package org.apex.text.menu;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;
import org.apex.base.logging.Logger;
import org.apex.base.menu.UILessMenu;
import org.apex.base.menu.ViewDocumentInBrowserMenu;
import org.apex.base.util.StringUtil;
import org.apex.text.constant.TextEditorKeyConstants;

/**
 * Opens online user help related URLs such as help document, bug reporting
 * web page, feature request web page etc.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class OnlineHelpMenu extends UILessMenu {

    /**
     * Online help URI
     */
    private static URI onlineHelpURI;
    /**
     * Report bug URI.
     */
    private static URI reportBugURI;
    /**
     * Request feature URI.
     */
    private static URI requestFeatureURI;

    /**
     * Creates a new instance of {@code OnlineHelpMenu}.
     */
    public OnlineHelpMenu() {
        try {
            onlineHelpURI = new URL(TextEditorKeyConstants.ONLINE_HELP_URL).
                    toURI();
            reportBugURI = new URL(TextEditorKeyConstants.REPORT_BUG_URL).toURI();
            requestFeatureURI = new URL(TextEditorKeyConstants.REQUEST_FEATURE_URL).
                    toURI();
        } catch (MalformedURLException ex) {
            Logger.logError(
                    "Could not form online help URI:MalformedURLException", ex);
        } catch (URISyntaxException use) {
            Logger.logError("Could not form online help URI:URISyntaxException",
                    use);
        }
    }

    @Override
    protected void execute(InputParams in, OutputParams out) {
        URI selectedtTargetURI = getTargetURI((String) in.get("MENU_ID"));
        ((ViewDocumentInBrowserMenu) MenuManager.getMenuById(
                MenuConstants.VIEW_IN_WEB_BROWSER)).browse(selectedtTargetURI);
    }

    /**
     * Depending on the menu chosen from Help main menu, the corresponding URI
     * is returned.
     * @param menuId The menu id.
     * @return The URI corresponding to provided help menu.
     */
    private URI getTargetURI(String menuId) {
        if (StringUtil.isNullOrEmpty(menuId)) {
            return null;
        }
        if (menuId.equalsIgnoreCase(MenuConstants.ONLINE_HELP)) {
            return onlineHelpURI;
        } else if (menuId.equalsIgnoreCase(MenuConstants.REPORT_BUG)) {
            return reportBugURI;
        } else if (menuId.equalsIgnoreCase(MenuConstants.REQUEST_FEATURE)) {
            return requestFeatureURI;
        }
        return null;
    }

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    @Override
    protected void postProcess(InputParams in, OutputParams out) {
    }
}
