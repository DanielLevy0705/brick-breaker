package listener;

import animation.GameLevel;
import sprite.Ball;
import sprite.WallBlock;

/**
 * a class that implements listener.HitListener and is removing balls.
 */
public class BallRemover implements HitListener {
    private GameLevel g;

    /**
     * constructor.
     *
     * @param game        a game to remove the ball from.
     */
    public BallRemover(GameLevel game) {
        this.g = game;
    }

    /**
     * a function that is responsible of removing the balls.
     *
     * @param beingHit the object that is being hit
     * @param hitter   the ball that is doing the hitting
     */
    @Override
    public void hitEvent(WallBlock beingHit, Ball hitter) {
        //when a hit occurs remove the ball that is hitting from the game and decrease the counter by one.
        hitter.removeFromGame(this.g);
    }
}