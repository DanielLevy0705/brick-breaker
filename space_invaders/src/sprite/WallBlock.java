package sprite;

import animation.GameLevel;
import background.BlockBackground;
import background.BlockColorBackground;
import background.BlockStroke;
import biuoop.DrawSurface;
import collision.Collidable;
import geometry.Point;
import geometry.Rectangle;
import listener.HitListener;
import listener.HitNotifier;
import tools.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * this class creates a new block.
 *
 * @author Daniel Levy
 */
public class WallBlock implements Collidable, Sprite, HitNotifier {
    private Rectangle blockShape;
    private BlockBackground blockBackground;
    private int hitNum;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private BlockStroke blockStroke;
    private Map<Integer, BlockBackground> hpBackground = new TreeMap<Integer, BlockBackground>();
    private int blockWidth, blockHeight;

    /**
     * creating a block which is a rectangle with a color.
     *
     * @param x the x of the top left of the block
     * @param y the y of the top left of the block
     */
    public WallBlock(int x, int y) {
        this.blockShape = new Rectangle(new Point(x, y), 0, 0);
    }

    /**
     * Constructor.
     *
     * @param r  rectangle of the block.
     * @param c  color of the block.
     * @param hp hit points of the block.
     */
    public WallBlock(Rectangle r, Color c, int hp) {
        this.blockShape = r;
        this.blockBackground = new BlockColorBackground(c);
        this.hitNum = hp;
        this.blockStroke = new BlockStroke(Color.GRAY);
    }

    /**
     * Return the "collision shape" of the object.
     *
     * @return the rectangle that we are colliding with.
     */
    public Rectangle getCollisionRectangle() {
        return this.blockShape;
    }

    /**
     * getter to block background.
     *
     * @return the block background
     */
    public BlockBackground getBlockBackground() {
        return this.blockBackground;
    }

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param hitter          the ball that hits the block.
     * @param collisionPoint  the intersection point of the collidable objects
     * @param currentVelocity the current velocity of the object that is colliding
     */
    public void hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(hitter);
    }

    /**
     * function to draw the block on the screen.
     *
     * @param d the surface to draw the block on
     */
    public void drawOn(DrawSurface d) {
        if (this.hpBackground.containsKey(getHitPoints())) {
            this.hpBackground.get(getHitPoints()).draw(d, getBlockShape());
        } else if (this.blockBackground != null) {
            this.blockBackground.draw(d, getBlockShape());
        } else {
            this.blockBackground = new BlockColorBackground(Color.BLACK);
            this.blockBackground.draw(d, getBlockShape());
        }
        if (this.blockStroke != null) {
            this.blockStroke.draw(d, getBlockShape());
        }
    }

    /**
     * notify the block the time passed.
     *
     * @param dt seconds number per frame.
     */
    public void timePassed(double dt) {
        //nothing for now
    }

    /**
     * add the block to the game as a sprite and collidable.
     *
     * @param g the game to add the block to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * getter to the block rectangle.
     *
     * @return the block rectangle
     */
    public Rectangle getBlockShape() {
        return blockShape;
    }

    /**
     * a function to remove the block from the game.
     *
     * @param game the game from which the block is removed.
     */
    public void removeFromGame(GameLevel game) {
        //remove the block from the game environment and the sprite collection.
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * a function to add hit listener.
     *
     * @param hl the hit listener which is added.
     */
    @Override
    public void addHitListener(HitListener hl) {
        //add this hit listener to the list
        this.hitListeners.add(hl);
    }

    /**
     * a function to remove hit listener.
     *
     * @param hl the hit listener which is removed.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        //remove this hit listener from the list
        this.hitListeners.remove(hl);
    }

    /**
     * a function that notifys the hit listener when the block is being hit.
     *
     * @param hitter the ball that is hitting the block.
     */
    public void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * a function that adds new background by hit points.
     *
     * @param hp the hit point number of the block.
     * @param bg the background that fits that hit point number.
     */
    public void newBackground(int hp, BlockBackground bg) {
        this.hpBackground.put(hp, bg);
    }

    /**
     * a setter for the stroke of the block.
     *
     * @param stroke the stroke of the block.
     */
    public void setStroke(BlockStroke stroke) {
        this.blockStroke = stroke;
    }

    /**
     * a getter function to hit points.
     *
     * @return the hit points.
     */
    public int getHitPoints() {
        return this.hitNum;
    }

    /**
     * a setter for the block hit points.
     *
     * @param hp the block hit points.
     */
    public void setHitPoints(int hp) {
        this.hitNum = hp;
    }

    /**
     * a setter for the block width.
     *
     * @param width the block width.
     */
    public void setWidth(int width) {
        this.blockWidth = width;
    }

    /**
     * a setter for the block height.
     *
     * @param height the block height.
     */
    public void setHeight(int height) {
        this.blockHeight = height;
    }

    /**
     * a setter for the block default background.
     *
     * @param background the block background.
     */
    public void setBackground(BlockBackground background) {
        this.blockBackground = background;
    }

    /**
     * a setter for the rectangle that is the shape of the block.
     *
     * @param r the block rectangle.
     */
    public void setBlockShape(Rectangle r) {
        this.blockShape = r;
    }

    /**
     * a getter to the list of listeners.
     * @return the list of the ball listeners.
     */
    public List<HitListener> getListeners() {
        return this.hitListeners;
    }
}