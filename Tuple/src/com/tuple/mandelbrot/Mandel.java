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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 * Show the Mandelbrot in a frame.
 *
 * @author Erwin Aitenbichler
 * @author Dirk Schnelle
 * @author Sebastian Döweling
 */
@SuppressWarnings("serial")
public class Mandel
        extends JFrame {
	
	/** The Mandelbrot container. */
    private MandelCanvas canvas;

    /**
     * Constructs a new object.
     */
    public Mandel() {
        super("Mandel");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        canvas = new MandelCanvas();

        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Mandel");
        JMenuItem btn;
        menu.add(btn = new JMenuItem("Refresh"));
        btn.addActionListener(new CalcAction());
        menu.add(new JSeparator());
        menu.add(btn = new JMenuItem("Exit"));
        btn.addActionListener(new ExitAction());
        menubar.add(menu);

        setJMenuBar(menubar);
        setSize(500, 500);
        Container content = getContentPane();
        content.setLayout(new BorderLayout());
        content.add("Center", canvas);
    }

    class CalcAction
            implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MandelCalculator calculator = new MandelCalculator(canvas, -2.1,
                    -1.25, 1.1, 1.25,
                    1000);
            Thread thread = new Thread(calculator);
            thread.start();
        }
    }


    class ExitAction
            implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }


    /**
     * Main method.
     * @param args Command line arguments. None expected.
     */
    public static void main(String[] args) {
        Mandel mandel = new Mandel();

        mandel.setVisible(true);
    }
}

// ------------------------------------------------------------------
