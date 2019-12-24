package level;

import sprite.Block;
import sprite.Sprite;
import tools.Velocity;

import java.util.List;

/**
 * an interface to classes that gives information about levels.
 */
public interface LevelInformation {
    /**
     * return the number of the balls in the game.
     *
     * @return the number of the balls in the game.
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball Note that initialBallVelocities().size() == numberOfBalls().
     *
     * @return a list of initial velocities.
     */
    List<Velocity> initialBallVelocities();

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
    List<Block> blocks();

    /**
     * Number of levels that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     *
     * @return the number of blocks that should be removed.
     */
    int numberOfBlocksToRemove();
}