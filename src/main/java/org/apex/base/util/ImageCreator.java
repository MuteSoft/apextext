/*
 * ImageCreator.java
 *
 * Created on December 29, 2006, 7:13 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.apex.base.util;

import org.apex.base.logging.Logger;
import java.net.URL;
import javax.swing.ImageIcon;
import org.apex.base.core.EditorBase;

/**
 * A utility class to locate and produce images and icons.
 * @author Mrityunjoy_Saha
 */
public class ImageCreator {

    /**
     * Creates a new instance of {@code ImageCreator}.
     */
    private ImageCreator() {
    }

    /**
     * Locates the image icon at specified {@code path} using given class reference
     * and a short description of the image.
     * Once the URL is resolved it creates an image icon and returns the same.
     * @param cl The class which is used as reference to locate the image icon.
     * @param path The pathname for the image.
     * @param description A brief textual description of the image.
     * @return An image icon. It returns {@code null} if given path can not be resolved
     *               or failed to load the image icon.
     */
    public static ImageIcon createImageIcon(Class cl, String path,
            String description) {
        if (StringUtil.isNullOrEmpty(path) || path.endsWith("null")) {
            return null;
        }
        try {
            URL imgURL = cl.getClassLoader().getResource(path);
            if (description != null) {
                return new ImageIcon(imgURL, description);
            } else {
                return new ImageIcon(imgURL);
            }
        } catch (Exception ex) {
            Logger.logError("Could not load image: " + path, ex);
            return null;
        }
    }

    /**
     * Locates the icon at specified path using editors base class reference.
     * @param path The pathname for the image.
     * @param description The short description of image, used as tool tip text.
     * @return The image icon.  It returns {@code null} if given path can not be resolved
     *               or failed to load the image icon.
     */
    public static ImageIcon createImageIcon(String path,
            String description) {
        return createImageIcon(EditorBase.getContext().getEditorProperties().
                getEditorBaseClass(),
                path, description);
    }

    /**
     * Locates the icon at specified path using editors base class reference.
     * @param path The pathname for the image.
     * @return The image icon.  It returns {@code null} if given path can not be resolved
     *               or failed to load the image icon.
     */
    public static ImageIcon createImageIcon(String path) {
        return createImageIcon(path, null);
    }

    /**
     * Locates the image icon at specified {@code path} using given class reference.
     * Once the URL is resolved it creates an image icon and returns the same.
     * @param cl The class which is used as reference to locate the image icon.
     * @param path The pathname for the image.
     * @return An image icon. It returns {@code null} if given path can not be resolved
     *               or failed to load the image icon.
     */
    public static ImageIcon createImageIcon(Class cl, String path) {
        return createImageIcon(cl, path, null);
    }
}
