package sprite;

import animation.GameLevel;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import geometry.Point;
import geometry.Rectangle;
import tools.Velocity;

import java.awt.Color;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.StrictMath.sqrt;

/**
 * a class that is a moving paddle.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle paddleRect;
    private Color paddleColor;
    private double speed;

    /**
     * paddle constructor.
     *
     * @param guiKeys     the keyboardSensor
     * @param rect        the rectangle of the paddle
     * @param color       the color of the paddle
     * @param paddleSpeed the speed of the paddle
     */
    public Paddle(biuoop.KeyboardSensor guiKeys, Rectangle rect, Color color, double paddleSpeed) {
        this.keyboard = guiKeys;
        this.paddleRect = rect;
        this.paddleColor = color;
        this.speed = paddleSpeed;
    }

    /**
     * move the paddle to the left.
     *
     * @param dt seconds number per frame.
     */
    public void moveLeft(double dt) {
        double fr = 60;
        double dx = (this.speed * dt);
        //epsilon for deviation
        double e = 0.0007;
        double borderSize = 25;
        //check if the point is within the screen limits and if it is change the paddle position.
        Point p = this.paddleRect.getUpperLeft();
        if (p.getX() + e >= borderSize + (this.speed * dt)) {
            p.setX(p.getX() - dx);
        } else {
            p.setX(borderSize);
        }
        this.paddleRect.changeUpperLeft(p);
    }

    /**
     * move the paddle to the right.
     *
     * @param dt seconds number per frame.
     */
    public void moveRight(double dt) {
        double fr = 60;
        double dx = (this.speed * dt);
        //epsilon for deviation
        double e = 0.0007;
        //the width of the screen
        double borderSize = 25;
        double screenWidth = 800 - borderSize - (this.speed * dt);
        //if the point is within the screen limits change the paddle position
        Point p = this.paddleRect.getUpperLeft();
        if (p.getX() + this.paddleRect.getWidth() - e <= screenWidth) {
            p.setX(p.getX() + dx);
        } else {
            p.setX(screenWidth + (this.speed * dt) - this.getCollisionRectangle().getWidth());
        }
        this.paddleRect.changeUpperLeft(p);
    }

    /**
     * notify the paddle that the time passed and it needs to do something.
     *
     * @param dt seconds number per frame.
     */
    public void timePassed(double dt) {
        //if the left key is pressed move left
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft(dt);
        }
        //if the right key is pressed move right
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight(dt);
        }
    }

    /**
     * draw the paddle on the surface.
     *
     * @param d the drawsurface to draw on.
     */
    public void drawOn(DrawSurface d) {
        //the paddle upper left
        Point p = this.paddleRect.getUpperLeft();
        //set the color of filling
        d.setColor(this.paddleColor);
        int paddleHeight = (int) this.paddleRect.getHeight();
        //fill the rectangle
        d.fillRectangle((int) p.getX(), (int) p.getY(), (int) this.paddleRect.getWidth(), paddleHeight);
        //set the color of the frame
        d.setColor(Color.black);
        //draw the rectangle
        d.drawRectangle((int) p.getX(), (int) p.getY(), (int) this.paddleRect.getWidth(), paddleHeight);
    }

    /**
     * get the collision rectangle.
     *
     * @return the paddle rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.paddleRect;
    }

    /**
     * change velocity by hit angle.
     *
     * @param collisionPoint  the intersection point of the collidable objects
     * @param currentVelocity the current velocity of the object that is colliding
     * @param hitter          the ball that hits the paddle
     * @return the new velocity
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double x1 = this.getCollisionRectangle().getUpperLeft().getX();
        double y1 = this.getCollisionRectangle().getUpperLeft().getY();
        double colX = collisionPoint.getX(), colY = collisionPoint.getY();
        //epsilon for deviation
        double e = 0.0007;
        //new velocity equal the current one at the moment.
        Velocity newVel = currentVelocity;
        double reg1Angle = 300, reg2Angle = 330, reg3Angle = 360, reg4Angle = 30, reg5Angle = 60;
        //check by regions and change the velocity to the correct angle for collision in different regions
        double newSpeed = sqrt(pow(currentVelocity.getDx(), 2) + pow(currentVelocity.getDy(), 2));
        double[] regs = regions();
        if ((colX > abs(x1 - e)) && (colX < (x1 + regs[4] + e)) && ((colY - y1) <= e)) {
            if (colX < (x1 + e + regs[3])) {
                if (colX < (x1 + e + regs[2])) {
                    if (colX < (x1 + e + regs[1])) {
                        if (colX < (x1 + e + regs[0])) {
                            newVel = Velocity.fromAngleAndSpeed(reg1Angle, newSpeed);
                        } else {
                            newVel = Velocity.fromAngleAndSpeed(reg2Angle, newSpeed);
                        }
                    } else {
                        newVel = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                    }
                } else {
                    newVel = Velocity.fromAngleAndSpeed(reg4Angle, newSpeed);
                }
            } else {
                newVel = Velocity.fromAngleAndSpeed(reg5Angle, newSpeed);
            }
        }
        //return the new velocity
        return newVel;
    }

    /**
     * add this paddle to the game.
     *
     * @param g the game to add the paddle to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * a function to remove the paddle from the game.
     *
     * @param g the game from which the paddle is removed.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }

    /**
     * get 5 different regions of the paddle.
     *
     * @return the regions array
     */
    public double[] regions() {
        int numOfRegs = 5;
        double[] regs = new double[numOfRegs];
        //devide the width to number of regions and add them to the array
        for (int i = 0; i < numOfRegs; i++) {
            regs[i] = (this.paddleRect.getWidth() / numOfRegs) * (i + 1);
        }
        return regs;
    }
}