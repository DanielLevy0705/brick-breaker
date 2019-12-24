package listener;

import animation.GameLevel;
import sprite.Ball;
import sprite.Block;
import tools.Counter;

/**
 * a listener.BlockRemover is in charge of removing blocks from the game,
 * as well as keeping count of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel g;
    private Counter remainingBlocks;

    /**
     * constructor.
     *
     * @param game         the game from which the block is removed.
     * @param remainBlocks counter for the blocks that remained in the game.
     */
    public BlockRemover(GameLevel game, Counter remainBlocks) {
        this.g = game;
        this.remainingBlocks = remainBlocks;
    }

    /**
     * Blocks that are hit and reach 0 hit-points should be removed
     * from the game. Remember to remove this listener from the block
     * that is being removed from the game.
     *
     * @param beingHit the object that is being hit
     * @param hitter   the ball that is doing the hitting
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        //if the block has 0 hit-points remove it from the game and remove the this hit listener
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(this.g);
            beingHit.removeHitListener(this);
            //decrease the number of remaining blocks by one.
            this.remainingBlocks.decrease(1);
        }
    }
}