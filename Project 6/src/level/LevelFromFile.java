package level;

import sprite.Block;
import sprite.Sprite;
import tools.Velocity;

import java.util.List;

/**
 * a class that create a new level from file.
 */
public class LevelFromFile implements LevelInformation {
    private int pSpeed;
    private int pWidth;
    private String levelName;
    private List<Velocity> velocityList;
    private Sprite levelBackground;
    private List<Block> listOfBlocks;
    private int blocksNum;

    /**
     * Constructor.
     *
     * @param name        the level name.
     * @param vList       the velocities list.
     * @param paddleSpeed the paddle speed.
     * @param paddleWidth the paddle width.
     * @param background  the background.
     * @param bList       the blocks list,
     * @param numOfBlocks the number of blocks.
     */
    public LevelFromFile(String name, List<Velocity> vList, int paddleSpeed, int paddleWidth,
                         Sprite background, List<Block> bList, int numOfBlocks) {
        this.levelBackground = background;
        this.levelName = name;
        this.velocityList = vList;
        this.pSpeed = paddleSpeed;
        this.pWidth = paddleWidth;
        this.listOfBlocks = bList;
        this.blocksNum = numOfBlocks;
    }

    /**
     * getter to the number of balls.
     *
     * @return the number of velocities in velocity list.
     */
    @Override
    public int numberOfBalls() {
        return this.velocityList.size();
    }

    /**
     * getter to the list of the velocities.
     *
     * @return the list of the velocities.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return this.velocityList;
    }

    /**
     * getter to the paddle speed.
     *
     * @return the paddle speed.
     */
    @Override
    public int paddleSpeed() {
        return this.pSpeed;
    }

    /**
     * getter to the paddle width.
     *
     * @return the paddle width.
     */
    @Override
    public int paddleWidth() {
        return this.pWidth;
    }

    /**
     * getter to the level name.
     *
     * @return the level name.
     */
    @Override
    public String levelName() {
        return this.levelName;
    }

    /**
     * getter to the background of the level.
     *
     * @return the background of the level.
     */
    @Override
    public Sprite getBackground() {
        return this.levelBackground;
    }

    /**
     * getter to the list of blocks.
     *
     * @return the list of blocks in this level.
     */
    @Override
    public List<Block> blocks() {
        return this.listOfBlocks;
    }

    /**
     * the number of blocks to remove.
     *
     * @return the number of blocks need to be removed to pass the level.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksNum;
    }
}
