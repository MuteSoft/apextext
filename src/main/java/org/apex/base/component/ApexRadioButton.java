package org.apex.base.component;

import javax.swing.JRadioButton;

/**
 * An extension of {@code JRadioButton}. It provides handy constructors and methods
 * to easily deal with radio button.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class ApexRadioButton extends JRadioButton {

    /**
     * Creates a new default instance of {@code ApexRadioButton}.
     */
    public ApexRadioButton() {
        this("");
    }

    /**
     * Creates a new instance of {@code ApexRadioButton} with given text.
     * @param text The text to be displayed.
     */
    public ApexRadioButton(String text) {
        super(text);
    }
}
