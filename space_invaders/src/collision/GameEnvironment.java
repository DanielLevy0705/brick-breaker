package collision;

import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * class of the game environment which in the game environment.
 */
public class GameEnvironment {
    private List<Collidable> colList = new ArrayList<Collidable>();

    /**
     * empty constructor.
     */
    public GameEnvironment() {

    }

    /**
     * add the given collidable to the environment.
     *
     * @param c a collidable to add
     */
    public void addCollidable(Collidable c) {
        colList.add(c);
    }

    /**
     * remove the given collidable from the environment.
     *
     * @param c a collidable to remove
     */
    public void removeCollidable(Collidable c) {
        colList.remove(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables in this collection, return null.
     * Else, return the information about the closest collision that is going to occur.
     *
     * @param trajectory the trajectory of the ball
     * @return the collision info
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        List<Point> collisionPointList = new ArrayList<Point>();
        List<CollisionInfo> infoList = new ArrayList<CollisionInfo>();
        //a flag and distances
        int flag = 0;
        double distance1 = -1, distance2 = -1;
        List<Collidable> colListCopy = new ArrayList<Collidable>(this.colList);
        //create new collision info object
        CollisionInfo collisionInfo;
        //fill the list of collision point and collision info for every collidable
        for (int i = 0; i < colListCopy.size(); i++) {
            Point colPoint = trajectory.closestIntersectionToStartOfLine(colListCopy.get(i).getCollisionRectangle());
            infoList.add(new CollisionInfo(colListCopy.get(i), colPoint));
            collisionPointList.add(infoList.get(i).collisionPoint());
        }
        //check for every collision point if its null and if its not check the distance from the trajectory end
        for (int i = 0; i < collisionPointList.size(); i++) {
            for (int j = i + 1; j < collisionPointList.size(); j++) {
                if (collisionPointList.get(i) != null) {
                    distance1 = collisionPointList.get(i).distance(trajectory.end());
                }
                if (collisionPointList.get(j) != null) {
                    distance2 = collisionPointList.get(j).distance(trajectory.end());
                }
                //the bigger distance means it closer to the line so make a flag that will keep the index
                if (distance1 < distance2) {
                    flag = j;
                    distance2 = -1;
                } else if (distance1 > distance2) {
                    flag = i;
                    distance1 = -1;
                }
            }
        }
        //return the collision info of the index of the flag.
        collisionInfo = infoList.get(flag);
        return collisionInfo;
    }
}