/*
 * TaskProgressIndicator.java 
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
package org.apex.base.component;

import org.apex.base.logging.Logger;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * A progress indicator. This component is invoked to show the progress
 * and also to prevent user input when a long running task is executed. With
 * multiple bars it forms a circle and animates the same.
 * @author Mrityunjoy Saha
 * @version 1.0
 * @since Apex 1.0
 */
public class TaskProgressIndicator extends JComponent implements MouseListener {

    /**
     * Bars in progress indicator circle. 
     */
    protected Area[] ticker = null;
    /**
     * The animation thread.
     */
    protected Thread animation = null;
    /**
     * A boolean that indicates whether or not the progress indicator started.
     */
    protected boolean started = false;
    /**
     * Alpha component of bar color.
     */
    protected int alphaLevel = 0;
    /**
     * Periodical delay of animation.
     */
    protected int rampDelay = 300;
    /**
     * Alpha component of bar color.
     */
    protected float shield = 0.70f;
    /**
     * The message to be displayed in progress indicator.
     */
    protected String text = "";
    /**
     * Numbers of bars in progress indicator circle.
     */
    protected int barsCount = 14;
    /**
     * FPS value.
     */
    protected float fps = 15.0f;
    /**
     * Rendering hints.
     */
    protected RenderingHints hints = null;

    /**
     * Creates a new instance of {@code TaskProgressIndicator}.
     */
    public TaskProgressIndicator() {
        this("");
    }

    /**
     * Creates a new instance of {@code TaskProgressIndicator} using given progress
     * message.
     * @param text The progress message.
     */
    public TaskProgressIndicator(String text) {
        this(text, 14);
    }

    /**
     * Creates a new instance of {@code TaskProgressIndicator} using
     * specified progress message and bars count.
     * @param text The progress message.
     * @param barsCount Number of bars in progress indicator circle.
     */
    public TaskProgressIndicator(String text, int barsCount) {
        this(text, barsCount, 0.70f);
    }

    /**
     * Creates a new instance of {@code TaskProgressIndicator} using
     * specified progress message, bars count and alpha component of bar color.
     * @param text The progress message.
     * @param barsCount Number of bars in progress indicator circle.
     * @param shield The alpha component of bar color.
     */
    public TaskProgressIndicator(String text, int barsCount, float shield) {
        this(text, barsCount, shield, 15.0f);
    }

    /**
     * Creates a new instance of {@code TaskProgressIndicator} using
     * specified progress message, bars count, alpha component of bar color
     * and FPS value.
     * @param text The progress message.
     * @param barsCount Number of bars in progress indicator circle.
     * @param shield The alpha component of bar color.
     * @param fps FPS value.
     */
    public TaskProgressIndicator(String text, int barsCount, float shield,
                                 float fps) {
        this(text, barsCount, shield, fps, 300);
    }

    /**
     * Creates a new instance of {@code TaskProgressIndicator} using
     * specified progress message, bars count, alpha component of bar color,
     * FPS value and periodical delay of animation.
     * @param text The progress message.
     * @param barsCount Number of bars in progress indicator circle.
     * @param shield The alpha component of bar color.
     * @param fps FPS value.
     * @param rampDelay Periodical delay of animation.
     */
    public TaskProgressIndicator(String text, int barsCount, float shield,
                                 float fps, int rampDelay) {
        this.text = text;
        this.rampDelay = rampDelay >= 0
                ? rampDelay
                : 0;
        this.shield = shield >= 0.0f
                ? shield
                : 0.0f;
        this.fps = fps > 0.0f
                ? fps
                : 15.0f;
        this.barsCount = barsCount > 0
                ? barsCount
                : 14;

        this.hints = new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        this.hints.put(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        this.hints.put(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    /**
     * Sets the progress message.
     * @param text The progress message.
     * @see #getText() 
     */
    public void setText(String text) {
        repaint();
        this.text = text;
    }

    /**
     * Returns the progress message.
     * @return The progress message.
     * @see #setText(java.lang.String) 
     */
    public String getText() {
        return text;
    }

    /**
     * Starts the animation and makes the progress indicator visible.
     */
    public void start() {
//        addMouseListener(this);
//        ticker = buildTicker();
//        makeVisible(true);
//        animation = new Thread(new Animator(true));
//        animation.start();
    }

    /**
     * Stops the animation and hides the progress indicator.
     */
    public void stop() {
//        if (animation != null) {
//            animation.interrupt();
//            animation = null;
//            animation = new Thread(new Animator(false));
//            animation.start();
//        }
    }

    /**
     * Interrupts the animation and hides the progress indicator.
     */
    public void interrupt() {
//        if (animation != null) {
//            animation.interrupt();
//            animation = null;
//            removeMouseListener(this);
//            makeVisible(false);
//        }
    }

    /**
     * Paints the progress indicator.
     * @param g The graphics context.
     */
    @Override
    public void paintComponent(Graphics g) {
        if (started) {
            int width = getWidth();

            double maxY = 0.0;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHints(hints);

            g2.setColor(new Color(255, 255, 255, (int) (alphaLevel * shield)));
            g2.fillRect(0, 0, getWidth(), getHeight());

            for (int i = 0; i < ticker.length; i++) {
                int channel = 224 - 128 / (i + 1);
                g2.setColor(new Color(channel, channel, channel, alphaLevel));
                g2.fill(ticker[i]);

                Rectangle2D bounds = ticker[i].getBounds2D();
                if (bounds.getMaxY() > maxY) {
                    maxY = bounds.getMaxY();
                }
            }

            if (text != null && text.length() > 0) {
                FontRenderContext context = g2.getFontRenderContext();
                TextLayout layout = new TextLayout(text, getFont(), context);
                Rectangle2D bounds = layout.getBounds();
                g2.setColor(getForeground());
                layout.draw(g2, (float) (width - bounds.getWidth()) / 2,
                        (float) (maxY + layout.getLeading() + 2 * layout.
                        getAscent()));
            }
        }
    }

    /**
     * Builds bars of animated circle.
     * @return An array of bars of animated circle.
     */
    private Area[] buildTicker() {
        Area[] tickers = new Area[barsCount];
        Point2D.Double center = new Point2D.Double((double) getWidth() / 2,
                (double) getHeight() / 2);
        double fixedAngle = 2.0 * Math.PI / ((double) barsCount);

        for (double i = 0.0; i < (double) barsCount; i++) {
            Area primitive = buildPrimitive();

            AffineTransform toCenter = AffineTransform.getTranslateInstance(
                    center.getX(), center.getY());
            AffineTransform toBorder =
                    AffineTransform.getTranslateInstance(45.0, -6.0);
            AffineTransform toCircle = AffineTransform.getRotateInstance(-i *
                    fixedAngle, center.getX(), center.getY());

            AffineTransform toWheel = new AffineTransform();
            toWheel.concatenate(toCenter);
            toWheel.concatenate(toBorder);

            primitive.transform(toWheel);
            primitive.transform(toCircle);

            tickers[(int) i] = primitive;
        }

        return tickers;
    }

    /**
     * Builds a default bar to be displayed in animated circle.
     * @return A bar to be displayed in animated circle.
     */
    private Area buildPrimitive() {
        Rectangle2D.Double body = new Rectangle2D.Double(6, 0, 30, 12);
        Ellipse2D.Double head = new Ellipse2D.Double(0, 0, 12, 12);
        Ellipse2D.Double tail = new Ellipse2D.Double(30, 0, 12, 12);

        Area tick = new Area(body);
        tick.add(new Area(head));
        tick.add(new Area(tail));

        return tick;
    }

    /**
     * The animator of progress indicator. It animates bars of a circle.
     */
    protected class Animator implements Runnable {

        /**
         * Rampup indicator.
         */
        private boolean rampUp = true;

        /**
         * Creates a new instance of {@code Animator} using specified rampup indicator.
         * @param rampUp Rampup indicator.
         */
        protected Animator(boolean rampUp) {
            this.rampUp = rampUp;
        }

        public void run() {
            Point2D.Double center = new Point2D.Double((double) getWidth() / 2,
                    (double) getHeight() / 2);
            double fixedIncrement = 2.0 * Math.PI / ((double) barsCount);
            AffineTransform toCircle = AffineTransform.getRotateInstance(
                    fixedIncrement, center.getX(), center.getY());

            long start = System.currentTimeMillis();
            if (rampDelay == 0) {
                alphaLevel = rampUp
                        ? 255
                        : 0;
            }
            started = true;
            boolean inRamp = rampUp;

            while (!Thread.interrupted()) {
                if (!inRamp) {
                    for (int i = 0; i < ticker.length; i++) {
                        ticker[i].transform(
                                toCircle);
                    }
                }
                doRepaint();
                if (rampUp) {
                    if (alphaLevel < 255) {
                        alphaLevel = (int) (255 * (System.currentTimeMillis() -
                                start) / rampDelay);
                        if (alphaLevel >= 255) {
                            alphaLevel = 255;
                            inRamp = false;
                        }
                    }
                } else if (alphaLevel > 0) {
                    alphaLevel = (int) (255 - (255 *
                            (System.currentTimeMillis() - start) / rampDelay));
                    if (alphaLevel <= 0) {
                        alphaLevel = 0;
                        break;
                    }
                }
                //doRepaint();
                try {
                    Thread.sleep(inRamp
                            ? 10
                            : (int) (1000 / fps));
                } catch (InterruptedException ie) {
                    break;
                }
                Thread.yield();
            }

            if (!rampUp) {
                started = false;
                doRepaint();
                makeVisible(false);
                removeMouseListener(TaskProgressIndicator.this);
            }
        }
    }

    /**
     * Repaints the progress indicator. It calls repaint() method to repaint this
     * component.
     */
    private void doRepaint() {
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                repaint();
            } else {

                SwingUtilities.invokeAndWait(new Runnable() {

                    public void run() {
                        repaint();
                    }
                });

            }
        } catch (Exception ex) {
            // Avoid logging and return quickly.
            //Logger.logWarning("Could not repaint the progress indicator.", ex);
        }
    }

    /**
     * Changes the visibility of progress indicator.
     * @param boolFlag A boolean that indicates whether or make the progress indicator visible or hide the
     *              the progress indicator.
     */
    private void makeVisible(final boolean boolFlag) {
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                setVisible(boolFlag);
            } else {

                SwingUtilities.invokeAndWait(new Runnable() {

                    public void run() {
                        setVisible(boolFlag);
                    }
                });

            }
        } catch (Exception ex) {
            Logger.logWarning(
                    "Could not change visibility of progress indicator to: " +
                    boolFlag, ex);
        }
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     * @param e The mouse event.
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     * @param e The mouse event.
     */
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been released on a component.
     * @param e The mouse event.
     */
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     * @param e The mouse event.
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component.
     * @param e The mouse event.
     */
    public void mouseExited(MouseEvent e) {
    }
}