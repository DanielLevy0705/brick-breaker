package geometry;

/**
 * this class is to create a point in space.
 *
 * @author Daniel Levy
 */
public class Point {
    //double variables of point
    private double x;
    private double y;

    /**
     * constructor to initialize the object.
     *
     * @param x the x value of the point
     * @param y the y value of the point
     */
    public Point(double x, double y) {
        //initialize the object with x and y values received by the user
        this.x = x;
        this.y = y;
    }

    /**
     * the function checks the distance between current point to another one.
     *
     * @param other a point to check the distance from
     * @return return the distance between two points
     */
    public double distance(Point other) {
        if (this.equals(other)) {
            return 0;
        }
        //initialize new distance variable with the distance formula
        double distance = Math.sqrt(Math.pow((this.x - other.x), 2.0) + Math.pow((this.y - other.y), 2.0));
        //return the distance
        return distance;
    }

    /**
     * the function checks if the current point x and y values are the same as the other point.
     *
     * @param other another point object to check if it's equal to the current point
     * @return boolean value if they're equals or not
     */
    public boolean equals(Point other) {
        //if the x values are equal check y values
        if (other.x == this.x) {
            //if y values are  equal the points are equal return true
            if (other.y == this.y) {
                return true;
            }
        }
        //only return false if x or y values are not equal in both points
        return false;
    }

    /**
     * function that returns the x value of the point.
     *
     * @return x value of the point
     */
    public double getX() {
        return this.x;
    }

    /**
     * function that returns the y value of the point.
     *
     * @return y value of the point
     */
    public double getY() {
        return this.y;
    }

    /**
     * setter to y value.
     *
     * @param newY new y value
     */
    public void setY(double newY) {
        this.y = newY;
    }

    /**
     * setter to x value.
     *
     * @param newX new x value
     */
    public void setX(double newX) {
        this.x = newX;
    }
}