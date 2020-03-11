package tools;

import geometry.Point;

/**
 * tools.Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * constructor to create tools.Velocity object.
     *
     * @param dx specifies the change in position on the x axis
     * @param dy specifies the change in position on the y axis
     */
    public Velocity(double dx, double dy) {
        //initialize the object with dx and dy values received from the user
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Take a point with position (x,y) and return a new point with position (x+dx,y+dy).
     *
     * @param p  the point to change
     * @param dt tells us how many seconds in one frame.
     * @return the point after change
     */
    public Point applyToPoint(Point p, double dt) {
        double framerate = 60;
        //create a new point using the point that is received and return it
        Point newPoint = new Point(p.getX() + (this.dx * dt), p.getY() + (this.dy * dt));
        return newPoint;
    }

    /**
     * static method to create velocity in terms of angle and speed.
     *
     * @param angle the angle from y axes to the direction of the ball
     * @param speed the speed of the movement of the ball
     * @return new velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        //sin(angle) is dx/speed according to trigonometric rules so dx=sin(angle)*speed
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        /*
         * cos(angle) is dy/speed according to trigonometric rules so dy=cos(angle)*speed*(-1),
         * because our y axis is upside down
         */
        double dy = -Math.cos(Math.toRadians(angle)) * speed;

        //return the new velocity
        return new Velocity(dx, dy);
    }

    /**
     * the function returns the dx of the velocity.
     *
     * @return the dx of the current velocity
     */
    public double getDx() {
        //return the dx of the current velocity
        return this.dx;
    }

    /**
     * the function returns the dy of the velocity.
     *
     * @return the dy of the current velocity
     */
    public double getDy() {
        //return the dy of the current velocity
        return this.dy;
    }
}