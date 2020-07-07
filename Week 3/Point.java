
/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import java.util.Objects;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that.x == this.x && that.y == this.y) {
            return Double.NEGATIVE_INFINITY;
        }

        if (that.y == this.y) {
            return 0;
        }

        if (that.x == this.x) {
            return Double.POSITIVE_INFINITY;
        }

        return (double) (that.y - this.y) / (double) (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (that.x == this.x && that.y == this.y) {
            return 0;
        }

        if (this.y < that.y
                || (this.y == that.y && this.x < that.x)) {
            return -1;
        }

        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        Point dis = this;
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                double s1 = o1.slopeTo(dis);
                double s2 = o2.slopeTo(dis);

                if (Objects.equals(s1, s2)
                        || Math.abs(s1 - s2) < 0.000001) {
                    return 0;
                }

                if (o1.slopeTo(dis) < o2.slopeTo(dis)) {
                    return -1;
                }

                return 1;
            }
        };
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        // points can range from 0 to 1000
                
        StdDraw.setXscale(0, 1000);
        StdDraw.setYscale(0, 1000);

        int N = 1000;
        int SCALE = 1000;
        Point[] ps = new Point[N];

        // ps[0] = new Point(100, 100); ps[0].draw();
        // ps[1] = new Point(300, 300); ps[1].draw();
        // ps[2] = new Point(500, 500); ps[2].draw();
        // ps[3] = new Point(600, 600); ps[3].draw();
        // ps[4] = new Point(300, 500); ps[4].draw();


        for (int i = 0; i < N; i++){
            ps[i] = new Point(StdRandom.uniform(SCALE), StdRandom.uniform(SCALE));
            ps[i].draw();
        }
        
        FastCollinearPoints points = new FastCollinearPoints(ps);
        System.out.println("We have " + points.numberOfSegments() + " line segments that contain at least 4 points");
        // System.out.println(points.lines.toString());

        // BruteCollinearPoints brute = new BruteCollinearPoints(ps);
        // System.out.println("We have " + brute.numberOfSegments() + " line segments that contain at least 4 points");

        
        // for (int i = 0; i < N; i++){
        //     int kk = StdRandom.uniform(N);
        //     p(ps[i].toString() + "->" + ps[kk].toString() + "=" + ps[i].slopeTo(ps[kk]));
        // }

        
        // for (int i = 0; i < N; i++){
        //     int kk = StdRandom.uniform(N);
        //     String symbol = ps[i].compareTo(ps[kk]) == 0 ? " = " : ps[i].compareTo(ps[kk]) == 1 ? " > " : " < ";
        //     p(ps[i].toString() + symbol + ps[kk].toString());
        // }
        
    }

}