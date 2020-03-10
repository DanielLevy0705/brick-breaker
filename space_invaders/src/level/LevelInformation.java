package level;

import sprite.Alien;
import sprite.Sprite;
import sprite.WallBlock;

import java.util.List;

/**
 * an interface to classes that gives information about levels.
 */
public interface LevelInformation {

    /**
     * return the speed of the paddle.
     *
     * @return the speed of the paddle.
     */
    int paddleSpeed();

    /**
     * return the width of the paddle.
     *
     * @return the width of the paddle
     */
    int paddleWidth();

    /**
     * the level name will be displayed at the top of the screen.
     *
     * @return String that holds the level name.
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     *
     * @return a Sprite.Sprite with the background of the level.
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains its size, color and location.
     *
     * @return a list of blocks that make up this level.
     */
    List<WallBlock> blocks();

    /**
     * Number of levels that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     *
     * @return the number of blocks that should be removed.
     */
    int numberOfBlocksToRemove();

    /**
     * a list of aliens in this level.
     * @return the list of aliens in this level.
     */
    List<Alien> aliens();
}