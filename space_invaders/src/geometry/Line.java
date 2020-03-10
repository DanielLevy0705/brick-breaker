package geometry;

/**
 * this class is creating a line in space.
 *
 * @author Daniel Levy
 */
public class Line {
    //point objects to create a line
    private Point start;
    private Point end;

    /**
     * constructor initializing line with points.
     *
     * @param start the start of the line
     * @param end   the end of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * constructor initializing line with double variables.
     *
     * @param x1 the x of the start point
     * @param y1 the y of the start point
     * @param x2 the x of the end point
     * @param y2 the y of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * query to check the length of the line using the distance method of points.
     *
     * @return return the length of the line
     */
    public double length() {
        double length = this.start.distance(this.end);
        return length;
    }

    /**
     * query to check what is the point in the middle of the line.
     *
     * @return the middle point
     */
    public Point middle() {
        double midx = ((this.start.getX() + this.end.getX()) / 2);
        double midy = ((this.start.getY() + this.end.getY()) / 2);
        Point middle = new Point(midx, midy);
        return middle;
    }

    /**
     * query to check what is the start point.
     *
     * @return the start point of the line
     */
    public Point start() {
        return this.start;
    }

    /**
     * query to check what is the end point.
     *
     * @return the end point of the line
     */
    public Point end() {
        return this.end;
    }

    /**
     * query to check the slope of the line.
     *
     * @return the slope of the line
     */
    public double slope() {
        //if there is no slope return 0 (later consider that case with the next method)
        if (this.start.getX() == this.end.getX()) {
            return 0;
        }
        //if there is slope calculate and return it
        double slope = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
        return slope;
    }

    /**
     * query to check if the slope exists.
     *
     * @return boolean that says if slope exists
     */
    public boolean doesSlopeExist() {
        //there is no slope to a straight line with the same x value in more than one point
        if (this.start.getX() == this.end.getX()) {
            return false;
        }
        return true;
    }

    /**
     * query to check if lines are intersecting.
     *
     * @param other the other line
     * @return boolean that says if they're intersecting
     */
    public boolean isIntersecting(Line other) {
        //if the next method return null then there is no intersection point
        if (this.intersectionWith(other) == null) {
            return false;
        }
        //if it didn't return null the lines are intersecting
        return true;
    }

    /**
     * a query to get to intersection point of two intersecting segments.
     *
     * @param other the other line
     * @return the intersection point
     */
    public Point intersectionWith(Line other) {
        //a,b,c,d variables to hold values of the equations y=ax+c,y=bx+d
        double a;
        double b;
        double c;
        double d;
        //xp,yp are the x and y values of the intersection point between two lines
        double xp, yp;
        //y/x1-4 are the y/x values of start and end points in both segments
        double y1, y2, y3, y4, x1, x2, x3, x4;
        //epsilon to check deviation
        double e = 0.0007;
        //if the lines are parallel there is no intersection points
        if (this.slope() == other.slope()) {
            //we need to consider that we gave vertical and horizontal the same slope,check if they're really parallel
            if (this.doesSlopeExist() == other.doesSlopeExist()) {
                return null;
            }
        }
        //if there is only one segment with no slope
        if (!this.doesSlopeExist()) {
            //the equation of the line with the slope is y=bx+d,b is the slope and d is the intersection with axis y
            b = other.slope();
            d = other.start.getY() - (b * other.start.getX());
            //give xp the value of the x of the vertical line
            xp = this.start.getX();
            //place xp value in the equation of the line with the slope and place the result in yp
            yp = b * xp + d;
            //initialize y1-y4 with the y values of the edges of both lines
            y1 = this.start.getY();
            y2 = this.end.getY();
            y3 = other.start.getY();
            y4 = other.end.getY();
            x1 = this.start.getX();
            x2 = this.end.getX();
            x3 = other.start.getX();
            x4 = other.end.getX();
            //initialize the intersection point of both lines with values of xp and yp
            Point intersectionPoint = new Point(xp, yp);
            //the segments have an intersection point only if the point is within both segments edges
            if ((yp < (y1 + e) && yp > (y2 - e)) || (yp > (y1 - e) && yp < (y2 + e)) || (yp == y1) || (yp == y2)) {
                if ((yp < (y3 + e) && yp > (y4 - e)) || (yp > (y3 + e) && yp < (y4 - e)) || (yp == y3) || (yp == y4)) {
                    if ((xp < (x1 + e) && xp > (x2 - e)) || (xp > (x1 - e) && xp < (x2 + e)) || (xp == x1)
                            || (xp == x2)) {
                        if ((xp < (x3 + e) && xp > (x4 - e)) || (xp > (x3 - e) && xp < (x4 + e)) || (xp == x3)
                                || (xp == x4)) {
                            //if its within the segments edges return it
                            return intersectionPoint;
                        }
                    }
                }
                //else return null
            } else {
                return null;
            }
            //if there is only one segment with no slope
        } else if (!other.doesSlopeExist()) {
            //the equation of the line with the slope is y=ax+c,a is the slope and c is the intersection with axis y
            a = this.slope();
            c = this.start.getY() - (a * this.start.getX());
            //give xp the value of the x of the vertical line
            xp = other.start.getX();
            //place xp value in the equation of the line with the slope and place the result in yp
            yp = a * xp + c;
            //initialize y1-y4 with the y values of the edges of both lines
            y1 = other.start.getY();
            y2 = other.end.getY();
            y3 = this.start.getY();
            y4 = this.end.getY();
            x1 = this.start.getX();
            x2 = this.end.getX();
            x3 = other.start.getX();
            x4 = other.end.getX();
            //initialize the intersection point of both lines with values of xp and yp
            Point intersectionPoint = new Point(xp, yp);
            //the segments have an intersection point only if the point is within both segments edges
            if ((yp < (y1 + e) && yp > (y2 - e)) || (yp > (y1 - e) && yp < (y2 + e)) || (yp == y1) || (yp == y2)) {
                if ((yp < (y3 + e) && yp > (y4 - e)) || (yp > (y3 + e) && yp < (y4 - e)) || (yp == y3) || (yp == y4)) {
                    if ((xp < (x1 + e) && xp > (x2 - e)) || (xp > (x1 - e) && xp < (x2 + e)) || (xp == x1)
                            || (xp == x2)) {
                        if ((xp < (x3 + e) && xp > (x4 - e)) || (xp > (x3 - e) && xp < (x4 + e)) || (xp == x3)
                                || (xp == x4)) {
                            //if its within the segments edges return it
                            return intersectionPoint;
                        }
                    }
                }
                //else return null
            } else {
                return null;
            }
        }
        //give a the slope of the current line
        a = this.slope();
        //give b the slope of the other line
        b = other.slope();
        //give c the value of the current line intersection with axis y
        c = this.start.getY() - (this.slope() * this.start.getX());
        //give d the value of the other line intersection with axis y
        d = other.start.getY() - (other.slope() * other.start.getX());
        /*
         * the x value of the intersection point is revealed by creating the equation ax+c=bx+d,
         * then extract the value of x and we get x=d-c/a-b. place the result in xp
         */
        xp = (d - c) / (a - b);
        //place the value of xp in one of the line equations and place the result in yp
        yp = a * xp + c;
        //initialize y1-y4 with the y values of the edges of both lines
        y1 = this.start.getY();
        y2 = this.end.getY();
        y3 = other.start.getY();
        y4 = other.end.getY();
        x1 = this.start.getX();
        x2 = this.end.getX();
        x3 = other.start.getX();
        x4 = other.end.getX();
        //initialize the intersection point of both lines with values of xp and yp
        Point intersectionPoint = new Point(xp, yp);
        //the segments have an intersection point only if the point is within both segments edges
        if ((yp < (y1 + e) && yp > (y2 - e)) || (yp > (y1 - e) && yp < (y2 + e)) || (yp == y1) || (yp == y2)) {
            if ((yp < (y3 + e) && yp > (y4 - e)) || (yp > (y3 + e) && yp < (y4 - e)) || (yp == y3) || (yp == y4)) {
                if ((xp < (x1 + e) && xp > (x2 - e)) || (xp > (x1 - e) && xp < (x2 + e)) || (xp == x1) || (xp == x2)) {
                    if ((xp < (x3 + e) && xp > (x4 - e)) || (xp > (x3 - e) && xp < (x4 + e)) || (xp == x3)
                            || (xp == x4)) {
                        //if its within the segments edges return it
                        return intersectionPoint;
                    }
                }
            }
        }
        //else return null
        return null;
    }

    /**
     * a query to check if two lines are equal.
     *
     * @param other a line to check if its equal to the current line
     * @return boolean value if they are equal or not
     */
    public boolean equals(Line other) {
        /*
         * if the start of the current line is the start or the end of the other line
         * and if the end of the current line is the start or the end of the other line
         * and if they both have the same length, they are equal,return true
         */
        if ((this.start.equals(other.start)) || (this.start.equals(other.end))) {
            if ((this.end.equals(other.end)) || (this.end.equals(other.start))) {
                if (this.length() == other.length()) {
                    return true;
                }
            }
        }
        //else return false
        return false;
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     *
     * @param rect a rectangle to check its intersection with the line
     * @return the intersection point that is closer to the start of the line
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        //variables to check distance
        double distance0, distance1;
        //if there are no intersection points return null.
        if (rect.intersectionPoints(this).size() == 0) {
            return null;
        }
        //if the first intersection point isn't null the first distance is the distance from the end of this line.
        if (rect.intersectionPoints(this).get(0) != null) {
            distance0 = this.end.distance(rect.intersectionPoints(this).get(0));
            //else the first distance is -1 (make the distance negative so that the other one will be bigger.
        } else {
            distance0 = -1;
        }
        //if there is more than one point
        if (rect.intersectionPoints(this).size() > 1) {
            //if the second intersection point is'nt null the second distance is the distance from the end of this line
            if (rect.intersectionPoints(this).get(1) != null) {
                distance1 = this.end.distance(rect.intersectionPoints(this).get(1));
                //else make the distance -1
            } else {
                distance1 = -1;
            }
            //else make the distance -1
        } else {
            distance1 = -1;
        }
        //check which distance is bigger and return the bigger distance
        if (distance1 < distance0) {
            return rect.intersectionPoints(this).get(0);
        }
        if (distance0 < distance1) {
            return rect.intersectionPoints(this).get(1);
        }
        //if the distances are equal return null
        return null;
    }

    /**
     * the function check if there is an x or y value with the values of the line.
     *
     * @param p    the point to check its value of x or y
     * @param xOrY a parameter to know which one to check x or y
     * @return true if the line contain the value or false if its not
     */
    public boolean contains(Point p, String xOrY) {
        //epsilon to check deviation
        double e = 0.0001;
        //x and y values of the edges of the line
        double x1 = this.start.getX();
        double x2 = this.end.getX();
        double y1 = this.start.getY();
        double y2 = this.end.getY();
        //x and y values of the point received
        double xp = p.getX();
        double yp = p.getY();
        //check if the y of the point is within the y values of the line.
        if (xOrY == "y") {
            if ((yp < (y1 + e) && yp > (y2 - e)) || (yp > (y1 - e) && yp < (y2 + e)) || (yp == y1) || (yp == y2)) {
                return true;
            }
        }
        //check if the x of the point is withing the x values of the line.
        if (xOrY == "x") {
            if ((xp < (x1 + e) && xp > (x2 - e)) || (xp > (x1 - e) && xp < (x2 + e)) || (xp == x1) || (xp == x2)) {
                return true;
            }
        }
        //if they are not return false
        return false;
    }
}