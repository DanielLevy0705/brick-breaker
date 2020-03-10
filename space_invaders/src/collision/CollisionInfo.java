package collision;

import geometry.Point;

/**
 * a class of information on the collidable object and the collision point.
 */
public class CollisionInfo {
    //the collidable object and collision point
    private Collidable col;
    private Point colPoint;

    /**
     * constructor of tools.collision.collision.CollisionInfo object.
     *
     * @param collidable the collidable object
     * @param point      the collision point
     */
    public CollisionInfo(Collidable collidable, Point point) {
        this.col = collidable;
        this.colPoint = point;
    }

    /**
     * the point at which the collision occurs.
     *
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.colPoint;
    }

    /**
     * the collidable object involved in the collision.
     *
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.col;
    }
}