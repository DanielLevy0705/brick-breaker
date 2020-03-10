package collision;

import geometry.Point;
import geometry.Rectangle;
import sprite.Ball;
import tools.Velocity;

/**
 * Collidables interface.
 */
public interface Collidable {
    /**
     * Return the "collision shape" of the object.
     *
     * @return the rectangle that we are colliding with.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param hitter          the ball that hit the blocks.
     * @param collisionPoint  the intersection point of the collidable objects.
     * @param currentVelocity the current velocity of the object that is colliding.
     */
    void hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
