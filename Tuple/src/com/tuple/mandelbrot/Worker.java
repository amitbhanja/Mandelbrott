package com.tuple.mandelbrot;

import com.ibm.tspaces.Field;
import com.ibm.tspaces.Tuple;
import com.ibm.tspaces.TupleSpace;
import com.ibm.tspaces.TupleSpaceException;

public class Worker {
public static int maxiter;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int color = 0;
		TupleSpace space = null;
		try {
			space = new TupleSpace("Mandelbrot","localhost:8200");
		} catch (TupleSpaceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
			try {
				
				if(color == 0)
				{
					Tuple template = new Tuple("Color",new Field(Integer.class));
					Tuple tuple1 = space.waitToTake(template);
					System.out.println(tuple1.getField(1).getValue());
					color = (Integer) tuple1.getField(1).getValue();
				}
				Tuple template1 = new Tuple("Task",new Field(Integer.class),new Field(Double.class),new Field(Double.class),new Field(Double.class),new Field(Integer.class),new Field(Integer.class));
				Tuple tuple = space.waitToTake(template1);
				System.out.println(tuple.getField(1).getValue());
				System.out.println(tuple.getField(2).getValue());
				System.out.println(tuple.getField(3).getValue());
				System.out.println(tuple.getField(4).getValue());
				System.out.println(tuple.getField(5).getValue());
				System.out.println(tuple.getField(6).getValue());
				int width = (Integer) tuple.getField(1).getValue();
				double xstart = (Double )tuple.getField(2).getValue();
				double xend = (Double)tuple.getField(3).getValue();
				double y1 = (Double)tuple.getField(4).getValue();
				 maxiter = (Integer)tuple.getField(5).getValue();
				 //System.out.println(maxiter);
				int y = (Integer)tuple.getField(6).getValue();
				
				int data[] = new int[width];
				int i = 0;
				for(int x = 0;x < 10;x++)
					data[i++] = color;
				for(int x = 10;x < width;x++)
				{
					data[i++] = iterate(xstart + (xend - xstart)*x/(width - 1),y1) % 256;
				}
				//System.out.printf("value at 200 is %d",data[200]);
				Tuple result = new Tuple("Result",data,y);
				space.write(result);
				
			} catch (TupleSpaceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

    /**
     * Calcluate the value for the given point.
     * @param x x-value of the point
     * @param y y-value of the point.
     * @return Value for the point.
     */
    public static int iterate(double x, double y) {
        int iter = 0;

        double aold = 0;
        double bold = 0;
        double a = 0;
        double b = 0;
        double asquared = a * a;
        double bsquared = b * b;

        a = x;
        b = y;

        double zsquared = asquared + bsquared;
        
        for (iter = 0; iter < maxiter; iter++) {
            a = asquared - bsquared + x;

            asquared = a * a;

            b = 2 * aold * b + y;

            if (bold == b && aold == a) {
                iter = maxiter - 1;
            }

            bsquared = b * b;

            zsquared = asquared + bsquared;

            if (zsquared > 4) {
          //  	System.out.println("I am here\n");
                break;
            }

            bold = b;
            aold = a;
        }
        return iter;
    }

}
