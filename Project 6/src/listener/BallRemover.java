package listener;

import animation.GameLevel;
import sprite.Ball;
import sprite.Block;
import tools.Counter;

/**
 * a class that implements listener.HitListener and is removing balls.
 */
public class BallRemover implements HitListener {
    private GameLevel g;
    private Counter remainingBalls;

    /**
     * constructor.
     *
     * @param game        a game to remove the ball from.
     * @param remainBalls a counter of the remaining balls.
     */
    public BallRemover(GameLevel game, Counter remainBalls) {
        this.g = game;
        this.remainingBalls = remainBalls;
    }

    /**
     * a function that is responsible of removing the balls.
     *
     * @param beingHit the object that is being hit
     * @param hitter   the ball that is doing the hitting
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        //when a hit occurs remove the ball that is hitting from the game and decrease the counter by one.
        hitter.removeFromGame(this.g);
        this.remainingBalls.decrease(1);
    }
}