package sprite;

import animation.GameLevel;
import biuoop.DrawSurface;
import collision.Collidable;
import collision.GameEnvironment;
import geometry.Line;
import geometry.Point;
import tools.Velocity;

import java.awt.Color;

import static java.lang.Math.abs;

/**
 * a class that creates a circle with velocity and color and draw it on the screen.
 *
 * @author Daniel Levy
 */
public class Ball implements Sprite {
    // variables that indicates the radius,center,color,velocity of the ball and edges of the screen
    private int r;
    private Point center;
    private Color color;
    private Velocity velocity;
    private GameEnvironment gameEnv;

    /**
     * constructor to create a Sprite.Sprite.Ball object.
     *
     * @param center the center point of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, Color color) {
        //initialize the ball with values received from the user
        this.center = center;
        this.r = r;
        this.color = color;
        //if the y or the x values of the center are smaller than the radius make the radius the new value
        if (center.getX() < r) {
            this.center = new Point(r, center.getY());
        }
        if (center.getY() < r) {
            this.center = new Point(this.center.getX(), r);
        }
    }

    /**
     * accessor to x value of the center of the ball.
     *
     * @return the x value of the center of the ball
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * accessor to y value of the center of the ball.
     *
     * @return the y value of the center of the ball
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * accessor to the size of the ball.
     *
     * @return the radius of the ball
     */
    public int getSize() {
        return this.r;
    }

    /**
     * accessor to the color of the ball.
     *
     * @return the color of the ball
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * draw the ball on a given surface.
     *
     * @param surface the surface to draw the ball on
     */
    public void drawOn(DrawSurface surface) {
        // set the color of the ball
        surface.setColor(this.color);
        //draw the ball on the surface
        surface.fillCircle(getX(), getY(), getSize());
        //set the color of the frame
        surface.setColor(Color.black);
        //draw the frame
        surface.drawCircle(getX(), getY(), getSize());
    }

    /**
     * set the velocity of the ball.
     *
     * @param v the velocity of the ball
     */
    public void setVelocity(Velocity v) {
        //initialize the velocity of the ball with v.
        this.velocity = v;
    }

    /**
     * set the velocity of the ball.
     *
     * @param dx the change in position on the x axis
     * @param dy the change in position on the y axis
     */
    public void setVelocity(double dx, double dy) {
        //initialize the velocity of the ball with the new parameters
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * accessor to the velocity of the ball.
     *
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * setter to gameEnv member.
     *
     * @param gameEnvironment the game environment of the ball
     */
    public void setGameEnv(GameEnvironment gameEnvironment) {
        this.gameEnv = gameEnvironment;
    }

    /**
     * getter of gameEnv member.
     *
     * @return the game environment of the ball
     */
    public GameEnvironment getGameEnv() {
        return this.gameEnv;
    }

    /**
     * the function is changing the center of the ball and in that way making it move.
     *
     * @param dt seconds number per frame.
     */
    public void moveOneStep(double dt) {
        // variables with values of the ball parameters.
        double dx = this.velocity.getDx();
        double dy = this.velocity.getDy();
        double x = this.center.getX();
        double y = this.center.getY();
        double xEnd = 775;
        double xStart = 25;
        double yStart = 35;
        // the trajectory of the ball is where it will be in the next movement
        Point trajectoryStart = new Point(x, y);
        Point trajectoryEnd = this.velocity.applyToPoint(this.center, dt);
        Point nextCenter;
        Line trajectory = new Line(trajectoryStart, trajectoryEnd);
        // if there is no collision move to the next point in the trajectory
        if (this.gameEnv.getClosestCollision(trajectory).collisionPoint() == null) {
            this.center = this.velocity.applyToPoint(this.center, dt);
            // if there is a collision get the collisionPoint the the collidable object
        } else {
            Collidable col = this.gameEnv.getClosestCollision(trajectory).collisionObject();
            Point colPoint = this.gameEnv.getClosestCollision(trajectory).collisionPoint();
            //deviation is to get the ball almost to the collision point but stop be4 it get there.
            double deviationY = (abs(colPoint.getY() - this.center.getY()) / 4);
            double deviationX = (abs(colPoint.getX() - this.center.getX()) / 4);
            /*
             * check the collision point values, and make the new center
             * near the collision point.
             */
            if (colPoint.getY() > this.center.getY()) {
                y = colPoint.getY() - deviationY;
            } else {
                y = colPoint.getY() + 3 * deviationY;
            }
            if (colPoint.getX() > this.center.getX()) {
                x = colPoint.getX() - deviationX;
            } else {
                x = colPoint.getX() + 3 * deviationX;
            }
            this.center = new Point(x, y);
            nextCenter = new Point(x + (dx), y + (dy));
            //set new velocity with the collidable hit function.
            this.setVelocity(col.hit(this, colPoint, this.velocity));
            //check if the ball is inside the rectangle and if it is make him go out
            if (col.getCollisionRectangle().insideOf(this.center)) {
                this.center = this.velocity.applyToPoint(this.center, dt);
                if (x > xEnd) {
                    this.center.setX(775 - r);
                }
                if (x < xStart) {
                    this.center.setX(25 + r);
                }
                if (y < yStart) {
                    this.center.setY(35 + r);
                }
                //if the ball is going to enter a rectangle and the object is the paddle let him in and out.
            } else if (col.getCollisionRectangle().insideOf(nextCenter)) {
                if (col instanceof Paddle) {
                    this.center = this.velocity.applyToPoint(this.center, dt);
                }
            }
        }
    }

    /**
     * the function call moveOneStep function.
     *
     * @param dt parameter that holds how many seconds there are in one frame.
     */
    public void timePassed(double dt) {
        moveOneStep(dt);
    }

    /**
     * the function add the ball as a sprite to the game.
     *
     * @param g the game to add the ball to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * a function to remove the ball from the game.
     *
     * @param g the game to remove the ball from.
     */
    public void removeFromGame(GameLevel g) {
        //remove the ball from the sprite collection.
        g.removeSprite(this);
    }
}