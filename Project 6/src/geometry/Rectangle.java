package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * create a geometry.Rectangle with upper left width and height.
 */
public class Rectangle {
    private Point pStart;
    private Point pEnd;
    private double recWidth;
    private double recHeight;
    private Line topSide;
    private Line bottomSide;
    private Line leftSide;
    private Line rightSide;

    /**
     * Create a new rectangle with location and width/height.
     *
     * @param upperLeft the upper left point of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.pStart = upperLeft;
        this.recHeight = height;
        this.recWidth = width;
        createSides();
        setBottomRight();
    }

    /**
     * Return a (possibly empty) List of intersection point with the specified line.
     *
     * @param line a line to check intersection points with rectangle.
     * @return the list with the intersection points (if there are intersections)
     */
    public List<Point> intersectionPoints(Line line) {
        //create a list of points
        List<Point> list = new ArrayList<Point>();
        int samePointCheck = 0;
        //check if the line received is intersecting with the top or bottom of the rectangle
        if (line.isIntersecting(getTopSide())) {
            //if they are add the intersection points to the list
            list.add(line.intersectionWith(getTopSide()));
        }
        if ((line.isIntersecting(getBottomSide()))) {
            list.add(line.intersectionWith(getBottomSide()));
        }
        //check intersections with the sides of the rectangle and check if its an existing point in the rectangle.
        if (line.isIntersecting(getRightSide())) {
            for (int i = 0; i < 2; i++) {
                //check if the there is an index (i) in the list
                if (list.size() > i) {
                    //if the intersection exist change the flag
                    if (line.intersectionWith(getRightSide()).equals(list.get(i))) {
                        samePointCheck++;
                    }
                }
            }
            //if the flag == 0 add the point, else dont add it and return the flag to be 0
            if (samePointCheck == 0) {
                list.add(line.intersectionWith(getRightSide()));
            } else {
                samePointCheck = 0;
            }
        }
        //check intersections with the sides of the rectangle and check if its an existing point in the rectangle.
        if (line.isIntersecting(getLeftSide())) {
            for (int i = 0; i < 2; i++) {
                //check if the there is an index (i) in the list
                if (list.size() > i) {
                    //if the intersection exist change the flag
                    if (line.intersectionWith(getLeftSide()).equals(list.get(i))) {
                        samePointCheck++;
                    }
                }
            }
            //if the flag == 0 add the point, else dont add it
            if (samePointCheck == 0) {
                list.add(line.intersectionWith(getLeftSide()));
            }
        }
        return list;
    }

    /**
     * return the width of the rectangle.
     *
     * @return the width of this rectangle
     */
    public double getWidth() {
        return this.recWidth;
    }

    /**
     * return the height of the rectangle.
     *
     * @return the height of this rectangle
     */
    public double getHeight() {
        return this.recHeight;
    }

    /**
     * return the upper left point of the rectangle.
     *
     * @return the upper left of this rectangle
     */
    public Point getUpperLeft() {
        return this.pStart;
    }

    /**
     * a function that initialize the members that are the sides of the rectangle.
     */
    public void createSides() {
        Point upperRight = new Point(this.recWidth + this.pStart.getX(), this.pStart.getY());
        Point bottomLeft = new Point(this.pStart.getX(), this.pStart.getY() + this.recHeight);
        Point bottomRight = new Point(upperRight.getX(), bottomLeft.getY());
        this.topSide = new Line(this.pStart, upperRight);
        this.leftSide = new Line(this.pStart, bottomLeft);
        this.rightSide = new Line(upperRight, bottomRight);
        this.bottomSide = new Line(bottomLeft, bottomRight);
    }

    /**
     * set the bottom right point.
     */
    public void setBottomRight() {
        this.pEnd = new Point(this.recWidth + this.pStart.getX(), this.recHeight + this.pStart.getY());
    }

    /**
     * get the bottom right point.
     *
     * @return the bottom right point of the rectangle
     */
    public Point getBottomRight() {
        return this.pEnd;
    }

    /**
     * getter to bottom side of the rectangle.
     *
     * @return the bottom side
     */
    public Line getBottomSide() {
        return this.bottomSide;
    }

    /**
     * getter to left side of the rectangle.
     *
     * @return the left side
     */
    public Line getLeftSide() {
        return this.leftSide;
    }

    /**
     * getter to the right side of the rectangle.
     *
     * @return the right side of the rect
     */
    public Line getRightSide() {
        return this.rightSide;
    }

    /**
     * getter to the top side of the rectangle.
     *
     * @return the top side
     */
    public Line getTopSide() {
        return this.topSide;
    }

    /**
     * change the upper left and by that the other stats of the rectangle.
     *
     * @param newUpperLeft the new upper left to set
     */
    public void changeUpperLeft(Point newUpperLeft) {
        this.pStart = newUpperLeft;
        createSides();
        setBottomRight();
    }

    /**
     * check if a point is inside of the rectangle.
     *
     * @param p the point to check if its inside
     * @return the boolean answer
     */
    public boolean insideOf(Point p) {
        double e = 0.0001;
        //check if it contains the x and y values that are withing the rect.
        if ((this.bottomSide.contains(p, "x")) && (this.leftSide.contains(p, "y"))) {
            return true;
        }
        return false;
    }

}