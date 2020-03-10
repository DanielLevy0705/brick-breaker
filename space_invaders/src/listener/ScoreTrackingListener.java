package listener;

import sprite.Ball;
import sprite.WallBlock;
import tools.Counter;

/**
 * a listener that tracks the score.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructor.
     *
     * @param scoreCounter counter that counts the score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * a function that tells us what to do when there is a hit.
     *
     * @param beingHit the object that is being hit
     * @param hitter   the ball that is doing the hitting
     */
    public void hitEvent(WallBlock beingHit, Ball hitter) {
        //increase the score by 100 for every hit.
        if (hitter.getVelocity().getDy() < 0) {
            this.currentScore.increase(100);
        }
    }
}