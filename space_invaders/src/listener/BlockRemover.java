package listener;

import animation.GameLevel;
import sprite.Alien;
import sprite.Ball;
import sprite.WallBlock;
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
    public void hitEvent(WallBlock beingHit, Ball hitter) {
        //if the block has 0 hit-points remove it from the game and remove the this hit listener
        if (beingHit.getHitPoints() == 0) {
            if (beingHit instanceof Alien) {
                if (hitter.getVelocity().getDy() > 0) {
                    return;
                } else {
                    beingHit.removeFromGame(this.g);
                    for (int i = 0; i < beingHit.getListeners().size(); i++) {
                        beingHit.removeHitListener(beingHit.getListeners().get(i));
                    }
                    //decrease the number of remaining blocks by one.
                    this.remainingBlocks.decrease(1);
                }
            } else {
                beingHit.removeFromGame(this.g);
                for (int i = 0; i < beingHit.getListeners().size(); i++) {
                    beingHit.removeHitListener(beingHit.getListeners().get(i));
                }
            }
        }
    }
}