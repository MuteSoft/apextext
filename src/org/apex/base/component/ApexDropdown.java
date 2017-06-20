package org.apex.base.component;

import java.util.Vector;

import javax.swing.JComboBox;

/**
 * An extension of {@code JComboBox}. It provides handy constructors and methods
 * to easily deal with dropdown.
 * @author mrityunjoy_saha
 * @version 1.0
 * @since Apex 1.2
 */
public class ApexDropdown extends JComboBox {

    /**
     * Creates a new default instance of {@code ApexDropdown}.
     */
    public ApexDropdown() {
        this(new Vector());
    }

    /**
     * Creates a new instance of {@code ApexDropdown} with given list of items.
     * @param list A list of items to be displayed in the dropdown.
     */
    public ApexDropdown(Vector list) {
        super(list);
    }
}
