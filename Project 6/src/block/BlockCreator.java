package block;

import sprite.Block;

/**
 * an interface for block creators.
 */
public interface BlockCreator {
    /**
     * create a block at the specified location.
     *
     * @param xpos the x of the block.
     * @param ypos the y of the block.
     * @return a new block.
     */
    Block create(int xpos, int ypos);
}