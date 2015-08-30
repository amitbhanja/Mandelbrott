/*
 * TK1 - Exercise
 * (c)2011 Telecooperation Dept. - Darmstadt University of Technology
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation;
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
 *  ALL LIABILITY, INCLUDING LIABILITY FOR INFRINGEMENT OF ANY PATENTS,
 *  COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS, RELATING TO USE OF THIS
 *  SOFTWARE IS DISCLAIMED.
 *
 *  Authors
 *  Erwin Aitenbichler
 *  Dirk Schnelle
 *  Sebastian Döweling
 */
package com.tuple.mandelbrot;

import javax.swing.JComponent;
import java.awt.Rectangle;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Canvas to display the Mandelbrot image.
 *
 * @author Erwin Aitenbichler
 * @author Dirk Schnelle
 * @author Sebastian Döweling
 */

@SuppressWarnings("serial")
public class MandelCanvas
        extends JComponent {
    /** The current image. */
    private Image image;

    /**
     * Constructs a new object.
     */
    public MandelCanvas() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void paintComponent(Graphics g) {
        Rectangle r = getBounds();
        g.setColor(Color.white);
        g.fillRect(r.x, r.y, r.width, r.height);
        if (image != null) {
            g.drawImage(image, 0, 0, null);
        }
    }

    /**
     * Sets the image to draw and shows it.
     * @param img The image to show.
     */
    public synchronized void setImage(Image img) {
        if (image != null) {
            image.flush();
        }

        image = img;

        repaint();
    }
}
