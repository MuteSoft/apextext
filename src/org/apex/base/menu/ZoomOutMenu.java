package org.apex.base.menu;

import org.apex.base.data.Fonts;
import org.apex.base.data.InputParams;
import org.apex.base.data.OutputParams;

import java.util.Vector;

/**
 * Created by msaha on 13-Jun-17.
 */
public class ZoomOutMenu extends UILessMenu {

    @Override
    protected void execute(InputParams in, OutputParams out) {
        // Get current font size
        int fontSize = getContext().getConfiguration().getStyleConfig().getFontStyle().getSize();
        // Get all font sizes
        Vector fontSizes = Fonts.getFontSizes();
        // Update font size to next level
        int fontIndex = fontSizes.indexOf(fontSize);
        if (fontIndex - 1 > 0) {
            // TODO change the font
            //getContext().getConfiguration().getStyleConfig().getFontStyle().updateFromClone();
            // do this if font change is successful
            if (fontIndex - 2 <= 0) {
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
