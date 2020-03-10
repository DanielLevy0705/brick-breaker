package sprite;

import animation.GameLevel;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.Collidable;
import geometry.Point;
import geometry.Rectangle;
import tools.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * a class that is a moving paddle.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle paddleRect;
    private Color paddleColor;
    private double speed;
    private long newShot, lastShot;
    private boolean gotShot;
    private Point start;
    private List<Ball> paddleBalls;

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
        this.newShot = 0;
        this.lastShot = 0;
        this.gotShot = false;
        this.start = new Point(rect.getUpperLeft().getX(), rect.getUpperLeft().getY());
        this.paddleBalls = new ArrayList<Ball>();
    }

    /**
     * move the paddle to the left.
     *
     * @param dt seconds number per frame.
     */
    public void moveLeft(double dt) {
        double dx = (this.speed * dt);
        //epsilon for deviation
        double e = 0.0007;
        double borderSize = 0;
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
        double dx = (this.speed * dt);
        //epsilon for deviation
        double e = 0.0007;
        //the width of the screen
        double borderSize = 0;
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
     * @param hitter          the ball that hits the paddle
     * @param collisionPoint  the intersection point of the collidable objects
     * @param currentVelocity the current velocity of the object that is colliding
     */
    public void hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (currentVelocity.getDy() > 0) {
            this.gotShot = true;
        }
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
     * a method to fire ball at the aliens.
     * @param game the game which we should add the "bullet" to.
     */
    public void fire(GameLevel game) {
        newShot = System.currentTimeMillis();
        int r = 2;
        double x = this.getCollisionRectangle().getUpperLeft().getX() + (this.getCollisionRectangle().getWidth() / 2);
        double y = this.getCollisionRectangle().getUpperLeft().getY() - r - 1;
        Point center = new Point(x, y);
        //if last shot was before more than 350 milliseconds create a new ball and add to game.
        if (newShot - lastShot > 350) {
            Ball ball = new Ball(center, r, Color.green);
            Velocity v = Velocity.fromAngleAndSpeed(0, 400);
            ball.setVelocity(v);
            //set the game environment of the ball
            ball.setGameEnv(game.getEnvironment());
            ball.addToGame(game);
            this.paddleBalls.add(ball);
            lastShot = System.currentTimeMillis();
        }
    }

    /**
     * a boolean function to check if the paddle got shot.
     * @return boolean that tells if the paddle got shot.
     */
    public boolean getGotShot() {
        return this.gotShot;
    }

    /**
     * a function that reset the paddle game location and the gotShot memeber.
     */
    public void reset() {
        this.paddleRect.changeUpperLeft(this.start);
        this.gotShot = false;
    }

    /**
     * return the list of the paddle balls.
     * @return the list of the paddle balls.
     */
    public List<Ball> getPaddleBalls() {
        return this.paddleBalls;
    }
}