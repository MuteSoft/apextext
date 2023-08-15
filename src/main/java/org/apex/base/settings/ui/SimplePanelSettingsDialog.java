/**
 */
package org.apex.base.settings.ui;

import java.awt.Window;
import org.apex.base.component.SimplePanelDialog;
import org.apex.base.constant.MenuConstants;
import org.apex.base.core.MenuManager;
import org.apex.base.menu.SettingsMenu;

/**
 *
 * @author msaha
 */
public abstract class SimplePanelSettingsDialog extends SimplePanelDialog {

    @Override
    public Window getParentWindow() {
        return ((SettingsMenu) MenuManager.getMenuById(
                MenuConstants.PREFERENCES)).getDialogWindow();
    }
}
