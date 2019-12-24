package listener;

import sprite.Ball;
import sprite.Block;
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
    public void hitEvent(Block beingHit, Ball hitter) {
        //increase the score by 5 for every hit and for destruction add 10 points.
        this.currentScore.increase(5);
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(10);
        }
    }
}