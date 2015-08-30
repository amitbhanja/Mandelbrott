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

import java.awt.Image;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;
import java.io.Serializable;

import com.ibm.tspaces.Field;
import com.ibm.tspaces.Tuple;
import com.ibm.tspaces.TupleSpace;
import com.ibm.tspaces.TupleSpaceException;

/**
 * Calculator for a mandelbrot image.
 * 
 * @author Erwin Aitenbichler
 * @author Dirk Schnelle
 * @author Sebastian Döweling
 */
class MandelCalculator
        implements Runnable,Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Image data. */
    private int data[];

    /** Image producer to create an image from the data. */
    private MemoryImageSource mis;

    /** The canvas to display the image. */
    private MandelCanvas canvas;

    /** Width of the canvas. */
    private int width;

    /** Height of the canvas. */
    private int height;

    private double xstart;
    private double xend;
    private double ystart;
    private double yend;

    /** Maximum number of iterations. */
    private int maxiter;

    /**
     * Constructs a new object.
     * @param c The canvas to show the image.
     * @param x1 double
     * @param y1 double
     * @param x2 double
     * @param y2 double
     * @param mi int
     */
    public MandelCalculator(MandelCanvas c, double x1, double y1, double x2,
                           double y2, int mi) {
        xstart = x1;
        ystart = y1;
        xend = x2;
        yend = y2;

        maxiter = mi;

        canvas = c;

        width = canvas.getWidth();
        height = canvas.getHeight();

        data = new int[width * height];

        mis = new MemoryImageSource(width, height, generateColorModel(),
                                    data, 0, width);
    }


    /**
     * Working method.
     */
    public void run() {
        //int i = 0;
        TupleSpace space = null;
        try {
			space = new TupleSpace("Mandelbrot","localhost:8200");
			int color = 0;
			for(int i = 0;i < 2;i++)
			{
				color = 255/(i+1);
				Tuple task = new Tuple("Color",color);
				space.write(task);
			}
		} catch (TupleSpaceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        int i = 0;
        int j = 0;
        for (int y = 0; y < height; y+=2) {
          /* for (int x = 0; x < width; x++) {
                data[i++] = iterate(xstart + (xend - xstart) * x / (width - 1),
                                    ystart + (yend - ystart) * y / (height - 1))
                            % 256;
            }
            */
        	double y1 = ystart + (yend - ystart) * y / (height - 1);
        	double y2 = 0;
        	if(y+1 < height)
        	{
        		y2 = ystart + (yend - ystart) * (y+1) / (height - 1);
        	}
        	try {
        		
				Tuple task1 = new Tuple("Task",width,xstart,xend,y1,maxiter,y);
				Tuple task2 = null;
				space.write(task1);
				if(y+1 < height)
				{
					task2 = new Tuple("Task",width,xstart,xend,y2,maxiter,y+1);
					space.write(task2);
				}
				Tuple result = null;
				Tuple tuple = null;
				// need to use this for colorbar
				result = new Tuple("Result",new Field(int[].class),new Field(Integer.class));
				tuple = space.waitToTake(result);
				int data1[] = new int[width];
				data1 = (int[]) tuple.getField(1).getValue();
				//System.out.println(data1.length);
				//System.out.printf("value at 200 is %d",data1[200]);
				int y3 = (Integer) tuple.getField(2).getValue();
				System.out.println(y3);
				//int color = (Integer) tuple.getField(3).getValue();
				if(y+1 < height)
				{
				  System.arraycopy(data1, 0, data, y3*width, width);
				  tuple = space.waitToTake(result);
				  
				  data1 = (int[]) tuple.getField(1).getValue();
				  System.out.println(data1.length);
				  int y4 = (Integer) tuple.getField(2).getValue();
				  System.out.println(y4);
				  System.arraycopy(data1, 0, data, y4*width, width);
				} 
				else
				{
					System.arraycopy(data1, 0, data, y3*width, width);
				}
			} catch (TupleSpaceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
        Image image = canvas.getToolkit().createImage(mis);
        canvas.setImage(image);
    }

    /**
     * Gets a color model for the image source.
     * @return IndexColorModel for the image source.
     */
    private IndexColorModel generateColorModel() {
        byte[] r = new byte[255];
        byte[] g = new byte[255];
        byte[] b = new byte[255];

        for (int iter = 0; iter < 255; iter++) {
            r[iter] = (byte) ((iter * 26) % 250);
            g[iter] = (byte) ((iter * 2) % 250);
            b[iter] = (byte) ((iter * 35) % 250);
        }

        return new IndexColorModel(8, 255, r, g, b);
    }

}
