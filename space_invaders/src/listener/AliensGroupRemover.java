package listener;

import sprite.Aliens;
import sprite.Ball;
import sprite.WallBlock;

/**
 * a listener that removes aliens that are hit from the aliens formation.
 */
public class AliensGroupRemover implements HitListener {
    private Aliens aliens;

    /**
     * Constructor.
     *
     * @param aliens1 the aliens formation.
     */
    public AliensGroupRemover(Aliens aliens1) {
        this.aliens = aliens1;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Sprite.Sprite.Ball that's doing the hitting.
     *
     * @param beingHit the object that is being hit
     * @param hitter   the ball that is doing the hitting
     */
    @Override
    public void hitEvent(WallBlock beingHit, Ball hitter) {
        if (hitter.getVelocity().getDy() > 0) {
            return;
        } else {
            this.aliens.remove(beingHit);
        }
    }
}
