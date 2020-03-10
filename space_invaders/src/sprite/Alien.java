package sprite;

import animation.GameLevel;
import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import listener.HitListener;
import tools.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * a class that represents an alien and extends a wallBlock (because it has the same features with some extras).
 */
public class Alien extends WallBlock {
    private Point start;
    private List<Ball> alienBalls;

    /**
     * creating a block which is a rectangle with a color.
     *
     * @param x the x of the top left of the block
     * @param y the y of the top left of the block
     */
    public Alien(int x, int y) {
        super(x, y);
        this.start = new Point(x, y);
        this.alienBalls = new ArrayList<Ball>();
    }

    /**
     * add the alien to the game the same way his father class does.
     *
     * @param game the game the alien will be added to.
     */
    @Override
    public void addToGame(GameLevel game) {
        super.addToGame(game);
    }

    /**
     * remove the alien from the game the same way his father class does.
     *
     * @param game the game from which the alien is removed.
     */
    @Override
    public void removeFromGame(GameLevel game) {
        super.removeFromGame(game);
    }

    /**
     * Return the "collision shape" of the object.
     *
     * @return the rectangle that we are colliding with.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return super.getCollisionRectangle();
    }

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param hitter          the ball that hit the blocks.
     * @param collisionPoint  the intersection point of the collidable objects.
     * @param currentVelocity the current velocity of the object that is colliding.
     */
    @Override
    public void hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        super.hit(hitter, collisionPoint, currentVelocity);
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl the hit listener
     */
    @Override
    public void addHitListener(HitListener hl) {
        super.addHitListener(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl the hit listener
     */
    @Override
    public void removeHitListener(HitListener hl) {
        super.removeHitListener(hl);
    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the drawing surface to draw on
     */
    @Override
    public void drawOn(DrawSurface d) {
        super.drawOn(d);
    }

    /**
     * notify the sprite that time passed.
     *
     * @param dt seconds number in one frame.
     */
    @Override
    public void timePassed(double dt) {
    }

    /**
     * a function used to move the alien to the left.
     *
     * @param dt    number of seconds per frame.
     * @param speed the speed of the alien movement.
     */
    public void moveLeft(double dt, double speed) {
        Point p = super.getBlockShape().getUpperLeft();
        super.getBlockShape().changeUpperLeft(new Point(p.getX() - speed, p.getY()));
    }

    /**
     * a function used to move the alien to the right.
     *
     * @param dt    number of seconds per frame.
     * @param speed the speed of the alien movement.
     */
    public void moveRight(double dt, double speed) {
        Point p = super.getBlockShape().getUpperLeft();
        super.getBlockShape().changeUpperLeft(new Point(p.getX() + speed, p.getY()));
    }

    /**
     * a function used to mvoe the alien down.
     *
     * @param dt number of seconds per frame.
     */
    public void moveDown(double dt) {
        Point p = super.getBlockShape().getUpperLeft();
        double dy = 1500 * dt;
        super.getBlockShape().changeUpperLeft(new Point(p.getX(), p.getY() + dy));
    }

    /**
     * a function used the shoot a bullet by the player.
     *
     * @param game the game which the ball is created in.
     */
    public void fire(GameLevel game) {
        int r = 2;
        double x = this.getCollisionRectangle().getUpperLeft().getX() + (this.getCollisionRectangle().getWidth() / 2);
        double y = this.getCollisionRectangle().getUpperLeft().getY() + this.getBlockShape().getHeight() + r + 1;
        Point center = new Point(x, y);
        Ball ball = new Ball(center, r, Color.red);
        Velocity v = Velocity.fromAngleAndSpeed(180, 400);
        ball.setVelocity(v);
        //set the game environment of the ball
        ball.setGameEnv(game.getEnvironment());
        ball.addToGame(game);
        this.alienBalls.add(ball);
    }

    /**
     * a method to reset the alien (return it to its original place).
     */
    public void reset() {
        super.getBlockShape().changeUpperLeft(this.start);
    }

    /**
     * a getter to the list of balls of this alien.
     *
     * @return the list of balls of this alien.
     */
    public List<Ball> getAlienBalls() {
        return this.alienBalls;
    }
}