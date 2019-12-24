package listener;

import sprite.Ball;
import sprite.Block;

/**
 * an interface for Hit Listeners objects that tells us what do to when a hit occurs.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Sprite.Sprite.Ball that's doing the hitting.
     *
     * @param beingHit the object that is being hit
     * @param hitter   the ball that is doing the hitting
     */
    void hitEvent(Block beingHit, Ball hitter);
}