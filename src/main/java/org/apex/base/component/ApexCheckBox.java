package org.apex.base.component;

import javax.swing.JCheckBox;

/**
 * An extension of {@code JCheckBox}. It provides handy constructors and methods
 * to easily deal with checkbox.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class ApexCheckBox extends JCheckBox {

    /**
     * Creates a new default instance of {@code ApexCheckBox}.
     */
    public ApexCheckBox() {
        this("");
    }

    /**
     * Creates a new instance of {@code ApexCheckBox} with given text.
     * @param text The text to be displayed.
     */
    public ApexCheckBox(String text) {
        super(text);
    }
}
