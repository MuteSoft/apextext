/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apex.base.menu;

import java.util.Vector;
import org.apex.base.data.Fonts;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;

/**
 *
 * @author msaha
 */
public class ZoomInMenu extends UILessMenu {

    @Override
    protected void execute(InputParams in, OutputParams out) {
        // Get current font size
        int fontSize = getContext().getConfiguration().getStyleConfig().getFontStyle().getSize();
        // Get all font sizes
        Vector fontSizes = Fonts.getFontSizes();
        // Update font size to next level
        int fontIndex = fontSizes.indexOf(fontSize);
        if (fontIndex + 1 < fontSizes.size()) {
            // TODO change the font
            //getContext().getConfiguration().getStyleConfig().getFontStyle().updateFromClone();
            // do this if font change is successful
            if (fontIndex + 2 >= fontSizes.size()) {
                // @TODO Reached end, disable the icon
            }
        }

    }

    @Override
    protected void preProcess(InputParams in, OutputParams out) {
    }

    @Override
    protected void postProcess(InputParams in, OutputParams out) {
    }
}
